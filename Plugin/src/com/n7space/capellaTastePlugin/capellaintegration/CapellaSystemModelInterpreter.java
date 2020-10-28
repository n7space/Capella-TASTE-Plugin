// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.polarsys.capella.core.data.cs.PhysicalLink;
import org.polarsys.capella.core.data.cs.PhysicalPath;
import org.polarsys.capella.core.data.pa.PhysicalActor;
import org.polarsys.capella.core.data.pa.PhysicalActorPkg;
import org.polarsys.capella.core.data.pa.PhysicalArchitecture;
import org.polarsys.capella.core.data.pa.PhysicalComponent;
import org.polarsys.capella.core.data.pa.PhysicalContext;

import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.DataModelElementInterpreter;
import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.FunctionalExchangeInterpretationProvider;
import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.FunctionalExchangeWithParents;
import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.PhysicalComponentInterpretationProvider;
import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.PhysicalFunctionInterpretationProvider;
import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.PhysicalFunctionWithParent;
import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.PhysicalLinkInterpretationProvider;
import com.n7space.capellatasteplugin.modelling.NameConverter;
import com.n7space.capellatasteplugin.modelling.architecture.Component;
import com.n7space.capellatasteplugin.modelling.architecture.ComponentExchange;
import com.n7space.capellatasteplugin.modelling.architecture.ComponentPort;
import com.n7space.capellatasteplugin.modelling.architecture.Direction;
import com.n7space.capellatasteplugin.modelling.architecture.Function;
import com.n7space.capellatasteplugin.modelling.architecture.FunctionExchange;
import com.n7space.capellatasteplugin.modelling.architecture.FunctionPort;
import com.n7space.capellatasteplugin.modelling.architecture.SystemModel;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.modelling.data.DataType;
import com.n7space.capellatasteplugin.modelling.data.DataTypeReference;
import com.n7space.capellatasteplugin.utils.Issue;
import com.n7space.capellatasteplugin.utils.Issue.Kind;

/**
 * Capella system model interpreter.
 *
 * Converts the Capella physical architecture model into an internal system
 * model understood by the rest of the plugin.
 */
public class CapellaSystemModelInterpreter {

	protected final PhysicalFunctionInterpretationProvider functionInterpreter;
	protected final PhysicalComponentInterpretationProvider componentInterpreter;
	protected final FunctionalExchangeInterpretationProvider exchangeInterpreter;
	protected final DataModelElementInterpreter elementInterpreter;
	protected final PhysicalLinkInterpretationProvider linkInterpreter;
	protected final NameConverter nameConverter;

	/**
	 * The constructor.
	 *
	 * @param dataModelElementInterpreter
	 *            Data model interpreter to be used for the interpretation of
	 *            encountered data types
	 * @param usedNameConverter
	 *            Name converter
	 */
	public CapellaSystemModelInterpreter(final DataModelElementInterpreter dataModelElementInterpreter,
			final NameConverter usedNameConverter) {
		elementInterpreter = dataModelElementInterpreter;
		functionInterpreter = new PhysicalFunctionInterpretationProvider();
		componentInterpreter = new PhysicalComponentInterpretationProvider();
		exchangeInterpreter = new FunctionalExchangeInterpretationProvider(elementInterpreter);
		linkInterpreter = new PhysicalLinkInterpretationProvider();
		nameConverter = usedNameConverter;
	}

	protected void addDefaultTasteAsn1Imports(final DataModel dataModel, final DataPackage dataViewPackage) {
		final DataPackage tasteBasicTypes = new DataPackage("TASTE-BasicTypes", "TASTE-BasicTypes");
		final DataType tint32 = new DataType(tasteBasicTypes, "T-Int32", "T-Int32");
		final DataType tuint32 = new DataType(tasteBasicTypes, "T-UInt32", "T-UInt32");
		final DataType tint8 = new DataType(tasteBasicTypes, "T-Int8", "T-Int8");
		final DataType tuint8 = new DataType(tasteBasicTypes, "T-UInt8", "T-UInt8");
		final DataType tboolean = new DataType(tasteBasicTypes, "T-Boolean", "T-Boolean");
		tasteBasicTypes.addTypeDeclaration(tint32);
		tasteBasicTypes.addTypeDeclaration(tuint32);
		tasteBasicTypes.addTypeDeclaration(tint8);
		tasteBasicTypes.addTypeDeclaration(tuint8);
		tasteBasicTypes.addTypeDeclaration(tboolean);

		dataViewPackage.addStandardImport(new DataTypeReference(tint32));
		dataViewPackage.addStandardImport(new DataTypeReference(tuint32));
		dataViewPackage.addStandardImport(new DataTypeReference(tint8));
		dataViewPackage.addStandardImport(new DataTypeReference(tuint8));
		dataViewPackage.addStandardImport(new DataTypeReference(tboolean));

		dataModel.dataPackages.add(tasteBasicTypes);
	}

	/**
	 * Converts the Capella physical architecture into an internal data model.
	 *
	 * @param systemRoot
	 *            Capella physical architecture
	 * @param dataModel
	 *            [output] Data model containing the used data types
	 * @param issues
	 *            [output] List of the issues detected during conversion
	 * @return The converted system model
	 */
	public SystemModel convertCapellaSystemModelToAbstractSystemModel(final PhysicalArchitecture systemRoot,
			final DataModel dataModel, final List<Issue> issues) {
		final SystemModel model = new SystemModel(systemRoot.getName(), systemRoot.getId(), dataModel);

		interpretModel(systemRoot, issues, model, dataModel);
		removeUnusedComponents(model, issues);
		detectNamingIssues(model, issues);
		detectInterfaceIssues(model, issues);
		performAdditionalChecksForExchanges(model, issues);
		return model;
	}

	protected boolean isInterNode(final FunctionExchange exchange, final List<Issue> issues) {
		try {
			final Function providingFunction = exchange.providingFunction;
			final Function requiringFunction = exchange.requiringFunction;
			final Component providingFunctionComponent = (Component) (providingFunction.getFirstParent());
			final Component requiringFunctionComponent = (Component) (requiringFunction.getFirstParent());
			final Component providingFunctionNode = providingFunctionComponent.getTopmostComponent();
			final Component requiringFunctionNode = requiringFunctionComponent.getTopmostComponent();
			return (!providingFunctionNode.id.equals(requiringFunctionNode.id));
		} catch (Exception e) {
			issues.add(new Issue(Kind.Error, "Deployment of " + exchange.name
					+ " exchange cannot be analysed due to an exception: " + e.toString(), exchange));
			return false;
		}
	}

	protected void performAdditionalChecksForExchanges(final SystemModel model, final List<Issue> issues) {
		for (FunctionExchange exchange : model.functionExchanges) {
			if (isInterNode(exchange, issues)) {
				if (exchange.exchangeKind != FunctionExchange.ExchangeKind.SPORADIC) {
					issues.add(new Issue(Kind.Error,
							"Exchange " + exchange.name + " is between different nodes and so must be sporadic",
							exchange));
				}
			}
		}
	}

	protected void detectInterfaceIssues(final SystemModel model, final List<Issue> issues) {
		for (final Function function : model.functions) {
			if (isActiveFunction(function)) {
				if (!hasInputPorts(function))
					issues.add(new Issue(Kind.Warning, "Function " + function.name + " does not have any input ports",
							function));
				if (!hasOutputPorts(function))
					issues.add(new Issue(Kind.Warning, "Function " + function.name + " does not have any output ports",
							function));
			}
		}
	}

	protected void detectNamingIssuesWithComponents(final SystemModel model, final HashSet<String> transformedNames,
			final List<Issue> issues) {
		for (final Component component : model.topLevelComponents) {
			final String transformedName = nameConverter.getAadlTypeName(component.name).toLowerCase();
			if (transformedNames.contains(transformedName))
				issues.add(new Issue(Kind.Warning, "Name " + component.name
						+ " is not unique when transformed for AADL use (" + transformedName + ")", component));
			transformedNames.add(transformedName);
			for (final ComponentPort port : component.ports) {
				final String transformedPortName = nameConverter.getAadlTypeName(port.name).toLowerCase();
				if (transformedNames.contains(transformedPortName))
					issues.add(new Issue(Kind.Warning, "Name " + port.name
							+ " is not unique when transformed for AADL use (" + transformedPortName + ")", port));
				transformedNames.add(transformedPortName);
			}
		}
		for (final ComponentExchange exchange : model.componentExchanges) {
			final String transformedName = nameConverter.getAadlTypeName(exchange.name).toLowerCase();
			if (transformedNames.contains(transformedName))
				issues.add(new Issue(Kind.Warning, "Name " + exchange.name
						+ " is not unique when transformed for AADL use (" + transformedName + ")", exchange));
			transformedNames.add(transformedName);
		}
	}

	protected void detectNamingIssuesWithFunctions(final SystemModel model, final HashSet<String> transformedNames,
			final List<Issue> issues) {
		for (final FunctionExchange exchange : model.functionExchanges) {
			final String transformedName = nameConverter.getAadlTypeName(exchange.name).toLowerCase();
			if (transformedNames.contains(transformedName))
				issues.add(new Issue(Kind.Warning, "Name " + exchange.name
						+ " is not unique when transformed for AADL use (" + transformedName + ")", exchange));
			transformedNames.add(transformedName);
		}
		for (final Function function : model.functions) {
			final String transformedName = nameConverter.getAadlTypeName(function.name).toLowerCase();
			if (transformedNames.contains(transformedName))
				issues.add(new Issue(Kind.Warning, "Name " + function.name
						+ " is not unique when transformed for AADL use (" + transformedName + ")", function));
			transformedNames.add(transformedName);
		}
	}

	protected void detectNamingIssues(final SystemModel model, final List<Issue> issues) {
		final HashSet<String> transformedNames = new HashSet<String>();
		detectNamingIssuesWithComponents(model, transformedNames, issues);
		detectNamingIssuesWithFunctions(model, transformedNames, issues);
	}

	protected boolean hasInputPorts(final Function function) {
		for (final FunctionPort port : function.ports)
			if (port.direction == Direction.IN)
				return true;
		return false;
	}

	protected boolean hasOutputPorts(final Function function) {
		for (final FunctionPort port : function.ports)
			if (port.direction == Direction.OUT)
				return true;
		return false;
	}

	protected void interpretModel(final PhysicalArchitecture systemRoot, final List<Issue> issues,
			final SystemModel model, final DataModel dataModel) {
		final List<PhysicalFunctionWithParent> discoveredFunctions = new LinkedList<PhysicalFunctionWithParent>();
		final List<FunctionalExchangeWithParents> discoveredFunctionalExchanges = new LinkedList<FunctionalExchangeWithParents>();
		final DataPackage dataViewPackage = new DataPackage("DataView", "DataView");

		dataModel.dataPackages.add(dataViewPackage);
		addDefaultTasteAsn1Imports(dataModel, dataViewPackage);

		final PhysicalComponent rootComponent = systemRoot.getOwnedPhysicalComponent();
		final PhysicalActorPkg actorPkg = systemRoot.getOwnedPhysicalActorPkg();
		final PhysicalContext context = systemRoot.getOwnedPhysicalContext();

		for (final PhysicalComponent component : rootComponent.getOwnedPhysicalComponents()) {
			componentInterpreter.convertCapellaComponentToAbstractComponentAndAttachItToSystemModel(model, model,
					component, discoveredFunctions, issues);
		}
		for (final PhysicalActor actor : actorPkg.getOwnedPhysicalActors()) {
			componentInterpreter.convertCapellaActorToAbstractComponentAndAttachItToSystemModel(model, model, actor,
					discoveredFunctions, issues);
		}

		for (final PhysicalFunctionWithParent functionWithParent : discoveredFunctions) {
			functionInterpreter.convertCapellaFunctionToAbstractFunctionAndAttachItToSystemModel(model,
					functionWithParent.parent, functionWithParent.function, discoveredFunctionalExchanges, issues);
		}
		final List<FunctionalExchangeWithParents> filteredExchanges = exchangeInterpreter
				.filterOutPartialOrphans(discoveredFunctionalExchanges);
		for (final FunctionalExchangeWithParents exchangeWithParent : filteredExchanges) {
			exchangeInterpreter.convertCapellaFunctionalExchangeToAbstractFunctionalExchangeAndAttachItToSystemModel(
					model, dataModel, dataViewPackage, exchangeWithParent.sourceFunction,
					exchangeWithParent.targetFunction, exchangeWithParent.exchange, issues);
		}

		for (final PhysicalLink link : rootComponent.getOwnedPhysicalLinks()) {
			linkInterpreter.convertCapellaPhysicalLinkToComponentExchangeAndAttachItToSystemModel(model, link, issues);
		}

		for (final PhysicalLink link : context.getOwnedPhysicalLinks()) {
			linkInterpreter.convertCapellaPhysicalLinkToComponentExchangeAndAttachItToSystemModel(model, link, issues);
		}

		for (final PhysicalPath path : rootComponent.getOwnedPhysicalPath()) {
			linkInterpreter.convertCapellaPhysicalPathToComponentExchangeAndAttachItToSystemModel(model, path, issues);
		}

		for (final PhysicalPath path : context.getOwnedPhysicalPath()) {
			linkInterpreter.convertCapellaPhysicalPathToComponentExchangeAndAttachItToSystemModel(model, path, issues);
		}

		functionInterpreter.attachFunctionalExchangesToFunctions(model.functions, model.functionExchanges);
		componentInterpreter.processComponentDeployment(model, issues);
	}

	protected boolean isActiveFunction(final Function function) {
		return function.language.toLowerCase().equals("sdl") || function.language.toLowerCase().equals("gui");
	}

	protected Set<Component> findComponentsToRemove(final SystemModel model) {
		final Set<Component> componentsToRemove = new HashSet<Component>();
		for (final Component component : model.components) {
			if (component.allocatedFunctions.size() == 0 && component.deployedComponents.size() == 0) {
				componentsToRemove.add(component);
			}
		}
		return componentsToRemove;
	}

	protected void removeIssuesForComponents(final Set<Component> components, final List<Issue> issues) {
		final Set<Issue> issuesToRemove = new HashSet<Issue>();
		for (final Component component : components) {
			for (final Issue issue : issues)
				if (issue.relevantObject.equals(component))
					issuesToRemove.add(issue);
		}
		for (final Issue issue : issuesToRemove)
			issues.remove(issue);
	}

	protected boolean performRemoveUnusedComponentsIteration(final SystemModel model, final List<Issue> issues) {
		final Set<Component> oldComponents = new HashSet<Component>();
		oldComponents.addAll(model.components);
		final Set<Component> componentsToRemove = findComponentsToRemove(model);

		for (final Component component : componentsToRemove)
			model.removeElementAndItsDependencies(component);

		final Set<Component> actuallyRemovedComponents = new HashSet<Component>();
		final Set<Component> currentComponents = new HashSet<Component>();
		currentComponents.addAll(model.components);

		for (final Component component : oldComponents)
			if (!currentComponents.contains(component))
				actuallyRemovedComponents.add(component);
		removeIssuesForComponents(actuallyRemovedComponents, issues);
		return componentsToRemove.size() > 0;
	}

	protected void removeUnusedComponents(final SystemModel model, final List<Issue> issues) {
		boolean tryRemove = true;
		while (tryRemove)
			tryRemove = performRemoveUnusedComponentsIteration(model, issues);
	}

}
