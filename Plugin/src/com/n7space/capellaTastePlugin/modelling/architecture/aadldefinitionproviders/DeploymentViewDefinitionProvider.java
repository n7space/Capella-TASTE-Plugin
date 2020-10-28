// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.architecture.aadldefinitionproviders;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.n7space.capellatasteplugin.modelling.NameConverter;
import com.n7space.capellatasteplugin.modelling.architecture.Component;
import com.n7space.capellatasteplugin.modelling.architecture.ComponentExchange;
import com.n7space.capellatasteplugin.modelling.architecture.ComponentPort;
import com.n7space.capellatasteplugin.modelling.architecture.Function;
import com.n7space.capellatasteplugin.modelling.architecture.FunctionExchange;
import com.n7space.capellatasteplugin.modelling.architecture.SystemModel;
import com.n7space.capellatasteplugin.modelling.architecture.aadl.AadlAccessConnection;
import com.n7space.capellatasteplugin.modelling.architecture.aadl.AadlAccessConnection.Type;
import com.n7space.capellatasteplugin.modelling.architecture.aadl.AadlAccessFeature;
import com.n7space.capellatasteplugin.modelling.architecture.aadl.AadlBus;
import com.n7space.capellatasteplugin.modelling.architecture.aadl.AadlChildElement;
import com.n7space.capellatasteplugin.modelling.architecture.aadl.AadlConnection;
import com.n7space.capellatasteplugin.modelling.architecture.aadl.AadlDevice;
import com.n7space.capellatasteplugin.modelling.architecture.aadl.AadlPackage;
import com.n7space.capellatasteplugin.modelling.architecture.aadl.AadlPackages;
import com.n7space.capellatasteplugin.modelling.architecture.aadl.AadlProcess;
import com.n7space.capellatasteplugin.modelling.architecture.aadl.AadlProcessor;
import com.n7space.capellatasteplugin.modelling.architecture.aadl.AadlProperty;
import com.n7space.capellatasteplugin.modelling.architecture.aadl.AadlSubcomponent;
import com.n7space.capellatasteplugin.modelling.architecture.aadl.AadlSystem;
import com.n7space.capellatasteplugin.modelling.architecture.aadldefinitionproviders.Layout.LayoutElement;
import com.n7space.capellatasteplugin.modelling.architecture.aadldefinitionproviders.Layout.LayoutElementConnection;
import com.n7space.capellatasteplugin.modelling.architecture.aadldefinitionproviders.Layout.LayoutType;
import com.n7space.capellatasteplugin.utils.StringUtils;

/**
 * Class for translating an abstract system architecture model into
 * DeploymentView AADL.
 *
 */
public class DeploymentViewDefinitionProvider extends BaseDefinitionProvider {

	/**
	 * Enumeration listing possible option handles.
	 */
	public static enum DeploymentViewDefinitionProviderOption {
		/**
		 * Name of the sequence member for class realizations.
		 */
		PathToHardwareLibraries("PathToHardwareLibraries");

		/**
		 * Prefix for the string representation.
		 */
		public final static String NAME_COVERTER_PREFIX = "com.n7space.capellatasteplugin.modelling.architecture.aadldefinitionproviders.DeploymentViewDefinitionProvider";

		private final String value;

		private DeploymentViewDefinitionProviderOption(final String baseName) {
			value = baseName;
		}

		/**
		 * Returns the string representation of the handle.
		 *
		 * @return The string representation of the handle
		 */
		@Override
		public String toString() {
			return NAME_COVERTER_PREFIX + value;
		}
	}

	/**
	 * DeploymentView name.
	 */
	public static final String DEPLOYMENT_VIEW_NAME = "DeploymentView";

	protected final Option[] OPTIONS = {
			new Option(DeploymentViewDefinitionProviderOption.PathToHardwareLibraries, "Path to hardware libraries",
					"/home/taste/tool-inst/share/ocarina/AADLv2/ocarina_components.aadl", false) };

	protected final NameConverter nameConverter;
	protected final List<AadlPackage> standardDeploymentPackages = new LinkedList<AadlPackage>();
	protected Map<String, AadlPackage> processorPackages = new HashMap<String, AadlPackage>();
	protected Map<String, AadlPackage> driverPackages = new HashMap<String, AadlPackage>();
	protected Map<String, AadlPackage> busPackages = new HashMap<String, AadlPackage>();

	/**
	 * A constructor.
	 *
	 * @param converter
	 *            Name converter.
	 */
	public DeploymentViewDefinitionProvider(final NameConverter converter) {
		nameConverter = converter;
		standardDeploymentPackages.add(new AadlPackage("Taste"));
		standardDeploymentPackages.add(new AadlPackage("Deployment"));
		standardDeploymentPackages.add(new AadlPackage("TASTE_DV_Properties"));
		addOptions(OPTIONS);
	}

	/**
	 * Generates AADL packages defining the DeploymentView for the given system
	 * model and set of InterfaceView packages.
	 *
	 * @param systemModel
	 *            System model
	 * @param interfaceViewPackages
	 *            InterfaceView packages
	 * @return DeploymentView packages
	 */
	public AadlPackages generateDeploymentViewPackages(final SystemModel systemModel,
			final AadlPackages interfaceViewPackages) {
		busPackages.clear();
		processorPackages.clear();
		driverPackages.clear();
		final Layout layout = generateDeploymentViewLayout(systemModel);
		final AadlPackages pkgs = new AadlPackages();

		final AadlPackage deploymentViewPackage = new AadlPackage(StandardPackageNames.DEPLOYMENT_VIEW_DV);
		deploymentViewPackage.referencedPackages.addAll(standardDeploymentPackages);

		final AadlSystem deploymentViewSystem = new AadlSystem(deploymentViewPackage,
				StandardSystemNames.DEPLOYMENT_VIEW);
		final AadlPackage interfaceViewPackage = interfaceViewPackages
				.getPackageByName(StandardPackageNames.INTERFACE_VIEW_IV);
		final AadlSystem interfaceViewSystem = interfaceViewPackage.getSystemByName(StandardSystemNames.INTERFACE_VIEW);

		for (final ComponentExchange exchange : systemModel.componentExchanges) {
			if (!isExchangeEligible(systemModel, exchange))
				continue;
			createBus(interfaceViewSystem, deploymentViewSystem, layout, exchange);
		}

		for (final Component nodeComponent : systemModel.topLevelComponents) {
			if (!isNodeEligible(systemModel, nodeComponent))
				continue;
			createNode(pkgs, interfaceViewPackages, deploymentViewPackage, deploymentViewSystem, nodeComponent, layout);
		}

		deploymentViewSystem.subcomponents.add(new AadlSubcomponent(deploymentViewSystem, interfaceViewSystem));

		deploymentViewPackage.systems.add(deploymentViewSystem);
		applyDeploymentViewPackageProperties(deploymentViewPackage, layout);

		pkgs.packages.add(deploymentViewPackage);

		resolveInterPackageDependencies(pkgs);

		return pkgs;
	}

	protected AadlSubcomponent addBusSubcomponent(final AadlSystem deploymentViewSystem, final Layout layout,
			final ComponentExchange exchange) {
		final AadlSubcomponent subcomponent = new AadlSubcomponent(deploymentViewSystem,
				nameConverter.getAadlTypeName(exchange.name), getBus(exchange.typeClass), "");

		final LayoutElement layoutElement = layout.getElementForWrappedObject(exchange);
		subcomponent.properties.add(new AadlProperty(subcomponent, TasteProperties.COORDINATES, CoordinateHelper
				.positionAndSizeToTasteCoordinates(layoutElement.getPosition(), layoutElement.getSize())));

		deploymentViewSystem.subcomponents.add(subcomponent);
		return subcomponent;
	}

	protected AadlSubcomponent addHostProcessorSubcomponent(final AadlProcessor hostProcessor,
			final Component nodeComponent, final Layout layout, final AadlSystem nodeSystem) {
		final AadlSubcomponent hostProcessorSubcomponent = new AadlSubcomponent(nodeSystem,
				nameConverter.getAadlTypeName(hostProcessor.name), hostProcessor, "");
		hostProcessorSubcomponent.properties
				.add(new AadlProperty(hostProcessorSubcomponent, TasteProperties.COORDINATES,
						CoordinateHelper.positionAndSizeToTasteCoordinates(
								layout.getPositionOfWrappedObject(nodeComponent.processor),
								layout.getSizeOfWrappedObject(nodeComponent.processor))));
		return hostProcessorSubcomponent;
	}

	protected AadlPackage addNodePackage(final Component nodeComponent) {
		final AadlPackage nodePackage = new AadlPackage(
				StandardPackageNames.DEPLOYMENT_VIEW_DV + "::" + nameConverter.getAadlTypeName(nodeComponent.name));
		nodePackage.referencedPackages.addAll(standardDeploymentPackages);
		return nodePackage;
	}

	protected AadlSystem addNodeSystem(final AadlPackage deploymentViewPackage, final Component nodeComponent) {
		final AadlSystem nodeSystem = new AadlSystem(deploymentViewPackage,
				nameConverter.getAadlTypeName(nodeComponent.name));
		deploymentViewPackage.systems.add(nodeSystem);
		return nodeSystem;
	}

	protected AadlSubcomponent addNodeSystemSubcomponent(final AadlSystem deploymentViewSystem,
			final Component nodeComponent, final Layout layout, final AadlSystem nodeSystem) {
		final AadlSubcomponent nodeSystemSubcomponent = new AadlSubcomponent(deploymentViewSystem, nodeSystem);
		final LayoutElement nodeElement = layout.getElementForWrappedObject(nodeComponent);

		nodeSystemSubcomponent.properties.add(new AadlProperty(nodeSystemSubcomponent, TasteProperties.COORDINATES,
				CoordinateHelper.positionAndSizeToTasteCoordinates(nodeElement.getPosition(), nodeElement.getSize())));
		deploymentViewSystem.subcomponents.add(nodeSystemSubcomponent);
		return nodeSystemSubcomponent;
	}

	protected AadlProcess addPartitionProcess(final AadlPackages resultingPackages, final AadlPackage nodePackage,
			final Component partitionComponent) {
		final AadlProcess partitionProcess = new AadlProcess(nodePackage,
				nameConverter.getAadlTypeName(partitionComponent.name));
		nodePackage.processes.add(partitionProcess);
		resultingPackages.packages.add(nodePackage);
		return partitionProcess;
	}

	protected AadlSubcomponent addPartitionSubcomponent(final AadlSystem nodeSystem,
			final AadlSubcomponent hostProcessorSubcomponent, final Layout layout, final Component partitionComponent,
			final AadlProcess partitionProcess) {
		final AadlSubcomponent partitionProcessSubcomponent = new AadlSubcomponent(nodeSystem, partitionProcess);

		partitionProcessSubcomponent.properties
				.add(new AadlProperty(partitionProcessSubcomponent, TasteProperties.COORDINATES,
						CoordinateHelper.positionAndSizeToTasteCoordinates(
								layout.getPositionOfWrappedObject(partitionComponent),
								layout.getSizeOfWrappedObject(partitionComponent))));
		partitionProcessSubcomponent.properties
				.add(new AadlProperty(partitionProcessSubcomponent, DeploymentProperties.PORT_NUMBER, "0"));
		nodeSystem.subcomponents.add(partitionProcessSubcomponent);
		nodeSystem.implementationProperties.add(new AadlProperty(nodeSystem, AadlProperties.ACTUAL_PROCESSOR_BINDING,
				"(reference (" + hostProcessorSubcomponent.name + ")) APPLIES TO " + partitionProcess.name));
		return partitionProcessSubcomponent;
	}

	protected void addPort(final AadlSystem deploymentViewSystem, final AadlPackage nodePackage,
			final AadlSystem nodeSystem, final AadlSubcomponent hostProcessorSubcomponent, final Layout layout,
			final ComponentPort port) {
		final AadlDevice portDevice = addPortDevice(nodePackage, port, layout);
		final AadlAccessFeature devicePortLink = addPortLinkAccessFeatureToDevice(portDevice, port, layout);
		final AadlAccessFeature systemPortLink = addPortLinkAccessFeatureToNode(nodeSystem, port);
		final AadlSubcomponent deviceSubcomponent = addPortDeviceSubcomponent(nodeSystem, layout, port, portDevice);
		createNodeLevelConnectionForPort(nodeSystem, devicePortLink, systemPortLink);
		bindPortDeviceToProcessor(nodeSystem, hostProcessorSubcomponent, deviceSubcomponent);
		createSystemLevelConnectionForPort(deploymentViewSystem, port, systemPortLink, layout);
	}

	protected AadlDevice addPortDevice(final AadlPackage nodePackage, final ComponentPort port, final Layout layout) {
		final AadlDevice device = new AadlDevice(nodePackage, getDevice(port.typeClass),
				getDevice(port.implementationTypeClass), nameConverter.getAadlTypeName(port.name));

		final LayoutElementConnection layoutConnection = layout.getConnectionForWrappedObject(port.getFirstParent());

		device.properties.add(new AadlProperty(device, TasteProperties.INTERFACE_COORDINATES,
				CoordinateHelper.positionToTasteCoordinates(layoutConnection.targetAttachmentPosition)
						+ " APPLIES TO link"));
		device.properties.add(new AadlProperty(device, DeploymentProperties.HELP, DeploymentProperties.HELP_CONTENT));
		if (StringUtils.isNotEmpty(port.configurationSchema))
			device.properties
					.add(new AadlProperty(device, DeploymentProperties.CONFIGURATION_SCHEMA, port.configurationSchema));
		if (StringUtils.isNotEmpty(port.configuration))
			device.properties.add(new AadlProperty(device, DeploymentProperties.CONFIGURATION, port.configuration));
		if (StringUtils.isNotEmpty(port.version))
			device.properties.add(new AadlProperty(device, DeploymentProperties.VERSION, port.version));
		nodePackage.devices.add(device);
		return device;
	}

	protected AadlSubcomponent addPortDeviceSubcomponent(final AadlSystem nodeSystem, final Layout layout,
			final ComponentPort port, final AadlDevice device) {
		final AadlSubcomponent deviceSubcomponent = new AadlSubcomponent(nodeSystem,
				nameConverter.getAadlTypeName(port.name), device);

		deviceSubcomponent.properties.add(new AadlProperty(deviceSubcomponent, TasteProperties.COORDINATES,
				CoordinateHelper.positionAndSizeToTasteCoordinates(layout.getPositionOfWrappedObject(port),
						layout.getSizeOfWrappedObject(port))));
		nodeSystem.subcomponents.add(deviceSubcomponent);
		return deviceSubcomponent;
	}

	protected AadlAccessFeature addPortLinkAccessFeatureToDevice(final AadlDevice device, final ComponentPort port,
			final Layout layout) {
		final AadlAccessFeature devicePortLink = new AadlAccessFeature("link", device,
				AadlAccessFeature.AccessType.REFINED_TO_REQUIRES, getBus(port.exchange.typeClass), "");

		final LayoutElementConnection layoutConnection = layout.getConnectionForWrappedObject(port.getFirstParent());

		devicePortLink.properties.add(new AadlProperty(devicePortLink, TasteProperties.COORDINATES,
				CoordinateHelper.positionToTasteCoordinates(layoutConnection.sourceAttachmentPosition)));
		device.features.add(devicePortLink);
		return devicePortLink;
	}

	protected AadlAccessFeature addPortLinkAccessFeatureToNode(final AadlSystem nodeSystem, final ComponentPort port) {
		final AadlAccessFeature systemPortLink = new AadlAccessFeature(
				nameConverter.getAadlTypeName(port.name + "_" + port.exchange.name), nodeSystem,
				AadlAccessFeature.AccessType.REQUIRES, getBus(port.exchange.typeClass), "");
		nodeSystem.features.add(systemPortLink);
		return systemPortLink;
	}

	protected void applyDeploymentViewPackageProperties(final AadlPackage deploymentViewPackage, final Layout layout) {
		deploymentViewPackage.properties.add(new AadlProperty(deploymentViewPackage, TasteProperties.COORDINATES,
				CoordinateHelper.boundsToTasteCoordinates(layout.getBounds())));
		deploymentViewPackage.properties
				.add(new AadlProperty(deploymentViewPackage, TasteProperties.VERSION, tasteVersion));
		deploymentViewPackage.properties.add(new AadlProperty(deploymentViewPackage, TasteProperties.INTERFACE_VIEW,
				"\"" + InterfaceViewDefinitionProvider.INTERFACE_VIEW_NAME + ".aadl\""));
		deploymentViewPackage.properties
				.add(new AadlProperty(deploymentViewPackage, TasteProperties.HW_LIBRARIES,
						"(" + StringUtils.escape(
								getStringOptionValue(DeploymentViewDefinitionProviderOption.PathToHardwareLibraries))
								+ ")"));
	}

	protected void bindPortDeviceToProcessor(final AadlSystem nodeSystem,
			final AadlSubcomponent hostProcessorSubcomponent, final AadlSubcomponent deviceSubcomponent) {
		nodeSystem.implementationProperties.add(new AadlProperty(nodeSystem, AadlProperties.ACTUAL_PROCESSOR_BINDING,
				"(reference (" + hostProcessorSubcomponent.name + ")) APPLIES TO " + deviceSubcomponent.name));
	}

	protected void createBus(final AadlSystem interfaceViewSystem, final AadlSystem deploymentViewSystem,
			final Layout layout, final ComponentExchange exchange) {
		final AadlSubcomponent subcomponent = addBusSubcomponent(deploymentViewSystem, layout, exchange);
		createInterfaceViewConnectionBindingsToBus(interfaceViewSystem, deploymentViewSystem, exchange, subcomponent);
	}

	protected void createInterfaceViewConnectionBindingsToBus(final AadlSystem interfaceViewSystem,
			final AadlSystem deploymentViewSystem, final ComponentExchange exchange,
			final AadlSubcomponent subcomponent) {
		for (final FunctionExchange allocatedExchange : exchange.allocatedFunctionExchanges) {
			final AadlConnection interfaceViweConnection = getConnectionByOwner(interfaceViewSystem, allocatedExchange);
			final AadlProperty binding = new AadlProperty(deploymentViewSystem,
					AadlProperties.ACTUAL_CONNECTION_BINDING, "(reference (" + subcomponent.name + ")) APPLIES TO "
							+ StandardSystemNames.INTERFACE_VIEW + "." + interfaceViweConnection.name);
			deploymentViewSystem.implementationProperties.add(binding);
		}
	}

	protected void createNode(final AadlPackages resultingPackages, final AadlPackages interfaceViewPackages,
			final AadlPackage deploymentViewPackage, final AadlSystem deploymentViewSystem,
			final Component nodeComponent, final Layout layout) {

		final AadlProcessor hostProcessor = getProcessor(nodeComponent.processor.typeClass);
		final AadlPackage nodePackage = addNodePackage(nodeComponent);
		final AadlSystem nodeSystem = addNodeSystem(deploymentViewPackage, nodeComponent);

		final AadlSubcomponent hostProcessorSubcomponent = addHostProcessorSubcomponent(hostProcessor, nodeComponent,
				layout, nodeSystem);

		for (final Component partitionComponent : nodeComponent.deployedComponents) {
			createPartition(resultingPackages, interfaceViewPackages, nodePackage, nodeSystem,
					hostProcessorSubcomponent, layout, partitionComponent);
		}

		nodeSystem.subcomponents.add(hostProcessorSubcomponent);

		for (final ComponentPort port : nodeComponent.ports) {
			addPort(deploymentViewSystem, nodePackage, nodeSystem, hostProcessorSubcomponent, layout, port);
		}
		addNodeSystemSubcomponent(deploymentViewSystem, nodeComponent, layout, nodeSystem);
	}

	protected void createNodeLevelConnectionForPort(final AadlSystem nodeSystem, final AadlAccessFeature devicePortLink,
			final AadlAccessFeature systemPortLink) {
		final AadlConnection nodeLevelConnection = new AadlAccessConnection(nodeSystem, Type.BUS, devicePortLink,
				systemPortLink);

		nodeSystem.connections.add(nodeLevelConnection);
	}

	protected void createPartition(final AadlPackages resultingPackages, final AadlPackages interfaceViewPackages,
			final AadlPackage nodePackage, final AadlSystem nodeSystem,
			final AadlSubcomponent hostProcessorSubcomponent, final Layout layout, final Component partitionComponent) {

		final AadlProcess partitionProcess = addPartitionProcess(resultingPackages, nodePackage, partitionComponent);

		deployFunctionsToNode(nodeSystem, interfaceViewPackages, partitionProcess,
				partitionComponent.allocatedFunctions, layout);

		addPartitionSubcomponent(nodeSystem, hostProcessorSubcomponent, layout, partitionComponent, partitionProcess);
	}

	protected void createSystemLevelConnectionForPort(final AadlSystem deploymentViewSystem, final ComponentPort port,
			final AadlAccessFeature systemPortLink, final Layout layout) {
		final AadlChildElement bus = getSubcomponentForName(deploymentViewSystem,
				nameConverter.getAadlTypeName(port.exchange.name));
		final AadlConnection systemLevelConnection = new AadlAccessConnection(deploymentViewSystem, Type.BUS,
				systemPortLink, bus);

		final LayoutElementConnection layoutConnection = layout.getConnectionForWrappedObject(port.getFirstParent());

		systemLevelConnection.properties.add(new AadlProperty(systemLevelConnection, TasteProperties.COORDINATES,
				CoordinateHelper.pathToTasteCoordinates(layoutConnection.path)));
		deploymentViewSystem.connections.add(systemLevelConnection);
	}

	protected void deployFunctionsToNode(final AadlSystem nodeSystem, final AadlPackages interfaceViewPackages,
			final AadlProcess partitionProcess, final Iterable<Function> functions, final Layout layout) {
		for (final Function function : functions) {
			final AadlPackage functionPackage = interfaceViewPackages.getOwnedPackage(function);
			final AadlSystem functionSystem = functionPackage.getSystemByOwner(function);
			final AadlSubcomponent functionSubcomponent = new AadlSubcomponent(nodeSystem, "IV_" + functionSystem.name,
					functionSystem);

			functionSubcomponent.properties.add(new AadlProperty(functionSubcomponent, TasteProperties.FUNCTION_NAME,
					"\"" + functionSystem.name + "\""));
			nodeSystem.subcomponents.add(functionSubcomponent);
			nodeSystem.implementationProperties.add(new AadlProperty(nodeSystem, TasteProperties.APLC_BINDING,
					"(reference (" + partitionProcess.name + ")) APPLIES TO " + functionSubcomponent.name));
		}
	}

	protected Layout generateDeploymentViewLayout(final SystemModel systemModel) {
		final Layout layout = new Layout(LayoutType.GRAPH);
		for (final Component nodeComponent : systemModel.topLevelComponents) {
			final LayoutElement node = new LayoutElement(nodeComponent);
			final LayoutElement processor = new LayoutElement(nodeComponent.processor);

			for (final Component partitionComponent : nodeComponent.deployedComponents) {
				final LayoutElement partition = new LayoutElement(partitionComponent);
				for (final Function function : partitionComponent.allocatedFunctions) {
					partition.internalLayout.addElement(new LayoutElement(function));
				}
				processor.internalLayout.addElement(partition);
			}
			for (final ComponentPort port : nodeComponent.ports) {
				final LayoutElement portElement = new LayoutElement(port);
				node.internalLayout.addElement(portElement);
			}

			node.internalLayout.addElement(processor);
			layout.addElement(node);
		}
		for (final ComponentExchange nodeExchange : systemModel.componentExchanges) {
			final LayoutElement node = new LayoutElement(nodeExchange);
			final LayoutElement sourceElement = layout.getElementForWrappedObject(nodeExchange.parents.get(0));
			final LayoutElement targetElement = layout.getElementForWrappedObject(nodeExchange.parents.get(1));
			layout.addElement(node);
			layout.addConnection(new LayoutElementConnection(nodeExchange.parents.get(0), sourceElement, node));
			layout.addConnection(new LayoutElementConnection(nodeExchange.parents.get(1), targetElement, node));
		}

		layout.generateLayout();
		return layout;
	}

	protected AadlBus getBus(final String typeClass) {

		final String pkgName = getPackageName(typeClass);
		final String className = getClassName(typeClass);

		AadlPackage pkg = busPackages.get(pkgName);
		if (pkg == null) {
			pkg = new AadlPackage(pkgName);
			busPackages.put(pkgName, pkg);
		}

		for (final AadlBus bus : pkg.busses) {
			if (bus.name.equals(className))
				return bus;
		}
		final AadlBus bus = new AadlBus(pkg, className);
		pkg.busses.add(bus);
		return bus;
	}

	protected String getClassName(final String typeClass) {
		try {
			final int packageDivider = typeClass.lastIndexOf(':');
			final String className = typeClass.substring(packageDivider + 1);
			return className;
		} catch (final Throwable e) {
			return "Unknown";
		}
	}

	protected AadlConnection getConnectionByOwner(final AadlSystem system, final Object owner) {
		for (final AadlConnection connection : system.connections) {
			if (owner.equals(connection.owner)) {
				return connection;
			}
		}
		return null;
	}

	protected AadlDevice getDevice(final String typeClass) {

		final String pkgName = getPackageName(typeClass);
		final String className = getClassName(typeClass);

		AadlPackage pkg = driverPackages.get(pkgName);
		if (pkg == null) {
			pkg = new AadlPackage(pkgName);
			driverPackages.put(pkgName, pkg);
		}

		for (final AadlDevice device : pkg.devices) {
			if (device.name.equals(className))
				return device;
		}
		final AadlDevice device = new AadlDevice(pkg, className);
		pkg.devices.add(device);
		return device;
	}

	protected String getPackageName(final String typeClass) {
		try {
			final int packageDivider = typeClass.lastIndexOf(':');
			final String pkgName = typeClass.substring(0, packageDivider - 1);
			return pkgName;
		} catch (final Throwable e) {
			return "Unknown";
		}
	}

	protected AadlProcessor getProcessor(final String typeClass) {

		final String pkgName = getPackageName(typeClass);
		final String className = getClassName(typeClass);
		AadlPackage pkg = processorPackages.get(pkgName);
		if (pkg == null) {
			pkg = new AadlPackage(pkgName);
			processorPackages.put(pkgName, pkg);
		}
		for (final AadlProcessor processor : pkg.processors) {
			if (processor.name.equals(className))
				return processor;
		}
		final AadlProcessor processor = new AadlProcessor(pkg, className);
		pkg.processors.add(processor);
		return processor;
	}

	protected AadlChildElement getSubcomponentForName(final AadlSystem system, final String name) {
		for (final AadlChildElement element : system.subcomponents) {
			if (element.name.equals(name))
				return element;
		}
		return null;
	}

	protected boolean isExchangeEligible(final SystemModel model, final ComponentExchange exchange) {
		final Component parent1 = ((Component) exchange.parents.get(0)).getTopmostComponent();
		final Component parent2 = ((Component) exchange.parents.get(1)).getTopmostComponent();
		return !parent1.id.equals(parent2.id);
	}

	protected boolean isNodeEligible(final SystemModel model, final Component component) {
		return component.deployedComponents.size() > 0;
	}

}
