/**
 *
 */
package com.mk.numbertowords.processor.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mk.numbertowords.processor.NumberToWordProcessor;

/**
 * Converts number to words, processes values greater than 999.
 *
 * @author muffa
 *
 */
public class HundredPlusProcessor implements NumberToWordProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(HundredPlusProcessor.class);
	private static final String[] SUFFIX = { "", "", "", " THOUSAND ", "", "", " MILLION ", "", "", " BILLION " };
	private NumberToWordProcessor previousRangeProcessor;
	private int divisor;
	private String suffixToUse;

	/**
	 * Creates objects for itself and a processor which can process a value lower
	 * than the current range.
	 *
	 * @param supportRange range to be supported, current maximum supported range is
	 *                     9
	 */
	public HundredPlusProcessor(int supportRange) {
		if (supportRange > 9) {
			throw new IllegalArgumentException("unsupported range " + supportRange);
		}
		if (supportRange <= 3) {
			this.previousRangeProcessor = new HundredsProcessor("");
		} else {
			this.previousRangeProcessor = new HundredPlusProcessor(supportRange - 3);
		}
		suffixToUse = SUFFIX[supportRange];
		/* Generates divisor */
		String output = "1";
		for (int i = 0; i < supportRange; i++) {
			output += "0";
		}
		divisor = Integer.parseInt(output);
	}

	/**
	 * Converts a number to word. Processes the value by dividing it with divisor
	 * and quotient and modulus are used with previousRangeProcessor to derive
	 * values.
	 */
	@Override
	public String convertNumberToWord(String value) {
		LOGGER.info("inside convertNumberToWord");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("processing value " + value);
		}
		String output;
		int intValue;
		String lowValue = "";
		String highValue = "";
		try {
			intValue = Math.abs(Integer.parseInt(value));
			int modulus = Math.abs(intValue % divisor);
			int quotient = Math.abs(intValue / divisor);
			if (quotient > 0) {
				highValue = previousRangeProcessor.convertNumberToWord(String.valueOf(quotient));
			}
			if (modulus > 0) {
				lowValue = previousRangeProcessor.convertNumberToWord(String.valueOf(modulus));
			}
		} catch (NumberFormatException nfex) {
			LOGGER.error(nfex.getMessage(), nfex);
			throw new IllegalArgumentException("Value isn't a valid number " + value);
		}
		if (!"".equals(highValue) && !"".equals(lowValue)) {
			output = highValue + suffixToUse + lowValue;
		} else if (!"".equals(highValue) && "".equals(lowValue)) {
			output = highValue + suffixToUse;
		} else {
			output = lowValue;
		}
		return output;
	}
}
