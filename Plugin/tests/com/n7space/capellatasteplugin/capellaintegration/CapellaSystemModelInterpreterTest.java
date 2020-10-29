// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import org.polarsys.capella.core.data.information.ExchangeMechanism;
import org.polarsys.capella.core.data.pa.PhysicalComponentNature;

import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.DataModelElementInterpreter;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.DataPackageMock;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.ExchangeItemTypeMock;
import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.CustomCapellaProperties;
import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.testmocks.ComponentExchangeMock;
import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.testmocks.FunctionalExchangeMock;
import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.testmocks.PhysicalActorPkgMock;
import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.testmocks.PhysicalArchitectureMock;
import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.testmocks.PhysicalComponentMock;
import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.testmocks.PhysicalContextMock;
import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.testmocks.PhysicalFunctionMock;
import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.testmocks.PhysicalLinkMock;
import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.testmocks.PhysicalPortMock;
import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.testmocks.StringPropertyMock;
import com.n7space.capellatasteplugin.modelling.NameConverter;
import com.n7space.capellatasteplugin.modelling.architecture.SystemModel;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.utils.Issue;

public class CapellaSystemModelInterpreterTest {

	protected PhysicalArchitectureMock createMockPhysicalArchitecture() {
		final PhysicalComponentMock rootComponent = new PhysicalComponentMock("System");
		final PhysicalActorPkgMock actorPkg = new PhysicalActorPkgMock();
		final PhysicalContextMock context = new PhysicalContextMock();
		final PhysicalArchitectureMock result = new PhysicalArchitectureMock(rootComponent, actorPkg, context);
		final PhysicalComponentMock node2 = new PhysicalComponentMock("N1", PhysicalComponentNature.NODE);
		final PhysicalComponentMock node1 = new PhysicalComponentMock("N2", PhysicalComponentNature.NODE);
		final PhysicalComponentMock component1 = new PhysicalComponentMock("C1", PhysicalComponentNature.BEHAVIOR);
		final PhysicalComponentMock component2 = new PhysicalComponentMock("C2", PhysicalComponentNature.BEHAVIOR);
		rootComponent.addOwnedPhysicalComponent(node1);
		rootComponent.addOwnedPhysicalComponent(node2);
		rootComponent.addOwnedPhysicalComponent(component1);
		rootComponent.addOwnedPhysicalComponent(component2);
		component1.addDeployingComponent(node1);
		component2.addDeployingComponent(node2);
		final PhysicalFunctionMock function1 = new PhysicalFunctionMock("F1");
		final PhysicalFunctionMock function2 = new PhysicalFunctionMock("F2");
		component1.addAllocatedPhysicalFunction(function1);
		component2.addAllocatedPhysicalFunction(function2);
		final PhysicalPortMock srcPort = new PhysicalPortMock(node1, "Src");
		final PhysicalPortMock dstPort = new PhysicalPortMock(node2, "Dst");
		final PhysicalLinkMock link = new PhysicalLinkMock("Link", srcPort, dstPort);
		rootComponent.addPhysicalLink(link);
		final FunctionalExchangeMock functionalExchange1 = new FunctionalExchangeMock("FE1");
		final FunctionalExchangeMock functionalExchange2 = new FunctionalExchangeMock("FE2");
		final ComponentExchangeMock componentExchange = new ComponentExchangeMock("CE");
		function1.addOutgoingEdge(functionalExchange1);
		function2.addIncomingEdge(functionalExchange1);
		function1.addIncomingEdge(functionalExchange2);
		function2.addOutgoingEdge(functionalExchange2);
		componentExchange.addAllocatedFunctionalExchange(functionalExchange1);
		componentExchange.addAllocatedFunctionalExchange(functionalExchange2);
		link.addAllocatedComponentExchange(componentExchange);
		function1.addAppliedPropertyValue(new StringPropertyMock(CustomCapellaProperties.LANGUAGE, "SDL"));
		function2.addAppliedPropertyValue(new StringPropertyMock(CustomCapellaProperties.LANGUAGE, "C"));
		link.addAppliedPropertyValue(new StringPropertyMock(CustomCapellaProperties.BUS, "Taste::Bus.i"));
		node1.addAppliedPropertyValue(new StringPropertyMock(CustomCapellaProperties.PROCESSOR, "Taste::Processor.i"));
		node2.addAppliedPropertyValue(new StringPropertyMock(CustomCapellaProperties.PROCESSOR, "Taste::Processor.i"));
		srcPort.addAppliedPropertyValue(
				new StringPropertyMock(CustomCapellaProperties.DEVICE_IMPLEMENTATION, "Taste::Device.i"));
		srcPort.addAppliedPropertyValue(new StringPropertyMock(CustomCapellaProperties.CONFIGURATION, "{}"));
		srcPort.addAppliedPropertyValue(new StringPropertyMock(CustomCapellaProperties.CONFIGURATION_SCHEMA, "{}"));
		dstPort.addAppliedPropertyValue(
				new StringPropertyMock(CustomCapellaProperties.DEVICE_IMPLEMENTATION, "Taste::Device.i"));
		dstPort.addAppliedPropertyValue(new StringPropertyMock(CustomCapellaProperties.CONFIGURATION, "{}"));
		dstPort.addAppliedPropertyValue(new StringPropertyMock(CustomCapellaProperties.CONFIGURATION_SCHEMA, "{}"));
		final DataPackageMock dataPkg = new DataPackageMock("Data", "DataId");
		final ExchangeItemTypeMock exchangeItem = new ExchangeItemTypeMock(dataPkg, "ExchangeItem", "ExchangeItemId",
				ExchangeMechanism.EVENT);
		functionalExchange1.addExchangedItem(exchangeItem);
		functionalExchange2.addExchangedItem(exchangeItem);

		return result;
	}

	protected PhysicalArchitectureMock createMockPhysicalArchitectureWithNameColllisions() {
		final PhysicalComponentMock rootComponent = new PhysicalComponentMock("System");
		final PhysicalActorPkgMock actorPkg = new PhysicalActorPkgMock();
		final PhysicalContextMock context = new PhysicalContextMock();
		final PhysicalArchitectureMock result = new PhysicalArchitectureMock(rootComponent, actorPkg, context);
		final PhysicalComponentMock node2 = new PhysicalComponentMock("N", PhysicalComponentNature.NODE);
		final PhysicalComponentMock node1 = new PhysicalComponentMock("N", PhysicalComponentNature.NODE);
		final PhysicalComponentMock component1 = new PhysicalComponentMock("C", PhysicalComponentNature.BEHAVIOR);
		final PhysicalComponentMock component2 = new PhysicalComponentMock("C", PhysicalComponentNature.BEHAVIOR);
		rootComponent.addOwnedPhysicalComponent(node1);
		rootComponent.addOwnedPhysicalComponent(node2);
		rootComponent.addOwnedPhysicalComponent(component1);
		rootComponent.addOwnedPhysicalComponent(component2);
		component1.addDeployingComponent(node1);
		component2.addDeployingComponent(node2);
		final PhysicalFunctionMock function1 = new PhysicalFunctionMock("F");
		final PhysicalFunctionMock function2 = new PhysicalFunctionMock("F");
		component1.addAllocatedPhysicalFunction(function1);
		component2.addAllocatedPhysicalFunction(function2);
		final PhysicalPortMock srcPort = new PhysicalPortMock(node1, "P");
		final PhysicalPortMock dstPort = new PhysicalPortMock(node2, "P");
		final PhysicalLinkMock link = new PhysicalLinkMock("Link", srcPort, dstPort);
		rootComponent.addPhysicalLink(link);
		final FunctionalExchangeMock functionalExchange1 = new FunctionalExchangeMock("FE");
		final FunctionalExchangeMock functionalExchange2 = new FunctionalExchangeMock("FE");
		final ComponentExchangeMock componentExchange = new ComponentExchangeMock("CE");
		function1.addOutgoingEdge(functionalExchange1);
		function2.addIncomingEdge(functionalExchange1);
		function1.addIncomingEdge(functionalExchange2);
		function2.addOutgoingEdge(functionalExchange2);
		componentExchange.addAllocatedFunctionalExchange(functionalExchange1);
		componentExchange.addAllocatedFunctionalExchange(functionalExchange2);
		link.addAllocatedComponentExchange(componentExchange);
		function1.addAppliedPropertyValue(new StringPropertyMock(CustomCapellaProperties.LANGUAGE, "SDL"));
		function2.addAppliedPropertyValue(new StringPropertyMock(CustomCapellaProperties.LANGUAGE, "C"));
		link.addAppliedPropertyValue(new StringPropertyMock(CustomCapellaProperties.BUS, "Taste::Bus.i"));
		node1.addAppliedPropertyValue(new StringPropertyMock(CustomCapellaProperties.PROCESSOR, "Taste::Processor.i"));
		node2.addAppliedPropertyValue(new StringPropertyMock(CustomCapellaProperties.PROCESSOR, "Taste::Processor.i"));
		srcPort.addAppliedPropertyValue(
				new StringPropertyMock(CustomCapellaProperties.DEVICE_IMPLEMENTATION, "Taste::Device.i"));
		srcPort.addAppliedPropertyValue(new StringPropertyMock(CustomCapellaProperties.CONFIGURATION, "{}"));
		srcPort.addAppliedPropertyValue(new StringPropertyMock(CustomCapellaProperties.CONFIGURATION_SCHEMA, "{}"));
		dstPort.addAppliedPropertyValue(
				new StringPropertyMock(CustomCapellaProperties.DEVICE_IMPLEMENTATION, "Taste::Device.i"));
		dstPort.addAppliedPropertyValue(new StringPropertyMock(CustomCapellaProperties.CONFIGURATION, "{}"));
		dstPort.addAppliedPropertyValue(new StringPropertyMock(CustomCapellaProperties.CONFIGURATION_SCHEMA, "{}"));
		final DataPackageMock dataPkg = new DataPackageMock("Data", "DataId");
		final ExchangeItemTypeMock exchangeItem = new ExchangeItemTypeMock(dataPkg, "ExchangeItem", "ExchangeItemId",
				ExchangeMechanism.EVENT);
		functionalExchange1.addExchangedItem(exchangeItem);
		functionalExchange2.addExchangedItem(exchangeItem);

		return result;
	}

	@Test
	public void testConvertCapellaSystemModelToAbstractSystemModel() {
		final DataModelElementInterpreter dataInterpreter = new DataModelElementInterpreter();
		final NameConverter converter = new NameConverter();
		final CapellaSystemModelInterpreter interpreter = new CapellaSystemModelInterpreter(dataInterpreter, converter);

		final List<Issue> issues = new LinkedList<Issue>();
		final DataModel dataModel = new DataModel();
		final PhysicalArchitectureMock systemRoot = createMockPhysicalArchitecture();

		final SystemModel systemModel = interpreter.convertCapellaSystemModelToAbstractSystemModel(systemRoot,
				dataModel, issues);
		assertEquals(dataModel.dataPackages.size(), 3);
		assertEquals(dataModel.dataPackages.get(0).name, "DataView");
		assertEquals(dataModel.dataPackages.get(1).name, "TASTE-BasicTypes");
		assertEquals(systemModel.components.size(), 4);
		assertEquals(systemModel.functions.size(), 2);
		assertEquals(systemModel.topLevelComponents.size(), 2);
		assertEquals(systemModel.name, "System");
		assertEquals(issues.size(), 0);
	}

	@Test
	public void testConvertCapellaSystemModelToAbstractSystemModel_removesUnusedComponents() {
		final DataModelElementInterpreter dataInterpreter = new DataModelElementInterpreter();
		final NameConverter converter = new NameConverter();
		final CapellaSystemModelInterpreter interpreter = new CapellaSystemModelInterpreter(dataInterpreter, converter);

		final List<Issue> issues = new LinkedList<Issue>();
		final DataModel dataModel = new DataModel();
		final PhysicalArchitectureMock systemRoot = createMockPhysicalArchitecture();

		final PhysicalComponentMock node = new PhysicalComponentMock("N", PhysicalComponentNature.NODE);
		final PhysicalComponentMock component = new PhysicalComponentMock("C", PhysicalComponentNature.BEHAVIOR);
		component.addDeployingComponent(node);
		((PhysicalComponentMock) systemRoot.getOwnedPhysicalComponent()).addOwnedPhysicalComponent(node);
		((PhysicalComponentMock) systemRoot.getOwnedPhysicalComponent()).addOwnedPhysicalComponent(component);

		final SystemModel systemModel = interpreter.convertCapellaSystemModelToAbstractSystemModel(systemRoot,
				dataModel, issues);

		assertEquals(systemModel.components.size(), 4);
		assertEquals(systemModel.topLevelComponents.size(), 2);
		assertEquals(systemModel.name, "System");
		assertEquals(issues.size(), 0);
	}

	@Test
	public void testConvertCapellaSystemModelToAbstractSystemModel_reportsNamingIssues() {
		final DataModelElementInterpreter dataInterpreter = new DataModelElementInterpreter();
		final NameConverter converter = new NameConverter();
		final CapellaSystemModelInterpreter interpreter = new CapellaSystemModelInterpreter(dataInterpreter, converter);

		final List<Issue> issues = new LinkedList<Issue>();
		final DataModel dataModel = new DataModel();
		final PhysicalArchitectureMock systemRoot = createMockPhysicalArchitectureWithNameColllisions();

		interpreter.convertCapellaSystemModelToAbstractSystemModel(systemRoot, dataModel, issues);
		assertEquals(issues.size(), 5);
		assertEquals(issues.get(0).description, "Name P is not unique when transformed for AADL use (p)");
		assertEquals(issues.get(1).description, "Name FE is not unique when transformed for AADL use (fe)");
		assertEquals(issues.get(2).description, "Name FE is not unique when transformed for AADL use (fe)");
		assertEquals(issues.get(3).description, "Name FE is not unique when transformed for AADL use (fe)");
		assertEquals(issues.get(4).description, "Name F is not unique when transformed for AADL use (f)");
	}

}
