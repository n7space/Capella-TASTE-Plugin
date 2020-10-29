// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.core.data.fa.FunctionalExchange;
import org.polarsys.capella.core.data.information.DataPkg;
import org.polarsys.capella.core.data.information.ExchangeItem;
import org.polarsys.capella.core.data.information.ExchangeMechanism;

import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.BaseDataModelElementInterpetationProvider;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.DataModelElementInterpreter;
import com.n7space.capellatasteplugin.modelling.architecture.Function;
import com.n7space.capellatasteplugin.modelling.architecture.FunctionExchange;
import com.n7space.capellatasteplugin.modelling.architecture.FunctionExchange.Encoding;
import com.n7space.capellatasteplugin.modelling.architecture.FunctionExchange.ExchangeKind;
import com.n7space.capellatasteplugin.modelling.architecture.FunctionExchange.ExchangeParameter;
import com.n7space.capellatasteplugin.modelling.architecture.FunctionExchange.ExchangeType;
import com.n7space.capellatasteplugin.modelling.architecture.SystemModel;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataModelElement;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.modelling.data.DataTypeReference;
import com.n7space.capellatasteplugin.modelling.data.MemberDefinition;
import com.n7space.capellatasteplugin.modelling.data.StructuredDataType;
import com.n7space.capellatasteplugin.modelling.data.StructuredDataType.Kind;
import com.n7space.capellatasteplugin.utils.Issue;
import com.n7space.capellatasteplugin.utils.OptionsHelper;

/**
 * Class providing facilities to interpret Capella Functional Exchanges.
 *
 */
public class FunctionalExchangeInterpretationProvider extends BaseDataModelElementInterpetationProvider {

	protected final DataModelElementInterpreter elementInterpreter;

	/**
	 * The constructor.
	 *
	 * @param dataModelElementInterpreter
	 *            Data model element interpreter
	 */
	public FunctionalExchangeInterpretationProvider(final DataModelElementInterpreter dataModelElementInterpreter) {
		elementInterpreter = dataModelElementInterpreter;

	}

	/**
	 * Finds matching Functional Exchanges with a single parent function and
	 * combines them into exchanges with both parents.
	 *
	 * @param exchanges
	 *            List of single parent FunctionalExchangesWithParents
	 * @return List of complete FunctionalExchangesWithParents
	 */
	public List<FunctionalExchangeWithParents> filterOutPartialOrphans(
			final List<FunctionalExchangeWithParents> exchanges) {
		final List<FunctionalExchangeWithParents> result = new LinkedList<FunctionalExchangeWithParents>();
		for (final FunctionalExchangeWithParents exchange1 : exchanges) {
			for (final FunctionalExchangeWithParents exchange2 : exchanges) {
				if (exchange1.exchange.getId().equals(exchange2.exchange.getId())) {
					if ((exchange1.sourceFunction != null) && (exchange1.targetFunction == null)
							&& (exchange2.sourceFunction == null) && (exchange2.targetFunction != null)) {
						result.add(new FunctionalExchangeWithParents(exchange1.sourceFunction, exchange2.targetFunction,
								exchange1.exchange));
					}
				}
			}
		}
		return result;
	}

	/**
	 * Converts Capella Functional Exchange into an abstract Functional Exchange and
	 * attaches them to the system model.
	 *
	 * @param model
	 *            System model
	 * @param dataModel
	 *            Data model for Exchange Items
	 * @param dataViewPackage
	 *            DataView package for Exchange Items
	 * @param sourceFunction
	 *            Exchange source
	 * @param targetFunction
	 *            Exchange target
	 * @param root
	 *            Functional Exchange to be interpreted
	 * @param [output]
	 *            issues List of detected issues
	 * @return An abstract Functional Exchange
	 */
	public FunctionExchange convertCapellaFunctionalExchangeToAbstractFunctionalExchangeAndAttachItToSystemModel(
			final SystemModel model, final DataModel dataModel, final DataPackage dataViewPackage,
			final Function sourceFunction, final Function targetFunction, final FunctionalExchange root,
			final List<Issue> issues) {
		final FunctionExchange.ExchangeType exchangeType = getExchangeTypeForExchange(root, issues);
		final FunctionExchange result = new FunctionExchange(sourceFunction, targetFunction, root.getName(),
				root.getId(), exchangeType);
		setParametersForExchange(dataModel, dataViewPackage, result, root, issues);

		setEncoding(result, root, issues);
		setExchangeKind(result, root, issues);
		setMinExecutionTime(result, root, issues);
		setMaxExecutionTime(result, root, issues);
		setDeadline(result, root, issues);
		setQueueSize(result, root, issues);
		issueWarningsAndErrors(result, sourceFunction, targetFunction, issues);
		model.functionExchanges.add(result);
		return result;
	}

	protected ExchangeMechanism getCommonExchangeMechanism(final FunctionalExchange functionalExchange) {
		if (functionalExchange.getExchangedItems().size() == 0)
			return null;
		final ExchangeMechanism mechanism = functionalExchange.getExchangedItems().get(0).getExchangeMechanism();
		for (final ExchangeItem item : functionalExchange.getExchangedItems()) {
			if (item.getExchangeMechanism() != mechanism) {
				return null;
			}
		}
		return mechanism;
	}

	protected DataPackage getDataPackage(final DataModel dataModel, final ExchangeItem item, final List<Issue> issues) {
		final DataPkg pkg = getDataPkg(item);
		if (pkg == null) {
			issues.add(new Issue(Issue.Kind.Error, "Package for " + item.getName() + " could not be found", item));
			return null;
		}
		final DataPackage dataPackage = dataModel.findDataPackageById(pkg.getId());
		if (dataPackage != null)
			return dataPackage;
		final DataModelElement element = elementInterpreter.interpretDataModelElement(dataModel, null,
				new ArrayDeque<DataModelElement>(), pkg, issues);
		if (element == null || !(element instanceof DataPackage)) {
			issues.add(
					new Issue(Issue.Kind.Error, "Package for " + item.getName() + " could not be interpreted", item));
			return null;
		}
		return (DataPackage) element;

	}

	protected DataPkg getDataPkg(final ExchangeItem item) {
		EObject element = item;
		while (element != null) {
			if (element instanceof DataPkg)
				return (DataPkg) element;
			element = element.eContainer();
		}
		return null;
	}

	protected FunctionExchange.ExchangeType getExchangeTypeForExchange(final FunctionalExchange functionalExchange,
			final List<Issue> issues) {
		FunctionExchange.ExchangeType type = ExchangeType.INVALID;
		if (functionalExchange.getExchangedItems().size() > 0) {
			type = getExchangeTypeFromMechanism(functionalExchange.getExchangedItems().get(0).getExchangeMechanism());
		}
		for (final ExchangeItem item : functionalExchange.getExchangedItems()) {
			final FunctionExchange.ExchangeType otherType = getExchangeTypeFromMechanism(item.getExchangeMechanism());
			if (otherType != type) {
				issues.add(
						new Issue(Issue.Kind.Warning,
								"Functional exchange " + functionalExchange.getName()
										+ " contains exchange items with different exchange mechanisms",
								functionalExchange));
				return ExchangeType.INVALID;
			}
		}
		return type;
	}

	protected FunctionExchange.ExchangeType getExchangeTypeFromMechanism(final ExchangeMechanism mechanism) {
		switch (mechanism) {
		case EVENT:
			return ExchangeType.EVENT;
		case FLOW:
			return ExchangeType.FLOW;
		case OPERATION:
			return ExchangeType.OPERATION;
		case SHARED_DATA:
			return ExchangeType.DATA;
		case UNSET:
		default:
			return ExchangeType.INVALID;
		}
	}

	protected boolean isExchangeTypeAndKindValid(final FunctionalExchange functionalExchange,
			final FunctionExchange.ExchangeType exchangeType, final List<Issue> issues) {
		if (functionalExchange.getExchangedItems().size() == 0) {
			issues.add(new Issue(Issue.Kind.Error,
					"Functional exchange " + functionalExchange.getName() + " does not carry any items",
					functionalExchange));
			return false;
		}
		final ExchangeMechanism mechanism = getCommonExchangeMechanism(functionalExchange);
		if (mechanism == null) {
			issues.add(new Issue(Issue.Kind.Error, "Functional exchange " + functionalExchange.getName()
					+ " contains items with different exchange mechanisms", functionalExchange));
			return false;
		}
		if (exchangeType == ExchangeType.OPERATION) {
			if (mechanism != ExchangeMechanism.OPERATION) {
				issues.add(new Issue(Issue.Kind.Error,
						"Functional exchange " + functionalExchange.getName()
								+ " is an operation but the carried items are not of an operation kind",
						functionalExchange));
				return false;
			}
			if (functionalExchange.getExchangedItems().size() != 1) {
				issues.add(new Issue(Issue.Kind.Error, "Functional exchange " + functionalExchange.getName()
						+ " is an operation but carries multiple exchange items", functionalExchange));
				return false;
			}
		}

		return true;
	}

	protected void issueWarningsAndErrors(final FunctionExchange exchange, final Function sourceFunction,
			final Function targetFunction, final List<Issue> issues) {
		if (exchange.exchangeKind != FunctionExchange.ExchangeKind.SPORADIC) {
			if (sourceFunction.language.equalsIgnoreCase("GUI") || targetFunction.language.equalsIgnoreCase("GUI")) {
				issues.add(new Issue(Issue.Kind.Warning,
						"Exchange " + exchange.name + " is of kind " + exchange.exchangeKind.name()
								+ " but is connected to a GUI function for which only sporadic kind is allowed",
						exchange));
			}
		}
		if (exchange.type == ExchangeType.INVALID) {
			issues.add(new Issue(Issue.Kind.Error, "Exchange " + exchange.name + " is of an invalid type", exchange));
		}
		if (exchange.type == ExchangeType.DATA) {
			issues.add(new Issue(Issue.Kind.Error,
					"Exchange " + exchange.name + " is of type shared data, which is not supported", exchange));
		}
	}

	protected void setEncoding(final FunctionExchange exchange, final FunctionalExchange source,
			final List<Issue> issues) {
		final String encodingString = PropertyUtils.getProperty(source, CustomCapellaProperties.ENCODING);
		if (encodingString == null || encodingString.length() == 0) {
			return;
		}
		try {
			exchange.encoding = Encoding.valueOf(encodingString.toUpperCase());
		} catch (final Throwable t) {
			issues.add(new Issue(Issue.Kind.Error,
					"Cannot parse encoding " + encodingString + " for exchange " + exchange.name, exchange));
		}
	}

	protected void setDeadline(final FunctionExchange exchange, final FunctionalExchange source,
			final List<Issue> issues) {
		final String deadlineString = PropertyUtils.getProperty(source, CustomCapellaProperties.DEADLINE);
		if (deadlineString == null || deadlineString.length() == 0) {
			return;
		}
		try {
			exchange.deadlineMs = Integer.parseInt(deadlineString);
		} catch (final Throwable t) {
			issues.add(new Issue(Issue.Kind.Error,
					"Cannot parse deadline " + deadlineString + " for exchange " + exchange.name, exchange));
		}
	}

	protected void setExchangeKind(final FunctionExchange exchange, final FunctionalExchange source,
			final List<Issue> issues) {
		final String kindString = PropertyUtils.getProperty(source, CustomCapellaProperties.EXCHANGE_KIND);
		if (kindString == null || kindString.length() == 0) {
			if (exchange.type == ExchangeType.OPERATION)
				exchange.exchangeKind = ExchangeKind.UNPROTECTED;
			return;
		}
		try {
			exchange.exchangeKind = Enum.valueOf(FunctionExchange.ExchangeKind.class, kindString.toUpperCase());
		} catch (final Throwable t) {
			issues.add(new Issue(Issue.Kind.Error,
					"Cannot parse exchange kind " + kindString + " for exchange " + exchange.name, exchange));
		}
		if (exchange.type == ExchangeType.OPERATION && exchange.exchangeKind == ExchangeKind.SPORADIC)
			issues.add(new Issue(Issue.Kind.Error,
					"Operation for exchange " + exchange.name + " should be protected or unprotected, not sporadic",
					exchange));
	}

	protected void setMaxExecutionTime(final FunctionExchange exchange, final FunctionalExchange source,
			final List<Issue> issues) {
		final String etString = PropertyUtils.getProperty(source, CustomCapellaProperties.MAXIMUM_EXECUTION_TIME);
		if (etString == null || etString.length() == 0) {
			return;
		}
		try {
			exchange.minimumExecutionTimeMs = Integer.parseInt(etString);
		} catch (final Throwable t) {
			issues.add(new Issue(Issue.Kind.Error,
					"Cannot parse maximum execution time " + etString + " for exchange " + exchange.name, exchange));
		}
	}

	protected void setMinExecutionTime(final FunctionExchange exchange, final FunctionalExchange source,
			final List<Issue> issues) {
		final String etString = PropertyUtils.getProperty(source, CustomCapellaProperties.MINIMUM_EXECUTION_TIME);
		if (etString == null || etString.length() == 0) {
			return;
		}
		try {
			exchange.minimumExecutionTimeMs = Integer.parseInt(etString);
		} catch (final Throwable t) {
			issues.add(new Issue(Issue.Kind.Error,
					"Cannot parse minimum execution time " + etString + " for exchange " + exchange.name, exchange));
		}
	}

	protected void setParametersForExchange(final DataModel dataModel, final DataPackage dataViewPackage,
			final FunctionExchange exchange, final FunctionalExchange functionalExchange, final List<Issue> issues) {

		if (!isExchangeTypeAndKindValid(functionalExchange, exchange.type, issues))
			return;

		if (functionalExchange.getExchangedItems().size() == 1) {
			if (exchange.type == ExchangeType.OPERATION) {
				setParametersForOperationExchange(dataModel, dataViewPackage, exchange,
						functionalExchange.getExchangedItems().get(0), issues);
				return;
			}
			final ExchangeItem item = functionalExchange.getExchangedItems().get(0);
			final DataTypeReference reference = getDataTypeReference(dataModel, item, issues);
			dataViewPackage.addForcedImport(reference);
			exchange.parameters.add(new ExchangeParameter(reference.name, reference, false));
			return;
		}

		final StructuredDataType type = new StructuredDataType(dataViewPackage, null,
				functionalExchange.getName()
						+ getStringOptionValue(DataModelElementInterpretationProviderOption.NameSeparator) + "Data",
				functionalExchange.getId() + "Data", Kind.Union);
		for (final ExchangeItem item : functionalExchange.getExchangedItems()) {
			final DataTypeReference reference = getDataTypeReference(dataModel, item, issues);
			final MemberDefinition member = new MemberDefinition(item.getName()
					+ getStringOptionValue(DataModelElementInterpretationProviderOption.NameSeparator) + "Item",
					item.getId(), reference);
			type.members.add(member);
		}
		dataViewPackage.addTypeDefinition(type);
		exchange.parameters.add(new ExchangeParameter(type.name, new DataTypeReference(type), false));
	}

	protected void setParametersForOperationExchange(final DataModel dataModel, final DataPackage dataViewPackage,
			final FunctionExchange exchange, final ExchangeItem item, final List<Issue> issues) {
		final DataPackage pkg = getDataPackage(dataModel, item, issues);
		if (pkg == null)
			return;
		final DataModelElement element = elementInterpreter.interpretDataModelElement(dataModel, pkg,
				new ArrayDeque<DataModelElement>(), item, issues);
		if (!(element instanceof StructuredDataType)) {
			issues.add(new Issue(Issue.Kind.Error, "Data type for " + exchange.name + " could not be interpreted",
					exchange));
			return;
		}
		// Due to previous checks, it is assumed that this is a structure containing
		// operation.
		final StructuredDataType parentType = (StructuredDataType) element;
		final String separator = (String) OptionsHelper.getOptionValue(elementInterpreter.getOptions(),
				DataModelElementInterpretationProviderOption.NameSeparator);
		for (final MemberDefinition member : parentType.members) {
			dataViewPackage.addForcedImport(member.dataType);
			if (member.name.toLowerCase().startsWith("in" + separator)) {
				exchange.parameters.add(new ExchangeParameter(member.name, member.dataType, false));
			} else if (member.name.toLowerCase().startsWith("out" + separator)) {
				exchange.parameters.add(new ExchangeParameter(member.name, member.dataType, true));
			} else {
				issues.add(new Issue(Issue.Kind.Error, "Operation for exchange " + exchange.name
						+ " contains a parameter " + member.name + ", direction of which is not supported", member));
				return;
			}
		}

	}

	protected void setQueueSize(final FunctionExchange exchange, final FunctionalExchange source,
			final List<Issue> issues) {
		final String sizeString = PropertyUtils.getProperty(source, CustomCapellaProperties.QUEUE_SIZE);
		if (sizeString == null || sizeString.length() == 0) {
			return;
		}
		try {
			exchange.queueSize = Integer.parseInt(sizeString);
		} catch (final Throwable t) {
			issues.add(new Issue(Issue.Kind.Error,
					"Cannot parse queue size " + sizeString + " for exchange " + exchange.name, exchange));
		}
	}

}
