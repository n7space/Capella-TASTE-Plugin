// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders;

import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.testmocks.FunctionalExchangeMock;
import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.testmocks.PhysicalFunctionMock;
import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.testmocks.StringPropertyMock;
import com.n7space.capellatasteplugin.modelling.architecture.Component;
import com.n7space.capellatasteplugin.modelling.architecture.Direction;
import com.n7space.capellatasteplugin.modelling.architecture.Function;
import com.n7space.capellatasteplugin.modelling.architecture.FunctionExchange;
import com.n7space.capellatasteplugin.modelling.architecture.SystemModel;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.utils.Issue;

public class PhysicalFunctionInterpretationProviderTest {

	@Test
	public void testAttachFunctionalExchangesToFunctions() {
		final PhysicalFunctionInterpretationProvider provider = new PhysicalFunctionInterpretationProvider();
		final List<Function> functions = new LinkedList<Function>();
		final List<FunctionExchange> exchanges = new LinkedList<FunctionExchange>();

		final Function f1 = new Function(null, "F1", "1");
		final Function f2 = new Function(null, "F2", "2");
		final Function f3 = new Function(null, "F3", "3");

		functions.add(f1);
		functions.add(f2);
		functions.add(f3);

		final FunctionExchange ea = new FunctionExchange(f1, f2, "EA", "A", FunctionExchange.ExchangeType.EVENT);
		final FunctionExchange eb = new FunctionExchange(f2, f3, "EB", "B", FunctionExchange.ExchangeType.EVENT);
		final FunctionExchange ec = new FunctionExchange(f3, f1, "EC", "C", FunctionExchange.ExchangeType.EVENT);

		exchanges.add(ea);
		exchanges.add(eb);
		exchanges.add(ec);

		provider.attachFunctionalExchangesToFunctions(functions, exchanges);

		assertTrue(f1.ports.size() == 2);
		assertTrue(f1.ports.get(0).direction == Direction.OUT);
		assertTrue(f1.ports.get(0).exchange.equals(ea));
		assertTrue(f1.ports.get(1).direction == Direction.IN);
		assertTrue(f1.ports.get(1).exchange.equals(ec));
		assertTrue(f2.ports.size() == 2);
		assertTrue(f2.ports.get(1).direction == Direction.OUT);
		assertTrue(f2.ports.get(1).exchange.equals(eb));
		assertTrue(f2.ports.get(0).direction == Direction.IN);
		assertTrue(f2.ports.get(0).exchange.equals(ea));
		assertTrue(f3.ports.size() == 2);
		assertTrue(f3.ports.get(1).direction == Direction.OUT);
		assertTrue(f3.ports.get(1).exchange.equals(ec));
		assertTrue(f3.ports.get(0).direction == Direction.IN);
		assertTrue(f3.ports.get(0).exchange.equals(eb));
	}

	@Test
	public void testConvertCapellaFunctionToAbstractFunctionAndAttachItToSystemModel() {
		final PhysicalFunctionInterpretationProvider provider = new PhysicalFunctionInterpretationProvider();
		final FunctionalExchangeInterpretationProvider exchangeInterpretationProvider = new FunctionalExchangeInterpretationProvider(
				null);
		final List<Issue> issues = new LinkedList<Issue>();
		final DataModel dataModel = new DataModel();
		final SystemModel systemModel = new SystemModel("TestModel", "TestId", dataModel);
		final List<FunctionalExchangeWithParents> discoveredFunctionalExchanges = new LinkedList<FunctionalExchangeWithParents>();

		final Component parentComponent = new Component(null, "MockedComponent", "MockedComponentId", false);
		final PhysicalFunctionMock mockedFunction1 = new PhysicalFunctionMock("MockedFunction1");
		final PhysicalFunctionMock mockedFunction2 = new PhysicalFunctionMock("MockedFunction2");
		final FunctionalExchangeMock mockedExchange = new FunctionalExchangeMock("Exchange");
		mockedFunction1.addOutgoingEdge(mockedExchange);
		mockedFunction1.addAppliedPropertyValue(new StringPropertyMock("Taste::Language", "C"));
		mockedFunction2.addIncomingEdge(mockedExchange);
		mockedFunction2.addAppliedPropertyValue(new StringPropertyMock("Taste::Language", "SDL"));
		mockedFunction2.addAppliedPropertyValue(new StringPropertyMock("Taste::Timer", "MyTimer"));

		provider.convertCapellaFunctionToAbstractFunctionAndAttachItToSystemModel(systemModel, parentComponent,
				mockedFunction1, discoveredFunctionalExchanges, issues);
		provider.convertCapellaFunctionToAbstractFunctionAndAttachItToSystemModel(systemModel, parentComponent,
				mockedFunction2, discoveredFunctionalExchanges, issues);

		final List<FunctionalExchangeWithParents> filteredExchanges = exchangeInterpretationProvider
				.filterOutPartialOrphans(discoveredFunctionalExchanges);

		assertTrue(systemModel.functions.size() == 2);
		assertTrue(systemModel.functions.get(0).name.equals("MockedFunction1"));
		assertTrue(systemModel.functions.get(1).name.equals("MockedFunction2"));
		assertTrue(systemModel.functions.get(1).timerNames.get(0).equals("MyTimer"));
		assertTrue(filteredExchanges.size() == 1);
		assertTrue(filteredExchanges.get(0).exchange.getName().equals("Exchange"));
		assertTrue(filteredExchanges.get(0).sourceFunction.name.equals("MockedFunction1"));
		assertTrue(filteredExchanges.get(0).targetFunction.name.equals("MockedFunction2"));
		assertTrue(issues.size() == 0);
	}

}
