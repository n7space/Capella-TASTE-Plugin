// N7 Space Sp. z o.o.
// www.n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.architecture.aadl;

public class AadlParameterFeature extends AadlFeature {

	/**
	 * Enumeration listing possible directions of a parameter.
	 *
	 */
	public static enum Direction {
		/**
		 * Direction IN.
		 */
		IN,
		/**
		 * Direction OUT.
		 */
		OUT
	}

	/**
	 * Enumeration listing possible parameter encodings.
	 *
	 */
	public static enum Encoding {
		/**
		 * Platform specific native encoding.
		 */
		NATIVE,
		/**
		 * Unaligned Packed Encoding Rules.
		 */
		UPER,
		/**
		 * Custom ACN encoding.
		 */
		ACN,
	}

	/**
	 * Data type name.
	 */
	public final String dataTypeName;

	/**
	 * Direction.
	 */
	public final Direction direction;

	/**
	 * Parameter encoding.
	 */
	public final Encoding encoding;

	/**
	 * A constructor.
	 * 
	 * @param elementName
	 *            Name
	 * @param parent
	 *            Parent
	 * @param parameterDirection
	 *            Direction
	 * @param parameterDataTypeName
	 *            Data type name
	 */
	public AadlParameterFeature(final String elementName, final AadlElement parent, final Direction parameterDirection,
			final String parameterDataTypeName, final Encoding parameterEncoding) {
		super(parent, elementName);
		dataTypeName = parameterDataTypeName;
		direction = parameterDirection;
		encoding = parameterEncoding;
	}

	/**
	 * {@inheritDoc}}
	 */
	@Override
	public String serializeToString(final UsageContext context, final String linePrefix) {
		final StringBuilder sb = new StringBuilder();
		sb.append(linePrefix + name + " : " + direction.name() + " PARAMETER DataView::" + dataTypeName + " {\n");
		sb.append(linePrefix + getIndent() + "Taste::encoding => " + encoding.name() + ";\n");
		sb.append(linePrefix + "};\n");
		return sb.toString();
	}

}
