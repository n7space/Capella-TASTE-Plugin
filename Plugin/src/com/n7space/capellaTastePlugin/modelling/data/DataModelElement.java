// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.data;

/**
 * Base class for all data model elements.
 */
public class DataModelElement {
	/**
	 * Name. Must be mutable due to post-processing algorithms.
	 */
	public String name;
	/**
	 * ID.
	 */
	public final String id;

	/**
	 * The constructor.
	 *
	 * @param elementName
	 *            Name of the element
	 * @param elementId
	 *            ID of the element
	 */
	public DataModelElement(final String elementName, final String elementId) {
		name = elementName;
		id = elementId;
	}
}
