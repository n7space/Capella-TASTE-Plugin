// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.architecture;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ComponentTest {

	@Test
	public void testGetTopmostComponent() {
		final Component l1 = new Component(null, "L1", "L1", true);
		final Component l2 = new Component(l1, "L2", "L2", false);
		final Component l3 = new Component(l2, "L3", "L3", false);

		assertEquals(l1, l2.getTopmostComponent());
		assertEquals(l1, l3.getTopmostComponent());
	}

	@Test
	public void testRemovePortForExchange() {
		final Component component = new Component(null, "C", "CID", false);
		final Component otherComponent = new Component(null, "OC", "OCID", false);
		final ComponentExchange exchangeToBeRemoved = new ComponentExchange(component, otherComponent, "ToBeRemoved",
				"ToBeRemovedId");
		final ComponentExchange exchangeToStay = new ComponentExchange(component, otherComponent, "ToStay", "ToStayId");
		final ComponentPort portToBeRemoved = new ComponentPort(component, "PortToRemove", "PortToRemoveId",
				exchangeToBeRemoved);
		final ComponentPort portToStay = new ComponentPort(component, "PortToStay", "PortToStayId", exchangeToStay);
		component.ports.add(portToBeRemoved);
		component.ports.add(portToStay);
		component.removePortForExchange(exchangeToBeRemoved);
		assertEquals(component.ports.size(), 1);
		assertEquals(component.ports.get(0).name, "PortToStay");
	}

}
