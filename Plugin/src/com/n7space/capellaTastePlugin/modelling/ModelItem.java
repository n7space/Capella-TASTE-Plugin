// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling;

import java.nio.ByteBuffer;

/**
 * Class representing a generated ASN.1 or AADL model item.
 */
public class ModelItem {
  /**
   * Enumeration listing possible types of the model item.
   */
  public static enum Kind {
    /**
     * ASN.1 data model
     */
    ASN1,
    /**
     * AADL architecture model
     */
    AADL
  }

  /**
   * Model item name.
   */
  public final String name;
  /**
   * Model item type.
   */
  public final Kind kind;
  /**
   * Buffer holding the generated model.
   */
  public final ByteBuffer buffer;

  /**
   * The constructor.
   * 
   * @param itemName
   *          Model item name
   * @param itemKind
   *          Model item type
   * @param itemBuffer
   *          Model item data
   */
  public ModelItem(final String itemName, final Kind itemKind, final ByteBuffer itemBuffer) {
    name = itemName;
    kind = itemKind;
    buffer = itemBuffer;
  }
}
