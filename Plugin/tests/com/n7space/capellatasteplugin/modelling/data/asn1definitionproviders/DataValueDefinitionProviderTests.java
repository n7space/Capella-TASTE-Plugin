// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.data.asn1definitionproviders;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.n7space.capellatasteplugin.modelling.NameConverter;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.modelling.data.DataType;
import com.n7space.capellatasteplugin.modelling.data.DataType.DataTypeValue;
import com.n7space.capellatasteplugin.modelling.data.DataTypeReference;

public class DataValueDefinitionProviderTests {

	protected DataPackage pkg = null;
	protected NameConverter nameConverter = null;
	protected DataValueDefinitionProvider provider = null;
	protected DataModel model = null;

	protected DataTypeValue createValueAndCompleteDataModel(final DataType type, final String name,
			final Object value) {
		final DataTypeValue result = new DataTypeValue(pkg, name, name + "_id", new DataTypeReference(type), value);
		pkg.addTypeDefinition(type);
		pkg.addValueDefinition(result);

		return result;
	}

	@Before
	public void setUp() throws Exception {
		pkg = new DataPackage("DummyPkg", "DummyPkg" + "_id");
		nameConverter = new NameConverter();
		provider = new DataValueDefinitionProvider(nameConverter);
		model = new DataModel();
		model.dataPackages.add(pkg);
	}

	@Test
	public void testProvideAsn1Definition_forBoolean() {
		final DataType type = TypeAssembler.createBasicBooleanDataType(pkg, "ABoolean");
		final DataTypeValue value = createValueAndCompleteDataModel(type, "Value", Boolean.valueOf(true));
		final String definition = provider.provideAsn1Definition(model, value);
		assertEquals(definition, "value ABoolean ::= TRUE\n\n");
	}

	@Test
	public void testProvideAsn1Definition_forFloat() {
		final DataType type = TypeAssembler.createBasicFloatDataType(pkg, "AFloat");
		final DataTypeValue value = createValueAndCompleteDataModel(type, "Value", Float.valueOf(3.14f));
		final String definition = provider.provideAsn1Definition(model, value);
		assertEquals(definition, "value AFloat ::= 3.14\n\n");
	}

	@Test
	public void testProvideAsn1Definition_forInteger() {
		final DataType type = TypeAssembler.createBasicIntegerDataType(pkg, "AnInt");
		final DataTypeValue value = createValueAndCompleteDataModel(type, "Value", Integer.valueOf(12));
		final String definition = provider.provideAsn1Definition(model, value);
		assertEquals(definition, "value AnInt ::= 12\n\n");
	}

	@Test
	public void testProvideAsn1Definition_forString() {
		final DataType type = TypeAssembler.createBasicStringDataType(pkg, "AString");
		final DataTypeValue value = createValueAndCompleteDataModel(type, "Value", "MyString");
		final String definition = provider.provideAsn1Definition(model, value);
		assertEquals(definition, "value AString ::= \"MyString\"\n\n");
	}

}
