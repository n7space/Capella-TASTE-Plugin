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
import com.n7space.capellatasteplugin.modelling.data.CollectionDataType;
import com.n7space.capellatasteplugin.modelling.data.CollectionDataType.Ordering;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.modelling.data.DataType;
import com.n7space.capellatasteplugin.modelling.data.DataTypeReference;
import com.n7space.capellatasteplugin.modelling.data.UsedNumericValue.ExplicitIntegerValue;

public class CollectionTypeDefinitionProviderTests {

	@Test
	public void testProvideAsn1DefinitionForAnOrderedCollection() {
		final NameConverter nameConverter = new NameConverter();
		final DataModel model = new DataModel();
		final DataPackage pkg = TypeAssembler.createBasicDataPackage(model, "testPkg");
		final DataType containedType = TypeAssembler.createBasicIntegerDataType(pkg, "containedInt");
		final CollectionDataType type = new CollectionDataType(pkg, "testCollection", "tc", Ordering.Ordered,
				new DataTypeReference(containedType), new ExplicitIntegerValue(BigInteger.valueOf(12)),
				new ExplicitIntegerValue(BigInteger.valueOf(31)));
		pkg.addTypeDefinition(type);

		final CollectionTypeDefinitionProvider provider = new CollectionTypeDefinitionProvider(nameConverter);

		final String definition = provider.provideAsn1Definition(model, type);
		assertEquals("TestCollection ::= SEQUENCE(SIZE(12..31)) OF ContainedInt", definition.trim());
	}

	@Test
	public void testProvideAsn1DefinitionForAnUnorderedCollection() {
		final NameConverter nameConverter = new NameConverter();
		final DataModel model = new DataModel();
		final DataPackage pkg = TypeAssembler.createBasicDataPackage(model, "testPkg");
		final DataType containedType = TypeAssembler.createBasicIntegerDataType(pkg, "containedInt");
		final CollectionDataType type = new CollectionDataType(pkg, "testCollection", "tc", Ordering.Unordered,
				new DataTypeReference(containedType), new ExplicitIntegerValue(BigInteger.valueOf(0)),
				new ExplicitIntegerValue(BigInteger.valueOf(31)));
		pkg.addTypeDefinition(type);

		final CollectionTypeDefinitionProvider provider = new CollectionTypeDefinitionProvider(nameConverter);

		final String definition = provider.provideAsn1Definition(model, type);
		assertEquals("TestCollection ::= SET(SIZE(0..31)) OF ContainedInt", definition.trim());

	}

}
