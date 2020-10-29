// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.data;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.n7space.capellatasteplugin.modelling.data.DataType.DataTypeValue;

public class DataModelTests {

	protected DataModel model;
	protected DataPackage pkg1;
	protected DataPackage pkg2;
	protected DataType type1;
	protected DataType type2;
	protected DataType.DataTypeValue value1;
	protected DataType.DataTypeValue value2;

	@Before
	public void setUp() throws Exception {
		model = new DataModel();
		pkg1 = new DataPackage("Pkg1", "Pkg1" + "_id");
		model.dataPackages.add(pkg1);
		type1 = new IntegerDataType(pkg1, "Type1", "Type1" + "_id");
		pkg1.addTypeDefinition(type1);
		value1 = new DataTypeValue(pkg1, "Value1", "Value1" + "_id", new DataTypeReference(type1), "12");
		pkg1.addValueDefinition(value1);
		pkg2 = new DataPackage("Pkg2", "Pkg2" + "_id");
		model.dataPackages.add(pkg2);
		type2 = new IntegerDataType(pkg2, "Type2", "Type2" + "_id");
		pkg2.addTypeDefinition(type2);
		value2 = new DataTypeValue(pkg2, "Value2", "Value2" + "_id", new DataTypeReference(type2), "13");
		pkg2.addValueDefinition(value2);
	}

	@Test
	public void testFindDataPackageById() {
		assertEquals(model.findDataPackageById(pkg1.id), pkg1);
		assertEquals(model.findDataPackageById(pkg2.id), pkg2);
	}

	@Test
	public void testFindDataTypeById() {
		assertEquals(model.findDataTypeById(type1.id), type1);
		assertEquals(model.findDataTypeById(type2.id), type2);
	}

	@Test
	public void testFindDataValueById() {
		assertEquals(model.findDataValueById(value1.id), value1);
		assertEquals(model.findDataValueById(value2.id), value2);
	}

}
