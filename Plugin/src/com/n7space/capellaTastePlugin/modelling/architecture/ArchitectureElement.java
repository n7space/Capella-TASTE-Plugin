// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.architecture;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

/**
 * Base class for architecture elements.
 *
 */
public abstract class ArchitectureElement {

	/**
	 * Parent elements.
	 */
	@XmlTransient
	public final List<ArchitectureElement> parents;
	/**
	 * Name.
	 */
	public String name;
	/**
	 * ID.
	 */
	public final String id;
	/**
	 * Properties.
	 */
	public final PropertyGroup properties = new PropertyGroup();

	/**
	 * Returns the first parent.
	 *
	 * @return First parent or null if there none
	 */
	public ArchitectureElement getFirstParent() {
		if (parents.size() == 0)
			return null;
		return parents.get(0);
	}

	/**
	 * Sets the only parent.
	 *
	 * @param parent
	 *            Parent element
	 */
	public void setTheOnlyParent(final ArchitectureElement parent) {
		parents.clear();
		parents.add(parent);
	}

	/**
	 * A constructor.
	 *
	 * @param parent1
	 *            First parent
	 * @param parent2
	 *            Second parent
	 * @param elementName
	 *            Element name
	 * @param elementId
	 *            Element ID
	 */
	public ArchitectureElement(final ArchitectureElement parent1, final ArchitectureElement parent2,
			final String elementName, final String elementId) {
		name = elementName;
		id = elementId;
		parents = new LinkedList<ArchitectureElement>();
		parents.add(parent1);
		parents.add(parent2);
	}

	/**
	 * A constructor.
	 *
	 * @param elementParent
	 *            Element parent
	 * @param elementName
	 *            Element name
	 * @param elementId
	 *            Element ID
	 */
	public ArchitectureElement(final ArchitectureElement elementParent, final String elementName,
			final String elementId) {
		name = elementName;
		id = elementId;
		parents = new LinkedList<ArchitectureElement>();
		parents.add(elementParent);
	}

}
