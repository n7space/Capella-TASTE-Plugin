// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.mmi;

/**
 * Interface declaration for a message presenter.
 *
 */
public interface MessagePresenter {

	/**
	 * Enumeration listing possible types of messages.
	 */
	public static enum MessageKind {
		/**
		 * Informative message.
		 */
		Information,
		/**
		 * Message indicating an error.
		 */
		Error
	}

	/**
	 * Method which should present the given message to the user.
	 * 
	 * @param kind
	 *            The type of the message
	 * @param title
	 *            The title of the message (e.g. to be used in a dialog's title bar)
	 * @param message
	 *            The message to be presented
	 */
	void presentMessage(final MessageKind kind, final String title, final String message);

}