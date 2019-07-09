package com.virtusa.galaxy.merchant.enums;

public enum EnumRomanNumbers {

	I('I', 1), V('V', 5), X('X', 10), L('L', 50), C('C', 100), D('D', 500), M('M', 1000);

	private Integer value;
	private Character symbol;

	EnumRomanNumbers(Character symbol, Integer value) {
		this.symbol = symbol;
		this.value = value;
	}

	public Integer getValue() {
		return value;
	}

	public Character getSymbol() {
		return symbol;
	}

}