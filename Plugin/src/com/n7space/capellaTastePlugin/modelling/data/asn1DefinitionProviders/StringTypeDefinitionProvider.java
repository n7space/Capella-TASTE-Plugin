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
import com.n7space.capellatasteplugin.modelling.data.StringDataType;

/**
 * Class providing ASN.1 definition for a string data type.
 *
 * @see StringDataType
 */
public class StringTypeDefinitionProvider extends BaseDefinitionProvider implements Asn1ElementDefinitionProvider {

	/**
	 * Enumeration listing possible option handles.
	 */
	public static enum StringTypeDefinitionProviderOption {
		/**
		 * Name of the sequence member for class realizations.
		 */
		UseOctetStrings("UseOctetStrings");

		/**
		 * Prefix for the string representation.
		 */
		public final static String NAME_COVERTER_PREFIX = "com.n7space.capellatasteplugin.modelling.data.asn1definitionproviders.StringTypeDefinitionProvider.";

		private final String value;

		private StringTypeDefinitionProviderOption(final String baseName) {
			value = baseName;
		}

		/**
		 * Returns the string representation of the handle.
		 * 
		 * @return The string representation of the handle
		 */
		@Override
		public String toString() {
			return NAME_COVERTER_PREFIX + value;
		}
	}

	protected final Option[] OPTIONS = { new Option(StringTypeDefinitionProviderOption.UseOctetStrings,
			"Use OCTET STRING instead of IA5String", Boolean.TRUE, true) };

	/**
	 * The constructor.
	 *
	 * @param converter
	 *            Name converter
	 */
	public StringTypeDefinitionProvider(final NameConverter converter) {
		super(converter);
		addOptions(OPTIONS);
	}

	protected String getBaseTypeName(final StringDataType dataType) {
		if (dataType.parent == null) {
			return getBooleanOptionValue(StringTypeDefinitionProviderOption.UseOctetStrings, true) ? "OCTET STRING"
					: "IA5String";
		}
		return nameConverter.getAsn1TypeName(dataType.parent.dataPackage.name, dataType.parent.name,
				dataType.parent.id);
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
		final StringDataType dataType = (StringDataType) element;
		final String baseTypeName = getBaseTypeName(dataType);
		if (dataType.minLength != null || dataType.maxLength != null) {
			final String minLengthSpec = dataType.minLength != null ? dataType.minLength.getValue(nameConverter) : "0";
			final String maxLengthSpec = dataType.maxLength != null ? dataType.maxLength.getValue(nameConverter)
					: "MAX";
			return getComment(dataType)
					+ nameConverter.getAsn1TypeName(dataType.dataPackage.name, dataType.name, dataType.id) + " ::= "
					+ baseTypeName + "(SIZE(" + minLengthSpec + ".." + maxLengthSpec + "))\n\n";
		}
		return getComment(dataType)
				+ nameConverter.getAsn1TypeName(dataType.dataPackage.name, dataType.name, dataType.id) + " ::= "
				+ baseTypeName + "\n\n";
	}
}
