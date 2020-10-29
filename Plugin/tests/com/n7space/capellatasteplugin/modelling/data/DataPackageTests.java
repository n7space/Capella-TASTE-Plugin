// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.n7space.capellatasteplugin.modelling.data.DataType.DataTypeValue;

public class DataPackageTests {

	protected DataPackage pkg;
	protected IntegerDataType dataType;
	protected DataType.DataTypeValue dataValue;

	@Before
	public void setUp() throws Exception {
		pkg = new DataPackage("Pkg", "Pkg" + "_id");
		dataType = new IntegerDataType(pkg, "IntegerType", "IntegerType" + "_id");
		dataValue = new DataTypeValue(pkg, "IntegerValue", "IntegerValue" + "_id", new DataTypeReference(dataType),
				"16");
	}

	@Test
	public void testAddTypeDeclaration() {
		assertEquals(pkg.definedDataTypes.size(), 0);
		assertEquals(pkg.declaredDataTypes.size(), 0);

		pkg.addTypeDeclaration(dataType);

		assertEquals(pkg.declaredDataTypes.size(), 1);
		assertEquals(pkg.declaredDataTypes.get(0), dataType);
		assertEquals(pkg.definedDataTypes.size(), 0);
	}

	@Test
	public void testAddTypeDefinition() {
		assertEquals(pkg.definedDataTypes.size(), 0);
		assertEquals(pkg.declaredDataTypes.size(), 0);

		pkg.addTypeDefinition(dataType);

		assertEquals(pkg.declaredDataTypes.size(), 1);
		assertEquals(pkg.declaredDataTypes.get(0), dataType);
		assertEquals(pkg.definedDataTypes.size(), 1);
		assertEquals(pkg.definedDataTypes.get(0), dataType);
	}

	@Test
	public void testAddValueDefinition() {
		assertEquals(pkg.definedValues.size(), 0);

		pkg.addValueDefinition(dataValue);

		assertEquals(pkg.definedValues.size(), 1);
		assertEquals(pkg.definedValues.get(0), dataValue);
	}

	@Test
	public void testCloneSubset() {
		final IntegerDataType dataType1 = new IntegerDataType(pkg, "IntegerType1", "IntegerType1" + "_id");
		final IntegerDataType dataType2 = new IntegerDataType(pkg, "IntegerType2", "IntegerType2" + "_id");
		pkg.addTypeDefinition(dataType1);
		pkg.addTypeDefinition(dataType2);

		final List<DataModelElement> elementsToClone = new LinkedList<DataModelElement>();
		elementsToClone.add(dataType1);

		final DataPackage clonedPkg = pkg.cloneSubset(elementsToClone);
		assertEquals(clonedPkg.definedDataTypes.size(), 1);
		assertEquals(clonedPkg.definedDataTypes.get(0), dataType1);
	}

	@Test
	public void testContainsTypeDeclarationDataType() {
		pkg.addTypeDeclaration(dataType);
		final IntegerDataType undefinedType = new IntegerDataType(pkg, "undefinedType", "undefinedType" + "_id");

		assertTrue(pkg.containsTypeDeclaration(dataType));
		assertFalse(pkg.containsTypeDeclaration(undefinedType));
	}

	@Test
	public void testContainsTypeDeclarationString() {
		pkg.addTypeDeclaration(dataType);

		assertTrue(pkg.containsTypeDeclaration(dataType.id));
		assertFalse(pkg.containsTypeDeclaration("InvalidId"));
	}

	@Test
	public void testContainsTypeDefinition() {
		pkg.addTypeDefinition(dataType);
		final IntegerDataType undefinedType = new IntegerDataType(pkg, "undefinedType", "undefinedType" + "_id");

		assertTrue(pkg.containsTypeDefinition(dataType));
		assertFalse(pkg.containsTypeDefinition(undefinedType));
	}

	@Test
	public void testContainsValueDefinition() {
		pkg.addValueDefinition(dataValue);
		final DataType.DataTypeValue undefinedValue = new DataTypeValue(pkg, "Undefined", "Undefined" + "_id", null,
				null);

		assertTrue(pkg.containsValueDefinition(dataValue));
		assertFalse(pkg.containsValueDefinition(undefinedValue));

	}

	@Test
	public void testGetAllDefinedElements() {
		pkg.addTypeDefinition(dataType);
		pkg.addValueDefinition(dataValue);

		final List<DataModelElement> elements = pkg.getDefinedElements();
		assertEquals(elements.size(), 2);
		assertTrue(elements.contains(dataType));
		assertTrue(elements.contains(dataValue));
	}

	/*
	 * @Test public void testGetDeclaredTypes() { fail("Not yet implemented"); }
	 * 
	 * @Test public void testGetDefinedTypes() { fail("Not yet implemented"); }
	 * 
	 * @Test public void testGetDefinedValues() { fail("Not yet implemented"); }
	 */

}
