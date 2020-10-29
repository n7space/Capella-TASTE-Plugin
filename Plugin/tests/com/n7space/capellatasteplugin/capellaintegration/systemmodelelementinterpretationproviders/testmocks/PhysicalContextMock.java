// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders.testmocks;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.polarsys.capella.common.data.modellingcore.AbstractConstraint;
import org.polarsys.capella.common.data.modellingcore.AbstractTrace;
import org.polarsys.capella.common.data.modellingcore.AbstractTypedElement;
import org.polarsys.capella.core.data.capellacommon.AbstractCapabilityPkg;
import org.polarsys.capella.core.data.capellacommon.GenericTrace;
import org.polarsys.capella.core.data.capellacommon.StateMachine;
import org.polarsys.capella.core.data.capellacore.AbstractPropertyValue;
import org.polarsys.capella.core.data.capellacore.EnumerationPropertyLiteral;
import org.polarsys.capella.core.data.capellacore.EnumerationPropertyType;
import org.polarsys.capella.core.data.capellacore.Feature;
import org.polarsys.capella.core.data.capellacore.GeneralizableElement;
import org.polarsys.capella.core.data.capellacore.Generalization;
import org.polarsys.capella.core.data.capellacore.NamingRule;
import org.polarsys.capella.core.data.capellacore.PropertyValueGroup;
import org.polarsys.capella.core.data.capellacore.Trace;
import org.polarsys.capella.core.data.capellacore.TypedElement;
import org.polarsys.capella.core.data.cs.Component;
import org.polarsys.capella.core.data.cs.ComponentAllocation;
import org.polarsys.capella.core.data.cs.Interface;
import org.polarsys.capella.core.data.cs.InterfaceAllocation;
import org.polarsys.capella.core.data.cs.InterfaceImplementation;
import org.polarsys.capella.core.data.cs.InterfacePkg;
import org.polarsys.capella.core.data.cs.InterfaceUse;
import org.polarsys.capella.core.data.cs.Part;
import org.polarsys.capella.core.data.cs.PhysicalLink;
import org.polarsys.capella.core.data.cs.PhysicalLinkCategory;
import org.polarsys.capella.core.data.cs.PhysicalPath;
import org.polarsys.capella.core.data.cs.PhysicalPort;
import org.polarsys.capella.core.data.fa.AbstractFunction;
import org.polarsys.capella.core.data.fa.ComponentExchange;
import org.polarsys.capella.core.data.fa.ComponentExchangeCategory;
import org.polarsys.capella.core.data.fa.ComponentFunctionalAllocation;
import org.polarsys.capella.core.data.fa.ComponentPort;
import org.polarsys.capella.core.data.fa.ExchangeLink;
import org.polarsys.capella.core.data.information.DataPkg;
import org.polarsys.capella.core.data.information.Partition;
import org.polarsys.capella.core.data.information.Property;
import org.polarsys.capella.core.data.information.communication.CommunicationLink;
import org.polarsys.capella.core.data.pa.PhysicalContext;
import org.polarsys.capella.core.data.requirement.Requirement;
import org.polarsys.capella.core.data.requirement.RequirementsTrace;
import org.polarsys.kitalpha.emde.model.ElementExtension;

public class PhysicalContextMock implements PhysicalContext {

	@Override
	public EList<InterfaceUse> getOwnedInterfaceUses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<InterfaceUse> getUsedInterfaceLinks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<Interface> getUsedInterfaces() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<InterfaceImplementation> getOwnedInterfaceImplementations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<InterfaceImplementation> getImplementedInterfaceLinks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<Interface> getImplementedInterfaces() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<ComponentAllocation> getProvisionedComponentAllocations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<ComponentAllocation> getProvisioningComponentAllocations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<Component> getAllocatedComponents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<Component> getAllocatingComponents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<Interface> getProvidedInterfaces() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<Interface> getRequiredInterfaces() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<ComponentPort> getContainedComponentPorts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<Part> getContainedParts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<PhysicalPort> getContainedPhysicalPorts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<PhysicalPath> getOwnedPhysicalPath() {
		return new BasicEList<PhysicalPath>();
	}

	@Override
	public EList<PhysicalLink> getOwnedPhysicalLinks() {
		return new BasicEList<PhysicalLink>();
	}

	@Override
	public EList<PhysicalLinkCategory> getOwnedPhysicalLinkCategories() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractCapabilityPkg getOwnedAbstractCapabilityPkg() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOwnedAbstractCapabilityPkg(final AbstractCapabilityPkg value) {
		// TODO Auto-generated method stub

	}

	@Override
	public InterfacePkg getOwnedInterfacePkg() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOwnedInterfacePkg(final InterfacePkg value) {
		// TODO Auto-generated method stub

	}

	@Override
	public DataPkg getOwnedDataPkg() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOwnedDataPkg(final DataPkg value) {
		// TODO Auto-generated method stub

	}

	@Override
	public EList<StateMachine> getOwnedStateMachines() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<TypedElement> getTypedElements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<AbstractTypedElement> getAbstractTypedElements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(final String value) {
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
	public EList<Trace> getOwnedTraces() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<GenericTrace> getContainedGenericTraces() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<RequirementsTrace> getContainedRequirementsTraces() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<NamingRule> getNamingRules() {
		// TODO Auto-generated method stub
		return null;
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
	public EList<ComponentFunctionalAllocation> getOwnedFunctionalAllocation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<ComponentExchange> getOwnedComponentExchanges() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<ComponentExchangeCategory> getOwnedComponentExchangeCategories() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<ComponentFunctionalAllocation> getFunctionalAllocations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<AbstractFunction> getAllocatedFunctions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<ExchangeLink> getInExchangeLinks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<ExchangeLink> getOutExchangeLinks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<Partition> getOwnedPartitions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<Partition> getRepresentingPartitions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDecomposed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public EList<Feature> getOwnedFeatures() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<Property> getContainedProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAbstract() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setAbstract(final boolean value) {
		// TODO Auto-generated method stub

	}

	@Override
	public EList<Generalization> getOwnedGeneralizations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<Generalization> getSuperGeneralizations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<Generalization> getSubGeneralizations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<GeneralizableElement> getSuper() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<GeneralizableElement> getSub() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<InterfaceAllocation> getOwnedInterfaceAllocations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<InterfaceAllocation> getProvisionedInterfaceAllocations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<Interface> getAllocatedInterfaces() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<CommunicationLink> getOwnedCommunicationLinks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<CommunicationLink> getProduce() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<CommunicationLink> getConsume() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<CommunicationLink> getSend() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<CommunicationLink> getReceive() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<CommunicationLink> getCall() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<CommunicationLink> getExecute() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<CommunicationLink> getWrite() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<CommunicationLink> getAccess() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<CommunicationLink> getAcquire() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<CommunicationLink> getTransmit() {
		// TODO Auto-generated method stub
		return null;
	}

}
