// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Implementation of a model serializer. Serializes the given set of model items to a set of files.
 */
public class FileSystemModelSerializer implements ModelSerializer {

  protected String getExtension(final ModelItem.Kind kind) {
    switch (kind) {
    case AADL:
      return "aadl";
    case ASN1:
      return "asn";
    default:
      return "";
    }
  }

  /**
   * Implementation of the ModelSerializer interface. Serializes the given set of model items to a set of files.
   * 
   * @see ModelSerializer
   * @param path
   *          Path to the directory under which the model items are to be serialized
   * @param items
   *          Model items
   */
  @Override
  public void serialize(final String path, final ModelItems items) throws IOException {
    final File folder = new File(path);
    if (!folder.exists())
      folder.mkdirs();
    for (final ModelItem item : items) {
      final File file = new File(path + File.separator + item.name + "." + getExtension(item.kind));
      if (file.exists())
        file.delete();
      final FileOutputStream os = new FileOutputStream(file);
      try {
        os.write(item.buffer.array());
        os.flush();
      } finally {
        os.close();
      }

    }
  }

}
