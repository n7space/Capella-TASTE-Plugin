// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.architecture;

import java.util.LinkedList;
import java.util.List;

/**
 * Class representing an abstract function.
 *
 */
public class Function extends ArchitectureElement {

	/**
	 * List of function ports.
	 */
	public final List<FunctionPort> ports = new LinkedList<FunctionPort>();
	/**
	 * List of SDL timer names.
	 */
	public final List<String> timerNames = new LinkedList<String>();
	/**
	 * List of TASTE directives.
	 */
	public final List<String> directives = new LinkedList<String>();
	/**
	 * Implementation language.
	 */
	public String language = "";

	/**
	 * A constructor.
	 * 
	 * @param parent
	 *            Parent component
	 * @param name
	 *            Name
	 * @param id
	 *            ID
	 */
	public Function(final Component parent, final String name, final String id) {
		super(parent, name, id);
	}

}
