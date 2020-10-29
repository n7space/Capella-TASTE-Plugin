// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.utils;

/**
 * Interface to be implemented by objects that provide configuration through
 * options.
 */
public interface ConfigurableObject {
	/**
	 * Class representing a single element of configuration.
	 */
	public static class Option {
		/**
		 * Handle by which the option is identified.
		 */
		public final Object handle;
		/**
		 * Option description.
		 */
		public final String description;
		private Object value;
		/**
		 * Default value.
		 */
		public final Object defaultValue;
		/**
		 * Whether the value can be empty (e.g. null or of size equal 0).
		 */
		public final boolean canBeEmpty;

		/**
		 * The constructor.
		 * 
		 * @param optionHandle
		 *            Handle
		 * @param optionDescription
		 *            Description
		 * @param optionValue
		 *            Default value
		 */
		public Option(final Object optionHandle, final String optionDescription, final Object optionValue) {
			handle = optionHandle;
			description = optionDescription;
			value = optionValue;
			canBeEmpty = true;
			defaultValue = optionValue;
		}

		/**
		 * The constructor.
		 * 
		 * @param optionHandle
		 *            Handle
		 * @param optionDescription
		 *            Description
		 * @param optionValue
		 *            Default value
		 * @param isEmptinessAllowed
		 *            Is the option value allowed to be empty (e.g. null or of size
		 *            equal 0).
		 */
		public Option(final Object optionHandle, final String optionDescription, final Object optionValue,
				final boolean isEmptinessAllowed) {
			handle = optionHandle;
			description = optionDescription;
			value = optionValue;
			defaultValue = optionValue;
			canBeEmpty = isEmptinessAllowed;
		}

		/**
		 * Returns option value.
		 * 
		 * @return Value
		 */
		public Object getValue() {
			if (!canBeEmpty && (value == null || value.toString().isEmpty())) {
				return defaultValue;
			}
			return value;
		}

		/**
		 * Sets option value.
		 * 
		 * @param newValue
		 *            New option value
		 */
		public void setValue(final Object newValue) {
			if (!canBeEmpty && (newValue == null || newValue.toString().isEmpty())) {
				return;
			}
			value = newValue;
		}
	}

	/**
	 * Returns list of options.
	 * 
	 * @return list of options
	 */
	Option[] getOptions();

	/**
	 * Returns the value of the option identified by the given handle.
	 * 
	 * @param optionHandle
	 *            Option handle
	 * @return Option value or null if no option is found for the given handle
	 */
	Object getOptionValue(final Object optionHandle);

	/**
	 * Sets options.
	 * 
	 * @param options
	 *            List of options
	 */
	void setOptions(final Option[] options);

	/**
	 * Sets the value of a single option identified by the given handle.
	 * 
	 * @param optionHandle
	 *            Option handle
	 * @param value
	 *            Value to be set
	 */
	void setOptionValue(final Object optionHandle, final Object value);

}
