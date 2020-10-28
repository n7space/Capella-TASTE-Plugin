// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration;

import java.util.List;

import org.eclipse.jface.viewers.ISelection;
import org.polarsys.capella.core.data.information.DataPkg;
import org.polarsys.capella.core.data.pa.PhysicalArchitecture;

import com.n7space.capellatasteplugin.capellaintegration.mmi.MessagePresenter;
import com.n7space.capellatasteplugin.capellaintegration.mmi.MessagePresenter.MessageKind;
import com.n7space.capellatasteplugin.eclipseintegration.mmi.GraphicalMessagePresenter;

/**
 * Helper class that shares the plugin action related code between navigator and
 * menu actions.
 * 
 * @see ExportToTasteAction
 * @see ExportToTasteActionHandler
 */
public class Launcher {
	protected static final MessagePresenter messagePresenter = new GraphicalMessagePresenter();

	/**
	 * Returns whether the given selection contains elements for which the plugin
	 * actions can be invoked.
	 * 
	 * @param selection
	 *            The selection to be analyzed
	 * @return Whether the plugin actions can be invoked for the given selection
	 */
	public static boolean isSelectionLaunchable(final ISelection selection) {
		try {
			if (selection != null) {
				final List<DataPkg> pkgs = SelectionHelper.findDataPackagesInSelection(selection);
				if (pkgs != null && pkgs.size() > 0) {
					return true;
				}
				final PhysicalArchitecture architecture = SelectionHelper
						.findPhysicalArchitectureInSelection(selection);
				if (architecture != null) {
					return true;
				}
			}
			return false;
		} catch (final Throwable t) {
			// Fail silently and return selection as not launchable.
			return false;
		}
	}

	/**
	 * Extracts the relevant elements from the given selection and, depending on the
	 * type of the found elements, invokes the applicable processing flow through
	 * the Coordinator.
	 * 
	 * @param selection
	 *            The selection to be processed
	 * @return Whether relevant elements were found, the processing was invoked and
	 *         finished successfully
	 * @see Coordinator
	 */
	public static boolean launchPluginForSelection(final ISelection selection) {
		try {
			if (selection != null) {
				final List<DataPkg> pkgs = SelectionHelper.findDataPackagesInSelection(selection);
				if (pkgs != null && pkgs.size() > 0) {
					Coordinator.getInstance().processDataPackages(pkgs);
					return true;
				}
				final PhysicalArchitecture architecture = SelectionHelper
						.findPhysicalArchitectureInSelection(selection);
				if (architecture != null) {
					Coordinator.getInstance().processArchitecture(architecture);
					return true;
				}
			}
			messagePresenter.presentMessage(MessageKind.Information, "No items to export",
					"No exportable items were found. Please select either Data Packages for ASN.1 export, or Physical Architecture for AADL export.");
		} catch (final Throwable t) {
			final StringBuilder sb = new StringBuilder();
			for (final StackTraceElement element : t.getStackTrace()) {
				sb.append(element.toString() + "\n");
			}
			messagePresenter.presentMessage(MessageKind.Error, "Critical error",
					"Export failed due to the following error: " + t.toString() + "\n" + sb.toString());
		}
		return false;
	}
}
