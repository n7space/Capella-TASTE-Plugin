// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.eclipseintegration;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;

import com.n7space.capellatasteplugin.utils.ConfigurableObject.Option;

/**
 * A singleton settings provider for managing the persistent configuration.
 */
public class SettingsProvider {
	/**
	 * Name of the default preferences node.
	 */
	public static final String PREFERENCES_NODE_NAME = "com.n7space.capellatasteplugin.preferences";

	/**
	 * ID of the preferences version.
	 */
	public static final int VERSION_ID = 9;

	/**
	 * Handle of the preferences version.
	 */
	public static final String PREFERENCES_VERSION_HANDLE = "com.n7space.capellatasteplugin.preferences.version";

	protected static SettingsProvider instance = new SettingsProvider();

	/**
	 * Returns the settings provider singleton instance.
	 *
	 * @return Settings provider instance
	 */
	public static SettingsProvider getInstance() {
		return instance;
	}

	protected int getVersion(final IEclipsePreferences preferences) {
		if (preferences == null) {
			return 0;
		}
		return preferences.getInt(PREFERENCES_VERSION_HANDLE, 0);
	}

	protected boolean handleOptionsVersioning(final Option[] options, final IEclipsePreferences preferences) {
		final int currentVersion = getVersion(preferences);
		if (currentVersion < VERSION_ID) {
			if (!updateOptionsVersion(options, preferences, currentVersion)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Restores the values of the given options from the given storage node.
	 *
	 * @param options
	 *            List of options the values of which are to be restored
	 * @param storageNode
	 *            The used storage node
	 * @return Whether the storage node was found
	 */
	public boolean restoreOptionValues(final Option[] options, final String storageNode) {
		final IEclipsePreferences preferences = ConfigurationScope.INSTANCE.getNode(storageNode);
		if (preferences == null) {
			return false;
		}
		if (!handleOptionsVersioning(options, preferences)) {
			return false;
		}
		for (final Option option : options) {
			if (option.getValue() instanceof String) {
				final String value = preferences.get(option.handle.toString(), null);
				if (value != null) {
					option.setValue(value);
				}
			} else if (option.getValue() instanceof Boolean) {
				final boolean value = preferences.getBoolean(option.handle.toString(), false);
				option.setValue(Boolean.valueOf(value));
			}
		}
		return true;
	}

	protected void storeOptionValues(final Option[] options, final IEclipsePreferences preferences) {
		for (final Option option : options) {
			if (option.getValue() instanceof String) {
				preferences.put(option.handle.toString(), (String) option.getValue());
			} else if (option.getValue() instanceof Boolean) {
				preferences.putBoolean(option.handle.toString(), (Boolean) option.getValue());
			}
		}
		preferences.putInt(PREFERENCES_VERSION_HANDLE, VERSION_ID);
	}

	/**
	 * Stores the values of the given options into the given storage node.
	 *
	 * @param options
	 *            List of options the values of which are to be stored
	 * @param storageNode
	 *            The used storage node
	 * @return Whether the preferences were successfully stored
	 */
	public boolean storeOptionValues(final Option[] options, final String storageNode) {
		final IEclipsePreferences preferences = ConfigurationScope.INSTANCE.getNode(storageNode);
		storeOptionValues(options, preferences);
		try {
			preferences.flush();
			return true;
		} catch (final Throwable t) {
			t.printStackTrace();
			return false;
		}
	}

	protected boolean updateOptionsVersion(final Option[] options, final IEclipsePreferences preferences,
			final int currentVersion) {
		// No previous versions were released, so just recreate.
		storeOptionValues(options, preferences);
		try {
			preferences.flush();
			return true;
		} catch (final Throwable t) {
			t.printStackTrace();
			return false;
		}
	}

}
