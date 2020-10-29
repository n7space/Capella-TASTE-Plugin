// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.mmi;

import java.util.List;

import com.n7space.capellatasteplugin.utils.Issue;

/**
 * Interface declaration for an issue browser.
 */
public interface IssuePresenter {

	public interface IssuePresentationCallback {
		/**
		 * Presentation callback declaration.
		 * 
		 * @param issues
		 *            The relevant issues
		 * @param feedback
		 *            User feedback
		 */
		void onIssuePresentationFeedback(final List<Issue> issues, final PresentationFeedback feedback);
	}

	/**
	 * Method which should present the issues to the user and provide the means to
	 * either accept or reject them.
	 * 
	 * @param issues
	 *            The issues to be presented
	 * @param callback
	 *            The callback to be invoked with the user feedback
	 */
	void PresentIssues(final List<Issue> issues, final IssuePresentationCallback callback);

}
