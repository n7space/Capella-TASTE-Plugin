// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.polarsys.capella.common.data.modellingcore.AbstractType;
import org.polarsys.capella.core.data.capellacore.Type;
import org.polarsys.capella.core.data.information.DataPkg;
import org.polarsys.capella.core.data.information.datatype.BooleanType;
import org.polarsys.capella.core.data.information.datavalue.AbstractBooleanValue;
import org.polarsys.capella.core.data.information.datavalue.LiteralBooleanValue;

public class BooleanTypeMock extends BaseTypeMock implements BooleanType {

	public static class LiteralBooleanValueMock extends BaseTypeMock implements LiteralBooleanValue {

		protected BooleanTypeMock mockType;
		protected boolean mockValue;

		public LiteralBooleanValueMock(final DataPkg pkg, final BooleanTypeMock type, final String name,
				final String id, final boolean value) {
			super(pkg, name, id);
			mockType = type;
			mockValue = value;
		}

		@Override
		public AbstractType getAbstractType() {
			return mockType;
		}

		@Override
		public BooleanType getBooleanType() {
			return mockType;
		}

		@Override
		public Type getType() {
			return mockType;
		}

		@Override
		public boolean isValue() {
			return mockValue;
		}

		@Override
		public void setAbstractType(final AbstractType value) {
			// This is a stub for tests; substitution of an incorrect type value will result
			// in an exception.
			mockType = (BooleanTypeMock) value;
		}

		@Override
		public void setValue(final boolean value) {
			mockValue = value;
		}

	}

	protected List<LiteralBooleanValueMock> mockValues = new LinkedList<BooleanTypeMock.LiteralBooleanValueMock>();
	protected LiteralBooleanValueMock mockDefaultValue = null;

	public BooleanTypeMock(final DataPkg pkg, final String name, final String id) {
		super(pkg, name, id);
	}

	public void addMockValue(final LiteralBooleanValueMock value) {
		mockValues.add(value);
	}

	@Override
	public AbstractBooleanValue getOwnedDefaultValue() {
		return mockDefaultValue;
	}

	@Override
	public EList<LiteralBooleanValue> getOwnedLiterals() {
		final BasicEList<LiteralBooleanValue> result = new BasicEList<LiteralBooleanValue>();
		for (final LiteralBooleanValue value : mockValues) {
			result.add(value);
		}
		return result;

	}

	@Override
	public void setOwnedDefaultValue(final AbstractBooleanValue value) {
		mockDefaultValue = (LiteralBooleanValueMock) value;

	}
}
