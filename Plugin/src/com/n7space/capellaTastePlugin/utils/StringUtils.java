// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.utils;

/**
 * String utilities.
 *
 */
public class StringUtils {
	/**
	 * Cleans-up an integer value from leading 0s, spaces, etc.
	 * 
	 * @param value
	 *            Integer value to be cleaned
	 * @return Cleaned string representation of the input integer value
	 */
	public static String cleanUpIntegerValue(final Object value) {
		try {
			return Long.valueOf(value.toString()).toString();
		} catch (final Exception e) {
			return value.toString();
		}
	}

	/**
	 * Escapes quotes in text and puts the text in embracing quotes.
	 * 
	 * @param text
	 *            Text to be escaped
	 * @return Escaped text
	 */
	public static String escape(final String text) {
		return "\"" + escapeQuotes(text) + "\"";
	}

	/**
	 * Escapes quotes in text.
	 * 
	 * @param text
	 *            Text to be escaped
	 * @return Escaped text
	 */
	public static String escapeQuotes(final String text) {
		return text.replaceAll("\"", "\"\"");
	}

	/**
	 * Returns whether the given string is empty.
	 * 
	 * @param text
	 *            The string in question
	 * @return Whether the given string is empty
	 */
	public static boolean isNotEmpty(final String text) {
		return text != null && text.length() > 0;
	}
}
