// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.data;

import java.util.HashSet;
import java.util.Set;

/**
 * Base class for internal data type representation.
 */
public class DataType extends DataModelElement {
	/**
	 * Provides internal representation of a value.
	 */
	public static class DataTypeValue extends DataModelElement implements DependentItem {
		/**
		 * Containing data package.
		 */
		public final DataPackage dataPackage;
		/**
		 * Value data type.
		 */
		public final DataTypeReference type;
		/**
		 * Value.
		 */
		public final Object value;

		/**
		 * The constructor.
		 *
		 * @param parentPackage
		 *            Parent data package.
		 * @param name
		 *            Value name
		 * @param valueId
		 *            Value ID
		 * @param valueType
		 *            Value type
		 * @param actualValue
		 *            The actual value
		 */
		public DataTypeValue(final DataPackage parentPackage, final String name, final String valueId,
				final DataTypeReference valueType, final Object actualValue) {
			super(name, valueId);
			dataPackage = parentPackage;
			type = valueType;
			value = actualValue;
		}

		/**
		 * Returns a set of dependencies.
		 *
		 * @param model
		 *            Data model
		 * @return Value dependencies
		 */
		@Override
		public Set<DataModelElement> getDependencies(final DataModel model) {
			final Set<DataModelElement> dependencies = new HashSet<DataModelElement>();
			final DataType referencedType = model.findDataTypeById(type.id);
			if (referencedType != null) {
				dependencies.add(referencedType);
			}
			return dependencies;
		}
	}

	/**
	 * Parent data type.
	 */
	public final DataTypeReference parent;
	/**
	 * Containing data package.
	 */
	public final DataPackage dataPackage;

	/**
	 * Description.
	 */
	public String description;

	/**
	 * The constructor.
	 *
	 * @param parentPackage
	 *            Parent data package
	 * @param dataTypeSuperClass
	 *            Parent data type
	 * @param dataTypeName
	 *            Type name
	 * @param dataTypeId
	 *            Type ID
	 */
	public DataType(final DataPackage parentPackage, final DataTypeReference dataTypeSuperClass,
			final String dataTypeName, final String dataTypeId) {
		super(dataTypeName, dataTypeId);
		dataPackage = parentPackage;
		parent = dataTypeSuperClass;
	}

	/**
	 * The constructor.
	 *
	 * @param parentPackage
	 *            Parent data package
	 * @param dataTypeName
	 *            Type name
	 * @param dataTypeId
	 *            Type ID
	 */
	public DataType(final DataPackage parentPackage, final String dataTypeName, final String dataTypeId) {
		super(dataTypeName, dataTypeId);
		dataPackage = parentPackage;
		parent = null;
	}

}
