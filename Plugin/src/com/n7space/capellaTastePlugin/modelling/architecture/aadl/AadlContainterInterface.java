// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.architecture.aadl;

import java.util.List;

/**
 * Interface for AADL elements containing other AADL elements.
 * 
 * @author mkuro
 *
 */
public interface AadlContainterInterface {

	/**
	 * Gets all contained AADL elements.
	 * 
	 * @return A List of all contained AADL elements
	 */
	List<AadlElement> getAllContainedElements();

}
