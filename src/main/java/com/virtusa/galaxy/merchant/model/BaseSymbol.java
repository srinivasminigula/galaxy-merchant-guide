package com.virtusa.galaxy.merchant.model;

public class BaseSymbol {

	protected final String symbol;

	public BaseSymbol(String symbol) {
		super();
		this.symbol = symbol;
	}

	public boolean isSame(String symbol) {
		return this.symbol.equals(symbol);
	}

	public String getSymbol() {
		return symbol;
	}

}