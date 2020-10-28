// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.architecture.aadldefinitionproviders;

import java.util.LinkedList;
import java.util.List;

import com.n7space.capellatasteplugin.modelling.NameConverter;
import com.n7space.capellatasteplugin.modelling.architecture.Component;
import com.n7space.capellatasteplugin.modelling.architecture.Function;
import com.n7space.capellatasteplugin.modelling.architecture.FunctionExchange;
import com.n7space.capellatasteplugin.modelling.architecture.SystemModel;
import com.n7space.capellatasteplugin.modelling.architecture.aadl.AadlAccessConnection;
import com.n7space.capellatasteplugin.modelling.architecture.aadl.AadlAccessConnection.Type;
import com.n7space.capellatasteplugin.modelling.architecture.aadl.AadlAccessFeature;
import com.n7space.capellatasteplugin.modelling.architecture.aadl.AadlAccessFeature.AccessType;
import com.n7space.capellatasteplugin.modelling.architecture.aadl.AadlData;
import com.n7space.capellatasteplugin.modelling.architecture.aadl.AadlFeature;
import com.n7space.capellatasteplugin.modelling.architecture.aadl.AadlPackage;
import com.n7space.capellatasteplugin.modelling.architecture.aadl.AadlPackages;
import com.n7space.capellatasteplugin.modelling.architecture.aadl.AadlParameterFeature;
import com.n7space.capellatasteplugin.modelling.architecture.aadl.AadlParameterFeature.Direction;
import com.n7space.capellatasteplugin.modelling.architecture.aadl.AadlProperty;
import com.n7space.capellatasteplugin.modelling.architecture.aadl.AadlSubcomponent;
import com.n7space.capellatasteplugin.modelling.architecture.aadl.AadlSubprogram;
import com.n7space.capellatasteplugin.modelling.architecture.aadl.AadlSystem;
import com.n7space.capellatasteplugin.modelling.architecture.aadldefinitionproviders.Layout.LayoutElement;
import com.n7space.capellatasteplugin.modelling.architecture.aadldefinitionproviders.Layout.LayoutElementConnection;
import com.n7space.capellatasteplugin.modelling.architecture.aadldefinitionproviders.Layout.LayoutType;
import com.n7space.capellatasteplugin.modelling.architecture.aadldefinitionproviders.Layout.Position;
import com.n7space.capellatasteplugin.utils.StringUtils;

/**
 * Class for translating an abstract system architecture model into
 * DeploymentView AADL.
 *
 */
public class InterfaceViewDefinitionProvider extends BaseDefinitionProvider {

	/**
	 * InterfaceView name.
	 */
	public static final String INTERFACE_VIEW_NAME = "InterfaceView";

	protected final AadlPackage tastePackage = new AadlPackage(StandardPackageNames.TASTE);
	protected final AadlPackage dataViewPackage = new AadlPackage(StandardPackageNames.DATA_VIEW);
	protected final AadlPackage tasteIvPropertiesPackage = new AadlPackage(StandardPackageNames.TASTE_IV_PROPERTIES);
	protected final AadlData timerDataType;
	protected final AadlData directiveDataType;

	protected final NameConverter nameConverter;

	/**
	 * A constructor.
	 *
	 * @param converter
	 *            Name converter
	 */
	public InterfaceViewDefinitionProvider(final NameConverter converter) {
		nameConverter = converter;
		timerDataType = new AadlData(dataViewPackage, "Timer");
		directiveDataType = new AadlData(dataViewPackage, "Taste_directive");
		dataViewPackage.data.add(timerDataType);
		dataViewPackage.data.add(directiveDataType);
	}

	/**
	 * Generates AADL packages defining the InterfaceView for the given system
	 * model.
	 *
	 * @param systemModel
	 *            System model
	 * @return InterfaceView packages
	 */
	public AadlPackages generateInterfaceViewPackages(final SystemModel systemModel) {
		final Layout engine = generateInterfaceViewLayout(systemModel);
		final AadlPackages pkgs = new AadlPackages();

		final AadlPackage interfaceViewIV = new AadlPackage(StandardPackageNames.INTERFACE_VIEW_IV);
		final List<AadlPackage> standardPackages = getStandardInterfaceViewPackages();
		interfaceViewIV.referencedPackages.addAll(standardPackages);
		interfaceViewIV.properties.add(new AadlProperty(interfaceViewIV, TasteProperties.DATA_VIEW, "(\"DataView\")"));
		interfaceViewIV.properties
				.add(new AadlProperty(interfaceViewIV, TasteProperties.DATA_VIEW_PATH, "(\"DataView.aadl\")"));

		final Position[] bounds = engine.getBounds();

		interfaceViewIV.properties.add(new AadlProperty(interfaceViewIV, TasteProperties.COORDINATES,
				CoordinateHelper.boundsToTasteCoordinates(bounds)));
		interfaceViewIV.properties.add(new AadlProperty(interfaceViewIV, TasteProperties.VERSION, tasteVersion));

		final AadlSystem interfaceViewSystem = new AadlSystem(interfaceViewIV, StandardSystemNames.INTERFACE_VIEW);

		pkgs.packages.addAll(
				generateFunctionPackagesAndSubcomponents(systemModel, interfaceViewSystem, engine, standardPackages));
		translateExchanges(pkgs, interfaceViewSystem, systemModel, engine);

		interfaceViewIV.systems.add(interfaceViewSystem);
		pkgs.packages.add(interfaceViewIV);
		resolveInterPackageDependencies(pkgs);

		return pkgs;
	}

	protected void addAllTimerSubcomponentsToFunctionSystem(final Function function, final AadlSystem system) {
		for (final String timerName : function.timerNames) {
			final AadlSubcomponent subcomponent = new AadlSubcomponent(system, nameConverter.getAadlTypeName(timerName),
					timerDataType, "");
			subcomponent.properties
					.add(new AadlProperty(subcomponent, TasteProperties.FS_DEFAULT_VALUE, "\"default\""));
			system.subcomponents.add(subcomponent);
		}
	}

	protected void addAllDirectiveSubcomponentsToFunctionSystem(final Function function, final AadlSystem system) {
		int count = 0;
		for (final String directive : function.directives) {
			count++;
			final AadlSubcomponent subcomponent = new AadlSubcomponent(system, "Directive" + count, directiveDataType,
					"");
			subcomponent.properties.add(
					new AadlProperty(subcomponent, TasteProperties.FS_DEFAULT_VALUE, StringUtils.escape(directive)));
			system.subcomponents.add(subcomponent);
		}
	}

	protected AadlSubprogram createSubprogramForProvidedInterface(final FunctionExchange exchange,
			final AadlPackage pkg) {
		final AadlSubprogram subprogram = new AadlSubprogram(pkg, "PI_" + nameConverter.getAadlTypeName(exchange.name));
		subprogram.features.addAll(getExchangeParametersAsFeatures(exchange, subprogram));
		final AadlProperty queueSize = new AadlProperty(subprogram, TasteProperties.ASSOCIATED_QUEUE_SIZE,
				"" + exchange.queueSize);
		subprogram.properties.add(queueSize);
		final AadlProperty executionTime = new AadlProperty(subprogram, AadlProperties.COMPUTE_EXECUTION_TIME,
				exchange.minimumExecutionTimeMs + " ms .. " + exchange.maximumExecutionTimeMs + " ms");
		subprogram.implementationProperties.add(executionTime);
		final AadlProperty deadline = new AadlProperty(subprogram, TasteProperties.DEADLINE,
				exchange.deadlineMs + " ms");
		subprogram.implementationProperties.add(deadline);

		return subprogram;
	}

	protected AadlSubprogram createSubprogramForRequiredInterface(final FunctionExchange exchange,
			final AadlPackage pkg) {
		final AadlSubprogram subprogram = new AadlSubprogram(pkg, "RI_" + nameConverter.getAadlTypeName(exchange.name));
		subprogram.features.addAll(getExchangeParametersAsFeatures(exchange, subprogram));
		return subprogram;
	}

	protected List<AadlPackage> generateFunctionPackagesAndSubcomponents(final SystemModel systemModel,
			final AadlSystem interfaceViewSystem, final Layout engine, final List<AadlPackage> standardPackages) {
		final List<AadlPackage> pkgs = new LinkedList<AadlPackage>();
		for (final Function function : systemModel.functions) {
			final LayoutElement layoutElement = engine.getElementForWrappedObject(function);
			final AadlPackage pkg = new AadlPackage(
					StandardPackageNames.INTERFACE_VIEW_IV + "::" + nameConverter.getAadlTypeName(function.name));
			pkg.owner = function;
			pkg.referencedPackages.addAll(standardPackages);
			pkgs.add(pkg);

			final AadlSystem system = new AadlSystem(pkg, nameConverter.getAadlTypeName(function.name));
			system.owner = function;
			system.properties
					.add(new AadlProperty(system, AadlProperties.SOURCE_LANGUAGE, "(" + function.language + ")"));
			system.properties.add(new AadlProperty(system, TasteProperties.ACTIVE_INTERFACES, "any"));
			addAllTimerSubcomponentsToFunctionSystem(function, system);
			addAllDirectiveSubcomponentsToFunctionSystem(function, system);

			pkg.systems.add(system);
			final AadlSubcomponent subcomponent = new AadlSubcomponent(interfaceViewSystem, system);
			subcomponent.properties.add(new AadlProperty(subcomponent, TasteProperties.COORDINATES,
					CoordinateHelper.getTasteCoordinates(layoutElement)));
			subcomponent.owner = function;
			interfaceViewSystem.subcomponents.add(subcomponent);
		}
		return pkgs;
	}

	protected Layout generateInterfaceViewLayout(final SystemModel systemModel) {
		final Layout engine = new Layout(LayoutType.GRAPH);
		for (final Function function : systemModel.functions) {
			engine.addElement(new LayoutElement(function));
		}
		for (final FunctionExchange exchange : systemModel.functionExchanges) {
			final LayoutElement sourceElement = engine.getElementForWrappedObject(exchange.requiringFunction);
			final LayoutElement targetElement = engine.getElementForWrappedObject(exchange.providingFunction);
			if (sourceElement != null && targetElement != null) {
				final LayoutElementConnection connection = new LayoutElementConnection(exchange, sourceElement,
						targetElement);
				engine.addConnection(connection);
				sourceElement.connections.add(connection);
				targetElement.connections.add(connection);
			}
		}
		engine.generateLayout();
		return engine;
	}

	boolean isConnectingDifferentPartitions(final FunctionExchange exchange) {
		try {
			final Function parent1 = ((Function) exchange.parents.get(0));
			final Function parent2 = ((Function) exchange.parents.get(1));
			final Component node1 = ((Component) (parent1.getFirstParent())).getTopmostComponent();
			final Component node2 = ((Component) (parent2.getFirstParent())).getTopmostComponent();
			return !node1.id.equals(node2.id);
		} catch (Exception e) {
			return true;
		}
	}

	AadlParameterFeature.Encoding getParameterEncodingForExchange(final FunctionExchange exchange) {
		switch (exchange.encoding) {
		case ACN:
			return AadlParameterFeature.Encoding.ACN;
		case NATIVE:
			return AadlParameterFeature.Encoding.NATIVE;
		case UPER:
			return AadlParameterFeature.Encoding.UPER;
		case DEFAULT:
		default:
			return isConnectingDifferentPartitions(exchange) ? AadlParameterFeature.Encoding.UPER
					: AadlParameterFeature.Encoding.NATIVE;
		}
	}

	protected List<AadlFeature> getExchangeParametersAsFeatures(final FunctionExchange exchange,
			final AadlSubprogram subprogram) {
		final AadlParameterFeature.Encoding encoding = getParameterEncodingForExchange(exchange);
		final List<AadlFeature> features = new LinkedList<AadlFeature>();
		for (final FunctionExchange.ExchangeParameter parameter : exchange.parameters) {
			features.add(new AadlParameterFeature(
					nameConverter.getAadlTypeName(nameConverter.getAsn1IdentifierName(parameter.type.dataPackage.name,
							parameter.name, parameter.name)),
					subprogram, parameter.isOutput ? Direction.OUT : Direction.IN,
					nameConverter.getAadlTypeName(nameConverter.getAsn1TypeName(parameter.type.dataPackage.name,
							parameter.type.name, parameter.type.id)),
					encoding));
		}
		return features;

	}

	protected List<AadlPackage> getStandardInterfaceViewPackages() {
		final List<AadlPackage> pkgs = new LinkedList<AadlPackage>();
		pkgs.add(tastePackage);
		pkgs.add(dataViewPackage);
		pkgs.add(tasteIvPropertiesPackage);
		return pkgs;
	}

	protected void translateExchanges(final AadlPackages functionPkgs, final AadlSystem interfaceViewSystem,
			final SystemModel systemModel, final Layout layout) {
		for (final FunctionExchange exchange : systemModel.functionExchanges) {
			final LayoutElementConnection layoutConnection = layout.getConnectionForWrappedObject(exchange);
			final AadlPackage requiringPkg = functionPkgs.getOwnedPackage(exchange.requiringFunction);
			final AadlPackage providingPkg = functionPkgs.getOwnedPackage(exchange.providingFunction);

			final AadlSystem requiringSystem = requiringPkg.getSystemByOwner(exchange.requiringFunction);
			final AadlSystem providingSystem = providingPkg.getSystemByOwner(exchange.providingFunction);

			final AadlSubprogram providedInterfaceSubprogram = createSubprogramForProvidedInterface(exchange,
					providingPkg);
			final AadlSubprogram requiredInterfaceSubprogram = createSubprogramForRequiredInterface(exchange,
					requiringPkg);

			final AadlAccessFeature providingFeature = new AadlAccessFeature(providedInterfaceSubprogram.name,
					providingSystem, AccessType.PROVIDES, providedInterfaceSubprogram, "others");
			providingFeature.properties.add(new AadlProperty(providingFeature, TasteProperties.COORDINATES,
					CoordinateHelper.positionToTasteCoordinates(layoutConnection.targetAttachmentPosition)));
			providingFeature.properties.add(new AadlProperty(providingFeature, TasteProperties.INTERFACE_NAME,
					"\"" + nameConverter.getAadlTypeName(exchange.name) + "\""));
			providingFeature.properties.add(new AadlProperty(providingFeature, TasteProperties.RCM_OPERATION_KIND,
					exchange.exchangeKind.name().toLowerCase()));

			final AadlAccessFeature requiringFeature = new AadlAccessFeature(requiredInterfaceSubprogram.name,
					requiringSystem, AccessType.REQUIRES, providedInterfaceSubprogram, "others");
			requiringFeature.properties.add(new AadlProperty(providingFeature, TasteProperties.COORDINATES,
					CoordinateHelper.positionToTasteCoordinates(layoutConnection.sourceAttachmentPosition)));
			requiringFeature.properties.add(new AadlProperty(requiringFeature, TasteProperties.INTERFACE_NAME,
					"\"" + nameConverter.getAadlTypeName(exchange.name) + "\""));
			requiringFeature.properties
					.add(new AadlProperty(providingFeature, TasteProperties.RCM_OPERATION_KIND, "any"));

			providingPkg.subprograms.add(providedInterfaceSubprogram);
			requiringPkg.subprograms.add(requiredInterfaceSubprogram);

			requiringSystem.features.add(requiringFeature);
			providingSystem.features.add(providingFeature);

			final AadlAccessConnection connection = new AadlAccessConnection(interfaceViewSystem, Type.SUBPROGRAM,
					requiringFeature, providingFeature);
			connection.owner = exchange;
			final AadlProperty pathProperty = new AadlProperty(connection, TasteProperties.COORDINATES,
					CoordinateHelper.pathToTasteCoordinates(layoutConnection.path));
			connection.properties.add(pathProperty);
			interfaceViewSystem.connections.add(connection);
		}
	}

}
