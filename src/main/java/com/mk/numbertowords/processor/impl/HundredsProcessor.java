/**
 *
 */
package com.mk.numbertowords.processor.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mk.numbertowords.processor.NumberToWordProcessor;

/**
 * Processes numbers less than 999.
 *
 * @author muffa
 *
 */
public class HundredsProcessor implements NumberToWordProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(HundredsProcessor.class);
	private NumberToWordProcessor tensProcessor;
	private String separator;

	/**
	 * Takes a separator and initializes {@link TensProcessor}. Separator for
	 * numbers greater than 999 is empty for values below 999 it should be AND
	 *
	 * @param separator used for formatting values
	 */
	public HundredsProcessor(String separator) {
		this.tensProcessor = new TensProcessor();
		this.separator = separator;
	}

	/**
	 * Processes values below 999
	 */
	@Override
	public String convertNumberToWord(String value) {
		LOGGER.info("inside convertNumberToWord");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("processing value " + value);
		}
		int intValue;
		String output;
		String unitsOutput = "";
		String tensOutput = "";
		try {
			intValue = Math.abs(Integer.valueOf(value));
			if (intValue > 999) {
				throw new IllegalArgumentException("Unsupported number " + value);
			}
			int modulus = intValue % 100;
			int quotient = intValue / 100;
			if (quotient > 0) {
				tensOutput = tensProcessor.convertNumberToWord(String.valueOf(quotient));
			}
			if (intValue == 0 || modulus > 0) {
				unitsOutput = tensProcessor.convertNumberToWord(String.valueOf(modulus));
			}
		} catch (NumberFormatException nfex) {
			LOGGER.error(nfex.getMessage(), nfex);
			throw new IllegalArgumentException("Value isn't a valid number " + value);
		}
		if (!"".equals(tensOutput) && !"".equals(unitsOutput)) {
			output = tensOutput + " HUNDRED " + separator + unitsOutput;
		} else if (!"".equals(tensOutput) && "".equals(unitsOutput)) {
			output = tensOutput + " HUNDRED";
		} else {
			output = separator + unitsOutput;
		}
		return output;
	}

}
