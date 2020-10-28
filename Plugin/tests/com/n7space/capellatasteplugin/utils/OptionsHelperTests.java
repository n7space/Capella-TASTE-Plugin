// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.n7space.capellatasteplugin.utils.ConfigurableObject.Option;

public class OptionsHelperTests {

	protected final String handle1 = "handle1";
	protected final String handle2 = "handle2";
	protected final String handle3 = "handle3";
	protected final String handle4 = "handle4";
	protected final Integer value1 = Integer.valueOf(1);
	protected final Integer value2 = Integer.valueOf(2);
	protected final Integer value3 = Integer.valueOf(3);
	protected final Integer value4 = Integer.valueOf(4);

	protected Option option1;
	protected Option option2;
	protected Option option3;
	protected Option option4;
	protected Option[] optionsA;
	protected Option[] optionsB;

	@Before
	public void setUp() throws Exception {
		option1 = new Option(handle1, "Option1", value1);
		option2 = new Option(handle2, "Option2", value2);
		option3 = new Option(handle3, "Option3", value3);
		option4 = new Option(handle4, "Option4", value4);

		optionsA = new Option[2];
		optionsA[0] = option1;
		optionsA[1] = option2;

		optionsB = new Option[2];
		optionsB[0] = option3;
		optionsB[1] = option4;
	}

	protected boolean isOptionPresent(final Option[] options, final Object handle) {
		for (int i = 0; i < options.length; i++) {
			if (options[i].handle.equals(handle)) {
				return true;
			}
		}
		return false;
	}

	@Test
	public void testAddOptions() {
		final Option[] result = OptionsHelper.addOptions(optionsA, optionsB);
		assertEquals(result.length, optionsA.length + optionsB.length);
		assertTrue(isOptionPresent(result, handle1));
		assertTrue(isOptionPresent(result, handle2));
		assertTrue(isOptionPresent(result, handle3));
		assertTrue(isOptionPresent(result, handle4));
	}

	@Test
	public void testConvert() {
		final Set<Option> source = new HashSet<ConfigurableObject.Option>();
		source.add(option1);
		source.add(option3);
		final Option[] result = OptionsHelper.convert(source);
		assertTrue(isOptionPresent(result, handle1));
		assertTrue(isOptionPresent(result, handle3));
	}

	@Test
	public void testGetOptionValue() {
		assertEquals(OptionsHelper.getOptionValue(optionsA, handle1), value1);
		assertEquals(OptionsHelper.getOptionValue(optionsA, handle2), value2);
		// Not found.
		assertEquals(OptionsHelper.getOptionValue(optionsA, handle3), null);
	}

	@Test
	public void testSetOptionValue() {
		final Integer newValue = Integer.valueOf(1969);
		OptionsHelper.setOptionValue(optionsA, handle1, newValue);
		assertEquals(option1.getValue(), newValue);
	}

}
