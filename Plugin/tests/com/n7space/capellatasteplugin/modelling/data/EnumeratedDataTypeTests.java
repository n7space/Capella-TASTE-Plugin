// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.data;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.n7space.capellatasteplugin.modelling.data.DataType.DataTypeValue;

public class EnumeratedDataTypeTests {

	protected DataModel model;
	protected DataPackage dataPkg;
	protected EnumeratedDataType enumeratedDataType;
	protected EnumeratedDataType.Literal literal1;
	protected EnumeratedDataType.Literal literal2;
	protected DataType.DataTypeValue value1;
	protected DataType.DataTypeValue value2;

	protected IntegerDataType integerType;

	@Before
	public void setUp() throws Exception {

		model = new DataModel();

		dataPkg = new DataPackage("Pkg", "Pkg" + "_id");
		model.dataPackages.add(dataPkg);
		integerType = new IntegerDataType(dataPkg, "Int", "Int" + "_id");
		dataPkg.addTypeDefinition(integerType);

		enumeratedDataType = new EnumeratedDataType(dataPkg, "Enumerated", "Enumerated" + "_id");
		dataPkg.addTypeDefinition(enumeratedDataType);

		value1 = new DataTypeValue(dataPkg, "val1", "val1" + "_id", new DataTypeReference(integerType),
				Integer.valueOf(1));
		dataPkg.addValueDefinition(value1);
		value2 = new DataTypeValue(dataPkg, "val2", "val2" + "_id", new DataTypeReference(integerType),
				Integer.valueOf(2));
		dataPkg.addValueDefinition(value2);

		literal1 = new EnumeratedDataType.Literal(enumeratedDataType, "Literal1", "Literal1" + "_id");
		literal1.domainValue = value1;

		literal2 = new EnumeratedDataType.Literal(enumeratedDataType, "Literal2", "Literal2" + "_id");
		literal2.domainValue = value2;

		enumeratedDataType.literals.add(literal1);
		enumeratedDataType.literals.add(literal2);

	}

	@Test
	public void testGetDependencies() {
		final Set<DataModelElement> dependencies = enumeratedDataType.getDependencies(model);
		assertTrue(dependencies.contains(value1));
		assertTrue(dependencies.contains(value2));
	}

}
