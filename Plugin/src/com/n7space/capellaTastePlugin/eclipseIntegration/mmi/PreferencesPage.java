// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.eclipseintegration.mmi;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.StringFieldEditor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

import com.n7space.capellatasteplugin.capellaintegration.Coordinator;
import com.n7space.capellatasteplugin.eclipseintegration.SettingsProvider;
import com.n7space.capellatasteplugin.utils.ConfigurableObject.Option;
import com.n7space.capellatasteplugin.utils.OptionsHelper;

/**
 * Plugin preferences page integrated with the Eclipse environment.
 */
public class PreferencesPage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

	protected class OptionSorter implements Comparator<Option> {
		@Override
		public int compare(final Option optionA, final Option optionB) {
			return optionA.handle.toString().compareToIgnoreCase(optionB.handle.toString());
		}
	}

	protected final List<StringFieldEditor> stringEditors = new LinkedList<StringFieldEditor>();

	/**
	 * The constructor.
	 */
	public PreferencesPage() {
		super(GRID);
	}

	@Override
	protected void createFieldEditors() {
		final Option[] options = Coordinator.getInstance().getOptions();

		Arrays.sort(options, new OptionSorter());
		stringEditors.clear();

		for (final Option option : options) {
			if (option.getValue() instanceof String) {
				final StringFieldEditor editor = new StringFieldEditor(option.handle.toString(),
						option.description + ":", getFieldEditorParent());
				editor.setEmptyStringAllowed(option.canBeEmpty);
				stringEditors.add(editor);
				addField(editor);
			}
			if (option.getValue() instanceof Boolean) {
				addField(new BooleanFieldEditor(option.handle.toString(), option.description, getFieldEditorParent()));
			}
		}
	}

	/**
	 * Initializes the page.
	 * 
	 * @param workbench
	 *            Workbench {@inheritDoc}
	 */
	@Override
	public void init(final IWorkbench workbench) {
		setPreferenceStore(
				new ScopedPreferenceStore(ConfigurationScope.INSTANCE, SettingsProvider.PREFERENCES_NODE_NAME));
		setDescription("Preferences for Capella-TASTE integration plugin");

	}

	/**
	 * Handles preferences acceptance. {@inheritDoc}
	 * 
	 * @return Whether the OK processing completed successfully
	 */
	@Override
	public boolean performOk() {
		final Option[] options = Coordinator.getInstance().getOptions();
		for (final StringFieldEditor editor : stringEditors) {
			final String optionName = editor.getPreferenceName();
			// Run the option value through internal options constraints.
			OptionsHelper.setOptionValue(options, optionName, editor.getStringValue());
			editor.setStringValue((String) OptionsHelper.getOptionValue(options, optionName));
		}
		return super.performOk();
	}

}
