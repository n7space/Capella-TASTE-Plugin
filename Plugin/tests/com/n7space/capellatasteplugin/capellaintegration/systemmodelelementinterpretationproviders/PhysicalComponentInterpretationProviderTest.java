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
import org.polarsys.capella.core.data.pa.PhysicalComponentNature;

import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.testmocks.PhysicalActorMock;
import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.testmocks.PhysicalComponentMock;
import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.testmocks.PhysicalFunctionMock;
import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.testmocks.StringPropertyMock;
import com.n7space.capellatasteplugin.modelling.architecture.SystemModel;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.utils.Issue;

public class PhysicalComponentInterpretationProviderTest {

	@Test
	public void testConvertCapellaModelWithComponentNodeToSystemModel() {
		final PhysicalComponentInterpretationProvider provider = new PhysicalComponentInterpretationProvider();
		final List<PhysicalFunctionWithParent> discoveredFunctions = new LinkedList<PhysicalFunctionWithParent>();
		final List<Issue> issues = new LinkedList<Issue>();
		final DataModel dataModel = new DataModel();
		final SystemModel systemModel = new SystemModel("TestModel", "TestId", dataModel);

		final PhysicalComponentMock mockedComponent = new PhysicalComponentMock("MockedComponent",
				PhysicalComponentNature.NODE);
		final PhysicalComponentMock mockedOwnedComponent = new PhysicalComponentMock("MockedOwnedComponent");
		final PhysicalFunctionMock mockedFunction = new PhysicalFunctionMock("MockedFunction");

		mockedComponent.addAppliedPropertyValue(new StringPropertyMock("Taste::Processor", "Pkg::Processor"));
		mockedComponent.addOwnedPhysicalComponent(mockedOwnedComponent);

		mockedOwnedComponent.addDeployingComponent(mockedComponent);
		mockedOwnedComponent.addAllocatedPhysicalFunction(mockedFunction);

		// Process the components.
		provider.convertCapellaComponentToAbstractComponentAndAttachItToSystemModel(systemModel, null, mockedComponent,
				discoveredFunctions, issues);
		// Update deployment.
		provider.processComponentDeployment(systemModel, issues);

		assertTrue(systemModel.components.size() == 2);
		assertTrue(systemModel.components.get(0).name.equals("MockedComponent"));

		assertTrue(systemModel.components.get(1).name.equals("MockedOwnedComponent"));
		assertTrue(systemModel.components.get(0).deployedComponents.get(0).equals(systemModel.components.get(1)));

		assertTrue(systemModel.topLevelComponents.size() == 1);
		assertTrue(systemModel.topLevelComponents.get(0).name.equals("MockedComponent"));

		assertTrue(discoveredFunctions.size() == 1);
		assertTrue(discoveredFunctions.get(0).function.equals(mockedFunction));

		assertTrue(issues.size() == 0);
	}

	@Test
	public void testConvertCapellaModelWithActorNodeToSystemModel() {
		final PhysicalComponentInterpretationProvider provider = new PhysicalComponentInterpretationProvider();
		final List<PhysicalFunctionWithParent> discoveredFunctions = new LinkedList<PhysicalFunctionWithParent>();
		final List<Issue> issues = new LinkedList<Issue>();
		final DataModel dataModel = new DataModel();
		final SystemModel systemModel = new SystemModel("TestModel", "TestId", dataModel);

		final PhysicalActorMock mockedActor = new PhysicalActorMock("MockedActor");
		final PhysicalComponentMock mockedDeployedComponent = new PhysicalComponentMock("MockedDeployedComponent");
		final PhysicalComponentMock mockedOwnedComponent = new PhysicalComponentMock("MockedOwnedComponent");
		final PhysicalFunctionMock mockedFunction = new PhysicalFunctionMock("MockedFunction");

		mockedActor.addAppliedPropertyValue(new StringPropertyMock("Taste::Processor", "Pkg::Processor"));
		mockedActor.addDeployedComponent(mockedDeployedComponent);

		mockedDeployedComponent.addOwnedPhysicalComponent(mockedOwnedComponent);
		mockedDeployedComponent.addDeployingActor(mockedActor);
		mockedOwnedComponent.addDeployingComponent(mockedDeployedComponent);
		mockedOwnedComponent.addAllocatedPhysicalFunction(mockedFunction);

		// Process the components first.
		provider.convertCapellaComponentToAbstractComponentAndAttachItToSystemModel(systemModel, null,
				mockedDeployedComponent, discoveredFunctions, issues);
		// Process the actor.
		provider.convertCapellaActorToAbstractComponentAndAttachItToSystemModel(systemModel, null, mockedActor,
				discoveredFunctions, issues);
		// Update deployment.
		provider.processComponentDeployment(systemModel, issues);

		assertTrue(systemModel.components.size() == 3);
		assertTrue(systemModel.components.get(0).name.equals("MockedDeployedComponent"));

		assertTrue(systemModel.components.get(1).name.equals("MockedOwnedComponent"));
		assertTrue(systemModel.components.get(0).deployedComponents.get(0).equals(systemModel.components.get(1)));

		assertTrue(systemModel.components.get(2).name.equals("MockedActor"));
		assertTrue(systemModel.components.get(2).processor.typeClass.equals("Pkg::Processor"));
		assertTrue(systemModel.components.get(2).deployedComponents.get(0).equals(systemModel.components.get(0)));

		assertTrue(systemModel.topLevelComponents.size() == 1);
		assertTrue(systemModel.topLevelComponents.get(0).name.equals("MockedActor"));

		assertTrue(discoveredFunctions.size() == 1);
		assertTrue(discoveredFunctions.get(0).function.equals(mockedFunction));

		assertTrue(issues.size() == 0);
	}

}
