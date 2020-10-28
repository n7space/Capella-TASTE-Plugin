// N7 Space Sp. z o.o.
// www.n7space.com
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

import com.n7space.capellatasteplugin.capellaintegration.mmi.PresentationFeedback;
import com.n7space.capellatasteplugin.capellaintegration.mmi.SystemModelBrowser;
import com.n7space.capellatasteplugin.modelling.architecture.ArchitectureElement;
import com.n7space.capellatasteplugin.modelling.architecture.Component;
import com.n7space.capellatasteplugin.modelling.architecture.Function;
import com.n7space.capellatasteplugin.modelling.architecture.SystemModel;
import com.n7space.capellatasteplugin.utils.ImageUtils;

/**
 * Implementation of a system model browser using GUI primitives supplied by the
 * Eclipse environment.
 *
 */
public class GraphicalSystemModelBrowser implements SystemModelBrowser {

	/**
	 * Content provider for UI controls. Translates the system model into elements
	 * that can be visualized.
	 *
	 */
	protected static class SystemModelContentProvider implements ITreeContentProvider {
		/**
		 * {@inheritDoc}
		 */
		@Override
		public Object[] getChildren(final Object parentElement) {
			if (parentElement instanceof Component) {
				final Component component = (Component) parentElement;
				final Object[] result = new Object[component.ownedComponents.size()
						+ component.deployedComponents.size() + component.allocatedFunctions.size()];
				int index = 0;
				for (final Component child : component.ownedComponents)
					result[index++] = child;

				for (final Component child : component.deployedComponents)
					result[index++] = child;

				for (final Function function : component.allocatedFunctions)
					result[index++] = function;
				return result;
			}
			return new Object[] {};
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Object[] getElements(final Object inputElement) {
			final SystemModel model = (SystemModel) inputElement;
			return model.topLevelComponents.toArray();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Object getParent(final Object element) {
			if (element instanceof SystemModel) {
				return null;
			}
			if (element instanceof ArchitectureElement) {
				return ((ArchitectureElement) element).getFirstParent();
			}
			return null;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public boolean hasChildren(final Object element) {
			return (element instanceof Component);
		}

	}

	/**
	 * Label provider for system model elements. Provides textual and graphical
	 * labels for model elements.
	 */
	protected static class SystemModelElementLabelProvider extends LabelProvider {
		protected static final int ICON_SIZE = 16;
		protected static final String NODE_ICON_NAME = "node.png";
		protected static final String COMPONENT_ICON_NAME = "component.png";
		protected static final String FUNCTION_ICON_NAME = "function.png";
		protected static final String OTHER_ICON_NAME = "userdefined.png";

		protected Map<String, String> iconNames = new HashMap<String, String>();

		/**
		 * The constructor.
		 */
		public SystemModelElementLabelProvider() {
		}

		protected String getIconPath(final Object element) {
			if (element instanceof Function)
				return FUNCTION_ICON_NAME;
			else if (element instanceof Component) {
				final Component component = (Component) element;
				if (component.deployedComponents.size() == 0)
					return COMPONENT_ICON_NAME;
				return NODE_ICON_NAME;
			}
			return null;
		}

		/**
		 * Returns an image which represents the given model element.
		 *
		 * @param element
		 *            Model element
		 * @return The corresponding image, or a default if none is registered
		 */
		@Override
		public Image getImage(final Object element) {
			final String iconPath = getIconPath(element);
			return ImageUtils.getScaledIcon(this, Display.getCurrent(), iconPath != null ? iconPath : OTHER_ICON_NAME,
					ICON_SIZE);
		}

		/**
		 * Returns text which represents the given model element.
		 *
		 * @param element
		 *            Model element
		 * @return The corresponding text
		 */
		@Override
		public String getText(final Object element) {
			if (element instanceof ArchitectureElement) {
				final ArchitectureElement me = (ArchitectureElement) element;
				return me.name;
			}
			return super.getText(element);
		}
	}

	/**
	 * Implementation of the SystemModelBrowser interface. Presents the system model
	 * to the user, allowing for a subset selection. Invokes callback upon
	 * interaction completion.
	 *
	 * @see SystemModelBrowser
	 * @param model
	 *            System model to be presented
	 * @param callback
	 *            Callback to be invoked upon interaction completion
	 */
	@Override
	public void captureSystemModelSubsetSelection(final SystemModel model,
			final SystemModelPresentationCallback callback) {
		final CheckedTreeSelectionDialog dialog = new CheckedTreeSelectionDialog(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), new SystemModelElementLabelProvider(),
				new SystemModelContentProvider());
		dialog.setTitle("System Model subset selection");
		dialog.setImage(Display.getCurrent().getSystemImage(SWT.ICON_QUESTION));
		dialog.setMessage("Please select the System Model elements to be described in AADL:");
		dialog.setInput(model);
		dialog.setContainerMode(true);
		dialog.setInitialSelections(getAllElements(model));
		if (dialog.open() == Window.OK)
			callback.onSystemModelPresentationCallback(model, retrieveSelectionFromDialog(dialog),
					PresentationFeedback.Accepted);
		else
			callback.onSystemModelPresentationCallback(model, new LinkedList<ArchitectureElement>(),
					PresentationFeedback.Rejected);
	}

	protected List<Object> getAllChildren(final ArchitectureElement element) {
		final List<Object> all = new LinkedList<Object>();
		if (element instanceof Component) {
			final Component component = (Component) element;
			for (final ArchitectureElement child : component.ownedComponents) {
				all.add(child);
				all.addAll(getAllChildren(child));
			}
		}
		return all;
	}

	protected Object[] getAllElements(final SystemModel model) {
		final Object[] topLevelElements = model.topLevelComponents.toArray();
		final List<Object> all = new LinkedList<Object>();
		for (final Object parent : topLevelElements) {
			all.add(parent);
			all.addAll(getAllChildren((ArchitectureElement) parent));
		}
		return all.toArray();
	}

	protected List<ArchitectureElement> retrieveSelectionFromDialog(final CheckedTreeSelectionDialog dialog) {
		final List<ArchitectureElement> result = new LinkedList<ArchitectureElement>();
		final Object[] selection = dialog.getResult();
		for (final Object item : selection) {
			if (item instanceof ArchitectureElement)
				result.add((ArchitectureElement) item);
		}
		return result;
	}

}
