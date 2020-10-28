// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.architecture.aadldefinitionproviders;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import com.n7space.capellatasteplugin.modelling.architecture.aadl.AadlContainterInterface;
import com.n7space.capellatasteplugin.modelling.architecture.aadl.AadlElement;
import com.n7space.capellatasteplugin.modelling.architecture.aadl.AadlPackage;
import com.n7space.capellatasteplugin.modelling.architecture.aadl.AadlPackageElement;
import com.n7space.capellatasteplugin.modelling.architecture.aadl.AadlPackages;
import com.n7space.capellatasteplugin.utils.BasicConfigurableItem;

/**
 * Base class for AADL definition providers.
 *
 */
public abstract class BaseDefinitionProvider extends BasicConfigurableItem {

	protected String tasteVersion = "\"2.0\"";

	protected List<AadlPackage> addPackagesRequiredByPackageElements(final AadlPackage pkg) {
		final Set<AadlPackage> requiredPacakges = new HashSet<AadlPackage>();

		for (final AadlElement element : pkg.getAllContainedElements()) {
			gatherRequiredPackages(pkg, element, requiredPacakges);
		}

		final List<AadlPackage> result = new LinkedList<AadlPackage>();
		for (final AadlPackage requiredPackage : requiredPacakges) {
			if (!pkg.referencedPackages.contains(requiredPackage)) {
				pkg.referencedPackages.add(requiredPackage);
				result.add(requiredPackage);
			}
		}
		return result;
	}

	protected void gatherRequiredPackages(final AadlPackage originalPkg, final AadlElement root,
			final Set<AadlPackage> pkgs) {
		if (root instanceof AadlPackageElement) {
			final AadlPackageElement element = (AadlPackageElement) root;
			if (element.pkg != originalPkg) {
				pkgs.add(element.pkg);
				return;
			}
		}
		if (root instanceof AadlContainterInterface) {
			final AadlContainterInterface container = (AadlContainterInterface) root;
			for (final AadlElement element : container.getAllContainedElements()) {
				gatherRequiredPackages(originalPkg, element, pkgs);
			}
		}
	}

	protected void resolveInterPackageDependencies(final AadlPackages pkgs) {
		for (final AadlPackage pkg : pkgs.packages) {
			addPackagesRequiredByPackageElements(pkg);
		}
	}

}
