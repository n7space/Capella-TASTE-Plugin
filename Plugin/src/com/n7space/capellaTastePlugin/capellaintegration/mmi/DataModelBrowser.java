// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.mmi;

import java.util.List;

import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataModelElement;

/**
 * Interface declaration for a data model browser.
 */
public interface DataModelBrowser {

	/**
	 * Interface declaration for data model presentation callback.
	 *
	 */
	public interface DataModelPresentationCallback {
		/**
		 * Presentation callback declaration.
		 *
		 * @param dataModel
		 *            The relevant data model
		 * @param selection
		 *            The user selection of data model elements
		 * @param feedback
		 *            User feedback
		 */
		void onDataModelPresentationCallback(final DataModel dataModel, final List<DataModelElement> selection,
				final PresentationFeedback feedback);
	}

	/**
	 * Method which should present the data model to the user and provide the means
	 * for a data model subset selection.
	 *
	 * @param dataModel
	 *            The data model to be presented
	 * @param callback
	 *            The callback to be invoked with the user feedback
	 */
	void captureDataModelSubsetSelection(final DataModel dataModel, final DataModelPresentationCallback callback);

}