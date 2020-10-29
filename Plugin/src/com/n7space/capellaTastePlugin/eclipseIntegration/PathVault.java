// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.eclipseintegration;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;

/**
 * Class for persistent path storage.
 *
 */
public class PathVault {
	/**
	 * Name of the default storage node.
	 */
	public static final String STORAGE_NODE_NAME = "com.n7space.capellatasteplugin.paths";

	protected static PathVault instance = new PathVault();

	/**
	 * Gets a PathVault instance.
	 * 
	 * @return PathValue instance.
	 */
	public static PathVault getInstance() {
		return instance;
	}

	private PathVault() {

	}

	/**
	 * Get path for the given ID from the given storage node.
	 * 
	 * @param pathId
	 *            ID of the path to obtain
	 * @param storageNode
	 *            Storage node to source the path from
	 * @return Path or null if none is found
	 */
	public String getPath(final String pathId, final String storageNode) {
		final IEclipsePreferences preferences = ConfigurationScope.INSTANCE.getNode(storageNode);
		if (preferences == null) {
			return null;
		}
		final String value = preferences.get(pathId, null);
		return value;
	}

	/**
	 * Persistently stores the given path for the given ID in the give storage node.
	 * 
	 * @param pathId
	 *            ID of the path to be stored
	 * @param path
	 *            Path to be stored
	 * @param storageNode
	 *            Storage node to store the path
	 * @return Whether the path was successfully stored
	 */
	public boolean setPath(final String pathId, final String path, final String storageNode) {
		final IEclipsePreferences preferences = ConfigurationScope.INSTANCE.getNode(storageNode);
		preferences.put(pathId, path);
		try {
			preferences.flush();
			return true;
		} catch (final Throwable t) {
			t.printStackTrace();
			return false;
		}
	}

}
