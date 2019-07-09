package com.virtusa.galaxy.merchant;

import static com.virtusa.galaxy.merchant.Constants.GOLD;
import static com.virtusa.galaxy.merchant.Constants.IRON;
import static com.virtusa.galaxy.merchant.Constants.PER_UNIT_VALUE;
import static com.virtusa.galaxy.merchant.Constants.SILVER;
import static com.virtusa.galaxy.merchant.Constants.SYMBOL;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.virtusa.galaxy.merchant.exception.InvalidGalacticTransactionException;
import com.virtusa.galaxy.merchant.model.RareMetal;
import com.virtusa.galaxy.merchant.service.GalacticCurrencyServiceImpl;
import com.virtusa.galaxy.merchant.service.RareMetalService;
import com.virtusa.galaxy.merchant.service.RareMetalServiceImpl;
import com.virtusa.galaxy.merchant.service.RomanSymbolsFactory;

class RareMetalTest {

	private RareMetalService rareMetalService = new RareMetalServiceImpl();

	@BeforeEach
	void setUp() {
		GalacticCurrencyServiceImpl.createMasterGalacticCurrencies(
				TestDataUtils.galacticCurrencyAssignments,
				RomanSymbolsFactory.initRomanSymbols());
	}

	@Test
	void testGivenRareMetalAssignmentTransactionItShouldReturnCredits() {
		assertThat(rareMetalService.rareMetalsInTransactionLogs(List.of(Constants.GLOB_PROK_GOLD_IS_57800_CREDITS)).get(0)
				.getPerUnitValue().intValue()).isEqualTo(57800 / 4);
	}

	@Test
	void testGivenRareMetalAssignmentTransactionItShouldReturnCreditsException() {
		Assertions.assertThrows(InvalidGalacticTransactionException.class, () -> {
			rareMetalService.rareMetalsInTransactionLogs(List.of("glob prok Gold is 57800 Dedits"));
		});
	}

	@Test
	void testGivenRareMetalAssignmentTransactionItShouldReturnMetalException() {
		Assertions.assertThrows(InvalidGalacticTransactionException.class, () -> {
			rareMetalService.rareMetalsInTransactionLogs(List.of("glob prok Zinc was 57800 Credits"));
		});
	}

	@Test
	void testGivenRareMetalAssignmentTransactionItShouldReturnMetal() {
		assertThat(rareMetalService.rareMetalsInTransactionLogs(List.of(Constants.GLOB_PROK_GOLD_IS_57800_CREDITS)).get(0)
				.getSymbol()).isEqualTo(GOLD);
	}

	@Test
	public void givenInputWithoutCurrencyAssignmentsWhenSelectingRareMetalsFromTransactionsItShouldOnlyRareMetals() {
		final List<RareMetal> rareMetals = rareMetalService.rareMetalsInTransactionLogs(TestDataUtils.creditAssignmentTransactions);
		assertThat(rareMetals).extracting(SYMBOL).containsExactly(SILVER, GOLD, IRON);
		assertThat(rareMetals).extracting(PER_UNIT_VALUE).contains(BigDecimal.valueOf(17), BigDecimal.valueOf(195.5), BigDecimal.valueOf(14450));
	}

}
