// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.utils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Class providing a topological sort.
 */
public class TopologicalSorter {

	protected final List<Object> vertices = new LinkedList<Object>();
	protected final Map<Object, List<Object>> edges = new HashMap<Object, List<Object>>();

	/**
	 * Adds a dependency (edge) to the topology.
	 * 
	 * @param itemThatIsDependent
	 *            Item that is dependent
	 * @param dependency
	 *            The item's dependency
	 */
	public void addDependency(final Object itemThatIsDependent, final Object dependency) {
		edges.get(itemThatIsDependent).add(dependency);
	}

	/**
	 * Adds a vertex to the topology.
	 * 
	 * @param item
	 *            Item to be added as a vertex
	 */
	public void addItem(final Object item) {
		vertices.add(item);
		edges.put(item, new LinkedList<Object>());
	}

	/**
	 * Returns a list of topologically sorted items.
	 * 
	 * @return List of topologically sorted items.
	 */
	public List<Object> getSortedItems() {
		final Set<Object> visited = new HashSet<Object>();
		final LinkedList<Object> sorted = new LinkedList<Object>();
		for (final Object x : vertices) {
			if (!visited.contains(x)) {
				sort(x, visited, sorted);
			}
		}
		return sorted;
	}

	protected void sort(final Object x, final Set<Object> visited, final List<Object> sorted) {
		visited.add(x);
		final List<Object> neighbours = edges.get(x);
		for (final Object neighbour : neighbours) {
			if (!visited.contains(neighbour)) {
				sort(neighbour, visited, sorted);
			}
		}
		sorted.add(x);
	}

}
