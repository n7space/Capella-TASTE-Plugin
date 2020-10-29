// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders;

import java.math.BigInteger;
import java.util.Deque;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.polarsys.capella.common.data.modellingcore.AbstractType;
import org.polarsys.capella.common.data.modellingcore.AbstractTypedElement;
import org.polarsys.capella.core.data.capellacore.CapellaElement;
import org.polarsys.capella.core.data.capellacore.GeneralizableElement;
import org.polarsys.capella.core.data.capellacore.NamedElement;
import org.polarsys.capella.core.data.capellacore.TypedElement;
import org.polarsys.capella.core.data.information.DataPkg;
import org.polarsys.capella.core.data.information.MultiplicityElement;
import org.polarsys.capella.core.data.information.datatype.PhysicalQuantity;
import org.polarsys.capella.core.data.information.datavalue.BinaryExpression;
import org.polarsys.capella.core.data.information.datavalue.BooleanReference;
import org.polarsys.capella.core.data.information.datavalue.DataValue;
import org.polarsys.capella.core.data.information.datavalue.LiteralNumericValue;
import org.polarsys.capella.core.data.information.datavalue.NumericReference;
import org.polarsys.capella.core.data.information.datavalue.NumericValue;
import org.polarsys.capella.core.data.information.datavalue.UnaryExpression;

import com.n7space.capellatasteplugin.capellaintegration.wrappers.LiteralNumericValueWrapper;
import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataModelElement;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.modelling.data.DataType;
import com.n7space.capellatasteplugin.modelling.data.DataType.DataTypeValue;
import com.n7space.capellatasteplugin.modelling.data.DataTypeReference;
import com.n7space.capellatasteplugin.modelling.data.EnumeratedDataType;
import com.n7space.capellatasteplugin.modelling.data.EnumeratedDataType.Literal;
import com.n7space.capellatasteplugin.modelling.data.MemberDefinition;
import com.n7space.capellatasteplugin.modelling.data.StructuredDataType;
import com.n7space.capellatasteplugin.modelling.data.Unit;
import com.n7space.capellatasteplugin.modelling.data.UsedNumericValue;
import com.n7space.capellatasteplugin.utils.BasicConfigurableItem;
import com.n7space.capellatasteplugin.utils.Issue;
import com.n7space.capellatasteplugin.utils.Issue.Kind;

/**
 * Base class for data model element interpretation providers.
 *
 * Provides common functionality reused by different concrete providers.
 */
public class BaseDataModelElementInterpetationProvider extends BasicConfigurableItem {

	/**
	 * Enumeration defining possible option handles for
	 * BaseDataModelElementInterpetationProvider.
	 *
	 * @see Option
	 */
	public static enum DataModelElementInterpretationProviderOption {
		/**
		 * Name separator.
		 */
		NameSeparator("NameSeparator");

		/**
		 * Prefix used in the option handles.
		 */
		public final static String NAME_CONVERTER_PREFIX = "com.n7space.capellatasteplugin.capellaintegration.modelelementinterpretationproviders.BaseDataModelElementInterpetationProvider.";

		private final String value;

		private DataModelElementInterpretationProviderOption(final String baseName) {
			value = baseName;
		}

		/**
		 * Returns the string value of the given handle.
		 *
		 * @return String value of the handle.
		 */
		@Override
		public String toString() {
			return NAME_CONVERTER_PREFIX + value;
		}
	}

	/**
	 * Returns the parent of the given Capella data package.
	 *
	 * @param pkg
	 *            The given data package
	 * @return The give data package's parent
	 */
	public static DataPkg getDataPackageParent(final DataPkg pkg) {
		if (pkg.eContainer() instanceof DataPkg) {
			final DataPkg parent = (DataPkg) pkg.eContainer();
			if ((parent.eContainer() == null) || !(parent.eContainer() instanceof DataPkg)) {
				// Ignore the default "container" package.
				return null;
			}
			return parent;
		}
		return null;
	}

	/**
	 * List of supported options.
	 */
	public final Option[] OPTIONS = {
			new Option(DataModelElementInterpretationProviderOption.NameSeparator, "Name separator", "-") };

	/**
	 * The constructor.
	 */
	public BaseDataModelElementInterpetationProvider() {
		addOptions(OPTIONS);
	}

	protected Integer calculateArithmeticExpressionValueUnsafe(
			final org.polarsys.capella.core.data.information.datavalue.BinaryOperator operator, final Integer left,
			final Integer right, final List<Issue> issues) throws Throwable {
		switch (operator) {
		case ADD:
			return left + right;
		case DIV:
			return left / right;
		case EQU:
			return left.equals(right) ? 1 : 0;
		case MAX:
			return Math.max(left, right);
		case MIN:
			return Math.min(left, right);
		case MUL:
			return left * right;
		case POW:
			return Integer.valueOf((int) Math.pow(left, right));
		case SUB:
			return left - right;
		default:
			return null;
		}
	}

	protected Integer calculateLogicExpressionValueUnsafe(
			final org.polarsys.capella.core.data.information.datavalue.BinaryOperator operator, final Integer left,
			final Integer right, final List<Issue> issues) throws Throwable {
		switch (operator) {
		case AND:
			return Integer.valueOf(left.intValue() & right.intValue());
		case IOR:
			return Integer.valueOf(left.intValue() | right.intValue());
		case XOR:
			return Integer.valueOf(left.intValue() ^ right.intValue());
		default:
			return null;
		}
	}

	protected Integer calculateOtherExpressionValueUnsafe(
			final org.polarsys.capella.core.data.information.datavalue.BinaryOperator operator, final Integer left,
			final Integer right, final List<Issue> issues) throws Throwable {
		switch (operator) {
		case UNSET:
			issues.add(
					new Issue(Issue.Kind.Error, "Operation not set for operands " + left + " and " + right, operator));
			return null;
		default:
			return null;
		}
	}

	protected Integer calculateExpressionValueUnsafe(
			final org.polarsys.capella.core.data.information.datavalue.BinaryOperator operator, final Integer left,
			final Integer right, final List<Issue> issues) throws Throwable {
		// This horrible construct has been committed to "reduce" Cyclomatic Complexity
		// number, because a "switch" has the
		// same CCN as a sequence of IFs, one for each case.
		switch (operator) {
		case AND:
		case IOR:
		case XOR:
			return calculateLogicExpressionValueUnsafe(operator, left, right, issues);
		case UNSET:
			return calculateOtherExpressionValueUnsafe(operator, left, right, issues);
		default:
			return calculateArithmeticExpressionValueUnsafe(operator, left, right, issues);
		}
	}

	protected Integer calculateExpressionValue(
			final org.polarsys.capella.core.data.information.datavalue.BinaryOperator operator, final Integer left,
			final Integer right, final List<Issue> issues) {
		try {
			return calculateExpressionValueUnsafe(operator, left, right, issues);
		} catch (final Throwable t) {
			issues.add(new Issue(Issue.Kind.Error, "Operation " + operator.getName()
					+ " could not be executed on operands " + left + " and " + right + ": " + t.toString(), operator));
		}
		return null;
	}

	protected Integer calculateExpressionValue(
			final org.polarsys.capella.core.data.information.datavalue.UnaryOperator operator, final Integer operand,
			final List<Issue> issues) {
		try {
			switch (operator) {
			case NOT:
				return -operand;
			case PRE:
				return operand - 1;
			case SUC:
				return operand + 1;
			case UNSET:
			case VAL:
			case POS:
				issues.add(new Issue(Issue.Kind.Error, "Unary operation " + operator.name() + " is not supported",
						operator));
				return null;
			}

		} catch (final Throwable t) {
			issues.add(new Issue(Issue.Kind.Error, "Operation " + operator.getName()
					+ " could not be executed on operand " + operand + ": " + t.toString(), operator));
		}
		return null;
	}

	protected LiteralNumericValue calculateLiteralNumericExpressionValue(
			final org.polarsys.capella.core.data.information.datavalue.BinaryOperator operator, final Integer left,
			final Integer right, final List<Issue> issues) {
		final Integer result = calculateExpressionValue(operator, left, right, issues);
		if (result == null) {
			return null;
		}
		final LiteralNumericValue wrapper = new LiteralNumericValueWrapper(operator.getName() + " result", result);
		return wrapper;
	}

	protected LiteralNumericValue calculateLiteralNumericExpressionValue(
			final org.polarsys.capella.core.data.information.datavalue.UnaryOperator operator, final Integer operand,
			final List<Issue> issues) {
		final Integer result = calculateExpressionValue(operator, operand, issues);
		if (result == null) {
			return null;
		}
		final LiteralNumericValue wrapper = new LiteralNumericValueWrapper(operator.getName() + " result", result);
		return wrapper;
	}

	protected String extractComment(final CapellaElement dataType) {
		final String description = dataType.getDescription();
		final String summary = dataType.getSummary();
		if (summary == null && description == null) {
			return null;
		}
		return ((summary != null ? summary + "\n" : "") + (description != null ? description : "")).trim();
	}

	protected Integer extractConcreteValue(final DataModel dataModel, final LiteralNumericValue value,
			final List<Issue> issues) {
		if (value == null)
			return null;
		try {
			return Integer.valueOf(value.getValue());
		} catch (final Throwable t) {
			return null;
		}
	}

	protected DataTypeReference extractSuperclass(final DataModel dataModel, final List<Issue> issues,
			final org.polarsys.capella.core.data.information.datatype.DataType type) {
		final EList<GeneralizableElement> parents = type.getSuper();
		if (parents != null) {
			if (parents.size() > 1) {
				issues.add(new Issue(Issue.Kind.Warning, "Type " + type.getName()
						+ " has multiple super classes - multiple inheritance is not supported", type));
			}
			if (parents.size() == 1) {
				return getDataTypeReference(dataModel, parents.get(0), issues);
			}
		}
		return null;
	}

	protected Unit extractUnit(final org.polarsys.capella.core.data.capellacore.Type type) {
		if (type instanceof PhysicalQuantity) {
			final PhysicalQuantity quantity = (PhysicalQuantity) type;
			final org.polarsys.capella.core.data.information.Unit quantityUnit = quantity.getUnit();
			if (quantityUnit == null) {
				return null;
			}
			return new Unit(quantityUnit.getName());

		}
		return null;
	}

	protected DataPackage getDataPackage(final DataModel model,
			final org.polarsys.capella.core.data.capellacore.Type type, final List<Issue> issues) {
		final EObject container = type.eContainer();
		if (container instanceof DataPkg) {
			final DataPkg pkg = (DataPkg) container;
			final DataPackage dataPackage = model.findDataPackageById(pkg.getId());
			if (dataPackage == null) {
				final DataPackage newDataPackage = new DataPackage(getDataPackageName(pkg), pkg.getId());
				model.dataPackages.add(newDataPackage);
				return newDataPackage;
			}
			return dataPackage;

		}
		issues.add(new Issue(Issue.Kind.Warning, "Could not find package for " + type.getName() + " type", type));
		return new DataPackage("Uknown", "");
	}

	/**
	 * Returns the qualified name of the given Capella data package.
	 *
	 * @param pkg
	 *            The given data package
	 * @return The qualified name
	 */
	public String getDataPackageName(final DataPkg pkg) {
		if (pkg == null) {
			return null;
		}
		final String prefix = getDataPackageName(getDataPackageParent(pkg));
		if (prefix != null) {
			return prefix + getStringOptionValue(DataModelElementInterpretationProviderOption.NameSeparator)
					+ pkg.getName();
		}
		return pkg.getName();
	}

	protected DataTypeReference getDataTypeReference(final DataModel model,
			final org.polarsys.capella.core.data.capellacore.Type type, final List<Issue> issues) {
		if (type == null)
			return null;
		final DataPackage pkg = getDataPackage(model, type, issues);
		if (!pkg.containsTypeDeclaration(type.getId())) {
			final Unit unit = extractUnit(type);
			final String name = ((unit != null) && (unit.name.length() > 0)) ? type.getName() + "-" + unit.name
					: type.getName();
			// Type declarations are not subject to unit postfixing during the
			// postprocessing.
			final DataType ghostType = new DataType(pkg, name, type.getId());
			pkg.addTypeDeclaration(ghostType);
		}
		final DataTypeReference r = new DataTypeReference(pkg, type.getName(), type.getId());
		return r;
	}

	protected UsedNumericValue getUsedNumericValue(final DataModel dataModel, final NumericValue value,
			final List<Issue> issues) {
		if (value == null) {
			return null;
		}
		final LiteralNumericValue literalValue = resolveLiteralNumericValue(dataModel, value, issues);
		if (literalValue == null) {
			issues.add(new Issue(Issue.Kind.Warning, "Cannot resolve value of element named " + value.getName()
					+ " of type " + value.getType().getName(), value));
			return null;
		}
		final DataTypeValue dataTypeValue = dataModel.findDataValueById(literalValue.getId());
		if (dataTypeValue != null) {
			return new UsedNumericValue.ReferencedNumericValue(dataTypeValue);
		}
		try {
			if ("*".equals(literalValue.getValue()))
				return new UsedNumericValue.SpecialNumericValue(UsedNumericValue.SpecialNumericValue.Kind.STAR);
			try {
				return new UsedNumericValue.ExplicitIntegerValue(
						BigInteger.valueOf(Long.parseLong(literalValue.getValue())));
			} catch (final NumberFormatException nfe) {
				return new UsedNumericValue.ExplicitRealValue(Double.parseDouble(literalValue.getValue()));
			}
		} catch (final Throwable t) {
			issues.add(new Issue(Issue.Kind.Warning, "Cannot parse the value of element named " + literalValue.getName()
					+ " of type " + value.getType().getName(), value));
			return null;
		}
	}

	protected String getValueName(final Deque<DataModelElement> context, final NamedElement interpretedElement) {
		final DataModelElement contextParent = context.peek();
		if (contextParent != null && (contextParent instanceof Literal)) {
			final Literal literal = (Literal) contextParent;
			final EnumeratedDataType enumerated = literal.containingType;
			return enumerated.name + getStringOptionValue(DataModelElementInterpretationProviderOption.NameSeparator)
					+ literal.name;
		}
		return interpretedElement.getName();
	}

	protected void intepretTypedMember(final DataModel dataModel, final DataPackage currentDataPackage,
			final StructuredDataType dataType, final AbstractTypedElement typedElement, final List<Issue> issues) {

		UsedNumericValue minCardinality = null;
		UsedNumericValue maxCardinality = null;
		if (typedElement instanceof MultiplicityElement) {
			minCardinality = getUsedNumericValue(dataModel, ((MultiplicityElement) typedElement).getOwnedMinCard(),
					issues);
			maxCardinality = getUsedNumericValue(dataModel, ((MultiplicityElement) typedElement).getOwnedMaxCard(),
					issues);
		}

		if (minCardinality == null) {
			minCardinality = new UsedNumericValue.ExplicitIntegerValue(BigInteger.valueOf(1));
		}
		if (maxCardinality == null) {
			maxCardinality = new UsedNumericValue.ExplicitIntegerValue(BigInteger.valueOf(1));
		}
		org.polarsys.capella.core.data.capellacore.Type type = null;
		if (typedElement instanceof TypedElement) {
			type = ((TypedElement) typedElement).getType();
		} else {
			final AbstractType abstractType = typedElement.getAbstractType();
			if (abstractType instanceof org.polarsys.capella.core.data.capellacore.Type) {
				type = (org.polarsys.capella.core.data.capellacore.Type) abstractType;
			}
		}

		if (type != null) {
			final MemberDefinition member = new MemberDefinition(typedElement.getName(), typedElement.getId(),
					getDataTypeReference(dataModel, type, issues), minCardinality, maxCardinality);
			dataType.members.add(member);
		} else {
			issues.add(new Issue(Kind.Warning, "Typed element " + typedElement.getName() + " does not have a type.",
					typedElement));
		}
	}

	protected LiteralNumericValue resolveBinaryExpression(final DataModel dataModel, final BinaryExpression expression,
			final List<Issue> issues) {
		final DataValue leftOperand = expression.getOwnedLeftOperand();
		final DataValue rightOperand = expression.getOwnedRightOperand();
		final LiteralNumericValue resolvedLeftValue = resolveLiteralNumericValue(dataModel, leftOperand, issues);
		final LiteralNumericValue resolvedRightValue = resolveLiteralNumericValue(dataModel, rightOperand, issues);
		final Integer concreteLeftValue = extractConcreteValue(dataModel, resolvedLeftValue, issues);
		final Integer concreteRightValue = extractConcreteValue(dataModel, resolvedRightValue, issues);
		if (concreteLeftValue == null) {
			return null;
		}
		if (concreteRightValue == null) {
			return null;
		}
		final LiteralNumericValue result = calculateLiteralNumericExpressionValue(expression.getOperator(),
				concreteLeftValue, concreteRightValue, issues);
		return result;
	}

	protected LiteralNumericValue resolveBooleanReference(final DataModel dataModel, final BooleanReference reference,
			final List<Issue> issues) {
		final DataValue value = reference.getReferencedValue();
		final LiteralNumericValue literalValue = resolveLiteralNumericValue(dataModel, value, issues);
		return literalValue;

	}

	protected LiteralNumericValue resolveLiteralNumericValue(final DataModel dataModel, final DataValue value,
			final List<Issue> issues) {
		if (value == null) {
			issues.add(new Issue(Kind.Warning, "Data value resolution failed", value));
			return null;
		}
		if (value instanceof LiteralNumericValue) {
			return (LiteralNumericValue) value;
		}
		if (value instanceof NumericReference) {
			final NumericReference reference = (NumericReference) value;
			return resolveLiteralNumericValue(dataModel, reference.getReferencedValue(), issues);
		}
		if (value instanceof BinaryExpression) {
			return resolveBinaryExpression(dataModel, (BinaryExpression) value, issues);
		}
		if (value instanceof UnaryExpression) {
			return resolveUnaryExpression(dataModel, (UnaryExpression) value, issues);
		}
		if (value instanceof BooleanReference) {
			return resolveBooleanReference(dataModel, (BooleanReference) value, issues);
		}
		issues.add(
				new Issue(Kind.Warning, "Numeric reference [" + value.getName() + "] is of unsupported type", value));
		return null;
	}

	protected LiteralNumericValue resolveUnaryExpression(final DataModel dataModel, final UnaryExpression expression,
			final List<Issue> issues) {
		final DataValue operand = expression.getOwnedOperand();
		final LiteralNumericValue resolvedValue = resolveLiteralNumericValue(dataModel, operand, issues);
		final Integer concreteValue = extractConcreteValue(dataModel, resolvedValue, issues);
		if (concreteValue == null) {
			return null;
		}
		final LiteralNumericValue result = calculateLiteralNumericExpressionValue(expression.getOperator(),
				concreteValue, issues);
		return result;

	}

}
