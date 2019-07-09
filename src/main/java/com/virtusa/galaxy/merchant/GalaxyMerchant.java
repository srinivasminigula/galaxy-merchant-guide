/**
 * 
 */
package com.virtusa.galaxy.merchant;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.virtusa.galaxy.merchant.exception.GalaxyMerchantException;
import com.virtusa.galaxy.merchant.model.RareMetal;
import com.virtusa.galaxy.merchant.model.RomanSymbol;
import com.virtusa.galaxy.merchant.service.GalacticCurrencyExpressionService;
import com.virtusa.galaxy.merchant.service.GalacticCurrencyExpressionServiceImpl;
import com.virtusa.galaxy.merchant.service.GalacticCurrencyServiceImpl;
import com.virtusa.galaxy.merchant.service.RareMetalService;
import com.virtusa.galaxy.merchant.service.RareMetalServiceImpl;
import com.virtusa.galaxy.merchant.utils.CreditsTransactionsUtils;
import com.virtusa.galaxy.merchant.utils.GalacticCurrencyExpressionTransactionUtils;
import com.virtusa.galaxy.merchant.utils.InvalidQueriesUtils;

/**
 * @author srinivasminigula
 *
 */
public class GalaxyMerchant {
	private static final String HOW_MANY = "how many";
	private static final String HOW_MUCH_IS = "how much is";
	private final RareMetalService rareMetalService = new RareMetalServiceImpl();
	private GalacticCurrencyExpressionService galacticCurrencyService = new GalacticCurrencyExpressionServiceImpl();
	public static final String CREDITS = "Credits";

	public GalaxyMerchant() {
		super();
	}

	public void startTrading(List<String> inputs, List<RomanSymbol> romanSymbols) {
		if (null != inputs && !inputs.isEmpty()) {
			List<String> sanitizedInput = this.sanitizeInput(inputs);

			ArrayList<String> inputWithoutCurrencyAssignments = processGalasticCurrency(romanSymbols, sanitizedInput);
			final List<RareMetal> rareMetals = processMetalAssignments(inputWithoutCurrencyAssignments);

			processCurrencyTransactions(inputWithoutCurrencyAssignments);
			processCreditsTransactions(inputWithoutCurrencyAssignments, rareMetals);
			processInvalidQueries(inputWithoutCurrencyAssignments);

		} else {
			throw new GalaxyMerchantException();
		}
	}

	/**
	 * @param inputWithoutCurrencyAssignments
	 */
	private void processInvalidQueries(ArrayList<String> inputWithoutCurrencyAssignments) {
		InvalidQueriesUtils.print(inputWithoutCurrencyAssignments);
	}

	/**
	 * @param inputWithoutCurrencyAssignments
	 * @param rareMetals
	 */
	private void processCreditsTransactions(ArrayList<String> inputWithoutCurrencyAssignments,
			final List<RareMetal> rareMetals) {
		List<String> creditTransactions = selectGalacticCurrencyTransactions(inputWithoutCurrencyAssignments, HOW_MANY);
		CreditsTransactionsUtils.printCreditsTransactionsInTransactionLogs(rareMetals, creditTransactions,
				galacticCurrencyService.getGalacticCurrencyExpressions(creditTransactions));
		inputWithoutCurrencyAssignments.removeAll(creditTransactions);
	}

	/**
	 * @param inputWithoutCurrencyAssignments
	 */
	private void processCurrencyTransactions(ArrayList<String> inputWithoutCurrencyAssignments) {
		List<String> currencyTransactions = selectGalacticCurrencyTransactions(inputWithoutCurrencyAssignments,
				HOW_MUCH_IS);
		GalacticCurrencyExpressionTransactionUtils.printGalacticCurrencyExpressionInTransactionLogs(
				currencyTransactions, galacticCurrencyService.getGalacticCurrencyExpressions(currencyTransactions));
		inputWithoutCurrencyAssignments.removeAll(currencyTransactions);
	}

	/**
	 * @param inputWithoutCurrencyAssignments
	 * @return
	 */
	private List<RareMetal> processMetalAssignments(ArrayList<String> inputWithoutCurrencyAssignments) {
		// metals
		final List<String> metalTransactions = selectRareMetalPerUnitValueAssignmentTransactions(
				inputWithoutCurrencyAssignments);
		final List<RareMetal> rareMetals = rareMetalService.rareMetalsInTransactionLogs(metalTransactions);
		inputWithoutCurrencyAssignments.removeAll(metalTransactions);
		return rareMetals;
	}

	/**
	 * @param romanSymbols
	 * @param sanitizedInput
	 * @return
	 */
	private ArrayList<String> processGalasticCurrency(List<RomanSymbol> romanSymbols, List<String> sanitizedInput) {
		List<String> currencyAssignments = this.selectOnlyGalacticCurrencyAssignments(sanitizedInput, romanSymbols);
		GalacticCurrencyServiceImpl.createMasterGalacticCurrencies(currencyAssignments, romanSymbols);
		ArrayList<String> inputWithoutCurrencyAssignments = new ArrayList<>(sanitizedInput);
		inputWithoutCurrencyAssignments.removeAll(currencyAssignments);
		return inputWithoutCurrencyAssignments;
	}

	private List<String> sanitizeInput(List<String> inputs) {
		return inputs.stream().map(input -> input.trim().replaceAll(" +", " ")).collect(Collectors.toList());
	}

	/**
	 * Merchant Transaction length is three and third element of the array is a
	 * matching symbol in Roman symbols list.
	 * 
	 * @param merchantTransactions
	 * @param romanSymbols
	 * @return
	 */
	private List<String> selectOnlyGalacticCurrencyAssignments(List<String> merchantTransactions,
			List<RomanSymbol> romanSymbols) {
		return merchantTransactions.stream().filter(merchantTransaction -> {
			if (merchantTransaction.split(" ").length == 3) {
				final Character symbol = merchantTransaction.split(" ")[2].toCharArray()[0];
				final Optional<RomanSymbol> matchedRomanSymbol = romanSymbols.stream()
						.filter(romanSymbol -> romanSymbol.isSameSymbol(symbol)).findAny();
				return matchedRomanSymbol.isPresent();
			}
			return false;
		}).collect(Collectors.toList());
	}

	private List<String> selectGalacticCurrencyTransactions(List<String> inputWithoutCurrencyAssignments,
			String criteria) {
		return inputWithoutCurrencyAssignments.stream().filter(s -> s.startsWith(criteria))
				.collect(Collectors.toList());
	}

	private List<String> selectRareMetalPerUnitValueAssignmentTransactions(List<String> sanitizedInput) {
		return sanitizedInput.stream().filter(transaction -> transaction.endsWith(CREDITS))
				.collect(Collectors.toList());

	}
}
