// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.data.asn1definitionproviders;

import com.n7space.capellatasteplugin.modelling.data.BooleanDataType;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.modelling.data.DataType;
import com.n7space.capellatasteplugin.modelling.data.DataType.DataTypeValue;
import com.n7space.capellatasteplugin.modelling.data.DataTypeReference;
import com.n7space.capellatasteplugin.modelling.data.EnumeratedDataType;
import com.n7space.capellatasteplugin.modelling.data.FloatDataType;
import com.n7space.capellatasteplugin.modelling.data.IntegerDataType;
import com.n7space.capellatasteplugin.modelling.data.StringDataType;

public class TypeAssembler {

	public static BooleanDataType createBasicBooleanDataType(final DataPackage pkg, final String name) {
		final BooleanDataType type = new BooleanDataType(pkg, name, name + "_id");
		pkg.addTypeDefinition(type);
		return type;
	}

	public static DataPackage createBasicDataPackage(final DataModel model, final String name) {
		final DataPackage pkg = new DataPackage(name, name + "_id");
		model.dataPackages.add(pkg);
		return pkg;
	}

	public static EnumeratedDataType createBasicEnumeratedDataType(final DataPackage pkg, final String name) {
		final EnumeratedDataType type = new EnumeratedDataType(pkg, name, name + "_id");
		pkg.addTypeDefinition(type);
		return type;
	}

	public static FloatDataType createBasicFloatDataType(final DataPackage pkg, final String name) {
		final FloatDataType type = new FloatDataType(pkg, name, name + "_id");
		pkg.addTypeDefinition(type);
		return type;
	}

	public static FloatDataType createBasicFloatDataTypeWithParent(final DataPackage pkg, final FloatDataType parent,
			final String name) {
		final FloatDataType type = new FloatDataType(pkg, new DataTypeReference(parent), name, name + "_id");
		pkg.addTypeDefinition(type);
		return type;
	}

	public static IntegerDataType createBasicIntegerDataType(final DataPackage pkg, final String name) {
		final IntegerDataType type = new IntegerDataType(pkg, name, name + "_id");
		pkg.addTypeDefinition(type);
		return type;
	}

	public static IntegerDataType createBasicIntegerDataTypeWithParent(final DataPackage pkg,
			final IntegerDataType parent, final String name) {
		final IntegerDataType type = new IntegerDataType(pkg, new DataTypeReference(parent), name, name + "_id");
		pkg.addTypeDefinition(type);
		return type;
	}

	public static StringDataType createBasicStringDataType(final DataPackage pkg, final String name) {
		final StringDataType type = new StringDataType(pkg, name, name + "_id");
		pkg.addTypeDefinition(type);
		return type;
	}

	public static StringDataType createBasicStringDataTypeWithParent(final DataPackage pkg, final StringDataType parent,
			final String name) {
		final StringDataType type = new StringDataType(pkg, new DataTypeReference(parent), name, name + "_id");
		pkg.addTypeDefinition(type);
		return type;
	}

	public static DataTypeValue createDataValue(final DataPackage pkg, final String name, final DataType type,
			final Object containedValue) {
		final DataTypeValue value = new DataTypeValue(pkg, name, name + "_id",
				new DataTypeReference(type.dataPackage, type.name, type.id), containedValue);
		pkg.addValueDefinition(value);
		return value;
	}

}
