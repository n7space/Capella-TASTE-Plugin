// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.utils;

/**
 * Class representing a 2D vector with double coordinates.
 *
 */
public class Vector2D {

	/**
	 * X coordinate.
	 */
	public double x = 0.0;
	/**
	 * Y coordinate.
	 */
	public double y = 0.0;

	/**
	 * A constructor.
	 * 
	 * @param xIn
	 *            Initial X coordinate
	 * @param yIn
	 *            Initial Y coordinate
	 */
	public Vector2D(final double xIn, final double yIn) {
		x = xIn;
		y = yIn;
	}

	/**
	 * Adds a vector.
	 * 
	 * @param v
	 *            Other vector
	 * @return A new vector containing the result of the operation
	 */
	public Vector2D add(final Vector2D v) {
		return new Vector2D(x + v.x, y + v.y);
	}

	/**
	 * Returns the intersection of this vector with a rectangle.
	 * 
	 * @param upperLeftCorner
	 *            Rectangle's upper-left corner
	 * @param size
	 *            Rectangle's size
	 * @return Intersection point
	 */
	public Vector2D intersectionWithRectangle(final Vector2D upperLeftCorner, final Vector2D size) {
		final double scaledX = x / size.x;
		final double scaledY = y / size.y;
		final Vector2D normalized = (new Vector2D(scaledX, scaledY)).normalize();
		final Vector2D rectangularized = new Vector2D(1, 1).add(normalized.rectangularize()).scale(0.5f);
		final Vector2D rescaled = new Vector2D(rectangularized.x * size.x, rectangularized.y * size.y);
		return upperLeftCorner.add(rescaled);
	}

	/**
	 * Returns vector length.
	 * 
	 * @return Length
	 */
	public double length() {
		return Math.sqrt(x * x + y * y);
	}

	/**
	 * Returns a normalized copy of this vector.
	 * 
	 * @return Normalized copy of this vector
	 */
	public Vector2D normalize() {
		final double l = length();
		if (l == 0.0)
			return new Vector2D(x, y);
		return new Vector2D(x / l, y / l);
	}

	/**
	 * Clamps the vector to a [-1, -1, 1, 1] rectangle.
	 * 
	 * @return Clamped copy of this vector
	 */
	public Vector2D rectangularize() {
		if (Math.abs(x) >= 0.5)
			return new Vector2D(Math.signum(x), y / 0.5);
		return new Vector2D(x / 0.5, Math.signum(y));
	}

	/**
	 * Scales this vector.
	 * 
	 * @param f
	 *            Scale
	 * @return A new vector containing the result of the operation
	 */
	public Vector2D scale(final double f) {
		return new Vector2D(x * f, y * f);
	}

	/**
	 * Subtracts a vector.
	 * 
	 * @param v
	 *            Other vector
	 * @return A new vector containing the result of the operation
	 */
	public Vector2D subtract(final Vector2D v) {
		return new Vector2D(x - v.x, y - v.y);
	}

}
