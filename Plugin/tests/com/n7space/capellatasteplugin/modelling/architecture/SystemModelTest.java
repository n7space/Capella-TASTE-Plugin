// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.architecture;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.n7space.capellatasteplugin.modelling.architecture.FunctionExchange.ExchangeType;

public class SystemModelTest {

	@Test
	public void testGetComponentById() {
		final SystemModel model = new SystemModel("Model", "Id", null);
		final Component c1 = new Component(null, "C1", "ID1", false);
		final Component c2 = new Component(null, "C2", "ID2", false);
		model.components.add(c1);
		model.components.add(c2);

		assertEquals(model.getComponentById("ID2"), c2);
		assertEquals(model.getComponentById("ID1"), c1);
	}

	@Test
	public void testGetComponentByName() {
		final SystemModel model = new SystemModel("Model", "Id", null);
		final Component c1 = new Component(null, "C1", "ID1", false);
		final Component c2 = new Component(null, "C2", "ID2", false);
		model.components.add(c1);
		model.components.add(c2);

		assertEquals(model.getComponentByName("C2"), c2);
		assertEquals(model.getComponentByName("C1"), c1);
	}

	@Test
	public void testGetComponentExchangeById() {
		final SystemModel model = new SystemModel("Model", "Id", null);
		final ComponentExchange e1 = new ComponentExchange(null, null, "E1", "EID1");
		final ComponentExchange e2 = new ComponentExchange(null, null, "E2", "EID2");
		model.componentExchanges.add(e1);
		model.componentExchanges.add(e2);

		assertEquals(model.getComponentExchangeById("EID2"), e2);
		assertEquals(model.getComponentExchangeById("EID1"), e1);
	}

	@Test
	public void testGetComponentsByName() {
		final SystemModel model = new SystemModel("Model", "Id", null);
		final Component c1 = new Component(null, "C1", "ID1", false);
		final Component c2 = new Component(null, "C2", "ID2", false);
		final Component c1Copy = new Component(null, "C1", "ID3", false);
		model.components.add(c1);
		model.components.add(c2);
		model.components.add(c1Copy);

		List<Component> components = model.getComponentsByName("C1");
		assertEquals(components.size(), 2);
		assertEquals(components.get(0), c1);
		assertEquals(components.get(1), c1Copy);
	}

	@Test
	public void testGetFunctionById() {
		final SystemModel model = new SystemModel("Model", "Id", null);
		final Function f1 = new Function(null, "F1", "ID1");
		final Function f2 = new Function(null, "F2", "ID2");
		model.functions.add(f1);
		model.functions.add(f2);

		assertEquals(model.getFunctionById("ID2"), f2);
		assertEquals(model.getFunctionById("ID1"), f1);
	}

	@Test
	public void testGetFunctionExchangeById() {
		final SystemModel model = new SystemModel("Model", "Id", null);
		final FunctionExchange e1 = new FunctionExchange(null, null, "E1", "EID1", ExchangeType.EVENT);
		final FunctionExchange e2 = new FunctionExchange(null, null, "E2", "EID2", ExchangeType.EVENT);
		model.functionExchanges.add(e1);
		model.functionExchanges.add(e2);

		assertEquals(model.getFunctionExchangeById("EID2"), e2);
		assertEquals(model.getFunctionExchangeById("EID1"), e1);
	}

	@Test
	public void testRemoveElementAndItsDependencies() {
		final SystemModel model = new SystemModel("Model", "Id", null);

		final Component n1 = new Component(null, "N1", "N1", true);
		final Component n2 = new Component(null, "N2", "N2", true);
		final Component n3 = new Component(null, "N3", "N3", true);
		model.components.add(n1);
		model.components.add(n2);
		model.components.add(n3);
		model.topLevelComponents.add(n1);
		model.topLevelComponents.add(n2);
		model.topLevelComponents.add(n3);

		final Component c1 = new Component(null, "C1", "C1", false);
		final Component c2 = new Component(null, "C2", "C2", false);
		final Component c3 = new Component(null, "C3", "C3", false);
		model.components.add(c1);
		model.components.add(c2);
		model.components.add(c3);
		n1.deployedComponents.add(c1);
		n2.deployedComponents.add(c2);
		n3.deployedComponents.add(c3);

		final Function f1 = new Function(c1, "F1", "F1");
		final Function f2 = new Function(c2, "F2", "F2");
		final Function f3 = new Function(c3, "F3", "F3");
		model.functions.add(f1);
		model.functions.add(f2);
		model.functions.add(f3);
		c1.allocatedFunctions.add(f1);
		c2.allocatedFunctions.add(f2);
		c3.allocatedFunctions.add(f3);

		final FunctionExchange fe1 = new FunctionExchange(f1, f2, "FE1", "FE1", ExchangeType.EVENT);
		final FunctionExchange fe2 = new FunctionExchange(f2, f3, "FE2", "FE2", ExchangeType.EVENT);
		model.functionExchanges.add(fe1);
		model.functionExchanges.add(fe2);

		final ComponentExchange ce1 = new ComponentExchange(n1, n2, "CE1", "CE1");
		final ComponentExchange ce2 = new ComponentExchange(n2, n3, "CE2", "CE2");
		model.componentExchanges.add(ce1);
		model.componentExchanges.add(ce2);
		ce1.allocatedFunctionExchanges.add(fe1);
		ce2.allocatedFunctionExchanges.add(fe2);

		n1.ports.add(new ComponentPort(n1, "N1P1", "N1P1", ce1));
		n2.ports.add(new ComponentPort(n2, "N2P1", "N2P1", ce1));
		n2.ports.add(new ComponentPort(n2, "N2P2", "N2P2", ce2));
		n3.ports.add(new ComponentPort(n3, "N3P2", "N3P2", ce2));
		f1.ports.add(new FunctionPort(f1, "F1P1", "F1P1", Direction.OUT, fe1));
		f2.ports.add(new FunctionPort(f2, "F2P1", "F2P1", Direction.IN, fe1));
		f2.ports.add(new FunctionPort(f2, "F2P2", "F2P2", Direction.OUT, fe2));
		f3.ports.add(new FunctionPort(f3, "F1P1", "F3P1", Direction.IN, fe2));

		model.removeElementAndItsDependencies(c3);

		assertEquals(model.components.size(), 4);
		assertEquals(model.functions.size(), 2);
		assertEquals(model.componentExchanges.size(), 1);
		assertEquals(model.functionExchanges.size(), 1);

		assertEquals(model.functionExchanges.get(0), fe1);
		assertEquals(model.componentExchanges.get(0), ce1);
	}

}
