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

import com.n7space.capellatasteplugin.modelling.data.DataType.DataTypeValue;

public class DataTypeTests {

	@Test
	public void testDataTypeValueGetDependencies() {
		final DataModel model = new DataModel();
		final DataPackage pkg = new DataPackage("Pkg", "Pkg" + "_id");
		model.dataPackages.add(pkg);

		final IntegerDataType type = new IntegerDataType(pkg, "DataType", "DataType" + "_id");
		pkg.addTypeDefinition(type);

		final DataType.DataTypeValue value = new DataTypeValue(pkg, "Value", "Value" + "_id",
				new DataTypeReference(type), "1");
		pkg.addValueDefinition(value);

		final Set<DataModelElement> dependencies = value.getDependencies(model);
		assertEquals(dependencies.size(), 1);
		assertTrue(dependencies.contains(type));
	}

}
