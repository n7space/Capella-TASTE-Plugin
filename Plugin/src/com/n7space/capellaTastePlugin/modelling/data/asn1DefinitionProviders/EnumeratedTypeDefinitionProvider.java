// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.data.asn1definitionproviders;

import com.n7space.capellatasteplugin.modelling.Asn1ElementDefinitionGenerator.Asn1ElementDefinitionProvider;
import com.n7space.capellatasteplugin.modelling.NameConverter;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataModelElement;
import com.n7space.capellatasteplugin.modelling.data.EnumeratedDataType;
import com.n7space.capellatasteplugin.modelling.data.EnumeratedDataType.Literal;

/**
 * Class providing ASN.1 definition for an enumerated data type.
 * 
 * @see EnumeratedDataType
 */
public class EnumeratedTypeDefinitionProvider extends BaseDefinitionProvider implements Asn1ElementDefinitionProvider {

	/**
	 * The constructor.
	 * 
	 * @param converter
	 *            Name converter
	 */
	public EnumeratedTypeDefinitionProvider(final NameConverter converter) {
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
		final EnumeratedDataType dataType = (EnumeratedDataType) element;
		final StringBuilder sb = new StringBuilder();
		sb.append(getComment(dataType));
		sb.append(nameConverter.getAsn1TypeName(dataType.dataPackage.name, dataType.name, dataType.id)
				+ " ::= ENUMERATED {\n");
		for (int i = 0; i < dataType.literals.size(); i++) {
			final Literal literal = dataType.literals.get(i);
			final String integerValue = getIntegerValueOfAModelElement(model, literal.domainValue);
			if (integerValue != null) {
				sb.append(getIndent()
						+ nameConverter.getAsn1IdentifierName(dataType.dataPackage.name + "." + dataType.name,
								literal.name, literal.id)
						+ " (" + integerValue + ")");
			} else {
				sb.append(getIndent() + nameConverter.getAsn1IdentifierName(
						dataType.dataPackage.name + "." + dataType.name, literal.name, literal.id));
			}
			sb.append((i == (dataType.literals.size() - 1)) ? "\n" : ",\n");
		}
		sb.append("}\n\n");
		return sb.toString();
	}
}
