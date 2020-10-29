// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.architecture.aadl;

import java.util.LinkedList;
import java.util.List;

/**
 * Class representing an abstract AADL feature.
 *
 */
public abstract class AadlFeature extends AadlChildElement implements AadlContainterInterface {

	/**
	 * List of properties.
	 */
	public final List<AadlProperty> properties = new LinkedList<AadlProperty>();

	/**
	 * A constructor.
	 * 
	 * @param parent
	 *            Parent
	 * @param elementName
	 *            Name
	 */
	public AadlFeature(final AadlElement parent, final String elementName) {
		super(parent, elementName);
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public List<AadlElement> getAllContainedElements() {
		final List<AadlElement> result = new LinkedList<AadlElement>();
		result.addAll(properties);
		return result;
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public String getTypeName() {
		return "FEATURE";
	}

}
