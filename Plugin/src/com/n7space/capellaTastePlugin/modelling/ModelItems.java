// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Convenience class for aggregating a set of generated model items.
 */
public class ModelItems implements Collection<ModelItem> {

  protected final List<ModelItem> items = new LinkedList<ModelItem>();

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean add(final ModelItem e) {
    return items.add(e);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean addAll(final Collection<? extends ModelItem> c) {
    return items.addAll(c);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void clear() {
    items.clear();

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean contains(final Object o) {
    return items.contains(o);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean containsAll(final Collection<?> c) {
    return items.containsAll(c);
  }

  /**
   * Returns the model item stored under the given index.
   * 
   * @param index
   *          Index
   * @return Model item
   */
  public ModelItem get(final int index) {
    return items.get(index);
  }

  /**
   * Returns the model item indicated by the given name.
   * 
   * @param name
   *          Model item name
   * @return Model item or null if none is found
   */
  public ModelItem getItemByName(final String name) {
    for (final ModelItem item : items) {
      if (item.name.equals(name)) {
        return item;
      }
    }
    return null;
  }

  /**
   * Returns all model items of the given type.
   * 
   * @param kind
   *          The desired type
   * @return List of model items of the desired type
   */
  public List<ModelItem> getItemsOfType(final ModelItem.Kind kind) {
    final List<ModelItem> result = new LinkedList<ModelItem>();
    for (final ModelItem item : items) {
      if (item.kind == kind) {
        result.add(item);
      }
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isEmpty() {
    return items.isEmpty();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Iterator<ModelItem> iterator() {
    return items.iterator();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean remove(final Object o) {
    return items.remove(o);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeAll(final Collection<?> c) {
    return items.removeAll(c);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean retainAll(final Collection<?> c) {
    return items.retainAll(c);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int size() {
    return items.size();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Object[] toArray() {
    return items.toArray();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <T> T[] toArray(final T[] a) {
    return items.toArray(a);
  }

}
