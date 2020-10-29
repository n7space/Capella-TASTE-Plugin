// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.architecture.aadl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Class representing AADL package.
 *
 */
public class AadlPackage extends AadlElement implements AadlContainterInterface {

	/**
	 * List of referenced packages.
	 */
	public final List<AadlPackage> referencedPackages = new LinkedList<AadlPackage>();
	/**
	 * List of processes.
	 */
	public final List<AadlProcess> processes = new LinkedList<AadlProcess>();
	/**
	 * List of systems.
	 */
	public final List<AadlSystem> systems = new LinkedList<AadlSystem>();
	/**
	 * List of properties.
	 */
	public final List<AadlProperty> properties = new LinkedList<AadlProperty>();
	/**
	 * List of subprograms.
	 */
	public final List<AadlSubprogram> subprograms = new LinkedList<AadlSubprogram>();
	/**
	 * List of processors.
	 */
	public final List<AadlProcessor> processors = new ArrayList<AadlProcessor>();
	/**
	 * List of devices.
	 */
	public final List<AadlDevice> devices = new LinkedList<AadlDevice>();
	/**
	 * List of busses.
	 */
	public final List<AadlBus> busses = new LinkedList<AadlBus>();
	/**
	 * List of data.
	 */
	public final List<AadlData> data = new LinkedList<AadlData>();

	/**
	 * A constructor.
	 * 
	 * @param packageName
	 *            Name
	 */
	public AadlPackage(final String packageName) {
		super(packageName);
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public List<AadlElement> getAllContainedElements() {
		final List<AadlElement> result = new LinkedList<AadlElement>();
		result.addAll(referencedPackages);
		result.addAll(systems);
		result.addAll(properties);
		result.addAll(subprograms);
		result.addAll(processes);
		result.addAll(processors);
		result.addAll(devices);
		result.addAll(busses);
		result.addAll(data);
		return result;
	}

	/**
	 * Gets system by its name
	 * 
	 * @param name
	 *            Name of the system to be retrieved
	 * @return The retrieved system
	 */
	public AadlSystem getSystemByName(final String name) {
		for (final AadlSystem system : systems) {
			if (system.name.equals(name)) {
				return system;
			}
		}
		return null;
	}

	/**
	 * Gets system by its owner
	 * 
	 * @param owner
	 *            Owner of the system to be retrieved
	 * @return The retrieved system
	 */
	public AadlSystem getSystemByOwner(final Object owner) {
		for (final AadlSystem system : systems) {
			if (owner.equals(system.owner)) {
				return system;
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public String getTypeName() {
		return "PACKAGE";
	}

	/**
	 * Sorts the contents for stable reproduction in serialization
	 */
	public void sortContents() {
		referencedPackages.sort(new Comparator<AadlPackage>() {
			@Override
			public int compare(AadlPackage o1, AadlPackage o2) {
				return o1.name.compareTo(o2.name);
			}
		});
		processors.sort(new Comparator<AadlProcessor>() {
			@Override
			public int compare(AadlProcessor o1, AadlProcessor o2) {
				return o1.name.compareTo(o2.name);
			}
		});
		processes.sort(new Comparator<AadlProcess>() {

			@Override
			public int compare(AadlProcess o1, AadlProcess o2) {
				return o1.name.compareTo(o2.name);
			}
		});
		devices.sort(new Comparator<AadlDevice>() {

			@Override
			public int compare(AadlDevice o1, AadlDevice o2) {
				return o1.name.compareTo(o2.name);
			}
		});
		subprograms.sort(new Comparator<AadlSubprogram>() {

			@Override
			public int compare(AadlSubprogram o1, AadlSubprogram o2) {
				return o1.name.compareTo(o2.name);
			}
		});
		systems.sort(new Comparator<AadlSystem>() {

			@Override
			public int compare(AadlSystem o1, AadlSystem o2) {
				return o1.name.compareTo(o2.name);
			}
		});
		properties.sort(new Comparator<AadlProperty>() {

			@Override
			public int compare(AadlProperty o1, AadlProperty o2) {
				return (o1.name + o1.value).compareTo(o2.name + o2.value);
			}
		});
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public String serializeToString(final UsageContext context, final String linePrefix) {
		final StringBuilder sb = new StringBuilder();

		sb.append("PACKAGE " + name + "\n");
		sb.append("PUBLIC\n");
		sb.append("\n");

		for (final AadlPackage pkg : referencedPackages)
			sb.append("WITH " + pkg.name + ";\n");
		if (referencedPackages.size() > 0) {
			sb.append("\n");
		}

		for (final AadlProcessor processor : processors) {
			sb.append(processor.serializeToString(new UsageContext(context, this), linePrefix));
		}

		for (final AadlProcess process : processes) {
			sb.append(process.serializeToString(new UsageContext(context, this), linePrefix));
		}

		for (final AadlDevice device : devices) {
			sb.append(device.serializeToString(new UsageContext(context, this), linePrefix));
		}

		// Busses are not needed
		// Data is not needed

		for (final AadlSubprogram subprogram : subprograms) {
			sb.append(subprogram.serializeToString(new UsageContext(context, this), linePrefix));
		}

		for (final AadlSystem system : systems) {
			sb.append(system.serializeToString(new UsageContext(context, this), linePrefix));
		}

		if (properties.size() > 0) {
			sb.append("PROPERTIES\n");
			for (final AadlProperty property : properties)
				sb.append("  " + property.serializeToString(new UsageContext(context, this), linePrefix + getIndent()));
		}

		sb.append("END " + name + ";\n\n");

		return sb.toString();
	}

}
