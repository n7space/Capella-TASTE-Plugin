// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.polarsys.capella.core.data.information.datatype.NumericTypeKind;

import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.DataModelElementInterpreter.DataModelElementInterpretationProvider;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.DataPackageMock;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.EnumerationTypeMock;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.NumericTypeMock;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.StringTypeMock;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataModelElement;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.modelling.data.DataType.DataTypeValue;
import com.n7space.capellatasteplugin.modelling.data.EnumeratedDataType;
import com.n7space.capellatasteplugin.utils.Issue;

public class EnumeratedElementInterpretationProviderTests {

	@Test
	public void testInterpretModelElement() {
		final DataModel model = new DataModel();
		final DataPackage pkg = new DataPackage("x", "x");
		final DataPackageMock pkgMock = new DataPackageMock("DummyPkg", "DummyPkg_id");
		final NumericTypeMock typeMock = new NumericTypeMock(pkgMock, "DummyType", "DummyType" + "_id",
				NumericTypeKind.INTEGER);
		final EnumerationTypeMock mock = new EnumerationTypeMock(pkgMock, "EnumeratedMock", "EnumeratedMock" + "_id");
		mock.addLiteral(new EnumerationTypeMock.EnumerationValueMock(pkgMock, "Value1", "Value1" + "_id", mock,
				new NumericTypeMock.NumericValueMock(pkgMock, "Value1", "Value1_id", typeMock, "1")));
		mock.addLiteral(new EnumerationTypeMock.EnumerationValueMock(pkgMock, "Value2", "Value2" + "_id", mock,
				new NumericTypeMock.NumericValueMock(pkgMock, "Value2", "Value2_id", typeMock, "2")));
		final List<Issue> issues = new LinkedList<Issue>();
		final DataModelElementInterpreter interpreter = new DataModelElementInterpreter();
		interpreter.registerInterpretationProvider(
				org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue.class,
				new NumericValueElementInterpretationProvider());

		final DataModelElementInterpretationProvider provider = new EnumeratedElementInterpretationProvider(
				interpreter);
		final DataModelElement element = provider.interpretModelElement(model, pkg, new ArrayDeque<DataModelElement>(),
				mock, issues);
		assertTrue(element instanceof EnumeratedDataType);
		assertEquals(element.name, "EnumeratedMock");
		final EnumeratedDataType typedElement = (EnumeratedDataType) element;
		assertEquals(typedElement.literals.size(), 2);
		assertEquals(typedElement.literals.get(0).name, "Value1");
		assertEquals(((DataTypeValue) typedElement.literals.get(0).domainValue).value, "1");
		assertEquals(((DataTypeValue) typedElement.literals.get(0).domainValue).type.name, "DummyType");
		assertEquals(typedElement.literals.get(1).name, "Value2");
		assertEquals(((DataTypeValue) typedElement.literals.get(1).domainValue).value, "2");
		assertEquals(((DataTypeValue) typedElement.literals.get(1).domainValue).type.name, "DummyType");

	}

	@Test
	public void testInterpretModelElement_literalsWithoutExplicitTypes() {
		final DataModel model = new DataModel();
		final DataPackage pkg = new DataPackage("x", "x");
		final DataPackageMock pkgMock = new DataPackageMock("DummyPkg", "DummyPkg_id");

		final EnumerationTypeMock mock = new EnumerationTypeMock(pkgMock, "EnumeratedMock", "EnumeratedMock" + "_id");
		mock.addLiteral(new EnumerationTypeMock.EnumerationValueMock(pkgMock, "Value1", "Value1" + "_id", mock,
				new NumericTypeMock.NumericValueMock(pkgMock, "Value1", "Value1_id", null, "1")));
		mock.addLiteral(new EnumerationTypeMock.EnumerationValueMock(pkgMock, "Value2", "Value2" + "_id", mock,
				new NumericTypeMock.NumericValueMock(pkgMock, "Value2", "Value2_id", null, "2")));
		final List<Issue> issues = new LinkedList<Issue>();
		final DataModelElementInterpreter interpreter = new DataModelElementInterpreter();
		interpreter.registerInterpretationProvider(
				org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue.class,
				new NumericValueElementInterpretationProvider());

		final DataModelElementInterpretationProvider provider = new EnumeratedElementInterpretationProvider(
				interpreter);
		final DataModelElement element = provider.interpretModelElement(model, pkg, new ArrayDeque<DataModelElement>(),
				mock, issues);
		assertTrue(element instanceof EnumeratedDataType);
		assertEquals(element.name, "EnumeratedMock");
		final EnumeratedDataType typedElement = (EnumeratedDataType) element;
		assertEquals(typedElement.literals.size(), 2);
		assertEquals(typedElement.literals.get(0).name, "Value1");
		assertEquals(((DataTypeValue) typedElement.literals.get(0).domainValue).value, "1");
		assertEquals(((DataTypeValue) typedElement.literals.get(0).domainValue).type.name, "INTEGER");
		assertEquals(typedElement.literals.get(1).name, "Value2");
		assertEquals(((DataTypeValue) typedElement.literals.get(1).domainValue).value, "2");
		assertEquals(((DataTypeValue) typedElement.literals.get(1).domainValue).type.name, "INTEGER");

	}

	@Test
	public void testInterpretModelElement_literalWithoutAnyType() {
		final DataModel model = new DataModel();
		final DataPackage pkg = new DataPackage("x", "x");
		final DataPackageMock pkgMock = new DataPackageMock("DummyPkg", "DummyPkg_id");

		final EnumerationTypeMock mock = new EnumerationTypeMock(pkgMock, "EnumeratedMock", "EnumeratedMock" + "_id");
		mock.addLiteral(new EnumerationTypeMock.EnumerationValueMock(pkgMock, "Value1", "Value1" + "_id", mock,
				new StringTypeMock.StringValueMock(pkgMock, "Value1", "Value1_id", null, "MyValue")));

		final List<Issue> issues = new LinkedList<Issue>();
		final DataModelElementInterpreter interpreter = new DataModelElementInterpreter();
		interpreter.registerInterpretationProvider(
				org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue.class,
				new NumericValueElementInterpretationProvider());
		interpreter.registerInterpretationProvider(
				org.polarsys.capella.core.data.information.datavalue.LiteralStringValue.class,
				new StringValueElementIntepretationProvider());

		final DataModelElementInterpretationProvider provider = new EnumeratedElementInterpretationProvider(
				interpreter);
		final DataModelElement element = provider.interpretModelElement(model, pkg, new ArrayDeque<DataModelElement>(),
				mock, issues);
		assertEquals(issues.size(), 1);
		assertTrue(element instanceof EnumeratedDataType);
		assertEquals(element.name, "EnumeratedMock");
		final EnumeratedDataType typedElement = (EnumeratedDataType) element;
		assertEquals(typedElement.literals.size(), 1);
		assertEquals(typedElement.literals.get(0).name, "Value1");
		assertEquals(typedElement.literals.get(0).domainValue, null);

	}

}
