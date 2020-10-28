// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling;

import java.io.IOException;

/**
 * Interface for model serialization.
 */
public interface ModelSerializer {

  /**
   * Serializes the given set of model items under the given path.
   * 
   * @see ModelSerializer
   * @param path
   *          Path under which the model items are to be serialized
   * @param items
   *          Model items
   * @throws IOException
   *           upon serialization issue
   */
  void serialize(final String path, final ModelItems items) throws IOException;

}