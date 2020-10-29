// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.eclipseintegration;

import static org.junit.Assert.assertEquals;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.junit.Before;
import org.junit.Test;

import com.n7space.capellatasteplugin.utils.ConfigurableObject.Option;

public class SettingsProviderTests {

	protected static final String PREFERENCES_NODE_NAME = "com.n7space.capellatasteplugin.preferences.tests";

	protected Option option1 = null;
	protected Option option2 = null;
	protected Option[] options = null;
	protected String option1Handle = "Handle1";
	protected String option2Handle = "Handle2";

	@Before
	public void setUp() throws Exception {
		option1 = new Option(option1Handle, "Description1", "default");
		option2 = new Option(option2Handle, "Description2", "default");
		options = new Option[2];
		options[0] = option1;
		options[1] = option2;
	}

	@Test
	public void testStoreAndRestoreOptionValues() {
		option1.setValue("ANewValue");
		option2.setValue("Let's build a space station");
		SettingsProvider.getInstance().storeOptionValues(options, SettingsProviderTests.PREFERENCES_NODE_NAME);
		option1.setValue("removed");
		option2.setValue("removed");
		SettingsProvider.getInstance().restoreOptionValues(options, SettingsProviderTests.PREFERENCES_NODE_NAME);
		assertEquals(option1.getValue(), "ANewValue");
		assertEquals(option2.getValue(), "Let's build a space station");
	}

	protected void forceSettingsVersion(final int version) {
		final IEclipsePreferences preferences = ConfigurationScope.INSTANCE
				.getNode(SettingsProviderTests.PREFERENCES_NODE_NAME);
		preferences.putInt(SettingsProvider.PREFERENCES_VERSION_HANDLE, version);
		try {
			preferences.flush();
		} catch (final Throwable t) {
			t.printStackTrace();
		}
	}

	@Test
	public void testRestoresDefaultValuesIfVersionIsOutdated() {
		option1.setValue("ANewValue");
		SettingsProvider.getInstance().storeOptionValues(options, SettingsProviderTests.PREFERENCES_NODE_NAME);
		forceSettingsVersion(0);

		option1.setValue("default");
		SettingsProvider.getInstance().restoreOptionValues(options, SettingsProviderTests.PREFERENCES_NODE_NAME);
		assertEquals(option1.getValue(), "default");
	}

}
