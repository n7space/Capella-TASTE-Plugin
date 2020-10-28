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
import org.polarsys.capella.common.data.activity.ActivityEdge;
import org.polarsys.capella.common.data.modellingcore.AbstractConstraint;
import org.polarsys.capella.common.data.modellingcore.AbstractExchangeItem;
import org.polarsys.capella.common.data.modellingcore.AbstractInformationFlow;
import org.polarsys.capella.common.data.modellingcore.AbstractRelationship;
import org.polarsys.capella.common.data.modellingcore.AbstractTrace;
import org.polarsys.capella.common.data.modellingcore.AbstractTypedElement;
import org.polarsys.capella.common.data.modellingcore.InformationsExchanger;
import org.polarsys.capella.core.data.capellacore.AbstractPropertyValue;
import org.polarsys.capella.core.data.capellacore.EnumerationPropertyLiteral;
import org.polarsys.capella.core.data.capellacore.EnumerationPropertyType;
import org.polarsys.capella.core.data.capellacore.PropertyValueGroup;
import org.polarsys.capella.core.data.cs.Part;
import org.polarsys.capella.core.data.cs.PhysicalLink;
import org.polarsys.capella.core.data.fa.ComponentExchange;
import org.polarsys.capella.core.data.fa.ComponentExchangeCategory;
import org.polarsys.capella.core.data.fa.ComponentExchangeEnd;
import org.polarsys.capella.core.data.fa.ComponentExchangeFunctionalExchangeAllocation;
import org.polarsys.capella.core.data.fa.ComponentExchangeKind;
import org.polarsys.capella.core.data.fa.ComponentExchangeRealization;
import org.polarsys.capella.core.data.fa.ExchangeContainment;
import org.polarsys.capella.core.data.fa.ExchangeLink;
import org.polarsys.capella.core.data.fa.ExchangeSpecificationRealization;
import org.polarsys.capella.core.data.fa.FunctionalExchange;
import org.polarsys.capella.core.data.information.Port;
import org.polarsys.capella.core.data.interaction.SequenceMessage;
import org.polarsys.capella.core.data.requirement.Requirement;
import org.polarsys.kitalpha.emde.model.ElementExtension;

public class ComponentExchangeMock implements ComponentExchange {

	protected final String name;
	protected final Port sourcePort;
	protected final Port targetPort;

	protected final EList<FunctionalExchange> allocatedFunctionalExchanges = new BasicEList<FunctionalExchange>();

	public void addAllocatedFunctionalExchange(final FunctionalExchange exchange) {
		allocatedFunctionalExchanges.add(exchange);
	}

	public ComponentExchangeMock(final String exchangeName) {
		name = exchangeName;
		sourcePort = null;
		targetPort = null;
	}

	public ComponentExchangeMock(final String exchangeName, final Port srcPort, final Port dstPort) {
		name = exchangeName;
		sourcePort = srcPort;
		targetPort = dstPort;
	}

	@Override
	public EList<AbstractTypedElement> getAbstractTypedElements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(final String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getId() {
		return name + "Id";
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
	public EList<SequenceMessage> getInvokingSequenceMessages() {
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
	public ExchangeLink getContainingLink() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExchangeContainment getLink() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setLink(final ExchangeContainment value) {
		// TODO Auto-generated method stub

	}

	@Override
	public EList<ExchangeSpecificationRealization> getOutgoingExchangeSpecificationRealizations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<ExchangeSpecificationRealization> getIncomingExchangeSpecificationRealizations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<ActivityEdge> getRealizingActivityFlows() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<AbstractRelationship> getRealizations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<AbstractExchangeItem> getConvoyedInformations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InformationsExchanger getSource() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSource(final InformationsExchanger value) {
		// TODO Auto-generated method stub

	}

	@Override
	public InformationsExchanger getTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTarget(final InformationsExchanger value) {
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
	public ComponentExchangeKind getKind() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setKind(final ComponentExchangeKind value) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isOriented() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setOriented(final boolean value) {
		// TODO Auto-generated method stub

	}

	@Override
	public EList<FunctionalExchange> getAllocatedFunctionalExchanges() {
		return allocatedFunctionalExchanges;
	}

	@Override
	public EList<ComponentExchangeRealization> getIncomingComponentExchangeRealizations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<ComponentExchangeRealization> getOutgoingComponentExchangeRealizations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<ComponentExchangeFunctionalExchangeAllocation> getOutgoingComponentExchangeFunctionalExchangeAllocations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<ComponentExchangeFunctionalExchangeAllocation> getOwnedComponentExchangeFunctionalExchangeAllocations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<ComponentExchangeRealization> getOwnedComponentExchangeRealizations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<ComponentExchangeEnd> getOwnedComponentExchangeEnds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Port getSourcePort() {
		return sourcePort;
	}

	@Override
	public Part getSourcePart() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Port getTargetPort() {
		return targetPort;
	}

	@Override
	public Part getTargetPart() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<ComponentExchangeCategory> getCategories() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<PhysicalLink> getAllocatorPhysicalLinks() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<ComponentExchange> getRealizedComponentExchanges() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<ComponentExchange> getRealizingComponentExchanges() {
		// TODO Auto-generated method stub
		return null;
	}

}
