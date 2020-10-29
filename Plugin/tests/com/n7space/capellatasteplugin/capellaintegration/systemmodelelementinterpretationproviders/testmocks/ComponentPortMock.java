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
import org.polarsys.capella.common.data.modellingcore.AbstractInformationFlow;
import org.polarsys.capella.common.data.modellingcore.AbstractTrace;
import org.polarsys.capella.common.data.modellingcore.AbstractType;
import org.polarsys.capella.core.data.capellacommon.StateMachine;
import org.polarsys.capella.core.data.capellacore.AbstractPropertyValue;
import org.polarsys.capella.core.data.capellacore.EnumerationPropertyLiteral;
import org.polarsys.capella.core.data.capellacore.EnumerationPropertyType;
import org.polarsys.capella.core.data.capellacore.PropertyValueGroup;
import org.polarsys.capella.core.data.capellacore.Type;
import org.polarsys.capella.core.data.capellacore.VisibilityKind;
import org.polarsys.capella.core.data.cs.Interface;
import org.polarsys.capella.core.data.cs.PhysicalPort;
import org.polarsys.capella.core.data.fa.ComponentExchange;
import org.polarsys.capella.core.data.fa.ComponentPort;
import org.polarsys.capella.core.data.fa.ComponentPortKind;
import org.polarsys.capella.core.data.fa.FunctionPort;
import org.polarsys.capella.core.data.fa.OrientationPortKind;
import org.polarsys.capella.core.data.information.AggregationKind;
import org.polarsys.capella.core.data.information.Association;
import org.polarsys.capella.core.data.information.PortAllocation;
import org.polarsys.capella.core.data.information.PortRealization;
import org.polarsys.capella.core.data.information.datavalue.DataValue;
import org.polarsys.capella.core.data.information.datavalue.NumericValue;
import org.polarsys.capella.core.data.interaction.InstanceRole;
import org.polarsys.capella.core.data.requirement.Requirement;
import org.polarsys.kitalpha.emde.model.ElementExtension;

public class ComponentPortMock implements ComponentPort {

	protected final String name;

	public ComponentPortMock(final String portName) {
		name = portName;
	}

	@Override
	public EList<PortRealization> getIncomingPortRealizations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<PortRealization> getOutgoingPortRealizations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<StateMachine> getOwnedProtocols() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<PortAllocation> getIncomingPortAllocations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<PortAllocation> getOutgoingPortAllocations() {
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
	public EList<PortRealization> getOwnedPortRealizations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<PortAllocation> getOwnedPortAllocations() {
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
	public EList<InstanceRole> getRepresentingInstanceRoles() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AggregationKind getAggregationKind() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAggregationKind(final AggregationKind value) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isIsDerived() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setIsDerived(final boolean value) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isIsReadOnly() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setIsReadOnly(final boolean value) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isIsPartOfKey() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setIsPartOfKey(final boolean value) {
		// TODO Auto-generated method stub

	}

	@Override
	public Association getAssociation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isIsAbstract() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setIsAbstract(final boolean value) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isIsStatic() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setIsStatic(final boolean value) {
		// TODO Auto-generated method stub

	}

	@Override
	public VisibilityKind getVisibility() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setVisibility(final VisibilityKind value) {
		// TODO Auto-generated method stub

	}

	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AbstractType getAbstractType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAbstractType(final AbstractType value) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isOrdered() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setOrdered(final boolean value) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isUnique() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setUnique(final boolean value) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isMinInclusive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setMinInclusive(final boolean value) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isMaxInclusive() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setMaxInclusive(final boolean value) {
		// TODO Auto-generated method stub

	}

	@Override
	public DataValue getOwnedDefaultValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOwnedDefaultValue(final DataValue value) {
		// TODO Auto-generated method stub

	}

	@Override
	public DataValue getOwnedMinValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOwnedMinValue(final DataValue value) {
		// TODO Auto-generated method stub

	}

	@Override
	public DataValue getOwnedMaxValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOwnedMaxValue(final DataValue value) {
		// TODO Auto-generated method stub

	}

	@Override
	public DataValue getOwnedNullValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOwnedNullValue(final DataValue value) {
		// TODO Auto-generated method stub

	}

	@Override
	public NumericValue getOwnedMinCard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOwnedMinCard(final NumericValue value) {
		// TODO Auto-generated method stub

	}

	@Override
	public NumericValue getOwnedMinLength() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOwnedMinLength(final NumericValue value) {
		// TODO Auto-generated method stub

	}

	@Override
	public NumericValue getOwnedMaxCard() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOwnedMaxCard(final NumericValue value) {
		// TODO Auto-generated method stub

	}

	@Override
	public NumericValue getOwnedMaxLength() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOwnedMaxLength(final NumericValue value) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isFinal() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setFinal(final boolean value) {
		// TODO Auto-generated method stub

	}

	@Override
	public EList<AbstractInformationFlow> getIncomingInformationFlows() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<AbstractInformationFlow> getOutgoingInformationFlows() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<AbstractInformationFlow> getInformationFlows() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OrientationPortKind getOrientation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOrientation(final OrientationPortKind value) {
		// TODO Auto-generated method stub

	}

	@Override
	public ComponentPortKind getKind() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setKind(final ComponentPortKind value) {
		// TODO Auto-generated method stub

	}

	@Override
	public EList<ComponentExchange> getComponentExchanges() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<FunctionPort> getAllocatedFunctionPorts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<ComponentPort> getDelegatedComponentPorts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<ComponentPort> getDelegatingComponentPorts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<PhysicalPort> getAllocatingPhysicalPorts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<ComponentPort> getRealizedComponentPorts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EList<ComponentPort> getRealizingComponentPorts() {
		// TODO Auto-generated method stub
		return null;
	}

}
