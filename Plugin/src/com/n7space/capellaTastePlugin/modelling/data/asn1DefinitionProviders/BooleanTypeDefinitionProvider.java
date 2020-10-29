// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.data.asn1definitionproviders;

import com.n7space.capellatasteplugin.modelling.Asn1ElementDefinitionGenerator.Asn1ElementDefinitionProvider;
import com.n7space.capellatasteplugin.modelling.NameConverter;
import com.n7space.capellatasteplugin.modelling.data.BooleanDataType;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataModelElement;

/**
 * Class providing ASN.1 definition for a boolean data type.
 * 
 * @see BooleanDataType
 */
public class BooleanTypeDefinitionProvider extends BaseDefinitionProvider implements Asn1ElementDefinitionProvider {

	/**
	 * The constructor.
	 * 
	 * @param converter
	 *            Name converter
	 */
	public BooleanTypeDefinitionProvider(final NameConverter converter) {
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
		final BooleanDataType dataType = (BooleanDataType) element;

		return getComment(dataType)
				+ nameConverter.getAsn1TypeName(dataType.dataPackage.name, dataType.name, dataType.id)
				+ " ::= BOOLEAN\n\n";
	}

}
