// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling;

import java.util.HashMap;
import java.util.Map;

import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataModelElement;
import com.n7space.capellatasteplugin.utils.ConfigurableObject;
import com.n7space.capellatasteplugin.utils.OptionsHelper;

/**
 * ASN.1 definition generator for data model elements.
 */
public class Asn1ElementDefinitionGenerator implements ConfigurableObject {
  /**
   * Interface for ASN.1 definition providers.
   */
  public interface Asn1ElementDefinitionProvider {
    /**
     * Provides ASN.1 definition for the given data model element.
     * 
     * @param model
     *          Data model
     * @param element
     *          Data model element
     * @return ASN.1 definition
     */
    String provideAsn1Definition(DataModel model, DataModelElement element);
  }

  protected final Map<Class<?>, Asn1ElementDefinitionProvider> providers = new HashMap<Class<?>, Asn1ElementDefinitionGenerator.Asn1ElementDefinitionProvider>();

  /**
   * Generates ASN.1 definition for the given data model element.
   * 
   * @param model
   *          Data model
   * @param element
   *          Data model element
   * @return ASN.1 definition
   */
  public String generateAsn1Definition(final DataModel model, final DataModelElement element) {
    return providers.get(element.getClass()).provideAsn1Definition(model, element);
  }

  /**
   * Returns the list of supported options.
   * 
   * @return List of supported options
   */
  @Override
  public Option[] getOptions() {
    Option[] options = new Option[0];
    for (final Class<?> key : providers.keySet()) {
      final Asn1ElementDefinitionProvider provider = providers.get(key);
      if (provider instanceof ConfigurableObject) {
        options = OptionsHelper.addOptions(options, ((ConfigurableObject) provider).getOptions());
      }
    }
    return options;
  }

  /**
   * Returns the option value for the given handle.
   * 
   * @param optionHandle
   *          Option handle
   * @return Option value or null if none exists for the given handle
   */
  @Override
  public Object getOptionValue(final Object optionHandle) {
    final Option[] options = getOptions();
    for (final Option option : options) {
      if (option.handle.equals(optionHandle))
        return option.getValue();
    }
    return null;
  }

  /**
   * Registers ASN.1 element definition provider for the given element type.
   * 
   * @param cls
   *          Data model element type
   * @param provider
   *          ASN.1 definition provider
   */
  public void registerDefinitionProvider(final Class<?> cls, final Asn1ElementDefinitionProvider provider) {
    providers.put(cls, provider);
  }

  /**
   * Sets the option values.
   * 
   * @param options
   *          List of options
   */
  @Override
  public void setOptions(final Option[] options) {
    for (final Class<?> key : providers.keySet()) {
      final Asn1ElementDefinitionProvider provider = providers.get(key);
      if (provider instanceof ConfigurableObject) {
        ((ConfigurableObject) provider).setOptions(options);
      }
    }
  }

  /**
   * Sets the value of the given option.
   * 
   * @param optionHandle
   *          Option handle
   * @param value
   *          Option value
   */
  @Override
  public void setOptionValue(final Object optionHandle, final Object value) {
    for (final Class<?> key : providers.keySet()) {
      final Asn1ElementDefinitionProvider provider = providers.get(key);
      if (provider instanceof ConfigurableObject) {
        ((ConfigurableObject) provider).setOptionValue(optionHandle, value);
      }
    }

  }

}
