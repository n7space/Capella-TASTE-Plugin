// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle.
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID.
	public static final String PLUGIN_ID = "capellatasteplugin";

	// The shared instance.
	private static Activator plugin;

	/**
	 * Returns the shared instance.
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in relative
	 * path.
	 *
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(final String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	/**
	 * The constructor.
	 */
	public Activator() {
	}

	/**
	 * Handles the activation of the plugin. Registers the selection listener.
	 * 
	 * @param context
	 *            Context
	 * @see SelectionListener
	 */
	@Override
	public void start(final BundleContext context) throws Exception {
		super.start(context);
		final IWorkbench workbench = PlatformUI.getWorkbench();
		workbench.getDisplay().asyncExec(new Runnable() {

			@Override
			public void run() {
				final IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
				if (window != null) {
					window.getSelectionService().addPostSelectionListener(SelectionListener.getListener());
				}

			}
		});
		plugin = this;
	}

	/**
	 * Handles the deactivation of the plugin. Unregisters the selection listener.
	 * 
	 * @param context
	 *            Context
	 * @see SelectionListener
	 */
	@Override
	public void stop(final BundleContext context) throws Exception {
		plugin = null;
		final IWorkbench workbench = PlatformUI.getWorkbench();
		final Display display = workbench.getDisplay();
		if (!display.isDisposed()) {
			display.asyncExec(new Runnable() {

				@Override
				public void run() {
					final IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
					if (window != null) {
						window.getSelectionService().removeSelectionListener(SelectionListener.getListener());
					}

				}
			});
		}
		super.stop(context);
	}
}
