// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks;

import org.polarsys.capella.common.data.modellingcore.AbstractType;
import org.polarsys.capella.core.data.capellacore.GeneralizableElement;
import org.polarsys.capella.core.data.capellacore.Type;
import org.polarsys.capella.core.data.information.DataPkg;
import org.polarsys.capella.core.data.information.Unit;
import org.polarsys.capella.core.data.information.datatype.NumericType;
import org.polarsys.capella.core.data.information.datatype.NumericTypeKind;
import org.polarsys.capella.core.data.information.datatype.PhysicalQuantity;
import org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue;
import org.polarsys.capella.core.data.information.datavalue.NumericValue;

public class NumericTypeMock extends BaseTypeMock implements PhysicalQuantity {

	public static class UnitMock extends BaseTypeMock implements Unit {

		public UnitMock(final String name) {
			super(null, name, name + "_id");
		}

	}

	public static class NumericValueMock extends BaseTypeMock implements LiteralNumericValue {

		protected final NumericType type;
		protected final String value;

		public NumericValueMock(final DataPkg pkg, final String name, final String id, final NumericType valueType,
				final String actualValue) {
			super(pkg, name, id);
			type = valueType;
			value = actualValue;
		}

		@Override
		public AbstractType getAbstractType() {
			return type;
		}

		@Override
		public NumericType getNumericType() {
			return type;
		}

		@Override
		public Type getType() {
			return type;
		}

		@Override
		public Unit getUnit() {
			return null;
		}

		@Override
		public String getValue() {
			return value;
		}

		@Override
		public void setAbstractType(final AbstractType value) {
			throw new RuntimeException("Not implemented");

		}

		@Override
		public void setUnit(final Unit value) {
			throw new RuntimeException("Not implemented");

		}

		@Override
		public void setValue(final String value) {
			throw new RuntimeException("Not implemented");
		}

	}

	protected final NumericTypeKind kind;
	protected final NumericValue maxValue;
	protected final NumericValue minValue;
	protected final UnitMock unit;

	public NumericTypeMock(final DataPkg pkg, final GeneralizableElement parentType, final String name, final String id,
			final NumericTypeKind typeKind) {
		super(pkg, parentType, name, id);
		kind = typeKind;
		maxValue = null;
		minValue = null;
		unit = null;
	}

	public NumericTypeMock(final DataPkg pkg, final String name, final String id, final NumericTypeKind typeKind) {
		super(pkg, name, id);
		kind = typeKind;
		maxValue = null;
		minValue = null;
		unit = null;
	}

	public NumericTypeMock(final DataPkg pkg, final String name, final String id, final NumericTypeKind typeKind,
			final UnitMock unitMock) {
		super(pkg, name, id);
		kind = typeKind;
		maxValue = null;
		minValue = null;
		unit = unitMock;
	}

	public NumericTypeMock(final DataPkg pkg, final String name, final String id, final NumericTypeKind typeKind,
			final String description) {
		super(pkg, null, name, id, description);
		kind = typeKind;
		maxValue = null;
		minValue = null;
		unit = null;
	}

	public NumericTypeMock(final DataPkg pkg, final String name, final String id, final NumericTypeKind typeKind,
			final String max, final String min) {
		super(pkg, name, id);
		kind = typeKind;
		minValue = new NumericValueMock(pkg, name + "_min", name + "_min_id", this, max);
		maxValue = new NumericValueMock(pkg, name + "_max", name + "_max_id", this, min);
		unit = null;
	}

	@Override
	public NumericTypeKind getKind() {
		return kind;
	}

	@Override
	public NumericValue getOwnedDefaultValue() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public NumericValue getOwnedMaxValue() {
		return maxValue;
	}

	@Override
	public NumericValue getOwnedMinValue() {
		return minValue;
	}

	@Override
	public NumericValue getOwnedNullValue() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void setKind(final NumericTypeKind value) {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void setOwnedDefaultValue(final NumericValue value) {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void setOwnedMaxValue(final NumericValue value) {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public void setOwnedMinValue(final NumericValue value) {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public void setOwnedNullValue(final NumericValue value) {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public Unit getUnit() {
		return unit;
	}

	@Override
	public void setUnit(final Unit value) {
		throw new RuntimeException("Not implemented");

	}

}
