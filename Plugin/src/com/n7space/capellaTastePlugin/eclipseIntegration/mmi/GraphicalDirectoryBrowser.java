// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.eclipseintegration.mmi;

import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.ui.PlatformUI;

import com.n7space.capellatasteplugin.capellaintegration.mmi.DirectoryBrowser;
import com.n7space.capellatasteplugin.eclipseintegration.SettingsProvider;
import com.n7space.capellatasteplugin.utils.ConfigurableObject.Option;
import com.n7space.capellatasteplugin.utils.OptionsHelper;

/**
 * Implementation of a graphical directory browser using GUI primitives supplied
 * by the Eclipse environment.
 *
 */
public class GraphicalDirectoryBrowser implements DirectoryBrowser {

	/**
	 * Enumeration defining browser's option's handles.
	 */
	public static enum GraphicalDirectoryBrowserOption {
		/**
		 * Starting output directory (equal to the last selected one).
		 */
		OutputDirectory("OutputDirectory");

		/**
		 * Handle prefix.
		 */
		public final static String PREFIX = "com.n7space.capellatasteplugin.eclipseintegration.mmi.windows.GraphicalDirectoryBrowserOption.";

		private final String value;

		private GraphicalDirectoryBrowserOption(final String baseName) {
			value = baseName;
		}

		/**
		 * Returns string representation of the given handle.
		 * 
		 * @return String representation of the handle.
		 */
		@Override
		public String toString() {
			return PREFIX + value;
		}
	}

	protected final Option[] options = { new Option(GraphicalDirectoryBrowserOption.OutputDirectory,
			"Output directory for model serialization", "") };

	/**
	 * Implements the DirectoryBrowser interface. Presents the given message to the
	 * user and provides a graphical interface for directory selection.
	 * 
	 * @see DirectoryBrowser
	 * @param message
	 *            Message to be shown
	 * @return The selected directory (may be null in case of an error or
	 *         cancellation)
	 */
	@Override
	public String getOutputDirectory(final String message) {
		SettingsProvider.getInstance().restoreOptionValues(options, SettingsProvider.PREFERENCES_NODE_NAME);
		final Object lastSelectedDirectory = OptionsHelper.getOptionValue(options,
				GraphicalDirectoryBrowserOption.OutputDirectory);
		final DirectoryDialog dialog = new DirectoryDialog(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell());
		if (lastSelectedDirectory != null)
			dialog.setFilterPath((String) lastSelectedDirectory);
		dialog.setMessage(message);
		final String currentSelection = dialog.open();
		if (currentSelection != null) {
			OptionsHelper.setOptionValue(options, GraphicalDirectoryBrowserOption.OutputDirectory, currentSelection);
			SettingsProvider.getInstance().storeOptionValues(options, SettingsProvider.PREFERENCES_NODE_NAME);
		}
		return currentSelection;

	}

}
