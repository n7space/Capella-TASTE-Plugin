// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.n7space.capellatasteplugin.modelling.Asn1ElementDefinitionGenerator.Asn1ElementDefinitionProvider;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataModelElement;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.modelling.data.DataType;
import com.n7space.capellatasteplugin.utils.BasicConfigurableItem;
import com.n7space.capellatasteplugin.utils.ConfigurableObject.Option;

public class Asn1ElementDefinitionGeneratorTests {

	protected static class DummyDataTypeA extends DataType {

		public DummyDataTypeA(final DataPackage parentPackage, final String dataTypeName, final String dataTypeId) {
			super(parentPackage, dataTypeName, dataTypeId);
		}

	}

	protected static class DummyDataTypeB extends DataType {

		public DummyDataTypeB(final DataPackage parentPackage, final String dataTypeName, final String dataTypeId) {
			super(parentPackage, dataTypeName, dataTypeId);
		}

	}

	protected static class DummyDefinitionProvider extends BasicConfigurableItem
			implements Asn1ElementDefinitionProvider {

		public boolean wasInvoked = false;

		@Override
		public String provideAsn1Definition(final DataModel model, final DataModelElement element) {
			wasInvoked = true;
			return element.name;
		}

	}

	protected Asn1ElementDefinitionGenerator generator = null;
	protected DummyDefinitionProvider providerA = null;
	protected DummyDefinitionProvider providerB = null;
	protected DummyDataTypeA typeA = null;
	protected DummyDataTypeB typeB = null;
	protected Option option1 = null;
	protected Option option2 = null;
	protected Option[] options = null;
	protected String option1Handle = "Handle1";
	protected String option2Handle = "Handle2";

	@Before
	public void setUp() throws Exception {
		generator = new Asn1ElementDefinitionGenerator();
		providerA = new DummyDefinitionProvider();
		providerB = new DummyDefinitionProvider();
		typeA = new DummyDataTypeA(null, "TypeA", "1");
		typeB = new DummyDataTypeB(null, "TypeB", "2");

		generator.registerDefinitionProvider(DummyDataTypeA.class, providerA);
		generator.registerDefinitionProvider(DummyDataTypeB.class, providerB);

		option1 = new Option(option1Handle, "Description1", "default");
		option2 = new Option(option2Handle, "Description2", "default");
		options = new Option[2];
		options[0] = option1;
		options[1] = option2;
	}

	@Test
	public void testGenerateAsn1Definition() {
		final String definition = generator.generateAsn1Definition(null, typeA);
		assertEquals(definition, "TypeA");
		assertTrue(providerA.wasInvoked);
		assertFalse(providerB.wasInvoked);
	}

	@Test
	public void testSetGetOptions() {
		generator.setOptions(options);
		final Option[] options2 = generator.getOptions();
		assertEquals(options.length, options2.length);
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
		generator.setOptions(options);
		generator.setOptionValue(option1Handle, "NewValue");
		assertEquals(generator.getOptionValue(option1Handle), "NewValue");
	}

}
