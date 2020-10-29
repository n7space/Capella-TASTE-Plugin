// N7 Space Sp. z o.o.
// n7space.com
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
import org.polarsys.capella.common.data.modellingcore.AbstractTrace;
import org.polarsys.capella.core.data.capellacommon.AbstractCapabilityPkg;
import org.polarsys.capella.core.data.capellacommon.GenericTrace;
import org.polarsys.capella.core.data.capellacore.AbstractPropertyValue;
import org.polarsys.capella.core.data.capellacore.EnumerationPropertyLiteral;
import org.polarsys.capella.core.data.capellacore.EnumerationPropertyType;
import org.polarsys.capella.core.data.capellacore.NamingRule;
import org.polarsys.capella.core.data.capellacore.PropertyValueGroup;
import org.polarsys.capella.core.data.capellacore.PropertyValuePkg;
import org.polarsys.capella.core.data.capellacore.Trace;
import org.polarsys.capella.core.data.cs.AbstractDeploymentLink;
import org.polarsys.capella.core.data.cs.ArchitectureAllocation;
import org.polarsys.capella.core.data.cs.BlockArchitecture;
import org.polarsys.capella.core.data.cs.InterfacePkg;
import org.polarsys.capella.core.data.epbs.EPBSArchitecture;
import org.polarsys.capella.core.data.fa.ComponentExchange;
import org.polarsys.capella.core.data.fa.ComponentExchangeCategory;
import org.polarsys.capella.core.data.fa.ComponentExchangeRealization;
import org.polarsys.capella.core.data.fa.ComponentFunctionalAllocation;
import org.polarsys.capella.core.data.fa.ExchangeLink;
import org.polarsys.capella.core.data.fa.FunctionPkg;
import org.polarsys.capella.core.data.information.DataPkg;
import org.polarsys.capella.core.data.la.CapabilityRealizationPkg;
import org.polarsys.capella.core.data.la.LogicalArchitecture;
import org.polarsys.capella.core.data.pa.LogicalArchitectureRealization;
import org.polarsys.capella.core.data.pa.PhysicalActorPkg;
import org.polarsys.capella.core.data.pa.PhysicalArchitecture;
import org.polarsys.capella.core.data.pa.PhysicalComponent;
import org.polarsys.capella.core.data.pa.PhysicalComponentPkg;
import org.polarsys.capella.core.data.pa.PhysicalContext;
import org.polarsys.capella.core.data.pa.PhysicalFunctionPkg;
import org.polarsys.capella.core.data.requirement.Requirement;
import org.polarsys.capella.core.data.requirement.RequirementsPkg;
import org.polarsys.capella.core.data.requirement.RequirementsTrace;
import org.polarsys.kitalpha.emde.model.ElementExtension;

public class PhysicalArchitectureMock implements PhysicalArchitecture {

	protected final PhysicalComponentMock ownedPhysicalComponent;
	protected final PhysicalActorPkgMock actorPkg;
	protected final PhysicalContextMock context;

	public PhysicalArchitectureMock(final PhysicalComponentMock componentMock, final PhysicalActorPkgMock actorPkgMock,
			final PhysicalContextMock contextMock) {
		ownedPhysicalComponent = componentMock;
		actorPkg = actorPkgMock;
		context = contextMock;
	}

	@Override
	public void destroy() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<Adapter> eAdapters() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public TreeIterator<EObject> eAllContents() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EClass eClass() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EObject eContainer() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EStructuralFeature eContainingFeature() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EReference eContainmentFeature() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<EObject> eContents() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<EObject> eCrossReferences() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public boolean eDeliver() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public Object eGet(final EStructuralFeature feature) {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public Object eGet(final EStructuralFeature feature, final boolean resolve) {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public Object eInvoke(final EOperation operation, final EList<?> arguments) throws InvocationTargetException {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public boolean eIsProxy() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public boolean eIsSet(final EStructuralFeature feature) {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public void eNotify(final Notification notification) {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public Resource eResource() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public void eSet(final EStructuralFeature feature, final Object newValue) {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public void eSetDeliver(final boolean deliver) {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public void eUnset(final EStructuralFeature feature) {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<BlockArchitecture> getAllocatedArchitectures() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<LogicalArchitectureRealization> getAllocatedLogicalArchitectureRealizations() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public EList<LogicalArchitecture> getAllocatedLogicalArchitectures() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public EList<BlockArchitecture> getAllocatingArchitectures() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<EPBSArchitecture> getAllocatingEpbsArchitectures() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public EList<PropertyValueGroup> getAppliedPropertyValueGroups() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<AbstractPropertyValue> getAppliedPropertyValues() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<Requirement> getAppliedRequirements() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<AbstractConstraint> getConstraints() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public CapabilityRealizationPkg getContainedCapabilityRealizationPkg() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<GenericTrace> getContainedGenericTraces() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public PhysicalFunctionPkg getContainedPhysicalFunctionPkg() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<RequirementsTrace> getContainedRequirementsTraces() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public String getDescription() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<EnumerationPropertyLiteral> getFeatures() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public String getFullLabel() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public String getId() {
		return ownedPhysicalComponent.getId();

	}

	@Override
	public EList<AbstractTrace> getIncomingTraces() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public String getLabel() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public String getName() {
		return ownedPhysicalComponent.getName();

	}

	@Override
	public EList<NamingRule> getNamingRules() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<AbstractTrace> getOutgoingTraces() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public AbstractCapabilityPkg getOwnedAbstractCapabilityPkg() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<ComponentExchangeCategory> getOwnedComponentExchangeCategories() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<ComponentExchangeRealization> getOwnedComponentExchangeRealizations() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<ComponentExchange> getOwnedComponentExchanges() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<AbstractConstraint> getOwnedConstraints() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public DataPkg getOwnedDataPkg() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<AbstractDeploymentLink> getOwnedDeployments() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public EList<EnumerationPropertyType> getOwnedEnumerationPropertyTypes() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<ElementExtension> getOwnedExtensions() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<ComponentFunctionalAllocation> getOwnedFunctionalAllocations() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<ExchangeLink> getOwnedFunctionalLinks() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public FunctionPkg getOwnedFunctionPkg() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public InterfacePkg getOwnedInterfacePkg() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<LogicalArchitectureRealization> getOwnedLogicalArchitectureRealizations() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public PhysicalActorPkg getOwnedPhysicalActorPkg() {
		return actorPkg;

	}

	@Override
	public PhysicalComponent getOwnedPhysicalComponent() {
		return ownedPhysicalComponent;
	}

	@Override
	public PhysicalComponentPkg getOwnedPhysicalComponentPkg() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public PhysicalContext getOwnedPhysicalContext() {
		return context;

	}

	@Override
	public EList<PropertyValueGroup> getOwnedPropertyValueGroups() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<PropertyValuePkg> getOwnedPropertyValuePkgs() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<AbstractPropertyValue> getOwnedPropertyValues() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<RequirementsPkg> getOwnedRequirementPkgs() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public EList<Trace> getOwnedTraces() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<ArchitectureAllocation> getProvisionedArchitectureAllocations() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<ArchitectureAllocation> getProvisioningArchitectureAllocations() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public String getReview() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public String getSid() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EnumerationPropertyLiteral getStatus() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public String getSummary() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public boolean hasUnnamedLabel() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public boolean isVisibleInDoc() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public boolean isVisibleInLM() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public void setDescription(final String value) {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public void setId(final String value) {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public void setName(final String value) {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public void setOwnedAbstractCapabilityPkg(final AbstractCapabilityPkg value) {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public void setOwnedDataPkg(final DataPkg value) {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public void setOwnedFunctionPkg(final FunctionPkg value) {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public void setOwnedInterfacePkg(final InterfacePkg value) {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public void setOwnedPhysicalActorPkg(final PhysicalActorPkg value) {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public void setOwnedPhysicalComponent(final PhysicalComponent value) {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public void setOwnedPhysicalComponentPkg(final PhysicalComponentPkg value) {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public void setOwnedPhysicalContext(final PhysicalContext value) {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public void setReview(final String value) {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public void setSid(final String value) {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public void setStatus(final EnumerationPropertyLiteral value) {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public void setSummary(final String value) {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public void setVisibleInDoc(final boolean value) {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public void setVisibleInLM(final boolean value) {
		throw new RuntimeException("Not implemented");

	}

}
