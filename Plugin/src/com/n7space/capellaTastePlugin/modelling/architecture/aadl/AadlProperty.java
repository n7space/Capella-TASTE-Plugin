// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.architecture.aadl;

/**
 * Class representing AADL property.
 *
 */
public class AadlProperty extends AadlChildElement {

	/**
	 * Value.
	 */
	public final String value;

	/**
	 * A constructor.
	 * 
	 * @param parent
	 *            Parent
	 * @param propertyName
	 *            Name
	 * @param propertyValue
	 *            Value
	 */
	public AadlProperty(final AadlElement parent, final String propertyName, final String propertyValue) {
		super(parent, propertyName);
		value = propertyValue;
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public String getTypeName() {
		return "PROPERTY";
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public String serializeToString(final UsageContext context, final String linePrefix) {
		return linePrefix + name + " => " + value + ";\n";
	}

}
