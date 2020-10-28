// N7 Space Sp. z o.o.
// www.n7space.com
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
import org.polarsys.capella.core.data.capellacommon.CapabilityRealizationInvolvement;
import org.polarsys.capella.core.data.capellacommon.GenericTrace;
import org.polarsys.capella.core.data.capellacommon.StateMachine;
import org.polarsys.capella.core.data.capellacore.AbstractPropertyValue;
import org.polarsys.capella.core.data.capellacore.Classifier;
import org.polarsys.capella.core.data.capellacore.EnumerationPropertyLiteral;
import org.polarsys.capella.core.data.capellacore.EnumerationPropertyType;
import org.polarsys.capella.core.data.capellacore.Feature;
import org.polarsys.capella.core.data.capellacore.GeneralizableElement;
import org.polarsys.capella.core.data.capellacore.Generalization;
import org.polarsys.capella.core.data.capellacore.Involvement;
import org.polarsys.capella.core.data.capellacore.NamingRule;
import org.polarsys.capella.core.data.capellacore.PropertyValueGroup;
import org.polarsys.capella.core.data.capellacore.Trace;
import org.polarsys.capella.core.data.capellacore.TypedElement;
import org.polarsys.capella.core.data.cs.AbstractDeploymentLink;
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
import org.polarsys.capella.core.data.cs.SystemComponentCapabilityRealizationInvolvement;
import org.polarsys.capella.core.data.epbs.ConfigurationItem;
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
import org.polarsys.capella.core.data.la.LogicalComponent;
import org.polarsys.capella.core.data.pa.LogicalComponentRealization;
import org.polarsys.capella.core.data.pa.LogicalInterfaceRealization;
import org.polarsys.capella.core.data.pa.PhysicalActor;
import org.polarsys.capella.core.data.pa.PhysicalComponent;
import org.polarsys.capella.core.data.pa.PhysicalComponentKind;
import org.polarsys.capella.core.data.pa.PhysicalComponentNature;
import org.polarsys.capella.core.data.pa.PhysicalComponentPkg;
import org.polarsys.capella.core.data.pa.PhysicalFunction;
import org.polarsys.capella.core.data.pa.deployment.DeploymentAspect;
import org.polarsys.capella.core.data.requirement.Requirement;
import org.polarsys.capella.core.data.requirement.RequirementsTrace;
import org.polarsys.kitalpha.emde.model.ElementExtension;

public class PhysicalComponentMock implements PhysicalComponent {

	protected final PhysicalComponentNature nature;
	protected final String name;
	protected final String id;
	protected EList<PhysicalLink> physicalLinks = new BasicEList<PhysicalLink>();
	protected EList<PhysicalPath> physicalPaths = new BasicEList<PhysicalPath>();
	protected final EList<PhysicalActor> deployingActors = new BasicEList<PhysicalActor>();
	protected final EList<PhysicalComponent> deployingComponents = new BasicEList<PhysicalComponent>();
	protected final EList<PhysicalFunction> allocatedPhysicalFunctions = new BasicEList<PhysicalFunction>();
	protected final EList<PhysicalComponent> ownedPhysicalComponents = new BasicEList<PhysicalComponent>();
	protected final EList<AbstractPropertyValue> appliedPropertyValues = new BasicEList<AbstractPropertyValue>();

	public PhysicalComponentMock(final String componentName) {
		name = componentName;
		id = name + "Id";
		nature = PhysicalComponentNature.UNSET;
	}

	public void addAppliedPropertyValue(final AbstractPropertyValue value) {
		appliedPropertyValues.add(value);
	}

	public PhysicalComponentMock(final String componentName, final PhysicalComponentNature componentNature) {
		name = componentName;
		id = name + "Id";
		nature = componentNature;
	}

	public void addAllocatedPhysicalFunction(final PhysicalFunction function) {
		allocatedPhysicalFunctions.add(function);
	}

	public void addDeployingComponent(final PhysicalComponent component) {
		deployingComponents.add(component);
	}

	public void addDeployingActor(final PhysicalActor actor) {
		deployingActors.add(actor);
	}

	public void addPhysicalLink(final PhysicalLink link) {
		physicalLinks.add(link);
	}

	public void addPhysicaPath(final PhysicalPath path) {
		physicalPaths.add(path);
	}

	public void addOwnedPhysicalComponent(final PhysicalComponent component) {
		ownedPhysicalComponents.add(component);
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
	public EList<AbstractTypedElement> getAbstractTypedElements() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<CommunicationLink> getAccess() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<CommunicationLink> getAcquire() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<Component> getAllocatedComponents() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<AbstractFunction> getAllocatedFunctions() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<Interface> getAllocatedInterfaces() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<PhysicalFunction> getAllocatedPhysicalFunctions() {
		return allocatedPhysicalFunctions;

	}

	@Override
	public EList<Component> getAllocatingComponents() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<ConfigurationItem> getAllocatorConfigurationItems() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<PropertyValueGroup> getAppliedPropertyValueGroups() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<AbstractPropertyValue> getAppliedPropertyValues() {
		return appliedPropertyValues;
	}

	@Override
	public EList<Requirement> getAppliedRequirements() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<CommunicationLink> getCall() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<AbstractConstraint> getConstraints() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<CommunicationLink> getConsume() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<ComponentPort> getContainedComponentPorts() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public EList<GenericTrace> getContainedGenericTraces() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<Part> getContainedParts() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<PhysicalPort> getContainedPhysicalPorts() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<Property> getContainedProperties() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<RequirementsTrace> getContainedRequirementsTraces() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<Classifier> getDataType() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<PhysicalComponent> getDeployedPhysicalComponents() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<AbstractDeploymentLink> getDeployingLinks() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<PhysicalActor> getDeployingPhysicalActors() {
		return deployingActors;
	}

	@Override
	public EList<PhysicalComponent> getDeployingPhysicalComponents() {
		return deployingComponents;
	}

	@Override
	public EList<AbstractDeploymentLink> getDeploymentLinks() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public String getDescription() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<CommunicationLink> getExecute() {
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
	public EList<ComponentFunctionalAllocation> getFunctionalAllocations() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public EList<InterfaceImplementation> getImplementedInterfaceLinks() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<Interface> getImplementedInterfaces() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<AbstractTrace> getIncomingTraces() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<ExchangeLink> getInExchangeLinks() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<CapabilityRealizationInvolvement> getInvolvingCapabilityRealizationInvolvements() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<Involvement> getInvolvingInvolvements() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public PhysicalComponentKind getKind() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public String getLabel() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<LogicalComponentRealization> getLogicalComponentRealizations() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<LogicalInterfaceRealization> getLogicalInterfaceRealizations() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public String getName() {
		return name;

	}

	@Override
	public EList<NamingRule> getNamingRules() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public PhysicalComponentNature getNature() {
		return nature;

	}

	@Override
	public EList<ExchangeLink> getOutExchangeLinks() {
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
	public EList<CommunicationLink> getOwnedCommunicationLinks() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<ComponentExchangeCategory> getOwnedComponentExchangeCategories() {
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
	public DeploymentAspect getOwnedDeploymentAspect() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<AbstractDeploymentLink> getOwnedDeploymentLinks() {
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
	public EList<Feature> getOwnedFeatures() {
		return new BasicEList<Feature>();

	}

	@Override
	public EList<ComponentFunctionalAllocation> getOwnedFunctionalAllocation() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<Generalization> getOwnedGeneralizations() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<InterfaceAllocation> getOwnedInterfaceAllocations() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<InterfaceImplementation> getOwnedInterfaceImplementations() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public InterfacePkg getOwnedInterfacePkg() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<InterfaceUse> getOwnedInterfaceUses() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<LogicalComponentRealization> getOwnedLogicalComponentRealizations() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<Partition> getOwnedPartitions() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<PhysicalComponentPkg> getOwnedPhysicalComponentPkgs() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<PhysicalComponent> getOwnedPhysicalComponents() {
		return ownedPhysicalComponents;
	}

	@Override
	public EList<PhysicalLinkCategory> getOwnedPhysicalLinkCategories() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<PhysicalLink> getOwnedPhysicalLinks() {
		return physicalLinks;

	}

	@Override
	public EList<PhysicalPath> getOwnedPhysicalPath() {
		return physicalPaths;

	}

	@Override
	public EList<PropertyValueGroup> getOwnedPropertyValueGroups() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<AbstractPropertyValue> getOwnedPropertyValues() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<StateMachine> getOwnedStateMachines() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<Trace> getOwnedTraces() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<SystemComponentCapabilityRealizationInvolvement> getParticipationsInCapabilityRealizations() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<CommunicationLink> getProduce() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<Interface> getProvidedInterfaces() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<ComponentAllocation> getProvisionedComponentAllocations() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<InterfaceAllocation> getProvisionedInterfaceAllocations() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<ComponentAllocation> getProvisioningComponentAllocations() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<LogicalComponent> getRealizedLogicalComponents() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<CommunicationLink> getReceive() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<Partition> getRepresentingPartitions() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<Interface> getRequiredInterfaces() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public String getReview() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<CommunicationLink> getSend() {
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
	public EList<GeneralizableElement> getSub() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<Generalization> getSubGeneralizations() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<PhysicalComponent> getSubPhysicalComponents() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public String getSummary() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<GeneralizableElement> getSuper() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<Generalization> getSuperGeneralizations() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<CommunicationLink> getTransmit() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<TypedElement> getTypedElements() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<InterfaceUse> getUsedInterfaceLinks() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<Interface> getUsedInterfaces() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public EList<CommunicationLink> getWrite() {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public boolean hasUnnamedLabel() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public boolean isAbstract() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public boolean isDataComponent() {
		throw new RuntimeException("Not implemented");
	}

	@Override
	public boolean isDecomposed() {
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
	public void setAbstract(final boolean value) {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public void setDataComponent(final boolean value) {
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
	public void setKind(final PhysicalComponentKind value) {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public void setName(final String value) {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public void setNature(final PhysicalComponentNature value) {
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
	public void setOwnedDeploymentAspect(final DeploymentAspect value) {
		throw new RuntimeException("Not implemented");

	}

	@Override
	public void setOwnedInterfacePkg(final InterfacePkg value) {
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
