// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.wrappers;

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
import org.polarsys.capella.core.data.capellacommon.GenericTrace;
import org.polarsys.capella.core.data.capellacommon.StateEvent;
import org.polarsys.capella.core.data.capellacore.AbstractPropertyValue;
import org.polarsys.capella.core.data.capellacore.EnumerationPropertyLiteral;
import org.polarsys.capella.core.data.capellacore.EnumerationPropertyType;
import org.polarsys.capella.core.data.capellacore.NamingRule;
import org.polarsys.capella.core.data.capellacore.PropertyValueGroup;
import org.polarsys.capella.core.data.capellacore.PropertyValuePkg;
import org.polarsys.capella.core.data.capellacore.Trace;
import org.polarsys.capella.core.data.capellacore.VisibilityKind;
import org.polarsys.capella.core.data.cs.Interface;
import org.polarsys.capella.core.data.cs.InterfacePkg;
import org.polarsys.capella.core.data.information.Association;
import org.polarsys.capella.core.data.information.Class;
import org.polarsys.capella.core.data.information.Collection;
import org.polarsys.capella.core.data.information.DataPkg;
import org.polarsys.capella.core.data.information.ExchangeItem;
import org.polarsys.capella.core.data.information.KeyPart;
import org.polarsys.capella.core.data.information.Unit;
import org.polarsys.capella.core.data.information.communication.Exception;
import org.polarsys.capella.core.data.information.communication.Message;
import org.polarsys.capella.core.data.information.communication.MessageReference;
import org.polarsys.capella.core.data.information.communication.Signal;
import org.polarsys.capella.core.data.information.datatype.DataType;
import org.polarsys.capella.core.data.information.datavalue.DataValue;
import org.polarsys.capella.core.data.requirement.Requirement;
import org.polarsys.capella.core.data.requirement.RequirementsTrace;
import org.polarsys.kitalpha.emde.model.ElementExtension;

/**
 * A wrapper class for InterfacePkg, which allows it to act as a DataPkg.
 */
public class InterfacePackageWrapper implements DataPkg {

	protected final InterfacePkg wrappedInterfacePackage;

	/**
	 * The constructor.
	 * 
	 * @param interfacePacakgeToBeWrapped
	 *            The wrapped interface package
	 */
	public InterfacePackageWrapper(final InterfacePkg interfacePacakgeToBeWrapped) {
		wrappedInterfacePackage = interfacePacakgeToBeWrapped;
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public void destroy() {
		wrappedInterfacePackage.destroy();
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public EList<Adapter> eAdapters() {
		return wrappedInterfacePackage.eAdapters();
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public TreeIterator<EObject> eAllContents() {
		return wrappedInterfacePackage.eAllContents();
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public EClass eClass() {
		return wrappedInterfacePackage.eClass();
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public EObject eContainer() {
		final EObject obj = wrappedInterfacePackage.eContainer();
		if (obj == null)
			return null;
		if (obj instanceof Interface) {
			return new InterfacePackageWrapper((InterfacePkg) obj);
		}
		return obj;
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public EStructuralFeature eContainingFeature() {
		return wrappedInterfacePackage.eContainingFeature();
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public EReference eContainmentFeature() {
		return wrappedInterfacePackage.eContainmentFeature();
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public EList<EObject> eContents() {
		return wrappedInterfacePackage.eContents();
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public EList<EObject> eCrossReferences() {
		return wrappedInterfacePackage.eCrossReferences();
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public boolean eDeliver() {
		return wrappedInterfacePackage.eDeliver();
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public Object eGet(final EStructuralFeature feature) {
		return wrappedInterfacePackage.eGet(feature);
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public Object eGet(final EStructuralFeature feature, final boolean resolve) {
		return wrappedInterfacePackage.eGet(feature, resolve);
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public Object eInvoke(final EOperation operation, final EList<?> arguments) throws InvocationTargetException {
		return wrappedInterfacePackage.eInvoke(operation, arguments);
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public boolean eIsProxy() {
		return wrappedInterfacePackage.eIsProxy();
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public boolean eIsSet(final EStructuralFeature feature) {
		return wrappedInterfacePackage.eIsSet(feature);
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public void eNotify(final Notification notification) {
		wrappedInterfacePackage.eNotify(notification);
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public Resource eResource() {
		return wrappedInterfacePackage.eResource();
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public void eSet(final EStructuralFeature feature, final Object newValue) {
		wrappedInterfacePackage.eSet(feature, newValue);

	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public void eSetDeliver(final boolean deliver) {
		wrappedInterfacePackage.eSetDeliver(deliver);
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public void eUnset(final EStructuralFeature feature) {
		wrappedInterfacePackage.eUnset(feature);

	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public EList<PropertyValueGroup> getAppliedPropertyValueGroups() {
		return wrappedInterfacePackage.getAppliedPropertyValueGroups();
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public EList<AbstractPropertyValue> getAppliedPropertyValues() {
		return wrappedInterfacePackage.getAppliedPropertyValues();
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public EList<Requirement> getAppliedRequirements() {
		return wrappedInterfacePackage.getAppliedRequirements();
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public EList<AbstractConstraint> getConstraints() {
		return null;
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public EList<GenericTrace> getContainedGenericTraces() {
		return null;
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public EList<RequirementsTrace> getContainedRequirementsTraces() {
		return null;
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public String getDescription() {
		return wrappedInterfacePackage.getDescription();
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public EList<EnumerationPropertyLiteral> getFeatures() {
		return wrappedInterfacePackage.getFeatures();
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public String getFullLabel() {
		return wrappedInterfacePackage.getFullLabel();
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public String getId() {
		return wrappedInterfacePackage.getId();
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public EList<AbstractTrace> getIncomingTraces() {
		return wrappedInterfacePackage.getIncomingTraces();
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public String getLabel() {
		return wrappedInterfacePackage.getLabel();
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public String getName() {
		return wrappedInterfacePackage.getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EList<NamingRule> getNamingRules() {
		return null;
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public EList<AbstractTrace> getOutgoingTraces() {
		return wrappedInterfacePackage.getOutgoingTraces();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EList<Association> getOwnedAssociations() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EList<Class> getOwnedClasses() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EList<Collection> getOwnedCollections() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EList<AbstractConstraint> getOwnedConstraints() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EList<DataPkg> getOwnedDataPkgs() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EList<DataType> getOwnedDataTypes() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EList<DataValue> getOwnedDataValues() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EList<EnumerationPropertyType> getOwnedEnumerationPropertyTypes() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EList<Exception> getOwnedExceptions() {
		return null;
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public EList<ExchangeItem> getOwnedExchangeItems() {
		return wrappedInterfacePackage.getOwnedExchangeItems();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EList<ElementExtension> getOwnedExtensions() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EList<KeyPart> getOwnedKeyParts() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EList<MessageReference> getOwnedMessageReferences() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EList<Message> getOwnedMessages() {
		return null;
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public EList<PropertyValueGroup> getOwnedPropertyValueGroups() {
		return wrappedInterfacePackage.getOwnedPropertyValueGroups();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EList<PropertyValuePkg> getOwnedPropertyValuePkgs() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EList<AbstractPropertyValue> getOwnedPropertyValues() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EList<Signal> getOwnedSignals() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EList<StateEvent> getOwnedStateEvents() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EList<Trace> getOwnedTraces() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public EList<Unit> getOwnedUnits() {
		return null;
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public String getReview() {
		return wrappedInterfacePackage.getReview();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getSid() {
		return null;
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public EnumerationPropertyLiteral getStatus() {
		return wrappedInterfacePackage.getStatus();
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public String getSummary() {
		return wrappedInterfacePackage.getSummary();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public VisibilityKind getVisibility() {
		return VisibilityKind.PUBLIC;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean hasUnnamedLabel() {
		return false;
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public boolean isVisibleInDoc() {
		return wrappedInterfacePackage.isVisibleInDoc();
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public boolean isVisibleInLM() {
		return wrappedInterfacePackage.isVisibleInLM();
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public void setDescription(final String value) {
		wrappedInterfacePackage.setDescription(value);

	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public void setId(final String value) {
		wrappedInterfacePackage.setId(value);
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public void setName(final String value) {
		wrappedInterfacePackage.setName(value);

	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public void setReview(final String value) {
		wrappedInterfacePackage.setReview(value);
	}

	@Override
	public void setSid(final String value) {
		// NOP
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public void setStatus(final EnumerationPropertyLiteral value) {
		wrappedInterfacePackage.setStatus(value);
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public void setSummary(final String value) {
		wrappedInterfacePackage.setSummary(value);
	}

	@Override
	public void setVisibility(final VisibilityKind value) {
		// NOP
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public void setVisibleInDoc(final boolean value) {
		wrappedInterfacePackage.setVisibleInDoc(value);
	}

	/**
	 * Wrapper method implemented by the contained InterfacePkg
	 * 
	 * @see org.polarsys.capella.core.data.cs.InterfacePkg {@inheritDoc}
	 */
	@Override
	public void setVisibleInLM(final boolean value) {
		wrappedInterfacePackage.setVisibleInLM(value);

	}

}
