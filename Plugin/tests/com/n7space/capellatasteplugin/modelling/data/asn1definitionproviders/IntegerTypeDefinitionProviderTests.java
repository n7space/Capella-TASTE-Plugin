// N7 Space Sp. z o.o.
// n7space.com
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
import com.n7space.capellatasteplugin.modelling.data.IntegerDataType;
import com.n7space.capellatasteplugin.modelling.data.Unit;
import com.n7space.capellatasteplugin.modelling.data.UsedNumericValue.ExplicitIntegerValue;

public class IntegerTypeDefinitionProviderTests {

	@Test
	public void testProvideAsn1Definition() {
		final NameConverter nameConverter = new NameConverter();
		final DataModel model = new DataModel();
		final DataPackage pkg = TypeAssembler.createBasicDataPackage(model, "testPkg");
		final IntegerDataType type = TypeAssembler.createBasicIntegerDataType(pkg, "testInteger");

		final IntegerTypeDefinitionProvider provider = new IntegerTypeDefinitionProvider(nameConverter);

		final String definition = provider.provideAsn1Definition(model, type);
		assertEquals("TestInteger ::= INTEGER", definition.trim());
	}

	@Test
	public void testProvideAsn1Definition_withUnit() {
		final NameConverter nameConverter = new NameConverter();
		final DataModel model = new DataModel();
		final DataPackage pkg = TypeAssembler.createBasicDataPackage(model, "testPkg");
		final IntegerDataType type = TypeAssembler.createBasicIntegerDataType(pkg, "testInteger");
		type.unit = new Unit("Library of Congress");

		final IntegerTypeDefinitionProvider provider = new IntegerTypeDefinitionProvider(nameConverter);

		final String definition = provider.provideAsn1Definition(model, type);
		assertEquals("-- Unit: Library of Congress\nTestInteger ::= INTEGER", definition.trim());
	}

	@Test
	public void testProvideAsn1Definition_withSuperclass() {
		final NameConverter nameConverter = new NameConverter();
		final DataModel model = new DataModel();
		final DataPackage pkg = TypeAssembler.createBasicDataPackage(model, "testPkg");
		final IntegerDataType parent = TypeAssembler.createBasicIntegerDataType(pkg, "parentInteger");
		final IntegerDataType type = TypeAssembler.createBasicIntegerDataTypeWithParent(pkg, parent, "testInteger");

		final IntegerTypeDefinitionProvider provider = new IntegerTypeDefinitionProvider(nameConverter);

		final String definition = provider.provideAsn1Definition(model, type);
		assertEquals("TestInteger ::= ParentInteger", definition.trim());
	}

	@Test
	public void testProvideAsn1DefinitionForBoundedInteger() {
		final NameConverter nameConverter = new NameConverter();
		final DataModel model = new DataModel();
		final DataPackage pkg = TypeAssembler.createBasicDataPackage(model, "testPkg");
		final IntegerDataType type = TypeAssembler.createBasicIntegerDataType(pkg, "testInteger");
		type.lowerBound = new ExplicitIntegerValue(BigInteger.valueOf(19));
		type.upperBound = new ExplicitIntegerValue(BigInteger.valueOf(69));

		final IntegerTypeDefinitionProvider provider = new IntegerTypeDefinitionProvider(nameConverter);

		final String definition = provider.provideAsn1Definition(model, type);
		assertEquals("TestInteger ::= INTEGER(19..69)", definition.trim());
	}

	@Test
	public void testProvideAsn1DefinitionForUnsignedInteger() {
		final NameConverter nameConverter = new NameConverter();
		final DataModel model = new DataModel();
		final DataPackage pkg = TypeAssembler.createBasicDataPackage(model, "testPkg");
		final IntegerDataType type = TypeAssembler.createBasicIntegerDataType(pkg, "testInteger");
		type.lowerBound = new ExplicitIntegerValue(BigInteger.valueOf(0));

		final IntegerTypeDefinitionProvider provider = new IntegerTypeDefinitionProvider(nameConverter);

		final String definition = provider.provideAsn1Definition(model, type);
		assertEquals("TestInteger ::= INTEGER(0..MAX)", definition.trim());
	}

}
