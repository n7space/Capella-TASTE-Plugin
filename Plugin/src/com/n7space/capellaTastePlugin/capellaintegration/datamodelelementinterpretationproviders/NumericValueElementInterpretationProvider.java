// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders;

import java.util.Deque;
import java.util.List;

import org.polarsys.capella.core.data.capellacore.NamedElement;
import org.polarsys.capella.core.data.capellacore.Type;
import org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue;

import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataModelElement;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.modelling.data.DataType;
import com.n7space.capellatasteplugin.modelling.data.DataTypeReference;
import com.n7space.capellatasteplugin.modelling.data.EnumeratedDataType;
import com.n7space.capellatasteplugin.modelling.data.EnumeratedDataType.Literal;
import com.n7space.capellatasteplugin.utils.Issue;

/**
 * Interpretation provider for Capella numeric value element.
 */
public class NumericValueElementInterpretationProvider extends BaseDataModelElementInterpetationProvider implements
		com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.DataModelElementInterpreter.DataModelElementInterpretationProvider {

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
		final LiteralNumericValue element = (LiteralNumericValue) interpretedElement;
		final Object actualValue = element.getValue();
		final Type type = element.getType();
		DataTypeReference typeReference = null;
		if (type == null) {
			final DataModelElement contextParent = context.peek();
			if (contextParent != null
					&& (contextParent instanceof EnumeratedDataType || contextParent instanceof Literal)) {
				// Assume integer. Don't add it as a separate declared value.
				typeReference = new DataTypeReference(null, "INTEGER", DataModel.BUILT_IN_INTEGER_TYPE_ID);
			} else {
				issues.add(new Issue(Issue.Kind.Warning, "Cannot get type of element named " + element.getName(),
						element));
				return null;
			}
		} else {
			typeReference = getDataTypeReference(dataModel, type, issues);
		}

		final DataType.DataTypeValue value = new DataType.DataTypeValue(currentDataPackage,
				getValueName(context, interpretedElement), element.getId(), typeReference, actualValue);
		currentDataPackage.addValueDefinition(value);
		return value;
	}
}
