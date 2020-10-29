// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.architecture.aadl;

import java.util.LinkedList;
import java.util.List;

/**
 * Class representing AADL subprogram.
 *
 */
public class AadlSubprogram extends AadlPackageElement implements AadlContainterInterface {

	/**
	 * List of properties.
	 */
	public final List<AadlProperty> properties = new LinkedList<AadlProperty>();
	/**
	 * List of implementation properties.
	 */
	public final List<AadlProperty> implementationProperties = new LinkedList<AadlProperty>();
	/**
	 * List of features.
	 */
	public final List<AadlFeature> features = new LinkedList<AadlFeature>();

	/**
	 * A constructor.
	 * 
	 * @param pkg
	 *            Containing package
	 * @param subprogramName
	 *            Name
	 */
	public AadlSubprogram(final AadlPackage pkg, final String subprogramName) {
		super(pkg, subprogramName);
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public List<AadlElement> getAllContainedElements() {
		final List<AadlElement> result = new LinkedList<AadlElement>();
		result.addAll(properties);
		result.addAll(implementationProperties);
		result.addAll(features);
		return result;
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public String getTypeName() {
		return "SUBPROGRAM";
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public String serializeToString(final UsageContext context, final String linePrefix) {
		final StringBuilder sb = new StringBuilder();

		sb.append(linePrefix + "SUBPROGRAM " + name + "\n");
		if (features.size() > 0) {
			sb.append(linePrefix + "FEATURES\n");
			for (final AadlFeature feature : features) {
				sb.append(feature.serializeToString(new UsageContext(context, this), linePrefix + getIndent()));
			}
		}
		if (properties.size() > 0) {
			sb.append(linePrefix + "PROPERTIES\n");
			for (final AadlProperty property : properties) {
				sb.append(property.serializeToString(new UsageContext(context, this), linePrefix + getIndent()));
			}
		}

		sb.append(linePrefix + "END " + name + ";\n\n");

		sb.append(linePrefix + "SUBPROGRAM IMPLEMENTATION " + name + ".others" + "\n");
		if (implementationProperties.size() > 0) {
			sb.append(linePrefix + "PROPERTIES\n");
			for (final AadlProperty property : implementationProperties) {
				sb.append(property.serializeToString(new UsageContext(context, this), linePrefix + getIndent()));
			}
		}

		sb.append(linePrefix + "END " + name + ".others;\n\n");

		return sb.toString();
	}

}
