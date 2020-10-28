// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.utils;

/**
 * Class representing a 2D vector with integer coordinates.
 *
 */
public class Vector2I {
	/**
	 * X coordinate.
	 */
	public int x;
	/**
	 * Y coordinate.
	 */
	public int y;

	/**
	 * A constructor.
	 * 
	 * @param initialX
	 *            Initial X coordinate
	 * @param initialY
	 *            Initial Y coordinate
	 */
	public Vector2I(final int initialX, final int initialY) {
		x = initialX;
		y = initialY;
	}
}