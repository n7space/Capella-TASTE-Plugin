// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.utils;

/**
 * Utility class for stack trace related operations.
 */
public class StackTraceUtilities {

	protected static String getTrimmedClassName(final String name) {
		try {
			return name.substring(name.lastIndexOf('.') + 1);
		} catch (final Throwable t) {
			// We don't want a crash while reporting an almost-a-crash.
			return name;
		}
	}

	/**
	 * Returns a shortened tip of the given throwable's stack trace.
	 *
	 * @param t
	 *            Throwable
	 * @param limit
	 *            The size of the tip
	 * @return A shortened string representation of the stack trace
	 */
	public static String getTrimmedStackTraceString(final Throwable t, final int limit) {
		final StringBuilder sb = new StringBuilder();
		int counter = 0;
		for (final StackTraceElement element : t.getStackTrace()) {
			counter++;
			sb.append(getTrimmedClassName(element.getClassName()));
			sb.append(".");
			sb.append(element.getMethodName());
			sb.append(":");
			sb.append(element.getLineNumber());
			sb.append("<-");
			if (counter >= limit) {
				break;
			}
		}
		final String result = sb.toString();
		return result.substring(0, result.length() - 2);
	}

}
