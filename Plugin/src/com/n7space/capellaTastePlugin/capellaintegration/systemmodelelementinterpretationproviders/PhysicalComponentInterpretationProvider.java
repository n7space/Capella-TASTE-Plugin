// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.polarsys.capella.core.data.pa.AbstractPhysicalComponent;
import org.polarsys.capella.core.data.pa.PhysicalActor;
import org.polarsys.capella.core.data.pa.PhysicalComponent;
import org.polarsys.capella.core.data.pa.PhysicalComponentNature;
import org.polarsys.capella.core.data.pa.PhysicalFunction;

import com.n7space.capellatasteplugin.modelling.architecture.ArchitectureElement;
import com.n7space.capellatasteplugin.modelling.architecture.Component;
import com.n7space.capellatasteplugin.modelling.architecture.Property;
import com.n7space.capellatasteplugin.modelling.architecture.SystemModel;
import com.n7space.capellatasteplugin.utils.Issue;

/**
 * Class providing facilities for interpreting Capella Physical Components.
 *
 */
public class PhysicalComponentInterpretationProvider {

	protected final String DEPLOYMENT_PROPERTY = "PhysicalComponentInterpretationProvider.Deployment";

	/**
	 * Converts a Capella Physical Actor into an abstract Component and attaches it
	 * to the system model.
	 *
	 * @param model
	 *            System model
	 * @param parent
	 *            Parent architecture element
	 * @param root
	 *            Physical Actor to be converted
	 * @param discoveredFunctions
	 *            [output] Physical Functions discovered while interpreting the
	 *            actor
	 * @param issues
	 *            [output] Detected issues
	 * @return An abstract Component
	 */
	public Component convertCapellaActorToAbstractComponentAndAttachItToSystemModel(final SystemModel model,
			final ArchitectureElement parent, final PhysicalActor root,
			final List<PhysicalFunctionWithParent> discoveredFunctions, final List<Issue> issues) {
		final Component result = new Component(parent, root.getName(), root.getId(), true);
		setProcessor(result, root, issues);
		model.components.add(result);
		for (final PhysicalComponent child : root.getDeployedPhysicalComponents()) {
			final Component component = model.getComponentById(child.getId());
			if (component == null) {
				issues.add(new Issue(Issue.Kind.Error,
						"Component " + child.getName() + " definition cannot be found for actor " + result.name,
						result));
				continue;
			}
			result.deployedComponents.add(component);
		}

		return result;
	}

	/**
	 * Converts a Capella Physical Component to an abstract Component and attaches
	 * it to the system model.
	 *
	 * @param model
	 *            System model
	 * @param parent
	 *            Parent architecture element
	 * @param root
	 *            Physical Component to be converted
	 * @param discoveredFunctions
	 *            [output] Physical Functions discovered while interpreting the
	 *            actor
	 * @param issues
	 *            [output] Detected issues
	 * @return An abstract Component
	 */
	public Component convertCapellaComponentToAbstractComponentAndAttachItToSystemModel(final SystemModel model,
			final ArchitectureElement parent, final PhysicalComponent root,
			final List<PhysicalFunctionWithParent> discoveredFunctions, final List<Issue> issues) {
		final Component result = new Component(parent, root.getName(), root.getId(),
				root.getNature().equals(PhysicalComponentNature.NODE));
		recordDeployingEntitiesInProperties(root, result);
		setProcessor(result, root, issues);
		model.components.add(result);
		for (final PhysicalComponent child : root.getOwnedPhysicalComponents()) {
			result.ownedComponents.add(convertCapellaComponentToAbstractComponentAndAttachItToSystemModel(model, result,
					child, discoveredFunctions, issues));
		}
		for (final PhysicalFunction function : root.getAllocatedPhysicalFunctions()) {
			discoveredFunctions.add(new PhysicalFunctionWithParent(result, function));
		}
		return result;
	}

	/**
	 * Retrieves the gathered deployment relations from properties and recreates the
	 * deployment from the Capella model in the abstract model.
	 *
	 * @param model
	 *            System model
	 * @param issues
	 *            [output] Detected issues
	 */
	public void processComponentDeployment(final SystemModel model, final List<Issue> issues) {
		final Set<Component> deployedComponents = new HashSet<Component>();
		for (final Component deployedComponent : model.components) {
			final List<Property> deploymentProperties = deployedComponent.properties
					.getPropertiesOfType(DEPLOYMENT_PROPERTY);
			for (final Property deploymentProperty : deploymentProperties) {
				final String id = deploymentProperty.value;
				final Component location = model.getComponentById(id);
				if (location == null) {
					issues.add(new Issue(Issue.Kind.Warning,
							"Cannot find deployment target for " + deployedComponent.name, deployedComponent));
					continue;
				}
				location.deployedComponents.add(deployedComponent);
			}
		}

		for (final Component parent : model.components)
			deployedComponents.addAll(parent.deployedComponents);
		for (final Component component : model.components) {
			if (!deployedComponents.contains(component) && component.isNode)
				model.topLevelComponents.add(component);
		}
	}

	protected void recordDeployingEntitiesInProperties(final PhysicalComponent root, final Component component) {
		for (final PhysicalActor deployingActor : root.getDeployingPhysicalActors())
			component.properties.addProperty(new Property(DEPLOYMENT_PROPERTY, "", "", deployingActor.getId()));

		for (final PhysicalComponent deployingComponent : root.getDeployingPhysicalComponents())
			component.properties.addProperty(new Property(DEPLOYMENT_PROPERTY, "", "", deployingComponent.getId()));

	}

	protected void setProcessor(final Component component, final AbstractPhysicalComponent source,
			final List<Issue> issues) {
		if (!component.isNode)
			return;
		if (component.properties.getPropertiesOfType(DEPLOYMENT_PROPERTY).size() > 0)
			return; // Node is deployed onto something else.
		final String processorString = PropertyUtils.getProperty(source, CustomCapellaProperties.PROCESSOR);
		if (processorString == null || processorString.length() == 0) {
			issues.add(
					new Issue(Issue.Kind.Error, "Processor for node " + component.name + " is undefined", component));
			return;
		}
		component.processor.typeClass = processorString;
	}

}
