// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.data;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

import com.n7space.capellatasteplugin.modelling.NameConverter;
import com.n7space.capellatasteplugin.modelling.data.DataType.DataTypeValue;

/**
 * An abstract class representing a numeric value used in type declarations.
 */
public abstract class UsedNumericValue implements DependentItem {

	/**
	 * Class representing an explicit integer value used in type declarations.
	 */
	public static class ExplicitIntegerValue extends UsedNumericValue {
		public final BigInteger explicitValue;

		/**
		 * The constructor.
		 *
		 * @param boundValue
		 *            The explicit value
		 */
		public ExplicitIntegerValue(final BigInteger boundValue) {
			explicitValue = boundValue;
		}

		/**
		 * Returns the string representation of the represented value.
		 *
		 * @param nameConverter
		 *            Name converter
		 * @return String representation of the represented value
		 */
		@Override
		public String getValue(final NameConverter nameConverter) {
			if (explicitValue != null)
				return explicitValue.toString();
			return "0";
		}

		/**
		 * Returns whether the represented value is equal one.
		 *
		 * @return Whether the represented value is equal one.
		 */
		@Override
		public boolean isOne() {
			if (explicitValue == null)
				return false;
			return explicitValue.longValue() == 1;
		}
	}

	/**
	 * Class representing an explicit real value used in type declarations.
	 */
	public static class ExplicitRealValue extends UsedNumericValue {
		public final double explicitValue;

		/**
		 * The constructor.
		 *
		 * @param boundValue
		 *            The explicit value
		 */
		public ExplicitRealValue(final double boundValue) {
			explicitValue = boundValue;
		}

		/**
		 * Returns the string representation of the represented value.
		 *
		 * @param nameConverter
		 *            Name converter
		 * @return String representation of the represented value
		 */
		@Override
		public String getValue(final NameConverter nameConverter) {
			return "" + explicitValue;
		}

		/**
		 * Returns whether the represented value is equal one.
		 *
		 * @return Whether the represented value is equal one.
		 */
		@Override
		public boolean isOne() {
			return explicitValue == 1.0;
		}
	}

	/**
	 * Class representing a numeric used in type declarations that is a reference to
	 * a data type value.
	 */
	public static class ReferencedNumericValue extends UsedNumericValue {
		public final DataTypeValue referencedValue;

		/**
		 * THe constructor.
		 *
		 * @param referencedNumericValue
		 *            The referenced value
		 */
		public ReferencedNumericValue(final DataTypeValue referencedNumericValue) {
			referencedValue = referencedNumericValue;
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
			final Set<DataModelElement> dependencies = super.getDependencies(model);
			if (referencedValue != null) {
				dependencies.add(referencedValue);
			}
			return dependencies;
		}

		/**
		 * Returns the string representation of the represented value.
		 *
		 * @param nameConverter
		 *            Name converter
		 * @return String representation of the represented value
		 */
		@Override
		public String getValue(final NameConverter nameConverter) {
			if (referencedValue != null)
				return nameConverter.getAsn1IdentifierName(referencedValue.dataPackage.name, referencedValue.name,
						referencedValue.id);
			return "0";
		}

		/**
		 * Returns whether the represented value is equal one.
		 *
		 * @return Whether the represented value is equal one.
		 */
		@Override
		public boolean isOne() {
			return false;
		}
	}

	/**
	 * Class representing a special numeric value used in type declarations.
	 */
	public static class SpecialNumericValue extends UsedNumericValue {
		/**
		 * Enumeration listing possible kinds of special integer values.
		 */
		public static enum Kind {
			/**
			 * Star, indicating an unbounded quantity.
			 */
			STAR
		}

		/**
		 * Kind of the special integer value.
		 */
		public final Kind kind;

		/**
		 * The constructor.
		 *
		 * @param valueKind
		 *            Kind of the special numeric value
		 */
		public SpecialNumericValue(final Kind valueKind) {
			kind = valueKind;
		}

		/**
		 * Returns the string representation of the represented value.
		 *
		 * @param nameConverter
		 *            Name converter
		 * @return String representation of the represented value
		 */
		@Override
		public String getValue(final NameConverter nameConverter) {
			switch (kind) {
			case STAR:
				return "MAX";
			default:
				break;
			}
			return "0";
		}

		/**
		 * Returns whether the represented value is equal one.
		 *
		 * @return Whether the represented value is equal one.
		 */
		@Override
		public boolean isOne() {
			return false;
		};
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
		return dependencies;
	}

	/**
	 * Returns the string representation of the represented value.
	 *
	 * @param nameConverter
	 *            Name converter
	 * @return String representation of the represented value
	 */
	public abstract String getValue(final NameConverter nameConverter);

	/**
	 * Returns whether the represented value is equal one.
	 *
	 * @return Whether the represented value is equal one.
	 */
	public abstract boolean isOne();
}
