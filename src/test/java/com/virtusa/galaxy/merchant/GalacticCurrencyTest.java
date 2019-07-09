/**
 * 
 */
package com.virtusa.galaxy.merchant;

import static com.virtusa.galaxy.merchant.Constants.CREDITS;
import static com.virtusa.galaxy.merchant.Constants.E7;
import static com.virtusa.galaxy.merchant.Constants.GLOB;
import static com.virtusa.galaxy.merchant.Constants.GOLD;
import static com.virtusa.galaxy.merchant.Constants.HOW;
import static com.virtusa.galaxy.merchant.Constants.IRON;
import static com.virtusa.galaxy.merchant.Constants.IS;
import static com.virtusa.galaxy.merchant.Constants.MANY;
import static com.virtusa.galaxy.merchant.Constants.MUCH;
import static com.virtusa.galaxy.merchant.Constants.PISH;
import static com.virtusa.galaxy.merchant.Constants.PROK;
import static com.virtusa.galaxy.merchant.Constants.SILVER;
import static com.virtusa.galaxy.merchant.Constants.STRING_34;
import static com.virtusa.galaxy.merchant.Constants.STRING_3910;
import static com.virtusa.galaxy.merchant.Constants.STRING_57800;
import static com.virtusa.galaxy.merchant.Constants.SYMBOL;
import static com.virtusa.galaxy.merchant.Constants.TEGJ;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.virtusa.galaxy.merchant.enums.EnumGalaxySymbols;
import com.virtusa.galaxy.merchant.model.GalacticCurrency;
import com.virtusa.galaxy.merchant.service.GalacticCurrencyService;
import com.virtusa.galaxy.merchant.service.GalacticCurrencyServiceImpl;
import com.virtusa.galaxy.merchant.service.RomanSymbolsFactory;

/**
 * @author srinivasminigula
 *
 */
class GalacticCurrencyTest {
	
	GalacticCurrencyService galacticCurrencyService = new GalacticCurrencyServiceImpl();

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() {
		GalacticCurrencyServiceImpl.createMasterGalacticCurrencies(TestDataUtils.galacticCurrencyAssignments,
				RomanSymbolsFactory.initRomanSymbols());

	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() {
	}

	@Test
	void test() {

		Assertions.assertTrue(TestDataUtils.glob.isSame(EnumGalaxySymbols.glob.toString()));
		Assertions.assertTrue(TestDataUtils.prok.isSame(EnumGalaxySymbols.prok.toString()));
		Assertions.assertTrue(TestDataUtils.pish.isSame(EnumGalaxySymbols.pish.toString()));
		Assertions.assertTrue(TestDataUtils.tegj.isSame(EnumGalaxySymbols.tegj.toString()));

		Assertions.assertEquals(TestDataUtils.glob.isRepeatable(), TestDataUtils.glob.getRomanSymbol().isRepeatable());
		Assertions.assertEquals(TestDataUtils.prok.isRepeatable(), TestDataUtils.prok.getRomanSymbol().isRepeatable());
		Assertions.assertEquals(TestDataUtils.pish.isRepeatable(), TestDataUtils.pish.getRomanSymbol().isRepeatable());
		Assertions.assertEquals(TestDataUtils.tegj.isRepeatable(), TestDataUtils.tegj.getRomanSymbol().isRepeatable());

		Assertions.assertEquals(TestDataUtils.glob.isSubstractable(),
				TestDataUtils.glob.getRomanSymbol().isSubstractable());
		Assertions.assertEquals(TestDataUtils.prok.isSubstractable(),
				TestDataUtils.prok.getRomanSymbol().isSubstractable());
		Assertions.assertEquals(TestDataUtils.pish.isSubstractable(),
				TestDataUtils.pish.getRomanSymbol().isSubstractable());
		Assertions.assertEquals(TestDataUtils.tegj.isSubstractable(),
				TestDataUtils.tegj.getRomanSymbol().isSubstractable());
	}

	@Test
	void testIsGalaxyCurrencyValidSubstraction() {
		Assertions.assertEquals(TestDataUtils.glob.isSubstractable(),
				TestDataUtils.glob.getRomanSymbol().isSubstractable());

	}

	@Test
	void testDecimalValue() {
		Assertions.assertEquals(TestDataUtils.glob.getDecimalValue().intValue(),
				TestDataUtils.glob.getRomanSymbol().getValue().intValue());
	}

	@Test
	public void givenCreditCalculationTransactionWhenCreatingGalacticCurrenciesItShouldCreateGalacticCurrenciesFromTheCurrenciesInTheTransaction() {
		List<GalacticCurrency> galacticCurrenciesFromTransaction = galacticCurrencyService
				.createGalacticCurrenciesFromTransactions(
						List.of(HOW, MANY, CREDITS, IS, GLOB, PROK, SILVER, E7));
		assertThat(galacticCurrenciesFromTransaction).extracting(SYMBOL).containsExactly(GLOB, PROK);
	}

	@Test
	public void givenRareMetalAssignmentTransactionWhenCreatingGalacticCurrenciesItShouldCreateGalacticCurrenciesFromTheCurrenciesInTheTransaction() {
		List<GalacticCurrency> galacticCurrenciesFromTransaction = galacticCurrencyService
				.createGalacticCurrenciesFromTransactions(List.of(GLOB, GLOB, SILVER, IS, STRING_34, CREDITS));
		assertThat(galacticCurrenciesFromTransaction).extracting(SYMBOL).containsExactly(GLOB, GLOB);

		galacticCurrenciesFromTransaction = galacticCurrencyService
				.createGalacticCurrenciesFromTransactions(List.of(GLOB, PROK, GOLD, IS, STRING_57800, CREDITS));
		assertThat(galacticCurrenciesFromTransaction).extracting(SYMBOL).containsExactly(GLOB, PROK);

		galacticCurrenciesFromTransaction = galacticCurrencyService
				.createGalacticCurrenciesFromTransactions(List.of(PISH, IRON, IS, STRING_3910, CREDITS));
		assertThat(galacticCurrenciesFromTransaction).extracting(SYMBOL).containsExactly(PISH);

	}

	@Test
	public void givenGalacticCurrencyTransactionWhenCreatingGalacticCurrenciesItShouldCreateGalacticCurrenciesFromTheCurrenciesInTheTransaction() {
		List<GalacticCurrency> galacticCurrenciesFromTransaction = galacticCurrencyService
				.createGalacticCurrenciesFromTransactions(List.of(HOW, MUCH, IS, PISH, TEGJ, GLOB, E7));
		assertThat(galacticCurrenciesFromTransaction).extracting(SYMBOL).containsExactly(PISH, TEGJ, GLOB);
	}

	@Test
	public void givenTransactionWithZeroGalacticCurrenciesWhenCreatingGalacticCurrenciesItShouldThrowInvalidTransactionException() {
		List<GalacticCurrency> galacticCurrenciesFromTransaction = galacticCurrencyService
				.createGalacticCurrenciesFromTransactions(List.of(HOW, MANY, CREDITS, IS, SILVER, E7));
		assertThat(galacticCurrenciesFromTransaction).isEmpty();
	}
}
