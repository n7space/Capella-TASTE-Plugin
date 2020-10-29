// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders;

import java.util.Deque;
import java.util.List;

import org.polarsys.capella.core.data.capellacore.NamedElement;
import org.polarsys.capella.core.data.information.datavalue.LiteralBooleanValue;

import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.DataModelElementInterpreter.DataModelElementInterpretationProvider;
import com.n7space.capellatasteplugin.modelling.data.BooleanDataType;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataModelElement;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.modelling.data.DataType;
import com.n7space.capellatasteplugin.modelling.data.DataType.DataTypeValue;
import com.n7space.capellatasteplugin.modelling.data.DataTypeReference;
import com.n7space.capellatasteplugin.utils.Issue;

/**
 * Interpretation provider for Capella boolean element.
 */
public class BooleanElementInterpretationProvider extends BaseDataModelElementInterpetationProvider
		implements DataModelElementInterpretationProvider {

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
		final org.polarsys.capella.core.data.information.datatype.BooleanType element = (org.polarsys.capella.core.data.information.datatype.BooleanType) interpretedElement;
		final DataType dataType = new BooleanDataType(currentDataPackage, element.getName(), element.getId());
		dataType.description = extractComment(element);
		currentDataPackage.addTypeDefinition(dataType);
		for (final LiteralBooleanValue literal : element.getOwnedLiterals()) {
			final DataTypeReference valueType = new DataTypeReference(currentDataPackage, element.getName(),
					element.getId());
			final Object actualValue = Boolean.valueOf(literal.isValue());
			final DataType.DataTypeValue value = new DataTypeValue(currentDataPackage,
					element.getName() + "-" + literal.getName(), literal.getId(), valueType, actualValue);
			currentDataPackage.addValueDefinition(value);
		}
		return dataType;
	}

}
