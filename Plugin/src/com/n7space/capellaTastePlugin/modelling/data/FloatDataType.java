// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.data;

import java.util.HashSet;
import java.util.Set;

/**
 * Class providing an internal representation of a float data type.
 */
public class FloatDataType extends DataType implements DependentItem {

	/**
	 * The lower bound.
	 */
	public UsedNumericValue lowerBound = null;
	/**
	 * The upper bound.
	 */
	public UsedNumericValue upperBound = null;

	/**
	 * Unit.
	 */
	public Unit unit = null;

	/**
	 * The constructor.
	 *
	 * @param parentPackage
	 *            Parent data package
	 * @param parent
	 *            Parent type
	 * @param name
	 *            Type name
	 * @param id
	 *            Type ID
	 */
	public FloatDataType(final DataPackage parentPackage, final DataTypeReference parent, final String name,
			final String id) {
		super(parentPackage, parent, name, id);
	}

	/**
	 * The constructor.
	 *
	 * @param parentPackage
	 *            Parent data package
	 * @param name
	 *            Type name
	 * @param id
	 *            Type ID
	 */
	public FloatDataType(final DataPackage parentPackage, final String name, final String id) {
		super(parentPackage, name, id);
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
		if (parent != null) {
			dependencies.add(model.findDataTypeById(parent.id));
		}
		return dependencies;
	}

}
