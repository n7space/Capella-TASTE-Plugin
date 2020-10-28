// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

public class FloatDataTypeTests {

	@Test
	public void testGetDependencies() {
		final DataModel model = new DataModel();
		final DataPackage pkg = new DataPackage("Pkg", "Pkg" + "_id");
		model.dataPackages.add(pkg);

		final StringDataType parentType = new StringDataType(pkg, "ParentType", "ParentType" + "_id");
		pkg.addTypeDefinition(parentType);

		final FloatDataType type = new FloatDataType(pkg, new DataTypeReference(parentType), "Float", "Float" + "_id");

		final Set<DataModelElement> dependencies = type.getDependencies(model);
		assertEquals(dependencies.size(), 1);
		assertTrue(dependencies.contains(parentType));
	}

}
