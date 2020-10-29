// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.architecture.aadl;

/**
 * Class representing AADL processor
 *
 */
public class AadlProcessor extends AadlPackageElement {

	/**
	 * A constructor.
	 * 
	 * @param containingPackage
	 *            Containing package
	 * @param elementName
	 *            Name
	 */
	public AadlProcessor(final AadlPackage containingPackage, final String elementName) {
		super(containingPackage, elementName);
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public String getTypeName() {
		return "PROCESSOR";
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public String serializeToString(final UsageContext context, final String linePrefix) {
		final StringBuilder sb = new StringBuilder();
		sb.append(linePrefix + "PROCESSOR " + name + "\n");
		sb.append(linePrefix + "END " + name + ";\n");
		return sb.toString();
	}

}
