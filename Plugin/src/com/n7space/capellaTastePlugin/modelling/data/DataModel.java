// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.data;

import java.util.LinkedList;
import java.util.List;

import com.n7space.capellatasteplugin.modelling.data.DataType.DataTypeValue;

/**
 * Class containing an internal data model representation.
 */
public class DataModel {
	/**
	 * Prefix for the IDs of the built-in types.
	 */
	public static final String BUILT_IN_TYPE_ID_PREFIX = "builtInId-";
	/**
	 * Built-in integer.
	 */
	public static final String BUILT_IN_INTEGER_TYPE_ID = BUILT_IN_TYPE_ID_PREFIX + "integer";

	/**
	 * List of contained data packages.
	 */
	public final List<DataPackage> dataPackages = new LinkedList<DataPackage>();

	/**
	 * List of built-in types.
	 */
	public final List<DataType> builtInTypes = new LinkedList<DataType>();

	protected DataPackage nullPackage = new DataPackage("", "");

	/**
	 * The constructor.
	 */
	public DataModel() {
		builtInTypes.add(new IntegerDataType(nullPackage, "INTEGER", BUILT_IN_INTEGER_TYPE_ID));
	}

	/**
	 * Returns the data package identified by the given ID.
	 * 
	 * @param id
	 *            The given ID
	 * @return The data package or null if none is found
	 */
	public DataPackage findDataPackageById(final String id) {
		for (final DataPackage pkg : dataPackages) {
			if (pkg.id.equals(id))
				return pkg;
		}
		return null;
	}

	/**
	 * Returns the data type identified by the given ID.
	 * 
	 * @param id
	 *            The given ID
	 * @return The data type or null if none is found
	 */
	public DataType findDataTypeById(final String id) {
		for (final DataPackage pkg : dataPackages) {
			for (final DataType type : pkg.definedDataTypes) {
				if (type.id.equals(id)) {
					return type;
				}
			}
			for (final DataType type : pkg.declaredDataTypes) {
				if (type.id.equals(id)) {
					return type;
				}
			}
		}
		for (final DataType type : builtInTypes) {
			if (type.id.equals(id)) {
				return type;
			}
		}
		return null;
	}

	/**
	 * Returns the data value identified by the given ID
	 * 
	 * @param id
	 *            The given ID
	 * @return The data value or null if none is found
	 */
	public DataTypeValue findDataValueById(final String id) {
		for (final DataPackage pkg : dataPackages) {
			for (final DataTypeValue value : pkg.definedValues) {
				if (value.id.equals(id)) {
					return value;
				}
			}
		}
		return null;
	}
}
