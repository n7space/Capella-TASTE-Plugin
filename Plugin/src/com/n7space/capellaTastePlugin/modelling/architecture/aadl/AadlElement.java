// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.architecture.aadl;

import java.util.LinkedList;

/**
 * Class representing an abstract AADL element.
 *
 */
public abstract class AadlElement {

	/**
	 * Class representing element's usage context.
	 *
	 */
	public static class UsageContext {
		/**
		 * Stack of context elements.
		 */
		protected LinkedList<AadlElement> contextElements = new LinkedList<AadlElement>();

		/**
		 * A constructor.
		 */
		public UsageContext() {

		}

		/**
		 * A constructor.
		 *
		 * @param oldContext
		 *            Old context
		 * @param newContextElement
		 *            New element to be put into the context
		 */
		public UsageContext(final UsageContext oldContext, final AadlElement newContextElement) {
			contextElements.add(newContextElement);
			contextElements.addAll(oldContext.contextElements);
		}

		/**
		 * Gets the nearest context element of the given class.
		 *
		 * @param clazz
		 *            Class of the sought element
		 * @return Retrieved context element
		 */
		public AadlElement get(final Class<?> clazz) {
			for (final AadlElement element : contextElements) {
				if (clazz.isInstance(element))
					return element;
			}
			return null;
		}
	}

	/**
	 * Name.
	 */
	public final String name;
	/**
	 * Owner.
	 */
	public Object owner;

	/**
	 * A constructor.
	 * 
	 * @param elementName
	 *            Name
	 */
	public AadlElement(final String elementName) {
		name = elementName;
	}

	/**
	 * Gets a single level indent.
	 * 
	 * @return
	 */
	public String getIndent() {
		return "  ";
	}

	/**
	 * Gets type name.
	 * 
	 * @return Type name
	 */
	public abstract String getTypeName();

	/**
	 * Serializes its AADL definition to a string.
	 * 
	 * @param context
	 *            Usage context
	 * @param linePrefix
	 *            Prefix for each AADL definition line
	 * @return AADL definition
	 */
	public abstract String serializeToString(final UsageContext context, final String linePrefix);
}
