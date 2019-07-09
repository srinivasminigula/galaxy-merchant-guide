/**
 * 
 */
package com.virtusa.galaxy.merchant.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * @author srinivasminigula
 *
 */
public class RareMetal extends BaseSymbol {
	private final BigDecimal perUnitValue;

	public RareMetal(String symbol, BigDecimal perUnitValue) {
		super(symbol);
		this.perUnitValue = perUnitValue;
	}

	public BigDecimal getPerUnitValue() {
		return perUnitValue;
	}

	public static Optional<RareMetal> selectBySymbol(String rareMetalSymbol, List<RareMetal> rareMetals) {
		return rareMetals.stream().filter(rareMetal -> rareMetal.symbol.equals(rareMetalSymbol)).findFirst();
	}

}
