// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.polarsys.capella.common.data.modellingcore.AbstractType;
import org.polarsys.capella.core.data.capellacore.GeneralizableElement;
import org.polarsys.capella.core.data.capellacore.Type;
import org.polarsys.capella.core.data.information.AggregationKind;
import org.polarsys.capella.core.data.information.Association;
import org.polarsys.capella.core.data.information.DataPkg;
import org.polarsys.capella.core.data.information.UnionKind;
import org.polarsys.capella.core.data.information.UnionProperty;
import org.polarsys.capella.core.data.information.datatype.DataType;
import org.polarsys.capella.core.data.information.datavalue.DataValue;
import org.polarsys.capella.core.data.information.datavalue.NumericValue;

public class UnionTypeMock extends ClassTypeMock implements org.polarsys.capella.core.data.information.Union {

	public static class UnionPropertyTypeMock extends BaseTypeMock implements UnionProperty {

		protected final boolean isOrdered;
		protected final DataType type;

		protected final NumericValue min;
		protected final NumericValue max;

		public UnionPropertyTypeMock(final DataPkg pkg, final String name, final String id,
				final boolean isCollectionOrdered, final DataType parentType, final int minCardinality,
				final int maxCardinality) {
			super(pkg, name, id);
			isOrdered = isCollectionOrdered;
			type = parentType;
			min = new NumericTypeMock.NumericValueMock(pkg, name + "_min", name + "_min_id", null,
					String.valueOf(minCardinality));
			max = new NumericTypeMock.NumericValueMock(pkg, name + "_max", name + "_max_id", null,
					String.valueOf(maxCardinality));
		}

		public UnionPropertyTypeMock(final DataPkg pkg, final String name, final String id, final DataType parentType) {
			super(pkg, name, id);
			min = null;
			max = null;
			type = parentType;
			isOrdered = false;
		}

		@Override
		public AbstractType getAbstractType() {
			return type;
		}

		@Override
		public AggregationKind getAggregationKind() {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public Association getAssociation() {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public DataValue getOwnedDefaultValue() {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public NumericValue getOwnedMaxCard() {
			return max;
		}

		@Override
		public NumericValue getOwnedMaxLength() {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public DataValue getOwnedMaxValue() {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public NumericValue getOwnedMinCard() {
			return min;
		}

		@Override
		public NumericValue getOwnedMinLength() {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public DataValue getOwnedMinValue() {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public DataValue getOwnedNullValue() {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public EList<DataValue> getQualifier() {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public Type getType() {
			return type;
		}

		@Override
		public boolean isIsAbstract() {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public boolean isIsDerived() {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public boolean isIsPartOfKey() {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public boolean isIsReadOnly() {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public boolean isIsStatic() {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public boolean isOrdered() {
			return isOrdered;
		}

		@Override
		public boolean isUnique() {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public void setAbstractType(final AbstractType value) {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public void setAggregationKind(final AggregationKind value) {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public void setIsAbstract(final boolean value) {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public void setIsDerived(final boolean value) {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public void setIsPartOfKey(final boolean value) {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public void setIsReadOnly(final boolean value) {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public void setIsStatic(final boolean value) {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public void setOrdered(final boolean value) {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public void setOwnedDefaultValue(final DataValue value) {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public void setOwnedMaxCard(final NumericValue value) {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public void setOwnedMaxLength(final NumericValue value) {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public void setOwnedMaxValue(final DataValue value) {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public void setOwnedMinCard(final NumericValue value) {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public void setOwnedMinLength(final NumericValue value) {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public void setOwnedMinValue(final DataValue value) {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public void setOwnedNullValue(final DataValue value) {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public void setUnique(final boolean value) {
			throw new RuntimeException("Not implemented");
		}

	}

	protected EList<UnionProperty> unionProperties = new BasicEList<UnionProperty>();

	public UnionTypeMock(final DataPkg pkg, final GeneralizableElement parent, final String name, final String id) {
		super(pkg, parent, name, id);
	}

	public UnionTypeMock(final DataPkg pkg, final String name, final String id) {
		super(pkg, name, id);
	}

	public void addUnionProperty(final UnionProperty property) {
		unionProperties.add(property);
	}

	@Override
	public EList<UnionProperty> getContainedUnionProperties() {
		return unionProperties;
	}

	@Override
	public UnionProperty getDefaultProperty() {
		throw new RuntimeException("Not implemenented");
	}

	@Override
	public UnionProperty getDiscriminant() {
		throw new RuntimeException("Not implemenented");
	}

	@Override
	public UnionKind getKind() {
		throw new RuntimeException("Not implemenented");
	}

	@Override
	public void setDefaultProperty(final UnionProperty value) {
		throw new RuntimeException("Not implemenented");
	}

	@Override
	public void setDiscriminant(final UnionProperty value) {
		throw new RuntimeException("Not implemenented");
	}

	@Override
	public void setKind(final UnionKind value) {
		throw new RuntimeException("Not implemenented");
	}

}
