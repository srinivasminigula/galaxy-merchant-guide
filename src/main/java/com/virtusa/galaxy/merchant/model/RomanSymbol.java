/**
 * 
 */
package com.virtusa.galaxy.merchant.model;

import java.util.Collections;
import java.util.List;

/**
 * @author srinivasminigula
 *
 */
public class RomanSymbol {
	private final Character symbol;
	private final Integer value;
	private final Boolean repeatable;
	private final Boolean substractable;
	private final List<RomanSymbol> substractableFrom;

	public RomanSymbol(Character symbol, Boolean repeatable, Boolean substractable, List<RomanSymbol> substractableFrom,
			Integer value) {
		this.symbol = symbol;
		this.value = value;
		this.repeatable = repeatable;
		this.substractable = substractable;
		this.substractableFrom = substractableFrom;
	}

	public static RomanSymbol standaloneSymbol(Character symbol, Integer value) {
		return new RomanSymbol(symbol, Boolean.FALSE, Boolean.FALSE, Collections.emptyList(), value);
	}

	public static RomanSymbol repeatableAndSubtractableSymbol(Character symbol, List<RomanSymbol> subtractableFrom,
			Integer value) {
		return new RomanSymbol(symbol, Boolean.TRUE, Boolean.TRUE, subtractableFrom, value);
	}

	public Character getSymbol() {
		return symbol;
	}

	public Integer getValue() {
		return value;
	}

	public Boolean isRepeatable() {
		return repeatable;
	}

	public Boolean isSubstractable() {
		return substractable;
	}

	public List<RomanSymbol> getSubstractableFrom() {
		return substractableFrom;
	}

	public boolean isSameSymbol(Character symbol) {
		return this.symbol.equals(symbol);
	}

	@Override
	public String toString() {
		return String.format("RomanSymbol [symbol=%s]", symbol);
	}

}
