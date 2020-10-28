// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.data;

import java.util.HashSet;
import java.util.Set;

/**
 * Provides an internal representation of a string data type.
 */
public class StringDataType extends DataType implements DependentItem {

	/**
	 * The minimum length.
	 */
	public UsedNumericValue minLength = null;
	/**
	 * The maximum length.
	 */
	public UsedNumericValue maxLength = null;

	/**
	 * The constructor.
	 * 
	 * @param parentPackage
	 *            Parent data package
	 * @param parentType
	 *            Parent data type
	 * @param name
	 *            Type name
	 * @param id
	 *            Type ID
	 */
	public StringDataType(final DataPackage parentPackage, final DataTypeReference parentType, final String name,
			final String id) {
		super(parentPackage, parentType, name, id);
	}

	/**
	 * The constructor
	 * 
	 * @param parentPackage
	 *            Parent data package
	 * @param name
	 *            Type name
	 * @param id
	 *            Type ID
	 */
	public StringDataType(final DataPackage parentPackage, final String name, final String id) {
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
			final DataType type = model.findDataTypeById(parent.id);
			if (type != null) {
				dependencies.add(type);
			}
		}

		if (minLength != null) {
			dependencies.addAll(minLength.getDependencies(model));
		}
		if (maxLength != null) {
			dependencies.addAll(maxLength.getDependencies(model));
		}
		return dependencies;
	}

}
