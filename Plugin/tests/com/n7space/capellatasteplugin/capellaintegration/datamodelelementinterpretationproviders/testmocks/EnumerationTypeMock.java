// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.polarsys.capella.common.data.modellingcore.AbstractType;
import org.polarsys.capella.core.data.capellacore.Type;
import org.polarsys.capella.core.data.information.DataPkg;
import org.polarsys.capella.core.data.information.datatype.DataType;
import org.polarsys.capella.core.data.information.datatype.Enumeration;
import org.polarsys.capella.core.data.information.datavalue.AbstractEnumerationValue;
import org.polarsys.capella.core.data.information.datavalue.DataValue;
import org.polarsys.capella.core.data.information.datavalue.EnumerationLiteral;

public class EnumerationTypeMock extends BaseTypeMock
		implements org.polarsys.capella.core.data.information.datatype.Enumeration {

	public static class EnumerationValueMock extends BaseTypeMock implements EnumerationLiteral {

		protected final Enumeration type;
		protected final DataValue value;

		public EnumerationValueMock(final DataPkg pkg, final String name, final String id,
				final Enumeration enumerationType, final DataValue enumerationValue) {
			super(pkg, name, id);
			type = enumerationType;
			value = enumerationValue;
		}

		@Override
		public AbstractType getAbstractType() {
			return type;
		}

		@Override
		public DataValue getDomainValue() {
			return value;
		}

		@Override
		public Enumeration getEnumerationType() {
			return type;
		}

		@Override
		public Type getType() {
			return type;
		}

		@Override
		public void setAbstractType(final AbstractType value) {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public void setDomainValue(final DataValue value) {
			throw new RuntimeException("Not implemented");
		}

	}

	protected EList<EnumerationLiteral> literals = new BasicEList<EnumerationLiteral>();

	public EnumerationTypeMock(final DataPkg pkg, final String name, final String id) {
		super(pkg, name, id);
	}

	public void addLiteral(final EnumerationLiteral literal) {
		literals.add(literal);
	}

	@Override
	public DataType getDomainType() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public AbstractEnumerationValue getOwnedDefaultValue() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public EList<EnumerationLiteral> getOwnedLiterals() {
		return literals;
	}

	@Override
	public AbstractEnumerationValue getOwnedMaxValue() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public AbstractEnumerationValue getOwnedMinValue() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public AbstractEnumerationValue getOwnedNullValue() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void setDomainType(final DataType value) {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void setOwnedDefaultValue(final AbstractEnumerationValue value) {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void setOwnedMaxValue(final AbstractEnumerationValue value) {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void setOwnedMinValue(final AbstractEnumerationValue value) {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void setOwnedNullValue(final AbstractEnumerationValue value) {
		throw new RuntimeException("Not implemented");
	}

}
