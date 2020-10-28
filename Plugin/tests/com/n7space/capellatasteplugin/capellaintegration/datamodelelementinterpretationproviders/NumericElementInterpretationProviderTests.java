// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.polarsys.capella.core.data.information.datatype.NumericType;
import org.polarsys.capella.core.data.information.datatype.NumericTypeKind;

import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.DataModelElementInterpreter.DataModelElementInterpretationProvider;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.DataPackageMock;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.NumericTypeMock;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.NumericTypeMock.UnitMock;
import com.n7space.capellatasteplugin.modelling.NameConverter;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataModelElement;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.modelling.data.FloatDataType;
import com.n7space.capellatasteplugin.modelling.data.IntegerDataType;
import com.n7space.capellatasteplugin.utils.Issue;

public class NumericElementInterpretationProviderTests {

	protected NumericType createBasicFloatTypeMock(final String name) {
		return new NumericTypeMock(new DataPackageMock("DummyPkg", "DummyPkg_id"), name, name + "_id",
				NumericTypeKind.FLOAT);
	}

	protected NumericType createBasicFloatTypeMockWithBounds(final String name, final String min, final String max) {
		return new NumericTypeMock(new DataPackageMock("DummyPkg", "DummyPkg_id"), name, name + "_id",
				NumericTypeKind.FLOAT, min, max);
	}

	protected NumericType createBasicIntegerTypeMock(final String name, final String min, final String max) {
		return new NumericTypeMock(new DataPackageMock("DummyPkg", "DummyPkg_id"), name, name + "_id",
				NumericTypeKind.INTEGER, min, max);
	}

	protected NumericType createBasicIntegerTypeMockWithComment(final String name, final String comment) {
		return new NumericTypeMock(new DataPackageMock("DummyPkg", "DummyPkg_id"), name, name + "_id",
				NumericTypeKind.INTEGER, comment);
	}

	protected NumericType createBasicIntegerTypeMockWithUnit(final String name, final String unitName) {
		return new NumericTypeMock(new DataPackageMock("DummyPkg", "DummyPkg_id"), name, name + "_id",
				NumericTypeKind.INTEGER, new UnitMock(unitName));
	}

	@Test
	public void testInterpretModelElement_floatType() {
		final DataModel model = new DataModel();
		final DataPackage pkg = new DataPackage("x", "x");
		final NumericType mock = createBasicFloatTypeMock("FloatMock");
		final List<Issue> issues = new LinkedList<Issue>();

		final DataModelElementInterpretationProvider provider = new NumericElementInterpretationProvider();
		final DataModelElement element = provider.interpretModelElement(model, pkg, new ArrayDeque<DataModelElement>(),
				mock, issues);
		assertTrue(element instanceof FloatDataType);
		final FloatDataType typedElement = (FloatDataType) element;
		assertEquals(typedElement.name, "FloatMock");
	}

	@Test
	public void testInterpretModelElement_floatTypeWithBounds() {
		final DataModel model = new DataModel();
		final DataPackage pkg = new DataPackage("x", "x");
		final NumericType mock = createBasicFloatTypeMockWithBounds("FloatMock", "3.14", "42");
		final List<Issue> issues = new LinkedList<Issue>();

		final DataModelElementInterpretationProvider provider = new NumericElementInterpretationProvider();
		final DataModelElement element = provider.interpretModelElement(model, pkg, new ArrayDeque<DataModelElement>(),
				mock, issues);
		assertTrue(element instanceof FloatDataType);
		final FloatDataType typedElement = (FloatDataType) element;
		assertEquals(typedElement.name, "FloatMock");
		assertEquals(typedElement.lowerBound.getValue(new NameConverter()), "3.14");
		assertEquals(typedElement.upperBound.getValue(new NameConverter()), "42");
	}

	@Test
	public void testInterpretModelElement_floatTypeWithParent() {
		final DataModel model = new DataModel();
		final DataPackage pkg = new DataPackage("x", "x");
		final DataPackageMock pkgMock = new DataPackageMock("DummyPkg", "DummyPkg_id");
		final NumericType parentMock = new NumericTypeMock(pkgMock, "ParentType", "ParentType" + "_id",
				NumericTypeKind.FLOAT);
		final NumericType mock = new NumericTypeMock(new DataPackageMock("DummyPkg", "DummyPkg_id"), parentMock,
				"FloatMock", "FloatMock" + "_id", NumericTypeKind.FLOAT);
		final List<Issue> issues = new LinkedList<Issue>();

		final DataModelElementInterpretationProvider provider = new NumericElementInterpretationProvider();
		final DataModelElement element = provider.interpretModelElement(model, pkg, new ArrayDeque<DataModelElement>(),
				mock, issues);
		assertTrue(element instanceof FloatDataType);
		final FloatDataType typedElement = (FloatDataType) element;
		assertEquals(typedElement.name, "FloatMock");
		assertEquals(typedElement.parent.name, "ParentType");
	}

	@Test
	public void testInterpretModelElement_integerType() {
		final DataModel model = new DataModel();
		final DataPackage pkg = new DataPackage("x", "x");
		final NumericType mock = createBasicIntegerTypeMock("IntegerMock", "12", "14");
		final List<Issue> issues = new LinkedList<Issue>();

		final DataModelElementInterpretationProvider provider = new NumericElementInterpretationProvider();
		final DataModelElement element = provider.interpretModelElement(model, pkg, new ArrayDeque<DataModelElement>(),
				mock, issues);
		assertTrue(element instanceof IntegerDataType);
		final IntegerDataType typedElement = (IntegerDataType) element;
		assertEquals(typedElement.name, "IntegerMock");
		assertNotNull(typedElement.lowerBound);
		assertEquals(typedElement.lowerBound.getValue(new NameConverter()), "12");
		assertNotNull(typedElement.upperBound);
		assertEquals(typedElement.upperBound.getValue(new NameConverter()), "14");
	}

	@Test
	public void testInterpretModelElement_integerTypeWithComment() {
		final DataModel model = new DataModel();
		final DataPackage pkg = new DataPackage("x", "x");
		final NumericType mock = createBasicIntegerTypeMockWithComment("IntegerMock", "Test comment");
		final List<Issue> issues = new LinkedList<Issue>();

		final DataModelElementInterpretationProvider provider = new NumericElementInterpretationProvider();
		final DataModelElement element = provider.interpretModelElement(model, pkg, new ArrayDeque<DataModelElement>(),
				mock, issues);
		assertTrue(element instanceof IntegerDataType);
		final IntegerDataType typedElement = (IntegerDataType) element;
		assertEquals(typedElement.name, "IntegerMock");
		assertEquals(typedElement.description, "Test comment");
	}

	@Test
	public void testInterpretModelElement_integerTypeWithUnit() {
		final DataModel model = new DataModel();
		final DataPackage pkg = new DataPackage("x", "x");
		final NumericType mock = createBasicIntegerTypeMockWithUnit("IntegerMock", "Library of Congress");
		final List<Issue> issues = new LinkedList<Issue>();

		final DataModelElementInterpretationProvider provider = new NumericElementInterpretationProvider();
		final DataModelElement element = provider.interpretModelElement(model, pkg, new ArrayDeque<DataModelElement>(),
				mock, issues);
		assertTrue(element instanceof IntegerDataType);
		final IntegerDataType typedElement = (IntegerDataType) element;
		assertEquals(typedElement.name, "IntegerMock");
		assertEquals(typedElement.unit.name, "Library of Congress");
	}

	@Test
	public void testInterpretModelElement_integerTypeWithParent() {
		final DataModel model = new DataModel();
		final DataPackage pkg = new DataPackage("x", "x");
		final DataPackageMock pkgMock = new DataPackageMock("DummyPkg", "DummyPkg_id");
		final NumericType parentMock = new NumericTypeMock(pkgMock, "ParentType", "ParentType" + "_id",
				NumericTypeKind.INTEGER);
		final NumericType mock = new NumericTypeMock(new DataPackageMock("DummyPkg", "DummyPkg_id"), parentMock,
				"IntegerMock", "IntegerMock" + "_id", NumericTypeKind.INTEGER);
		final List<Issue> issues = new LinkedList<Issue>();

		final DataModelElementInterpretationProvider provider = new NumericElementInterpretationProvider();
		final DataModelElement element = provider.interpretModelElement(model, pkg, new ArrayDeque<DataModelElement>(),
				mock, issues);
		assertTrue(element instanceof IntegerDataType);
		final IntegerDataType typedElement = (IntegerDataType) element;
		assertEquals(typedElement.name, "IntegerMock");
		assertEquals(typedElement.parent.name, "ParentType");
	}

}
