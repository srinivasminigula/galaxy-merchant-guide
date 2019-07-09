/**
 * 
 */
package com.virtusa.galaxy.merchant.model;

/**
 * @author srinivasminigula
 *
 */
public class GalacticCurrency extends BaseSymbol {

	private final RomanSymbol romanSymbol;

	public GalacticCurrency(String symbol, RomanSymbol romanSymbol) {
		super(symbol);
		this.romanSymbol = romanSymbol;
	}

	public Boolean isRepeatable() {
		return this.romanSymbol.isRepeatable();
	}

	public Boolean isSubstractable() {
		return this.romanSymbol.isSubstractable();
	}

	public Integer getDecimalValue() {
		return this.romanSymbol.getValue();
	}

	public RomanSymbol getRomanSymbol() {
		return this.romanSymbol;
	}

	public Boolean isValidSubstraction(GalacticCurrency galacticCurrency) {
		return this.isSubstractable()
				&& this.romanSymbol.getSubstractableFrom().contains(galacticCurrency.getRomanSymbol());
	}

}
