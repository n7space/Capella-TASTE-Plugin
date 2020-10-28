// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.architecture;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.n7space.capellatasteplugin.modelling.data.DataModel;

/**
 * Class representing an abstract system architecture model.
 *
 */
@XmlRootElement
public class SystemModel extends ArchitectureElement {

	/**
	 * Data model used for exchange parameters.
	 */
	public final DataModel dataModel;
	/**
	 * A list of components.
	 */
	@XmlTransient
	public final List<Component> components = new LinkedList<Component>();
	/**
	 * A list of functions.
	 */
	@XmlTransient
	public final List<Function> functions = new LinkedList<Function>();
	/**
	 * A list of function exchanges.
	 */
	@XmlTransient
	public final List<FunctionExchange> functionExchanges = new LinkedList<FunctionExchange>();
	/**
	 * A list of component exchanges.
	 */
	@XmlTransient
	public final List<ComponentExchange> componentExchanges = new LinkedList<ComponentExchange>();
	/**
	 * A list of top-level components.
	 */
	public final List<Component> topLevelComponents = new LinkedList<Component>();

	private SystemModel() {
		super((ArchitectureElement) null, "", "");
		dataModel = new DataModel();
	}

	/**
	 * A constructor.
	 *
	 * @param name
	 *            Architecture name
	 * @param id
	 *            ID
	 * @param data
	 *            Data model to be used
	 */
	public SystemModel(final String name, final String id, final DataModel data) {
		super((ArchitectureElement) null, name, id);
		dataModel = data;
	}

	/**
	 * Gets a component with the given ID.
	 * 
	 * @param id
	 *            ID of the component to be retrieved
	 * @return The sought component
	 */
	public Component getComponentById(final String id) {
		for (final Component component : components) {
			if (component.id.equals(id))
				return component;
		}
		return null;
	}

	/**
	 * Gets a component with the given name.
	 * 
	 * @param name
	 *            Name of the component to be retrieved
	 * @return The sought component
	 */
	public Component getComponentByName(final String name) {
		for (final Component component : components) {
			if (component.name.equals(name))
				return component;
		}
		return null;
	}

	/**
	 * Gets a component exchange with the given ID.
	 * 
	 * @param id
	 *            ID of the exchange to be retrieved
	 * @return The sought exchange
	 */
	public ComponentExchange getComponentExchangeById(final String id) {
		for (final ComponentExchange exchange : componentExchanges)
			if (exchange.id.equals(id))
				return exchange;

		return null;
	}

	/**
	 * Gets all components with the given name.
	 * 
	 * @param name
	 *            Name of the components to be retrieved
	 * @return A list of retrieved components
	 */
	public List<Component> getComponentsByName(final String name) {
		final List<Component> result = new LinkedList<Component>();
		for (final Component component : components) {
			if (component.name.equals(name))
				result.add(component);
		}
		return result;
	}

	/**
	 * Gets a function with the given ID.
	 * 
	 * @param id
	 *            ID of the function to be retrieved
	 * @return The sought function
	 */
	public Function getFunctionById(final String id) {
		for (final Function function : functions)
			if (function.id.equals(id))
				return function;

		return null;
	}

	/**
	 * Gets a function exchange with the given ID.
	 * 
	 * @param id
	 *            ID of the exchange to be retrieved
	 * @return The sought exchange
	 */
	public FunctionExchange getFunctionExchangeById(final String id) {
		for (final FunctionExchange exchange : functionExchanges)
			if (exchange.id.equals(id))
				return exchange;
		return null;
	}

	/**
	 * Remove element from the model together with all its dependencies.
	 * 
	 * @param elementToBeRemoved
	 *            Element to be removed
	 */
	public void removeElementAndItsDependencies(final ArchitectureElement elementToBeRemoved) {
		if (elementToBeRemoved instanceof Function)
			removeFunctionAndItsDependencies((Function) elementToBeRemoved);
		if (elementToBeRemoved instanceof Component)
			removeComponentAndItsDependencies((Component) elementToBeRemoved);
	}

	protected void removeComponentAndItsDependencies(final Component componentToBeRemoved) {
		components.remove(componentToBeRemoved);
		topLevelComponents.remove(componentToBeRemoved);
		final Set<ComponentExchange> exchangesToBeRemoved = new HashSet<ComponentExchange>();
		for (final Component component : components) {
			component.deployedComponents.remove(componentToBeRemoved);
			component.ownedComponents.remove(componentToBeRemoved);
		}

		// Remove the interacting exchanges
		for (final ComponentPort port : componentToBeRemoved.ports)
			exchangesToBeRemoved.add(port.exchange);
		for (final ComponentExchange exchange : exchangesToBeRemoved)
			removeExchangeAndItsDependencies(exchange);

		// Remove hosted functions
		final Set<Function> hostedFunctions = new HashSet<Function>();
		hostedFunctions.addAll(componentToBeRemoved.allocatedFunctions);
		for (final Function function : hostedFunctions)
			removeFunctionAndItsDependencies(function);

		removeCutOffFunctions();
		removeUnusedComponents();
	}

	protected void removeCutOffFunctions() {
		final Set<Function> unusedFunctions = new HashSet<Function>();
		for (final Function function : functions)
			if (function.ports.size() == 0)
				unusedFunctions.add(function);
		for (final Function function : unusedFunctions)
			removeFunctionAndItsDependencies(function);
	}

	protected void removeExchangeAndItsDependencies(final ComponentExchange exchangeToBeRemoved) {
		componentExchanges.remove(exchangeToBeRemoved);
		for (final Component component : components) {
			final Set<ComponentPort> portsToBeRemoved = new HashSet<ComponentPort>();
			for (final ComponentPort port : component.ports)
				if (port.exchange.equals(exchangeToBeRemoved))
					portsToBeRemoved.add(port);
			for (final ComponentPort port : portsToBeRemoved)
				component.ports.remove(port);
		}
		for (final FunctionExchange functionExchange : exchangeToBeRemoved.allocatedFunctionExchanges)
			removeExchangeAndItsDependencies(functionExchange);
	}

	protected void removeExchangeAndItsDependencies(final FunctionExchange exchangeToBeRemoved) {
		functionExchanges.remove(exchangeToBeRemoved);
		for (final Function function : functions) {
			final Set<FunctionPort> portsToBeRemoved = new HashSet<FunctionPort>();
			for (final FunctionPort port : function.ports)
				if (port.exchange.equals(exchangeToBeRemoved))
					portsToBeRemoved.add(port);
			for (final FunctionPort port : portsToBeRemoved)
				function.ports.remove(port);
		}
		for (final ComponentExchange exchange : componentExchanges)
			exchange.allocatedFunctionExchanges.remove(exchangeToBeRemoved);
		removeUnusedComponentExchanges();
	}

	protected void removeFunctionAndItsDependencies(final Function functionToBeRemoved) {
		functions.remove(functionToBeRemoved);
		final Set<FunctionExchange> exchangesToBeRemoved = new HashSet<FunctionExchange>();
		for (final Component component : components)
			component.allocatedFunctions.remove(functionToBeRemoved);

		// Remove the interacting exchanges
		for (final FunctionPort port : functionToBeRemoved.ports)
			exchangesToBeRemoved.add(port.exchange);
		for (final FunctionExchange exchange : exchangesToBeRemoved)
			removeExchangeAndItsDependencies(exchange);

		removeCutOffFunctions();
		removeUnusedComponents();
	}

	protected void removeUnusedComponentExchanges() {
		final Set<ComponentExchange> unusedExchanges = new HashSet<ComponentExchange>();
		for (final ComponentExchange exchange : componentExchanges)
			if (exchange.allocatedFunctionExchanges.size() == 0)
				unusedExchanges.add(exchange);
		for (final ComponentExchange exchange : unusedExchanges)
			removeExchangeAndItsDependencies(exchange);
	}

	protected void removeUnusedComponents() {
		final Set<Component> unusedComponents = new HashSet<Component>();
		for (final Component component : components)
			if ((component.allocatedFunctions.size() == 0) && (component.deployedComponents.size() == 0)
					&& (component.ownedComponents.size() == 0))
				unusedComponents.add(component);
		for (final Component component : unusedComponents)
			removeComponentAndItsDependencies(component);
	}

}
