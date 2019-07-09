/**
 * 
 */
package com.virtusa.galaxy.merchant.model;

import java.util.List;

import com.virtusa.galaxy.merchant.service.GalacticCurrencyExpressionService;
import com.virtusa.galaxy.merchant.service.GalacticCurrencyExpressionServiceImpl;

/**
 * @author srinivasminigula
 *
 */
public class GalacticCurrencyExpression {
	private final List<GalacticCurrency> galacticCurrencies;
	private final Integer galacticCurrencyExpressionValue;
	private final GalacticCurrencyExpressionService galacticCurrencyExpressionService = new GalacticCurrencyExpressionServiceImpl();

	public GalacticCurrencyExpression(List<GalacticCurrency> galacticCurrencies) {
		this.galacticCurrencies = galacticCurrencies;
		galacticCurrencyExpressionService.validateExpression(galacticCurrencies);
		this.galacticCurrencyExpressionValue = galacticCurrencyExpressionService.calculateExpressionValue(galacticCurrencies);
	}

	public List<GalacticCurrency> getGalacticCurrencies() {
		return galacticCurrencies;
	}

	public Integer getGalacticCurrencyExpressionValue() {
		return galacticCurrencyExpressionValue;
	}

}
