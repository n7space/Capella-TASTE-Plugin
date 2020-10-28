// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.utils;

import java.io.InputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;

/**
 * Utility class providing image related methods.
 */
public class ImageUtils {
	/**
	 * Returns an icon image identified by the given name from the jar pointed to by
	 * the provided context.
	 * 
	 * @param context
	 *            Context providing the used class loader. Object must belong to the
	 *            same jar as the requested icon
	 * @param device
	 *            Graphic device
	 * @param name
	 *            Name of the icon
	 * @return Icon image
	 */
	public static Image getIcon(final Object context, final Device device, final String name) {
		InputStream stream = context.getClass().getResourceAsStream("icons/" + name);
		if (stream == null) {
			stream = context.getClass().getResourceAsStream("/icons/" + name);
		}
		if (stream == null) {
			stream = context.getClass().getResourceAsStream(name);
		}
		if (stream == null) {
			return null;
		}
		final Image image = new Image(device, stream);
		return image;
	}

	/**
	 * Returns a resized icon image identified by the given name from the jar
	 * pointed to by the provided context.
	 * 
	 * @param context
	 *            Context providing the used class loader. Object must belong to the
	 *            same jar as the requested icon
	 * @param device
	 *            Graphic device
	 * @param name
	 *            Name of the icon
	 * @param size
	 *            The desired size
	 * @return Resized icon image
	 */
	public static Image getScaledIcon(final Object context, final Device device, final String name, final int size) {
		final Image src = getIcon(context, device, name);
		if (src == null) {
			return null;
		}
		final Image dst = getScaledImage(src, size);
		src.dispose();
		return dst;
	}

	/**
	 * Returns a resized image.
	 * 
	 * @param src
	 *            The source image to be resized
	 * @param size
	 *            The desired size
	 * @return Resized image
	 */
	public static Image getScaledImage(final Image src, final int size) {
		final Image dst = new Image(src.getDevice(), size, size);
		final GC gc = new GC(dst);
		gc.setAntialias(SWT.ON);
		gc.setInterpolation(SWT.HIGH);
		gc.drawImage(src, 0, 0, src.getBounds().width, src.getBounds().height, 0, 0, dst.getBounds().width,
				dst.getBounds().height);
		gc.dispose();
		return dst;
	}
}
