// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.architecture.aadl;

import java.util.LinkedList;
import java.util.List;

/**
 * Class representing AADL device.
 *
 */
public class AadlDevice extends AadlPackageElement implements AadlContainterInterface {

	/**
	 * A list of features.
	 */
	public final List<AadlFeature> features = new LinkedList<AadlFeature>();
	/**
	 * A list of properties.
	 */
	public final List<AadlProperty> properties = new LinkedList<AadlProperty>();

	/**
	 * Base device.
	 */
	public final AadlDevice base;
	/**
	 * Base device for the implementation.
	 */
	public final AadlDevice implementationBase;

	/**
	 * A constructor.
	 * 
	 * @param containingPackage
	 *            Containing package
	 * @param baseDevice
	 *            Base device
	 * @param baseImplementationDevice
	 *            Implementation base device
	 * @param elementName
	 *            Name
	 */
	public AadlDevice(final AadlPackage containingPackage, final AadlDevice baseDevice,
			final AadlDevice baseImplementationDevice, final String elementName) {
		super(containingPackage, elementName);
		base = baseDevice;
		implementationBase = baseImplementationDevice;
	}

	/**
	 * A constructor.
	 * 
	 * @param containingPackage
	 *            Containing package
	 * @param elementName
	 *            Name
	 */
	public AadlDevice(final AadlPackage containingPackage, final String elementName) {
		super(containingPackage, elementName);
		base = null;
		implementationBase = null;
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public List<AadlElement> getAllContainedElements() {
		final LinkedList<AadlElement> result = new LinkedList<AadlElement>();
		if (base != null) {
			result.add(base);
		}
		if (implementationBase != null) {
			result.add(implementationBase);
		}
		result.addAll(features);
		return result;
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public String getTypeName() {
		return "DEVICE";
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public String serializeToString(final UsageContext context, final String linePrefix) {
		final StringBuilder sb = new StringBuilder();

		sb.append(linePrefix + "DEVICE " + name);
		if (base != null) {
			sb.append("\nEXTENDS " + getQualifiedNameOfAnExternalAadlElement(base) + "\n");
		} else {
			sb.append("\n");
		}
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

		sb.append(linePrefix + "DEVICE IMPLEMENTATION " + name + ".others");
		if (implementationBase != null) {
			sb.append("\nEXTENDS " + getQualifiedNameOfAnExternalAadlElement(implementationBase) + "\n");
		} else {
			sb.append("\n");
		}
		sb.append(linePrefix + "END " + name + ".others;\n\n");

		return sb.toString();
	}

}
