// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.actions.BaseSelectionListenerAction;
import org.polarsys.capella.core.data.pa.PhysicalArchitecture;
import org.polarsys.capella.core.data.pa.PhysicalFunction;

/**
 * Custom action for Capella project navigator. Invokes the "edit" action
 * applicable to the current selection.
 *
 * @see ActionProvider
 * @see Launcher
 *
 */
public class EditInTasteAction extends BaseSelectionListenerAction {

	/**
	 * The default constructor.
	 */
	public EditInTasteAction() {
		super("Edit in TASTE");
	}

	protected EditInTasteAction(final String text) {
		super(text);
	}

	/**
	 * Executes the action by invoking the "edit" action applicable to the current
	 * selection.
	 *
	 * @see Launcher
	 */
	@Override
	public void run() {

		final IStructuredSelection selection = this.getStructuredSelection();
		final PhysicalArchitecture architecture = SelectionHelper.findPhysicalArchitectureInSelection(selection);
		if (architecture != null) {
			EditProjectInTasteActionHandler.editProject(architecture);
		}
		final PhysicalFunction function = SelectionHelper.findPhysicalFunctionInSelection(selection);
		if (function != null) {
			EditFunctionActionHandler.editFunction(function);
		}
	}

	/**
	 * Updates the current status within the project navigator. The status is set to
	 * enabled only if the selection contains a physical function or a physical
	 * architecture.
	 */
	public void updateSatus() {
		final IStructuredSelection selection = this.getStructuredSelection();
		setEnabled(SelectionHelper.findPhysicalArchitectureInSelection(selection) != null
				|| SelectionHelper.findPhysicalFunctionInSelection(selection) != null);
	}
}
