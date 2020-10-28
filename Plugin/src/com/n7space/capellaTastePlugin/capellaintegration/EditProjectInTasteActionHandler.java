// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.polarsys.capella.core.data.pa.PhysicalArchitecture;

import com.n7space.capellatasteplugin.capellaintegration.mmi.MessagePresenter;
import com.n7space.capellatasteplugin.capellaintegration.mmi.MessagePresenter.MessageKind;
import com.n7space.capellatasteplugin.eclipseintegration.PathVault;
import com.n7space.capellatasteplugin.eclipseintegration.mmi.GraphicalMessagePresenter;
import com.n7space.capellatasteplugin.tasteintegration.SystemCommandExecutor;
import com.n7space.capellatasteplugin.tasteintegration.TasteCommandExecutor;

/**
 *
 * Handler for "Edit project" action registered in plugin.xml.
 *
 * Captures execution events for which it is registered in plugin.xml and
 * invokes the "Edit project" action. Exposes the "Edit project" action to other
 * modules.
 *
 */
public class EditProjectInTasteActionHandler extends AbstractHandler {

	/**
	 * Gathers the required information from PathVault and invokes the "Edit
	 * project" action. Upon encountering an error, shows the corresponding message.
	 *
	 * @param architecture
	 *            Physical Architecture to be edited
	 *
	 * @see PathVault
	 * @see TasteCommandExecutor
	 */
	public static void editProject(final PhysicalArchitecture architecture) {
		final MessagePresenter presenter = new GraphicalMessagePresenter();
		if (architecture == null) {
			presenter.presentMessage(MessageKind.Information, "Please select the architecture",
					"Please select the architecture to edit in TASTE. Please note that the architecture must be already exported and DataView.aadl generated");
			return;
		}
		final PathVault vault = PathVault.getInstance();
		final String path = vault.getPath(architecture.getId(), PathVault.STORAGE_NODE_NAME);
		if (path == null) {
			presenter.presentMessage(MessageKind.Error, "Cannot execute command",
					"Export path of the current architecture is unknown");
			return;
		}

		final SystemCommandExecutor.CommandExecutionResult result = TasteCommandExecutor.getInstance()
				.editProject(path);

		if (result.isSuccessful) {
			// NOP
		} else {
			presenter.presentMessage(MessageKind.Error, "Cannot execute command",
					"Command cannot be executed\n" + result.output);
		}
	}

	/**
	 * Captures an execution event, gathers the required information from the
	 * selection and calls "editProject". Upon encountering an error, shows the
	 * corresponding message.
	 *
	 * @param event
	 *            The captured execution event
	 * @return null
	 *
	 * @see SelectionListener
	 *
	 */
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final MessagePresenter presenter = new GraphicalMessagePresenter();
		if (!TasteCommandExecutor.getInstance().doSupportPlatformForTaste()) {
			presenter.presentMessage(MessageKind.Information, "Platform not compatible",
					"TASTE is not supported on Microsoft Windows. If you want to continue anyway, please change the applicable option in the plugin preferences");
			return null;
		}
		final ISelection selection = SelectionListener.getListener().getCurrentSelection();
		final PhysicalArchitecture architecture = SelectionHelper.findPhysicalArchitectureInSelection(selection);
		editProject(architecture);
		return null;
	}
}
