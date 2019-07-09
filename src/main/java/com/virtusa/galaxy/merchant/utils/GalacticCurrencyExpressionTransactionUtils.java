/**
 * 
 */
package com.virtusa.galaxy.merchant.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.virtusa.galaxy.merchant.model.GalacticCurrencyExpression;
import com.virtusa.galaxy.merchant.service.PrinterService;
import com.virtusa.galaxy.merchant.service.PrinterServiceImpl;

/**
 * @author srinivasminigula
 *
 */
public class GalacticCurrencyExpressionTransactionUtils {

	private static PrinterService printerService = new PrinterServiceImpl();

	private GalacticCurrencyExpressionTransactionUtils() {
	}

	public static void printGalacticCurrencyExpressionInTransactionLogs(List<String> galacticCurrencyTransactions,
			List<GalacticCurrencyExpression> galacticCurrencyExpressions) {

		List<String> expressionTransactionOutput = new ArrayList<>();
		if (galacticCurrencyTransactions.size() == galacticCurrencyExpressions.size()) {
			for (int i = 0; i < galacticCurrencyTransactions.size(); i++) {
				final String transaction = galacticCurrencyTransactions.get(i);
				final GalacticCurrencyExpression expression = galacticCurrencyExpressions.get(i);
				expressionTransactionOutput.add(formatOutputStringForExpressionTransaction(transaction, expression));
			}
		}
		printerService.print(expressionTransactionOutput);
	}

	private static String formatOutputStringForExpressionTransaction(final String expressionTransaction,
			final GalacticCurrencyExpression expression) {
		final String[] galaxyCurrenciesFromExpression = expressionTransaction.split(" ");
		final List<String> galacticCurrenciesInTransactionOutput = Arrays.asList(galaxyCurrenciesFromExpression)
				.stream().filter(s -> expression.getGalacticCurrencies().stream()
						.anyMatch(galacticCurrency -> galacticCurrency.isSame(s)))
				.collect(Collectors.toList());
		galacticCurrenciesInTransactionOutput.add("is");
		galacticCurrenciesInTransactionOutput.add(expression.getGalacticCurrencyExpressionValue().toString());
		return String.join(" ", galacticCurrenciesInTransactionOutput);
	}
}
