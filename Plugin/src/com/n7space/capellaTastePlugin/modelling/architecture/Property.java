// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.architecture;

/**
 * Class representing a property.
 *
 */
public class Property {
	/**
	 * ID.
	 */
	public final String id;
	/**
	 * Name.
	 */
	public final String name;
	/**
	 * Type. Used for grouping.
	 */
	public final String type;
	/**
	 * Value.
	 */
	public final String value;

	/**
	 * A constructor.
	 * 
	 * @param propertyType
	 *            Type.
	 * @param propertyName
	 *            Name.
	 * @param propertyId
	 *            ID.
	 * @param propertyValue
	 *            Value.
	 */
	public Property(final String propertyType, final String propertyName, final String propertyId,
			final String propertyValue) {
		id = propertyId;
		type = propertyType;
		name = propertyName;
		value = propertyValue;
	}
}
