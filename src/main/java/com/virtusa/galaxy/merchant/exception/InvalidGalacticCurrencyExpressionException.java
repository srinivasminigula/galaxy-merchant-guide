/**
 * 
 */
package com.virtusa.galaxy.merchant.exception;

/**
 * @author srinivasminigula
 *
 */
public class InvalidGalacticCurrencyExpressionException extends RuntimeException {

	private static final long serialVersionUID = -5350393563068801367L;

	public InvalidGalacticCurrencyExpressionException() {
		super();
	}

	/**
	 * @param message
	 */
	public InvalidGalacticCurrencyExpressionException(String message) {
		super(message);
	}
}
