// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.data;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Class providing an internal representation of an enumeration data type.
 */
public class EnumeratedDataType extends DataType implements DependentItem {

	/**
	 * Class providing an internal representation of an enumeration literal.
	 */
	public static class Literal extends DataModelElement implements DependentItem {

		/**
		 * Domain value of the enumeration.
		 */
		public DataModelElement domainValue;

		/**
		 * Containing enumerated type.
		 */
		public final EnumeratedDataType containingType;

		/**
		 * The constructor.
		 * 
		 * @param parent
		 *            Containing enumeration
		 * @param elementName
		 *            Literal name
		 * @param elementId
		 *            Literal ID
		 */
		public Literal(final EnumeratedDataType parent, final String elementName, final String elementId) {
			super(elementName, elementId);
			containingType = parent;
		}

		/**
		 * Returns a set of all dependencies.
		 * 
		 * @param model
		 *            Data model
		 * @return Set of dependencies
		 */
		@Override
		public Set<DataModelElement> getDependencies(final DataModel model) {
			final Set<DataModelElement> dependencies = new HashSet<DataModelElement>();
			if (domainValue != null) {
				dependencies.add(domainValue);
			}
			return dependencies;
		}

	}

	/**
	 * List of defined literals.
	 */
	public final List<Literal> literals = new LinkedList<Literal>();

	/**
	 * The constructor
	 * 
	 * @param parentPackage
	 *            Parent data package
	 * @param name
	 *            Enumeration name
	 * @param id
	 *            Enumeration ID
	 */
	public EnumeratedDataType(final DataPackage parentPackage, final String name, final String id) {
		super(parentPackage, name, id);
	}

	/**
	 * Returns a set of all dependencies.
	 * 
	 * @param model
	 *            Data model
	 * @return Set of dependencies
	 */
	@Override
	public Set<DataModelElement> getDependencies(final DataModel model) {
		final Set<DataModelElement> dependencies = new HashSet<DataModelElement>();
		for (final Literal literal : literals) {
			dependencies.addAll(literal.getDependencies(model));
		}
		return dependencies;
	}

}
