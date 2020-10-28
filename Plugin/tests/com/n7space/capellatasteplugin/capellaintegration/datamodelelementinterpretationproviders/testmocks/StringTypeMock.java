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
import org.polarsys.capella.core.data.information.datatype.StringType;
import org.polarsys.capella.core.data.information.datavalue.AbstractStringValue;
import org.polarsys.capella.core.data.information.datavalue.LiteralStringValue;
import org.polarsys.capella.core.data.information.datavalue.NumericValue;

public class StringTypeMock extends BaseTypeMock implements StringType {

	public static class StringValueMock extends BaseTypeMock implements LiteralStringValue {

		protected final StringType type;
		protected final String value;

		public StringValueMock(final DataPkg pkg, final String name, final String id, final StringType valueType,
				final String literalValue) {
			super(pkg, name, id);
			value = literalValue;
			type = valueType;
		}

		@Override
		public AbstractType getAbstractType() {
			return type;
		}

		@Override
		public StringType getStringType() {
			return type;
		}

		@Override
		public Type getType() {
			return type;
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
		public void setValue(final String value) {
			throw new RuntimeException("Not implemented");
		}

	}

	protected final NumericValue minLength;
	protected final NumericValue maxLength;

	public StringTypeMock(final DataPkg pkg, final GeneralizableElement parentType, final String name,
			final String id) {
		super(pkg, parentType, name, id);
		minLength = null;
		maxLength = null;
	}

	public StringTypeMock(final DataPkg pkg, final GeneralizableElement parentType, final String name, final String id,
			final String description) {
		super(pkg, parentType, name, id, description);
		minLength = null;
		maxLength = null;
	}

	public StringTypeMock(final DataPkg pkg, final String name, final String id) {
		super(pkg, name, id);
		minLength = null;
		maxLength = null;
	}

	public StringTypeMock(final DataPkg pkg, final String name, final String id, final int min, final int max) {
		super(pkg, name, id);
		minLength = new NumericTypeMock.NumericValueMock(pkg, name + "_min", name + "_min_id", null,
				String.valueOf(min));
		maxLength = new NumericTypeMock.NumericValueMock(pkg, name + "_max", name + "_max_id", null,
				String.valueOf(max));
	}

	@Override
	public AbstractStringValue getOwnedDefaultValue() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public NumericValue getOwnedMaxLength() {
		return maxLength;
	}

	@Override
	public NumericValue getOwnedMinLength() {
		return minLength;
	}

	@Override
	public AbstractStringValue getOwnedNullValue() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void setOwnedDefaultValue(final AbstractStringValue value) {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void setOwnedMaxLength(final NumericValue value) {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void setOwnedMinLength(final NumericValue value) {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void setOwnedNullValue(final AbstractStringValue value) {
		throw new RuntimeException("Not implemented");
	}

}
