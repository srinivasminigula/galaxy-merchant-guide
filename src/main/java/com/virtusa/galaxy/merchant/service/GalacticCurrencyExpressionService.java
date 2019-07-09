/**
 * 
 */
package com.virtusa.galaxy.merchant.service;

import java.util.List;

import com.virtusa.galaxy.merchant.model.GalacticCurrency;
import com.virtusa.galaxy.merchant.model.GalacticCurrencyExpression;

/**
 * @author srinivasminigula
 *
 */
public interface GalacticCurrencyExpressionService {

	void validateExpression(List<GalacticCurrency> galacticCurrencyExpression);

	Integer calculateExpressionValue(List<GalacticCurrency> galacticCurrencyExpression);

	List<GalacticCurrencyExpression> getGalacticCurrencyExpressions(
			List<String> transactionsWithGalacticCurrencyExpressions);

}
