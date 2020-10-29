// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.utils;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

public class TopologicalSorterTests {

	@Test
	public void testGetSortedObjects() {
		final TopologicalSorter sorter = new TopologicalSorter();
		final String a = "A";
		final String b = "B";
		final String c = "C";
		final String d = "D";
		final String e = "E";

		sorter.addItem(a);
		sorter.addItem(b);
		sorter.addItem(c);
		sorter.addItem(d);
		sorter.addItem(e);

		sorter.addDependency(a, b);
		sorter.addDependency(a, c);
		sorter.addDependency(c, b);
		sorter.addDependency(a, d);
		sorter.addDependency(d, c);
		sorter.addDependency(e, d);
		sorter.addDependency(a, e);

		final List<Object> sortedList = sorter.getSortedItems();
		assertEquals(b, sortedList.get(0));
		assertEquals(c, sortedList.get(1));
		assertEquals(d, sortedList.get(2));
		assertEquals(e, sortedList.get(3));
		assertEquals(a, sortedList.get(4));
	}

}
