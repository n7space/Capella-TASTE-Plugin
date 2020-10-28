// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.Test;

import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.modelling.data.DataTypeReference;
import com.n7space.capellatasteplugin.modelling.data.IntegerDataType;
import com.n7space.capellatasteplugin.modelling.data.asn1definitionproviders.IntegerTypeDefinitionProvider;

public class Asn1ModelGeneratorTests {

	private NameConverter nameConverter = null;
	private Asn1ElementDefinitionGenerator definitionGenerator = null;
	private IntegerDataType dummyType = null;
	private DataPackage dummyPackage = null;
	private DataPackage importedPackage = null;
	private IntegerDataType importedType = null;
	private Asn1ModelGenerator generator = null;
	private DataModel model = null;

	@Before
	public void setUp() throws Exception {
		model = new DataModel();
		nameConverter = new NameConverter();
		definitionGenerator = new Asn1ElementDefinitionGenerator();
		definitionGenerator.registerDefinitionProvider(IntegerDataType.class,
				new IntegerTypeDefinitionProvider(nameConverter));

		importedPackage = new DataPackage("ImportedPackage", "ImportedPackage" + "_id");
		importedType = new IntegerDataType(importedPackage, "ImportedType", "ImportedType" + "_id");
		importedPackage.addTypeDefinition(importedType);

		model.dataPackages.add(importedPackage);

		dummyPackage = new DataPackage("DummyPackage", "DummyPackage" + "_id");
		dummyType = new IntegerDataType(dummyPackage, new DataTypeReference(importedType), "DummyType",
				"DummyType" + "_id");
		dummyPackage.addTypeDefinition(dummyType);

		model.dataPackages.add(dummyPackage);

		generator = new Asn1ModelGenerator(nameConverter, definitionGenerator);
	}

	@Test
	public void testAppendDefinitions() {
		final StringBuilder sb = new StringBuilder();
		generator.appendDefinitions(sb, model, dummyPackage);
		assertEquals(sb.toString(), "DummyType ::= ImportedType\n\n");
	}

	@Test
	public void testAppendPackageDefinitionEnd() {
		final StringBuilder sb = new StringBuilder();
		generator.appendPackageDefinitionEnd(sb, dummyPackage.name);
		assertEquals(sb.toString(), "\nEND\n");
	}

	@Test
	public void testAppendPackageDefinitionStart() {
		final StringBuilder sb = new StringBuilder();
		generator.appendPackageDefinitionStart(sb, dummyPackage.name);
		assertEquals(sb.toString(), "DummyPackage DEFINITIONS AUTOMATIC TAGS ::= BEGIN\n\n");
	}

	@Test
	public void testAppendPackageImports() {
		final StringBuilder sb = new StringBuilder();
		generator.appendPackageImports(sb, model, dummyPackage);
		assertEquals(sb.toString(), "IMPORTS\n\tImportedType\n\t\tFROM ImportedPackage\n\t;\n\n");
	}

	@Test
	public void testGenerateAsn1DataModelFromAbstractDataModelDataModel() {
		final ModelItems items = generator.generateAsn1DataModelFromAbstractDataModel(model);
		assertEquals(items.size(), 2);
		assertEquals(items.get(0).kind, ModelItem.Kind.ASN1);
		assertEquals(items.get(0).name, "ImportedPackage");
		assertTrue(items.get(0).buffer.toString().length() > 0);
		assertEquals(items.get(1).kind, ModelItem.Kind.ASN1);
		assertEquals(items.get(1).name, "DummyPackage");
		assertTrue(items.get(1).buffer.toString().length() > 0);
	}

	@Test
	public void testGenerateAsn1DataModelFromAbstractDataModelDataModelDataPackage() {
		final ModelItems items = generator.generateAsn1DataModelFromAbstractDataModel(model, dummyPackage);
		assertEquals(items.size(), 1);
		assertEquals(items.get(0).kind, ModelItem.Kind.ASN1);
		assertEquals(items.get(0).name, "DummyPackage");
		assertTrue(items.get(0).buffer.toString().length() > 0);
	}

	@Test
	public void testAddsForcedImports() {

		final DataPackage pkg = new DataPackage("PKG", "PKG_id");
		pkg.addForcedImport(new DataTypeReference(importedType));

		final ModelItems items = generator.generateAsn1DataModelFromAbstractDataModel(model, pkg);
		assertEquals(items.size(), 1);
		assertEquals(items.get(0).kind, ModelItem.Kind.ASN1);
		assertEquals(items.get(0).name, "PKG");
		try {
			assertEquals(new String(items.get(0).buffer.array(), "utf-8"),
					"PKG DEFINITIONS AUTOMATIC TAGS ::= BEGIN\n" + "\n" + "IMPORTS\n" + "\tImportedType\n"
							+ "\t\tFROM ImportedPackage\n" + "\t;\n" + "\n" + "\n" + "END\n");
		} catch (final UnsupportedEncodingException e) {
			assertEquals(e.toString(), "");
		}
	}

}
