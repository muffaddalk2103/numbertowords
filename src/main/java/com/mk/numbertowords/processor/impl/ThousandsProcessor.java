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
public class ThousandsProcessor implements NumberToWordProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(ThousandsProcessor.class);
	private NumberToWordProcessor hundredsProcessor;

	public ThousandsProcessor() {
		this(new HundredsProcessor());
	}

	public ThousandsProcessor(NumberToWordProcessor hundredsProcessor) {
		this.hundredsProcessor = hundredsProcessor;
	}

	@Override
	public String convertNumberToWord(String value) {
		LOGGER.info("inside convertNumberToWord");
		int intValue;
		String lowValue = null;
		String highValue = null;
		try {
			intValue = Math.abs(Integer.valueOf(value));
			if (intValue > 9999) {
				throw new IllegalArgumentException("Unsupported number " + value);
			}
			int mod = intValue % 1000;
			int divident = intValue / 1000;
			if (divident > 0) {
				highValue = hundredsProcessor.convertNumberToWord(divident + "");
				if (mod > 0) {
					lowValue = hundredsProcessor.convertNumberToWord(mod + "");
				}
			} else {
				lowValue = hundredsProcessor.convertNumberToWord("" + mod);
			}
		} catch (NumberFormatException nfex) {
			LOGGER.error(nfex.getMessage(), nfex);
			throw new IllegalArgumentException("Value isn't a valid number " + value);
		}
		if (highValue != null && lowValue != null) {
			return highValue + " THOUSAND " + lowValue;
		} else if (highValue != null && lowValue == null) {
			return highValue + " THOUSAND";
		} else {
			return lowValue;
		}
	}

}
