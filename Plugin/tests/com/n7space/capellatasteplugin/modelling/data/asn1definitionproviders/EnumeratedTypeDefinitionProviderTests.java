// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.data.asn1definitionproviders;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.n7space.capellatasteplugin.modelling.NameConverter;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.modelling.data.EnumeratedDataType;
import com.n7space.capellatasteplugin.modelling.data.EnumeratedDataType.Literal;
import com.n7space.capellatasteplugin.modelling.data.IntegerDataType;

public class EnumeratedTypeDefinitionProviderTests {

	@Test
	public void testProvideAsn1Definition() {
		final NameConverter nameConverter = new NameConverter();
		final DataModel model = new DataModel();
		final DataPackage pkg = TypeAssembler.createBasicDataPackage(model, "testPkg");
		final EnumeratedDataType type = TypeAssembler.createBasicEnumeratedDataType(pkg, "testEnum");
		type.literals.add(new Literal(type, "literal1", "1"));
		type.literals.add(new Literal(type, "literal2", "2"));
		type.literals.add(new Literal(type, "literal3", "3"));

		final EnumeratedTypeDefinitionProvider provider = new EnumeratedTypeDefinitionProvider(nameConverter);

		final String definition = provider.provideAsn1Definition(model, type);
		assertEquals("TestEnum ::= ENUMERATED {\n\tliteral1,\n\tliteral2,\n\tliteral3\n}", definition.trim());
	}

	@Test
	public void testProvideAsn1DefinitionForAValuedLiteral() {
		final NameConverter nameConverter = new NameConverter();
		final DataModel model = new DataModel();
		final DataPackage pkg = TypeAssembler.createBasicDataPackage(model, "testPkg");
		final IntegerDataType integerType = TypeAssembler.createBasicIntegerDataType(pkg, "myInt");

		final EnumeratedDataType type = TypeAssembler.createBasicEnumeratedDataType(pkg, "testEnum");
		final Literal literal = new Literal(type, "literal", "1");
		literal.domainValue = TypeAssembler.createDataValue(pkg, "literalValue", integerType, Integer.valueOf(16));
		type.literals.add(literal);

		final EnumeratedTypeDefinitionProvider provider = new EnumeratedTypeDefinitionProvider(nameConverter);

		final String definition = provider.provideAsn1Definition(model, type);
		assertEquals("TestEnum ::= ENUMERATED {\n\tliteral (16)\n}", definition.trim());
	}

}
