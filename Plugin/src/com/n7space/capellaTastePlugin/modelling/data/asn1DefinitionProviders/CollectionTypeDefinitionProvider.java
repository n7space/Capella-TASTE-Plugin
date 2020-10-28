// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.data.asn1definitionproviders;

import com.n7space.capellatasteplugin.modelling.Asn1ElementDefinitionGenerator.Asn1ElementDefinitionProvider;
import com.n7space.capellatasteplugin.modelling.NameConverter;
import com.n7space.capellatasteplugin.modelling.data.CollectionDataType;
import com.n7space.capellatasteplugin.modelling.data.CollectionDataType.Ordering;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataModelElement;

/**
 * Class providing ASN.1 definition for a collection data type.
 * 
 * @see CollectionDataType
 */
public class CollectionTypeDefinitionProvider extends BaseDefinitionProvider implements Asn1ElementDefinitionProvider {

	/**
	 * The constructor.
	 * 
	 * @param converter
	 *            Name converter
	 */
	public CollectionTypeDefinitionProvider(final NameConverter converter) {
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
		final StringBuilder sb = new StringBuilder();
		final CollectionDataType collection = (CollectionDataType) element;
		final String type = collection.ordering == Ordering.Ordered ? "SEQUENCE" : "SET";

		sb.append(getComment(collection));
		sb.append(nameConverter.getAsn1TypeName(collection.dataPackage.name, collection.name, collection.id) + " ::= ");
		sb.append(type + "(SIZE(" + collection.minimumCardinality.getValue(nameConverter) + ".."
				+ collection.maximumCardinality.getValue(nameConverter) + ")) OF ");
		sb.append(nameConverter.getAsn1TypeName(collection.dataType.dataPackage.name, collection.dataType.name,
				collection.dataType.id));
		sb.append("\n\n");

		return sb.toString();
	}

}
