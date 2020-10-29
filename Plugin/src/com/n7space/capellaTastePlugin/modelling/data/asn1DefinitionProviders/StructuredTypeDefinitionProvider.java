// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.data.asn1definitionproviders;

import java.util.LinkedList;
import java.util.List;

import com.n7space.capellatasteplugin.modelling.Asn1ElementDefinitionGenerator.Asn1ElementDefinitionProvider;
import com.n7space.capellatasteplugin.modelling.NameConverter;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataModelElement;
import com.n7space.capellatasteplugin.modelling.data.DataType;
import com.n7space.capellatasteplugin.modelling.data.MemberDefinition;
import com.n7space.capellatasteplugin.modelling.data.StructuredDataType;

/**
 * Class providing ASN.1 definition for a structured data type.
 * 
 * @see StructuredDataType
 */
public class StructuredTypeDefinitionProvider extends BaseDefinitionProvider implements Asn1ElementDefinitionProvider {

	/**
	 * Enumeration listing possible option handles.
	 */
	public static enum StructuredTypeDefinitionProviderOption {
		/**
		 * Name of the sequence member for class realizations.
		 */
		MemberNameForClassRealizations("MemberNameForClassRealizations"),
		/**
		 * Name of the sequence member for union values.
		 */
		MemberNameForUnionValues("MemberNameForUnionValues");

		/**
		 * Prefix for the string representation.
		 */
		public final static String NAME_COVERTER_PREFIX = "com.n7space.capellatasteplugin.modelling.data.asn1definitionproviders.StructuredTypeDefinitionProvider.";

		private final String value;

		private StructuredTypeDefinitionProviderOption(final String baseName) {
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

	protected final Option[] OPTIONS = {
			new Option(StructuredTypeDefinitionProviderOption.MemberNameForClassRealizations,
					"Member name for class realizations", "realization", false),
			new Option(StructuredTypeDefinitionProviderOption.MemberNameForUnionValues, "Member name for union values",
					"value", false) };

	/**
	 * The constructor.
	 * 
	 * @param converter
	 *            Name converter
	 */
	public StructuredTypeDefinitionProvider(final NameConverter converter) {
		super(converter);
		addOptions(OPTIONS);
	}

	protected List<MemberDefinition> getMemberDefinitions(final DataModel model, final StructuredDataType dataType) {
		final List<MemberDefinition> result = new LinkedList<MemberDefinition>();
		if (dataType.parent != null) {
			final DataType superClassType = model.findDataTypeById(dataType.parent.id);
			if (superClassType != null && superClassType instanceof StructuredDataType) {
				// Only direct descendants are listed as realizations, so only direct parents
				// are probed for members.
				result.addAll(((StructuredDataType) superClassType).members);
			}
		}
		result.addAll(dataType.members);
		return result;
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
		final StructuredDataType dataType = (StructuredDataType) element;
		final StringBuilder sb = new StringBuilder();
		sb.append(getComment(dataType));

		sb.append(nameConverter.getAsn1TypeName(dataType.dataPackage.name, dataType.name, dataType.id) + " ::= "
				+ "SEQUENCE" + " {\n");

		switch (dataType.kind) {
		case Structure:
			provideStructureDefinition(model, dataType, sb);
			break;
		case Union:
			provideUnionDefinition(model, dataType, sb);
		default:
			break;

		}

		sb.append("}\n\n");
		return sb.toString();
	}

	protected void provideMemberDefinition(final StringBuilder sb, final String prefix, final DataModel model,
			final StructuredDataType dataType, final MemberDefinition member) {
		sb.append(prefix + nameConverter.getAsn1IdentifierName(dataType.dataPackage.name + "." + dataType.name,
				member.name, member.id) + " ");
		if (!member.maximumCardinality.isOne() || !member.minimumCardinality.isOne()) {
			sb.append("SEQUENCE(SIZE(" + member.minimumCardinality.getValue(nameConverter) + ".."
					+ member.maximumCardinality.getValue(nameConverter) + ")) OF ");
		}

		sb.append(nameConverter.getAsn1TypeName(member.dataType.dataPackage.name, member.dataType.name,
				member.dataType.id));
	}

	protected void provideMemberListing(final DataModel model, final StructuredDataType dataType,
			final List<MemberDefinition> members, final String prefix, final StringBuilder sb) {
		final int memberCount = members.size();
		for (int i = 0; i < memberCount; i++) {
			final MemberDefinition member = members.get(i);
			provideMemberDefinition(sb, prefix, model, dataType, member);
			sb.append((i == (memberCount - 1)) ? "\n" : ",\n");
		}
	}

	protected void provideStructureDefinition(final DataModel model, final StructuredDataType dataType,
			final StringBuilder sb) {
		// Descendants must have all the fields from the super class. Therefore, they
		// shouldn't be repeated in the base
		// class.
		final List<StructuredDataType> descendants = dataType.getDirectDescendants(model);
		final List<MemberDefinition> members = getMemberDefinitions(model, dataType);

		if (descendants.size() == 0) {
			provideMemberListing(model, dataType, members, getIndent(), sb);
		} else {
			sb.append(getIndent() + nameConverter.getAsn1IdentifierName(dataType.dataPackage.id + "." + dataType.name,
					getStringOptionValue(StructuredTypeDefinitionProviderOption.MemberNameForClassRealizations),
					dataType.id + "memberRealizationChoice") + " CHOICE {\n");
			final int realizationCount = descendants.size();
			for (int i = 0; i < realizationCount; i++) {
				final StructuredDataType descendant = descendants.get(i);
				final String typeName = nameConverter.getAsn1TypeName(descendant.dataPackage.name, descendant.name,
						descendant.id);
				sb.append(getIndent() + getIndent()
						+ nameConverter.getAsn1IdentifierName(dataType.dataPackage.id + "." + dataType.name,
								getStringOptionValue(
										StructuredTypeDefinitionProviderOption.MemberNameForClassRealizations)
										+ typeName,
								dataType.id + "descendant" + i)
						+ " " + typeName);
				sb.append((i == (realizationCount + members.size() - 1)) ? "\n" : ",\n");
			}

			if (members.size() > 0) {
				sb.append(getIndent() + getIndent() + nameConverter.getAsn1IdentifierName(
						dataType.dataPackage.id + "." + dataType.name,
						getStringOptionValue(StructuredTypeDefinitionProviderOption.MemberNameForClassRealizations)
								+ nameConverter.getAsn1TypeName(dataType.dataPackage.name, dataType.name, dataType.id),
						dataType.id + "memberRealization") + " SEQUENCE {\n");
				provideMemberListing(model, dataType, members, getIndent() + getIndent() + getIndent(), sb);
				sb.append(getIndent() + getIndent() + "}\n");
			}

			sb.append(getIndent() + "}\n");
		}
	}

	protected void provideUnionDefinition(final DataModel model, final StructuredDataType dataType,
			final StringBuilder sb) {
		final List<MemberDefinition> members = getMemberDefinitions(model, dataType);
		sb.append(getIndent() + nameConverter.getAsn1IdentifierName(dataType.dataPackage.id + "." + dataType.name,
				getStringOptionValue(StructuredTypeDefinitionProviderOption.MemberNameForUnionValues),
				dataType.id + "unionValuesChoice") + " CHOICE {\n");
		provideMemberListing(model, dataType, members, getIndent() + getIndent(), sb);
		sb.append(getIndent() + "}\n");
	}
}
