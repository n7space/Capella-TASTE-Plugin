// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;

/**
 *
 * Handler for "Export to TASTE" action registered in plugin.xml.
 *
 * Captures execution events for which it is registered in plugin.xml and calls
 * the Launcher.
 *
 * @see Launcher
 * @see SelectionListener
 */

public class ExportToTasteActionHandler extends AbstractHandler {

	/**
	 * Captures an execution event and invokes the Launcher with the current
	 * selection.
	 *
	 * @param event
	 *            The captured execution event
	 * @return null
	 *
	 * @see Launcher
	 * @see SelectionListener
	 */
	@Override
	public Object execute(final ExecutionEvent event) throws ExecutionException {
		final ISelection selection = SelectionListener.getListener().getCurrentSelection();
		Launcher.launchPluginForSelection(selection);
		return null;
	}

}
