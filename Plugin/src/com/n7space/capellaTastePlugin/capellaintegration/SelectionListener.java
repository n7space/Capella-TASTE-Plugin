// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;

/**
 * Selection listener singleton.
 */
public class SelectionListener implements ISelectionListener {

	private static SelectionListener listener = new SelectionListener();

	/**
	 * Returns the selection listener singleton instance.
	 * 
	 * @return Singleton instance
	 */
	public static SelectionListener getListener() {
		return listener;
	}

	private ISelection currentSelection = null;

	/**
	 * Returns the current selection.
	 * 
	 * @return The current selection
	 */
	public ISelection getCurrentSelection() {
		return currentSelection;
	}

	/**
	 * Listener method called when selection changes. Updates the current selection.
	 * 
	 * @param part
	 *            Workbench part
	 * @param selection
	 *            The new selection
	 */
	@Override
	public void selectionChanged(final IWorkbenchPart part, final ISelection selection) {
		currentSelection = selection;
	}

}
