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
import org.polarsys.capella.core.data.information.DataPkg;

import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.BooleanTypeMock;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.BooleanTypeMock.LiteralBooleanValueMock;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.DataPackageMock;
import com.n7space.capellatasteplugin.modelling.data.BooleanDataType;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataModelElement;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.modelling.data.DataType;
import com.n7space.capellatasteplugin.utils.Issue;

public class BooleanElementInterpretationProviderTests {

	protected BooleanTypeMock createBasicBooleanTypeMock(final String name) {
		final BooleanTypeMock mock = new BooleanTypeMock(new DataPackageMock("DummyPkg", "DummyPkg_id"), name,
				name + "_id");
		return mock;
	}

	@Test
	public void testInterpretModelElement() {
		final DataModel model = new DataModel();
		final DataPackage pkg = new DataPackage("x", "x");
		final BooleanTypeMock mock = createBasicBooleanTypeMock("BooleanMock");
		final List<Issue> issues = new LinkedList<Issue>();

		final BooleanElementInterpretationProvider provider = new BooleanElementInterpretationProvider();
		final DataModelElement element = provider.interpretModelElement(model, pkg, new ArrayDeque<DataModelElement>(),
				mock, issues);
		assertTrue(element instanceof BooleanDataType);
		assertEquals(element.name, "BooleanMock");
	}

	@Test
	public void testInterpretModelElement_withLiterals() {
		final DataModel model = new DataModel();
		final DataPackage pkg = new DataPackage("x", "x");
		final BooleanTypeMock mock = createBasicBooleanTypeMock("BooleanMock");
		mock.addMockValue(new LiteralBooleanValueMock((DataPkg) mock.eContainer(), mock, "FalseLiteral",
				"FalseLiteral_id", false));
		mock.addMockValue(
				new LiteralBooleanValueMock((DataPkg) mock.eContainer(), mock, "TrueLiteral", "TrueLiteral_id", true));
		final List<Issue> issues = new LinkedList<Issue>();

		final BooleanElementInterpretationProvider provider = new BooleanElementInterpretationProvider();
		provider.interpretModelElement(model, pkg, new ArrayDeque<DataModelElement>(), mock, issues);
		final List<DataType.DataTypeValue> values = pkg.getDefinedValues();
		assertEquals(values.size(), 2);
		assertEquals(values.get(0).name, "BooleanMock-FalseLiteral");
		assertEquals(values.get(1).name, "BooleanMock-TrueLiteral");

	}

}
