// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.architecture;

import java.util.LinkedList;
import java.util.List;

/**
 * Class representing an abstract component exchange.
 *
 */
public class ComponentExchange extends ArchitectureElement {

	/**
	 * Implementation class.
	 */
	public String typeClass = "";

	/**
	 * Allocated functional exchanges.
	 */
	public final List<FunctionExchange> allocatedFunctionExchanges = new LinkedList<FunctionExchange>();

	/**
	 * A constructor.
	 *
	 * @param parent1
	 *            First parent
	 * @param parent2
	 *            Second parent
	 * @param elementName
	 *            Name
	 * @param elementId
	 *            ID
	 */
	public ComponentExchange(final Component parent1, final Component parent2, final String elementName,
			final String elementId) {
		super(parent1, parent2, elementName, elementId);
	}

}
