// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.architecture.aadl;

import java.util.LinkedList;
import java.util.List;

/**
 * Class representing a list of AADL packages.
 *
 */
public class AadlPackages {
	/**
	 * List of packages.
	 */
	public final List<AadlPackage> packages = new LinkedList<AadlPackage>();

	/**
	 * Gets AADL package for the given owner.
	 * 
	 * @param owner
	 *            Owner of the package to be retrieved
	 * @return Retrieved package
	 */
	public AadlPackage getOwnedPackage(final Object owner) {
		for (final AadlPackage pkg : packages) {
			if (owner.equals(pkg.owner)) {
				return pkg;
			}
		}
		return null;
	}

	/**
	 * Gets AADL package for the given name.
	 * 
	 * @param name
	 *            Name of the package to be retrieved
	 * @return Retrieved package
	 */
	public AadlPackage getPackageByName(final String name) {
		for (final AadlPackage pkg : packages) {
			if (pkg.name.equals(name)) {
				return pkg;
			}
		}
		return null;
	}

}
