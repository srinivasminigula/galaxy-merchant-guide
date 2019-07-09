/**
 * 
 */
package com.virtusa.galaxy.merchant;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.virtusa.galaxy.merchant.exception.GalaxyMerchantException;
import com.virtusa.galaxy.merchant.service.RomanSymbolsFactory;

/**
 * @author srinivasminigula
 *
 */
class GalaxyMerchantTest {
	GalaxyMerchant galaxyMerchant;
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() {
		galaxyMerchant = new GalaxyMerchant();
	}

	@Test
	public void givenNullInput() {
		Assertions.assertThrows(GalaxyMerchantException.class, () -> {

		galaxyMerchant.startTrading(null, RomanSymbolsFactory.initRomanSymbols());
		});
	}
	/**
	 * @param args
	 * @throws URISyntaxException 
	 * @throws IOException 
	 */
	@Test
	public void givenAllTransactionShouldPrintValues() {
		galaxyMerchant.startTrading(TestDataUtils.inpuText, RomanSymbolsFactory.initRomanSymbols());
	}
}
