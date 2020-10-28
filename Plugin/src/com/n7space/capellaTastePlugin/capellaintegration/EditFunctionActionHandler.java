// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.polarsys.capella.core.data.pa.PhysicalArchitecture;
import org.polarsys.capella.core.data.pa.PhysicalFunction;

import com.n7space.capellatasteplugin.capellaintegration.mmi.MessagePresenter;
import com.n7space.capellatasteplugin.capellaintegration.mmi.MessagePresenter.MessageKind;
import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.CustomCapellaProperties;
import com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.PropertyUtils;
import com.n7space.capellatasteplugin.eclipseintegration.PathVault;
import com.n7space.capellatasteplugin.eclipseintegration.mmi.GraphicalMessagePresenter;
import com.n7space.capellatasteplugin.modelling.NameConverter;
import com.n7space.capellatasteplugin.tasteintegration.SystemCommandExecutor;
import com.n7space.capellatasteplugin.tasteintegration.TasteCommandExecutor;

/**
 *
 * Handler for "Edit function" action registered in plugin.xml.
 *
 * Captures execution events for which it is registered in plugin.xml and
 * invokes the "Edit function" action. Exposes the "Edit function" action to
 * other modules.
 *
 */
public class EditFunctionActionHandler extends AbstractHandler {

	/**
	 * Gathers the required information from the model and PathVault and invokes the
	 * "Edit function" action. Upon encountering an error, shows the corresponding
	 * message.
	 *
	 * @param function
	 *            Physical Function to be edited
	 *
	 * @see PathVault
	 * @see TasteCommandExecutor
	 */
	public static void editFunction(final PhysicalFunction function) {
		final MessagePresenter presenter = new GraphicalMessagePresenter();

		if (function == null) {
			presenter.presentMessage(MessageKind.Information, "Please select the physical function",
					"Please select the function to edit. Please note that the parent architecture must be already exported and DataView.aadl along with skeletons generated");
			return;
		}

		final PhysicalArchitecture architecture = getArchitectureForFunction(function);
		if (architecture == null) {
			presenter.presentMessage(MessageKind.Information, "Architecture not found",
					"Architecture for the selected function could not be found");
			return;
		}
		final PathVault vault = PathVault.getInstance();
		final String path = vault.getPath(architecture.getId(), PathVault.STORAGE_NODE_NAME);
		if (path == null) {
			presenter.presentMessage(MessageKind.Error, "Cannot execute command",
					"Export path of the current architecture is unknown");
			return;
		}

		final SystemCommandExecutor.CommandExecutionResult result = TasteCommandExecutor.getInstance().editFunction(
				path, (new NameConverter()).getAadlTypeName(function.getName()),
				PropertyUtils.getProperty(function, CustomCapellaProperties.LANGUAGE));

		if (result.isSuccessful) {
			// NOP
		} else {
			presenter.presentMessage(MessageKind.Error, "Cannot execute command",
					"Command cannot be executed\n" + result.output);
		}
	}

	protected static PhysicalArchitecture getArchitectureForFunction(final PhysicalFunction function) {
		EObject element = function;
		while (element != null) {
			if (element instanceof PhysicalArchitecture)
				return (PhysicalArchitecture) element;
			final EObject container = element.eContainer();
			element = container;
		}
		return null;
	}

	/**
	 * Captures an execution event, gathers the required information from the
	 * selection and calls "editFunction".
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
		final ISelection selection = SelectionListener.getListener().getCurrentSelection();
		final PhysicalFunction function = SelectionHelper.findPhysicalFunctionInSelection(selection);
		editFunction(function);
		return null;
	}

}
