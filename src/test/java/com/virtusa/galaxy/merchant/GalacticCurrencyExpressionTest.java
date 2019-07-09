package com.virtusa.galaxy.merchant;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.virtusa.galaxy.merchant.exception.InvalidGalacticCurrencyExpressionException;
import com.virtusa.galaxy.merchant.model.GalacticCurrency;
import com.virtusa.galaxy.merchant.model.GalacticCurrencyExpression;
import com.virtusa.galaxy.merchant.service.GalacticCurrencyExpressionService;
import com.virtusa.galaxy.merchant.service.GalacticCurrencyExpressionServiceImpl;

class GalacticCurrencyExpressionTest {
	GalacticCurrencyExpressionService galacticCurrencyExpressionService = new GalacticCurrencyExpressionServiceImpl();


	@Test
	void testConstructorEmptyArgs() {
		new GalacticCurrencyExpression(List.of());
	}

	@Test
	public void whenNonRepeatCurrenciesRepeatItShouldThrowInvalidExpressionException() {
		Assertions.assertThrows(InvalidGalacticCurrencyExpressionException.class, () -> {
			galacticCurrencyExpressionService.validateExpression(List.of(TestDataUtils.prok, TestDataUtils.prok));
		});
	}

	@Test
	public void whenCurrenciesRepeatMoreThanLegalLimitItShouldThrowInvalidExpressionException() {
		Assertions.assertThrows(InvalidGalacticCurrencyExpressionException.class, () -> {
			galacticCurrencyExpressionService.validateExpression(
					List.of(TestDataUtils.glob, TestDataUtils.glob, TestDataUtils.glob, TestDataUtils.glob));
		});
	}

	@Test
	public void whenOnlyRepeatableCurrenciesRepeatItShouldPassValidations() {
		List<GalacticCurrency> repeatCurrencyMoreThanLegalLimitExpression = List.of(TestDataUtils.glob,
				TestDataUtils.glob, TestDataUtils.glob);
		galacticCurrencyExpressionService.validateExpression(repeatCurrencyMoreThanLegalLimitExpression);
	}


	@Test
	public void whenValidSmallerDenominationPrecededLargerDenominationItShouldPassValidations() {
		List<GalacticCurrency> smallerDenominationPrecedesLargerDenomination = List.of(
				TestDataUtils.pish, TestDataUtils.glob);
		galacticCurrencyExpressionService.validateExpression(smallerDenominationPrecedesLargerDenomination);
	}

	@Test
	public void whenBreakingSubstactRuleThrowsException() {
		Assertions.assertThrows(InvalidGalacticCurrencyExpressionException.class, () -> {
			galacticCurrencyExpressionService.validateExpression(List.of(TestDataUtils.glob, TestDataUtils.tegj));
		});
	}

	@Test
	public void whenInValidSmallerDenominationPrecededLargerDenominationItShouldThrowInvalidExpressionException() {
		List<GalacticCurrency> invalidSmallerDenominationPrecedesLargerDenomination = List.of(TestDataUtils.glob,
				TestDataUtils.tegj, TestDataUtils.pish, TestDataUtils.tegj, TestDataUtils.glob);
		Assertions.assertThrows(InvalidGalacticCurrencyExpressionException.class, () -> {
			galacticCurrencyExpressionService.validateExpression(invalidSmallerDenominationPrecedesLargerDenomination);
		});
	}

	@Test
	public void givenExpressionWithNoRepetitionOrSubtractionsWhenDecimalValueIsCalculatedItShouldReturnDecimalValue() {
		List<GalacticCurrency> noRepetitionOrSubstractions = List.of(TestDataUtils.tegj, TestDataUtils.pish,
				TestDataUtils.prok, TestDataUtils.glob);
		final Integer expressionValue = galacticCurrencyExpressionService
				.calculateExpressionValue(noRepetitionOrSubstractions);
		assertThat(expressionValue).isEqualTo(66);
	}

	@Test
	public void givenExpressionWithRepetitionsButNoSubtractionsWhenDecimalValueIsCalculatedItShouldReturnDecimalValue() {
		List<GalacticCurrency> noRepetitionOrSubstractions = List.of(TestDataUtils.tegj, TestDataUtils.pish,
				TestDataUtils.pish, TestDataUtils.pish, TestDataUtils.prok, TestDataUtils.glob, TestDataUtils.glob);
		final Integer expressionValue = galacticCurrencyExpressionService
				.calculateExpressionValue(noRepetitionOrSubstractions);
		assertThat(expressionValue).isEqualTo(87);

	}

	@Test
	public void givenExpressionWithNoRepetitionsButOneSubtractionWhenDecimalValueIsCalculatedItShouldReturnDecimalValue() {
		List<GalacticCurrency> noRepetitionOrSubstractions = List.of(TestDataUtils.tegj, TestDataUtils.pish,
				TestDataUtils.tegj);
		final Integer expressionValue = galacticCurrencyExpressionService
				.calculateExpressionValue(noRepetitionOrSubstractions);
		assertThat(expressionValue).isEqualTo(90);

	}

	@Test
	public void givenExpressionWithNoRepetitionsButMultipleSubtractionsWhenDecimalValueIsCalculatedItShouldReturnDecimalValue() {
		List<GalacticCurrency> noRepetitionOrSubstractions = List.of(TestDataUtils.tegj, TestDataUtils.pish,
				TestDataUtils.tegj, TestDataUtils.glob, TestDataUtils.prok, TestDataUtils.glob);
		final Integer expressionValue = galacticCurrencyExpressionService
				.calculateExpressionValue(noRepetitionOrSubstractions);
		assertThat(expressionValue).isEqualTo(95);

	}

	@Test
	public void givenExpressionWithMultipleRepetitionsAndMultipleSubtractionsWhenDecimalValueIsCalculatedItShouldReturnDecimalValue() {
		List<GalacticCurrency> noRepetitionOrSubstractions = List.of(TestDataUtils.tegj, TestDataUtils.tegj,
				TestDataUtils.pish, TestDataUtils.tegj, TestDataUtils.glob, TestDataUtils.prok, TestDataUtils.glob,
				TestDataUtils.glob);
		final Integer expressionValue = galacticCurrencyExpressionService
				.calculateExpressionValue(noRepetitionOrSubstractions);
		assertThat(expressionValue).isEqualTo(146);

	}
}
