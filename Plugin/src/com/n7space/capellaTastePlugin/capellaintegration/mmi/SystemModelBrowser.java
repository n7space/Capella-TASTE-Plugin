// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.mmi;

import java.util.List;

import com.n7space.capellatasteplugin.modelling.architecture.ArchitectureElement;
import com.n7space.capellatasteplugin.modelling.architecture.SystemModel;

/**
 * Interface declaration for a system model browser.
 */
public interface SystemModelBrowser {

	/**
	 * Interface declaration for system model presentation callback.
	 *
	 */
	public interface SystemModelPresentationCallback {
		/**
		 * Presentation callback declaration.
		 *
		 * @param model
		 *            The relevant system model
		 * @param selection
		 *            The user selection of model elements
		 * @param feedback
		 *            User feedback
		 */
		void onSystemModelPresentationCallback(final SystemModel model, final List<ArchitectureElement> selection,
				final PresentationFeedback feedback);
	}

	/**
	 * Method which should present the system model to the user and provide the
	 * means for a system model subset selection.
	 *
	 * @param model
	 *            The system model to be presented
	 * @param callback
	 *            The callback to be invoked with the user feedback
	 */
	void captureSystemModelSubsetSelection(final SystemModel model, final SystemModelPresentationCallback callback);

}