// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.tasteintegration;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import com.n7space.capellatasteplugin.eclipseintegration.SettingsProvider;
import com.n7space.capellatasteplugin.utils.BasicConfigurableItem;

/**
 * Class facilitating the execution of TASTE commands.
 *
 */
public class TasteCommandExecutor extends BasicConfigurableItem {

	/**
	 * Enumeration listing TASTE executor options.
	 *
	 */
	public static enum TasteCommandExecutorOption {

		/**
		 * Command for updating DataView.
		 */
		TasteUpdateDataViewCommand("TasteUpdateDataViewCommand"),
		/**
		 * Command for building the system.
		 */
		TasteBuildCommand("TasteBuildCommand"),
		/**
		 * Command for cleaning the directory.
		 */
		TasteCleanCommand("TasteMakeCommand"),
		/**
		 * Command for editing a function.
		 */
		TasteEditFunctionCommand("TasteEditFunctionCommand"),
		/**
		 * Command for editing a project.
		 */
		TasteEditProjectCommand("TasteEditProjectCommand"),
		/**
		 * Command for generation of code skeletons.
		 */
		TasteGenerateSkeletonsCommand("TasteGenerateSkeletonsCommand"),
		/**
		 * Whether or not to show TASTE incompatibility warning message on windows.
		 */
		ShowWarningOnWindows("ShowWarningOnWindows"),
		/**
		 * Whether or not to automatically generate TASTE artefacts after achitecture
		 * export.
		 */
		GenerateTasteArtefactsAfterExport("GenerateTasteArtefactsAfterExport"),
		/**
		 * Whether or not to automatically clean the build artefacts before building.
		 */
		CleanBeforeBuild("CleanBeforeBuild"),
		/**
		 * Initialize Taste project before export.
		 */
		InitializeTasteProjectBeforeExport("InitializeTasteProjectBeforeExport"),
		/**
		 * Command for initializing TASTE project.
		 */
		TasteInitProjectCommand("TasteInitProjectCommand");

		/**
		 * Prefix for option handles.
		 */
		public final static String OPTION_PREFIX = "com.n7space.capellatasteplugin.tasteintegration.TasteCommandExecutor.";

		private final String value;

		private TasteCommandExecutorOption(final String baseName) {
			value = baseName;
		}

		/**
		 * Returns string representation of the option handle.
		 *
		 * @return String representation of the option handle.
		 */
		@Override
		public String toString() {
			return OPTION_PREFIX + value;
		}
	}

	protected static final TasteCommandExecutor instance = new TasteCommandExecutor();
	protected SystemCommandExecutor executor = new SystemCommandExecutor();

	/**
	 * Gets TASTE command executor instance.
	 * 
	 * @return Command executor instance
	 */
	public static TasteCommandExecutor getInstance() {
		return instance;
	}

	public void setCommandExecutor(final SystemCommandExecutor commandExecutor) {
		executor = commandExecutor;
	}

	protected final Option[] OPTIONS = {
			new Option(TasteCommandExecutorOption.TasteUpdateDataViewCommand, "TASTE update DataView command",
					"taste-update-data-view *.asn"),
			new Option(TasteCommandExecutorOption.TasteInitProjectCommand, "TASTE init project command", "taste init"),
			new Option(TasteCommandExecutorOption.TasteBuildCommand, "TASTE build command", "make"),
			new Option(TasteCommandExecutorOption.TasteCleanCommand, "TASTE clean command", "make clean"),
			new Option(TasteCommandExecutorOption.TasteEditFunctionCommand, "TASTE edit function command", "make edit"),
			new Option(TasteCommandExecutorOption.TasteEditProjectCommand, "TASTE edit project command", "taste"),
			new Option(TasteCommandExecutorOption.TasteGenerateSkeletonsCommand, "TASTE generate skeletons command",
					"make skeletons"),
			new Option(TasteCommandExecutorOption.ShowWarningOnWindows, "Show TASTE incompatibility warning on Windows",
					Boolean.TRUE),
			new Option(TasteCommandExecutorOption.GenerateTasteArtefactsAfterExport,
					"Generate TASTE artefacts after export", Boolean.TRUE),
			new Option(TasteCommandExecutorOption.CleanBeforeBuild, "Clean before building", Boolean.FALSE),
			new Option(TasteCommandExecutorOption.InitializeTasteProjectBeforeExport,
					"Initialize TASTE project before AADL export", Boolean.TRUE) };

	private TasteCommandExecutor() {
		setOptions(OPTIONS);
	}

	/**
	 * Invoke the automatic generation of TASTE artefacts, if applicable.
	 * 
	 * @param path
	 *            Architecture export path
	 * @return Whether the invocation was successful
	 */
	public boolean autogenerateArtefactsAfterExportIfApplicable(final String path) {
		restoreOptions();
		if (!getBooleanOptionValue(TasteCommandExecutorOption.GenerateTasteArtefactsAfterExport, true))
			return true;
		if (!doSupportPlatformForTaste())
			return true;
		final SystemCommandExecutor.CommandExecutionResult aadlResult = generateDataViewAaadl(path);
		if (!aadlResult.isSuccessful)
			return false;
		final SystemCommandExecutor.CommandExecutionResult skeletonsResult = generateSkeletons(path);
		return skeletonsResult.isSuccessful;
	}

	/**
	 * Invoke TASTE project initialization, if applicable.
	 * 
	 * @param path
	 *            Architecture export path
	 * @return Whether the invocation was successful
	 */
	public boolean intializeTasteProjectIfApplicable(final String path) {
		restoreOptions();
		if (!getBooleanOptionValue(TasteCommandExecutorOption.InitializeTasteProjectBeforeExport, true))
			return true;
		if (!doSupportPlatformForTaste())
			return true;
		final SystemCommandExecutor.CommandExecutionResult initResult = executor.executeProcess(path,
				getStringOptionValue(TasteCommandExecutorOption.TasteInitProjectCommand), null, true);
		return initResult.isSuccessful;
	}

	protected void cleanDirectory(final String path, final String subdirectory) {
		final List<String> arguments = new LinkedList<String>();
		arguments.add("-r"); // recursive
		arguments.add(subdirectory);
		executor.executeProcess(path, "rm", arguments, true);
	}

	/**
	 * Invoke the build system command for the given path.
	 * 
	 * @param path
	 *            Architecture export path
	 * @return Execution result
	 */
	public SystemCommandExecutor.CommandExecutionResult buildSystem(final String path) {
		restoreOptions();
		if (getBooleanOptionValue(TasteCommandExecutorOption.CleanBeforeBuild, true)) {
			executor.executeProcess(path, getStringOptionValue(TasteCommandExecutorOption.TasteCleanCommand), null,
					true);
			cleanDirectory(path, "work" + File.separatorChar + "binaries");
		}
		final SystemCommandExecutor.CommandExecutionResult result = executor.executeProcess(path,
				getStringOptionValue(TasteCommandExecutorOption.TasteBuildCommand), null, true);
		return result;
	}

	/**
	 * Check whether the current platform supports TASTE.
	 * 
	 * @return Whether the current platform supports TASTE
	 */
	public boolean doSupportPlatformForTaste() {
		restoreOptions();
		return !isRunningOnWindows() || (!getBooleanOptionValue(TasteCommandExecutorOption.ShowWarningOnWindows, true));
	}

	/**
	 * Invoke function editor for the given function.
	 * 
	 * @param path
	 *            Architecture export path
	 * @param functionName
	 *            Function name
	 * @param functionType
	 *            Function type
	 * @return Execution result
	 */
	public SystemCommandExecutor.CommandExecutionResult editFunction(final String path, final String functionName,
			final String functionTypeName) {
		restoreOptions();
		final String functionPath = path + File.separatorChar + "work" + File.separatorChar + functionName.toLowerCase()
				+ File.separatorChar + functionTypeName;
		final SystemCommandExecutor.CommandExecutionResult result = executor.executeProcess(functionPath,
				getStringOptionValue(TasteCommandExecutorOption.TasteEditFunctionCommand), null, false);
		return result;
	}

	/**
	 * Invoke the edit project command for the given path.
	 * 
	 * @param path
	 *            Architecture export path
	 * @return Execution result
	 */
	public SystemCommandExecutor.CommandExecutionResult editProject(final String path) {
		restoreOptions();
		final SystemCommandExecutor.CommandExecutionResult result = executor.executeProcess(path,
				getStringOptionValue(TasteCommandExecutorOption.TasteEditProjectCommand), null, false);
		return result;
	}

	/**
	 * Invoke the update DataView command for the given path.
	 * 
	 * @param path
	 *            Architecture export path
	 * @return Execution result
	 */
	public SystemCommandExecutor.CommandExecutionResult generateDataViewAaadl(final String path) {
		restoreOptions();
		final SystemCommandExecutor.CommandExecutionResult result = executor.executeProcess(path,
				getStringOptionValue(TasteCommandExecutorOption.TasteUpdateDataViewCommand), null, true);
		return result;
	}

	/**
	 * Invoke the generate code skeletons command for the given path.
	 * 
	 * @param path
	 *            Architecture export path
	 * @return Execution result
	 */
	public SystemCommandExecutor.CommandExecutionResult generateSkeletons(final String path) {
		restoreOptions();
		final SystemCommandExecutor.CommandExecutionResult result = executor.executeProcess(path,
				getStringOptionValue(TasteCommandExecutorOption.TasteGenerateSkeletonsCommand), null, true);
		return result;
	}

	protected boolean isRunningOnWindows() {
		final String name = System.getProperty("os.name").toLowerCase();
		return name.contains("windows");
	}

	protected void restoreOptions() {
		SettingsProvider.getInstance().restoreOptionValues(getOptions(), SettingsProvider.PREFERENCES_NODE_NAME);
	}

}
