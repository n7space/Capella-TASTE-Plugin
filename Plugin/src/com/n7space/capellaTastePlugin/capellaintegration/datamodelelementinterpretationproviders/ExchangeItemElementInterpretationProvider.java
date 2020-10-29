// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.polarsys.capella.core.data.capellacore.GeneralizableElement;
import org.polarsys.capella.core.data.capellacore.NamedElement;
import org.polarsys.capella.core.data.information.ExchangeItemElement;
import org.polarsys.capella.core.data.information.ExchangeMechanism;
import org.polarsys.capella.core.data.information.ParameterDirection;

import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataModelElement;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.modelling.data.DataTypeReference;
import com.n7space.capellatasteplugin.modelling.data.MemberDefinition;
import com.n7space.capellatasteplugin.modelling.data.StructuredDataType;
import com.n7space.capellatasteplugin.modelling.data.StructuredDataType.Kind;
import com.n7space.capellatasteplugin.utils.Issue;

/**
 * Interpretation provider for Capella exchange item element.
 */
public class ExchangeItemElementInterpretationProvider extends BaseDataModelElementInterpetationProvider implements
		com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.DataModelElementInterpreter.DataModelElementInterpretationProvider {

	/**
	 * Enumeration defining possible option handles for
	 * ExchangeItemElementInterpretationProvider.
	 *
	 * @see Option
	 */
	public static enum ExchangeItemElementInterpretationProviderOption {
		/**
		 * Postfix for members containing operation parameters.
		 */
		OperationParametersPostfix("OperationParametersPostfix");

		/**
		 * Prefix used in the option handles.
		 */
		public final static String NAME_COVERTER_PREFIX = "com.n7space.capellatasteplugin.capellaintegration.modelelementinterpretationproviders.ExchangeItemElementInterpretationProvider.";

		private final String value;

		private ExchangeItemElementInterpretationProviderOption(final String baseName) {
			value = baseName;
		}

		/**
		 * Returns the string value of the given handle.
		 *
		 * @return String value of the handle.
		 */
		@Override
		public String toString() {
			return NAME_COVERTER_PREFIX + value;
		}
	}

	/**
	 * List of supported options.
	 */
	public final Option[] OPTIONS = {
			new Option(ExchangeItemElementInterpretationProviderOption.OperationParametersPostfix,
					"Operation Exchange Item parameter name postfix", "parameters") };

	/**
	 * The constructor.
	 */
	public ExchangeItemElementInterpretationProvider() {
		addOptions(OPTIONS);
	}

	protected List<ExchangeItemElement> getElementsOfDirection(final Iterable<ExchangeItemElement> elements,
			final ParameterDirection direction) {
		final List<ExchangeItemElement> result = new LinkedList<ExchangeItemElement>();
		for (final ExchangeItemElement element : elements) {
			if (element.getDirection().equals(direction)) {
				result.add(element);
			}
		}
		return result;
	}

	protected DataTypeReference getParentItem(final DataModel dataModel,
			final org.polarsys.capella.core.data.information.ExchangeItem exchangeItem, final List<Issue> issues) {
		final EList<GeneralizableElement> parents = exchangeItem.getSuper();
		if (parents != null) {
			if (parents.size() > 1) {
				issues.add(
						new Issue(Issue.Kind.Warning,
								"ExchangeItem " + exchangeItem.getName()
										+ " has multiple super classes - multiple inheritance is not supported",
								exchangeItem));
			}
			if (parents.size() == 1) {
				return getDataTypeReference(dataModel, parents.get(0), issues);
			}
		}
		return null;
	}

	protected void interpretMembers(final DataModel dataModel, final DataPackage currentDataPackage,
			final StructuredDataType dataType, final Iterable<ExchangeItemElement> members, final List<Issue> issues) {
		for (final ExchangeItemElement eie : members) {
			intepretTypedMember(dataModel, currentDataPackage, dataType, eie, issues);
		}
	}

	/**
	 * Provides interpretation for the given Capella data model element.
	 *
	 * @param dataModel
	 *            The internal data model
	 * @param currentDataPackage
	 *            The current internal data package
	 * @param context
	 *            The current interpretation context
	 * @param interpretedElement
	 *            The element to be interpreted
	 * @param issues
	 *            [output] List of detected issues
	 * @return The element converted to the internal data model or null if the
	 *         interpretation failed
	 */
	@Override
	public DataModelElement interpretModelElement(final DataModel dataModel, final DataPackage currentDataPackage,
			final Deque<DataModelElement> context, final NamedElement interpretedElement, final List<Issue> issues) {
		final org.polarsys.capella.core.data.information.ExchangeItem exchangeItem = (org.polarsys.capella.core.data.information.ExchangeItem) interpretedElement;

		final DataTypeReference parentItem = getParentItem(dataModel, exchangeItem, issues);
		final StructuredDataType dataType = parentItem != null
				? new StructuredDataType(currentDataPackage, parentItem, exchangeItem.getName(), exchangeItem.getId(),
						Kind.Structure)
				: new StructuredDataType(currentDataPackage, exchangeItem.getName(), exchangeItem.getId(),
						Kind.Structure);

		if (exchangeItem.getExchangeMechanism() == ExchangeMechanism.OPERATION) {
			final List<MemberDefinition> arguments = packMembersIntoStructures(dataModel, currentDataPackage, dataType,
					exchangeItem.getOwnedElements(), issues);
			dataType.members.addAll(arguments);
		} else {
			interpretMembers(dataModel, currentDataPackage, dataType, exchangeItem.getOwnedElements(), issues);
		}
		dataType.description = extractComment(exchangeItem);
		currentDataPackage.addTypeDefinition(dataType);
		return dataType;
	}

	protected List<MemberDefinition> packMembersIntoStructures(final DataModel dataModel,
			final DataPackage currentDataPackage, final StructuredDataType dataType,
			final Iterable<ExchangeItemElement> members, final List<Issue> issues) {
		final List<MemberDefinition> result = new LinkedList<MemberDefinition>();
		for (final ParameterDirection direction : ParameterDirection.VALUES) {
			final List<ExchangeItemElement> elements = getElementsOfDirection(members, direction);
			if (elements.size() > 0) {
				final StructuredDataType memberDataType = new StructuredDataType(currentDataPackage,
						dataType.name + getStringOptionValue(DataModelElementInterpretationProviderOption.NameSeparator)
								+ direction.getName(),
						dataType.id + getStringOptionValue(DataModelElementInterpretationProviderOption.NameSeparator)
								+ direction.getName(),
						Kind.Structure);
				interpretMembers(dataModel, currentDataPackage, memberDataType, elements, issues);
				currentDataPackage.addTypeDefinition(memberDataType);
				final MemberDefinition memberDefinition = new MemberDefinition(
						direction.getName().toLowerCase()
								+ getStringOptionValue(DataModelElementInterpretationProviderOption.NameSeparator)
								+ getStringOptionValue(
										ExchangeItemElementInterpretationProviderOption.OperationParametersPostfix),
						dataType.id + getStringOptionValue(DataModelElementInterpretationProviderOption.NameSeparator)
								+ direction.getName()
								+ getStringOptionValue(DataModelElementInterpretationProviderOption.NameSeparator)
								+ getStringOptionValue(
										ExchangeItemElementInterpretationProviderOption.OperationParametersPostfix),
						new DataTypeReference(memberDataType.dataPackage, memberDataType.name, memberDataType.id));
				result.add(memberDefinition);
			}
		}
		return result;
	}

}
