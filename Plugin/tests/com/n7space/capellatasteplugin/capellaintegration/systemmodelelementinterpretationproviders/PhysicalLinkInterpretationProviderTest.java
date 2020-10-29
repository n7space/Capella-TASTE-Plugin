// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders;

import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.testmocks.ComponentExchangeMock;
import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.testmocks.ComponentPortMock;
import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.testmocks.FunctionalExchangeMock;
import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.testmocks.PhysicalComponentMock;
import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.testmocks.PhysicalLinkMock;
import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.testmocks.PhysicalPathInvolvementMock;
import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.testmocks.PhysicalPathMock;
import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.testmocks.PhysicalPortMock;
import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.testmocks.StringPropertyMock;
import com.n7space.capellatasteplugin.modelling.architecture.FunctionExchange;
import com.n7space.capellatasteplugin.modelling.architecture.FunctionExchange.ExchangeType;
import com.n7space.capellatasteplugin.modelling.architecture.SystemModel;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.utils.Issue;

public class PhysicalLinkInterpretationProviderTest {

	@Test
	public void testConvertCapellaPhysicalLinkToComponentExchangeAndAttachItToSystemModel() {
		final PhysicalComponentInterpretationProvider componentInterpreter = new PhysicalComponentInterpretationProvider();
		final PhysicalLinkInterpretationProvider linkInterpreter = new PhysicalLinkInterpretationProvider();
		final DataModel dataModel = new DataModel();
		final SystemModel systemModel = new SystemModel("System", "SystemId", dataModel);
		final List<Issue> issues = new LinkedList<Issue>();

		final PhysicalComponentMock mockedSourceComponent = new PhysicalComponentMock("SC");
		final PhysicalComponentMock mockedTargetComponent = new PhysicalComponentMock("TC");
		final PhysicalPortMock mockedSourcePort = new PhysicalPortMock(mockedSourceComponent, "SP");
		final PhysicalPortMock mockedTargetPort = new PhysicalPortMock(mockedTargetComponent, "TP");
		final PhysicalLinkMock mockedLink = new PhysicalLinkMock("Link", mockedSourcePort, mockedTargetPort);
		final ComponentExchangeMock mockedComponentExchange = new ComponentExchangeMock("ComponentExchange");
		final FunctionalExchangeMock mockedFunctionalExchange = new FunctionalExchangeMock("FunctionalExchange");
		mockedComponentExchange.addAllocatedFunctionalExchange(mockedFunctionalExchange);

		mockedSourcePort.addAppliedPropertyValue(new StringPropertyMock("Taste::Device", "Taste::Connector.i"));
		mockedSourcePort.addAppliedPropertyValue(new StringPropertyMock("Taste::Configuration", "{}"));
		mockedSourcePort.addAppliedPropertyValue(new StringPropertyMock("Taste::ConfigurationSchema", "/path"));

		mockedTargetPort.addAppliedPropertyValue(new StringPropertyMock("Taste::Device", "Taste::Connector.i"));
		mockedTargetPort.addAppliedPropertyValue(new StringPropertyMock("Taste::Configuration", "{}"));
		mockedTargetPort.addAppliedPropertyValue(new StringPropertyMock("Taste::ConfigurationSchema", "/path"));

		mockedLink.addAppliedPropertyValue(new StringPropertyMock("Taste::Bus", "Taste::ExampleBus"));
		mockedLink.addAllocatedComponentExchange(mockedComponentExchange);

		// Add the mocked components to the model
		componentInterpreter.convertCapellaComponentToAbstractComponentAndAttachItToSystemModel(systemModel, null,
				mockedSourceComponent, null, issues);
		componentInterpreter.convertCapellaComponentToAbstractComponentAndAttachItToSystemModel(systemModel, null,
				mockedTargetComponent, null, issues);
		// Add the corresponding FunctionalExchange to the model; using a interpreter is
		// more complex to to the dependency
		// on data
		systemModel.functionExchanges.add(
				new FunctionExchange(null, null, "FunctionalExchange", "FunctionalExchangeId", ExchangeType.EVENT));

		linkInterpreter.convertCapellaPhysicalLinkToComponentExchangeAndAttachItToSystemModel(systemModel, mockedLink,
				issues);

		assertTrue(systemModel.componentExchanges.size() == 1);
		assertTrue(systemModel.componentExchanges.get(0).name.equals("Link"));
		assertTrue(issues.size() == 0);
	}

	@Test
	public void testConvertCapellaPhysicalPathToComponentExchangeAndAttachItToSystemModel() {
		final PhysicalComponentInterpretationProvider componentInterpreter = new PhysicalComponentInterpretationProvider();
		final PhysicalLinkInterpretationProvider linkInterpreter = new PhysicalLinkInterpretationProvider();
		final DataModel dataModel = new DataModel();
		final SystemModel systemModel = new SystemModel("System", "SystemId", dataModel);
		final List<Issue> issues = new LinkedList<Issue>();

		final PhysicalComponentMock mockedSourceComponent = new PhysicalComponentMock("SC");
		final PhysicalComponentMock mockedTargetComponent = new PhysicalComponentMock("TC");
		final PhysicalPortMock mockedSourcePort = new PhysicalPortMock(mockedSourceComponent, "SP");
		final PhysicalPortMock mockedTargetPort = new PhysicalPortMock(mockedTargetComponent, "TP");

		final ComponentPortMock mockedSourceComponentPort = new ComponentPortMock("SCP");
		final ComponentPortMock mockedTargetComponentPort = new ComponentPortMock("TCP");

		final PhysicalLinkMock mockedLink = new PhysicalLinkMock("Link", mockedSourcePort, mockedTargetPort);
		final PhysicalPathMock mockedPath = new PhysicalPathMock("Path");
		final ComponentExchangeMock mockedComponentExchange = new ComponentExchangeMock("ComponentExchange",
				mockedSourceComponentPort, mockedTargetComponentPort);
		final FunctionalExchangeMock mockedFunctionalExchange = new FunctionalExchangeMock("FunctionalExchange");
		mockedComponentExchange.addAllocatedFunctionalExchange(mockedFunctionalExchange);

		mockedSourcePort.addAppliedPropertyValue(new StringPropertyMock("Taste::Device", "Taste::Connector.i"));
		mockedSourcePort.addAppliedPropertyValue(new StringPropertyMock("Taste::Configuration", "{}"));
		mockedSourcePort.addAppliedPropertyValue(new StringPropertyMock("Taste::ConfigurationSchema", "/path"));
		mockedSourcePort.addAllocatedComponentPort(mockedSourceComponentPort);

		mockedTargetPort.addAppliedPropertyValue(new StringPropertyMock("Taste::Device", "Taste::Connector.i"));
		mockedTargetPort.addAppliedPropertyValue(new StringPropertyMock("Taste::Configuration", "{}"));
		mockedTargetPort.addAppliedPropertyValue(new StringPropertyMock("Taste::ConfigurationSchema", "/path"));
		mockedTargetPort.addAllocatedComponentPort(mockedTargetComponentPort);

		mockedPath.addAppliedPropertyValue(new StringPropertyMock("Taste::Bus", "Taste::ExampleBus"));
		mockedPath.addAllocatedComponentExchange(mockedComponentExchange);
		mockedPath.addInvolvedInvolvement(new PhysicalPathInvolvementMock(mockedLink));

		// Add the mocked components to the model
		componentInterpreter.convertCapellaComponentToAbstractComponentAndAttachItToSystemModel(systemModel, null,
				mockedSourceComponent, null, issues);
		componentInterpreter.convertCapellaComponentToAbstractComponentAndAttachItToSystemModel(systemModel, null,
				mockedTargetComponent, null, issues);
		// Add the corresponding FunctionalExchange to the model; using a interpreter is
		// more complex to to the dependency
		// on data
		systemModel.functionExchanges.add(
				new FunctionExchange(null, null, "FunctionalExchange", "FunctionalExchangeId", ExchangeType.EVENT));

		linkInterpreter.convertCapellaPhysicalPathToComponentExchangeAndAttachItToSystemModel(systemModel, mockedPath,
				issues);

		assertTrue(systemModel.componentExchanges.size() == 1);
		assertTrue(systemModel.componentExchanges.get(0).name.equals("Path"));
		assertTrue(issues.size() == 0);
	}

	protected PhysicalLinkMock createPhysicalLinkWithEnvironmentWithoutProperties(final SystemModel systemModel) {
		final PhysicalComponentInterpretationProvider componentInterpreter = new PhysicalComponentInterpretationProvider();
		final List<Issue> issues = new LinkedList<Issue>();
		final PhysicalComponentMock mockedSourceComponent = new PhysicalComponentMock("SC");
		final PhysicalComponentMock mockedTargetComponent = new PhysicalComponentMock("TC");
		final PhysicalPortMock mockedSourcePort = new PhysicalPortMock(mockedSourceComponent, "SP");
		final PhysicalPortMock mockedTargetPort = new PhysicalPortMock(mockedTargetComponent, "TP");
		final PhysicalLinkMock mockedLink = new PhysicalLinkMock("Link", mockedSourcePort, mockedTargetPort);
		final ComponentExchangeMock mockedComponentExchange = new ComponentExchangeMock("ComponentExchange");
		final FunctionalExchangeMock mockedFunctionalExchange = new FunctionalExchangeMock("FunctionalExchange");
		mockedComponentExchange.addAllocatedFunctionalExchange(mockedFunctionalExchange);

		mockedLink.addAllocatedComponentExchange(mockedComponentExchange);

		// Add the mocked components to the model
		componentInterpreter.convertCapellaComponentToAbstractComponentAndAttachItToSystemModel(systemModel, null,
				mockedSourceComponent, null, issues);
		componentInterpreter.convertCapellaComponentToAbstractComponentAndAttachItToSystemModel(systemModel, null,
				mockedTargetComponent, null, issues);
		// Add the corresponding FunctionalExchange to the model; using a interpreter is
		// more complex to to the dependency
		// on data
		systemModel.functionExchanges.add(
				new FunctionExchange(null, null, "FunctionalExchange", "FunctionalExchangeId", ExchangeType.EVENT));

		return mockedLink;
	}

	@Test
	public void testCapellaPhysicalLinkConversionReportsMissingProperties() {
		final PhysicalLinkInterpretationProvider linkInterpreter = new PhysicalLinkInterpretationProvider();
		final DataModel dataModel = new DataModel();
		final SystemModel systemModel = new SystemModel("System", "SystemId", dataModel);
		final List<Issue> issues = new LinkedList<Issue>();

		final PhysicalLinkMock mockedLink = createPhysicalLinkWithEnvironmentWithoutProperties(systemModel);

		linkInterpreter.convertCapellaPhysicalLinkToComponentExchangeAndAttachItToSystemModel(systemModel, mockedLink,
				issues);

		assertTrue(issues.size() == 3);
		assertTrue(issues.get(0).description.contains("Device implementation for port SP is undefined"));
		assertTrue(issues.get(1).description.contains("Device implementation for port TP is undefined"));
		assertTrue(issues.get(2).description.contains("Bus class for Link is undefined"));
	}

}
