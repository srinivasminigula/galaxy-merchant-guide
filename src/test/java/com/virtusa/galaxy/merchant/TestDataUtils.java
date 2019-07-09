package com.virtusa.galaxy.merchant;

import static com.virtusa.galaxy.merchant.Constants.GLOB_GLOB_SILVER_IS_34_CREDITS;
import static com.virtusa.galaxy.merchant.Constants.GLOB_PROK_GOLD_IS_57800_CREDITS;
import static com.virtusa.galaxy.merchant.Constants.PISH_PISH_IRON_IS_3910_CREDITS;

import java.util.List;

import com.virtusa.galaxy.merchant.enums.EnumGalaxySymbols;
import com.virtusa.galaxy.merchant.model.GalacticCurrency;
import com.virtusa.galaxy.merchant.service.RomanSymbolsFactory;
public class TestDataUtils {
	
	private TestDataUtils() {
	}
	protected static final List<String> galacticCurrencyAssignments = List.of(Constants.GLOB_IS_I, Constants.PROK_IS_V, Constants.PISH_IS_X, Constants.TEGJ_IS_L);
	protected static final List<String> creditAssignmentTransactions = List.of(GLOB_GLOB_SILVER_IS_34_CREDITS, GLOB_PROK_GOLD_IS_57800_CREDITS, PISH_PISH_IRON_IS_3910_CREDITS);
	protected static final List<String> currecyQuery = List.of(Constants.HOW_MUCH_IS_PISH_TEGJ_GLOB_GLOB);
	protected static final List<String> creditTxnsQuery = List.of(Constants.HOW_MANY_CREDITS_IS_GLOB_PROK_SILVER, Constants.HOW_MANY_CREDITS_IS_GLOB_PROK_GOLD, Constants.HOW_MANY_CREDITS_IS_GLOB_PROK_IRON);
	protected static final List<String> invalidQuery = List.of(Constants.HOW_MUCH_INVALID_QUERY_1);
	protected static final List<String> inpuText = List.of(Constants.GLOB_IS_I, Constants.PROK_IS_V,
			Constants.PISH_IS_X, Constants.TEGJ_IS_L, GLOB_GLOB_SILVER_IS_34_CREDITS, GLOB_PROK_GOLD_IS_57800_CREDITS,
			PISH_PISH_IRON_IS_3910_CREDITS, Constants.HOW_MUCH_IS_PISH_TEGJ_GLOB_GLOB,
			Constants.HOW_MANY_CREDITS_IS_GLOB_PROK_SILVER, Constants.HOW_MANY_CREDITS_IS_GLOB_PROK_GOLD,
			Constants.HOW_MANY_CREDITS_IS_GLOB_PROK_IRON, Constants.HOW_MUCH_INVALID_QUERY_1);
	public static final GalacticCurrency glob = new GalacticCurrency(EnumGalaxySymbols.glob.toString(), RomanSymbolsFactory.initRomanSymbols().get(0));
	public static final GalacticCurrency prok = new GalacticCurrency(EnumGalaxySymbols.prok.toString(), RomanSymbolsFactory.initRomanSymbols().get(1));
	public static final GalacticCurrency pish = new GalacticCurrency(EnumGalaxySymbols.pish.toString(), RomanSymbolsFactory.initRomanSymbols().get(2));
	public static final GalacticCurrency tegj = new GalacticCurrency(EnumGalaxySymbols.tegj.toString(), RomanSymbolsFactory.initRomanSymbols().get(3));
	
	protected static final List<GalacticCurrency> galacticCurrencies = List.of(glob, prok, pish, tegj);
}
