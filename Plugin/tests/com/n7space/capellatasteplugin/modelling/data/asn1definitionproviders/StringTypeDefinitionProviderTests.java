// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.data.asn1definitionproviders;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Test;

import com.n7space.capellatasteplugin.modelling.NameConverter;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.modelling.data.StringDataType;
import com.n7space.capellatasteplugin.modelling.data.UsedNumericValue.ExplicitIntegerValue;

public class StringTypeDefinitionProviderTests {

	@Test
	public void testProvideAsn1Definition() {
		final NameConverter nameConverter = new NameConverter();
		final DataModel model = new DataModel();
		final DataPackage pkg = TypeAssembler.createBasicDataPackage(model, "testPkg");
		final StringDataType type = TypeAssembler.createBasicStringDataType(pkg, "testString");

		final StringTypeDefinitionProvider provider = new StringTypeDefinitionProvider(nameConverter);

		final String definition = provider.provideAsn1Definition(model, type);
		assertEquals("TestString ::= OCTET STRING", definition.trim());
	}

	@Test
	public void testProvideAsn1Definition_withSuperclass() {
		final NameConverter nameConverter = new NameConverter();
		final DataModel model = new DataModel();
		final DataPackage pkg = TypeAssembler.createBasicDataPackage(model, "testPkg");
		final StringDataType parentType = TypeAssembler.createBasicStringDataType(pkg, "parentType");
		final StringDataType type = TypeAssembler.createBasicStringDataTypeWithParent(pkg, parentType, "testString");

		final StringTypeDefinitionProvider provider = new StringTypeDefinitionProvider(nameConverter);

		final String definition = provider.provideAsn1Definition(model, type);
		assertEquals("TestString ::= ParentType", definition.trim());
	}

	@Test
	public void testProvideAsn1DefinitionForABoundedString() {
		final NameConverter nameConverter = new NameConverter();
		final DataModel model = new DataModel();
		final DataPackage pkg = TypeAssembler.createBasicDataPackage(model, "testPkg");
		final StringDataType type = TypeAssembler.createBasicStringDataType(pkg, "testString");
		type.minLength = new ExplicitIntegerValue(BigInteger.valueOf(11));
		type.maxLength = new ExplicitIntegerValue(BigInteger.valueOf(17));

		final StringTypeDefinitionProvider provider = new StringTypeDefinitionProvider(nameConverter);

		final String definition = provider.provideAsn1Definition(model, type);
		assertEquals("TestString ::= OCTET STRING(SIZE(11..17))", definition.trim());
	}

	@Test
	public void testProvideAsn1DefinitionForALengthLimitedString() {
		final NameConverter nameConverter = new NameConverter();
		final DataModel model = new DataModel();
		final DataPackage pkg = TypeAssembler.createBasicDataPackage(model, "testPkg");
		final StringDataType type = TypeAssembler.createBasicStringDataType(pkg, "testString");
		type.maxLength = new ExplicitIntegerValue(BigInteger.valueOf(17));

		final StringTypeDefinitionProvider provider = new StringTypeDefinitionProvider(nameConverter);

		final String definition = provider.provideAsn1Definition(model, type);
		assertEquals("TestString ::= OCTET STRING(SIZE(0..17))", definition.trim());
	}

}
