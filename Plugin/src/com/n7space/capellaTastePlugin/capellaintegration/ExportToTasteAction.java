// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.actions.BaseSelectionListenerAction;

/**
 * Custom action for Capella project navigator. Invokes the Launcher with the
 * current selection.
 * 
 * @see ActionProvider
 * @see Launcher
 *
 */
public class ExportToTasteAction extends BaseSelectionListenerAction {

	/**
	 * The default constructor.
	 */
	public ExportToTasteAction() {
		super("Export to TASTE");
	}

	protected ExportToTasteAction(final String text) {
		super(text);
	}

	/**
	 * Executes the action by invoking the launcher with the current selection.
	 * 
	 * @see Launcher
	 */
	@Override
	public void run() {
		final IStructuredSelection selection = this.getStructuredSelection();
		Launcher.launchPluginForSelection(selection);
	}

	/**
	 * Updates the current status within the project navigator. The status is set to
	 * enabled only if the selection contains a convertible element.
	 */
	public void updateSatus() {
		setEnabled(Launcher.isSelectionLaunchable(getStructuredSelection()));
	}

}
