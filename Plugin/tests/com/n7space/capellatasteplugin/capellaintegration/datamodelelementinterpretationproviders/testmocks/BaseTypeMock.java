// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.testmocks;

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
import org.polarsys.capella.core.data.capellacommon.GenericTrace;
import org.polarsys.capella.core.data.capellacore.AbstractPropertyValue;
import org.polarsys.capella.core.data.capellacore.EnumerationPropertyLiteral;
import org.polarsys.capella.core.data.capellacore.EnumerationPropertyType;
import org.polarsys.capella.core.data.capellacore.GeneralizableElement;
import org.polarsys.capella.core.data.capellacore.Generalization;
import org.polarsys.capella.core.data.capellacore.NamingRule;
import org.polarsys.capella.core.data.capellacore.PropertyValueGroup;
import org.polarsys.capella.core.data.capellacore.PropertyValuePkg;
import org.polarsys.capella.core.data.capellacore.Trace;
import org.polarsys.capella.core.data.capellacore.TypedElement;
import org.polarsys.capella.core.data.capellacore.VisibilityKind;
import org.polarsys.capella.core.data.information.DataPkg;
import org.polarsys.capella.core.data.information.InformationRealization;
import org.polarsys.capella.core.data.information.datatype.DataType;
import org.polarsys.capella.core.data.information.datavalue.DataValue;
import org.polarsys.capella.core.data.requirement.Requirement;
import org.polarsys.capella.core.data.requirement.RequirementsTrace;
import org.polarsys.kitalpha.emde.model.ElementExtension;

public class BaseTypeMock {

	protected final String mockName;
	protected final String mockId;
	protected final DataPkg container;
	protected final GeneralizableElement parent;
	protected final String description;
	protected final String summary;

	public BaseTypeMock(final DataPkg pkg, final GeneralizableElement parentType, final String name, final String id,
			final String mockDescription) {
		mockName = name;
		mockId = id;
		container = pkg;
		parent = parentType;
		summary = null;
		description = mockDescription;
	}

	public BaseTypeMock(final DataPkg pkg, final GeneralizableElement parentType, final String name, final String id) {
		mockName = name;
		mockId = id;
		container = pkg;
		parent = parentType;
		summary = null;
		description = null;
	}

	public BaseTypeMock(final DataPkg pkg, final String name, final String id) {
		mockName = name;
		mockId = id;
		container = pkg;
		parent = null;
		summary = null;
		description = null;
	}

	public void destroy() {
		throw new RuntimeException("Not implemented");
	}

	public EList<Adapter> eAdapters() {
		throw new RuntimeException("Not implemented");
	}

	public TreeIterator<EObject> eAllContents() {
		throw new RuntimeException("Not implemented");
	}

	public EClass eClass() {
		throw new RuntimeException("Not implemented");
	}

	public EObject eContainer() {
		return container;
	}

	public EStructuralFeature eContainingFeature() {
		throw new RuntimeException("Not implemented");
	}

	public EReference eContainmentFeature() {
		throw new RuntimeException("Not implemented");
	}

	public EList<EObject> eContents() {
		throw new RuntimeException("Not implemented");
	}

	public EList<EObject> eCrossReferences() {
		throw new RuntimeException("Not implemented");
	}

	public boolean eDeliver() {
		throw new RuntimeException("Not implemented");
	}

	public Object eGet(final EStructuralFeature feature) {
		throw new RuntimeException("Not implemented");
	}

	public Object eGet(final EStructuralFeature feature, final boolean resolve) {
		throw new RuntimeException("Not implemented");
	}

	public Object eInvoke(final EOperation operation, final EList<?> arguments) throws InvocationTargetException {
		throw new RuntimeException("Not implemented");
	}

	public boolean eIsProxy() {
		throw new RuntimeException("Not implemented");
	}

	public boolean eIsSet(final EStructuralFeature feature) {
		throw new RuntimeException("Not implemented");
	}

	public void eNotify(final Notification notification) {
		throw new RuntimeException("Not implemented");
	}

	public Resource eResource() {
		throw new RuntimeException("Not implemented");
	}

	public void eSet(final EStructuralFeature feature, final Object newValue) {
		throw new RuntimeException("Not implemented");
	}

	public void eSetDeliver(final boolean deliver) {
		throw new RuntimeException("Not implemented");
	}

	public void eUnset(final EStructuralFeature feature) {
		throw new RuntimeException("Not implemented");
	}

	public EList<AbstractTypedElement> getAbstractTypedElements() {
		throw new RuntimeException("Not implemented");
	}

	public EList<PropertyValueGroup> getAppliedPropertyValueGroups() {
		throw new RuntimeException("Not implemented");
	}

	public EList<AbstractPropertyValue> getAppliedPropertyValues() {
		throw new RuntimeException("Not implemented");
	}

	public EList<Requirement> getAppliedRequirements() {
		throw new RuntimeException("Not implemented");
	}

	public EList<AbstractConstraint> getConstraints() {
		throw new RuntimeException("Not implemented");
	}

	public EList<GenericTrace> getContainedGenericTraces() {
		throw new RuntimeException("Not implemented");
	}

	public EList<RequirementsTrace> getContainedRequirementsTraces() {
		throw new RuntimeException("Not implemented");
	}

	public DataValue getDefaultValue() {
		throw new RuntimeException("Not implemented");
	}

	public String getDescription() {
		return description;
	}

	public EList<EnumerationPropertyLiteral> getFeatures() {
		throw new RuntimeException("Not implemented");
	}

	public String getFullLabel() {
		throw new RuntimeException("Not implemented");
	}

	public String getId() {
		return mockId;
	}

	public EList<AbstractTrace> getIncomingTraces() {
		throw new RuntimeException("Not implemented");
	}

	public String getLabel() {
		throw new RuntimeException("Not implemented");
	}

	public String getName() {
		return mockName;
	}

	public EList<NamingRule> getNamingRules() {
		throw new RuntimeException("Not implemented");
	}

	public DataValue getNullValue() {
		throw new RuntimeException("Not implemented");
	}

	public EList<AbstractTrace> getOutgoingTraces() {
		throw new RuntimeException("Not implemented");
	}

	public EList<AbstractConstraint> getOwnedConstraints() {
		throw new RuntimeException("Not implemented");
	}

	public EList<DataValue> getOwnedDataValues() {
		return new BasicEList<DataValue>();
	}

	public EList<EnumerationPropertyType> getOwnedEnumerationPropertyTypes() {
		throw new RuntimeException("Not implemented");
	}

	public EList<ElementExtension> getOwnedExtensions() {
		throw new RuntimeException("Not implemented");
	}

	public EList<Generalization> getOwnedGeneralizations() {
		throw new RuntimeException("Not implemented");
	}

	public EList<InformationRealization> getOwnedInformationRealizations() {
		throw new RuntimeException("Not implemented");
	}

	public EList<PropertyValueGroup> getOwnedPropertyValueGroups() {
		throw new RuntimeException("Not implemented");
	}

	public EList<PropertyValuePkg> getOwnedPropertyValuePkgs() {
		throw new RuntimeException("Not implemented");
	}

	public EList<AbstractPropertyValue> getOwnedPropertyValues() {
		throw new RuntimeException("Not implemented");
	}

	public EList<Trace> getOwnedTraces() {
		throw new RuntimeException("Not implemented");
	}

	public String getPattern() {
		throw new RuntimeException("Not implemented");
	}

	public EList<DataType> getRealizedDataTypes() {
		throw new RuntimeException("Not implemented");
	}

	public EList<DataType> getRealizingDataTypes() {
		throw new RuntimeException("Not implemented");
	}

	public String getReview() {
		throw new RuntimeException("Not implemented");
	}

	public String getSid() {
		throw new RuntimeException("Not implemented");
	}

	public EnumerationPropertyLiteral getStatus() {
		throw new RuntimeException("Not implemented");
	}

	public EList<GeneralizableElement> getSub() {
		throw new RuntimeException("Not implemented");
	}

	public EList<Generalization> getSubGeneralizations() {
		throw new RuntimeException("Not implemented");
	}

	public String getSummary() {
		return summary;
	}

	public EList<GeneralizableElement> getSuper() {
		if (parent == null) {
			return null;
		}
		final BasicEList<GeneralizableElement> result = new BasicEList<GeneralizableElement>();
		result.add(parent);
		return result;
	}

	public EList<Generalization> getSuperGeneralizations() {
		throw new RuntimeException("Not implemented");
	}

	public EList<TypedElement> getTypedElements() {
		throw new RuntimeException("Not implemented");
	}

	public VisibilityKind getVisibility() {
		throw new RuntimeException("Not implemented");
	}

	public boolean hasUnnamedLabel() {
		throw new RuntimeException("Not implemented");
	}

	public boolean isAbstract() {
		throw new RuntimeException("Not implemented");
	}

	public boolean isDiscrete() {
		throw new RuntimeException("Not implemented");
	}

	public boolean isFinal() {
		throw new RuntimeException("Not implemented");
	}

	public boolean isMaxInclusive() {
		throw new RuntimeException("Not implemented");
	}

	public boolean isMinInclusive() {
		throw new RuntimeException("Not implemented");
	}

	public boolean isVisibleInDoc() {
		throw new RuntimeException("Not implemented");
	}

	public boolean isVisibleInLM() {
		throw new RuntimeException("Not implemented");
	}

	public void setAbstract(final boolean value) {
		throw new RuntimeException("Not implemented");
	}

	public void setDescription(final String value) {
		throw new RuntimeException("Not implemented");
	}

	public void setDiscrete(final boolean value) {
		throw new RuntimeException("Not implemented");
	}

	public void setFinal(final boolean value) {
		throw new RuntimeException("Not implemented");
	}

	public void setId(final String value) {
		throw new RuntimeException("Not implemented");
	}

	public void setMaxInclusive(final boolean value) {
		throw new RuntimeException("Not implemented");
	}

	public void setMinInclusive(final boolean value) {
		throw new RuntimeException("Not implemented");
	}

	public void setName(final String value) {
		throw new RuntimeException("Not implemented");
	}

	public void setPattern(final String value) {
		throw new RuntimeException("Not implemented");
	}

	public void setReview(final String value) {
		throw new RuntimeException("Not implemented");
	}

	public void setSid(final String value) {
		throw new RuntimeException("Not implemented");
	}

	public void setStatus(final EnumerationPropertyLiteral value) {
		throw new RuntimeException("Not implemented");
	}

	public void setSummary(final String value) {
		throw new RuntimeException("Not implemented");
	}

	public void setVisibility(final VisibilityKind value) {
		throw new RuntimeException("Not implemented");
	}

	public void setVisibleInDoc(final boolean value) {
		throw new RuntimeException("Not implemented");
	}

	public void setVisibleInLM(final boolean value) {
		throw new RuntimeException("Not implemented");
	}

}
