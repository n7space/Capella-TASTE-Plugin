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
import org.polarsys.capella.core.data.capellacore.NamedElement;
import org.polarsys.capella.core.data.information.datatype.StringType;

import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.DataModelElementInterpreter.DataModelElementInterpretationProvider;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.DataPackageMock;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.StringTypeMock;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataModelElement;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.modelling.data.DataType;
import com.n7space.capellatasteplugin.utils.Issue;

public class StringValueElementIntepretationProviderTests {

	public NamedElement createStringValue(final String name, final String value) {
		final DataPackageMock pkgMock = new DataPackageMock("DummyPkg", "DummyPkg_id");
		final StringType typeMock = new StringTypeMock(pkgMock, name + "_type", name + "_type_id");
		return new StringTypeMock.StringValueMock(pkgMock, name, name + "_id", typeMock, value);
	}

	@Test
	public void testInterpretModelElement() {
		final DataModel model = new DataModel();
		final DataPackage pkg = new DataPackage("x", "x");
		final NamedElement mock = createStringValue("StringValue", "Capella is nice");
		final List<Issue> issues = new LinkedList<Issue>();

		final DataModelElementInterpretationProvider provider = new StringValueElementIntepretationProvider();
		final DataModelElement element = provider.interpretModelElement(model, pkg, new ArrayDeque<DataModelElement>(),
				mock, issues);
		assertTrue(element instanceof DataType.DataTypeValue);
		assertEquals(element.name, "StringValue");
		assertEquals(((DataType.DataTypeValue) element).value, "Capella is nice");
	}
}
