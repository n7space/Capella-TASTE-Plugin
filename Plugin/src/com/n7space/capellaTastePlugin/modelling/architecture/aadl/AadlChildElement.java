// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.architecture.aadl;

/**
 * An abstract class representing AADL element that has a parent.
 *
 */
public abstract class AadlChildElement extends AadlElement {

	/**
	 * Element's parent.
	 */
	public final AadlElement parent;

	/**
	 * A constructor.
	 *
	 * @param elementParent
	 *            Parent
	 * @param elementName
	 *            Name
	 */
	public AadlChildElement(final AadlElement elementParent, final String elementName) {
		super(elementName);
		parent = elementParent;
	}

	/**
	 * Gets the element's qualified name within the given context.
	 *
	 * @param context
	 *            The context of the name
	 * @return Name qualified within the given context
	 */
	public String getQualifiedName(final UsageContext context) {
		final AadlElement immediateContext = context.get(AadlElement.class);
		if (parent == null || parent.equals(immediateContext)) {
			return name;
		}
		return parent.name + "." + name;
	}

	/**
	 * Gets the name of an external AADL element, qualified within the internal
	 * context.
	 *
	 * @param element
	 *            External AADL element
	 * @return Name qualified within the internal context
	 */
	public String getQualifiedNameOfAnExternalAadlElement(final AadlPackageElement element) {
		AadlPackage containingPackage = null;
		if (parent instanceof AadlPackageElement) {
			containingPackage = ((AadlPackageElement) parent).pkg;
		}
		if (containingPackage != null && containingPackage.name.equals(element.pkg.name)) {
			return element.name;
		}
		return element.pkg.name + "::" + element.name;
	}

}
