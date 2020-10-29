// N7 Space Sp. z o.o.
// n7space.com
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
import org.polarsys.capella.core.data.information.datavalue.UnaryOperator;

import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.DataModelElementInterpreter.DataModelElementInterpretationProvider;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.DataPackageMock;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.NumericTypeMock;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.NumericTypeMock.NumericValueMock;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.UnaryExpressionMock;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataModelElement;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.modelling.data.DataType;
import com.n7space.capellatasteplugin.utils.Issue;

public class UnaryExpressionInterpretationProviderTest {

	protected void assertUnaryExpressionIsEvaluatedCorrectly(final String input, final UnaryOperator operator,
			final String exprectedOutput) {
		final DataModel model = new DataModel();
		final DataPackage pkg = new DataPackage("x", "x");
		final DataPackageMock packageMock = new DataPackageMock("x", "x");
		final NumericTypeMock type = new NumericTypeMock(packageMock, "Number", "NumberId", NumericTypeKind.INTEGER);
		final NumericValueMock value = new NumericValueMock(packageMock, "Value", "ValueId", type, input);
		final NamedElement mock = new UnaryExpressionMock("ExpressionMock", type, operator, value);
		final List<Issue> issues = new LinkedList<Issue>();

		final DataModelElementInterpretationProvider provider = new UnaryExpressionInterpretationProvider();
		final DataModelElement element = provider.interpretModelElement(model, pkg, new ArrayDeque<DataModelElement>(),
				mock, issues);
		assertTrue(element instanceof DataType.DataTypeValue);
		assertEquals(element.name, "ExpressionMock");
		final DataType.DataTypeValue typedElement = (DataType.DataTypeValue) element;
		assertTrue(typedElement.value.equals(exprectedOutput));
		assertEquals(issues.size(), 0);
	}

	@Test
	public void testInterpretModelElement_evaluatesSuc() {
		assertUnaryExpressionIsEvaluatedCorrectly("12", UnaryOperator.SUC, "13");
	}

	@Test
	public void testInterpretModelElement_evaluatesPre() {
		assertUnaryExpressionIsEvaluatedCorrectly("12", UnaryOperator.PRE, "11");
	}

	@Test
	public void testInterpretModelElement_evaluatesNot() {
		assertUnaryExpressionIsEvaluatedCorrectly("12", UnaryOperator.NOT, "-12");
	}

	@Test
	public void testInterpretModelElement_reportsIssueWithIncorrectOperation() {
		final DataModel model = new DataModel();
		final DataPackage pkg = new DataPackage("x", "x");
		final DataPackageMock packageMock = new DataPackageMock("x", "x");
		final NumericTypeMock type = new NumericTypeMock(packageMock, "Number", "NumberId", NumericTypeKind.INTEGER);
		final NumericValueMock value = new NumericValueMock(packageMock, "Value", "ValueId", type, "12");
		final NamedElement mock = new UnaryExpressionMock("ExpressionMock", type, UnaryOperator.UNSET, value);
		final List<Issue> issues = new LinkedList<Issue>();

		final DataModelElementInterpretationProvider provider = new UnaryExpressionInterpretationProvider();
		final DataModelElement element = provider.interpretModelElement(model, pkg, new ArrayDeque<DataModelElement>(),
				mock, issues);
		assertNull(element);
		assertEquals(issues.size(), 2);
		assertTrue(issues.get(0).description.contains("Unary operation UNSET is not supported"));
		assertTrue(
				issues.get(1).description.contains("Could not resolve the value of unary expression ExpressionMock"));
	}

	@Test
	public void testInterpretModelElement_reportsIssueWithValue() {
		final DataModel model = new DataModel();
		final DataPackage pkg = new DataPackage("x", "x");
		final DataPackageMock packageMock = new DataPackageMock("x", "x");
		final NumericTypeMock type = new NumericTypeMock(packageMock, "Number", "NumberId", NumericTypeKind.INTEGER);
		final NumericValueMock value = new NumericValueMock(packageMock, "Value", "ValueId", type, "incorrect input");
		final NamedElement mock = new UnaryExpressionMock("ExpressionMock", type, UnaryOperator.PRE, value);
		final List<Issue> issues = new LinkedList<Issue>();

		final DataModelElementInterpretationProvider provider = new UnaryExpressionInterpretationProvider();
		final DataModelElement element = provider.interpretModelElement(model, pkg, new ArrayDeque<DataModelElement>(),
				mock, issues);
		assertNull(element);
		assertEquals(issues.size(), 1);
		assertTrue(
				issues.get(0).description.contains("Could not resolve the value of unary expression ExpressionMock"));
	}

	@Test
	public void testInterpretModelElement_reportsIssueWithType() {
		final DataModel model = new DataModel();
		final DataPackage pkg = new DataPackage("x", "x");
		final DataPackageMock packageMock = new DataPackageMock("x", "x");
		final NumericTypeMock type = new NumericTypeMock(packageMock, "Number", "NumberId", NumericTypeKind.INTEGER);
		final NumericValueMock value = new NumericValueMock(packageMock, "Value", "ValueId", type, "12");
		final NamedElement mock = new UnaryExpressionMock("ExpressionMock", null, UnaryOperator.PRE, value);
		final List<Issue> issues = new LinkedList<Issue>();

		final DataModelElementInterpretationProvider provider = new UnaryExpressionInterpretationProvider();
		final DataModelElement element = provider.interpretModelElement(model, pkg, new ArrayDeque<DataModelElement>(),
				mock, issues);
		assertNull(element);
		assertEquals(issues.size(), 1);
		assertTrue(issues.get(0).description.contains("Could not resolve the type of unary expression ExpressionMock"));
	}

}
