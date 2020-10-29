// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders;

import java.util.Deque;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.polarsys.capella.core.data.capellacore.GeneralizableElement;
import org.polarsys.capella.core.data.capellacore.NamedElement;
import org.polarsys.capella.core.data.information.Property;
import org.polarsys.capella.core.data.information.datavalue.DataValue;

import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataModelElement;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.modelling.data.DataTypeReference;
import com.n7space.capellatasteplugin.modelling.data.StructuredDataType;
import com.n7space.capellatasteplugin.modelling.data.StructuredDataType.Kind;
import com.n7space.capellatasteplugin.utils.Issue;

/**
 * Interpretation provider for Capella class element. Also handles unions, as
 * unions in Capella are specialization of classes.
 */
public class ClassModelElementInterpretationProvider extends BaseDataModelElementInterpetationProvider implements
		com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.DataModelElementInterpreter.DataModelElementInterpretationProvider {

	protected DataTypeReference extractSuperclass(final DataModel dataModel, final List<Issue> issues,
			final org.polarsys.capella.core.data.information.Class classElement, final Kind kind) {
		final EList<GeneralizableElement> parents = classElement.getSuper();
		if (parents != null) {
			if (kind == Kind.Union && parents.size() > 0) {
				issues.add(
						new Issue(Issue.Kind.Warning,
								"Union " + classElement.getName()
										+ " has at least one parent - union inheritance is not supported",
								classElement));
			} else {
				if (parents.size() > 1) {
					issues.add(new Issue(Issue.Kind.Warning,
							"Class " + classElement.getName()
									+ " has multiple super classes - multiple inheritance is not supported",
							classElement));
				}
				if (parents.size() == 1) {
					return getDataTypeReference(dataModel, parents.get(0), issues);
				}
			}
		}
		return null;
	}

	/**
	 * Provides interpretation for the given Capella data model element.
	 *
	 * @param dataModel
	 *            The internal data model
	 * @param currentDataPackage
	 *            The current internal data package
	 * @param context
	 *            The current interpretation context
	 * @param interpretedElement
	 *            The element to be interpreted
	 * @param issues
	 *            [output] List of detected issues
	 * @return The element converted to the internal data model or null if the
	 *         interpretation failed
	 */
	@Override
	public DataModelElement interpretModelElement(final DataModel dataModel, final DataPackage currentDataPackage,
			final Deque<DataModelElement> context, final NamedElement interpretedElement, final List<Issue> issues) {
		final org.polarsys.capella.core.data.information.Class classElement = (org.polarsys.capella.core.data.information.Class) interpretedElement;
		final Kind kind = (interpretedElement instanceof org.polarsys.capella.core.data.information.Union) ? Kind.Union
				: Kind.Structure;

		final DataTypeReference superClass = extractSuperclass(dataModel, issues, classElement, kind);

		final StructuredDataType dataType = superClass != null
				? new StructuredDataType(currentDataPackage, superClass, classElement.getName(), classElement.getId(),
						kind)
				: new StructuredDataType(currentDataPackage, classElement.getName(), classElement.getId(), kind);
		for (final Property cp : classElement.getContainedProperties()) {
			intepretTypedMember(dataModel, currentDataPackage, dataType, cp, issues);
		}

		for (final DataValue val : classElement.getOwnedDataValues()) {
			intepretTypedMember(dataModel, currentDataPackage, dataType, val, issues);
		}

		if (interpretedElement instanceof org.polarsys.capella.core.data.information.Union) {
			final org.polarsys.capella.core.data.information.Union unionElement = (org.polarsys.capella.core.data.information.Union) interpretedElement;
			for (final Property up : unionElement.getContainedUnionProperties()) {
				intepretTypedMember(dataModel, currentDataPackage, dataType, up, issues);
			}
		}
		dataType.description = extractComment(classElement);
		currentDataPackage.addTypeDefinition(dataType);
		return dataType;
	}
}
