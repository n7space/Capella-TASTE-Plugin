// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Class providing an internal representation of a data package.
 */
public class DataPackage extends DataModelElement {

	protected final List<DataType> definedDataTypes = new ArrayList<DataType>();
	protected final List<DataType> declaredDataTypes = new ArrayList<DataType>();
	protected final List<DataTypeReference> forcedImports = new ArrayList<DataTypeReference>();
	protected final List<DataTypeReference> standardImports = new ArrayList<DataTypeReference>();

	protected final List<DataType.DataTypeValue> definedValues = new ArrayList<DataType.DataTypeValue>();

	/**
	 * The constructor.
	 *
	 * @param name
	 *            Package name
	 * @param packageId
	 *            Package ID
	 */
	public DataPackage(final String name, final String packageId) {
		super(name, packageId);
	}

	/**
	 * Adds type to a forced imports list.
	 *
	 * @param typeReference
	 *            Type reference
	 * @return Whether the declaration was successfully added
	 */
	public boolean addForcedImport(final DataTypeReference typeReference) {
		for (final DataTypeReference other : forcedImports) {
			if (other.id.equals(typeReference.id))
				return false;
		}
		forcedImports.add(typeReference);
		return true;
	}

	/**
	 * Adds type to a standard imports list.
	 *
	 * @param typeReference
	 *            Type reference
	 * @return Whether the declaration was successfully added
	 */
	public boolean addStandardImport(final DataTypeReference typeReference) {
		for (final DataTypeReference other : standardImports) {
			if (other.id.equals(typeReference.id))
				return false;
		}
		standardImports.add(typeReference);
		return true;
	}

	/**
	 * Adds type declaration.
	 *
	 * @param type
	 *            Type
	 * @return Whether the declaration was successfully added
	 */
	public boolean addTypeDeclaration(final DataType type) {
		if (!containsTypeDeclaration(type)) {
			declaredDataTypes.add(type);
			return true;
		}
		return false;
	}

	/**
	 * Adds type definition and implicitly the corresponding declaration.
	 *
	 * @param type
	 *            Type
	 * @return Whether the definition was successfully added
	 */
	public boolean addTypeDefinition(final DataType type) {
		if (!containsTypeDefinition(type)) {
			if (containsTypeDeclaration(type)) {
				// Remove the old one; it is a placeholder created when reference to it was
				// first encountered.
				for (int i = 0; i < declaredDataTypes.size(); i++) {
					final DataType oldOne = declaredDataTypes.get(i);
					if (oldOne.id.equals(type.id)) {
						declaredDataTypes.remove(i);
						break;
					}
				}
			}
			declaredDataTypes.add(type);
			definedDataTypes.add(type);
			return true;
		}
		return false;
	}

	/**
	 * Adds value definition.
	 *
	 * @param value
	 *            Value
	 * @return Whether the definition was successfully added
	 */
	public boolean addValueDefinition(final DataType.DataTypeValue value) {
		if (!containsValueDefinition(value)) {
			definedValues.add(value);
			return true;
		}
		return false;
	}

	/**
	 * Creates a clone of the current data package containing only the selected
	 * subset of items. The copy is shallow.
	 *
	 * @param selection
	 *            The selected of items to be included in the clone
	 * @return A clone of the current package containing the selected subset
	 */
	public DataPackage cloneSubset(final Iterable<DataModelElement> selection) {
		final Set<DataModelElement> set = new HashSet<DataModelElement>();
		for (final DataModelElement element : selection)
			set.add(element);
		final DataPackage clone = new DataPackage(name, id);
		for (final DataType dataType : definedDataTypes) {
			if (set.contains(dataType))
				clone.definedDataTypes.add(dataType);
		}
		for (final DataType dataType : declaredDataTypes) {
			// All declarations are copied, as they may be required to properly resolve
			// references.
			clone.declaredDataTypes.add(dataType);
		}
		for (final DataType.DataTypeValue value : definedValues) {
			if (set.contains(value))
				clone.definedValues.add(value);
		}
		return clone;
	}

	/**
	 * Returns whether the given type is declared in the data package.
	 *
	 * @param type
	 *            The queried type
	 * @return Whether the type is declared in the data package.
	 */
	public boolean containsTypeDeclaration(final DataType type) {
		for (final DataType element : declaredDataTypes) {
			if (element.id.equals(type.id)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns whether the given type, identified by ID, is declared in the data
	 * package.
	 *
	 * @param id
	 *            The queried ID
	 * @return Whether the type is declared in the data package.
	 */
	public boolean containsTypeDeclaration(final String id) {
		for (final DataType element : declaredDataTypes) {
			if (element.id.equals(id)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns whether the given type is defined in the data package.
	 *
	 * @param type
	 *            The queried type
	 * @return Whether the type is defined in the data package.
	 */
	public boolean containsTypeDefinition(final DataType type) {
		for (final DataType element : definedDataTypes) {
			if (element.id.equals(type.id)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns whether the given value is defined in the data package.
	 *
	 * @param value
	 *            The queried value
	 * @return Whether the value is defined in the data package.
	 */
	public boolean containsValueDefinition(final DataType.DataTypeValue value) {
		for (final DataType.DataTypeValue element : definedValues) {
			if (element.id.equals(value.id)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns a list of all declared types.
	 *
	 * @return List of all declared types
	 */
	public List<DataType> getDeclaredTypes() {
		return Collections.unmodifiableList(declaredDataTypes);
	}

	/**
	 * Returns a list of all defined elements.
	 *
	 * @return List of all defined elements
	 */
	public List<DataModelElement> getDefinedElements() {
		final List<DataModelElement> elements = new LinkedList<DataModelElement>();
		elements.addAll(definedDataTypes);
		elements.addAll(definedValues);
		return elements;
	}

	/**
	 * Returns a list of all defined types.
	 *
	 * @return List of all defined types
	 */
	public List<DataType> getDefinedTypes() {
		return Collections.unmodifiableList(definedDataTypes);
	}

	/**
	 * Returns a list of all defined values.
	 *
	 * @return List of all defined values
	 */
	public List<DataType.DataTypeValue> getDefinedValues() {
		return Collections.unmodifiableList(definedValues);
	}

	/**
	 * Returns a list of all forced dependencies.
	 *
	 * @return List of all forced dependencies
	 */
	public List<DataTypeReference> getForcedImports() {
		return Collections.unmodifiableList(forcedImports);
	}

	/**
	 * Returns a list of all forced dependencies.
	 *
	 * @return List of all forced dependencies
	 */
	public List<DataTypeReference> getStandardImports() {
		return Collections.unmodifiableList(standardImports);
	}

}
