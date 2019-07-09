package com.virtusa.galaxy.merchant.service;

import java.util.List;

import com.virtusa.galaxy.merchant.model.RomanSymbol;

public class RomanSymbolsFactory {
	private RomanSymbolsFactory() {
		
	}
	public static List<RomanSymbol> initRomanSymbols() {
		RomanSymbol romanSymbolThousand = RomanSymbol.standaloneSymbol('M', 1000);
		RomanSymbol romanSymbolFiveHundred = RomanSymbol.standaloneSymbol('D', 500);
		RomanSymbol romanSymbolFifty = RomanSymbol.standaloneSymbol('L', 50);
		RomanSymbol romanSymbolFive = RomanSymbol.standaloneSymbol('V', 5);
		RomanSymbol romanSymbolHundred = RomanSymbol.repeatableAndSubtractableSymbol('C',
				List.of(romanSymbolFiveHundred, romanSymbolThousand), 100);
		RomanSymbol romanSymbolTen = RomanSymbol.repeatableAndSubtractableSymbol('X',
				List.of(romanSymbolFifty, romanSymbolHundred), 10);
		RomanSymbol romanSymbolOne = RomanSymbol.repeatableAndSubtractableSymbol('I',
				List.of(romanSymbolFive, romanSymbolTen), 1);
		return List.of(romanSymbolOne, romanSymbolFive, romanSymbolTen, romanSymbolFifty, romanSymbolHundred,
				romanSymbolFiveHundred, romanSymbolThousand);

	}
	
}
