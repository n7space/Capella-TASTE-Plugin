// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.testmocks;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.polarsys.capella.common.data.modellingcore.AbstractConstraint;
import org.polarsys.capella.common.data.modellingcore.AbstractInformationFlow;
import org.polarsys.capella.common.data.modellingcore.AbstractTrace;
import org.polarsys.capella.core.data.capellacore.AbstractPropertyValue;
import org.polarsys.capella.core.data.capellacore.EnumerationPropertyLiteral;
import org.polarsys.capella.core.data.capellacore.EnumerationPropertyType;
import org.polarsys.capella.core.data.capellacore.InvolvedElement;
import org.polarsys.capella.core.data.capellacore.InvolverElement;
import org.polarsys.capella.core.data.capellacore.PropertyValueGroup;
import org.polarsys.capella.core.data.cs.AbstractPathInvolvedElement;
import org.polarsys.capella.core.data.cs.Component;
import org.polarsys.capella.core.data.cs.PhysicalPathInvolvement;
import org.polarsys.capella.core.data.requirement.Requirement;
import org.polarsys.kitalpha.emde.model.ElementExtension;

public class PhysicalPathInvolvementMock implements PhysicalPathInvolvement {

	final InvolvedElement involvedElement;

	public PhysicalPathInvolvementMock(final InvolvedElement element) {
		involvedElement = element;
	}

	@Override
	public InvolverElement getInvolver() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InvolvedElement getInvolved() {
		return involvedElement;
	}

	@Override
	public void setInvolved(final InvolvedElement value) {
		// TODO Auto-generated method stub

	}

	@Override
	public AbstractInformationFlow getRealizedFlow() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRealizedFlow(final AbstractInformationFlow value) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setId(final String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getSid() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSid(final String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public EList<AbstractConstraint> getConstraints() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<AbstractConstraint> getOwnedConstraints() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getFullLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasUnnamedLabel() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public EList<ElementExtension> getOwnedExtensions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EClass eClass() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Resource eResource() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EObject eContainer() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EStructuralFeature eContainingFeature() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EReference eContainmentFeature() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<EObject> eContents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TreeIterator<EObject> eAllContents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean eIsProxy() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public EList<EObject> eCrossReferences() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object eGet(final EStructuralFeature feature) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object eGet(final EStructuralFeature feature, final boolean resolve) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eSet(final EStructuralFeature feature, final Object newValue) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean eIsSet(final EStructuralFeature feature) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void eUnset(final EStructuralFeature feature) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object eInvoke(final EOperation operation, final EList<?> arguments) throws InvocationTargetException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<Adapter> eAdapters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean eDeliver() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void eSetDeliver(final boolean deliver) {
		// TODO Auto-generated method stub

	}

	@Override
	public void eNotify(final Notification notification) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getSummary() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSummary(final String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDescription(final String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getReview() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setReview(final String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public EList<AbstractPropertyValue> getOwnedPropertyValues() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<EnumerationPropertyType> getOwnedEnumerationPropertyTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<AbstractPropertyValue> getAppliedPropertyValues() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<PropertyValueGroup> getOwnedPropertyValueGroups() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<PropertyValueGroup> getAppliedPropertyValueGroups() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EnumerationPropertyLiteral getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStatus(final EnumerationPropertyLiteral value) {
		// TODO Auto-generated method stub

	}

	@Override
	public EList<EnumerationPropertyLiteral> getFeatures() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<Requirement> getAppliedRequirements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<AbstractTrace> getIncomingTraces() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<AbstractTrace> getOutgoingTraces() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isVisibleInDoc() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setVisibleInDoc(final boolean value) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isVisibleInLM() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setVisibleInLM(final boolean value) {
		// TODO Auto-generated method stub

	}

	@Override
	public EList<PhysicalPathInvolvement> getNextInvolvements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<PhysicalPathInvolvement> getPreviousInvolvements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractPathInvolvedElement getInvolvedElement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Component getInvolvedComponent() {
		// TODO Auto-generated method stub
		return null;
	}

}
