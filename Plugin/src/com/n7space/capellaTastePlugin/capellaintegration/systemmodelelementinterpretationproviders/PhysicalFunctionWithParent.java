// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders;

import org.polarsys.capella.core.data.pa.PhysicalFunction;

import com.n7space.capellatasteplugin.modelling.architecture.Component;

/**
 * A pair of a Physical Function and its parent.
 *
 */
public class PhysicalFunctionWithParent {

	/**
	 * Physical Function's parent.
	 */
	public final Component parent;
	/**
	 * Physical Function.
	 */
	public final PhysicalFunction function;

	/**
	 * The constructor.
	 *
	 * @param functionParent
	 *            Function's parent
	 * @param childFunction
	 *            Function
	 */
	public PhysicalFunctionWithParent(final Component functionParent, final PhysicalFunction childFunction) {
		parent = functionParent;
		function = childFunction;
	}

}
