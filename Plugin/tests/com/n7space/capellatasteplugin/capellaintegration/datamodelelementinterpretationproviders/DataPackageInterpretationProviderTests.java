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

import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.DataModelElementInterpreter.DataModelElementInterpretationProvider;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.DataPackageMock;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataModelElement;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.utils.Issue;

public class DataPackageInterpretationProviderTests {

	@Test
	public void testInterpretModelElement() {
		final DataModel model = new DataModel();
		final DataPackage pkg = new DataPackage("x", "x");
		final DataPackageMock mock = new DataPackageMock("DummyPkg", "DummyPkg_id");
		final List<Issue> issues = new LinkedList<Issue>();

		final DataModelElementInterpretationProvider provider = new DataPackageInterpretationProvider();
		final DataModelElement element = provider.interpretModelElement(model, pkg, new ArrayDeque<DataModelElement>(),
				mock, issues);
		assertTrue(element instanceof DataPackage);
		assertEquals(element.name, "DummyPkg");
	}

	@Test
	public void testInterpretModelElement_withParent() {
		final DataModel model = new DataModel();
		final DataPackage pkg = new DataPackage("x", "x");
		final DataPackageMock parentPackageMock = new DataPackageMock("ParentPkg", "ParentPkg_id");
		final DataPackageMock mock = new DataPackageMock(parentPackageMock, "DummyPkg", "DummyPkg_id");
		final List<Issue> issues = new LinkedList<Issue>();

		final DataModelElementInterpretationProvider provider = new DataPackageInterpretationProvider();
		final DataModelElement element = provider.interpretModelElement(model, pkg, new ArrayDeque<DataModelElement>(),
				mock, issues);
		assertTrue(element instanceof DataPackage);
		assertEquals(element.name, "DummyPkg");
	}
}
