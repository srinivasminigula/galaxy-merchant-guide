/**
 * 
 */
package com.virtusa.galaxy.merchant.service;

import static com.virtusa.galaxy.merchant.constants.Constants.LEGAL_REPETITION_LIMIT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.virtusa.galaxy.merchant.exception.InvalidGalacticCurrencyExpressionException;
import com.virtusa.galaxy.merchant.model.BaseSymbol;
import com.virtusa.galaxy.merchant.model.GalacticCurrency;
import com.virtusa.galaxy.merchant.model.GalacticCurrencyExpression;

/**
 * @author srinivasminigula
 *
 */
public class GalacticCurrencyExpressionServiceImpl implements GalacticCurrencyExpressionService {

	private GalacticCurrencyService galacticCurrencyService = new GalacticCurrencyServiceImpl();

	public GalacticCurrencyExpressionServiceImpl() {
		super();
	}

	@Override
	public List<GalacticCurrencyExpression> getGalacticCurrencyExpressions(
			List<String> transactionsWithGalacticCurrencyExpressions) {
		final List<GalacticCurrencyExpression> galacticCurrencyExpressions = new ArrayList<>();
		for (String galacticCurrencyTransaction : transactionsWithGalacticCurrencyExpressions) {
			final List<String> galacticCurrencyTransactionComponents = Arrays
					.asList(galacticCurrencyTransaction.split(" "));
			final List<GalacticCurrency> galacticCurrencies = galacticCurrencyService
					.createGalacticCurrenciesFromTransactions(galacticCurrencyTransactionComponents);
			GalacticCurrencyExpression galacticCurrencyExpression = new GalacticCurrencyExpression(galacticCurrencies);
			galacticCurrencyExpressions.add(galacticCurrencyExpression);
		}
		return galacticCurrencyExpressions;
	}

	@Override
	public void validateExpression(List<GalacticCurrency> galacticCurrencyExpression) {
		validateOnlyRepeatableCurrenciesAreRepeated(galacticCurrencyExpression);
		validateCurrenciesRepeatOnlyValidNumberOfTimesConsecutively(LEGAL_REPETITION_LIMIT, galacticCurrencyExpression);
		substractionRule(galacticCurrencyExpression);
	}

	@Override
	public Integer calculateExpressionValue(List<GalacticCurrency> galacticCurrencyExpression) {
		final List<Integer> decimalValues = galacticCurrencyExpression.stream()
				.mapToInt(GalacticCurrency::getDecimalValue).boxed().collect(Collectors.toList());
		final List<Integer> additionsList = new ArrayList<>();
		for (int currentIndex = 0; currentIndex < decimalValues.size();) {
			Integer currentValue = decimalValues.get(currentIndex);
			Integer nextValue = hasReachedEndOfExpression(decimalValues, currentIndex) ? 0
					: decimalValues.get(currentIndex + 1);
			if (currentValue >= nextValue) {
				additionsList.add(currentValue);
				currentIndex++;
			} else {
				additionsList.add(nextValue - currentValue);
				currentIndex = currentIndex + 2;
			}
		}
		return additionsList.stream().mapToInt(Integer::intValue).sum();
	}

	private boolean hasReachedEndOfExpression(List<Integer> decimalValues, int currentIndex) {
		return currentIndex + 1 >= decimalValues.size();
	}

	private static void substractionRule(List<GalacticCurrency> galacticCurrencyExpression) {
		for (int index = 0; index < galacticCurrencyExpression.size() - 1; index++) {
			GalacticCurrency currentGalacticCurrencyInExpression = galacticCurrencyExpression.get(index);
			GalacticCurrency nextGalacticCurrencyInExpression = galacticCurrencyExpression.get(index + 1);
			if (currentGalacticCurrencyInExpression.getDecimalValue() < nextGalacticCurrencyInExpression
					.getDecimalValue()
					&& !currentGalacticCurrencyInExpression.isValidSubstraction(nextGalacticCurrencyInExpression)) {
				throw new InvalidGalacticCurrencyExpressionException();
			}
		}
	}

	private void validateCurrenciesRepeatOnlyValidNumberOfTimesConsecutively(int legalRepetitionLimit,
			List<GalacticCurrency> galacticCurrencyExpression) {
		for (int currentIndex = 0; currentIndex < galacticCurrencyExpression.size(); currentIndex++) {
			BaseSymbol candidateGalacticCurrency = galacticCurrencyExpression.get(currentIndex);
			int endIndexToCheckRepeatLimit = currentIndex + legalRepetitionLimit + 1;
			endIndexToCheckRepeatLimit = endIndexToCheckRepeatLimit > galacticCurrencyExpression.size()
					? galacticCurrencyExpression.size()
					: endIndexToCheckRepeatLimit;
			final List<GalacticCurrency> currenciesImmediatelyFollowingCurrentCurrency = galacticCurrencyExpression
					.subList(currentIndex + 1, endIndexToCheckRepeatLimit);
			if (legalRepetitionLimit > currenciesImmediatelyFollowingCurrentCurrency.size()) {
				break;
			}
			final boolean isRepeatLimitExceeded = currenciesImmediatelyFollowingCurrentCurrency.stream()
					.allMatch(galacticCurrency -> galacticCurrency.equals(candidateGalacticCurrency));
			if (isRepeatLimitExceeded) {
				throw new InvalidGalacticCurrencyExpressionException();
			}
		}
	}

	private static void validateOnlyRepeatableCurrenciesAreRepeated(List<GalacticCurrency> galacticCurrenciesList) {
		if (galacticCurrenciesList.stream().anyMatch(galacticCurrency -> !galacticCurrency.isRepeatable()
				&& Collections.frequency(galacticCurrenciesList, galacticCurrency) > 1)) {
			throw new InvalidGalacticCurrencyExpressionException("Non repeatables are repeating");
		}
	}

}
