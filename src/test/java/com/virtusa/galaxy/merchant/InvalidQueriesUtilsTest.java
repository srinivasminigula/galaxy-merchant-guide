/**
 * 
 */
package com.virtusa.galaxy.merchant;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.virtusa.galaxy.merchant.utils.InvalidQueriesUtils;

/**
 * @author srinivasminigula
 *
 */
class InvalidQueriesUtilsTest {

	@Test
	void test() {
		List<String> inputWithoutCurrencyAssignments = List.of("Invalid query 1?");
		InvalidQueriesUtils.print(inputWithoutCurrencyAssignments);
	}

}
