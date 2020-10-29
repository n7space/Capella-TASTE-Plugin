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
 * Class providing an internal representation of a structured data type.
 */
public class StructuredDataType extends DataType implements DependentItem {

	/**
	 * Enumeration listing possible kinds of the structured data type.
	 */
	public static enum Kind {
		/**
		 * The structured type represents a structure.
		 */
		Structure,
		/**
		 * The structured type represents a union.
		 */
		Union
	}

	/**
	 * Structured type kind.
	 */
	public final Kind kind;

	/**
	 * List of members.
	 */
	public final List<MemberDefinition> members = new LinkedList<MemberDefinition>();

	/**
	 * The constructor.
	 * 
	 * @param parentPackage
	 *            Parent data package
	 * @param parent
	 *            Parent data type
	 * @param name
	 *            Type name
	 * @param id
	 *            Type ID
	 * @param dataTypeKind
	 *            Structured data type kind
	 */
	public StructuredDataType(final DataPackage parentPackage, final DataTypeReference parent, final String name,
			final String id, final Kind dataTypeKind) {
		super(parentPackage, parent, name, id);
		kind = dataTypeKind;
	}

	/**
	 * The constructor.
	 * 
	 * @param parentPackage
	 *            Parent data package
	 * @param name
	 *            Type name
	 * @param id
	 *            Type ID
	 * @param dataTypeKind
	 *            Structured data type kind
	 */
	public StructuredDataType(final DataPackage parentPackage, final String name, final String id,
			final Kind dataTypeKind) {
		super(parentPackage, name, id);
		kind = dataTypeKind;
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
		for (final MemberDefinition member : members) {
			dependencies.addAll(member.getDependencies(model));
		}
		dependencies.addAll(getDirectDescendants(model));
		return dependencies;
	}

	/**
	 * Returns a list of all direct descendants.
	 * 
	 * @param model
	 *            Data model
	 * @return List of all direct descendants
	 */
	public List<StructuredDataType> getDirectDescendants(final DataModel model) {
		final List<StructuredDataType> result = new LinkedList<StructuredDataType>();
		for (final DataPackage pkg : model.dataPackages) {
			for (final DataType type : pkg.definedDataTypes) {
				if (type instanceof StructuredDataType) {
					final StructuredDataType structure = (StructuredDataType) type;
					if (structure.parent != null && structure.parent.refersTo(this)) {
						result.add(structure);
					}
				}
			}
		}
		return result;
	}
}
