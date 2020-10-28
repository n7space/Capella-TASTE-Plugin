// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.architecture;

/**
 * Class representing an abstract component port.
 *
 */
public class ComponentPort extends ArchitectureElement {

	/**
	 * Component exchange that this port handles.
	 */
	public final ComponentExchange exchange;
	/**
	 * Port's device high-level class.
	 */
	public String typeClass = "";
	/**
	 * Port's device implementation class.
	 */
	public String implementationTypeClass = "";
	/**
	 * Port's device configuration schema.
	 */
	public String configurationSchema = "\"\"";
	/**
	 * Port's device configuration.
	 */
	public String configuration = "{}";
	/**
	 * Port's device version.
	 */
	public String version = "";

	/**
	 * A constructor.
	 *
	 * @param parent
	 *            Parent component
	 * @param name
	 *            Name
	 * @param id
	 *            ID
	 * @param portExchange
	 *            Exchange handled by this port
	 */
	public ComponentPort(final Component parent, final String name, final String id,
			final ComponentExchange portExchange) {
		super(parent, name, id);
		exchange = portExchange;
	}

}
