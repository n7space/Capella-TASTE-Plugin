// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.data.asn1definitionproviders;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;

import com.n7space.capellatasteplugin.modelling.NameConverter;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.modelling.data.DataType;
import com.n7space.capellatasteplugin.modelling.data.DataType.DataTypeValue;
import com.n7space.capellatasteplugin.modelling.data.DataTypeReference;
import com.n7space.capellatasteplugin.modelling.data.MemberDefinition;
import com.n7space.capellatasteplugin.modelling.data.StructuredDataType;
import com.n7space.capellatasteplugin.modelling.data.StructuredDataType.Kind;
import com.n7space.capellatasteplugin.modelling.data.UsedNumericValue;

public class StructuredTypeDefinitionProviderTests {

	protected DataPackage pkg = null;
	protected NameConverter nameConverter = null;
	protected StructuredTypeDefinitionProvider provider = null;
	protected DataModel model = null;
	protected DataType type1 = null;
	protected DataType type2 = null;
	protected DataType intType = null;

	@Before
	public void setUp() throws Exception {
		pkg = new DataPackage("DummyPkg", "DummyPkg" + "_id");
		nameConverter = new NameConverter();
		provider = new StructuredTypeDefinitionProvider(nameConverter);

		type1 = TypeAssembler.createBasicIntegerDataType(pkg, "Type1");
		type2 = TypeAssembler.createBasicIntegerDataType(pkg, "Type2");
		intType = TypeAssembler.createBasicIntegerDataType(pkg, "IntegerType");
		pkg.addTypeDefinition(type1);
		pkg.addTypeDefinition(type2);
		pkg.addTypeDefinition(intType);
		model = new DataModel();
		model.dataPackages.add(pkg);
	}

	@Test
	public void testProvideAsn1Definition_forArrayMembers() {
		final DataTypeValue maxCadinality = new DataTypeValue(pkg, "IntValue", "IntValue" + "_id",
				new DataTypeReference(intType), "12");
		pkg.addValueDefinition(maxCadinality);

		final StructuredDataType type = new StructuredDataType(pkg, "StructuredType", "StructuredType" + "_id",
				Kind.Structure);
		pkg.addTypeDefinition(type);
		type.members.add(new MemberDefinition("Member1", "Member1" + "_id", new DataTypeReference(type1),
				new UsedNumericValue.ExplicitIntegerValue(BigInteger.valueOf(7)),
				new UsedNumericValue.SpecialNumericValue(UsedNumericValue.SpecialNumericValue.Kind.STAR)));
		type.members.add(new MemberDefinition("Member2", "Member2" + "_id", new DataTypeReference(type2),
				new UsedNumericValue.ExplicitIntegerValue(BigInteger.valueOf(0)),
				new UsedNumericValue.ReferencedNumericValue(maxCadinality)));
		final String definition = provider.provideAsn1Definition(model, type);
		assertEquals(definition,
				"StructuredType ::= SEQUENCE {\n\tmember1 SEQUENCE(SIZE(7..MAX)) OF Type1,\n\tmember2 SEQUENCE(SIZE(0..intValue)) OF Type2\n}\n\n");
	}

	@Test
	public void testProvideAsn1Definition_forClass() {
		final StructuredDataType type = new StructuredDataType(pkg, "StructuredType", "StructuredType" + "_id",
				Kind.Structure);
		pkg.addTypeDefinition(type);
		type.members.add(new MemberDefinition("Member1", "Member1" + "_id", new DataTypeReference(type1)));
		type.members.add(new MemberDefinition("Member2", "Member2" + "_id", new DataTypeReference(type2)));
		final String definition = provider.provideAsn1Definition(model, type);
		assertEquals(definition, "StructuredType ::= SEQUENCE {\n\tmember1 Type1,\n\tmember2 Type2\n}\n\n");
	}

	@Test
	public void testProvideAsn1Definition_forClassWithComment() {
		final StructuredDataType type = new StructuredDataType(pkg, "StructuredType", "StructuredType" + "_id",
				Kind.Structure);
		type.description = "A comment";
		pkg.addTypeDefinition(type);
		type.members.add(new MemberDefinition("Member1", "Member1" + "_id", new DataTypeReference(type1)));
		type.members.add(new MemberDefinition("Member2", "Member2" + "_id", new DataTypeReference(type2)));
		final String definition = provider.provideAsn1Definition(model, type);
		assertEquals(definition,
				"-- A comment\nStructuredType ::= SEQUENCE {\n\tmember1 Type1,\n\tmember2 Type2\n}\n\n");
	}

	@Test
	public void testProvideAsn1Definition_forClassWithSuperclass() {
		final StructuredDataType parentType = new StructuredDataType(pkg, "ParentType", "ParentType" + "_id",
				Kind.Structure);
		pkg.addTypeDefinition(parentType);
		parentType.members
				.add(new MemberDefinition("ParentMember1", "ParentMember1" + "_id", new DataTypeReference(type1)));
		parentType.members
				.add(new MemberDefinition("ParentMember2", "ParentMember2" + "_id", new DataTypeReference(type2)));

		final StructuredDataType type = new StructuredDataType(pkg, new DataTypeReference(parentType), "StructuredType",
				"StructuredType" + "_id", Kind.Structure);
		pkg.addTypeDefinition(type);
		type.members.add(new MemberDefinition("Member1", "Member1" + "_id", new DataTypeReference(type1)));
		type.members.add(new MemberDefinition("Member2", "Member2" + "_id", new DataTypeReference(type2)));
		final String parentDefinition = provider.provideAsn1Definition(model, parentType);
		assertEquals(parentDefinition, "ParentType ::= SEQUENCE {\n" + "\trealization CHOICE {\n"
				+ "\t\trealizationStructuredType StructuredType,\n" + "\t\trealizationParentType SEQUENCE {\n"
				+ "\t\t\tparentMember1 Type1,\n" + "\t\t\tparentMember2 Type2\n" + "\t\t}\n" + "\t}\n" + "}\n\n");

		final String definition = provider.provideAsn1Definition(model, type);
		assertEquals(definition,
				"StructuredType ::= SEQUENCE {\n\tparentMember1 Type1,\n\tparentMember2 Type2,\n\tmember1 Type1,\n\tmember2 Type2\n}\n\n");
	}

	@Test
	public void testProvideAsn1Definition_forUnion() {
		final StructuredDataType type = new StructuredDataType(pkg, "StructuredType", "StructuredType" + "_id",
				Kind.Union);
		pkg.addTypeDefinition(type);
		type.members.add(new MemberDefinition("Member1", "Member1" + "_id", new DataTypeReference(type1)));
		type.members.add(new MemberDefinition("Member2", "Member2" + "_id", new DataTypeReference(type2)));
		final String definition = provider.provideAsn1Definition(model, type);
		assertEquals(definition,
				"StructuredType ::= SEQUENCE {\n\tvalue CHOICE {\n\t\tmember1 Type1,\n\t\tmember2 Type2\n\t}\n}\n\n");
	}

}
