// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.eclipseintegration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PathVaultTest {

	protected static final String PREFERENCES_NODE_NAME = "com.n7space.capellatasteplugin.preferences.tests";

	@Test
	public void testSetGetPath() {
		final PathVault vault = PathVault.getInstance();
		assertTrue(vault.setPath("PathId1", "Path1", PREFERENCES_NODE_NAME));
		assertTrue(vault.setPath("PathId2", "Path2", PREFERENCES_NODE_NAME));

		assertEquals(vault.getPath("PathId1", PREFERENCES_NODE_NAME), "Path1");
		assertEquals(vault.getPath("PathId2", PREFERENCES_NODE_NAME), "Path2");

		assertNull(vault.getPath("IncorrectId", PREFERENCES_NODE_NAME));
	}

}
