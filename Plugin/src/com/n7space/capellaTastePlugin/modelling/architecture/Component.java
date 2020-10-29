// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.architecture;

import java.util.LinkedList;
import java.util.List;

/**
 * Class representing an abstract component.
 *
 */
public class Component extends ArchitectureElement {

	/**
	 * Class representing a processor.
	 *
	 */
	public static class Processor {
		/**
		 * Processor implementation class.
		 */
		public String typeClass = "";
	}

	/**
	 * List of ports.
	 */
	public final List<ComponentPort> ports = new LinkedList<ComponentPort>();
	/**
	 * List of allocated functions.
	 */
	public final List<Function> allocatedFunctions = new LinkedList<Function>();
	/**
	 * List representing owned components.
	 */
	public final List<Component> ownedComponents = new LinkedList<Component>();
	/**
	 * List representing deployed components.
	 */
	public final List<Component> deployedComponents = new LinkedList<Component>();
	/**
	 * List representing a processor.
	 */
	public final Processor processor = new Processor();
	/**
	 * Whether the component is a node.
	 */
	public final boolean isNode;

	/**
	 * A constructor.
	 *
	 * @param parent
	 *            Parent
	 * @param name
	 *            Name
	 * @param id
	 *            ID
	 * @param isNodeComponent
	 *            Is a node
	 */
	public Component(final ArchitectureElement parent, final String name, final String id,
			final boolean isNodeComponent) {
		super(parent, name, id);
		isNode = isNodeComponent;
	}

	/**
	 * Get the topmost component that this component resides in. May return itself.
	 *
	 * @return The topmost component that this component resides in
	 */
	public Component getTopmostComponent() {
		Component parent = this;
		while (parent != null) {
			if (parent.getFirstParent() == null || !(parent.getFirstParent() instanceof Component))
				return parent;
			parent = (Component) parent.getFirstParent();
		}
		return null;
	}

	/**
	 * Removes a port relevant to the given exchange.
	 * 
	 * @param exchange
	 *            Exchange defining the port to be removed
	 * @return Whether the port was successfully removed
	 */
	public boolean removePortForExchange(final ComponentExchange exchange) {
		ComponentPort portToRemove = null;
		for (final ComponentPort port : ports) {
			if (port.exchange.id.equals(exchange.id)) {
				portToRemove = port;
				break;
			}
		}
		if (portToRemove == null)
			return false;
		ports.remove(portToRemove);
		return true;
	}

}
