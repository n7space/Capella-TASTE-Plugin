// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.polarsys.capella.core.data.information.DataPkg;
import org.polarsys.capella.core.data.information.datatype.NumericTypeKind;

import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.DataPackageMock;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.NumericTypeMock;
import com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks.UnionTypeMock;
import com.n7space.capellatasteplugin.capellaintegration.mmi.DataModelBrowser;
import com.n7space.capellatasteplugin.capellaintegration.mmi.DirectoryBrowser;
import com.n7space.capellatasteplugin.capellaintegration.mmi.IssuePresenter;
import com.n7space.capellatasteplugin.capellaintegration.mmi.MessagePresenter;
import com.n7space.capellatasteplugin.capellaintegration.mmi.PresentationFeedback;
import com.n7space.capellatasteplugin.capellaintegration.mmi.SystemModelBrowser;
import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.testmocks.PhysicalActorPkgMock;
import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.testmocks.PhysicalArchitectureMock;
import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.testmocks.PhysicalComponentMock;
import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.testmocks.PhysicalContextMock;
import com.n7space.capellatasteplugin.modelling.ModelItem;
import com.n7space.capellatasteplugin.modelling.ModelItems;
import com.n7space.capellatasteplugin.modelling.ModelSerializer;
import com.n7space.capellatasteplugin.modelling.architecture.ArchitectureElement;
import com.n7space.capellatasteplugin.modelling.architecture.SystemModel;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataModelElement;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.utils.ConfigurableObject.Option;
import com.n7space.capellatasteplugin.utils.Issue;

public class CoordinatorTests {

	protected static class MockDataModelBrowser implements DataModelBrowser {

		public boolean wasInvoked = false;

		@Override
		public void captureDataModelSubsetSelection(final DataModel dataModel,
				final DataModelPresentationCallback callback) {
			wasInvoked = true;
			final List<DataModelElement> elements = new LinkedList<DataModelElement>();
			for (final DataPackage pkg : dataModel.dataPackages) {
				elements.add(pkg);
				for (final DataModelElement element : pkg.getDefinedElements()) {
					elements.add(element);
				}
			}
			callback.onDataModelPresentationCallback(dataModel, elements, PresentationFeedback.Accepted);
		}

	}

	protected static class MockDirectoryBrowser implements DirectoryBrowser {

		public boolean wasInvoked = false;
		public String lastMessage = null;
		public String directoryToReturn = null;

		@Override
		public String getOutputDirectory(final String message) {
			wasInvoked = true;
			lastMessage = message;
			return directoryToReturn;
		}

	}

	protected static class MockIssuePresenter implements IssuePresenter {

		public boolean wasInvoked = false;
		public PresentationFeedback desiredReturnValue = PresentationFeedback.Accepted;

		@Override
		public void PresentIssues(final List<Issue> issues, final IssuePresentationCallback callback) {
			wasInvoked = true;
			callback.onIssuePresentationFeedback(issues, desiredReturnValue);
		}

	}

	protected static class MockMessagePresenter implements MessagePresenter {

		public boolean wasInvoked = false;
		public String lastMessage = null;

		@Override
		public void presentMessage(final MessageKind kind, final String title, final String message) {
			wasInvoked = true;
			lastMessage = message;

		}

	}

	protected static class MockSerializer implements ModelSerializer {

		public boolean wasInvoked = false;
		public String lastPath = null;
		public ModelItems lastItems = null;

		@Override
		public void serialize(final String path, final ModelItems items) throws IOException {
			wasInvoked = true;
			lastPath = path;
			lastItems = items;
		}

	}

	protected static class MockSystemModelBrowser implements SystemModelBrowser {

		public boolean wasInvoked = false;

		@Override
		public void captureSystemModelSubsetSelection(final SystemModel model,
				final SystemModelPresentationCallback callback) {
			wasInvoked = true;
			final List<ArchitectureElement> elements = new LinkedList<ArchitectureElement>();
			elements.addAll(model.components);
			elements.addAll(model.functions);
			callback.onSystemModelPresentationCallback(model, elements, PresentationFeedback.Accepted);
		}

	}

	protected Option option1 = null;
	protected Option option2 = null;
	protected Option[] options = null;
	protected String option1Handle = "Handle1";
	protected String option2Handle = "Handle2";
	protected Coordinator coordinator;
	protected MockSystemModelBrowser systemModelBrowser;
	protected MockDataModelBrowser dataModelBrowser;
	protected MockIssuePresenter issuePresenter;
	protected MockMessagePresenter messagePresenter;
	protected MockDirectoryBrowser directoryBrowser;
	protected MockSerializer serializer;

	protected DataPackageMock pkgMock;
	protected NumericTypeMock typeMock;

	protected PhysicalArchitectureMock architectureMock;
	protected PhysicalComponentMock componentMock;
	protected PhysicalActorPkgMock actorPkgMock;
	protected PhysicalContextMock contextMock;

	protected void addUnionWithIssuesToPackageMock() {
		final UnionTypeMock parent = new UnionTypeMock(pkgMock, "UnionWithIssuesParent",
				"UnionWithIssuesParent" + "_id");
		final UnionTypeMock mock = new UnionTypeMock(pkgMock, parent, "UnionWithIssues", "UnionWithIssues" + "_id");
		pkgMock.classes.add(parent);
		pkgMock.classes.add(mock);
	}

	@Before
	public void setUp() throws Exception {
		option1 = new Option(option1Handle, "Description1", "default");
		option2 = new Option(option2Handle, "Description2", "default");
		options = new Option[2];
		options[0] = option1;
		options[1] = option2;

		systemModelBrowser = new MockSystemModelBrowser();
		dataModelBrowser = new MockDataModelBrowser();
		issuePresenter = new MockIssuePresenter();
		messagePresenter = new MockMessagePresenter();
		directoryBrowser = new MockDirectoryBrowser();
		serializer = new MockSerializer();

		coordinator = Coordinator.getInstance();
		coordinator.setDataModelBrowser(dataModelBrowser);
		coordinator.setDirectoryBrowser(directoryBrowser);
		coordinator.setIssuePresenter(issuePresenter);
		coordinator.setMessagePresenter(messagePresenter);
		coordinator.setSystemModelBrowser(systemModelBrowser);
		coordinator.setModelSerializer(serializer);

		pkgMock = new DataPackageMock("Pkg1", "Pkg1" + "_id");
		typeMock = new NumericTypeMock(pkgMock, "FloatMock", "FloatMock" + "_id", NumericTypeKind.FLOAT);
		pkgMock.dataTypes.add(typeMock);

		componentMock = new PhysicalComponentMock("System");
		actorPkgMock = new PhysicalActorPkgMock();
		contextMock = new PhysicalContextMock();
		architectureMock = new PhysicalArchitectureMock(componentMock, actorPkgMock, contextMock);
	}

	@Test
	public void testProcessArchitecture() {

		directoryBrowser.directoryToReturn = "dummyDirectory";

		Coordinator.getInstance().processArchitecture(architectureMock);

		assertTrue(systemModelBrowser.wasInvoked);
		assertTrue(messagePresenter.wasInvoked);
		assertTrue(directoryBrowser.wasInvoked);
		assertTrue(serializer.wasInvoked);

		assertEquals(serializer.lastPath, directoryBrowser.directoryToReturn);
		assertEquals(serializer.lastItems.size(), 3);
		assertEquals(serializer.lastItems.get(0).kind, ModelItem.Kind.AADL);
		assertEquals(serializer.lastItems.get(0).name, "InterfaceView");
		assertEquals(serializer.lastItems.get(1).kind, ModelItem.Kind.AADL);
		assertEquals(serializer.lastItems.get(1).name, "DeploymentView");
		assertEquals(serializer.lastItems.get(2).kind, ModelItem.Kind.ASN1);
		assertEquals(serializer.lastItems.get(2).name, "DataView");
	}

	@Test
	public void testProcessDataPackages() {
		final List<DataPkg> selection = new LinkedList<DataPkg>();
		selection.add(pkgMock);

		directoryBrowser.directoryToReturn = "dummyDirectory";

		Coordinator.getInstance().processDataPackages(selection);

		assertTrue(dataModelBrowser.wasInvoked);
		assertTrue(messagePresenter.wasInvoked);
		assertTrue(directoryBrowser.wasInvoked);
		assertTrue(serializer.wasInvoked);

		assertEquals(serializer.lastPath, directoryBrowser.directoryToReturn);
		assertEquals(serializer.lastItems.size(), 1);
		assertEquals(serializer.lastItems.get(0).kind, ModelItem.Kind.ASN1);
		assertEquals(serializer.lastItems.get(0).name, pkgMock.getName());
	}

	@Test
	public void testProcessDataPackages_showsIssues() {
		addUnionWithIssuesToPackageMock();
		final List<DataPkg> selection = new LinkedList<DataPkg>();
		selection.add(pkgMock);

		directoryBrowser.directoryToReturn = "dummyDirectory";

		Coordinator.getInstance().processDataPackages(selection);

		assertTrue(issuePresenter.wasInvoked);
		assertTrue(dataModelBrowser.wasInvoked);
		assertTrue(messagePresenter.wasInvoked);
		assertTrue(directoryBrowser.wasInvoked);
		assertTrue(serializer.wasInvoked);

		assertEquals(serializer.lastPath, directoryBrowser.directoryToReturn);
		assertEquals(serializer.lastItems.size(), 1);
		assertEquals(serializer.lastItems.get(0).kind, ModelItem.Kind.ASN1);
		assertEquals(serializer.lastItems.get(0).name, pkgMock.getName());
	}

	@Test
	public void testProcessDataPackages_stopsProcessingIfUserRejectsIssues() {
		addUnionWithIssuesToPackageMock();
		issuePresenter.desiredReturnValue = PresentationFeedback.Rejected;
		final List<DataPkg> selection = new LinkedList<DataPkg>();
		selection.add(pkgMock);

		directoryBrowser.directoryToReturn = "dummyDirectory";

		Coordinator.getInstance().processDataPackages(selection);

		assertTrue(issuePresenter.wasInvoked);
		assertFalse(dataModelBrowser.wasInvoked);
		assertFalse(messagePresenter.wasInvoked);
		assertFalse(directoryBrowser.wasInvoked);
		assertFalse(serializer.wasInvoked);

	}

	@Test
	public void testSetOptions() {
		coordinator.setOptions(options);
		final Option[] options2 = coordinator.getOptions();
		for (int i = 0; i < options.length; i++) {
			boolean found = false;
			for (int j = 0; j < options2.length; j++) {
				if (options[i].handle.equals(options2[j].handle)) {
					found = true;
				}
			}
			assertTrue(found);

		}
	}

	@Test
	public void testSetOptionValue() {
		coordinator.setOptions(options);
		coordinator.setOptionValue(option1Handle, "NewValue");
		assertEquals(coordinator.getOptionValue(option1Handle), "NewValue");
	}

}
