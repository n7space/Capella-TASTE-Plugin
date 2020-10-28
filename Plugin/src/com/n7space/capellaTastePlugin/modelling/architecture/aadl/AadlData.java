// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.architecture.aadl;

/**
 * Class represeting AADL data.
 *
 */
public class AadlData extends AadlPackageElement {

	/**
	 * A constructor.
	 * 
	 * @param containingPackage
	 *            Containing package
	 * @param elementName
	 *            Name
	 */
	public AadlData(final AadlPackage containingPackage, final String elementName) {
		super(containingPackage, elementName);
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public String getTypeName() {
		return "DATA";
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public String serializeToString(final UsageContext context, final String linePrefix) {
		final StringBuilder sb = new StringBuilder();
		sb.append(linePrefix + "DATA " + name + "\n");
		sb.append(linePrefix + "END " + name + ";\n");
		return sb.toString();
	}

}
