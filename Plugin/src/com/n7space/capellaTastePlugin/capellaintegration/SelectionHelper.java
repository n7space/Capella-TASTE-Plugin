// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.sirius.diagram.DDiagramElement;
import org.eclipse.sirius.diagram.ui.edit.api.part.AbstractBorderedDiagramElementEditPart;
import org.polarsys.capella.core.data.cs.InterfacePkg;
import org.polarsys.capella.core.data.information.DataPkg;
import org.polarsys.capella.core.data.la.LogicalArchitecture;
import org.polarsys.capella.core.data.pa.PhysicalArchitecture;
import org.polarsys.capella.core.data.pa.PhysicalComponent;
import org.polarsys.capella.core.data.pa.PhysicalFunction;

import com.n7space.capellatasteplugin.capellaintegration.wrappers.InterfacePackageWrapper;

/**
 * Utility class for processing Capella selections.
 */
public class SelectionHelper {
	protected static void extractDataPackagesFromDiagramElementPart(final AbstractBorderedDiagramElementEditPart part,
			final List<DataPkg> result) {
		final DDiagramElement element = part.resolveDiagramElement();
		if (element == null) {
			return;
		}
		final EObject object = element.getTarget();
		if (object == null) {
			return;
		}
		if (object instanceof DataPkg) {
			result.add((DataPkg) object);
		}
		if (object instanceof InterfacePkg) {
			result.add(new InterfacePackageWrapper((InterfacePkg) object));
		}
	}

	/**
	 * Finds Capella data model in the given selection.
	 *
	 * @param selection
	 *            The selection to be analyzed
	 * @return Found Capella data packages
	 */
	public static List<DataPkg> findDataPackagesInSelection(final ISelection selection) {
		final List<DataPkg> result = new LinkedList<DataPkg>();
		if (selection instanceof org.eclipse.jface.viewers.StructuredSelection) {
			final StructuredSelection structuredSelection = (StructuredSelection) selection;
			final Iterator<?> i = structuredSelection.iterator();
			while (i.hasNext()) {
				final Object object = i.next();
				if (object instanceof AbstractBorderedDiagramElementEditPart) {
					final AbstractBorderedDiagramElementEditPart part = (AbstractBorderedDiagramElementEditPart) object;
					extractDataPackagesFromDiagramElementPart(part, result);
				}
				if (object instanceof DataPkg) {
					result.add((DataPkg) object);
				}
				if (object instanceof InterfacePkg) {
					result.add(new InterfacePackageWrapper((InterfacePkg) object));
				}
			}
		}
		return result;
	}

	/**
	 * Finds Capella logical architecture in the given selection.
	 *
	 * @param selection
	 *            The selection to be analyzed
	 * @return Found logical architecture or null if none was found
	 */
	public static LogicalArchitecture findLogicalArchitectureInSelection(final ISelection selection) {
		if (selection instanceof org.eclipse.jface.viewers.StructuredSelection) {
			final StructuredSelection ss = (StructuredSelection) selection;
			final Iterator<?> i = ss.iterator();
			while (i.hasNext()) {
				final Object o = i.next();
				if (o instanceof LogicalArchitecture) {
					return (LogicalArchitecture) o;
				}
			}
		}
		return null;
	}

	/**
	 * Finds Capella physical architecture in the given selection.
	 *
	 * @param selection
	 *            The selection to be analyzed
	 * @return Found physical architecture or null if none was found
	 */
	public static PhysicalArchitecture findPhysicalArchitectureInSelection(final ISelection selection) {
		if (selection instanceof org.eclipse.jface.viewers.StructuredSelection) {
			final StructuredSelection ss = (StructuredSelection) selection;
			final Iterator<?> i = ss.iterator();
			while (i.hasNext()) {
				final Object o = i.next();
				if (o instanceof org.polarsys.capella.core.data.pa.PhysicalArchitecture) {
					return (PhysicalArchitecture) o;
				}
			}
		}
		return null;
	}

	/**
	 * Finds Capella physical component in the given selection.
	 *
	 * @param selection
	 *            The selection to be analyzed
	 * @return Found physical component or null if none was found
	 */
	public static PhysicalComponent findPhysicalComponentInSelection(final ISelection selection) {
		if (selection instanceof org.eclipse.jface.viewers.StructuredSelection) {
			final StructuredSelection ss = (StructuredSelection) selection;
			final Iterator<?> i = ss.iterator();
			while (i.hasNext()) {
				final Object o = i.next();
				if (o instanceof PhysicalComponent) {
					return (PhysicalComponent) o;
				}
			}
		}
		return null;
	}

	/**
	 * Finds Capella physical function in the given selection.
	 *
	 * @param selection
	 *            The selection to be analyzed
	 * @return Found physical function or null if none was found
	 */
	public static PhysicalFunction findPhysicalFunctionInSelection(final ISelection selection) {
		if (selection instanceof org.eclipse.jface.viewers.StructuredSelection) {
			final StructuredSelection ss = (StructuredSelection) selection;
			final Iterator<?> i = ss.iterator();
			while (i.hasNext()) {
				final Object o = i.next();
				if (o instanceof PhysicalFunction) {
					return (PhysicalFunction) o;
				}
			}
		}
		return null;
	}
}
