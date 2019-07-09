/**
 * 
 */
package com.virtusa.galaxy.merchant;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.virtusa.galaxy.merchant.enums.EnumRomanNumbers;
import com.virtusa.galaxy.merchant.model.RomanSymbol;

/**
 * @author srinivasminigula
 *
 */
class RomanSymbolTest {

	@Test
	void testStandaloneSymbol() {
		RomanSymbol romanSymbolThousand = RomanSymbol.standaloneSymbol(EnumRomanNumbers.M.getSymbol(), EnumRomanNumbers.M.getValue());
		Assertions.assertEquals(EnumRomanNumbers.M.getSymbol().charValue(), romanSymbolThousand.getSymbol().charValue());
		Assertions.assertEquals(EnumRomanNumbers.M.getValue().intValue(), romanSymbolThousand.getValue().intValue());
		Assertions.assertFalse(romanSymbolThousand.isRepeatable());
		Assertions.assertFalse(romanSymbolThousand.isSubstractable());
	}

	@Test
	void testRepeatableAndSubtractableSymbol() {
		RomanSymbol romanSymbolThousand = RomanSymbol.standaloneSymbol(EnumRomanNumbers.M.getSymbol(), EnumRomanNumbers.M.getValue());
		RomanSymbol romanSymbolFiveHundred = RomanSymbol.standaloneSymbol(EnumRomanNumbers.D.getSymbol(), EnumRomanNumbers.D.getValue());
		RomanSymbol romanSymbolHundred = RomanSymbol.repeatableAndSubtractableSymbol(EnumRomanNumbers.C.getSymbol(), List.of(romanSymbolFiveHundred, romanSymbolThousand), EnumRomanNumbers.C.getValue());
		Assertions.assertEquals(EnumRomanNumbers.C.getSymbol().charValue(), romanSymbolHundred.getSymbol().charValue());
		Assertions.assertEquals(EnumRomanNumbers.C.getValue().intValue(), romanSymbolHundred.getValue().intValue());
		Assertions.assertTrue(romanSymbolHundred.isRepeatable());
		Assertions.assertTrue(romanSymbolHundred.isSubstractable());
		Assertions.assertTrue(romanSymbolHundred.isSameSymbol(EnumRomanNumbers.C.getSymbol()));
		Assertions.assertEquals("RomanSymbol [symbol=C]", romanSymbolHundred.toString());
	}

}
