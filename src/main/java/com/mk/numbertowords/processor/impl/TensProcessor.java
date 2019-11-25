/**
 *
 */
package com.mk.numbertowords.processor.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mk.numbertowords.processor.NumberToWordProcessor;

/**
 * Processes values in range of 0 to 99
 *
 * @author muffa
 *
 */
public class TensProcessor implements NumberToWordProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(TensProcessor.class);
	private static final String[] TENS = { "", "", "TWENTY", "THIRTY", "FORTY", "FIFTY", "SIXTY", "SEVENTY", "EIGHTY",
			"NINETY" };
	private NumberToWordProcessor unitsProcessor;

	public TensProcessor() {
		this(new UnitsProcessor());
	}

	public TensProcessor(NumberToWordProcessor unitsProcessor) {
		this.unitsProcessor = unitsProcessor;
	}

	/**
	 * Processes values in range of 0 to 99
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
			if (intValue > 99) {
				throw new IllegalArgumentException("Unsupported number " + value);
			}
			int modulus = intValue % 10;
			int quotient = intValue / 10;
			if (quotient > 1) {
				tensOutput = TENS[quotient];
				/* For values in range of 20 to 99 */
				if (modulus > 0) {
					unitsOutput = unitsProcessor.convertNumberToWord(String.valueOf(modulus));
				}
			} else {
				/* for values in range of 0 to 19 */
				unitsOutput = unitsProcessor.convertNumberToWord(String.valueOf(intValue));
			}
		} catch (NumberFormatException nfex) {
			LOGGER.error(nfex.getMessage(), nfex);
			throw new IllegalArgumentException("Value isn't a valid number " + value);
		}
		if (!"".equals(tensOutput) && !"".equals(unitsOutput)) {
			output = tensOutput + " " + unitsOutput;
		} else if (!"".equals(tensOutput) && "".equals(unitsOutput)) {
			output = tensOutput;
		} else {
			output = unitsOutput;
		}
		return output;
	}

}
