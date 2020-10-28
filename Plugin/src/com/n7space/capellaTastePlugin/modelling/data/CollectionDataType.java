// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.data;

import java.util.HashSet;
import java.util.Set;

/**
 * Class providing the internal representation of a collection data type.
 */
public class CollectionDataType extends DataType implements DependentItem {
	/**
	 * Enumeration listing possible orderings.
	 */
	public static enum Ordering {
		/**
		 * The collection is ordered (e.g. a sequence or an array).
		 */
		Ordered,
		/**
		 * The collection is unordered (e.g. a set).
		 */
		Unordered
	}

	/**
	 * Collection ordering.
	 */
	public final Ordering ordering;
	/**
	 * The contained data type.
	 */
	public final DataTypeReference dataType;
	/**
	 * The minimum cardinality of the collection.
	 */
	public final UsedNumericValue minimumCardinality;
	/**
	 * The maximum cardinality of the collection.
	 */
	public final UsedNumericValue maximumCardinality;

	/**
	 * The constructor.
	 * 
	 * @param parentPackage
	 *            Parent package
	 * @param name
	 *            Type name
	 * @param id
	 *            Type ID
	 * @param collectionOrdering
	 *            Ordering
	 * @param containedType
	 *            The contained type
	 * @param minimumCollectionCardinality
	 *            Minimum cardinality
	 * @param maximumCollectionCardinality
	 *            Maximum cardinality
	 */
	public CollectionDataType(final DataPackage parentPackage, final String name, final String id,
			final Ordering collectionOrdering, final DataTypeReference containedType,
			final UsedNumericValue minimumCollectionCardinality, final UsedNumericValue maximumCollectionCardinality) {
		super(parentPackage, name, id);
		ordering = collectionOrdering;
		minimumCardinality = minimumCollectionCardinality;
		maximumCardinality = maximumCollectionCardinality;
		dataType = containedType;
	}

	/**
	 * Returns a set of all dependencies.
	 * 
	 * @param model
	 *            Data model
	 * @return Set of dependencies
	 */
	@Override
	public Set<DataModelElement> getDependencies(final DataModel model) {
		final Set<DataModelElement> dependencies = new HashSet<DataModelElement>();
		if (dataType != null) {
			final DataType type = model.findDataTypeById(dataType.id);
			if (type != null) {
				dependencies.add(type);
			}
		}
		if (minimumCardinality != null) {
			dependencies.addAll(minimumCardinality.getDependencies(model));
		}
		if (maximumCardinality != null) {
			dependencies.addAll(maximumCardinality.getDependencies(model));
		}
		return dependencies;
	}

}
