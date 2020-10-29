// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.utils;

/**
 * Class representing an issue.
 */
public class Issue {

	/**
	 * Kind of the issue.
	 */
	public static enum Kind {
		/**
		 * Error.
		 */
		Error,
		/**
		 * Warning.
		 */
		Warning,
		/**
		 * Information.
		 */
		Info
	}

	/**
	 * Issue description.
	 */
	public final String description;
	/**
	 * The object that is relevant to the issue.
	 */
	public final Object relevantObject;
	/**
	 * Kind of the issue.
	 */
	public final Kind kind;

	/**
	 * The constructor.
	 * 
	 * @param issueKind
	 *            kind of the issue
	 * @param issueDescription
	 *            Description of the issue
	 * @param objectRelevantToIssue
	 *            Object relevant to the issue
	 */
	public Issue(final Kind issueKind, final String issueDescription, final Object objectRelevantToIssue) {
		description = issueDescription;
		relevantObject = objectRelevantToIssue;
		kind = issueKind;
	}
}
