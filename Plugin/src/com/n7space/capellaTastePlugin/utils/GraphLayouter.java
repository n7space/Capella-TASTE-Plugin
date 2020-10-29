// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.utils;

import java.util.LinkedList;
import java.util.List;

/**
 * A modified, size-aware implementation of Fruchterman-Reingold force directed
 * graph layouting algorithm. Based on Graph Drawing by Force-directed Placement
 * by THOMAS M. J. FRUCHTERMAN* AND EDWARD M. REINGOLD
 *
 */
public class GraphLayouter {

	protected class Edge {

		public final Vertex source;
		public final Vertex destination;

		public Edge(final Vertex srcVertex, final Vertex dstVertex) {
			source = srcVertex;
			destination = dstVertex;
		}
	}

	protected class Vertex {
		public final Object handle;
		public Vector2D position = new Vector2D(0.0, 0.0);
		public Vector2D force = new Vector2D(0.0, 0.0);
		public final double area;

		public Vertex(final Object vertexHandle, final double vertexArea) {
			handle = vertexHandle;
			area = vertexArea;
		}

	}

	protected List<Vertex> vertices = new LinkedList<GraphLayouter.Vertex>();
	protected List<Edge> edges = new LinkedList<GraphLayouter.Edge>();
	protected int seed = 0;

	/**
	 * Adds edge to the graph connecting the given elements. The given elements must
	 * be already added to the graph.
	 *
	 * @param srcHandle
	 *            Edge source
	 * @param dstHandle
	 *            Edge target
	 */
	public void addEdge(final Object srcHandle, final Object dstHandle) {
		final Vertex src = getVertex(srcHandle);
		final Vertex dst = getVertex(dstHandle);
		edges.add(new Edge(src, dst));
	}

	/**
	 * Adds a vertex to the graph.
	 *
	 * @param handle
	 *            Handle
	 * @param area
	 *            Vertex's area
	 */
	public void addVertex(final Object handle, final double area) {
		vertices.add(new Vertex(handle, area));
	}

	protected double attractiveForce(final double d, final double k) {
		return d * d / k;
	}

	protected double calculateArea() {
		double result = 0.0;
		for (final Vertex vertex : vertices)
			result += vertex.area;
		return result * 9.0;
	}

	protected void calculateAttractiveForces(final double k) {
		for (final Edge e : edges) {
			final Vector2D delta = e.destination.position.subtract(e.source.position);
			final Vector2D normalizedDelta = delta.normalize();
			final double distance = delta.length();
			final double attraction = attractiveForce(distance, k);
			e.destination.force = e.destination.force.add(normalizedDelta.scale(-attraction));
			e.source.force = e.source.force.add(normalizedDelta.scale(attraction));
		}
	}

	protected void calculateDisplacement(final double temperature) {
		for (final Vertex v : vertices) {
			final Vector2D normalized = v.force.normalize();
			final double magnitude = v.force.length();
			v.position = v.position.add(normalized.scale(Math.min(magnitude, temperature)));
		}
	}

	/**
	 * Calculate layout for the internal graph.
	 */
	public void calculateLayout() {
		final double area = calculateArea();
		final double minTemperature = getMinTemperature();
		final double maxTemperature = getMaxTemperature(area);
		final double k = getMaxSize();
		final int iterations = Math.min(16384, (vertices.size() * vertices.size() + edges.size() * edges.size()) * 128);
		setInitialPositions(area);
		for (int iteration = 0; iteration < iterations; iteration++) {
			resetForces();
			calculateRepulsiveForces(k);
			calculateAttractiveForces(k);
			calculateDisplacement(getTemperature(minTemperature, maxTemperature, iterations, iteration));
			constrainPositions(area);
		}
	}

	protected void calculateRepulsiveForces(final double k) {
		for (final Vertex v : vertices) {
			for (final Vertex u : vertices) {
				if (v.equals(u))
					continue;
				final Vector2D delta = nonZero(v.position.subtract(u.position));
				final Vector2D normalizedDelta = delta.normalize();
				final double distance = Math.max(0, delta.length() - Math.sqrt(v.area) - Math.sqrt(u.area));
				final double repulsion = repulsiveForce(distance, k);
				v.force = v.force.add(normalizedDelta.scale(repulsion));
			}
		}
	}

	protected void constrainPositions(final double area) {
		final double side = Math.sqrt(area);
		for (final Vertex v : vertices) {
			final Vector2D newPosition = new Vector2D(Math.max(0, Math.min(side, v.position.x)),
					Math.max(0, Math.min(side, v.position.y)));
			v.position = newPosition;
		}
	}

	protected double getMaxSize() {
		if (vertices.size() == 0)
			return 0;
		double maxSize = vertices.get(0).area;
		for (final Vertex v : vertices)
			maxSize = Math.max(maxSize, v.area);
		return Math.sqrt(maxSize);
	}

	protected double getMaxTemperature(final double area) {
		return Math.sqrt(area) / 2.0;
	}

	protected double getMinSize() {
		if (vertices.size() == 0)
			return 0;
		double minSize = vertices.get(0).area;
		for (final Vertex v : vertices)
			minSize = Math.min(minSize, v.area);
		return Math.sqrt(minSize);
	}

	protected double getMinTemperature() {
		return getMinSize() / 10;
	}

	protected double getTemperature(final double minTemperature, final double maxTemperature, final int iterations,
			final int iteration) {
		if (iteration < iterations / 2) {
			final double factor = (iterations / 2 - iteration) / (iterations / 2);
			return minTemperature + ((maxTemperature - minTemperature) * factor);
		}
		return minTemperature;
	}

	protected Vertex getVertex(final Object handle) {
		for (final Vertex v : vertices)
			if (v.handle.equals(handle))
				return v;
		return null;
	}

	/**
	 * Gets position of a vertex with the given handle.
	 *
	 * @param handle
	 *            Vertex's handle
	 * @return Vertex's position
	 */
	public Vector2D getVertexPosition(final Object handle) {
		return getVertex(handle).position;
	}

	protected Vector2D nonZero(final Vector2D source) {
		if (source.x == 0.0 || source.y == 0.0) {
			seed++;
			final double angle = Math.PI * 2.0 * seed / vertices.size();
			final double radius = 0.001;
			return new Vector2D(Math.sin(angle) * radius, Math.cos(angle) * radius);
		}
		return source;
	}

	protected double repulsiveForce(final double d, final double k) {
		return k * k / (d + 0.001);
	}

	protected void resetForces() {
		for (final Vertex v : vertices)
			v.force = new Vector2D(0, 0);
	}

	protected void setInitialPositions(final double area) {
		final double side = Math.sqrt(area);
		final int count = vertices.size();
		int index = 0;
		for (final Vertex vertex : vertices) {
			final double factor = ((double) (index++)) / (double) count;
			final double angle = factor * Math.PI * 2.0;
			vertex.position.x = (side + Math.sin(angle) * side) / 2.0;
			vertex.position.y = (side + Math.cos(angle) * side) / 2.0;
		}
	}

}
