/**
 * 
 */
package com.virtusa.galaxy.merchant.service;

import java.util.List;

import com.virtusa.galaxy.merchant.model.GalacticCurrencyExpression;
import com.virtusa.galaxy.merchant.model.RareMetal;

/**
 * @author srinivasminigula
 *
 */
public interface RareMetalService {

	RareMetal createRareMetal(String assignmentTransaction,
			GalacticCurrencyExpression galacticCurrenciesInTransaction);


	List<RareMetal> rareMetalsInTransactionLogs(List<String> transactions);

}
