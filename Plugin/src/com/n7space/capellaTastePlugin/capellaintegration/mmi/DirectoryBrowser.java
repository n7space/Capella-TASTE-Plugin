// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.mmi;

/**
 * Interface declaration for a directory browser.
 */
public interface DirectoryBrowser {

	/**
	 * Method which should ask the user - using the provided message - for a
	 * directory path.
	 * 
	 * @param message
	 *            Message to be shown to the user
	 * @return Directory path selected by the user
	 */
	String getOutputDirectory(final String message);

}