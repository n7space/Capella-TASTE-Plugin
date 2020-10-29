// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.widgets.Display;
import org.junit.Before;
import org.junit.Test;

public class ImageUtilsTests {

	// These tests require to run in an environment that provides display. If the
	// environment is headless, it needs to be
	// properly setup.

	protected Image srcImage;

	@Before
	public void setUp() throws Exception {
		final Device device = Display.getCurrent();
		srcImage = new Image(device, new ImageData(16, 16, 8, new PaletteData(0xFF, 0xFF, 0xFF)));
	}

	@Test
	public void testGetIcon() {
		final Image icon = ImageUtils.getIcon(this, Display.getCurrent(), "test");
		// Testing stability and improving coverage. Jar cannot be accessed from unit
		// tests.
		assertNull(icon);
	}

	@Test
	public void testGetScaledIcon() {
		final Image icon = ImageUtils.getScaledIcon(this, Display.getCurrent(), "test", 16);
		// Testing stability and improving coverage. Jar cannot be accessed from unit
		// tests.
		assertNull(icon);
	}

	@Test
	public void testGetScaledImage() {
		final Image dstImage = ImageUtils.getScaledImage(srcImage, 8);
		assertEquals(dstImage.getBounds().width, 8);
		assertEquals(dstImage.getBounds().height, 8);
		dstImage.dispose();
	}

}
