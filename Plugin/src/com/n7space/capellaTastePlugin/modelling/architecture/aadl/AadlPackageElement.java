// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.architecture.aadl;

/**
 * Class representing an abstract AADL package element.
 *
 */
public abstract class AadlPackageElement extends AadlElement {

	/**
	 * Containing package.
	 */
	public final AadlPackage pkg;

	/**
	 * A constructor.
	 * 
	 * @param containingPackage
	 *            Containing package
	 * @param elementName
	 *            Name
	 */
	public AadlPackageElement(final AadlPackage containingPackage, final String elementName) {
		super(elementName);
		pkg = containingPackage;
	}

	/**
	 * Gets name of an external AADL element, qualified within the context of this
	 * element.
	 * 
	 * @param element
	 *            External AADL element
	 * @return Qualified name of the external element
	 */
	public String getQualifiedNameOfAnExternalAadlElement(final AadlPackageElement element) {
		if (pkg != null && pkg.name.equals(element.pkg.name)) {
			return element.name;
		}
		return element.pkg.name + "::" + element.name;
	}

}
