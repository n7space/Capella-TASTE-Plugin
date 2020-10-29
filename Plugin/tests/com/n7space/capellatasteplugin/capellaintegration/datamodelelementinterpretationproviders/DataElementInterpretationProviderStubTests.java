// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders;

import static org.junit.Assert.assertNull;

import org.junit.Test;

public class DataElementInterpretationProviderStubTests {

	@Test
	public void test() {
		final DataElementInterpretationProviderStub stub = new DataElementInterpretationProviderStub();
		// Provide coverage for a stub that does nothing.
		assertNull(stub.interpretModelElement(null, null, null, null, null));
	}

}
