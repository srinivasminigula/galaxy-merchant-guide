/**
 * 
 */
package com.virtusa.galaxy.merchant.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.virtusa.galaxy.merchant.exception.InvalidGalacticTransactionException;
import com.virtusa.galaxy.merchant.model.GalacticCurrency;
import com.virtusa.galaxy.merchant.model.GalacticCurrencyExpression;
import com.virtusa.galaxy.merchant.model.RareMetal;

/**
 * @author srinivasminigula
 *
 */
public class RareMetalServiceImpl implements RareMetalService {

	private static final String IS = "is";
	public static final String CREDITS = "Credits";
	private final GalacticCurrencyService galacticCurrencyService = new GalacticCurrencyServiceImpl();

	/**
	 * 
	 */
	public RareMetalServiceImpl() {
		super();
	}

	/**
	 * @param rareMetalPerUnitValueAssignmentTransactions
	 * @param galacticCurrenciesMasterList
	 * @return
	 */
	public List<RareMetal> rareMetalsInTransactionLogs(List<String> rareMetalPerUnitValueAssignmentTransactions) {
		final List<RareMetal> rareMetalsInTransactionLogs = new ArrayList<>();
		for (String rareMetalPerUnitValueAssignmentTransaction : rareMetalPerUnitValueAssignmentTransactions) {
			GalacticCurrencyExpression galacticCurrencyExpression = getGalacticCurrenciesFromTranactionAndExpression(
					rareMetalPerUnitValueAssignmentTransaction);
			rareMetalsInTransactionLogs
					.add(createRareMetal(rareMetalPerUnitValueAssignmentTransaction, galacticCurrencyExpression));
		}
		return rareMetalsInTransactionLogs;
	}

	/**
	 * @param galacticCurrenciesMasterList
	 * @param rareMetalPerUnitValueAssignmentTransaction
	 * @return
	 */
	private GalacticCurrencyExpression getGalacticCurrenciesFromTranactionAndExpression(
			String rareMetalPerUnitValueAssignmentTransaction) {
		final List<GalacticCurrency> galacticCurrenciesInTransaction = galacticCurrencyService
				.createGalacticCurrenciesFromTransactions(
						Arrays.asList(rareMetalPerUnitValueAssignmentTransaction.split(" ")));
		return new GalacticCurrencyExpression(galacticCurrenciesInTransaction);
	}

	@Override
	public RareMetal createRareMetal(String assignmentTransaction,
			final GalacticCurrencyExpression galacticCurrenciesInTransaction) {
		final String rareMetalSymbol = extractRareMetalSymbol(assignmentTransaction);
		final Integer creditValue = extractCreditValueFromAssignmentTransaction(assignmentTransaction);
		BigDecimal perUnitValue = BigDecimal.valueOf(creditValue)
				.divide(BigDecimal.valueOf(galacticCurrenciesInTransaction.getGalacticCurrencyExpressionValue()));
		return new RareMetal(rareMetalSymbol, perUnitValue);
	}

	private String extractRareMetalSymbol(String assignmentTransaction) {
		final String[] components = assignmentTransaction.split(" ");
		for (int i = 0; i < components.length; i++) {
			if (components[i].equals(IS)) {
				return components[i - 1];
			}
		}
		throw new InvalidGalacticTransactionException("No Rare metal symbol in transaction");
	}

	/**
	 * @param assignmentTransaction
	 * @return
	 */
	private Integer extractCreditValueFromAssignmentTransaction(String assignmentTransaction) {
		final String[] components = assignmentTransaction.split(" ");
		for (int i = 0; i < components.length; i++) {
			if (components[i].equals(CREDITS) && !assignmentTransaction.contains("?")) {
				return Integer.valueOf(components[i - 1]);
			}
		}
		throw new InvalidGalacticTransactionException("No credits found in transaction");
	}
}
