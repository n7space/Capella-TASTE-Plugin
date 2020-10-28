// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.data;

import java.util.Set;

/**
 * Interface to be implemented by model items which are dependent on other model
 * items (e.g. collection that is dependent on the contained type).
 */
public interface DependentItem {

	/**
	 * Provides set of data model dependencies.
	 * 
	 * @param model
	 *            Data model
	 * @return Set of dependencies
	 */
	Set<DataModelElement> getDependencies(final DataModel model);

}
