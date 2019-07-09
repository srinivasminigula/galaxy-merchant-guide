/**
 * 
 */
package com.virtusa.galaxy.merchant;

import org.junit.jupiter.api.Test;

import com.virtusa.galaxy.merchant.service.GalacticCurrencyExpressionService;
import com.virtusa.galaxy.merchant.service.GalacticCurrencyExpressionServiceImpl;
import com.virtusa.galaxy.merchant.utils.GalacticCurrencyExpressionTransactionUtils;

/**
 * @author srinivasminigula
 *
 */
class GalacticCurrencyExpressionTransactionUtilsTest {
	private GalacticCurrencyExpressionService galacticCurrencyService = new GalacticCurrencyExpressionServiceImpl();

	@Test
	public void whenSubstractionRuleFailsThrowInvalidExpressionException3() {
		GalacticCurrencyExpressionTransactionUtils.printGalacticCurrencyExpressionInTransactionLogs(
				TestDataUtils.currecyQuery,
				galacticCurrencyService.getGalacticCurrencyExpressions(TestDataUtils.currecyQuery));
	}

}
