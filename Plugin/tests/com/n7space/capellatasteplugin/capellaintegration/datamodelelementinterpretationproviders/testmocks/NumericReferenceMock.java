// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks;

import org.polarsys.capella.common.data.modellingcore.AbstractType;
import org.polarsys.capella.core.data.capellacore.Type;
import org.polarsys.capella.core.data.information.DataPkg;
import org.polarsys.capella.core.data.information.Property;
import org.polarsys.capella.core.data.information.Unit;
import org.polarsys.capella.core.data.information.datatype.NumericType;
import org.polarsys.capella.core.data.information.datavalue.NumericReference;
import org.polarsys.capella.core.data.information.datavalue.NumericValue;

public class NumericReferenceMock extends BaseTypeMock implements NumericReference {

	protected final NumericValue referencedValue;

	public NumericReferenceMock(final DataPkg pkg, final String name, final String id,
			final NumericValue valueThatIsReferenced) {
		super(pkg, name, id);
		referencedValue = valueThatIsReferenced;
	}

	@Override
	public AbstractType getAbstractType() {
		return referencedValue.getAbstractType();
	}

	@Override
	public NumericType getNumericType() {
		return referencedValue.getNumericType();
	}

	@Override
	public Property getReferencedProperty() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public NumericValue getReferencedValue() {
		return referencedValue;
	}

	@Override
	public Type getType() {
		return referencedValue.getType();
	}

	@Override
	public Unit getUnit() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void setAbstractType(final AbstractType value) {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void setReferencedProperty(final Property value) {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void setReferencedValue(final NumericValue value) {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public void setUnit(final Unit value) {
		throw new RuntimeException("Not implemented");
	}

}
