// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.data.asn1definitionproviders;

import com.n7space.capellatasteplugin.modelling.NameConverter;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataModelElement;
import com.n7space.capellatasteplugin.modelling.data.DataType;
import com.n7space.capellatasteplugin.modelling.data.IntegerDataType;
import com.n7space.capellatasteplugin.utils.BasicConfigurableItem;
import com.n7space.capellatasteplugin.utils.StringUtils;

/**
 * Base class for ASN.1 definition providers.
 */
public class BaseDefinitionProvider extends BasicConfigurableItem {

	/**
	 * Enumeration listing possible option handles.
	 */
	public static enum BaseDefinitionProviderOption {
		/**
		 * Use tab character instead of spaces for indentation.
		 */
		UseTabsForIndent("UseTabsForIndent"),
		/**
		 * Include comments in the generated ASN.1 definitions.
		 */
		IncludeComments("IncludeComments");

		/**
		 * Prefix for string representation.
		 */
		public final static String NAME_COVERTER_PREFIX = "com.n7space.capellatasteplugin.modelling.data.asn1definitionproviders.BaseDefinitionProvider.";

		private final String value;

		private BaseDefinitionProviderOption(final String baseName) {
			value = baseName;
		}

		/**
		 * Returns the string representation of the option handle.
		 *
		 * @return the string representation of the option handle.
		 */
		@Override
		public String toString() {
			return NAME_COVERTER_PREFIX + value;
		}
	}

	protected final Option[] OPTIONS = {
			new Option(BaseDefinitionProviderOption.UseTabsForIndent, "Use tabs for indent in ASN.1 definitions",
					Boolean.TRUE),
			new Option(BaseDefinitionProviderOption.IncludeComments, "Include comments in ASN.1 definitions",
					Boolean.TRUE) };

	protected final NameConverter nameConverter;

	/**
	 * The constructor.
	 *
	 * @param converter
	 *            Name converter
	 */
	public BaseDefinitionProvider(final NameConverter converter) {
		nameConverter = converter;
		addOptions(OPTIONS);
	}

	protected String getComment(final DataType dataType) {
		if (!getBooleanOptionValue(BaseDefinitionProviderOption.IncludeComments, true)) {
			return "";
		}
		if (dataType.description == null) {
			return "";
		}
		final StringBuilder sb = new StringBuilder();
		for (final String line : dataType.description.trim().split("\n")) {
			final String trimmedLine = line.trim();
			if (trimmedLine.length() == 0) {
				continue;
			}
			sb.append("-- " + trimmedLine + "\n");
		}
		return sb.toString();
	}

	/**
	 * Returns a single level of indentation.
	 *
	 * @return Single level of indentation
	 */
	public String getIndent() {
		if (getBooleanOptionValue(BaseDefinitionProviderOption.UseTabsForIndent, true))
			return "\t";
		return "  ";
	}

	protected String getIntegerValueOfAModelElement(final DataModel model, final DataModelElement element) {
		if (element == null) {
			return null;
		}
		if (!(element instanceof DataType.DataTypeValue)) {
			return null;
		}
		final DataType.DataTypeValue value = (DataType.DataTypeValue) element;
		final DataType type = model.findDataTypeById(value.type.id);
		if (!(type instanceof IntegerDataType)) {
			return null;
		}
		try {
			return StringUtils.cleanUpIntegerValue(value.value);
		} catch (final Exception e) {
			return null;
		}
	}

}
