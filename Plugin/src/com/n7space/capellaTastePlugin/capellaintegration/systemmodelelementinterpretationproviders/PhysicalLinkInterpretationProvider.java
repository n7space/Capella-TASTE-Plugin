// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders;

import java.util.LinkedList;
import java.util.List;

import org.polarsys.capella.core.data.capellacore.CapellaElement;
import org.polarsys.capella.core.data.capellacore.Involvement;
import org.polarsys.capella.core.data.cs.AbstractPhysicalLinkEnd;
import org.polarsys.capella.core.data.cs.PhysicalLink;
import org.polarsys.capella.core.data.cs.PhysicalPath;
import org.polarsys.capella.core.data.cs.PhysicalPathInvolvement;
import org.polarsys.capella.core.data.cs.PhysicalPort;
import org.polarsys.capella.core.data.fa.ComponentExchange;
import org.polarsys.capella.core.data.fa.ComponentPort;
import org.polarsys.capella.core.data.fa.FunctionalExchange;
import org.polarsys.capella.core.data.information.Port;
import org.polarsys.capella.core.data.pa.AbstractPhysicalComponent;

import com.n7space.capellatasteplugin.modelling.architecture.Component;
import com.n7space.capellatasteplugin.modelling.architecture.FunctionExchange;
import com.n7space.capellatasteplugin.modelling.architecture.SystemModel;
import com.n7space.capellatasteplugin.utils.Issue;
import com.n7space.capellatasteplugin.utils.StringUtils;

/**
 * Class providing facilities for interpreting Physical Links and Physical
 * Paths.
 *
 */
public class PhysicalLinkInterpretationProvider {

	/**
	 * Converts a Capella Physical Link into an abstract Component Exchange and
	 * attaches it to the system model.
	 *
	 * @param model
	 *            System model
	 * @param link
	 *            Physical Link to be interpreted
	 * @param issues
	 *            [output] Detected issues
	 */
	public void convertCapellaPhysicalLinkToComponentExchangeAndAttachItToSystemModel(final SystemModel model,
			final PhysicalLink link, final List<Issue> issues) {
		if (link.getAllocatedComponentExchanges().size() == 0)
			return;

		final PhysicalPort srcPhysicalPort = link.getSourcePhysicalPort();
		final PhysicalPort dstPhysicalPort = link.getTargetPhysicalPort();
		final com.n7space.capellatasteplugin.modelling.architecture.ComponentExchange exchange = createAndAttachExchangeAndPorts(
				model, link.getName(), link.getId(), srcPhysicalPort, dstPhysicalPort, issues);

		if (exchange == null)
			return;

		setBusData(exchange, link, issues);
		attachAllocatedFunctionalExchanges(model, exchange, link, issues);
	}

	protected List<PhysicalPort> getPathSourcePorts(final PhysicalPath path, final List<PhysicalPort> ports) {
		final List<PhysicalPort> result = new LinkedList<PhysicalPort>();
		for (final ComponentExchange componentExchange : path.getAllocatedComponentExchanges()) {
			final PhysicalPort port = getTopLevelPort(ports, componentExchange.getSourcePort());
			result.add(port);
		}
		return result;
	}

	protected List<PhysicalPort> getPathTargetPorts(final PhysicalPath path, final List<PhysicalPort> ports) {
		final List<PhysicalPort> result = new LinkedList<PhysicalPort>();
		for (final ComponentExchange componentExchange : path.getAllocatedComponentExchanges()) {
			final PhysicalPort port = getTopLevelPort(ports, componentExchange.getTargetPort());
			result.add(port);
		}
		return result;
	}

	protected PhysicalPort getSinglePort(final List<PhysicalPort> ports) {
		if (ports.size() == 0)
			return null;
		final PhysicalPort reference = ports.get(0);
		for (final PhysicalPort port : ports)
			if (!port.getId().equals(reference.getId()))
				return null;
		return reference;
	}

	/**
	 * Converts a Capella Physical Path into abstract Component Exchange and
	 * attaches it to the system model.
	 *
	 * @param model
	 *            System model
	 * @param link
	 *            Physical Path to be interpreted
	 * @param issues
	 *            [output] Detected issues
	 */
	public void convertCapellaPhysicalPathToComponentExchangeAndAttachItToSystemModel(final SystemModel model,
			final PhysicalPath path, final List<Issue> issues) {
		final List<PhysicalPort> ports = new LinkedList<PhysicalPort>();

		getAllInvolvedPorts(path, ports);

		final PhysicalPort srcPhysicalPort = getSinglePort(getPathSourcePorts(path, ports));
		final PhysicalPort dstPhysicalPort = getSinglePort(getPathTargetPorts(path, ports));
		final com.n7space.capellatasteplugin.modelling.architecture.ComponentExchange exchange = createAndAttachExchangeAndPorts(
				model, path.getName(), path.getId(), srcPhysicalPort, dstPhysicalPort, issues);
		if (exchange == null)
			return;
		setBusData(exchange, path, issues);

		for (final ComponentExchange componentExchange : path.getAllocatedComponentExchanges())
			attachAllocatedFunctionalExchanges(model, exchange, componentExchange, issues);
	}

	protected void setBusData(final com.n7space.capellatasteplugin.modelling.architecture.ComponentExchange exchange,
			final PhysicalLink link, final List<Issue> issues) {
		final String typeClass = PropertyUtils.getProperty(link, CustomCapellaProperties.BUS);
		if (typeClass == null || typeClass.length() == 0) {
			issues.add(new Issue(Issue.Kind.Error, "Bus class for " + link.getName() + " is undefined", link));
			return;
		}
		exchange.typeClass = typeClass;

	}

	protected void setBusData(final com.n7space.capellatasteplugin.modelling.architecture.ComponentExchange exchange,
			final PhysicalPath path, final List<Issue> issues) {
		final String typeClass = PropertyUtils.getProperty(path, CustomCapellaProperties.BUS);
		if (typeClass == null || typeClass.length() == 0) {
			issues.add(new Issue(Issue.Kind.Error, "Bus class for " + path.getName() + " is undefined", path));
			return;
		}
		exchange.typeClass = typeClass;
	}

	protected void attachAllocatedFunctionalExchanges(final SystemModel model,
			final com.n7space.capellatasteplugin.modelling.architecture.ComponentExchange exchange,
			final ComponentExchange componentExchange, final List<Issue> issues) {
		for (final FunctionalExchange functionalExchange : componentExchange.getAllocatedFunctionalExchanges()) {
			final FunctionExchange allocatedExchange = model.getFunctionExchangeById(functionalExchange.getId());
			if (allocatedExchange == null) {
				issues.add(new Issue(Issue.Kind.Error,
						"Exchange " + functionalExchange.getName() + " cannot be found in the model",
						functionalExchange));
				continue;
			}
			exchange.allocatedFunctionExchanges.add(allocatedExchange);
		}
	}

	protected void attachAllocatedFunctionalExchanges(final SystemModel model,
			final com.n7space.capellatasteplugin.modelling.architecture.ComponentExchange exchange,
			final PhysicalLink link, final List<Issue> issues) {
		for (final ComponentExchange componentExchange : link.getAllocatedComponentExchanges()) {
			attachAllocatedFunctionalExchanges(model, exchange, componentExchange, issues);
		}
	}

	protected com.n7space.capellatasteplugin.modelling.architecture.ComponentExchange createAndAttachExchangeAndPorts(
			final SystemModel model, final String pathName, final String pathId, final PhysicalPort srcPhysicalPort,
			final PhysicalPort dstPhysicalPort, final List<Issue> issues) {
		final Component srcComponent = getComponentForPort(model, srcPhysicalPort);
		final Component dstComponent = getComponentForPort(model, dstPhysicalPort);
		if (srcComponent == null) {
			issues.add(new Issue(Issue.Kind.Error, "Source for connection " + pathName + " cannot be found", pathName));
			return null;
		}
		if (dstComponent == null) {
			issues.add(new Issue(Issue.Kind.Error, "Target for connection " + pathName + " cannot be found", pathName));
			return null;
		}

		final com.n7space.capellatasteplugin.modelling.architecture.ComponentExchange exchange = new com.n7space.capellatasteplugin.modelling.architecture.ComponentExchange(
				srcComponent, dstComponent, pathName, pathId);

		final com.n7space.capellatasteplugin.modelling.architecture.ComponentPort srcPort = new com.n7space.capellatasteplugin.modelling.architecture.ComponentPort(
				srcComponent, srcPhysicalPort.getName(), srcPhysicalPort.getId(), exchange);

		setDeviceData(srcPort, srcPhysicalPort, issues);

		final com.n7space.capellatasteplugin.modelling.architecture.ComponentPort dstPort = new com.n7space.capellatasteplugin.modelling.architecture.ComponentPort(
				dstComponent, dstPhysicalPort.getName(), dstPhysicalPort.getId(), exchange);

		setDeviceData(dstPort, dstPhysicalPort, issues);

		srcComponent.ports.add(srcPort);
		dstComponent.ports.add(dstPort);

		model.componentExchanges.add(exchange);

		return exchange;
	}

	protected Component getComponentForPort(final SystemModel model, final PhysicalPort port) {
		if (port == null)
			return null;
		if (!(port.eContainer() instanceof AbstractPhysicalComponent))
			return null;
		final AbstractPhysicalComponent parent = (AbstractPhysicalComponent) port.eContainer();
		for (final Component component : model.components) {
			if (component.id.equals(parent.getId()))
				return component;
		}
		return null;
	}

	protected PhysicalPort getParentPort(final List<PhysicalPort> ports, final ComponentPort port) {

		for (final PhysicalPort parent : ports) {
			for (final ComponentPort allocatedPort : parent.getAllocatedComponentPorts()) {
				if (allocatedPort.getId().equals(port.getId()))
					return parent;
			}
		}
		return null;
	}

	protected PhysicalPort getTopLevelPort(final List<PhysicalPort> ports, final Port port) {
		if (port instanceof ComponentPort) {
			final ComponentPort componentPort = (ComponentPort) port;
			return getParentPort(ports, componentPort);
		}
		return null;
	}

	protected void getAllInvolvedPorts(final PhysicalPath path, final List<PhysicalPort> ports) {
		for (final Involvement involvement : path.getInvolvedInvolvements()) {
			if (involvement instanceof PhysicalPathInvolvement)
				getAllPortsFromAnInvolvement(ports, (PhysicalPathInvolvement) involvement);
		}
	}

	protected void getAllPortsFromAnInvolvement(final List<PhysicalPort> ports,
			final PhysicalPathInvolvement physicalInvolvement) {

		final CapellaElement element = physicalInvolvement.getInvolved();
		if (element instanceof PhysicalLink) {
			final PhysicalLink link = (PhysicalLink) element;
			for (final AbstractPhysicalLinkEnd end : link.getLinkEnds()) {
				if (end instanceof PhysicalPort) {
					final PhysicalPort port = (PhysicalPort) end;
					ports.add(port);
				}
			}
		}
	}

	protected void setDeviceData(final com.n7space.capellatasteplugin.modelling.architecture.ComponentPort port,
			final PhysicalPort source, final List<Issue> issues) {

		final String implementationString = PropertyUtils.getProperty(source,
				CustomCapellaProperties.DEVICE_IMPLEMENTATION);
		if (implementationString == null || implementationString.length() == 0) {
			issues.add(
					new Issue(Issue.Kind.Error, "Device implementation for port " + port.name + " is undefined", port));
			return;
		}
		port.implementationTypeClass = implementationString;
		final String classString = PropertyUtils.getProperty(source, CustomCapellaProperties.DEVICE_CLASS);
		if (classString == null || classString.length() == 0) {
			final String derivedClassString = extractClassFromImplementation(implementationString);
			if (derivedClassString == null) {
				issues.add(new Issue(Issue.Kind.Error,
						"Device class for port " + port.name + " is undefined and cannot be derived", port));
				return;

			}
			port.typeClass = derivedClassString;

		} else {
			port.typeClass = classString;
		}

		final String configString = PropertyUtils.getProperty(source, CustomCapellaProperties.CONFIGURATION);
		final String configSchemaString = PropertyUtils.getProperty(source,
				CustomCapellaProperties.CONFIGURATION_SCHEMA);
		final String versionString = PropertyUtils.getProperty(source, CustomCapellaProperties.VERSION);

		setDeviceVersion(port, versionString);
		setDeviceConfiguration(port, issues, configString);
		setDeviceConfigurationSchema(port, issues, configSchemaString);
	}

	protected void setDeviceVersion(final com.n7space.capellatasteplugin.modelling.architecture.ComponentPort port,
			final String versionString) {
		if (versionString != null && versionString.length() > 0)
			port.version = StringUtils.escape(versionString);
	}

	protected void setDeviceConfigurationSchema(
			final com.n7space.capellatasteplugin.modelling.architecture.ComponentPort port, final List<Issue> issues,
			final String configSchemaString) {
		if (configSchemaString == null || configSchemaString.length() == 0)
			issues.add(new Issue(Issue.Kind.Warning, "Configuration schema for port " + port.name + " is undefined",
					port));
		else
			port.configurationSchema = StringUtils.escape(configSchemaString);
	}

	protected void setDeviceConfiguration(
			final com.n7space.capellatasteplugin.modelling.architecture.ComponentPort port, final List<Issue> issues,
			final String configString) {
		if (configString == null || configString.length() == 0)
			issues.add(new Issue(Issue.Kind.Warning, "Configuration for port " + port.name + " is undefined", port));
		else
			port.configuration = StringUtils.escape(configString);
	}

	protected String extractClassFromImplementation(final String implementation) {
		final int dotIndex = implementation.lastIndexOf('.');
		if (dotIndex <= 0 || dotIndex > implementation.length())
			return null;
		return implementation.substring(0, dotIndex);
	}

}
