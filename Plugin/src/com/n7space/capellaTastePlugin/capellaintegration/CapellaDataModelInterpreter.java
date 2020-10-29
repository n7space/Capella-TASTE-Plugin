// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration;

import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.polarsys.capella.core.data.information.DataPkg;
import org.polarsys.capella.core.data.information.datavalue.DataValue;

import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.DataModelElementInterpreter;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataModelElement;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.modelling.data.DataType;
import com.n7space.capellatasteplugin.modelling.data.DataTypeReference;
import com.n7space.capellatasteplugin.modelling.data.DependentItem;
import com.n7space.capellatasteplugin.modelling.data.FloatDataType;
import com.n7space.capellatasteplugin.modelling.data.IntegerDataType;
import com.n7space.capellatasteplugin.modelling.data.MemberDefinition;
import com.n7space.capellatasteplugin.modelling.data.StructuredDataType;
import com.n7space.capellatasteplugin.modelling.data.Unit;
import com.n7space.capellatasteplugin.utils.Issue;

/**
 * Capella data model interpreter.
 *
 * Converts the Capella data model contained in a list of packages into an
 * internal data model understood by the rest of the plugin.
 */
public class CapellaDataModelInterpreter {

	protected final DataModelElementInterpreter elementInterpreter;

	/**
	 * The constructor.
	 *
	 * @param dataModelElementInterpreter
	 *            Interpreter of data model elements.
	 */
	public CapellaDataModelInterpreter(final DataModelElementInterpreter dataModelElementInterpreter) {
		elementInterpreter = dataModelElementInterpreter;
	}

	/**
	 * Converts the Capella data model contained in a list of packages into an
	 * internal data model.
	 *
	 * @param pkgs
	 *            List of Capella packages containing the Capella data model
	 * @param issues
	 *            [output] List of the issues detected during conversion
	 * @return The converted data model
	 */
	public DataModel convertCapellaDataModelToAbstractDataModel(final List<DataPkg> pkgs, final List<Issue> issues) {
		final DataModel model = new DataModel();
		final Set<DataPkg> allPackages = new HashSet<DataPkg>();
		gatherAllPackages(model, null, pkgs, allPackages, issues);
		for (final DataPkg pkg : allPackages) {
			final DataPackage dataPackage = model.findDataPackageById(pkg.getId());
			if (dataPackage == null) {
				issues.add(new Issue(Issue.Kind.Error, "Cannot process the package. Orphan?", pkg));
				continue;
			}
			extractOwnedDataTypes(model, dataPackage, pkg, issues);
			extractOwnedCollectinos(model, dataPackage, pkg, issues);
			extractOwnedDataValues(model, dataPackage, pkg, issues);
			extractOwnedClasses(model, dataPackage, pkg, issues);
			extractOwnedExchangeItems(model, dataPackage, pkg, issues);
		}
		createPostfixesFromUnits(model);
		return model;
	}

	/**
	 * Creates a postfix for each numeric (float or integer) data type name if the
	 * corresponding type has an associated physical unit.
	 *
	 * @param model
	 *            Data model to be processed
	 */
	public void createPostfixesFromUnits(final DataModel model) {
		for (final DataPackage pkg : model.dataPackages) {
			for (final DataModelElement element : pkg.getDefinedElements()) {
				if (element instanceof FloatDataType) {
					createPostfixFromUnit(model, pkg, (FloatDataType) element, ((FloatDataType) element).unit);
				}
				if (element instanceof IntegerDataType) {
					createPostfixFromUnit(model, pkg, (IntegerDataType) element, ((IntegerDataType) element).unit);
				}
			}
		}
	}

	protected void createPostfixFromUnit(final DataModel model, final DataPackage pkg, final DataType type,
			final Unit unit) {
		if (unit == null)
			return;

		if (unit.name == null || unit.name.length() == 0)
			return;
		// To avoid duplication
		if (type.name.endsWith("-" + unit.name))
			return;
		renameType(model, pkg.name, type.name, type.id, type.name + "-" + unit.name);
	}

	protected void extractOwnedClasses(final DataModel dataModel, final DataPackage parentPackage, final DataPkg pkg,
			final List<Issue> issues) {
		if (pkg.getOwnedClasses() == null)
			return;
		for (final org.polarsys.capella.core.data.information.Class cc : pkg.getOwnedClasses()) {
			elementInterpreter.interpretDataModelElement(dataModel, parentPackage, new ArrayDeque<DataModelElement>(),
					cc, issues);
		}
	}

	protected void extractOwnedCollectinos(final DataModel dataModel, final DataPackage parentPackage,
			final DataPkg pkg, final List<Issue> issues) {
		if (pkg.getOwnedCollections() == null)
			return;
		for (final org.polarsys.capella.core.data.information.Collection cc : pkg.getOwnedCollections()) {
			elementInterpreter.interpretDataModelElement(dataModel, parentPackage, new ArrayDeque<DataModelElement>(),
					cc, issues);
		}
	}

	protected void extractOwnedDataTypes(final DataModel dataModel, final DataPackage parentPackage, final DataPkg pkg,
			final List<Issue> issues) {
		if (pkg.getOwnedDataTypes() == null)
			return;
		for (final org.polarsys.capella.core.data.information.datatype.DataType cdt : pkg.getOwnedDataTypes()) {
			elementInterpreter.interpretDataModelElement(dataModel, parentPackage, new ArrayDeque<DataModelElement>(),
					cdt, issues);
		}
	}

	protected void extractOwnedDataValues(final DataModel dataModel, final DataPackage parentPackage, final DataPkg pkg,
			final List<Issue> issues) {
		if (pkg.getOwnedDataValues() == null)
			return;
		for (final DataValue value : pkg.getOwnedDataValues()) {
			elementInterpreter.interpretDataModelElement(dataModel, parentPackage, new ArrayDeque<DataModelElement>(),
					value, issues);
		}
	}

	protected void extractOwnedExchangeItems(final DataModel dataModel, final DataPackage parentPackage,
			final DataPkg pkg, final List<Issue> issues) {
		if (pkg.getOwnedExchangeItems() == null)
			return;
		for (final org.polarsys.capella.core.data.information.ExchangeItem ei : pkg.getOwnedExchangeItems()) {
			elementInterpreter.interpretDataModelElement(dataModel, parentPackage, new ArrayDeque<DataModelElement>(),
					ei, issues);
		}
	}

	protected void gatherAllPackages(final DataModel dataModel, final DataPackage parentPackage,
			final Collection<DataPkg> inputPackages, final Set<DataPkg> outputPackages, final List<Issue> issues) {
		for (final DataPkg pkg : inputPackages) {
			outputPackages.add(pkg);
			final DataPackage dataPackage = (DataPackage) elementInterpreter.interpretDataModelElement(dataModel,
					parentPackage, new ArrayDeque<DataModelElement>(), pkg, issues);
			if (pkg.getOwnedDataPkgs() != null) {
				gatherAllPackages(dataModel, dataPackage, pkg.getOwnedDataPkgs(), outputPackages, issues);
			}
		}
	}

	protected void renameElement(final DataModelElement element, final String packageName, final String name,
			final String id, final String newName) {
		if (!(element instanceof DataType)) {
			return;
		}
		final DataType type = (DataType) element;
		try {
			if (type.name.equals(name) && type.id.equals(id) && type.dataPackage.name.equals(packageName)) {
				type.name = newName;
			}
		} catch (final NullPointerException e) {
			// Some of the fields is null, ergo not equal.
		}
	}

	protected void renameReferences(final DataModelElement element, final String packageName, final String name,
			final String id, final String newName) {
		try {
			final Class<?> elementClass = element.getClass();
			for (final Field field : elementClass.getFields()) {
				if (!field.getType().equals(DataTypeReference.class)) {
					continue;
				}
				final DataTypeReference reference = (DataTypeReference) field.get(element);
				if (reference == null)
					continue;
				if (reference.dataPackage == null) {
					continue;
				}
				if (reference.dataPackage.name.equals(packageName) && reference.name.equals(name)
						&& reference.id.equals(id)) {
					reference.name = newName;
				}
			}
		} catch (final Throwable t) {
			// Some assumptions are not met, meaining that this field is not eligible to
			// update.
		}
	}

	protected void renameType(final DataModel model, final String packageName, final String name, final String id,
			final String newName) {
		for (final DataPackage pkg : model.dataPackages) {
			for (final DataModelElement element : pkg.getDefinedElements()) {
				renameElement(element, packageName, name, id, newName);
				renameReferences(element, packageName, name, id, newName);
			}
			for (final DataModelElement element : pkg.getDeclaredTypes()) {
				// Some things may be repeated
				renameElement(element, packageName, name, id, newName);
				renameReferences(element, packageName, name, id, newName);

				if (element instanceof DependentItem) {
					for (final DataModelElement dependency : ((DependentItem) element).getDependencies(model)) {
						renameReferences(dependency, packageName, name, id, newName);
					}
				}
				if (element instanceof StructuredDataType) {
					final StructuredDataType struct = (StructuredDataType) element;
					for (final MemberDefinition member : struct.members) {
						renameReferences(member, packageName, name, id, newName);
					}
				}

			}
		}
	}

}
