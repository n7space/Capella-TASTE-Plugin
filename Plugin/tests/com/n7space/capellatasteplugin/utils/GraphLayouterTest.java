// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.utils;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GraphLayouterTest {

	@Test
	public void testCalculateLayout() {
		final GraphLayouter layouter = new GraphLayouter();
		final Object a = new Object();
		final Object b = new Object();
		final Object c = new Object();
		final Object d = new Object();
		layouter.addVertex(a, 10);
		layouter.addVertex(b, 10);
		layouter.addVertex(c, 10);
		layouter.addVertex(d, 10);
		layouter.addEdge(a, b);
		layouter.addEdge(c, d);

		layouter.calculateLayout();

		final Vector2D positionA = layouter.getVertexPosition(a);
		final Vector2D positionB = layouter.getVertexPosition(b);
		final Vector2D positionC = layouter.getVertexPosition(c);
		final Vector2D positionD = layouter.getVertexPosition(d);

		final double distanceAB = positionA.subtract(positionB).length();
		final double distanceCD = positionC.subtract(positionD).length();
		final double distanceAC = positionA.subtract(positionC).length();

		assertTrue(distanceAB > 0);
		assertTrue(distanceCD > 0);
		assertTrue(distanceAC > 0);

		assertTrue(distanceAB < distanceAC);
		assertTrue(distanceCD < distanceAC);
	}

}
