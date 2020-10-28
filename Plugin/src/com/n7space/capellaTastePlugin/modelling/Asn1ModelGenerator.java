// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.n7space.capellatasteplugin.modelling.ModelItem.Kind;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataModelElement;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.modelling.data.DataType;
import com.n7space.capellatasteplugin.modelling.data.DataTypeReference;
import com.n7space.capellatasteplugin.modelling.data.DependentItem;
import com.n7space.capellatasteplugin.utils.TopologicalSorter;

/**
 * Class for transforming the internal data model into ASN.1 data model
 */
public class Asn1ModelGenerator {

  protected final NameConverter nameConverter;
  protected final Asn1ElementDefinitionGenerator elementDefinitionGenerator;

  /**
   * The constructor.
   *
   * @param converter
   *          Name converter for transforming user-supplied names into ASN.1 compatible ones
   * @param definitionGenerator
   *          ASN.1 definition provider for individual data model elements
   */
  public Asn1ModelGenerator(final NameConverter converter, final Asn1ElementDefinitionGenerator definitionGenerator) {
    nameConverter = converter;
    elementDefinitionGenerator = definitionGenerator;
  }

  protected void addForcedImports(final DataPackage pkg, final Map<DataPackage, Set<DataTypeReference>> result) {
    for (final DataTypeReference type : pkg.getForcedImports()) {
      Set<DataTypeReference> types = result.get(type.dataPackage);
      if (types == null) {
        types = new HashSet<DataTypeReference>();
        result.put(type.dataPackage, types);
      }
      types.add(type);
    }
  }

  protected void addStandardImports(final DataPackage pkg, final Map<DataPackage, Set<DataTypeReference>> result) {
    for (final DataTypeReference type : pkg.getStandardImports()) {
      Set<DataTypeReference> types = result.get(type.dataPackage);
      if (types == null) {
        types = new HashSet<DataTypeReference>();
        result.put(type.dataPackage, types);
      }
      types.add(type);
    }
  }

  protected void appendDataTypeImports(final StringBuilder sb, final List<DataTypeReference> standardImports,
      final Map<DataPackage, Set<DataTypeReference>> imports) {
    if (imports.size() == 0)
      return;
    sb.append("IMPORTS\n");
    for (final DataPackage pkg : imports.keySet()) {
      final Set<DataTypeReference> dependencies = imports.get(pkg);
      final int count = dependencies.size();
      int index = 0;
      boolean areStandardImports = false;
      for (final DataTypeReference dependency : dependencies) {
        final boolean isStandardImport = standardImports.contains(dependency);
        areStandardImports |= isStandardImport;
        // Standard imports should not be mangled.
        final String name = isStandardImport ? dependency.name
            : nameConverter.getAsn1TypeName(dependency.dataPackage.name, dependency.name, dependency.id);
        sb.append("\t" + name + ((index == count - 1) ? "" : ",") + "\n");
        index++;
      }
      // Standard imports should not be mangled.
      final String packageName = areStandardImports ? pkg.name : nameConverter.getAsn1TypeName("", pkg.name, "");
      sb.append("\t\tFROM " + packageName + "\n");
    }
    sb.append("\t;\n\n");
  }

  protected void appendDefinitions(final StringBuilder sb, final DataModel model, final DataPackage pkg) {
    final List<DataModelElement> elements = new LinkedList<DataModelElement>();
    elements.addAll(pkg.getDefinedTypes());
    elements.addAll(pkg.getDefinedValues());
    final List<DataModelElement> sortedElements = sortPackageElements(model, pkg, elements);

    for (final DataModelElement element : sortedElements) {
      try {
        appendElementDefinition(sb, model, element);
      } catch (final Throwable t) {
        t.printStackTrace();
      }
    }

  }

  protected void appendElementDefinition(final StringBuilder sb, final DataModel model,
      final DataModelElement element) {
    sb.append(elementDefinitionGenerator.generateAsn1Definition(model, element));
  }

  protected void appendPackageDefinitionEnd(final StringBuilder sb, final String name) {
    sb.append("\nEND\n");
  }

  protected void appendPackageDefinitionStart(final StringBuilder sb, final String name) {
    sb.append(name);
    sb.append(" DEFINITIONS AUTOMATIC TAGS ::= BEGIN\n\n");
  }

  protected void appendPackageImports(final StringBuilder sb, final DataModel dataModel, final DataPackage pkg) {
    final Set<DataModelElement> dependencies = new HashSet<DataModelElement>();
    for (final DataModelElement element : pkg.getDefinedElements()) {
      if (element instanceof DependentItem) {
        final DependentItem dependentItem = (DependentItem) element;
        final Set<DataModelElement> itemDependencies = dependentItem.getDependencies(dataModel);
        dependencies.addAll(itemDependencies);
      }
    }
    final Map<DataPackage, Set<DataTypeReference>> dataTypeDependencies = getDataTypeDependencies(dataModel, pkg,
        dependencies);
    addForcedImports(pkg, dataTypeDependencies);
    addStandardImports(pkg, dataTypeDependencies);
    appendDataTypeImports(sb, pkg.getStandardImports(), dataTypeDependencies);
  }

  /**
   * Generates an ASN.1 data model from the given internal data model.
   *
   * @param dataModel
   *          Internal data model
   * @return ASN.1 data model
   */
  public ModelItems generateAsn1DataModelFromAbstractDataModel(final DataModel dataModel) {
    final ModelItems items = new ModelItems();
    for (final DataPackage pkg : dataModel.dataPackages) {
      if (pkg.getDefinedElements().size() > 0 || pkg.getForcedImports().size() > 0
          || pkg.getStandardImports().size() > 0) {
        items.addAll(generateAsn1DataModelFromAbstractDataModel(dataModel, pkg));
      }
    }
    return items;
  }

  protected ModelItems generateAsn1DataModelFromAbstractDataModel(final DataModel model,
      final DataPackage dataPackage) {
    final String packageName = nameConverter.getPackageName(dataPackage.name, dataPackage.id);
    final StringBuilder sb = new StringBuilder();
    appendPackageDefinitionStart(sb, packageName);
    appendPackageImports(sb, model, dataPackage);
    appendDefinitions(sb, model, dataPackage);
    appendPackageDefinitionEnd(sb, packageName);

    final ByteBuffer buffer = ByteBuffer.wrap(sb.toString().getBytes(Charset.forName("UTF-8")));
    final ModelItem item = new ModelItem(packageName, Kind.ASN1, buffer);
    final ModelItems result = new ModelItems();
    result.add(item);
    return result;
  }

  protected Map<DataPackage, Set<DataTypeReference>> getDataTypeDependencies(final DataModel dataModel,
      final DataPackage pkg, final Iterable<DataModelElement> elements) {
    final Map<DataPackage, Set<DataTypeReference>> result = new HashMap<DataPackage, Set<DataTypeReference>>();
    for (final DataModelElement element : elements) {
      if (element instanceof DataType) {
        final DataType type = (DataType) element;
        getDataTypeDependencyFromType(dataModel, pkg, type, result);
      }
      if (element instanceof DataType.DataTypeValue) {
        final DataType.DataTypeValue value = (DataType.DataTypeValue) element;
        final DataType type = dataModel.findDataTypeById(value.type.id);
        getDataTypeDependencyFromType(dataModel, pkg, type, result);
      }
    }
    return result;
  }

  protected boolean isTypeReferenced(final Set<DataTypeReference> references, final DataType type) {
    for (final DataTypeReference reference : references) {
      if (reference.name.equals(type.name))
        return true;
    }
    return false;
  }

  protected void getDataTypeDependencyFromType(final DataModel dataModel, final DataPackage pkg, final DataType type,
      final Map<DataPackage, Set<DataTypeReference>> dependencies) {
    if (type.dataPackage.id.length() == 0) {
      // Special type "null" package.
      return;
    }
    if (type.dataPackage.id.equals(pkg.id)) {
      return;
    }
    Set<DataTypeReference> types = dependencies.get(type.dataPackage);
    if (types == null) {
      types = new HashSet<DataTypeReference>();
      dependencies.put(type.dataPackage, types);
    }
    if (!isTypeReferenced(types, type))
      types.add(new DataTypeReference(type));
  }

  protected List<DataModelElement> sortPackageElements(final DataModel model, final DataPackage pkg,
      final List<DataModelElement> elements) {
    try {
      final TopologicalSorter sorter = new TopologicalSorter();
      final Set<DataModelElement> packageElements = (new HashSet<DataModelElement>(pkg.getDefinedElements()));
      for (final DataModelElement element : elements) {
        sorter.addItem(element);
        if (element instanceof DependentItem) {
          final Set<DataModelElement> dependencies = ((DependentItem) element).getDependencies(model);
          for (final DataModelElement dependency : dependencies) {
            if (packageElements.contains(dependency))
              sorter.addDependency(element, dependency);
          }
        }
      }
      final List<DataModelElement> sortedElements = new LinkedList<DataModelElement>();
      for (final Object o : sorter.getSortedItems()) {
        sortedElements.add((DataModelElement) o);
      }
      return sortedElements;

    } catch (final Throwable t) {
      return elements;
    }
  }

}
