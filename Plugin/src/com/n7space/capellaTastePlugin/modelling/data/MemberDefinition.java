// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.data;

import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;

/**
 * Class providing an internal representation of a member of another data type
 * (e.g. a structure).
 */
public class MemberDefinition extends DataModelElement implements DependentItem {
	/**
	 * Member data type.
	 */
	public final DataTypeReference dataType;
	/**
	 * The minimum cardinality of the member.
	 */
	public final UsedNumericValue minimumCardinality;
	/**
	 * The maximum cardinality of the member.
	 */
	public final UsedNumericValue maximumCardinality;

	/**
	 * The constructor.
	 * 
	 * @param memberName
	 *            Member name
	 * @param memberId
	 *            Member ID
	 * @param memberDataType
	 *            Member data type
	 */
	public MemberDefinition(final String memberName, final String memberId, final DataTypeReference memberDataType) {
		super(memberName, memberId);
		dataType = memberDataType;
		minimumCardinality = new UsedNumericValue.ExplicitIntegerValue(BigInteger.valueOf(1));
		maximumCardinality = new UsedNumericValue.ExplicitIntegerValue(BigInteger.valueOf(1));
	}

	/**
	 * The constructor.
	 * 
	 * @param memberName
	 *            Member name
	 * @param memberId
	 *            Member ID
	 * @param memberDataType
	 *            Member data type
	 * @param minimumMemberCardinality
	 *            The minimum cardinality of the member
	 * @param maximumMemberCardinality
	 *            The maximum cardinality of the member
	 */
	public MemberDefinition(final String memberName, final String memberId, final DataTypeReference memberDataType,
			final UsedNumericValue minimumMemberCardinality, final UsedNumericValue maximumMemberCardinality) {
		super(memberName, memberId);
		dataType = memberDataType;
		minimumCardinality = minimumMemberCardinality;
		maximumCardinality = maximumMemberCardinality;
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
		if (dataType != null) {
			final DataType type = model.findDataTypeById(dataType.id);
			if (type != null) {
				dependencies.add(type);
			}
		}
		if (minimumCardinality != null) {
			dependencies.addAll(minimumCardinality.getDependencies(model));
		}
		if (maximumCardinality != null) {
			dependencies.addAll(maximumCardinality.getDependencies(model));
		}
		return dependencies;
	}

}
