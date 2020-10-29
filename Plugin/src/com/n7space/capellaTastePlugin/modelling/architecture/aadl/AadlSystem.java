// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.architecture.aadl;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Class representing AADL system.
 *
 */
public class AadlSystem extends AadlPackageElement implements AadlContainterInterface {

	/**
	 * List of subcomponents.
	 */
	public final List<AadlSubcomponent> subcomponents = new LinkedList<AadlSubcomponent>();
	/**
	 * List of connections.
	 */
	public final List<AadlConnection> connections = new LinkedList<AadlConnection>();
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
	 * @param systemName
	 *            Name
	 */
	public AadlSystem(final AadlPackage pkg, final String systemName) {
		super(pkg, systemName);
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public List<AadlElement> getAllContainedElements() {
		final List<AadlElement> result = new LinkedList<AadlElement>();
		result.addAll(subcomponents);
		result.addAll(connections);
		result.addAll(properties);
		result.addAll(features);
		return result;
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public String getTypeName() {
		return "SYSTEM";
	}

	protected boolean hasImplementation() {
		return true; // Update to TASTE version 2.3
	}

	protected void serializeDeclaration(final UsageContext context, final String linePrefix, final StringBuilder sb) {
		sb.append(linePrefix + "SYSTEM " + name + "\n");
		if (features.size() > 0) {
			sb.append(linePrefix + "FEATURES\n");
			for (final AadlFeature feature : features) {
				sb.append(feature.serializeToString(new UsageContext(context, this), linePrefix + getIndent()));
			}
		}
		if (properties.size() > 0) {
			sb.append(linePrefix + "PROPERTIES\n");
			for (final AadlProperty property : properties)
				sb.append(property.serializeToString(new UsageContext(context, this), linePrefix + getIndent()));
		}

		sb.append(linePrefix + "END " + name + ";\n\n");
	}

	protected void serializeDefinition(final UsageContext context, final String linePrefix, final StringBuilder sb) {
		if (hasImplementation()) {
			sb.append(linePrefix + "SYSTEM IMPLEMENTATION " + name + ".others" + "\n");
			if (subcomponents.size() != 0) {
				sb.append(linePrefix + "SUBCOMPONENTS\n");
				for (final AadlSubcomponent component : subcomponents) {
					sb.append(component.serializeToString(new UsageContext(context, this), linePrefix + getIndent()));
				}
			}
			if (connections.size() != 0) {
				sb.append(linePrefix + "CONNECTIONS\n");
				for (final AadlConnection connection : connections) {
					sb.append(connection.serializeToString(new UsageContext(context, this), linePrefix + getIndent()));
				}
			}
			if (implementationProperties.size() > 0) {
				sb.append(linePrefix + "PROPERTIES\n");
				for (final AadlProperty property : implementationProperties) {
					sb.append(property.serializeToString(new UsageContext(context, this), linePrefix + getIndent()));
				}
			}

			sb.append(linePrefix + "END " + name + ".others;\n\n");
		}
	}

	protected void sort() {
		connections.sort(new Comparator<AadlConnection>() {

			@Override
			public int compare(AadlConnection o1, AadlConnection o2) {
				return o1.name.compareTo(o2.name);
			}
		});
		properties.sort(new Comparator<AadlProperty>() {

			@Override
			public int compare(AadlProperty o1, AadlProperty o2) {
				return (o1.name + o1.value).compareTo(o2.name + o2.value);
			}
		});
		features.sort(new Comparator<AadlFeature>() {

			@Override
			public int compare(AadlFeature o1, AadlFeature o2) {
				return o1.name.compareTo(o2.name);
			}
		});
		implementationProperties.sort(new Comparator<AadlProperty>() {

			@Override
			public int compare(AadlProperty o1, AadlProperty o2) {
				return (o1.name + o1.value).compareTo(o2.name + o2.value);
			}
		});
		subcomponents.sort(new Comparator<AadlSubcomponent>() {

			@Override
			public int compare(AadlSubcomponent o1, AadlSubcomponent o2) {
				return o1.name.compareTo(o2.name);
			}
		});
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public String serializeToString(final UsageContext context, final String linePrefix) {
		final StringBuilder sb = new StringBuilder();
		sort();
		serializeDeclaration(context, linePrefix, sb);
		serializeDefinition(context, linePrefix, sb);

		return sb.toString();
	}

}
