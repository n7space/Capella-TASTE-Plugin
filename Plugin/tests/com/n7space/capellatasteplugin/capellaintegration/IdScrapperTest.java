// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;

import org.junit.Test;

import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.testmocks.PhysicalComponentMock;
import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.testmocks.PhysicalFunctionMock;

public class IdScrapperTest {

	protected Object createMockSystem() {
		final PhysicalComponentMock root = new PhysicalComponentMock("N1");
		final PhysicalComponentMock child = new PhysicalComponentMock("C1");
		child.addDeployingComponent(root);
		final PhysicalFunctionMock function = new PhysicalFunctionMock("F1");
		child.addAllocatedPhysicalFunction(function);
		root.addOwnedPhysicalComponent(child);
		return root;
	}

	@Test
	public void testGetAllIdsObject() {
		HashSet<String> ids = IdScrapper.getAllIds(createMockSystem());
		assertEquals(ids.size(), 3);
		assertTrue(ids.contains("N1Id"));
		assertTrue(ids.contains("C1Id"));
		assertTrue(ids.contains("F1Id"));
	}

	@Test
	public void testGetPathToIdObjectString() {
		List<String> ids = IdScrapper.getPathToId(createMockSystem(), "F1Id");
		assertTrue(ids.get(0).equals("N1"));
		assertTrue(ids.get(1).equals("->getOwnedPhysicalComponents()"));
		assertTrue(ids.get(2).equals("C1"));
		assertTrue(ids.get(3).equals("->getAllocatedPhysicalFunctions()"));
		assertTrue(ids.get(4).equals("F1"));
	}

}
