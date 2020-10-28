// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders;

import java.util.LinkedList;
import java.util.List;

import org.polarsys.capella.core.data.capellacore.AbstractPropertyValue;
import org.polarsys.capella.core.data.capellacore.CapellaElement;
import org.polarsys.capella.core.data.capellacore.StringPropertyValue;

/**
 * Class facilitation extraction of Capella properties.
 *
 */
public class PropertyUtils {

	/**
	 * Gets String properties with a given name of a given Capella element.
	 * 
	 * @param element
	 *            Capella element
	 * @param name
	 *            Name of the properties to obtain
	 * @return List of string property values.
	 */
	public static List<String> getProperties(final CapellaElement element, final String name) {
		final List<String> result = new LinkedList<String>();
		for (final AbstractPropertyValue value : element.getAppliedPropertyValues())
			if (value.getName().equals(name) && (value instanceof StringPropertyValue)) {
				final StringPropertyValue stringValue = (StringPropertyValue) value;
				result.add(stringValue.getValue());
			}
		return result;
	}

	/**
	 * Gets String property with a given name of a given Capella element.
	 * 
	 * @param element
	 *            Capella elemeent
	 * @param name
	 *            Name of the property to obtain
	 * @return String property value or null if a) no property is found b) more than
	 *         one property is found (thus making a single property ambiguously
	 *         defined)
	 */
	public static String getProperty(final CapellaElement element, final String name) {
		final List<String> result = getProperties(element, name);
		if (result.size() == 1)
			return result.get(0);
		return null;
	}
}
