// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders;

import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.polarsys.capella.core.data.information.DataPkg;
import org.polarsys.capella.core.data.information.ExchangeItem;
import org.polarsys.capella.core.data.information.ExchangeMechanism;
import org.polarsys.capella.core.data.information.ParameterDirection;
import org.polarsys.capella.core.data.information.datatype.NumericTypeKind;

import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.DataModelElementInterpreter;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.DataPackageInterpretationProvider;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.ExchangeItemElementInterpretationProvider;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.DataPackageMock;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.ExchangeItemTypeMock;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.NumericTypeMock;
import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.testmocks.FunctionalExchangeMock;
import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.testmocks.StringPropertyMock;
import com.n7space.capellatasteplugin.modelling.architecture.Function;
import com.n7space.capellatasteplugin.modelling.architecture.SystemModel;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.utils.Issue;

public class FunctionalExchangeInterpretationProviderTest {

	@Test
	public void testConvertCapellaFunctionalExchangeToAbstractFunctionalExchangeAndAttachItToSystemModel() {
		final DataModelElementInterpreter dataInterpreter = new DataModelElementInterpreter();
		dataInterpreter.registerInterpretationProvider(ExchangeItem.class,
				new ExchangeItemElementInterpretationProvider());
		final FunctionalExchangeInterpretationProvider provider = new FunctionalExchangeInterpretationProvider(
				dataInterpreter);
		final List<Issue> issues = new LinkedList<Issue>();
		final DataModel dataModel = new DataModel();
		final SystemModel systemModel = new SystemModel("TestModel", "TestId", dataModel);
		final DataPackage dataViewPackage = new DataPackage("DataView", "DataViewId");
		dataModel.dataPackages.add(dataViewPackage);

		final Function sourceFunction = new Function(null, "F1", "1");
		final Function targetFunction = new Function(null, "F2", "2");

		final DataPackageMock pkgMock = new DataPackageMock("Data", "DataId");
		final NumericTypeMock typeMock = new NumericTypeMock(pkgMock, "TypeMock", "TypeMock" + "_id",
				NumericTypeKind.INTEGER);

		final ExchangeItemTypeMock exchangedEvent1 = new ExchangeItemTypeMock(pkgMock, "EventType1", "EventTypeId1",
				ExchangeMechanism.EVENT);
		final ExchangeItemTypeMock exchangedEvent2 = new ExchangeItemTypeMock(pkgMock, "EventType2", "EventTypeId2",
				ExchangeMechanism.EVENT);
		final ExchangeItemTypeMock exchangedOperation = new ExchangeItemTypeMock(pkgMock, "OperationType",
				"OperationTypeId", ExchangeMechanism.OPERATION);

		exchangedOperation.addElement(new ExchangeItemTypeMock.ExchangeItemElementMock(pkgMock, "ItemMock1",
				"ItemMock1" + "_id", typeMock, ParameterDirection.IN));
		exchangedOperation.addElement(new ExchangeItemTypeMock.ExchangeItemElementMock(pkgMock, "ItemMock2",
				"ItemMock2" + "_id", typeMock, ParameterDirection.OUT));

		final FunctionalExchangeMock mockedEventExchange = new FunctionalExchangeMock("EventExchange");
		mockedEventExchange.addExchangedItem(exchangedEvent1);
		mockedEventExchange.addExchangedItem(exchangedEvent2);
		mockedEventExchange.addAppliedPropertyValue(new StringPropertyMock("Taste::MinimumExecutionTime", "12"));
		mockedEventExchange.addAppliedPropertyValue(new StringPropertyMock("Taste::MaximumExecutionTime", "20"));
		mockedEventExchange.addAppliedPropertyValue(new StringPropertyMock("Taste::QueueSize", "2"));

		final FunctionalExchangeMock mockedOperationExchange = new FunctionalExchangeMock("OperationExchange");
		mockedOperationExchange.addExchangedItem(exchangedOperation);
		mockedOperationExchange.addAppliedPropertyValue(new StringPropertyMock("Taste::Deadline", "100"));

		provider.convertCapellaFunctionalExchangeToAbstractFunctionalExchangeAndAttachItToSystemModel(systemModel,
				dataModel, dataViewPackage, sourceFunction, targetFunction, mockedEventExchange, issues);
		provider.convertCapellaFunctionalExchangeToAbstractFunctionalExchangeAndAttachItToSystemModel(systemModel,
				dataModel, dataViewPackage, sourceFunction, targetFunction, mockedOperationExchange, issues);

		assertTrue(systemModel.functionExchanges.size() == 2);
		assertTrue(systemModel.functionExchanges.get(0).name.equals("EventExchange"));
		assertTrue(systemModel.functionExchanges.get(0).parameters.get(0).type.name.equals("EventExchange-Data"));

		assertTrue(dataModel.findDataTypeById("EventExchangeIdData") != null);

		assertTrue(systemModel.functionExchanges.get(1).name.equals("OperationExchange"));
		assertTrue(systemModel.functionExchanges.get(1).parameters.size() == 2);

		assertTrue(issues.size() == 0);
	}

	protected void assertIssuePresent(final List<Issue> issues, final String issueText) {
		for (final Issue issue : issues) {
			if (issue.description.toLowerCase().contains(issueText.toLowerCase()))
				return;
		}
		assertTrue(false);
	}

	@Test
	public void testConvertCapellaFunctionalExchangeToAbstractFunctionalExchangeAndAttachItToSystemModel_ProducesWarnings1() {
		final DataModelElementInterpreter dataInterpreter = new DataModelElementInterpreter();
		dataInterpreter.registerInterpretationProvider(ExchangeItem.class,
				new ExchangeItemElementInterpretationProvider());
		final FunctionalExchangeInterpretationProvider provider = new FunctionalExchangeInterpretationProvider(
				dataInterpreter);
		final List<Issue> issues = new LinkedList<Issue>();
		final DataModel dataModel = new DataModel();
		final SystemModel systemModel = new SystemModel("TestModel", "TestId", dataModel);
		final DataPackage dataViewPackage = new DataPackage("DataView", "DataViewId");

		final Function sourceFunction = new Function(null, "F1", "1");
		sourceFunction.language = "SDL";
		final Function targetFunction = new Function(null, "F2", "2");
		targetFunction.language = "GUI";

		final DataPackageMock pkgMock = new DataPackageMock("Data", "DataId");

		final ExchangeItemTypeMock exchangedOperation1 = new ExchangeItemTypeMock(pkgMock, "OperationType1",
				"OperationTypeId", ExchangeMechanism.OPERATION);
		final ExchangeItemTypeMock exchangedOperation2 = new ExchangeItemTypeMock(pkgMock, "OperationType2",
				"OperationTypeId", ExchangeMechanism.OPERATION);

		final FunctionalExchangeMock mockedOperationExchange = new FunctionalExchangeMock("OperationExchange");
		mockedOperationExchange.addAppliedPropertyValue(new StringPropertyMock("Taste::ExchangeKind", "sporadic"));
		mockedOperationExchange.addExchangedItem(exchangedOperation1);
		mockedOperationExchange.addExchangedItem(exchangedOperation2);

		provider.convertCapellaFunctionalExchangeToAbstractFunctionalExchangeAndAttachItToSystemModel(systemModel,
				dataModel, dataViewPackage, sourceFunction, targetFunction, mockedOperationExchange, issues);

		assertTrue(issues.size() > 0);
		assertIssuePresent(issues,
				"Functional exchange OperationExchange is an operation but carries multiple exchange items");
		assertIssuePresent(issues,
				"Operation for exchange OperationExchange should be protected or unprotected, not sporadic");
	}

	@Test
	public void testConvertCapellaFunctionalExchangeToAbstractFunctionalExchangeAndAttachItToSystemModel_ProducesWarnings2() {
		final DataModelElementInterpreter dataInterpreter = new DataModelElementInterpreter();
		dataInterpreter.registerInterpretationProvider(ExchangeItem.class,
				new ExchangeItemElementInterpretationProvider());
		dataInterpreter.registerInterpretationProvider(DataPkg.class, new DataPackageInterpretationProvider());
		final FunctionalExchangeInterpretationProvider provider = new FunctionalExchangeInterpretationProvider(
				dataInterpreter);
		final List<Issue> issues = new LinkedList<Issue>();
		final DataModel dataModel = new DataModel();
		final SystemModel systemModel = new SystemModel("TestModel", "TestId", dataModel);
		final DataPackage dataViewPackage = new DataPackage("DataView", "DataViewId");

		final Function sourceFunction = new Function(null, "F1", "1");
		sourceFunction.language = "SDL";
		final Function targetFunction = new Function(null, "F2", "2");
		targetFunction.language = "GUI";

		final DataPackageMock pkgMock = new DataPackageMock("Data", "DataId");
		final NumericTypeMock typeMock = new NumericTypeMock(pkgMock, "TypeMock", "TypeMock" + "_id",
				NumericTypeKind.INTEGER);

		final ExchangeItemTypeMock exchangedOperation = new ExchangeItemTypeMock(pkgMock, "OperationType1",
				"OperationTypeId", ExchangeMechanism.OPERATION);
		exchangedOperation.addElement(new ExchangeItemTypeMock.ExchangeItemElementMock(pkgMock, "ItemMock1",
				"ItemMock1" + "_id", typeMock, ParameterDirection.UNSET));

		final FunctionalExchangeMock mockedOperationExchange = new FunctionalExchangeMock("OperationExchange");
		mockedOperationExchange.addExchangedItem(exchangedOperation);
		mockedOperationExchange.addAppliedPropertyValue(new StringPropertyMock("Taste::MinimumExecutionTime", "12,7"));
		mockedOperationExchange
				.addAppliedPropertyValue(new StringPropertyMock("Taste::MaximumExecutionTime", "Rosetta"));
		mockedOperationExchange.addAppliedPropertyValue(new StringPropertyMock("Taste::QueueSize", "BigOne"));
		mockedOperationExchange.addAppliedPropertyValue(new StringPropertyMock("Taste::Deadline", "Soon"));

		provider.convertCapellaFunctionalExchangeToAbstractFunctionalExchangeAndAttachItToSystemModel(systemModel,
				dataModel, dataViewPackage, sourceFunction, targetFunction, mockedOperationExchange, issues);

		assertTrue(issues.size() > 0);
		assertIssuePresent(issues,
				"Exchange OperationExchange is of kind UNPROTECTED but is connected to a GUI function for which only sporadic kind is allowed");
		assertIssuePresent(issues,
				"Operation for exchange OperationExchange contains a parameter unset-parameters, direction of which is not supported");

		assertIssuePresent(issues, "Cannot parse minimum execution time 12,7 for exchange OperationExchange");
		assertIssuePresent(issues, "Cannot parse maximum execution time Rosetta for exchange OperationExchange");
		assertIssuePresent(issues, "Cannot parse deadline Soon for exchange OperationExchange");
		assertIssuePresent(issues, "Cannot parse queue size BigOne for exchange OperationExchange");

	}

	@Test
	public void testConvertCapellaFunctionalExchangeToAbstractFunctionalExchangeAndAttachItToSystemModel_ProducesWarnings3() {
		final DataModelElementInterpreter dataInterpreter = new DataModelElementInterpreter();
		dataInterpreter.registerInterpretationProvider(ExchangeItem.class,
				new ExchangeItemElementInterpretationProvider());
		final FunctionalExchangeInterpretationProvider provider = new FunctionalExchangeInterpretationProvider(
				dataInterpreter);
		final List<Issue> issues = new LinkedList<Issue>();
		final DataModel dataModel = new DataModel();
		final SystemModel systemModel = new SystemModel("TestModel", "TestId", dataModel);
		final DataPackage dataViewPackage = new DataPackage("DataView", "DataViewId");

		final Function sourceFunction = new Function(null, "F1", "1");
		final Function targetFunction = new Function(null, "F2", "2");

		final FunctionalExchangeMock mockedDataExchange = new FunctionalExchangeMock("DataExchange");

		provider.convertCapellaFunctionalExchangeToAbstractFunctionalExchangeAndAttachItToSystemModel(systemModel,
				dataModel, dataViewPackage, sourceFunction, targetFunction, mockedDataExchange, issues);

		assertTrue(issues.size() > 0);
		assertIssuePresent(issues, "Functional exchange DataExchange does not carry any items");
		assertIssuePresent(issues, "Exchange DataExchange is of an invalid type");
	}

}
