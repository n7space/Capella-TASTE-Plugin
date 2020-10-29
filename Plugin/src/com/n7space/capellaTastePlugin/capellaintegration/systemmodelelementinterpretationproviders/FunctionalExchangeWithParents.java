// N7 Space Sp. z o.o.
// n7space.com
// COPYRIGHT 2020

// Licensed under the terms of:
// Eclipse Public License available at http://www.eclipse.org/legal/epl-v10.html

package com.n7space.capellatasteplugin.capellaintegration.systemmodelelementinterpretationproviders;

import org.polarsys.capella.core.data.fa.FunctionalExchange;

import com.n7space.capellatasteplugin.modelling.architecture.Function;

/**
 * A Functional Exchange together with its source and target functions.
 *
 */
public class FunctionalExchangeWithParents {

	/**
	 * Functional Exchange.
	 */
	public final FunctionalExchange exchange;
	/**
	 * Source function.
	 */
	public final Function sourceFunction;
	/**
	 * Target function.
	 */
	public final Function targetFunction;

	/**
	 * The constructor.
	 * 
	 * @param exchangeSourceFunction
	 *            Source function
	 * @param exchangeTargetFunction
	 *            Target function
	 * @param childExchange
	 *            Functional Exchange between the source and target functions
	 */
	public FunctionalExchangeWithParents(final Function exchangeSourceFunction, final Function exchangeTargetFunction,
			final FunctionalExchange childExchange) {
		sourceFunction = exchangeSourceFunction;
		targetFunction = exchangeTargetFunction;
		exchange = childExchange;
	}

}
