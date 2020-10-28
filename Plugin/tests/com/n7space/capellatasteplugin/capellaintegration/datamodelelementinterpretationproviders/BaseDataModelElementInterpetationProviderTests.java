// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.polarsys.capella.common.data.modellingcore.AbstractType;
import org.polarsys.capella.core.data.capellacore.Type;
import org.polarsys.capella.core.data.information.DataPkg;
import org.polarsys.capella.core.data.information.Unit;
import org.polarsys.capella.core.data.information.datatype.NumericType;
import org.polarsys.capella.core.data.information.datatype.NumericTypeKind;
import org.polarsys.capella.core.data.information.datavalue.NumericValue;

import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.BaseTypeMock;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.DataPackageMock;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.NumericTypeMock;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.NumericTypeMock.NumericValueMock;
import com.n7space.capellatasteplugin.modelling.NameConverter;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.modelling.data.DataType;
import com.n7space.capellatasteplugin.modelling.data.DataType.DataTypeValue;
import com.n7space.capellatasteplugin.modelling.data.DataTypeReference;
import com.n7space.capellatasteplugin.modelling.data.IntegerDataType;
import com.n7space.capellatasteplugin.modelling.data.UsedNumericValue;
import com.n7space.capellatasteplugin.utils.Issue;

public class BaseDataModelElementInterpetationProviderTests {

	protected static class NakedNumericValueMock extends BaseTypeMock implements NumericValue {

		protected final NumericType type;

		public NakedNumericValueMock(final DataPkg pkg, final NumericTypeMock valueType, final String name,
				final String id) {
			super(pkg, name, id);
			type = valueType;
		}

		@Override
		public AbstractType getAbstractType() {
			return type;
		}

		@Override
		public NumericType getNumericType() {
			return type;
		}

		@Override
		public Type getType() {
			return type;
		}

		@Override
		public Unit getUnit() {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public void setAbstractType(final AbstractType value) {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public void setUnit(final Unit value) {
			throw new RuntimeException("Not implemented");
		}

	}

	protected BaseDataModelElementInterpetationProvider provider;
	protected List<Issue> issues;
	protected DataPackageMock pkg;
	protected NumericTypeMock numericType;
	protected NumericValueMock numericValue16;
	protected NumericValueMock numericValueStar;
	protected NumericValueMock numericValueInvalid;
	protected DataModel model;
	protected NameConverter nameConverter;

	protected void addRegisterDataTypesInDataModel() {
		final DataPackage dataPackage = new DataPackage("Pkg", "Pkg" + "_id");
		model.dataPackages.add(dataPackage);
		final IntegerDataType integerType = new IntegerDataType(dataPackage, "NumericType", "NumericType" + "_id");
		dataPackage.addTypeDefinition(integerType);
		final DataType.DataTypeValue value16 = new DataTypeValue(dataPackage, "NumericValue16",
				"NumericValue16" + "_id", new DataTypeReference(integerType), "16");
		dataPackage.addValueDefinition(value16);
		final DataType.DataTypeValue valueStar = new DataTypeValue(dataPackage, "NumericValueStar",
				"NumericValueStar" + "_id", new DataTypeReference(integerType), "*");
		dataPackage.addValueDefinition(valueStar);
	}

	@Before
	public void setUp() throws Exception {
		nameConverter = new NameConverter();
		provider = new BaseDataModelElementInterpetationProvider();
		model = new DataModel();
		issues = new LinkedList<Issue>();
		pkg = new DataPackageMock("Pkg", "Pkg" + "_id");
		numericType = new NumericTypeMock(pkg, "NumericType", "NumericType" + "_id", NumericTypeKind.INTEGER);
		numericValue16 = new NumericValueMock(pkg, "NumericValue16", "NumericValue16" + "_id", numericType, "16");
		numericValueStar = new NumericValueMock(pkg, "NumericValueStar", "NumericValueStar" + "_id", null, "*");
		numericValueInvalid = new NumericValueMock(pkg, "NumericValueInvalid", "NumericValueInvalid" + "_id",
				numericType, "DEADBEEF");
	}

	@Test
	public void testGetUsedIntegerValue_invalidSpecialValue() {
		final UsedNumericValue value = provider.getUsedNumericValue(model, numericValueInvalid, issues);
		assertNull(value);
		assertEquals(issues.size(), 1);
	}

	@Test
	public void testGetUsedIntegerValue_referencedValue() {
		addRegisterDataTypesInDataModel();
		final UsedNumericValue value = provider.getUsedNumericValue(model, numericValue16, issues);
		assertTrue(value instanceof UsedNumericValue.ReferencedNumericValue);
		assertEquals(value.getValue(nameConverter), "numericValue16");
	}

	@Test
	public void testGetUsedIntegerValue_specialValue() {
		final UsedNumericValue value = provider.getUsedNumericValue(model, numericValueStar, issues);
		assertTrue(value instanceof UsedNumericValue.SpecialNumericValue);
		assertEquals(value.getValue(nameConverter), "MAX");
	}

	@Test
	public void testGetUsedIntegerValue_unhandledReferencedValue() {
		addRegisterDataTypesInDataModel();
		final UsedNumericValue value = provider.getUsedNumericValue(model,
				new NakedNumericValueMock(pkg, numericType, "Naked", "Naked" + "_id"), issues);
		assertNull(value);
		assertTrue(issues.size() > 0);
	}

}
