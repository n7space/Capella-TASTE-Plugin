// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration;

import org.eclipse.ui.IStartup;

import com.n7space.capellatasteplugin.eclipseintegration.mmi.GraphicalDataModelBrowser;
import com.n7space.capellatasteplugin.eclipseintegration.mmi.GraphicalDirectoryBrowser;
import com.n7space.capellatasteplugin.eclipseintegration.mmi.GraphicalIssuePresenter;
import com.n7space.capellatasteplugin.eclipseintegration.mmi.GraphicalMessagePresenter;
import com.n7space.capellatasteplugin.eclipseintegration.mmi.GraphicalSystemModelBrowser;
import com.n7space.capellatasteplugin.modelling.FileSystemModelSerializer;

/**
 * 
 * Class responsible for setting up the plugin environment.
 * 
 * This class is registered in plugin.xml as org.eclipse.ui.startup.
 *
 */
public class Startup implements IStartup {

	/**
	 * Method called at plugin startup. Binds the Coordinator instance to GUI.
	 */
	@Override
	public void earlyStartup() {
		Coordinator.getInstance().setModelSerializer(new FileSystemModelSerializer());
		Coordinator.getInstance().setDataModelBrowser(new GraphicalDataModelBrowser());
		Coordinator.getInstance().setDirectoryBrowser(new GraphicalDirectoryBrowser());
		Coordinator.getInstance().setIssuePresenter(new GraphicalIssuePresenter());
		Coordinator.getInstance().setMessagePresenter(new GraphicalMessagePresenter());
		Coordinator.getInstance().setSystemModelBrowser(new GraphicalSystemModelBrowser());
	}

}
