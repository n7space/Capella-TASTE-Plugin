// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders;

import java.util.Deque;
import java.util.List;

import org.polarsys.capella.core.data.capellacore.NamedElement;
import org.polarsys.capella.core.data.information.datavalue.NumericValue;

import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataModelElement;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.modelling.data.StringDataType;
import com.n7space.capellatasteplugin.modelling.data.UsedNumericValue;
import com.n7space.capellatasteplugin.utils.Issue;

/**
 * Interpretation provider for Capella string element.
 */
public class StringElementInterpretationProvider extends BaseDataModelElementInterpetationProvider implements
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
		final org.polarsys.capella.core.data.information.datatype.StringType stringElement = (org.polarsys.capella.core.data.information.datatype.StringType) interpretedElement;
		final StringDataType dataType = new StringDataType(currentDataPackage,
				extractSuperclass(dataModel, issues, stringElement), interpretedElement.getName(),
				interpretedElement.getId());
		currentDataPackage.addTypeDefinition(dataType);
		final NumericValue minLength = stringElement.getOwnedMinLength();
		dataType.minLength = getUsedNumericValue(dataModel, minLength, issues);

		final NumericValue maxLength = stringElement.getOwnedMaxLength();
		dataType.maxLength = getUsedNumericValue(dataModel, maxLength, issues);
		if (dataType.maxLength == null || dataType.maxLength instanceof UsedNumericValue.SpecialNumericValue) {
			issues.add(new Issue(Issue.Kind.Info, "String type " + interpretedElement.getName()
					+ " does not contain maximum length specification; this may cause issues with ASN.1 compilation.",
					interpretedElement));
		}

		dataType.description = extractComment(stringElement);
		return dataType;
	}

}
