package com.virtusa.galaxy.merchant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.virtusa.galaxy.merchant.exception.InvalidGalacticCurrencyExpressionException;
import com.virtusa.galaxy.merchant.exception.InvalidGalacticTransactionException;
import com.virtusa.galaxy.merchant.model.GalacticCurrency;
import com.virtusa.galaxy.merchant.model.GalacticCurrencyExpression;
import com.virtusa.galaxy.merchant.model.RareMetal;
import com.virtusa.galaxy.merchant.service.GalacticCurrencyExpressionService;
import com.virtusa.galaxy.merchant.service.GalacticCurrencyExpressionServiceImpl;
import com.virtusa.galaxy.merchant.service.GalacticCurrencyServiceImpl;
import com.virtusa.galaxy.merchant.service.RareMetalService;
import com.virtusa.galaxy.merchant.service.RareMetalServiceImpl;
import com.virtusa.galaxy.merchant.service.RomanSymbolsFactory;
import com.virtusa.galaxy.merchant.utils.CreditsTransactionsUtils;

public class CreditsTransactionsUtilsTest {
	private RareMetalService rareMetalService = new RareMetalServiceImpl();

	private GalacticCurrencyExpressionService galacticCurrencyService = new GalacticCurrencyExpressionServiceImpl();

	@BeforeEach
	void setUp() {
		GalacticCurrencyServiceImpl.createMasterGalacticCurrencies(TestDataUtils.galacticCurrencyAssignments,
				RomanSymbolsFactory.initRomanSymbols());
	}

	@Test
	public void whenSubstractionRuleFailsThrowInvalidExpressionException() {
		Assertions.assertThrows(InvalidGalacticCurrencyExpressionException.class, () -> {
			List<RareMetal> rareMetals = new ArrayList<>();
			rareMetals.add(new RareMetal("Iron", BigDecimal.TEN));
			rareMetals.add(new RareMetal("Silver", BigDecimal.TEN));
			rareMetals.add(new RareMetal("Gold", BigDecimal.TEN));
			List<GalacticCurrencyExpression> galacticCurrencyExpressions = new ArrayList<>();
			galacticCurrencyExpressions.add(new GalacticCurrencyExpression(TestDataUtils.galacticCurrencies));
			CreditsTransactionsUtils.printCreditsTransactionsInTransactionLogs(rareMetals,
					TestDataUtils.creditAssignmentTransactions, galacticCurrencyExpressions);
		});
	}

	@Test
	public void whenRareMetalNotFoundThrowInvalidGalacticTransactionException() {
		Assertions.assertThrows(InvalidGalacticTransactionException.class, () -> {
		List<GalacticCurrency> galacticCurrencyExpressionsss = List.of(TestDataUtils.tegj, TestDataUtils.glob);
		List<GalacticCurrencyExpression> galacticCurrencyExpressions = new ArrayList<>();
		galacticCurrencyExpressions.add(new GalacticCurrencyExpression(galacticCurrencyExpressionsss));
		CreditsTransactionsUtils.printCreditsTransactionsInTransactionLogs(
				rareMetalService.rareMetalsInTransactionLogs(TestDataUtils.creditAssignmentTransactions),
				TestDataUtils.creditAssignmentTransactions, galacticCurrencyExpressions);
		});
	}

	@Test
	public void whenValidDataPassed() {
		CreditsTransactionsUtils.printCreditsTransactionsInTransactionLogs(rareMetalService.rareMetalsInTransactionLogs(TestDataUtils.creditAssignmentTransactions), TestDataUtils.creditTxnsQuery,
				galacticCurrencyService.getGalacticCurrencyExpressions(TestDataUtils.creditTxnsQuery));
	}

	

}
