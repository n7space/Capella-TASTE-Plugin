// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.polarsys.capella.core.data.capellacore.NamedElement;
import org.polarsys.capella.core.data.information.datatype.NumericTypeKind;
import org.polarsys.capella.core.data.information.datavalue.BinaryOperator;

import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.DataModelElementInterpreter.DataModelElementInterpretationProvider;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.BinaryExpressionMock;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.DataPackageMock;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.NumericTypeMock;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.NumericTypeMock.NumericValueMock;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataModelElement;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.modelling.data.DataType;
import com.n7space.capellatasteplugin.utils.Issue;

public class BinaryExpressionInterpretationProviderTest {

	protected void assertBinaryExpressionIsEvaluatedCorrectly(final String input1, final String input2,
			final BinaryOperator operator, final String expectedOutput) {
		final DataModel model = new DataModel();
		final DataPackage pkg = new DataPackage("x", "x");
		final DataPackageMock packageMock = new DataPackageMock("x", "x");
		final NumericTypeMock type = new NumericTypeMock(packageMock, "Number", "NumberId", NumericTypeKind.INTEGER);
		final NumericValueMock value1 = new NumericValueMock(packageMock, "Value1", "Value1Id", type, input1);
		final NumericValueMock value2 = new NumericValueMock(packageMock, "Value2", "Value2Id", type, input2);

		final NamedElement mock = new BinaryExpressionMock("ExpressionMock", type, operator, value1, value2);

		final List<Issue> issues = new LinkedList<Issue>();

		final DataModelElementInterpretationProvider provider = new BinaryExpressionInterpretationProvider();
		final DataModelElement element = provider.interpretModelElement(model, pkg, new ArrayDeque<DataModelElement>(),
				mock, issues);
		assertTrue(element instanceof DataType.DataTypeValue);
		assertEquals(element.name, "ExpressionMock");
		final DataType.DataTypeValue typedElement = (DataType.DataTypeValue) element;
		assertTrue(typedElement.value.equals(expectedOutput));
		assertEquals(issues.size(), 0);
	}

	@Test
	public void testInterpretModelElement_evaluatesExpressionsCorrectly() {
		assertBinaryExpressionIsEvaluatedCorrectly("7", "5", BinaryOperator.ADD, "12");
		assertBinaryExpressionIsEvaluatedCorrectly("7", "5", BinaryOperator.DIV, "1");
		assertBinaryExpressionIsEvaluatedCorrectly("7", "5", BinaryOperator.EQU, "0");
		assertBinaryExpressionIsEvaluatedCorrectly("7", "5", BinaryOperator.MAX, "7");
		assertBinaryExpressionIsEvaluatedCorrectly("7", "5", BinaryOperator.MIN, "5");
		assertBinaryExpressionIsEvaluatedCorrectly("7", "5", BinaryOperator.MUL, "35");
		assertBinaryExpressionIsEvaluatedCorrectly("7", "5", BinaryOperator.POW, "16807");
		assertBinaryExpressionIsEvaluatedCorrectly("7", "5", BinaryOperator.SUB, "2");

		assertBinaryExpressionIsEvaluatedCorrectly("8", "12", BinaryOperator.AND, "8");
		assertBinaryExpressionIsEvaluatedCorrectly("8", "4", BinaryOperator.IOR, "12");
		assertBinaryExpressionIsEvaluatedCorrectly("8", "12", BinaryOperator.XOR, "4");
	}

	@Test
	public void testInterpretModelElement_reportsIssueWithValue() {
		final DataModel model = new DataModel();
		final DataPackage pkg = new DataPackage("x", "x");
		final DataPackageMock packageMock = new DataPackageMock("x", "x");
		final NumericTypeMock type = new NumericTypeMock(packageMock, "Number", "NumberId", NumericTypeKind.INTEGER);
		final NumericValueMock value1 = new NumericValueMock(packageMock, "Value1", "Value1Id", type,
				"incorrect value");
		final NumericValueMock value2 = new NumericValueMock(packageMock, "Value2", "Value2Id", type,
				"incorrect value");

		final NamedElement mock = new BinaryExpressionMock("ExpressionMock", type, BinaryOperator.ADD, value1, value2);

		final List<Issue> issues = new LinkedList<Issue>();

		final DataModelElementInterpretationProvider provider = new BinaryExpressionInterpretationProvider();
		final DataModelElement element = provider.interpretModelElement(model, pkg, new ArrayDeque<DataModelElement>(),
				mock, issues);
		assertNull(element);

		assertEquals(issues.size(), 1);
		assertTrue(
				issues.get(0).description.contains("Could not resolve the value of binary expression ExpressionMock"));
	}

	@Test
	public void testInterpretModelElement_reportsIssueWithOperation() {
		final DataModel model = new DataModel();
		final DataPackage pkg = new DataPackage("x", "x");
		final DataPackageMock packageMock = new DataPackageMock("x", "x");
		final NumericTypeMock type = new NumericTypeMock(packageMock, "Number", "NumberId", NumericTypeKind.INTEGER);
		final NumericValueMock value1 = new NumericValueMock(packageMock, "Value1", "Value1Id", type, "12");
		final NumericValueMock value2 = new NumericValueMock(packageMock, "Value2", "Value2Id", type, "0");

		final NamedElement mock = new BinaryExpressionMock("ExpressionMock", type, BinaryOperator.DIV, value1, value2);

		final List<Issue> issues = new LinkedList<Issue>();

		final DataModelElementInterpretationProvider provider = new BinaryExpressionInterpretationProvider();
		final DataModelElement element = provider.interpretModelElement(model, pkg, new ArrayDeque<DataModelElement>(),
				mock, issues);
		assertNull(element);

		assertEquals(issues.size(), 2);
		assertTrue(issues.get(0).description.contains("Operation DIV could not be executed on operands 12 and 0"));
		assertTrue(
				issues.get(1).description.contains("Could not resolve the value of binary expression ExpressionMock"));
	}

	@Test
	public void testInterpretModelElement_reportsIssueWithIncorrectOperation() {
		final DataModel model = new DataModel();
		final DataPackage pkg = new DataPackage("x", "x");
		final DataPackageMock packageMock = new DataPackageMock("x", "x");
		final NumericTypeMock type = new NumericTypeMock(packageMock, "Number", "NumberId", NumericTypeKind.INTEGER);
		final NumericValueMock value1 = new NumericValueMock(packageMock, "Value1", "Value1Id", type, "12");
		final NumericValueMock value2 = new NumericValueMock(packageMock, "Value2", "Value2Id", type, "0");

		final NamedElement mock = new BinaryExpressionMock("ExpressionMock", type, BinaryOperator.UNSET, value1,
				value2);

		final List<Issue> issues = new LinkedList<Issue>();

		final DataModelElementInterpretationProvider provider = new BinaryExpressionInterpretationProvider();
		final DataModelElement element = provider.interpretModelElement(model, pkg, new ArrayDeque<DataModelElement>(),
				mock, issues);
		assertNull(element);

		assertEquals(issues.size(), 2);
		assertTrue(issues.get(0).description.contains("Operation not set for operands 12 and 0"));
		assertTrue(
				issues.get(1).description.contains("Could not resolve the value of binary expression ExpressionMock"));
	}

	@Test
	public void testInterpretModelElement_reportsIssueWithType() {
		final DataModel model = new DataModel();
		final DataPackage pkg = new DataPackage("x", "x");
		final DataPackageMock packageMock = new DataPackageMock("x", "x");
		final NumericTypeMock type = new NumericTypeMock(packageMock, "Number", "NumberId", NumericTypeKind.INTEGER);
		final NumericValueMock value1 = new NumericValueMock(packageMock, "Value1", "Value1Id", type, "12");
		final NumericValueMock value2 = new NumericValueMock(packageMock, "Value2", "Value2Id", type, "13");

		final NamedElement mock = new BinaryExpressionMock("ExpressionMock", null, BinaryOperator.ADD, value1, value2);

		final List<Issue> issues = new LinkedList<Issue>();

		final DataModelElementInterpretationProvider provider = new BinaryExpressionInterpretationProvider();
		final DataModelElement element = provider.interpretModelElement(model, pkg, new ArrayDeque<DataModelElement>(),
				mock, issues);
		assertNull(element);
		assertEquals(issues.size(), 1);
		assertTrue(
				issues.get(0).description.contains("Could not resolve the type of binary expression ExpressionMock"));
	}

}
