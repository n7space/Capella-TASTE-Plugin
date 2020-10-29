// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.architecture.aadl;

import java.util.List;

/**
 * Class representing an AADL access connection.
 *
 */
public class AadlAccessConnection extends AadlConnection {

	/**
	 * Enumeration listing possible connection types.
	 *
	 */
	public static enum Type {
		/**
		 * Subprogram connection.
		 */
		SUBPROGRAM,
		/**
		 * Bus connection.
		 */
		BUS
	}

	protected static String constructName(final AadlChildElement sourceElement, final AadlChildElement targetElement) {
		final String targetName = targetElement.name.startsWith(targetElement.parent.name) ? targetElement.name
				: (targetElement.parent.name + "_" + targetElement.name);
		final String sourceName = sourceElement.name.startsWith(sourceElement.parent.name) ? sourceElement.name
				: (sourceElement.parent.name + "_" + sourceElement.name);
		return targetName + "_" + sourceName;
	}

	/**
	 * Source.
	 */
	public final AadlChildElement source;

	/**
	 * Target.
	 */
	public final AadlChildElement target;

	/**
	 * Type.
	 */
	public final Type type;

	/**
	 * A constructor.
	 * 
	 * @param parent
	 *            Parent.
	 * @param connectionType
	 *            Type.
	 * @param sourceElement
	 *            Source.
	 * @param targetElement
	 *            Target.
	 */
	public AadlAccessConnection(final AadlElement parent, final Type connectionType,
			final AadlChildElement sourceElement, final AadlChildElement targetElement) {
		super(parent, constructName(sourceElement, targetElement));
		source = sourceElement;
		target = targetElement;
		type = connectionType;
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public List<AadlElement> getAllContainedElements() {
		final List<AadlElement> result = super.getAllContainedElements();
		result.add(source);
		result.add(target);
		return result;
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public String serializeToString(final UsageContext context, final String linePrefix) {
		final StringBuilder sb = new StringBuilder();
		sb.append(linePrefix + name + " : " + type.name() + " ACCESS " + target.getQualifiedName(context) + " -> "
				+ source.getQualifiedName(context));

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
