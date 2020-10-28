// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.architecture;

/**
 * Class representing an abstract function port.
 *
 */
public class FunctionPort extends ArchitectureElement {

	/**
	 * Direction.
	 */
	public final Direction direction;
	/**
	 * Exchange attached to this port.
	 */
	public final FunctionExchange exchange;

	/**
	 * A constructor.
	 * 
	 * @param parent
	 *            Parent
	 * @param name
	 *            Name
	 * @param id
	 *            ID
	 * @param portDirection
	 *            Direction
	 * @param portExchange
	 *            Attached exchange
	 */
	public FunctionPort(final ArchitectureElement parent, final String name, final String id,
			final Direction portDirection, final FunctionExchange portExchange) {
		super(parent, name, id);
		direction = portDirection;
		exchange = portExchange;
	}

}