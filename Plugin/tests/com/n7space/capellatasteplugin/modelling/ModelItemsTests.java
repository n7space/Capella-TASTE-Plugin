// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.n7space.capellatasteplugin.modelling.ModelItem.Kind;

public class ModelItemsTests {

	protected ModelItems items = null;

	@Before
	public void setUp() {
		items = new ModelItems();
		final ModelItem a = new ModelItem("a", ModelItem.Kind.AADL, null);
		final ModelItem b = new ModelItem("b", ModelItem.Kind.AADL, null);
		final ModelItem c = new ModelItem("c", ModelItem.Kind.ASN1, null);
		items.items.add(a);
		items.items.add(b);
		items.items.add(c);
	}

	@Test
	public void testGetItemByName() {
		assertEquals(items.getItemByName("a").name, "a");
		assertEquals(items.getItemByName("b").name, "b");
		assertEquals(items.getItemByName("c").name, "c");
	}

	@Test
	public void testGetItemsOfType() {
		final List<ModelItem> aadlItems = items.getItemsOfType(ModelItem.Kind.AADL);
		assertTrue(aadlItems.size() > 0);
		for (final ModelItem item : aadlItems) {
			assertEquals(item.kind, ModelItem.Kind.AADL);
		}
		final List<ModelItem> asn1Items = items.getItemsOfType(ModelItem.Kind.ASN1);
		assertTrue(asn1Items.size() > 0);
		for (final ModelItem item : asn1Items) {
			assertEquals(item.kind, ModelItem.Kind.ASN1);
		}

	}

	@Test
	public void testCollectionTraits() {
		final ModelItems items = new ModelItems();
		final ModelItem item1 = new ModelItem("a", Kind.AADL, null);
		final ModelItem item2 = new ModelItem("b", Kind.AADL, null);
		final List<ModelItem> itemsCollection = new LinkedList<ModelItem>();
		itemsCollection.add(item1);
		itemsCollection.add(item2);

		assertTrue(items.isEmpty());
		items.add(item1);
		assertFalse(items.isEmpty());
		assertTrue(items.contains(item1));
		assertFalse(items.contains(item2));
		items.clear();
		assertTrue(items.isEmpty());
		items.add(item1);
		assertFalse(items.containsAll(itemsCollection));
		items.add(item2);
		assertTrue(items.containsAll(itemsCollection));
	}
}
