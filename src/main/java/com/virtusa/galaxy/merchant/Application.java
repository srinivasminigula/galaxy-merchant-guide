/**
 * Main class to run the application
 */
package com.virtusa.galaxy.merchant;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.virtusa.galaxy.merchant.service.RomanSymbolsFactory;

/**
 * @author srinivasminigula
 *
 */
public class Application {

	/**
	 * @param args
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException, URISyntaxException {
		final List<String> input = Files.readAllLines(Paths.get(ClassLoader.getSystemResource("input.txt").toURI()));
		new GalaxyMerchant().startTrading(input, RomanSymbolsFactory.initRomanSymbols());
	}

}
