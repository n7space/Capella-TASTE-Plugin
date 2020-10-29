// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders;

import java.util.Deque;
import java.util.List;

import org.polarsys.capella.core.data.capellacore.NamedElement;
import org.polarsys.capella.core.data.capellacore.Type;
import org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue;
import org.polarsys.capella.core.data.information.datavalue.UnaryExpression;

import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.DataModelElementInterpreter.DataModelElementInterpretationProvider;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataModelElement;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.modelling.data.DataType;
import com.n7space.capellatasteplugin.modelling.data.DataType.DataTypeValue;
import com.n7space.capellatasteplugin.modelling.data.DataTypeReference;
import com.n7space.capellatasteplugin.utils.Issue;

/**
 * Interpretation provider for Capella unary expression element.
 */
public class UnaryExpressionInterpretationProvider extends BaseDataModelElementInterpetationProvider
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
		final UnaryExpression element = (UnaryExpression) interpretedElement;
		final LiteralNumericValue resolvedValue = resolveLiteralNumericValue(dataModel, element, issues);
		if (resolvedValue == null) {
			issues.add(new Issue(Issue.Kind.Error,
					"Could not resolve the value of unary expression " + interpretedElement.getName(),
					interpretedElement));
			return null;
		}

		final Type type = element.getType();
		final DataTypeReference valueType = getDataTypeReference(dataModel, type, issues);
		if (valueType == null) {
			issues.add(new Issue(Issue.Kind.Error,
					"Could not resolve the type of unary expression " + interpretedElement.getName(),
					interpretedElement));
			return null;
		}
		final Object actualValue = resolvedValue.getValue();
		final DataType.DataTypeValue value = new DataTypeValue(currentDataPackage, element.getName(), element.getId(),
				valueType, actualValue);
		currentDataPackage.addValueDefinition(value);
		return value;
	}

}