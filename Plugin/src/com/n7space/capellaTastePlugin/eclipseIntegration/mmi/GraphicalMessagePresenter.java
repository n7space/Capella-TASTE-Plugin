// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.eclipseintegration.mmi;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import com.n7space.capellatasteplugin.capellaintegration.mmi.MessagePresenter;

/**
 * Implementation of a message presenter using GUI primitives supplied by the
 * Eclipse environment.
 */
public class GraphicalMessagePresenter implements MessagePresenter {

	/**
	 * Implements the MessagePresenter interface. Presents the given message under
	 * the given window title.
	 * 
	 * @see MessagePresenter
	 * @param kind
	 *            Kind of the message
	 * @param title
	 *            Window title
	 * @param message
	 *            Message to be presented
	 */
	@Override
	public void presentMessage(final MessageKind kind, final String title, final String message) {
		switch (kind) {
		case Error:
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), title, message);
			break;
		case Information:
			MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), title,
					message);
			break;
		default:
			break;
		}
	}
}
