// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.architecture.aadldefinitionproviders;

import java.util.List;

import com.n7space.capellatasteplugin.modelling.architecture.aadldefinitionproviders.Layout.LayoutElement;
import com.n7space.capellatasteplugin.modelling.architecture.aadldefinitionproviders.Layout.Position;

/**
 * Class facilitating translation of Layout properties into TASTE coordinates.
 *
 */
public class CoordinateHelper {

	/**
	 * Converts bounds into TASTE coordinates.
	 *
	 * @param bounds
	 *            Bounds
	 * @return TASTE coordinates
	 */
	public static String boundsToTasteCoordinates(final Layout.Position[] bounds) {
		return "\"" + bounds[0].x + " " + bounds[0].y + " " + bounds[1].x + " " + bounds[1].y + "\"";
	}

	/**
	 * Converts element position and size to TASTE coordinates.
	 * 
	 * @param element
	 *            Positioned element
	 * @return TASTE coordinates
	 */
	public static String getTasteCoordinates(final LayoutElement element) {
		return positionAndSizeToTasteCoordinates(element.getPosition(), element.getSize());
	}

	/**
	 * Converts a path into TASTE coordinates.
	 * 
	 * @param path
	 *            A list of positions representing a path
	 * @return TASTE coordinates
	 */
	public static String pathToTasteCoordinates(final List<Position> path) {
		String pathString = "";
		for (final Position position : path) {
			pathString += " " + position.x + " " + position.y;
		}
		return "\"" + pathString.trim() + "\"";
	}

	/**
	 * Converts position and size to TASTE coordinates.
	 * 
	 * @param position
	 *            Position
	 * @param size
	 *            Size
	 * @return TASTE coordinates
	 */
	public static String positionAndSizeToTasteCoordinates(final Layout.Position position, final Layout.Size size) {
		return "\"" + position.x + " " + position.y + " " + (position.x + size.x) + " " + (position.y + size.y) + "\"";
	}

	/**
	 * Converts position to TASTE coordinates.
	 * 
	 * @param position
	 *            Position
	 * @return TASTE coordinates
	 */
	public static String positionToTasteCoordinates(final Layout.Position position) {
		return "\"" + position.x + " " + position.y + "\"";
	}
}
