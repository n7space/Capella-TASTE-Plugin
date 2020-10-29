// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders;

import java.util.List;

import org.polarsys.capella.common.data.activity.ActivityEdge;
import org.polarsys.capella.core.data.fa.FunctionalExchange;
import org.polarsys.capella.core.data.pa.PhysicalFunction;

import com.n7space.capellatasteplugin.modelling.architecture.ArchitectureElement;
import com.n7space.capellatasteplugin.modelling.architecture.Component;
import com.n7space.capellatasteplugin.modelling.architecture.Direction;
import com.n7space.capellatasteplugin.modelling.architecture.Function;
import com.n7space.capellatasteplugin.modelling.architecture.FunctionExchange;
import com.n7space.capellatasteplugin.modelling.architecture.FunctionPort;
import com.n7space.capellatasteplugin.modelling.architecture.SystemModel;
import com.n7space.capellatasteplugin.utils.Issue;
import com.n7space.capellatasteplugin.utils.Issue.Kind;

/**
 * Class providing facilities for interpreting Physical Functions.
 *
 */
public class PhysicalFunctionInterpretationProvider {

	/**
	 * Attach exchanges to functions.
	 *
	 * @param functions
	 *            Attachment targets
	 * @param exchanges
	 *            Exchanges to be attached
	 */
	public void attachFunctionalExchangesToFunctions(final Iterable<Function> functions,
			final Iterable<FunctionExchange> exchanges) {
		for (final Function function : functions) {
			for (final FunctionExchange exchange : exchanges) {
				if (exchange.requiringFunction.id.equals(function.id)) {
					final FunctionPort port = new FunctionPort(function, getPortName(function, exchange),
							getPortId(function, exchange), Direction.OUT, exchange);
					function.ports.add(port);
				}
				if (exchange.providingFunction.id.equals(function.id)) {
					final FunctionPort port = new FunctionPort(function, getPortName(function, exchange),
							getPortId(function, exchange), Direction.IN, exchange);
					function.ports.add(port);
				}
			}
		}
	}

	/**
	 * Converts Capella Function to an abstract Function and attaches it to the
	 * system model.
	 *
	 * @param model
	 *            System model
	 * @param parent
	 *            Parent architecture element
	 * @param function
	 *            Physical Function to be interpreted
	 * @param discoveredFunctionalExchanges
	 *            [output] Discovered Functional Exchanges
	 * @param issues
	 *            [output] Detected issues
	 * @return An abstract Function
	 */
	public Function convertCapellaFunctionToAbstractFunctionAndAttachItToSystemModel(final SystemModel model,
			final Component parent, final PhysicalFunction function,
			final List<FunctionalExchangeWithParents> discoveredFunctionalExchanges, final List<Issue> issues) {
		final Function result = new Function(parent, function.getName(), function.getId());
		setLanguage(result, function, issues);
		getAllTimers(result, function, issues);
		getAllDirectives(result, function);

		for (final ActivityEdge edge : function.getIncoming()) {
			if (edge instanceof FunctionalExchange) {
				final FunctionalExchange exchange = (FunctionalExchange) edge;
				discoveredFunctionalExchanges.add(new FunctionalExchangeWithParents(null, result, exchange));
			}
		}

		for (final ActivityEdge edge : function.getOutgoing()) {
			if (edge instanceof FunctionalExchange) {
				final FunctionalExchange exchange = (FunctionalExchange) edge;
				discoveredFunctionalExchanges.add(new FunctionalExchangeWithParents(result, null, exchange));
			}
		}

		final Component component = getOwningComponent(parent);
		if (component != null)
			component.allocatedFunctions.add(result);
		model.functions.add(result);
		return result;
	}

	protected void getAllDirectives(final Function function, final PhysicalFunction source) {
		final List<String> directives = PropertyUtils.getProperties(source, CustomCapellaProperties.TASTE_DIRECTIVE);
		function.directives.addAll(directives);
	}

	protected void getAllTimers(final Function function, final PhysicalFunction source, final List<Issue> issues) {
		final List<String> timerNames = PropertyUtils.getProperties(source, CustomCapellaProperties.TIMER);
		for (final String timerName : timerNames)
			function.timerNames.add(timerName);
		if (!function.language.toLowerCase().equals("sdl") && function.timerNames.size() > 0) {
			issues.add(new Issue(Kind.Warning,
					"Function " + function.name + " defines timers but its language is not SDL", function));
			function.timerNames.clear();
		}
	}

	protected Component getOwningComponent(final ArchitectureElement parent) {
		ArchitectureElement element = parent;
		while (element != null) {
			if (element instanceof Component)
				return (Component) element;
			else if (element instanceof Function)
				element = ((Function) element).getFirstParent();
			else
				element = null;

		}
		return null;
	}

	protected String getPortId(final Function function, final FunctionExchange exchange) {
		return function.id + "-" + exchange.id;
	}

	protected String getPortName(final Function function, final FunctionExchange exchange) {
		return function.name + "-" + exchange.name;
	}

	protected void setLanguage(final Function function, final PhysicalFunction source, final List<Issue> issues) {
		final String languageString = PropertyUtils.getProperty(source, CustomCapellaProperties.LANGUAGE);

		if (languageString == null || languageString.length() == 0) {
			issues.add(
					new Issue(Issue.Kind.Error, "Language for function " + function.name + " is undefined", function));
			return;
		}
		function.language = languageString;
	}

}
