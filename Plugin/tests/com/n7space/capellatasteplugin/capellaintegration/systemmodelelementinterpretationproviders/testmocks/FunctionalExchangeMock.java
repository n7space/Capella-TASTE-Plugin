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
import org.polarsys.capella.common.data.activity.ActivityNode;
import org.polarsys.capella.common.data.activity.ActivityPartition;
import org.polarsys.capella.common.data.activity.InterruptibleActivityRegion;
import org.polarsys.capella.common.data.activity.StructuredActivityNode;
import org.polarsys.capella.common.data.behavior.AbstractBehavior;
import org.polarsys.capella.common.data.modellingcore.AbstractConstraint;
import org.polarsys.capella.common.data.modellingcore.AbstractInformationFlow;
import org.polarsys.capella.common.data.modellingcore.AbstractTrace;
import org.polarsys.capella.common.data.modellingcore.AbstractTypedElement;
import org.polarsys.capella.common.data.modellingcore.RateKind;
import org.polarsys.capella.common.data.modellingcore.ValueSpecification;
import org.polarsys.capella.core.data.capellacore.AbstractPropertyValue;
import org.polarsys.capella.core.data.capellacore.EnumerationPropertyLiteral;
import org.polarsys.capella.core.data.capellacore.EnumerationPropertyType;
import org.polarsys.capella.core.data.capellacore.Involvement;
import org.polarsys.capella.core.data.capellacore.PropertyValueGroup;
import org.polarsys.capella.core.data.fa.ComponentExchange;
import org.polarsys.capella.core.data.fa.ComponentExchangeFunctionalExchangeAllocation;
import org.polarsys.capella.core.data.fa.ExchangeCategory;
import org.polarsys.capella.core.data.fa.FunctionInputPort;
import org.polarsys.capella.core.data.fa.FunctionOutputPort;
import org.polarsys.capella.core.data.fa.FunctionalChain;
import org.polarsys.capella.core.data.fa.FunctionalExchange;
import org.polarsys.capella.core.data.fa.FunctionalExchangeRealization;
import org.polarsys.capella.core.data.fa.FunctionalExchangeSpecification;
import org.polarsys.capella.core.data.information.ExchangeItem;
import org.polarsys.capella.core.data.interaction.SequenceMessage;
import org.polarsys.capella.core.data.requirement.Requirement;
import org.polarsys.kitalpha.emde.model.ElementExtension;

public class FunctionalExchangeMock implements FunctionalExchange {

	protected final String name;
	protected final EList<AbstractPropertyValue> appliedPropertyValues = new BasicEList<AbstractPropertyValue>();
	protected final EList<ExchangeItem> exchangedItems = new BasicEList<ExchangeItem>();

	public FunctionalExchangeMock(final String exchangeName) {
		name = exchangeName;
	}

	public void addAppliedPropertyValue(final AbstractPropertyValue value) {
		appliedPropertyValues.add(value);
	}

	public void addExchangedItem(final ExchangeItem item) {
		exchangedItems.add(item);
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
		return appliedPropertyValues;
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
	public AbstractInformationFlow getRealizedFlow() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRealizedFlow(final AbstractInformationFlow value) {
		// TODO Auto-generated method stub

	}

	@Override
	public EList<Involvement> getInvolvingInvolvements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isIsMulticast() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setIsMulticast(final boolean value) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isIsMultireceive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setIsMultireceive(final boolean value) {
		// TODO Auto-generated method stub

	}

	@Override
	public AbstractBehavior getTransformation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTransformation(final AbstractBehavior value) {
		// TODO Auto-generated method stub

	}

	@Override
	public AbstractBehavior getSelection() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSelection(final AbstractBehavior value) {
		// TODO Auto-generated method stub

	}

	@Override
	public RateKind getKindOfRate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setKindOfRate(final RateKind value) {
		// TODO Auto-generated method stub

	}

	@Override
	public ActivityPartition getInActivityPartition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public InterruptibleActivityRegion getInInterruptibleRegion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StructuredActivityNode getInStructuredNode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ValueSpecification getRate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRate(final ValueSpecification value) {
		// TODO Auto-generated method stub

	}

	@Override
	public ValueSpecification getProbability() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProbability(final ValueSpecification value) {
		// TODO Auto-generated method stub

	}

	@Override
	public ActivityNode getTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTarget(final ActivityNode value) {
		// TODO Auto-generated method stub

	}

	@Override
	public ActivityNode getSource() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSource(final ActivityNode value) {
		// TODO Auto-generated method stub

	}

	@Override
	public ValueSpecification getGuard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGuard(final ValueSpecification value) {
		// TODO Auto-generated method stub

	}

	@Override
	public ValueSpecification getWeight() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setWeight(final ValueSpecification value) {
		// TODO Auto-generated method stub

	}

	@Override
	public InterruptibleActivityRegion getInterrupts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInterrupts(final InterruptibleActivityRegion value) {
		// TODO Auto-generated method stub

	}

	@Override
	public EList<AbstractTypedElement> getAbstractTypedElements() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<SequenceMessage> getInvokingSequenceMessages() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<FunctionalExchangeSpecification> getExchangeSpecifications() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<FunctionalChain> getInvolvingFunctionalChains() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<ExchangeItem> getExchangedItems() {
		return exchangedItems;
	}

	@Override
	public EList<ComponentExchange> getAllocatingComponentExchanges() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<ComponentExchangeFunctionalExchangeAllocation> getIncomingComponentExchangeFunctionalExchangeRealizations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<FunctionalExchangeRealization> getIncomingFunctionalExchangeRealizations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<FunctionalExchangeRealization> getOutgoingFunctionalExchangeRealizations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<ExchangeCategory> getCategories() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<FunctionalExchangeRealization> getOwnedFunctionalExchangeRealizations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FunctionOutputPort getSourceFunctionOutputPort() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FunctionInputPort getTargetFunctionInputPort() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<FunctionalExchange> getRealizedFunctionalExchanges() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<FunctionalExchange> getRealizingFunctionalExchanges() {
		// TODO Auto-generated method stub
		return null;
	}

}
