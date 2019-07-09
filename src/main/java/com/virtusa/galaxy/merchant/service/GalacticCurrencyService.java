/**
 * 
 */
package com.virtusa.galaxy.merchant.service;

import java.util.List;

import com.virtusa.galaxy.merchant.model.GalacticCurrency;

/**
 * @author srinivasminigula
 *
 */
public interface GalacticCurrencyService {

	List<GalacticCurrency> createGalacticCurrenciesFromTransactions(List<String> transactionComponents);
}
