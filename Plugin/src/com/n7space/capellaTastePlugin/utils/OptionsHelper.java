// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.utils;

import java.util.HashMap;
import java.util.Map;

import com.n7space.capellatasteplugin.utils.ConfigurableObject.Option;

/**
 * Utility class for manipulating options.
 */
public class OptionsHelper {
	/**
	 * Adds two arrays of options.
	 * 
	 * @param a
	 *            First array of options
	 * @param b
	 *            Second array of options
	 * @return Sum of options
	 */
	public static Option[] addOptions(final Option[] a, final Option[] b) {
		final Map<String, Option> options = new HashMap<String, Option>();
		for (final Option option : a) {
			options.put(option.handle.toString(), option);
		}
		for (final Option option : b) {
			options.put(option.handle.toString(), option);
		}
		final Option[] resultArray = new Option[options.values().size()];
		int index = 0;
		for (final Option option : options.values()) {
			resultArray[index++] = option;
		}
		return resultArray;
	}

	/**
	 * Converts an option iterator to an array of options.
	 * 
	 * @param options
	 *            Option iterator
	 * @return Array of options
	 */
	public static Option[] convert(final Iterable<Option> options) {
		int count = 0;
		for (@SuppressWarnings("unused")
		final Option option : options) {
			count++;
		}
		final Option[] result = new Option[count];
		int index = 0;
		for (final Option option : options) {
			result[index++] = option;
		}
		return result;
	}

	/**
	 * Returns value of an option identified by the given handle.
	 * 
	 * @param options
	 *            Array of options
	 * @param optionHandle
	 *            Handle identifying the requested option
	 * @return Value of the requested option or null if none is identified by the
	 *         given handle
	 */
	public static Object getOptionValue(final Option[] options, final Object optionHandle) {
		for (final Option option : options) {
			if (option.handle.equals(optionHandle) || option.handle.toString().equals(optionHandle)) {
				return option.getValue();
			}
		}
		return null;
	}

	/**
	 * Sets the value of an option identified by the given handle.
	 * 
	 * @param options
	 *            Array of options
	 * @param optionHandle
	 *            Handle identifying the relevant option *
	 * @param value
	 *            New option value to be set
	 */
	public static void setOptionValue(final Option[] options, final Object optionHandle, final Object value) {
		for (final Option option : options) {
			if (option.handle.equals(optionHandle) || option.handle.toString().equals(optionHandle)) {
				option.setValue(value);
			}
		}
	}

}
