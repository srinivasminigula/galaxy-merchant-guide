/**
 * 
 */
package com.virtusa.galaxy.merchant.utils;

import java.util.List;

import com.virtusa.galaxy.merchant.service.PrinterService;
import com.virtusa.galaxy.merchant.service.PrinterServiceImpl;

/**
 * @author srinivasminigula
 *
 */
public class InvalidQueriesUtils {
	private static PrinterService printerService = new PrinterServiceImpl();
	public static final String NO_IDEA = "I have no idea what you are talking about";

	private InvalidQueriesUtils() {
	}

	public static void print(List<String> inputWithoutCurrencyAssignments) {
		inputWithoutCurrencyAssignments.forEach(invalidQuery -> {
			printerService.print(List.of(NO_IDEA));
		});

	}

}
