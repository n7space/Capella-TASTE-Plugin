// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.architecture.aadl;

/**
 * Class representing AADL process.
 *
 */
public class AadlProcess extends AadlPackageElement {

	/**
	 * A constructor.
	 * 
	 * @param containingPackage
	 *            Containing package
	 * @param elementName
	 *            Name
	 */
	public AadlProcess(final AadlPackage containingPackage, final String elementName) {
		super(containingPackage, elementName);

	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public String getTypeName() {
		return "PROCESS";
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public String serializeToString(final UsageContext context, final String linePrefix) {
		final StringBuilder sb = new StringBuilder();

		sb.append(linePrefix + "PROCESS " + name + "\n");
		sb.append(linePrefix + "END " + name + ";\n\n");

		sb.append(linePrefix + "PROCESS IMPLEMENTATION " + name + ".others" + "\n");
		sb.append(linePrefix + "END " + name + ".others;\n\n");

		return sb.toString();
	}

}
