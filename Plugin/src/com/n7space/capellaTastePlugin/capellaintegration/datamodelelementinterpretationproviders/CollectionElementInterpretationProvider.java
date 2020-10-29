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

import com.n7space.capellatasteplugin.modelling.data.CollectionDataType;
import com.n7space.capellatasteplugin.modelling.data.CollectionDataType.Ordering;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataModelElement;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.modelling.data.DataTypeReference;
import com.n7space.capellatasteplugin.modelling.data.UsedNumericValue;
import com.n7space.capellatasteplugin.utils.Issue;
import com.n7space.capellatasteplugin.utils.Issue.Kind;

/**
 * Interpretation provider for Capella collection element.
 */
public class CollectionElementInterpretationProvider extends BaseDataModelElementInterpetationProvider implements
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
		final org.polarsys.capella.core.data.information.Collection collection = (org.polarsys.capella.core.data.information.Collection) interpretedElement;
		final Type innerType = collection.getType();
		if (innerType == null) {

			issues.add(new Issue(Kind.Warning,
					"Collection type " + collection.getName() + " does not have an element type.", collection));
			return null;
		}
		final DataTypeReference memberType = getDataTypeReference(dataModel, innerType, issues);
		final UsedNumericValue minimumCardinality = getUsedNumericValue(dataModel, collection.getOwnedMinCard(),
				issues);
		final UsedNumericValue maximumCardinality = getUsedNumericValue(dataModel, collection.getOwnedMaxCard(),
				issues);
		final Ordering ordering = collection.isOrdered() ? Ordering.Ordered : Ordering.Unordered;
		final CollectionDataType dataType = new CollectionDataType(currentDataPackage, collection.getName(),
				collection.getId(), ordering, memberType, minimumCardinality, maximumCardinality);
		dataType.description = extractComment(collection);
		currentDataPackage.addTypeDefinition(dataType);
		return dataType;
	}

}
