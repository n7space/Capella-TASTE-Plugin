// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.eclipseintegration.mmi;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.CheckedTreeSelectionDialog;

import com.n7space.capellatasteplugin.capellaintegration.mmi.DataModelBrowser;
import com.n7space.capellatasteplugin.capellaintegration.mmi.PresentationFeedback;
import com.n7space.capellatasteplugin.modelling.data.BooleanDataType;
import com.n7space.capellatasteplugin.modelling.data.CollectionDataType;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataModelElement;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.modelling.data.DataType;
import com.n7space.capellatasteplugin.modelling.data.DataType.DataTypeValue;
import com.n7space.capellatasteplugin.modelling.data.EnumeratedDataType;
import com.n7space.capellatasteplugin.modelling.data.FloatDataType;
import com.n7space.capellatasteplugin.modelling.data.IntegerDataType;
import com.n7space.capellatasteplugin.modelling.data.StringDataType;
import com.n7space.capellatasteplugin.modelling.data.StructuredDataType;
import com.n7space.capellatasteplugin.utils.ImageUtils;

/**
 * Implementation of a data model browser using GUI primitives supplied by the
 * Eclipse environment.
 *
 */
public class GraphicalDataModelBrowser implements DataModelBrowser {

	/**
	 * Content provider for UI controls. Translates the data model into elements
	 * that can be visualized.
	 *
	 */
	protected static class DataModelContentProvider implements ITreeContentProvider {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public Object[] getChildren(final Object parentElement) {
			return DataModelElementExtractor.getChildElements(parentElement);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Object[] getElements(final Object inputElement) {
			return DataModelElementExtractor.getTopLevelElements((DataModel) inputElement);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Object getParent(final Object element) {
			return DataModelElementExtractor.getParentElement(element);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean hasChildren(final Object element) {
			return element instanceof DataPackage;
		}

	}

	/**
	 * Utility class for accessing desired elements of the data model.
	 *
	 */
	protected static class DataModelElementExtractor {
		/**
		 * Returns the list of all data model elements that are relevant to the data
		 * model browser.
		 *
		 * @param model
		 *            Data model
		 * @return The list of data model elements
		 */
		public static Object[] getAllElements(final DataModel model) {
			final Object[] topLevelElements = getTopLevelElements(model);
			final List<Object> all = new LinkedList<Object>();
			for (final Object parent : topLevelElements) {
				all.add(parent);
				final Object[] children = getChildElements(parent);
				for (final Object child : children) {
					all.add(child);
				}
			}
			return all.toArray();
		}

		/**
		 * Returns the list of the given element's children that are relevant to the
		 * data model browser.
		 *
		 * @param parentElement
		 *            The queried parent element
		 * @return The list of parent's children (may be empty)
		 */
		public static Object[] getChildElements(final Object parentElement) {
			if (parentElement instanceof DataPackage) {
				final DataPackage pkg = (DataPackage) parentElement;
				final List<DataType> definedDataTypes = pkg.getDefinedTypes();
				final List<DataType.DataTypeValue> definedValues = pkg.getDefinedValues();
				final Object[] result = new Object[definedDataTypes.size() + definedValues.size()];
				int index = 0;
				for (final DataType dataType : definedDataTypes)
					result[index++] = dataType;
				for (final DataTypeValue value : definedValues)
					result[index++] = value;
				return result;
			}
			return new Object[] {};
		}

		/**
		 * Returns the given element's parent.
		 *
		 * @param element
		 *            The queried child element
		 * @return The child's parent or null if there is none
		 */
		public static Object getParentElement(final Object element) {
			if (element instanceof DataTypeValue) {
				final DataTypeValue value = (DataTypeValue) element;
				return value.dataPackage;
			} else if (element instanceof DataType) {
				final DataType dataType = (DataType) element;
				return dataType.dataPackage;
			}
			return null;
		}

		/**
		 * Returns a list of packages contained in the data model which contain some
		 * defined elements.
		 *
		 * @param model
		 *            Data model to extract the packages from
		 * @return A list of packages
		 */
		public static Object[] getTopLevelElements(final DataModel model) {
			final List<DataPackage> dataPackages = model.dataPackages;
			final List<DataPackage> filteredPackages = new LinkedList<DataPackage>();
			for (final DataPackage pkg : dataPackages) {
				// Let's present only the packages that actually define something.
				if (pkg.getDefinedElements().size() > 0) {
					filteredPackages.add(pkg);
				}
			}
			return filteredPackages.toArray();
		}
	}

	/**
	 * Label provider for data model elements. Provides textual and graphical labels
	 * for data model elements.
	 */
	protected static class DataModelElementLabelProvider extends LabelProvider {
		protected static final int ICON_SIZE = 16;
		protected static final String PACKAGE_ICON_NAME = "package.png";
		protected static final String INTEGER_ICON_NAME = "integer.png";
		protected static final String FLOAT_ICON_NAME = "real.png";
		protected static final String STRUCTURED_ICON_NAME = "sequence.png";
		protected static final String COLLECTION_ICON_NAME = "sequenceof.png";
		protected static final String BOOLEAN_ICON_NAME = "boolean.png";
		protected static final String ENUMERATED_ICON_NAME = "enumerated.png";
		protected static final String STRING_ICON_NAME = "ia5string.png";
		protected static final String OTHER_ICON_NAME = "userdefined.png";

		protected Map<Class<?>, String> iconNames = new HashMap<Class<?>, String>();

		/**
		 * The constructor.
		 */
		public DataModelElementLabelProvider() {
			iconNames.put(DataPackage.class, PACKAGE_ICON_NAME);
			iconNames.put(BooleanDataType.class, BOOLEAN_ICON_NAME);
			iconNames.put(CollectionDataType.class, COLLECTION_ICON_NAME);
			iconNames.put(DataType.DataTypeValue.class, OTHER_ICON_NAME);
			iconNames.put(EnumeratedDataType.class, ENUMERATED_ICON_NAME);
			iconNames.put(FloatDataType.class, FLOAT_ICON_NAME);
			iconNames.put(IntegerDataType.class, INTEGER_ICON_NAME);
			iconNames.put(StringDataType.class, STRING_ICON_NAME);
			iconNames.put(StructuredDataType.class, STRUCTURED_ICON_NAME);
		}

		/**
		 * Returns an image which represents the given data model element.
		 *
		 * @param element
		 *            Data model element
		 * @return The corresponding image, or a default if none is registered
		 */
		@Override
		public Image getImage(final Object element) {
			final String iconPath = iconNames.get(element.getClass());
			return ImageUtils.getScaledIcon(this, Display.getCurrent(), iconPath != null ? iconPath : OTHER_ICON_NAME,
					ICON_SIZE);
		}

		/**
		 * Returns text which represents the given data model element.
		 *
		 * @param element
		 *            Data model element
		 * @return The corresponding text
		 */
		@Override
		public String getText(final Object element) {
			if (element instanceof DataModelElement) {
				final DataModelElement me = (DataModelElement) element;
				return me.name;
			}
			return super.getText(element);
		}
	}

	/**
	 * Implementation of the DataModelBrowser interface. Presents the data model to
	 * the user, allowing for a subset selection. Invokes callback upon interaction
	 * completion.
	 *
	 * @see DataModelBrowser
	 * @param dataModel
	 *            Data model to be presented
	 * @param callback
	 *            Callback to be invoked upon interaction completion
	 */
	@Override
	public void captureDataModelSubsetSelection(final DataModel dataModel,
			final DataModelPresentationCallback callback) {
		final CheckedTreeSelectionDialog dialog = new CheckedTreeSelectionDialog(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), new DataModelElementLabelProvider(),
				new DataModelContentProvider());
		dialog.setTitle("Data Model subset selection");
		dialog.setImage(Display.getCurrent().getSystemImage(SWT.ICON_QUESTION));
		dialog.setMessage("Please select the Data Model elements to be described in ASN.1:");
		dialog.setInput(dataModel);
		dialog.setContainerMode(true);
		dialog.setInitialSelections(DataModelElementExtractor.getAllElements(dataModel));

		if (dialog.open() == Window.OK)
			callback.onDataModelPresentationCallback(dataModel, retrieveSelectionFromDialog(dialog),
					PresentationFeedback.Accepted);
		else
			callback.onDataModelPresentationCallback(dataModel, new LinkedList<DataModelElement>(),
					PresentationFeedback.Rejected);
	}

	protected List<DataModelElement> retrieveSelectionFromDialog(final CheckedTreeSelectionDialog dialog) {
		final List<DataModelElement> result = new LinkedList<DataModelElement>();
		final Object[] selection = dialog.getResult();
		for (final Object item : selection) {
			if (item instanceof DataModelElement)
				result.add((DataModelElement) item);
		}
		return result;
	}

}
