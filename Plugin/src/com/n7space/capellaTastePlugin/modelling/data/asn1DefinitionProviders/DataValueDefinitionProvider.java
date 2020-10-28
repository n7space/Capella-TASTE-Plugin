// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.data.asn1definitionproviders;

import com.n7space.capellatasteplugin.modelling.Asn1ElementDefinitionGenerator.Asn1ElementDefinitionProvider;
import com.n7space.capellatasteplugin.modelling.NameConverter;
import com.n7space.capellatasteplugin.modelling.data.BooleanDataType;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataModelElement;
import com.n7space.capellatasteplugin.modelling.data.DataType;
import com.n7space.capellatasteplugin.modelling.data.FloatDataType;
import com.n7space.capellatasteplugin.modelling.data.IntegerDataType;
import com.n7space.capellatasteplugin.modelling.data.StringDataType;
import com.n7space.capellatasteplugin.utils.StringUtils;

/**
 * Class providing ASN.1 definition for a data value.
 *
 * @see DataType.DataTypeValue
 */
public class DataValueDefinitionProvider extends BaseDefinitionProvider implements Asn1ElementDefinitionProvider {

	/**
	 * The constructor.
	 *
	 * @param converter
	 *            Name converter
	 */
	public DataValueDefinitionProvider(final NameConverter converter) {
		super(converter);
	}

	/**
	 * Provides ASN.1 definition for the given data model element.
	 *
	 * @param model
	 *            Data model
	 * @param element
	 *            Data model element
	 * @return ASN.1 definition
	 */
	@Override
	public String provideAsn1Definition(final DataModel model, final DataModelElement element) {
		final DataType.DataTypeValue value = (DataType.DataTypeValue) element;
		final DataType type = model.findDataTypeById(value.type.id);
		if (type == null)
			return "";
		if (type instanceof IntegerDataType) {

			return nameConverter.getAsn1IdentifierName(value.dataPackage.name, value.name, value.id) + " "
					+ nameConverter.getAsn1TypeName(type.dataPackage.name, type.name, type.id) + " ::= "
					+ StringUtils.cleanUpIntegerValue(value.value) + "\n\n";
		} else if (type instanceof FloatDataType) {
			final String valueSpec = value.value.toString();
			valueSpec.replaceAll(",", ".");
			return nameConverter.getAsn1IdentifierName(value.dataPackage.name, value.name, value.id) + " "
					+ nameConverter.getAsn1TypeName(type.dataPackage.name, type.name, type.id) + " ::= " + valueSpec
					+ "\n\n";
		} else if (type instanceof StringDataType) {
			return nameConverter.getAsn1IdentifierName(value.dataPackage.name, value.name, value.id) + " "
					+ nameConverter.getAsn1TypeName(type.dataPackage.name, type.name, type.id) + " ::= \""
					+ value.value.toString() + "\"\n\n";
		} else if (type instanceof BooleanDataType) {
			return nameConverter.getAsn1IdentifierName(value.dataPackage.name, value.name, value.id) + " "
					+ nameConverter.getAsn1TypeName(type.dataPackage.name, type.name, type.id) + " ::= "
					+ value.value.toString().toUpperCase() + "\n\n";
		}
		return nameConverter.getAsn1IdentifierName(value.dataPackage.name, value.name, value.id) + " "
				+ nameConverter.getAsn1TypeName(type.dataPackage.name, type.name, type.id) + " ::= "
				+ StringUtils.cleanUpIntegerValue(value.value) + "\n\n";
	}
}
