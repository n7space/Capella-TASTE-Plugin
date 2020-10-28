// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders;

import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.polarsys.capella.core.data.capellacore.NamedElement;

import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataModelElement;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.utils.ConfigurableObject;
import com.n7space.capellatasteplugin.utils.Issue;
import com.n7space.capellatasteplugin.utils.OptionsHelper;
import com.n7space.capellatasteplugin.utils.StackTraceUtilities;

/**
 * Container class which manages a set of individual data model element
 * interpretation providers.
 */
public class DataModelElementInterpreter implements ConfigurableObject {

	/**
	 * Interface declaration for data model element interpretation providers.
	 */
	public interface DataModelElementInterpretationProvider {
		/**
		 * Provides interpretation for the given Capella data model element.
		 * 
		 * @param dataModel
		 *            The internal data model
		 * @param currentDataPackage
		 *            The current internal data package
		 * @param context
		 *            The current interpretation context
		 * @param interpretedElement
		 *            The element to be interpreted
		 * @param issues
		 *            [output] List of detected issues
		 * @return The element converted to the internal data model or null if the
		 *         interpretation failed
		 */
		DataModelElement interpretModelElement(final DataModel dataModel, final DataPackage currentDataPackage,
				final Deque<DataModelElement> context, final NamedElement interpretedElement, final List<Issue> issues);
	}

	protected Option[] optionCache = new Option[0];
	protected final Map<Class<?>, DataModelElementInterpretationProvider> dataModelElementInterpretationProviders = new HashMap<Class<?>, DataModelElementInterpretationProvider>();

	/**
	 * Returns the options supported by the managed interpretation providers.
	 * 
	 * @return List of options
	 */
	@Override
	public Option[] getOptions() {
		Option[] options = new Option[0];
		options = OptionsHelper.addOptions(optionCache, new Option[0]);
		for (final Class<?> key : dataModelElementInterpretationProviders.keySet()) {
			final DataModelElementInterpretationProvider provider = dataModelElementInterpretationProviders.get(key);
			if (provider instanceof ConfigurableObject) {
				options = OptionsHelper.addOptions(options, ((ConfigurableObject) provider).getOptions());
			}
		}
		return options;
	}

	/**
	 * Returns the value of the option indicated by the given handle.
	 * 
	 * @param optionHandle
	 *            The given handle
	 * @return The option value or null if none is found
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
	 * Provides interpretation for the given Capella data model element.
	 * 
	 * @param dataModel
	 *            The internal data model
	 * @param currentDataPackage
	 *            The current internal data package
	 * @param context
	 *            The current interpretation context
	 * @param interpretedElement
	 *            The element to be interpreted
	 * @param issues
	 *            [output] List of detected issues
	 * @return The element converted to the internal data model or null if the
	 *         interpretation failed
	 */
	public DataModelElement interpretDataModelElement(final DataModel dataModel, final DataPackage currentDataPackage,
			final Deque<DataModelElement> context, final NamedElement interpretedElement, final List<Issue> issues) {
		try {
			final Class<?> clazz = interpretedElement.getClass();
			for (final Class<?> key : dataModelElementInterpretationProviders.keySet()) {
				if (key.isInstance(interpretedElement)) {
					final DataModelElement element = dataModelElementInterpretationProviders.get(key)
							.interpretModelElement(dataModel, currentDataPackage, context, interpretedElement, issues);
					return element;
				}
			}
			issues.add(new Issue(Issue.Kind.Error, "No interpreter registered for " + clazz.getSimpleName(), clazz));
		} catch (final Throwable t) {
			issues.add(new Issue(
					Issue.Kind.Error, "Critical error while trying to interpret " + interpretedElement.getName() + ": "
							+ t.toString() + "@" + StackTraceUtilities.getTrimmedStackTraceString(t, 3),
					interpretedElement));
		}
		return null;
	}

	/**
	 * Registers an element interpretation provider for the given Capella data model
	 * element class.
	 * 
	 * @param cls
	 *            Capella data model element class
	 * @param provider
	 *            The provider to be registered
	 */
	public void registerInterpretationProvider(final Class<?> cls,
			final DataModelElementInterpretationProvider provider) {
		dataModelElementInterpretationProviders.put(cls, provider);
	}

	/**
	 * Sets the options for the managed providers.
	 * 
	 * @param options
	 *            Options
	 */
	@Override
	public void setOptions(final Option[] options) {
		optionCache = OptionsHelper.addOptions(optionCache, options);
		for (final Class<?> key : dataModelElementInterpretationProviders.keySet()) {
			final DataModelElementInterpretationProvider provider = dataModelElementInterpretationProviders.get(key);
			if (provider instanceof ConfigurableObject) {
				((ConfigurableObject) provider).setOptions(options);
			}
		}
	}

	/**
	 * Sets - for all the managed providers - the option value for the given option
	 * handle.
	 * 
	 * @param optionHandle
	 *            Option handle
	 * @param value
	 *            Option value
	 */
	@Override
	public void setOptionValue(final Object optionHandle, final Object value) {
		for (final Option option : optionCache) {
			if (option.handle.equals(optionHandle)) {
				option.setValue(value);
			}
		}
		for (final Class<?> key : dataModelElementInterpretationProviders.keySet()) {
			final DataModelElementInterpretationProvider provider = dataModelElementInterpretationProviders.get(key);
			if (provider instanceof ConfigurableObject) {
				((ConfigurableObject) provider).setOptionValue(optionHandle, value);
			}
		}

	}

}
