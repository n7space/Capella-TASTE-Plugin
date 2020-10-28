// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders;

import java.util.Deque;
import java.util.List;

import org.polarsys.capella.core.data.capellacore.NamedElement;
import org.polarsys.capella.core.data.information.datavalue.DataValue;
import org.polarsys.capella.core.data.information.datavalue.EnumerationLiteral;

import com.n7space.capellatasteplugin.modelling.data.DataModel;
import com.n7space.capellatasteplugin.modelling.data.DataModelElement;
import com.n7space.capellatasteplugin.modelling.data.DataPackage;
import com.n7space.capellatasteplugin.modelling.data.EnumeratedDataType;
import com.n7space.capellatasteplugin.modelling.data.EnumeratedDataType.Literal;
import com.n7space.capellatasteplugin.utils.Issue;

/**
 * Interpretation provider for Capella enumerated element.
 */
public class EnumeratedElementInterpretationProvider extends BaseDataModelElementInterpetationProvider implements
		com.n7space.capellatasteplugin.capellaintegration.datamodelelementinterpretationproviders.DataModelElementInterpreter.DataModelElementInterpretationProvider {

	protected final DataModelElementInterpreter elementInterpreter;

	/**
	 * The constructor.
	 *
	 * @param memberInterpreter
	 *            Data model element interpreter for interpreting inner members
	 */
	public EnumeratedElementInterpretationProvider(final DataModelElementInterpreter memberInterpreter) {
		elementInterpreter = memberInterpreter;
	}

	/**
	 * Provides interpretation for the given Capella data model element.
	 *
	 * @param dataModel
	 *            The internal data model
	 * @param currentDataPackage
	 *            The current internal data package
	 * @param context
	 *            The current interpretation context
	 * @param interpretedElement
	 *            The element to be interpreted
	 * @param issues
	 *            [output] List of detected issues
	 * @return The element converted to the internal data model or null if the
	 *         interpretation failed
	 */
	@Override
	public DataModelElement interpretModelElement(final DataModel dataModel, final DataPackage currentDataPackage,
			final Deque<DataModelElement> context, final NamedElement interpretedElement, final List<Issue> issues) {
		final EnumeratedDataType dataType = new EnumeratedDataType(currentDataPackage, interpretedElement.getName(),
				interpretedElement.getId());
		currentDataPackage.addTypeDefinition(dataType);
		final org.polarsys.capella.core.data.information.datatype.Enumeration enumeratedElement = (org.polarsys.capella.core.data.information.datatype.Enumeration) interpretedElement;
		context.push(dataType);
		for (final DataValue literal : enumeratedElement.getOwnedDataValues())
			processLiteral(dataModel, currentDataPackage, context, dataType, literal, literal, issues);

		for (final EnumerationLiteral literal : enumeratedElement.getOwnedLiterals())
			processLiteral(dataModel, currentDataPackage, context, dataType, literal, literal.getDomainValue(), issues);
		context.pop();
		dataType.description = extractComment(enumeratedElement);
		return dataType;
	}

	protected void processLiteral(final DataModel dataModel, final DataPackage currentDataPackage,
			final Deque<DataModelElement> context, final EnumeratedDataType dataType, final DataValue literal,
			final DataValue domainValue, final List<Issue> issues) {
		final Literal enumeratedLiteralValue = new Literal(dataType, literal.getName(), literal.getId());
		context.push(enumeratedLiteralValue);
		dataType.literals.add(enumeratedLiteralValue);
		if (domainValue != null) {
			final DataModelElement member = elementInterpreter.interpretDataModelElement(dataModel, currentDataPackage,
					context, domainValue, issues);
			if (member != null) {
				enumeratedLiteralValue.domainValue = member;
			} else {
				issues.add(new Issue(Issue.Kind.Warning,
						"Cannot extract the domain value of element named " + domainValue.getName(), domainValue));
			}
		}
		context.pop();
	}

}
