/**
 * 
 */
package com.virtusa.galaxy.merchant.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.virtusa.galaxy.merchant.model.GalacticCurrency;
import com.virtusa.galaxy.merchant.model.RomanSymbol;

/**
 * @author srinivasminigula
 *
 */
public class GalacticCurrencyServiceImpl implements GalacticCurrencyService {
	private static List<GalacticCurrency> galacticCurrenciesMasterList;

	/**
	 * 
	 * @param galacticCurrencyAssignments
	 * @param romanSymbols
	 * @return
	 */
	public static void createMasterGalacticCurrencies(List<String> galacticCurrencyAssignments,
			List<RomanSymbol> romanSymbols) {
		galacticCurrenciesMasterList = galacticCurrencyAssignments.stream().map(galacticCurrency -> {
			final String[] transaction = galacticCurrency.split(" ");
			final String galacticCurrencySymbol = transaction[0];
			final Character romanValueSymbol = transaction[2].toCharArray()[0];
			final RomanSymbol selectedRomanSymbol = romanSymbols.stream()
					.filter(romanSymbol -> romanSymbol.isSameSymbol(romanValueSymbol)).findAny().get();
			return new GalacticCurrency(galacticCurrencySymbol, selectedRomanSymbol);
		}).collect(Collectors.toList());
	}

	public List<GalacticCurrency> createGalacticCurrenciesFromTransactions(List<String> transactionComponents) {

		List<GalacticCurrency> currencies = new ArrayList<>();
		if (null != galacticCurrenciesMasterList) {
			for (String transactionComponent : transactionComponents) {
				for (GalacticCurrency galacticCurrency : galacticCurrenciesMasterList) {
					if (galacticCurrency.isSame(transactionComponent)) {
						currencies.add(galacticCurrency);
					}
				}
			}
		}
		return currencies;

	}

}
