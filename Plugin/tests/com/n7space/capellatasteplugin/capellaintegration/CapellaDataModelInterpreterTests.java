// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.polarsys.capella.core.data.information.DataPkg;
import org.polarsys.capella.core.data.information.ExchangeMechanism;
import org.polarsys.capella.core.data.information.datatype.NumericTypeKind;

import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.ClassModelElementInterpretationProvider;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.CollectionElementInterpretationProvider;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.DataModelElementInterpreter;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.DataPackageInterpretationProvider;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.ExchangeItemElementInterpretationProvider;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.NumericElementInterpretationProvider;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.NumericValueElementInterpretationProvider;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.ClassTypeMock;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.DataPackageMock;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.ExchangeItemTypeMock;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.NumericTypeMock;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.NumericTypeMock.NumericValueMock;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.modelling.data.DataType;
import com.n7space.capellatasteplugin.modelling.data.DataType.DataTypeValue;
import com.n7space.capellatasteplugin.modelling.data.DataTypeReference;
import com.n7space.capellatasteplugin.modelling.data.EnumeratedDataType;
import com.n7space.capellatasteplugin.modelling.data.IntegerDataType;
import com.n7space.capellatasteplugin.modelling.data.MemberDefinition;
import com.n7space.capellatasteplugin.modelling.data.StructuredDataType;
import com.n7space.capellatasteplugin.modelling.data.StructuredDataType.Kind;
import com.n7space.capellatasteplugin.modelling.data.Unit;
import com.n7space.capellatasteplugin.utils.Issue;

public class CapellaDataModelInterpreterTests {

	protected DataModelElementInterpreter elementInterpreter;
	protected CapellaDataModelInterpreter interpreter;
	protected DataPackageMock pkgMock;
	protected NumericTypeMock typeMock;
	protected ClassTypeMock classMock;
	protected NumericValueMock valueMock;
	protected ExchangeItemTypeMock exchangeItemMock;

	protected List<Issue> issues;

	@Before
	public void setUp() throws Exception {
		elementInterpreter = new DataModelElementInterpreter();

		elementInterpreter.registerInterpretationProvider(org.polarsys.capella.core.data.information.DataPkg.class,
				new DataPackageInterpretationProvider());

		elementInterpreter.registerInterpretationProvider(
				org.polarsys.capella.core.data.information.datatype.NumericType.class,
				new NumericElementInterpretationProvider());
		elementInterpreter.registerInterpretationProvider(
				org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue.class,
				new NumericValueElementInterpretationProvider());
		elementInterpreter.registerInterpretationProvider(org.polarsys.capella.core.data.information.ExchangeItem.class,
				new ExchangeItemElementInterpretationProvider());
		elementInterpreter.registerInterpretationProvider(org.polarsys.capella.core.data.information.Collection.class,
				new CollectionElementInterpretationProvider());
		elementInterpreter.registerInterpretationProvider(org.polarsys.capella.core.data.information.Class.class,
				new ClassModelElementInterpretationProvider());

		interpreter = new CapellaDataModelInterpreter(elementInterpreter);
		pkgMock = new DataPackageMock("Pkg1", "Pkg1" + "_id");
		exchangeItemMock = new ExchangeItemTypeMock(pkgMock, "ExchangeItemMock", "ExchangeItemMock" + "_id",
				ExchangeMechanism.EVENT);
		classMock = new ClassTypeMock(pkgMock, "ClassMock", "ClassMock" + "_id");
		typeMock = new NumericTypeMock(pkgMock, "FloatMock", "FloatMock" + "_id", NumericTypeKind.FLOAT);
		;
		valueMock = new NumericValueMock(pkgMock, "Value", "Value" + "_id", typeMock, "0");
		pkgMock.classes.add(classMock);
		pkgMock.dataTypes.add(typeMock);
		pkgMock.exchangeItems.add(exchangeItemMock);
		pkgMock.dataValues.add(valueMock);

		issues = new LinkedList<Issue>();
	}

	@Test
	public void testConvertCapellaDataModelToAbstractDataModel() {
		final List<DataPkg> pkgs = new LinkedList<DataPkg>();
		pkgs.add(pkgMock);
		final DataModel model = interpreter.convertCapellaDataModelToAbstractDataModel(pkgs, issues);
		assertEquals(model.dataPackages.size(), 1);
		assertEquals(model.dataPackages.get(0).name, pkgMock.getName());
		assertTrue(model.findDataTypeById(classMock.getId()) != null);
		assertTrue(model.findDataTypeById(typeMock.getId()) != null);
		assertTrue(model.findDataTypeById(exchangeItemMock.getId()) != null);
		assertTrue(model.findDataValueById(valueMock.getId()) != null);
	}

	@Test
	public void testAppendUnits() {
		final DataModel model = new DataModel();
		final DataPackage pkg1 = new DataPackage("Pkg1", "Pkg1" + "_id");
		final DataPackage pkg2 = new DataPackage("Pkg1", "Pkg2" + "_id");
		model.dataPackages.add(pkg1);
		model.dataPackages.add(pkg2);

		final Unit unit = new Unit("Volt");
		final IntegerDataType type1 = new IntegerDataType(pkg1, "Voltage", "voltage_id");
		type1.unit = unit;
		pkg1.addTypeDefinition(type1);

		final IntegerDataType type2 = new IntegerDataType(pkg2, new DataTypeReference(type1), "Cpu Voltage",
				"Cpu Voltage_id");
		pkg2.addTypeDefinition(type2);

		final DataType.DataTypeValue value1 = new DataTypeValue(pkg1, "Value1", "Value1_id",
				new DataTypeReference(type1), Integer.valueOf(12));
		pkg1.addValueDefinition(value1);
		final DataType.DataTypeValue value2 = new DataTypeValue(pkg2, "Value2", "Value2_id",
				new DataTypeReference(type1), Integer.valueOf(13));
		pkg1.addValueDefinition(value2);

		final StructuredDataType struct1 = new StructuredDataType(pkg1, "Struct1", "Struct1_id", Kind.Structure);
		final MemberDefinition member1 = new MemberDefinition("m1", "m1Id", new DataTypeReference(type1));
		final MemberDefinition member2 = new MemberDefinition("m2", "m2Id", new DataTypeReference(type2));
		struct1.members.add(member1);
		struct1.members.add(member2);

		pkg1.addTypeDefinition(struct1);

		final EnumeratedDataType enum1 = new EnumeratedDataType(pkg1, "Enum1", "Enum1_id");
		final EnumeratedDataType.Literal literal1 = new EnumeratedDataType.Literal(enum1, "Literal1", "Literal1_id");
		literal1.domainValue = new DataType.DataTypeValue(pkg1, "DomainValue1", "DomainValue1_id",
				new DataTypeReference(type1), Integer.valueOf(14));
		enum1.literals.add(literal1);

		pkg1.addTypeDefinition(enum1);

		interpreter.createPostfixesFromUnits(model);
		assertEquals(type1.name, "Voltage-Volt");
		assertEquals(type2.parent.name, "Voltage-Volt");
		assertEquals(value1.type.name, "Voltage-Volt");
		assertEquals(value2.type.name, "Voltage-Volt");
		assertEquals(struct1.members.get(0).dataType.name, "Voltage-Volt");
		assertEquals(((DataType.DataTypeValue) (enum1.literals.get(0).domainValue)).type.name, "Voltage-Volt");

	}

}
