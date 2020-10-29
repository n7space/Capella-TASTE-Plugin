// N7 Space Sp. z o.o.
// n7space.com
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
import org.polarsys.capella.common.data.modellingcore.AbstractType;
import org.polarsys.capella.core.data.capellacore.AbstractPropertyValue;
import org.polarsys.capella.core.data.capellacore.EnumerationPropertyLiteral;
import org.polarsys.capella.core.data.capellacore.EnumerationPropertyType;
import org.polarsys.capella.core.data.capellacore.PropertyValueGroup;
import org.polarsys.capella.core.data.capellacore.Type;
import org.polarsys.capella.core.data.information.Unit;
import org.polarsys.capella.core.data.information.datatype.NumericType;
import org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue;
import org.polarsys.capella.core.data.requirement.Requirement;
import org.polarsys.kitalpha.emde.model.ElementExtension;

/**
 * Class implementing the LiteralNumericValue interface and wrapping a concrete
 * Integer value.
 */
public class LiteralNumericValueWrapper implements LiteralNumericValue {

	protected final String name;
	protected final Integer value;

	/**
	 * The constructor.
	 *
	 * @param wrappedName
	 *            Name of the Integer to be wrapped
	 * @param wrappedValue
	 *            Value of the Integer to be wrapped
	 */
	public LiteralNumericValueWrapper(final String wrappedName, final Integer wrappedValue) {
		name = wrappedName;
		value = wrappedValue;
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public void destroy() {

	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public EList<Adapter> eAdapters() {
		return null;
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public TreeIterator<EObject> eAllContents() {
		return null;
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public EClass eClass() {
		return null;
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public EObject eContainer() {
		return null;
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public EStructuralFeature eContainingFeature() {
		return null;
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public EReference eContainmentFeature() {
		return null;
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public EList<EObject> eContents() {
		return null;
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public EList<EObject> eCrossReferences() {
		return null;
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public boolean eDeliver() {
		return false;
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public Object eGet(final EStructuralFeature feature) {
		return null;
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public Object eGet(final EStructuralFeature feature, final boolean resolve) {
		return null;
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public Object eInvoke(final EOperation operation, final EList<?> arguments) throws InvocationTargetException {
		return null;
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public boolean eIsProxy() {
		return false;
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public boolean eIsSet(final EStructuralFeature feature) {
		return false;
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public void eNotify(final Notification notification) {

	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public Resource eResource() {
		return null;
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public void eSet(final EStructuralFeature feature, final Object newValue) {

	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public void eSetDeliver(final boolean deliver) {

	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public void eUnset(final EStructuralFeature feature) {

	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public AbstractType getAbstractType() {
		return null;
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public EList<PropertyValueGroup> getAppliedPropertyValueGroups() {
		return null;
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public EList<AbstractPropertyValue> getAppliedPropertyValues() {
		return null;
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public EList<Requirement> getAppliedRequirements() {
		return null;
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public EList<AbstractConstraint> getConstraints() {
		return null;
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public String getDescription() {
		return null;
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public EList<EnumerationPropertyLiteral> getFeatures() {
		return null;
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public String getFullLabel() {
		return null;
	}

	/**
	 * Returns the ID of the literal numeric value.
	 * 
	 * @return Name of the wrapped Integer to be interpreted as an ID
	 */
	@Override
	public String getId() {
		return name;
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public EList<AbstractTrace> getIncomingTraces() {
		return null;
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public String getLabel() {
		return null;
	}

	/**
	 * Returns the name of the literal numeric value.
	 * 
	 * @return Name of the wrapped Integer
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public NumericType getNumericType() {
		return null;
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public EList<AbstractTrace> getOutgoingTraces() {
		return null;
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public EList<AbstractConstraint> getOwnedConstraints() {
		return null;
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public EList<EnumerationPropertyType> getOwnedEnumerationPropertyTypes() {
		return null;
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public EList<ElementExtension> getOwnedExtensions() {
		return null;
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public EList<PropertyValueGroup> getOwnedPropertyValueGroups() {
		return null;
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public EList<AbstractPropertyValue> getOwnedPropertyValues() {
		return null;
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public String getReview() {
		return null;
	}

	/**
	 * Returns the SID of the literal numeric value.
	 * 
	 * @return Name of the wrapped Integer to be interpreted as an SID
	 */
	@Override
	public String getSid() {
		return name;
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public EnumerationPropertyLiteral getStatus() {
		return null;
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public String getSummary() {
		return null;
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public Type getType() {
		return null;
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public Unit getUnit() {
		return null;
	}

	/**
	 * Returns the value of the wrapped Integer.
	 * 
	 * @return The value of the wrapped Integer
	 */
	@Override
	public String getValue() {
		return value.toString();
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public boolean hasUnnamedLabel() {
		return false;
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public boolean isAbstract() {
		return false;
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public boolean isVisibleInDoc() {
		return false;
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public boolean isVisibleInLM() {
		return false;
	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public void setAbstract(final boolean value) {

	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public void setAbstractType(final AbstractType value) {

	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public void setDescription(final String value) {

	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public void setId(final String value) {

	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public void setName(final String value) {

	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public void setReview(final String value) {

	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public void setSid(final String value) {

	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public void setStatus(final EnumerationPropertyLiteral value) {

	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public void setSummary(final String value) {

	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public void setUnit(final Unit value) {

	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public void setValue(final String value) {

	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public void setVisibleInDoc(final boolean value) {

	}

	/**
	 * Dummy method required by the LiteralNumericValue interface.
	 *
	 * @see org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue
	 *      {@inheritDoc}
	 */
	@Override
	public void setVisibleInLM(final boolean value) {

	}

}
