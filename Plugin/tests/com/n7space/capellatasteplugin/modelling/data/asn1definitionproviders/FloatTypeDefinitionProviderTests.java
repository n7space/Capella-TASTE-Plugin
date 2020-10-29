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
import com.n7space.capellatasteplugin.modelling.data.FloatDataType;
import com.n7space.capellatasteplugin.modelling.data.UsedNumericValue.ExplicitRealValue;

public class FloatTypeDefinitionProviderTests {

	@Test
	public void testProvideAsn1Definition() {
		final NameConverter nameConverter = new NameConverter();
		final DataModel model = new DataModel();
		final DataPackage pkg = TypeAssembler.createBasicDataPackage(model, "testPkg");
		final FloatDataType type = TypeAssembler.createBasicFloatDataType(pkg, "testFloat");

		final FloatTypeDefinitionProvider provider = new FloatTypeDefinitionProvider(nameConverter);

		final String definition = provider.provideAsn1Definition(model, type);
		assertEquals("TestFloat ::= REAL", definition.trim());
	}

	@Test
	public void testProvideAsn1Definition_withSuperclass() {
		final NameConverter nameConverter = new NameConverter();
		final DataModel model = new DataModel();
		final DataPackage pkg = TypeAssembler.createBasicDataPackage(model, "testPkg");
		final FloatDataType parentType = TypeAssembler.createBasicFloatDataType(pkg, "parentFloat");
		final FloatDataType type = TypeAssembler.createBasicFloatDataTypeWithParent(pkg, parentType, "testFloat");

		final FloatTypeDefinitionProvider provider = new FloatTypeDefinitionProvider(nameConverter);

		final String definition = provider.provideAsn1Definition(model, type);
		assertEquals("TestFloat ::= ParentFloat", definition.trim());
	}

	@Test
	public void testProvideAsn1Definition_withBounds() {
		final NameConverter nameConverter = new NameConverter();
		final DataModel model = new DataModel();
		final DataPackage pkg = TypeAssembler.createBasicDataPackage(model, "testPkg");
		final FloatDataType parentType = TypeAssembler.createBasicFloatDataType(pkg, "parentFloat");
		final FloatDataType type = TypeAssembler.createBasicFloatDataTypeWithParent(pkg, parentType, "testFloat");
		type.lowerBound = new ExplicitRealValue(12.3);
		type.upperBound = new ExplicitRealValue(16.6);

		final FloatTypeDefinitionProvider provider = new FloatTypeDefinitionProvider(nameConverter);

		final String definition = provider.provideAsn1Definition(model, type);
		assertEquals("TestFloat ::= ParentFloat(12.3..16.6)", definition.trim());
	}

}
