// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks;

import org.eclipse.emf.common.util.EList;
import org.polarsys.capella.core.data.capellacore.Feature;
import org.polarsys.capella.core.data.capellacore.Type;
import org.polarsys.capella.core.data.information.AggregationKind;
import org.polarsys.capella.core.data.information.CollectionKind;
import org.polarsys.capella.core.data.information.DataPkg;
import org.polarsys.capella.core.data.information.Operation;
import org.polarsys.capella.core.data.information.Property;
import org.polarsys.capella.core.data.information.datatype.DataType;
import org.polarsys.capella.core.data.information.datavalue.DataValue;
import org.polarsys.capella.core.data.information.datavalue.NumericValue;

public class CollectionTypeMock extends BaseTypeMock implements org.polarsys.capella.core.data.information.Collection {

	protected final boolean isOrdered;
	protected final DataType type;

	protected final NumericValue min;
	protected final NumericValue max;

	public CollectionTypeMock(final DataPkg pkg, final String name, final String id, final boolean isCollectionOrdered,
			final DataType parentType) {
		super(pkg, name, id);
		isOrdered = isCollectionOrdered;
		type = parentType;
		min = null;
		max = null;
	}

	public CollectionTypeMock(final DataPkg pkg, final String name, final String id, final boolean isCollectionOrdered,
			final DataType parentType, final int minCardinality, final int maxCardinality) {
		super(pkg, name, id);
		isOrdered = isCollectionOrdered;
		type = parentType;
		min = new NumericTypeMock.NumericValueMock(pkg, name + "_min", name + "_min_id", null,
				String.valueOf(minCardinality));
		max = new NumericTypeMock.NumericValueMock(pkg, name + "_max", name + "_max_id", null,
				String.valueOf(maxCardinality));
	}

	public CollectionTypeMock(final DataPkg pkg, final String name, final String id, final boolean isCollectionOrdered,
			final DataType parentType, final NumericValue minCardinality, final NumericValue maxCardinality) {
		super(pkg, name, id);
		isOrdered = isCollectionOrdered;
		type = parentType;
		min = minCardinality;
		max = maxCardinality;
	}

	@Override
	public AggregationKind getAggregationKind() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public EList<Operation> getContainedOperations() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public EList<Property> getContainedProperties() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public EList<DataType> getIndex() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public CollectionKind getKind() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public DataValue getOwnedDefaultValue() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public EList<Feature> getOwnedFeatures() {
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
	public Type getType() {
		return type;
	}

	@Override
	public boolean isIsPrimitive() {
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
	public void setAggregationKind(final AggregationKind value) {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public void setIsPrimitive(final boolean value) {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void setKind(final CollectionKind value) {
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
	public void setType(final Type value) {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void setUnique(final boolean value) {
		throw new RuntimeException("Not implemented");
	}

}
