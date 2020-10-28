// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.polarsys.capella.core.data.information.datatype.NumericTypeKind;

import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.DataModelElementInterpreter.DataModelElementInterpretationProvider;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.ClassTypeMock;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.DataPackageMock;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.NumericTypeMock;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.UnionTypeMock;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataModelElement;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.modelling.data.StructuredDataType;
import com.n7space.capellatasteplugin.utils.Issue;

public class ClassModelElementInterpretationProviderTests {

	@Test
	public void testInterpretModelElement_class() {
		final DataModel model = new DataModel();
		final DataPackage pkg = new DataPackage("x", "x");
		final DataPackageMock pkgMock = new DataPackageMock("DummyPkg", "DummyPkg_id");
		final NumericTypeMock typeMock = new NumericTypeMock(pkgMock, "TypeMock", "TypeMock" + "_id",
				NumericTypeKind.INTEGER);

		final ClassTypeMock mock = new ClassTypeMock(pkgMock, "ClassMock", "ClassMock" + "_id");
		mock.addProperty(new ClassTypeMock.PropertyTypeMock(pkgMock, "property1", "property1" + "_id", typeMock));
		mock.addValue(new ClassTypeMock.DataValueMock(pkgMock, "property2", "property2" + "_id", typeMock));
		final List<Issue> issues = new LinkedList<Issue>();

		final DataModelElementInterpretationProvider provider = new ClassModelElementInterpretationProvider();
		final DataModelElement element = provider.interpretModelElement(model, pkg, new ArrayDeque<DataModelElement>(),
				mock, issues);
		assertTrue(element instanceof StructuredDataType);
		assertEquals(element.name, "ClassMock");
		final StructuredDataType typedElement = (StructuredDataType) element;
		assertEquals(typedElement.kind, StructuredDataType.Kind.Structure);
		assertEquals(typedElement.members.size(), 2);
		assertEquals(typedElement.members.get(0).name, "property1");
		assertEquals(typedElement.members.get(1).name, "property2");
	}

	@Test
	public void testInterpretModelElement_classWithParent() {
		final DataModel model = new DataModel();
		final DataPackage pkg = new DataPackage("x", "x");
		final DataPackageMock pkgMock = new DataPackageMock("DummyPkg", "DummyPkg_id");
		final ClassTypeMock parentMock = new ClassTypeMock(pkgMock, "ParentClassMock", "ParentClassMock" + "_id");
		final ClassTypeMock mock = new ClassTypeMock(pkgMock, parentMock, "ClassMock", "ClassMock" + "_id");

		final List<Issue> issues = new LinkedList<Issue>();

		final DataModelElementInterpretationProvider provider = new ClassModelElementInterpretationProvider();
		final DataModelElement element = provider.interpretModelElement(model, pkg, new ArrayDeque<DataModelElement>(),
				mock, issues);
		assertTrue(element instanceof StructuredDataType);
		assertEquals(element.name, "ClassMock");
		final StructuredDataType typedElement = (StructuredDataType) element;
		assertEquals(typedElement.kind, StructuredDataType.Kind.Structure);
		assertNotNull(typedElement.parent);
		assertEquals(typedElement.parent.name, "ParentClassMock");
	}

	@Test
	public void testInterpretModelElement_union() {
		final DataModel model = new DataModel();
		final DataPackage pkg = new DataPackage("x", "x");
		final DataPackageMock pkgMock = new DataPackageMock("DummyPkg", "DummyPkg_id");
		final NumericTypeMock typeMock = new NumericTypeMock(pkgMock, "TypeMock", "TypeMock" + "_id",
				NumericTypeKind.INTEGER);

		final UnionTypeMock mock = new UnionTypeMock(pkgMock, "UnionMock", "UnionMock" + "_id");
		mock.addUnionProperty(
				new UnionTypeMock.UnionPropertyTypeMock(pkgMock, "property1", "property1" + "_id", typeMock));

		final List<Issue> issues = new LinkedList<Issue>();

		final DataModelElementInterpretationProvider provider = new ClassModelElementInterpretationProvider();
		final DataModelElement element = provider.interpretModelElement(model, pkg, new ArrayDeque<DataModelElement>(),
				mock, issues);
		assertTrue(element instanceof StructuredDataType);
		assertEquals(element.name, "UnionMock");
		final StructuredDataType typedElement = (StructuredDataType) element;
		assertEquals(typedElement.kind, StructuredDataType.Kind.Union);
		assertEquals(typedElement.members.size(), 1);
		assertEquals(typedElement.members.get(0).name, "property1");
	}

	@Test
	public void testInterpretModelElement_unionWithParent() {
		final DataModel model = new DataModel();
		final DataPackage pkg = new DataPackage("x", "x");
		final DataPackageMock pkgMock = new DataPackageMock("DummyPkg", "DummyPkg_id");
		final UnionTypeMock parentMock = new UnionTypeMock(pkgMock, "ParentUnionMock", "ParentUnionMock" + "_id");
		final UnionTypeMock mock = new UnionTypeMock(pkgMock, parentMock, "UnionMock", "UnionMock" + "_id");

		final List<Issue> issues = new LinkedList<Issue>();

		final DataModelElementInterpretationProvider provider = new ClassModelElementInterpretationProvider();
		final DataModelElement element = provider.interpretModelElement(model, pkg, new ArrayDeque<DataModelElement>(),
				mock, issues);
		assertTrue(element instanceof StructuredDataType);
		assertEquals(element.name, "UnionMock");
		final StructuredDataType typedElement = (StructuredDataType) element;
		assertEquals(typedElement.kind, StructuredDataType.Kind.Union);
		assertNull(typedElement.parent);
		assertEquals(issues.size(), 1);

	}

}
