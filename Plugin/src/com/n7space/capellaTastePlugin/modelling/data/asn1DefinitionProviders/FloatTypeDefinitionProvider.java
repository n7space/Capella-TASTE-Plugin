// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.data.asn1definitionproviders;

import com.n7space.capellatasteplugin.modelling.Asn1ElementDefinitionGenerator.Asn1ElementDefinitionProvider;
import com.n7space.capellatasteplugin.modelling.NameConverter;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataModelElement;
import com.n7space.capellatasteplugin.modelling.data.FloatDataType;

/**
 * Class providing ASN.1 definition for a float data type.
 *
 * @see FloatDataType
 */
public class FloatTypeDefinitionProvider extends BaseDefinitionProvider implements Asn1ElementDefinitionProvider {

	/**
	 * The constructor.
	 *
	 * @param converter
	 *            Name converter
	 */
	public FloatTypeDefinitionProvider(final NameConverter converter) {
		super(converter);
	}

	protected String getBaseTypeName(final FloatDataType dataType) {
		if (dataType.parent == null) {
			return "REAL";
		}
		return nameConverter.getAsn1TypeName(dataType.parent.dataPackage.name, dataType.parent.name,
				dataType.parent.id);
	}

	protected String getUnit(final FloatDataType dataType) {
		if (dataType.unit == null) {
			return "";
		}
		return "-- Unit: " + dataType.unit.name.trim().replace('\n', ' ') + "\n";
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
		final FloatDataType dataType = (FloatDataType) element;
		final String baseTypeName = getBaseTypeName(dataType);
		final String comment = getUnit(dataType) + getComment(dataType);
		if (dataType.lowerBound != null || dataType.upperBound != null) {
			final String lowerBoundSpec = dataType.lowerBound != null ? dataType.lowerBound.getValue(nameConverter)
					: "MIN";
			final String upperBoundSpec = dataType.upperBound != null ? dataType.upperBound.getValue(nameConverter)
					: "MAX";
			return comment + nameConverter.getAsn1TypeName(dataType.dataPackage.name, dataType.name, dataType.id)
					+ " ::= " + baseTypeName + "(" + lowerBoundSpec + ".." + upperBoundSpec + ")\n\n";
		}
		return comment + nameConverter.getAsn1TypeName(dataType.dataPackage.name, dataType.name, dataType.id) + " ::= "
				+ baseTypeName + "\n\n";
	}
}
