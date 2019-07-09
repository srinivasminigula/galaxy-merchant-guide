package com.virtusa.galaxy.merchant.service;

import java.util.List;

public class PrinterServiceImpl implements PrinterService {

	@Override
	public void print(List<String> transactionOutput) {
		transactionOutput.stream().forEach(System.out::println);
	}

}
