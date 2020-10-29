// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.data;

/**
 * Class containing a reference to an internal data type.
 */
public class DataTypeReference {
	/**
	 * ID of the referred to type.
	 */
	public final String id;
	/**
	 * Name of the referred to type. Must be mutable due to post-processing
	 * algorithms.
	 */
	public String name;
	/**
	 * Data package containing the referred to type.
	 */
	public final DataPackage dataPackage;

	/**
	 * The constructor.
	 *
	 * @param referenceDataPackage
	 *            The package containing the referred to type
	 * @param referenceName
	 *            The name of the referred to type
	 * @param referenceId
	 *            The ID of the referred to type
	 */
	public DataTypeReference(final DataPackage referenceDataPackage, final String referenceName,
			final String referenceId) {
		id = referenceId;
		name = referenceName;
		dataPackage = referenceDataPackage;
	}

	/**
	 * The constructor
	 *
	 * @param type
	 *            The type to refer to.
	 */
	public DataTypeReference(final DataType type) {
		dataPackage = type.dataPackage;
		name = type.name;
		id = type.id;
	}

	/**
	 * Returns whether the reference refers to the given type.
	 *
	 * @param type
	 *            The given type
	 * @return Whether the given type is referred to by the given reference
	 */
	public boolean refersTo(final DataType type) {
		return type.id.equals(id);
	}
}
