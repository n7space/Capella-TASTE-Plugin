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
import org.polarsys.capella.core.data.cs.Interface;
import org.polarsys.capella.core.data.information.DataPkg;
import org.polarsys.capella.core.data.information.ElementKind;
import org.polarsys.capella.core.data.information.ExchangeItem;
import org.polarsys.capella.core.data.information.ExchangeItemElement;
import org.polarsys.capella.core.data.information.ExchangeItemInstance;
import org.polarsys.capella.core.data.information.ExchangeMechanism;
import org.polarsys.capella.core.data.information.Operation;
import org.polarsys.capella.core.data.information.ParameterDirection;
import org.polarsys.capella.core.data.information.Property;
import org.polarsys.capella.core.data.information.datatype.DataType;
import org.polarsys.capella.core.data.information.datavalue.DataValue;
import org.polarsys.capella.core.data.information.datavalue.NumericValue;

public class ExchangeItemTypeMock extends BaseTypeMock implements ExchangeItem {

	public static class ExchangeItemElementMock extends BaseTypeMock implements ExchangeItemElement {

		protected final boolean isOrdered;
		protected final DataType type;

		protected final NumericValue min;
		protected final NumericValue max;

		protected final ParameterDirection direction;

		public ExchangeItemElementMock(final DataPkg pkg, final String name, final String id,
				final DataType parentType) {
			super(pkg, name, id);
			type = parentType;
			min = null;
			max = null;
			isOrdered = false;
			direction = ParameterDirection.UNSET;
		}

		public ExchangeItemElementMock(final DataPkg pkg, final String name, final String id, final DataType parentType,
				final ParameterDirection parameterDirection) {
			super(pkg, name, id);
			type = parentType;
			min = null;
			max = null;
			isOrdered = false;
			direction = parameterDirection;
		}

		@Override
		public AbstractType getAbstractType() {
			return type;
		}

		@Override
		public ParameterDirection getDirection() {
			return direction;
		}

		@Override
		public ElementKind getKind() {
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
		public EList<Property> getReferencedProperties() {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public Type getType() {
			return type;
		}

		@Override
		public boolean isComposite() {
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
		public void setComposite(final boolean value) {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public void setDirection(final ParameterDirection value) {
			throw new RuntimeException("Not implemented");
		}

		@Override
		public void setKind(final ElementKind value) {
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

	protected final ExchangeMechanism mechanism;
	protected final EList<ExchangeItemElement> elements = new BasicEList<ExchangeItemElement>();

	public ExchangeItemTypeMock(final DataPkg pkg, final String name, final String id,
			final ExchangeMechanism exchangeMechanism) {
		super(pkg, name, id);
		mechanism = exchangeMechanism;
	}

	public void addElement(final ExchangeItemElement element) {
		elements.add(element);
	}

	@Override
	public EList<Interface> getAllocatorInterfaces() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public ExchangeMechanism getExchangeMechanism() {
		return mechanism;
	}

	@Override
	public EList<ExchangeItemElement> getOwnedElements() {
		return elements;
	}

	@Override
	public EList<ExchangeItemInstance> getOwnedExchangeItemInstances() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public EList<ExchangeItem> getRealizedExchangeItems() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public EList<ExchangeItem> getRealizingExchangeItems() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public EList<Operation> getRealizingOperations() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void setExchangeMechanism(final ExchangeMechanism value) {
		throw new RuntimeException("Not implemented");
	}

}
