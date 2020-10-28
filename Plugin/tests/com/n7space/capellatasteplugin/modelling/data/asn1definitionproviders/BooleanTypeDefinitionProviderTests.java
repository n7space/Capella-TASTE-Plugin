// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.data.asn1definitionproviders;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.n7space.capellatasteplugin.modelling.NameConverter;
import com.n7space.capellatasteplugin.modelling.data.BooleanDataType;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;

public class BooleanTypeDefinitionProviderTests {

	@Test
	public void testProvideAsn1Definition() {
		final NameConverter nameConverter = new NameConverter();
		final DataModel model = new DataModel();
		final DataPackage pkg = TypeAssembler.createBasicDataPackage(model, "testPkg");
		final BooleanDataType type = TypeAssembler.createBasicBooleanDataType(pkg, "testBool");

		final BooleanTypeDefinitionProvider provider = new BooleanTypeDefinitionProvider(nameConverter);

		final String definition = provider.provideAsn1Definition(model, type);
		assertEquals("TestBool ::= BOOLEAN", definition.trim());

	}

}
