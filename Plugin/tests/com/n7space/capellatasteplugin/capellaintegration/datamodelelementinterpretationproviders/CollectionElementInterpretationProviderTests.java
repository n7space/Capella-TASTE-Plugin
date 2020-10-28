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
import org.polarsys.capella.core.data.information.datatype.DataType;
import org.polarsys.capella.core.data.information.datatype.NumericTypeKind;

import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.DataModelElementInterpreter.DataModelElementInterpretationProvider;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.CollectionTypeMock;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.DataPackageMock;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.NumericReferenceMock;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.NumericTypeMock;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.NumericTypeMock.NumericValueMock;
import com.n7space.capellatasteplugin.modelling.NameConverter;
import com.n7space.capellatasteplugin.modelling.data.CollectionDataType;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataModelElement;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.utils.Issue;

public class CollectionElementInterpretationProviderTests {

	@Test
	public void testInterpretModelElement() {
		final DataModel model = new DataModel();
		final DataPackage pkg = new DataPackage("x", "x");
		final DataPackageMock pkgMock = new DataPackageMock("DummyPkg", "DummyPkg_id");
		final DataType typeMock = new NumericTypeMock(pkgMock, "CollectedType", "CollectedType" + "_id",
				NumericTypeKind.INTEGER);
		final NamedElement mock = new CollectionTypeMock(pkgMock, "CollectionMock", "CollectionMock" + "_id", true,
				typeMock, 0, 16);
		final List<Issue> issues = new LinkedList<Issue>();

		final DataModelElementInterpretationProvider provider = new CollectionElementInterpretationProvider();
		final DataModelElement element = provider.interpretModelElement(model, pkg, new ArrayDeque<DataModelElement>(),
				mock, issues);
		assertTrue(element instanceof CollectionDataType);
		assertEquals(element.name, "CollectionMock");
		final CollectionDataType typedElement = (CollectionDataType) element;
		assertEquals(typedElement.minimumCardinality.getValue(new NameConverter()), "0");
		assertEquals(typedElement.maximumCardinality.getValue(new NameConverter()), "16");
		assertEquals(typedElement.dataType.name, "CollectedType");
	}

	@Test
	public void testInterpretModelElement_withoutType() {
		final DataModel model = new DataModel();
		final DataPackage pkg = new DataPackage("x", "x");
		final DataPackageMock pkgMock = new DataPackageMock("DummyPkg", "DummyPkg_id");
		final NamedElement mock = new CollectionTypeMock(pkgMock, "CollectionMock", "CollectionMock" + "_id", true,
				null, 0, 16);
		final List<Issue> issues = new LinkedList<Issue>();

		final DataModelElementInterpretationProvider provider = new CollectionElementInterpretationProvider();
		final DataModelElement element = provider.interpretModelElement(model, pkg, new ArrayDeque<DataModelElement>(),
				mock, issues);
		assertNull(element);
		assertEquals(issues.size(), 1);

	}

	@Test
	public void testInterpretModelElement_withReferencedValuesAsCardinalities() {
		final NameConverter nameConverter = new NameConverter();
		final DataModel model = new DataModel();
		final DataPackage pkg = new DataPackage("x", "x");
		final DataPackageMock pkgMock = new DataPackageMock("DummyPkg", "DummyPkg_id");
		final DataType typeMock = new NumericTypeMock(pkgMock, "CollectedType", "CollectedType" + "_id",
				NumericTypeKind.INTEGER);

		final NumericTypeMock numericTypeMock = new NumericTypeMock(pkgMock, "NumericType", "NumericType" + "_id",
				NumericTypeKind.INTEGER);
		final NumericValueMock numericValue1Mock = new NumericValueMock(pkgMock, "NumericValue", "NumericValue_id",
				numericTypeMock, "1");
		final NumericReferenceMock numericReference1Mock = new NumericReferenceMock(pkgMock, "NumericReference",
				"NumericReference" + "_id", numericValue1Mock);
		final NumericValueMock numericValue2Mock = new NumericValueMock(pkgMock, "NumericValue", "NumericValue_id",
				numericTypeMock, "15");
		final NumericReferenceMock numericReference2Mock = new NumericReferenceMock(pkgMock, "NumericReference",
				"NumericReference" + "_id", numericValue2Mock);

		final NamedElement mock = new CollectionTypeMock(pkgMock, "CollectionMock", "CollectionMock" + "_id", true,
				typeMock, numericReference1Mock, numericReference2Mock);

		final List<Issue> issues = new LinkedList<Issue>();

		final DataModelElementInterpretationProvider provider = new CollectionElementInterpretationProvider();
		final DataModelElement element = provider.interpretModelElement(model, pkg, new ArrayDeque<DataModelElement>(),
				mock, issues);
		assertTrue(element instanceof CollectionDataType);
		assertEquals(element.name, "CollectionMock");
		final CollectionDataType typedElement = (CollectionDataType) element;
		assertEquals(typedElement.minimumCardinality.getValue(nameConverter), "1");
		assertEquals(typedElement.maximumCardinality.getValue(nameConverter), "15");
		assertEquals(typedElement.dataType.name, "CollectedType");
	}

}
