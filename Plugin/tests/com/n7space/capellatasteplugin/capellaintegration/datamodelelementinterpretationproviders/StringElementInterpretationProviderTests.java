// N7 Space Sp. z o.o.
// www.n7space.com
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
import org.polarsys.capella.core.data.capellacore.NamedElement;

import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.DataModelElementInterpreter.DataModelElementInterpretationProvider;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.DataPackageMock;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.StringTypeMock;
import com.n7space.capellatasteplugin.modelling.NameConverter;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataModelElement;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.modelling.data.StringDataType;
import com.n7space.capellatasteplugin.utils.Issue;

public class StringElementInterpretationProviderTests {

	@Test
	public void testInterpretModelElement() {
		final DataModel model = new DataModel();
		final DataPackage pkg = new DataPackage("x", "x");
		final NamedElement mock = new StringTypeMock(new DataPackageMock("DummyPkg", "DummyPkg_id"), "StringMock",
				"StringMock" + "_id", 12, 16);
		final List<Issue> issues = new LinkedList<Issue>();

		final DataModelElementInterpretationProvider provider = new StringElementInterpretationProvider();
		final DataModelElement element = provider.interpretModelElement(model, pkg, new ArrayDeque<DataModelElement>(),
				mock, issues);
		assertTrue(element instanceof StringDataType);
		assertEquals(element.name, "StringMock");
		final StringDataType typedElement = (StringDataType) element;
		assertEquals(typedElement.minLength.getValue(new NameConverter()), "12");
		assertEquals(typedElement.maxLength.getValue(new NameConverter()), "16");
	}

	@Test
	public void testInterpretModelElemen_withSummary() {
		final DataModel model = new DataModel();
		final DataPackage pkg = new DataPackage("x", "x");
		final NamedElement mock = new StringTypeMock(new DataPackageMock("DummyPkg", "DummyPkg_id"), null, "StringMock",
				"StringMock" + "_id", "Test description");
		final List<Issue> issues = new LinkedList<Issue>();

		final DataModelElementInterpretationProvider provider = new StringElementInterpretationProvider();
		final DataModelElement element = provider.interpretModelElement(model, pkg, new ArrayDeque<DataModelElement>(),
				mock, issues);
		assertTrue(element instanceof StringDataType);
		assertEquals(element.name, "StringMock");
		final StringDataType typedElement = (StringDataType) element;
		assertEquals(typedElement.description, "Test description");
	}

	@Test
	public void testInterpretModelElement_withSuperclass() {
		final DataModel model = new DataModel();
		final DataPackage pkg = new DataPackage("x", "x");
		final StringTypeMock parentMock = new StringTypeMock(new DataPackageMock("DummyPkg", "DummyPkg_id"),
				"ParentMock", "ParentMock" + "_id");
		final StringTypeMock mock = new StringTypeMock(new DataPackageMock("DummyPkg", "DummyPkg_id"), parentMock,
				"StringMock", "StringMock" + "_id");
		final List<Issue> issues = new LinkedList<Issue>();

		final DataModelElementInterpretationProvider provider = new StringElementInterpretationProvider();
		final DataModelElement element = provider.interpretModelElement(model, pkg, new ArrayDeque<DataModelElement>(),
				mock, issues);
		assertTrue(element instanceof StringDataType);
		assertEquals(element.name, "StringMock");
		final StringDataType typedElement = (StringDataType) element;
		assertEquals(typedElement.parent.name, "ParentMock");
	}

}
