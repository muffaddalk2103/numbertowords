/**
 *
 */
package com.mk.numbertowords.processor.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mk.numbertowords.processor.NumberToWordProcessor;

/**
 * @author muffa
 *
 */
public class GreaterThanHundredProcessor implements NumberToWordProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(GreaterThanHundredProcessor.class);
	private static final String[] SUFFIX = { "", "", " HUNDRED ", " THOUSAND ", "", "", " MILLION ", "", "",
			" BILLION " };
	private NumberToWordProcessor previousRangeProcessor;
	private int divisor;
	private String suffixToUse;

	public GreaterThanHundredProcessor(int supportRange) {
		if (supportRange < 3) {
			this.previousRangeProcessor = new HundredsProcessor();
		} else {
			this.previousRangeProcessor = new GreaterThanHundredProcessor(supportRange - 3);
		}
		suffixToUse = SUFFIX[supportRange];
		String output = "1";
		for (int i = 0; i < supportRange; i++) {
			output += "0";
		}
		divisor = Integer.parseInt(output);
	}

	@Override
	public String convertNumberToWord(String value) {
		LOGGER.info("inside convertNumberToWord");
		int intValue;
		String lowValue = null;
		String highValue = null;
		try {
			intValue = Math.abs(Integer.valueOf(value));
			int mod = intValue % divisor;
			int divident = intValue / divisor;
			if (divident > 0) {
				highValue = previousRangeProcessor.convertNumberToWord(divident + "");
				if (mod > 0) {
					lowValue = previousRangeProcessor.convertNumberToWord(mod + "");
				}
			} else {
				lowValue = previousRangeProcessor.convertNumberToWord("" + mod);
			}
		} catch (NumberFormatException nfex) {
			LOGGER.error(nfex.getMessage(), nfex);
			throw new IllegalArgumentException("Value isn't a valid number " + value);
		}
		if (highValue != null && lowValue != null) {
			return highValue + suffixToUse + lowValue;
		} else if (highValue != null && lowValue == null) {
			return highValue + suffixToUse;
		} else {
			return lowValue;
		}
	}
}
