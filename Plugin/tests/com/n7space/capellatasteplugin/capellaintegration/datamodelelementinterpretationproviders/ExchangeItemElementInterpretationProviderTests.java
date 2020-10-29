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
import org.polarsys.capella.core.data.information.ExchangeMechanism;
import org.polarsys.capella.core.data.information.ParameterDirection;
import org.polarsys.capella.core.data.information.datatype.NumericTypeKind;

import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.DataModelElementInterpreter.DataModelElementInterpretationProvider;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.DataPackageMock;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.ExchangeItemTypeMock;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.NumericTypeMock;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataModelElement;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.modelling.data.StructuredDataType;
import com.n7space.capellatasteplugin.utils.Issue;

public class ExchangeItemElementInterpretationProviderTests {

	@Test
	public void testInterpretModelElement_event() {
		final DataModel model = new DataModel();
		final DataPackage pkg = new DataPackage("x", "x");
		final DataPackageMock pkgMock = new DataPackageMock("DummyPkg", "DummyPkg_id");
		final NumericTypeMock typeMock = new NumericTypeMock(pkgMock, "TypeMock", "TypeMock" + "_id",
				NumericTypeKind.INTEGER);
		final ExchangeItemTypeMock mock = new ExchangeItemTypeMock(pkgMock, "ExchangeItemMock",
				"ExchangeItemMock" + "_id", ExchangeMechanism.EVENT);
		mock.addElement(
				new ExchangeItemTypeMock.ExchangeItemElementMock(pkgMock, "ItemMock1", "ItemMock1" + "_id", typeMock));
		mock.addElement(
				new ExchangeItemTypeMock.ExchangeItemElementMock(pkgMock, "ItemMock2", "ItemMock2" + "_id", typeMock));

		final List<Issue> issues = new LinkedList<Issue>();

		final DataModelElementInterpretationProvider provider = new ExchangeItemElementInterpretationProvider();
		final DataModelElement element = provider.interpretModelElement(model, pkg, new ArrayDeque<DataModelElement>(),
				mock, issues);
		assertTrue(element instanceof StructuredDataType);
		assertEquals(element.name, "ExchangeItemMock");
		final StructuredDataType typedElement = (StructuredDataType) element;
		assertEquals(typedElement.kind, StructuredDataType.Kind.Structure);
		assertEquals(typedElement.members.size(), 2);
		assertEquals(typedElement.members.get(0).name, "ItemMock1");
		assertEquals(typedElement.members.get(1).name, "ItemMock2");
	}

	@Test
	public void testInterpretModelElement_operation() {
		final DataModel model = new DataModel();
		final DataPackage pkg = new DataPackage("x", "x");
		final DataPackageMock pkgMock = new DataPackageMock("DummyPkg", "DummyPkg_id");
		final NumericTypeMock typeMock = new NumericTypeMock(pkgMock, "TypeMock", "TypeMock" + "_id",
				NumericTypeKind.INTEGER);
		final ExchangeItemTypeMock mock = new ExchangeItemTypeMock(pkgMock, "ExchangeItemMock",
				"ExchangeItemMock" + "_id", ExchangeMechanism.OPERATION);
		mock.addElement(new ExchangeItemTypeMock.ExchangeItemElementMock(pkgMock, "InputItem", "InputItem" + "_id",
				typeMock, ParameterDirection.IN));
		mock.addElement(new ExchangeItemTypeMock.ExchangeItemElementMock(pkgMock, "OutputItem", "OutputItem" + "_id",
				typeMock, ParameterDirection.OUT));

		final List<Issue> issues = new LinkedList<Issue>();

		final DataModelElementInterpretationProvider provider = new ExchangeItemElementInterpretationProvider();
		final DataModelElement element = provider.interpretModelElement(model, pkg, new ArrayDeque<DataModelElement>(),
				mock, issues);
		assertTrue(element instanceof StructuredDataType);
		assertEquals(element.name, "ExchangeItemMock");
		final StructuredDataType typedElement = (StructuredDataType) element;
		assertEquals(typedElement.kind, StructuredDataType.Kind.Structure);
		assertEquals(typedElement.members.size(), 2);
		assertEquals(typedElement.members.get(0).name, "in-parameters");
		assertEquals(typedElement.members.get(0).dataType.name, "ExchangeItemMock-IN");
		assertEquals(typedElement.members.get(1).dataType.name, "ExchangeItemMock-OUT");

		// Make sure, that the required types are defined.
		assertEquals(pkg.getDefinedTypes().get(0).name, "ExchangeItemMock-IN");
		assertEquals(pkg.getDefinedTypes().get(1).name, "ExchangeItemMock-OUT");

	}

}
