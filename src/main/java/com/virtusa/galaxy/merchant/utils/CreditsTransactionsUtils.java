package com.virtusa.galaxy.merchant.utils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.virtusa.galaxy.merchant.exception.InvalidGalacticTransactionException;
import com.virtusa.galaxy.merchant.model.GalacticCurrency;
import com.virtusa.galaxy.merchant.model.GalacticCurrencyExpression;
import com.virtusa.galaxy.merchant.model.RareMetal;
import com.virtusa.galaxy.merchant.service.PrinterService;
import com.virtusa.galaxy.merchant.service.PrinterServiceImpl;

public class CreditsTransactionsUtils {
	private static PrinterService printerService = new PrinterServiceImpl();

	private CreditsTransactionsUtils() {

	}

	public static void printCreditsTransactionsInTransactionLogs(List<RareMetal> rareMetals,
			List<String> creditsTransactions, List<GalacticCurrencyExpression> galacticCurrencyExpressions) {

		final List<String> creditTransactionOutput = new ArrayList<>();
		for (int i = 0; i < creditsTransactions.size(); i++) {
			final String creditsTransaction = creditsTransactions.get(i);
			GalacticCurrencyExpression galacticCurrencyExpression = galacticCurrencyExpressions.get(i);
			creditTransactionOutput.add(
					formatSingleCreditTransactionOutput(creditsTransaction, rareMetals, galacticCurrencyExpression));
		}
		printerService.print(creditTransactionOutput);
	}

	private static String formatSingleCreditTransactionOutput(String creditsTransaction, List<RareMetal> rareMetals,
			GalacticCurrencyExpression galacticCurrencyExpression) {

		String[] creditTransactionComponents = creditsTransaction.split(" ");
		final String rareMetalSymbol = creditTransactionComponents[creditTransactionComponents.length - 2];
		Optional<RareMetal> rareMetal = RareMetal.selectBySymbol(rareMetalSymbol, rareMetals);
		if (rareMetal.isPresent()) {
			final BigDecimal creditsValue = rareMetal.get().getPerUnitValue()
					.multiply(BigDecimal.valueOf(galacticCurrencyExpression.getGalacticCurrencyExpressionValue()));
			return formatOutputStringForCreditsTransaction(creditsTransaction, creditsValue,
					galacticCurrencyExpression.getGalacticCurrencies());
		} else {
			throw new InvalidGalacticTransactionException("Rare metal not found in credit transaction");
		}

	}

	private static String formatOutputStringForCreditsTransaction(String creditsTransaction, BigDecimal creditsValue,
			List<GalacticCurrency> galacticCurrenciesInTransaction) {
		final String[] splits = creditsTransaction.split(" ");
		final List<String> galacticCurrenciesInTransactionOutput = Arrays.asList(splits).stream().filter(
				s -> galacticCurrenciesInTransaction.stream().anyMatch(galacticCurrency -> galacticCurrency.isSame(s)))
				.collect(Collectors.toList());
		galacticCurrenciesInTransactionOutput.add(splits[splits.length - 2]);
		galacticCurrenciesInTransactionOutput.add("is");
		galacticCurrenciesInTransactionOutput.add(creditsValue.stripTrailingZeros().toPlainString());
		galacticCurrenciesInTransactionOutput.add("Credits");
		return String.join(" ", galacticCurrenciesInTransactionOutput);
	}
}
