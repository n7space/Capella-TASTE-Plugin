// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.ui.navigator.CommonActionProvider;
import org.eclipse.ui.navigator.ICommonActionExtensionSite;
import org.eclipse.ui.navigator.ICommonMenuConstants;
import org.polarsys.capella.core.platform.sirius.ui.navigator.actions.SelectionHelper;

/**
 * Custom action provider for Capella project navigator, registered in
 * plugin.xml
 *
 * Initializes the custom actions and adds them to the relevant context menu.
 *
 * @see ExportToTasteAction
 */
public class ActionProvider extends CommonActionProvider {

	private ExportToTasteAction exportToTasteAction;
	private EditInTasteAction editInTasteAction;

	/**
	 * Unregisters and cleans-up the created resources.
	 */
	@Override
	public void dispose() {
		final ISelectionProvider selectionProvider = getActionSite().getViewSite().getSelectionProvider();
		if (exportToTasteAction != null) {
			selectionProvider.removeSelectionChangedListener(exportToTasteAction);
			exportToTasteAction = null;
		}
		if (editInTasteAction != null) {
			selectionProvider.removeSelectionChangedListener(editInTasteAction);
			editInTasteAction = null;
		}
		super.dispose();
	}

	/**
	 * Adds the custom actions to the relevant context menu and updates their
	 * enabled status.
	 *
	 * @param menuManager
	 *            Menu manager
	 * 
	 * @see ExportToTasteAction
	 * @see EditInTasteAction
	 */
	@Override
	public void fillContextMenu(final IMenuManager menuManager) {
		menuManager.appendToGroup(ICommonMenuConstants.GROUP_GENERATE, exportToTasteAction);
		if (exportToTasteAction != null) {
			exportToTasteAction.updateSatus();
		}
		menuManager.appendToGroup(ICommonMenuConstants.GROUP_GENERATE, editInTasteAction);
		if (editInTasteAction != null) {
			editInTasteAction.updateSatus();
		}
	}

	/**
	 * Initializes the custom actions and registers them with a selection provider.
	 *
	 * @param extensionSite
	 *            Extension site
	 */
	@Override
	public void init(final ICommonActionExtensionSite extensionSite) {
		super.init(extensionSite);
		final ISelectionProvider selectionProvider = extensionSite.getViewSite().getSelectionProvider();
		exportToTasteAction = new ExportToTasteAction();
		SelectionHelper.registerToSelectionChanges(exportToTasteAction, selectionProvider);
		editInTasteAction = new EditInTasteAction();
		SelectionHelper.registerToSelectionChanges(editInTasteAction, selectionProvider);
	}
}
