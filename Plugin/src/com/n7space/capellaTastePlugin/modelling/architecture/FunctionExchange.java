// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.modelling.architecture;

import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlTransient;

import com.n7space.capellatasteplugin.modelling.data.DataTypeReference;

/**
 * Class representing an abstract functional exchange
 *
 */
public class FunctionExchange extends ArchitectureElement {

	/**
	 * Enumeration listing possible exchange kinds.
	 *
	 */
	public static enum ExchangeKind {
		/**
		 * Sporadic (e.g. event).
		 */
		SPORADIC,
		/**
		 * Protected (e.g. synchronized operation).
		 */
		PROTECTED,
		/**
		 * Unprotected (e.g. unsynchronized operation).
		 */
		UNPROTECTED
	}

	/**
	 * Enumeration listing possible exchange types.
	 *
	 */
	public static enum ExchangeType {
		/**
		 * Data access.
		 */
		DATA,
		/**
		 * Asynchronous event.
		 */
		EVENT,
		/**
		 * Asynchronous flow.
		 */
		FLOW,
		/**
		 * Synchronous operation.
		 */
		OPERATION,
		/**
		 * Invalid type.
		 */
		INVALID
	}

	/**
	 * Enumeration listing possible parameter encodings.
	 *
	 */
	public static enum Encoding {
		/**
		 * Default
		 */
		DEFAULT,
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
	 * Class representing an exchange parameter.
	 *
	 */
	public static class ExchangeParameter {
		/**
		 * Name.
		 */
		public final String name;
		/**
		 * Whether is an output parameter.
		 */
		public final boolean isOutput;
		/**
		 * Reference to parameter's data type
		 */
		public final DataTypeReference type;

		/**
		 * A constructor.
		 *
		 * @param parameterName
		 *            Name
		 * @param parameterType
		 *            Data type reference
		 * @param isOutputParameter
		 *            Whether is an output parameter
		 */
		public ExchangeParameter(final String parameterName, final DataTypeReference parameterType,
				final boolean isOutputParameter) {
			name = parameterName;
			type = parameterType;
			isOutput = isOutputParameter;
		}
	}

	/**
	 * List of exchange parameters.
	 */
	public final List<ExchangeParameter> parameters = new LinkedList<FunctionExchange.ExchangeParameter>();
	/**
	 * Function requiring the exchange.
	 */
	@XmlTransient
	public Function requiringFunction;
	/**
	 * Function providing the exchange.
	 */
	@XmlTransient
	public Function providingFunction;
	/**
	 * Exchange type.
	 */
	public final ExchangeType type;
	/**
	 * Minimum execution time.
	 */
	public int minimumExecutionTimeMs = 0;
	/**
	 * Maximum execution time.
	 */
	public int maximumExecutionTimeMs = 1000;
	/**
	 * Deadline.
	 */
	public Integer deadlineMs = 0;
	/**
	 * Queue size.
	 */
	public int queueSize = 1;
	/**
	 * Exchange kind.
	 */
	public ExchangeKind exchangeKind = ExchangeKind.SPORADIC;
	/**
	 * Encoding used for the parameters.
	 */
	public Encoding encoding = Encoding.DEFAULT;

	/**
	 * A constructor.
	 * 
	 * @param exchangeSourceFunction
	 *            Source (requiring) function
	 * @param exchangeTargetFunction
	 *            Target (providing) function
	 * @param name
	 *            Name
	 * @param id
	 *            ID
	 * @param exchangeType
	 *            Type
	 */
	public FunctionExchange(final Function exchangeSourceFunction, final Function exchangeTargetFunction,
			final String name, final String id, final ExchangeType exchangeType) {
		super(exchangeSourceFunction, exchangeTargetFunction, name, id);
		requiringFunction = exchangeSourceFunction;
		providingFunction = exchangeTargetFunction;
		type = exchangeType;
	}

}
