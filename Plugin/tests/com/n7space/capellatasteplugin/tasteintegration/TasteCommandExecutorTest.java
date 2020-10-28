// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.tasteintegration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.n7space.capellatasteplugin.eclipseintegration.SettingsProvider;
import com.n7space.capellatasteplugin.tasteintegration.SystemCommandExecutor.CommandExecutionResult;
import com.n7space.capellatasteplugin.tasteintegration.SystemCommandExecutor.ProcessExecutorFactoryInterface;
import com.n7space.capellatasteplugin.tasteintegration.SystemCommandExecutor.ProcessExecutorInterface;
import com.n7space.capellatasteplugin.utils.ConfigurableObject.Option;

public class TasteCommandExecutorTest {

	protected class MockExecutor implements ProcessExecutorInterface {
		public final List<String> commands = new LinkedList<String>();
		public final List<String> paths = new LinkedList<String>();

		protected String currentPath = null;
		protected String currentCommand = null;

		@Override
		public InputStream getInputStream() {
			return new ByteArrayInputStream(new byte[0]);
		}

		@Override
		public int getResult() throws InterruptedException {
			return 0;
		}

		@Override
		public void setCommand(List<String> command) {
			currentCommand = "";
			for (String part : command)
				currentCommand += part + ";";

		}

		@Override
		public void setDirectory(String path) {
			currentPath = path;
		}

		@Override
		public void startProcess() throws IOException {
			commands.add(currentCommand);
			paths.add(currentPath);
			currentCommand = null;
			currentPath = null;
		}

	}

	protected class MockExecutorFactory implements ProcessExecutorFactoryInterface {
		final ProcessExecutorInterface executor;

		public MockExecutorFactory(final ProcessExecutorInterface mockedExecutor) {
			executor = mockedExecutor;
		}

		@Override
		public ProcessExecutorInterface createExecutor() {
			return executor;
		}

	}

	Option[] oldOptions = null;

	@Before
	public void setUp() throws Exception {
		final TasteCommandExecutor instance = TasteCommandExecutor.getInstance();
		oldOptions = instance.getOptions().clone();
		SettingsProvider.getInstance().restoreOptionValues(oldOptions, SettingsProvider.PREFERENCES_NODE_NAME);
		instance.setOptionValue(TasteCommandExecutor.TasteCommandExecutorOption.GenerateTasteArtefactsAfterExport,
				Boolean.TRUE);
		instance.setOptionValue(TasteCommandExecutor.TasteCommandExecutorOption.ShowWarningOnWindows, Boolean.FALSE);
		instance.setOptionValue(TasteCommandExecutor.TasteCommandExecutorOption.CleanBeforeBuild, Boolean.TRUE);
		SettingsProvider.getInstance().storeOptionValues(instance.getOptions(), SettingsProvider.PREFERENCES_NODE_NAME);

	}

	@After
	public void tearDown() throws Exception {
		final TasteCommandExecutor instance = TasteCommandExecutor.getInstance();
		instance.setOptions(oldOptions);
		SettingsProvider.getInstance().storeOptionValues(oldOptions, SettingsProvider.PREFERENCES_NODE_NAME);
	}

	@Test
	public void testAutogenerateArtefactsAfterExportIfApplicable() {
		final TasteCommandExecutor instance = TasteCommandExecutor.getInstance();
		final SystemCommandExecutor commandExecutor = new SystemCommandExecutor();
		final MockExecutor mockExecutor = new MockExecutor();
		commandExecutor.setProcessExecutorFactory(new MockExecutorFactory(mockExecutor));
		instance.setCommandExecutor(commandExecutor);

		instance.autogenerateArtefactsAfterExportIfApplicable("Path");

		assertEquals(mockExecutor.commands.size(), 2);
		assertEquals(mockExecutor.commands.get(0), "taste-update-data-view;*.asn;");
		assertEquals(mockExecutor.paths.get(0), "Path");
		assertEquals(mockExecutor.commands.get(1), "make;skeletons;");
		assertEquals(mockExecutor.paths.get(1), "Path");
	}

	@Test
	public void testBuildSystem() {
		final TasteCommandExecutor instance = TasteCommandExecutor.getInstance();
		final SystemCommandExecutor commandExecutor = new SystemCommandExecutor();
		final MockExecutor mockExecutor = new MockExecutor();
		commandExecutor.setProcessExecutorFactory(new MockExecutorFactory(mockExecutor));
		instance.setCommandExecutor(commandExecutor);

		instance.buildSystem("Path");

		assertEquals(mockExecutor.commands.size(), 3);
		assertEquals(mockExecutor.commands.get(0), "make;clean;");
		assertEquals(mockExecutor.commands.get(1), "rm;-r;work" + File.separatorChar + "binaries;");
		assertEquals(mockExecutor.commands.get(2), "make;");
		assertEquals(mockExecutor.paths.get(2), "Path");
	}

	@Test
	public void testEditFunction() {
		final TasteCommandExecutor instance = TasteCommandExecutor.getInstance();
		final SystemCommandExecutor commandExecutor = new SystemCommandExecutor();
		final MockExecutor mockExecutor = new MockExecutor();
		commandExecutor.setProcessExecutorFactory(new MockExecutorFactory(mockExecutor));
		instance.setCommandExecutor(commandExecutor);

		final String name = "dummy_function";
		final String path = "ProjectDirectory";

		CommandExecutionResult result = instance.editFunction(path, name, "C");
		assertTrue(result.isSuccessful);
		assertEquals(mockExecutor.commands.size(), 1);
		assertEquals(mockExecutor.commands.get(0), "make;edit;");
		assertEquals(mockExecutor.paths.get(0), "ProjectDirectory" + File.separatorChar + "work" + File.separatorChar
				+ name + File.separatorChar + "C");
	}

	@Test
	public void testEditProject() {
		final TasteCommandExecutor instance = TasteCommandExecutor.getInstance();
		final SystemCommandExecutor commandExecutor = new SystemCommandExecutor();
		final MockExecutor mockExecutor = new MockExecutor();
		commandExecutor.setProcessExecutorFactory(new MockExecutorFactory(mockExecutor));
		instance.setCommandExecutor(commandExecutor);

		instance.editProject("Path");

		assertEquals(mockExecutor.commands.size(), 1);
		assertEquals(mockExecutor.commands.get(0), "taste;");
		assertEquals(mockExecutor.paths.get(0), "Path");
	}

	@Test
	public void testGenerateDataViewAaadl() {
		final TasteCommandExecutor instance = TasteCommandExecutor.getInstance();
		final SystemCommandExecutor commandExecutor = new SystemCommandExecutor();
		final MockExecutor mockExecutor = new MockExecutor();
		commandExecutor.setProcessExecutorFactory(new MockExecutorFactory(mockExecutor));
		instance.setCommandExecutor(commandExecutor);

		instance.generateDataViewAaadl("Path");

		assertEquals(mockExecutor.commands.size(), 1);
		assertEquals(mockExecutor.commands.get(0), "taste-update-data-view;*.asn;");
		assertEquals(mockExecutor.paths.get(0), "Path");
	}

	@Test
	public void testGenerateSkeletons() {
		final TasteCommandExecutor instance = TasteCommandExecutor.getInstance();
		final SystemCommandExecutor commandExecutor = new SystemCommandExecutor();
		final MockExecutor mockExecutor = new MockExecutor();
		commandExecutor.setProcessExecutorFactory(new MockExecutorFactory(mockExecutor));
		instance.setCommandExecutor(commandExecutor);

		instance.generateSkeletons("Path");

		assertEquals(mockExecutor.commands.size(), 1);
		assertEquals(mockExecutor.commands.get(0), "make;skeletons;");
		assertEquals(mockExecutor.paths.get(0), "Path");
	}

}
