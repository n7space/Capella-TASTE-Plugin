// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StackTraceUtilitiesTests {

	protected Exception getExceptionFromAnInnerMethod() {
		final Exception e = new Exception();
		e.fillInStackTrace();
		return e;
	}

	@Test
	public void testGetTrimmedClassName() {
		assertEquals(StackTraceUtilities.getTrimmedClassName("my.dummy.class"), "class");
		assertEquals(StackTraceUtilities.getTrimmedClassName("class"), "class");
	}

	@Test
	public void testGetTrimmedStackTraceString() {
		final Exception e = getExceptionFromAnInnerMethod();
		final String trace = StackTraceUtilities.getTrimmedStackTraceString(e, 2);
		assertTrue(trace.contains("StackTraceUtilitiesTests"));
		assertTrue(trace.contains("testGetTrimmedStackTraceString"));
		assertTrue(trace.contains("getExceptionFromAnInnerMethod"));
	}

}
