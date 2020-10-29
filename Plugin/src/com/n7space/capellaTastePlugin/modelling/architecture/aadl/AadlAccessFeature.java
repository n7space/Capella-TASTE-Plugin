// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.architecture.aadl;

import java.util.List;

/**
 * Class representing AADL access feature.
 *
 */
public class AadlAccessFeature extends AadlFeature implements AadlContainterInterface {

	/**
	 * Enumeration listing possible access types.
	 *
	 */
	public static enum AccessType {
		/**
		 * Provides access.
		 */
		PROVIDES("PROVIDES"),
		/**
		 * Requires access.
		 */
		REQUIRES("REQUIRES"),
		/**
		 * Access refined to provides.
		 */
		REFINED_TO_PROVIDES("REFINED TO PROVIDES"),
		/**
		 * Access refined to requires.
		 */
		REFINED_TO_REQUIRES("REFINED TO REQUIRES");

		/**
		 * The used AADL keyword.
		 */
		final String aadlKeyword;

		private AccessType(final String definedAadlKeyword) {
			aadlKeyword = definedAadlKeyword;
		}

	}

	/**
	 * Access type.
	 */
	public final AccessType accessType;
	/**
	 * Target element.
	 */
	public final AadlPackageElement targetElement;
	/**
	 * Access point.
	 */
	public final String accessPoint;

	/**
	 * A constructor.
	 * 
	 * @param elementName
	 *            Name
	 * @param parent
	 *            Parent
	 * @param elementAccessType
	 *            Access type
	 * @param accessedElement
	 *            Target element
	 * @param accessedAccessPoint
	 *            Access point (may be empty)
	 */
	public AadlAccessFeature(final String elementName, final AadlElement parent, final AccessType elementAccessType,
			final AadlPackageElement accessedElement, final String accessedAccessPoint) {
		super(parent, elementName);
		targetElement = accessedElement;
		accessType = elementAccessType;
		accessPoint = accessedAccessPoint;
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public List<AadlElement> getAllContainedElements() {
		final List<AadlElement> result = super.getAllContainedElements();
		result.add(targetElement);
		return result;
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public String serializeToString(final UsageContext context, final String linePrefix) {
		final StringBuilder sb = new StringBuilder();
		sb.append(linePrefix + name + " : " + accessType.aadlKeyword + " " + targetElement.getTypeName() + " ACCESS "
				+ targetElement.pkg.name + "::" + targetElement.name
				+ (accessPoint.length() > 0 ? ("." + accessPoint) : ""));
		if (properties.size() == 0) {
			sb.append(";\n");
		} else {
			sb.append(" {\n");
			for (final AadlProperty property : properties) {
				sb.append(property.serializeToString(new UsageContext(context, this), linePrefix + getIndent()));
			}
			sb.append(linePrefix + "};\n");
		}
		return sb.toString();
	}

}
