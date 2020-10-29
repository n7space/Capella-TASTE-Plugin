// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders;

import java.util.Deque;
import java.util.List;

import org.polarsys.capella.core.data.capellacore.NamedElement;
import org.polarsys.capella.core.data.information.datatype.NumericTypeKind;

import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataModelElement;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.modelling.data.DataType;
import com.n7space.capellatasteplugin.modelling.data.FloatDataType;
import com.n7space.capellatasteplugin.modelling.data.IntegerDataType;
import com.n7space.capellatasteplugin.utils.Issue;

/**
 * Interpretation provider for Capella numeric element.
 */
public class NumericElementInterpretationProvider extends BaseDataModelElementInterpetationProvider implements
		com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.DataModelElementInterpreter.DataModelElementInterpretationProvider {

	protected DataType interpretFloatNumeric(final DataModel dataModel, final DataPackage currentDataPackage,
			final org.polarsys.capella.core.data.information.datatype.NumericType numericType,
			final List<Issue> issues) {
		final FloatDataType dataType = new FloatDataType(currentDataPackage,
				extractSuperclass(dataModel, issues, numericType), numericType.getName(), numericType.getId());
		dataType.upperBound = getUsedNumericValue(dataModel, numericType.getOwnedMaxValue(), issues);
		dataType.lowerBound = getUsedNumericValue(dataModel, numericType.getOwnedMinValue(), issues);
		dataType.unit = extractUnit(numericType);
		dataType.description = extractComment(numericType);
		currentDataPackage.addTypeDefinition(dataType);
		return dataType;
	}

	protected DataType interpretIntegerNumeric(final DataModel dataModel, final DataPackage currentDataPackage,
			final org.polarsys.capella.core.data.information.datatype.NumericType numericType,
			final List<Issue> issues) {
		final IntegerDataType dataType = new IntegerDataType(currentDataPackage,
				extractSuperclass(dataModel, issues, numericType), numericType.getName(), numericType.getId());
		dataType.upperBound = getUsedNumericValue(dataModel, numericType.getOwnedMaxValue(), issues);
		dataType.lowerBound = getUsedNumericValue(dataModel, numericType.getOwnedMinValue(), issues);
		dataType.unit = extractUnit(numericType);
		dataType.description = extractComment(numericType);
		currentDataPackage.addTypeDefinition(dataType);
		return dataType;
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
	public DataType interpretModelElement(final DataModel dataModel, final DataPackage currentDataPackage,
			final Deque<DataModelElement> context, final NamedElement interpretedElement, final List<Issue> issues) {
		final org.polarsys.capella.core.data.information.datatype.NumericType numericType = (org.polarsys.capella.core.data.information.datatype.NumericType) interpretedElement;
		if (numericType.getKind() == NumericTypeKind.FLOAT) {
			return interpretFloatNumeric(dataModel, currentDataPackage, numericType, issues);
		} else if (numericType.getKind() == NumericTypeKind.INTEGER) {
			return interpretIntegerNumeric(dataModel, currentDataPackage, numericType, issues);
		} else {
			issues.add(new Issue(Issue.Kind.Error, "Unhandled numeric type " + interpretedElement.getName(),
					interpretedElement));
			return null;
		}

	}

}
