// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.polarsys.capella.core.data.capellacore.NamedElement;
import org.polarsys.capella.core.data.information.datatype.NumericType;
import org.polarsys.capella.core.data.information.datatype.NumericTypeKind;

import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.NumericTypeMock;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataModelElement;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.utils.ConfigurableObject.Option;
import com.n7space.capellatasteplugin.utils.Issue;

public class DataModelElementInterpreterTests {

	protected static class DummyDataModelElementInterpretationProvider
			implements DataModelElementInterpreter.DataModelElementInterpretationProvider {

		@Override
		public DataModelElement interpretModelElement(final DataModel dataModel, final DataPackage currentDataPackage,
				final Deque<DataModelElement> context, final NamedElement interpretedElement,
				final List<Issue> issues) {
			return new DataModelElement(interpretedElement.getName(), interpretedElement.getId());
		}

	}

	protected DataModel model;
	protected DataPackage pkg;
	protected NumericTypeMock mockType;
	protected DataModelElementInterpreter interpreter;
	protected DataModelElementInterpreter.DataModelElementInterpretationProvider interpretationProvider;
	protected List<Issue> issues;
	protected Option option1 = null;
	protected Option option2 = null;
	protected Option[] options = null;
	protected String option1Handle = "Handle1";
	protected String option2Handle = "Handle2";

	@Before
	public void setUp() throws Exception {
		interpreter = new DataModelElementInterpreter();
		interpretationProvider = new DummyDataModelElementInterpretationProvider();
		model = new DataModel();
		pkg = new DataPackage("Package", "Package" + "_id");
		model.dataPackages.add(pkg);
		mockType = new NumericTypeMock(null, "MockType", "MockType" + "_id", NumericTypeKind.INTEGER);
		issues = new LinkedList<Issue>();

		option1 = new Option(option1Handle, "Description1", "default");
		option2 = new Option(option2Handle, "Description2", "default");
		options = new Option[2];
		options[0] = option1;
		options[1] = option2;
	}

	@Test
	public void testInterpretDataModelElement() {
		interpreter.registerInterpretationProvider(NumericType.class, new NumericElementInterpretationProvider());
		final DataModelElement element = interpreter.interpretDataModelElement(model, pkg,
				new LinkedList<DataModelElement>(), mockType, issues);
		assertNotNull(element);
		assertEquals(element.name, mockType.getName());
	}

	@Test
	public void testRegisterInterpretationProvider() {
		assertEquals(interpreter.dataModelElementInterpretationProviders.size(), 0);

		interpreter.registerInterpretationProvider(Integer.class, interpretationProvider);
		assertEquals(interpreter.dataModelElementInterpretationProviders.size(), 1);
		assertTrue(interpreter.dataModelElementInterpretationProviders.containsKey(Integer.class));
		assertTrue(interpreter.dataModelElementInterpretationProviders.values().contains(interpretationProvider));
	}

	@Test
	public void testSetGetOptions() {
		interpreter.registerInterpretationProvider(NumericType.class, new NumericElementInterpretationProvider());
		interpreter.setOptions(options);
		final Option[] options2 = interpreter.getOptions();
		for (int i = 0; i < options.length; i++) {
			boolean found = false;
			for (int j = 0; j < options2.length; j++) {
				if (options[i].handle.equals(options2[j].handle)) {
					found = true;
				}
			}
			assertTrue(found);

		}
	}

	@Test
	public void testSetGetOptionValue() {
		interpreter.registerInterpretationProvider(NumericType.class, new NumericElementInterpretationProvider());
		interpreter.setOptions(options);
		interpreter.setOptionValue(option1Handle, "NewValue");
		assertEquals(interpreter.getOptionValue(option1Handle), "NewValue");
	}

}
