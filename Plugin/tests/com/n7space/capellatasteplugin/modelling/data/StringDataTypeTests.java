// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.data;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Test;

import com.n7space.capellatasteplugin.modelling.data.DataType.DataTypeValue;
import com.n7space.capellatasteplugin.modelling.data.UsedNumericValue.ReferencedNumericValue;

public class StringDataTypeTests {

	@Test
	public void testGetDependencies() {
		final DataModel model = new DataModel();
		final DataPackage pkg = new DataPackage("Pkg", "Pkg" + "_id");
		model.dataPackages.add(pkg);

		final IntegerDataType cardinalityType = new IntegerDataType(pkg, "CardinalityType", "CardinalityType" + "_id");
		pkg.addTypeDefinition(cardinalityType);
		final StringDataType parentType = new StringDataType(pkg, "ParentType", "ParentType" + "_id");
		pkg.addTypeDefinition(parentType);

		final DataType.DataTypeValue minValue = new DataTypeValue(pkg, "Minimum", "MinimumId",
				new DataTypeReference(cardinalityType), "1");
		pkg.addValueDefinition(minValue);

		final UsedNumericValue.ReferencedNumericValue minValueRef = new ReferencedNumericValue(minValue);

		final DataType.DataTypeValue maxValue = new DataTypeValue(pkg, "Maximum", "MaximumId",
				new DataTypeReference(cardinalityType), "15");
		pkg.addValueDefinition(maxValue);

		final UsedNumericValue.ReferencedNumericValue maxValueRef = new ReferencedNumericValue(maxValue);

		final StringDataType type = new StringDataType(pkg, new DataTypeReference(parentType), "String",
				"String" + "_id");
		type.minLength = minValueRef;
		type.maxLength = maxValueRef;

		final Set<DataModelElement> dependencies = type.getDependencies(model);
		assertEquals(dependencies.size(), 3);
		assertTrue(dependencies.contains(minValue));
		assertTrue(dependencies.contains(maxValue));
		assertTrue(dependencies.contains(parentType));
	}

}
