/**
 *
 */
package com.mk.numbertowords.processor.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mk.numbertowords.processor.NumberToWordProcessor;

/**
 * Processes values in range of 0 to 19
 *
 * @author muffa
 *
 */
public class UnitsProcessor implements NumberToWordProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(UnitsProcessor.class);
	private static final String[] UNITS = { "ZERO", "ONE", "TWO", "THREE", "FOUR", "FIVE", "SIX", "SEVEN", "EIGHT",
			"NINE", "TEN", "ELEVEN", "TWELVE", "THIRTEEN", "FIURTEEN", "FIFTEEN", "SIXTEEN", "SEVENTEEN", "EIGHTEEN",
			"NINETEEN" };

	/**
	 * Processes values in range of 0 to 19
	 */
	@Override
	public String convertNumberToWord(String value) {
		LOGGER.info("inside convertNumberToWord");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("processing value " + value);
		}
		int intValue;
		try {
			intValue = Math.abs(Integer.valueOf(value));
			if (intValue > 19) {
				throw new IllegalArgumentException("Unsupported number " + value);
			}
		} catch (NumberFormatException nfex) {
			LOGGER.error(nfex.getMessage(), nfex);
			throw new IllegalArgumentException("Value isn't a valid number " + value);
		}
		return UNITS[intValue];
	}

}
