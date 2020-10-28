// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.architecture;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class PropertyGroupTest {

	@Test
	public void testGetPropertiesOfType() {
		final PropertyGroup group = new PropertyGroup();
		group.addProperty(new Property("TypeA", "P1", "ID1", "Val"));
		group.addProperty(new Property("TypeB", "P2", "ID2", "Val"));
		group.addProperty(new Property("TypeA", "P3", "ID3", "Val"));

		List<Property> properties = group.getPropertiesOfType("TypeA");
		assertEquals(properties.size(), 2);
		assertEquals(properties.get(0).id, "ID1");
		assertEquals(properties.get(1).id, "ID3");
	}

	@Test
	public void testGetPropertyById() {
		final PropertyGroup group = new PropertyGroup();
		group.addProperty(new Property("TypeA", "P1", "ID1", "Val"));
		group.addProperty(new Property("TypeB", "P2", "ID2", "Val"));
		group.addProperty(new Property("TypeA", "P3", "ID3", "Val"));

		Property property = group.getPropertyById("ID2");
		assertEquals(property.name, "P2");
	}

}
