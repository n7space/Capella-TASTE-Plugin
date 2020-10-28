// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.polarsys.capella.common.data.modellingcore.AbstractType;
import org.polarsys.capella.core.data.capellacommon.StateMachine;
import org.polarsys.capella.core.data.capellacore.Feature;
import org.polarsys.capella.core.data.capellacore.GeneralClass;
import org.polarsys.capella.core.data.capellacore.GeneralizableElement;
import org.polarsys.capella.core.data.capellacore.Type;
import org.polarsys.capella.core.data.information.AggregationKind;
import org.polarsys.capella.core.data.information.Association;
import org.polarsys.capella.core.data.information.Class;
import org.polarsys.capella.core.data.information.DataPkg;
import org.polarsys.capella.core.data.information.KeyPart;
import org.polarsys.capella.core.data.information.Operation;
import org.polarsys.capella.core.data.information.Property;
import org.polarsys.capella.core.data.information.datatype.DataType;
import org.polarsys.capella.core.data.information.datavalue.DataValue;
import org.polarsys.capella.core.data.information.datavalue.NumericValue;

public class ClassTypeMock extends BaseTypeMock implements org.polarsys.capella.core.data.information.Class {

	public static class DataValueMock extends BaseTypeMock implements DataValue {

		protected final Type type;

		public DataValueMock(final DataPkg pkg, final String name, final String id, final Type valueType) {
			super(pkg, name, id);
			type = valueType;
		}

		@Override
		public AbstractType getAbstractType() {
			return type;
		}

		@Override
		public void setAbstractType(final AbstractType value) {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public Type getType() {
			return type;
		}

	}

	public static class PropertyTypeMock extends BaseTypeMock implements Property {

		protected final boolean isOrdered;
		protected final DataType type;

		protected final NumericValue min;
		protected final NumericValue max;

		public PropertyTypeMock(final DataPkg pkg, final String name, final String id,
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

		public PropertyTypeMock(final DataPkg pkg, final String name, final String id, final DataType parentType) {
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

	protected EList<Property> properties = new BasicEList<Property>();
	protected EList<DataValue> values = new BasicEList<DataValue>();

	public ClassTypeMock(final DataPkg pkg, final GeneralizableElement parent, final String name, final String id) {
		super(pkg, parent, name, id);
	}

	public ClassTypeMock(final DataPkg pkg, final String name, final String id) {
		super(pkg, name, id);
	}

	public void addProperty(final Property property) {
		properties.add(property);
	}

	public void addValue(final DataValueMock value) {
		values.add(value);
	}

	@Override
	public EList<Operation> getContainedOperations() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public EList<Property> getContainedProperties() {
		return properties;
	}

	@Override
	public EList<KeyPart> getKeyParts() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public EList<GeneralClass> getNestedGeneralClasses() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public EList<Feature> getOwnedFeatures() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public EList<StateMachine> getOwnedStateMachines() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public EList<Class> getRealizedClasses() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public EList<Class> getRealizingClasses() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public boolean isIsPrimitive() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void setIsPrimitive(final boolean value) {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public EList<DataValue> getOwnedDataValues() {
		return values;
	}

}
