// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.architecture.aadl;

import java.util.LinkedList;
import java.util.List;

/**
 * Class representing AADL subcomponent
 *
 */
public class AadlSubcomponent extends AadlChildElement implements AadlContainterInterface {

	/**
	 * AADL element that is treated as the subcomponent
	 */
	public final AadlPackageElement subcomponent;
	/**
	 * List of properties.
	 */
	public final List<AadlProperty> properties = new LinkedList<AadlProperty>();

	/**
	 * Attachment point.
	 */
	public final String attachmentPoint;

	/**
	 * A constructor.
	 * 
	 * @param parent
	 *            Parent
	 * @param element
	 *            Element treated as the subcomponent
	 */
	public AadlSubcomponent(final AadlElement parent, final AadlPackageElement element) {
		super(parent, element.name);
		subcomponent = element;
		attachmentPoint = "others";
	}

	/**
	 * A constructor.
	 * 
	 * @param parent
	 *            Parent
	 * @param element
	 *            Element treated as the subcomponent
	 * @param property
	 *            Additional subcomponent property
	 */
	public AadlSubcomponent(final AadlElement parent, final AadlPackageElement element, final AadlProperty property) {
		super(parent, element.name);
		attachmentPoint = "others";
		subcomponent = element;
		properties.add(property);
	}

	/**
	 * A constructor.
	 * 
	 * @param parent
	 *            Parent
	 * @param customName
	 *            Custom name for the subcomponent
	 * @param element
	 *            Element treated as the subcomponent
	 */
	public AadlSubcomponent(final AadlElement parent, final String customName, final AadlPackageElement element) {
		super(parent, customName);
		subcomponent = element;
		attachmentPoint = "others";
	}

	/**
	 * A constructor.
	 * 
	 * @param parent
	 *            Parent
	 * @param customName
	 *            Custom name for the subcomponent
	 * @param element
	 *            Element treated as the subcomponent
	 * @param customAttachmentPoint
	 *            Custom attachment point
	 */
	public AadlSubcomponent(final AadlElement parent, final String customName, final AadlPackageElement element,
			final String customAttachmentPoint) {
		super(parent, customName);
		subcomponent = element;
		attachmentPoint = customAttachmentPoint;
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public List<AadlElement> getAllContainedElements() {
		final List<AadlElement> result = new LinkedList<AadlElement>();
		result.add(subcomponent);
		result.addAll(properties);
		return result;
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public String getTypeName() {
		return "SUBCOMPONENT";
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public String serializeToString(final UsageContext context, final String linePrefix) {
		final StringBuilder sb = new StringBuilder();
		sb.append(linePrefix + name + " : " + subcomponent.getTypeName() + " "
				+ getQualifiedNameOfAnExternalAadlElement(subcomponent)
				+ (attachmentPoint.length() > 0 ? "." + attachmentPoint : ""));

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