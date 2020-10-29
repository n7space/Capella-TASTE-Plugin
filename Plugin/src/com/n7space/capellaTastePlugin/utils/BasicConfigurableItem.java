// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Base class that helps implementing the ConfigurableObject interface.
 */
public class BasicConfigurableItem implements ConfigurableObject {
	private final Map<String, Option> options = new HashMap<String, Option>();

	protected void addOption(final Option option) {
		options.put(option.handle.toString(), option);
	}

	protected void addOptions(final Iterable<Option> options) {
		for (final Option option : options) {
			addOption(option);
		}
	}

	protected void addOptions(final Option[] options) {
		for (final Option option : options) {
			addOption(option);
		}
	}

	protected boolean getBooleanOptionValue(final Object optionHandle, final boolean defaultValue) {
		final Object value = getOptionValue(optionHandle);
		if (value != null && value instanceof Boolean)
			return (Boolean) value;
		return defaultValue;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Option[] getOptions() {
		return OptionsHelper.convert(options.values());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getOptionValue(final Object optionHandle) {
		final Option option = options.get(optionHandle.toString());
		if (option != null)
			return option.getValue();
		return null;
	}

	protected String getStringOptionValue(final Object optionHandle) {
		final Object value = getOptionValue(optionHandle);
		if (value != null && value instanceof String)
			return (String) value;
		return "";
	}

	protected String getStringOptionValue(final Object optionHandle, final String defaultValue) {
		final Object value = getOptionValue(optionHandle);
		if (value != null && value instanceof String)
			return (String) value;
		return defaultValue;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setOptions(final Option[] newOptions) {
		for (final Option option : newOptions) {
			options.put(option.handle.toString(), option);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setOptionValue(final Object optionHandle, final Object value) {
		final Option option = options.get(optionHandle.toString());
		if (option != null)
			option.setValue(value);
	}
}