// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.architecture;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

/**
 * Class representing a group of properties.
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class PropertyGroup {

	protected final List<Property> properties = new LinkedList<Property>();

	/**
	 * Adds a property to the group.
	 *
	 * @param property
	 *            Property to be added
	 */
	public void addProperty(final Property property) {
		properties.add(property);
	}

	/**
	 * Gets all properties of the given type.
	 *
	 * @param type
	 *            Type of the properties to be retrieved
	 * @return A list of the retrieved properties
	 */
	public List<Property> getPropertiesOfType(final String type) {
		final List<Property> result = new LinkedList<Property>();
		for (final Property property : properties) {
			if (property.type.equals(type)) {
				result.add(property);
			}
		}
		return result;
	}

	/**
	 * Gets a property with given ID.
	 *
	 * @param id
	 *            ID of the property to be retrieved
	 * @return The property with the given ID or null if none is found
	 */
	public Property getPropertyById(final String id) {
		for (final Property property : properties) {
			if (property.id.equals(id)) {
				return property;
			}
		}
		return null;
	}

}
