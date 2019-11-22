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

	@Override
	public String convertNumberToWord(String value) {
		LOGGER.info("inside convertNumberToWord");
		int intValue;
		String unitsOutput = null;
		String tensOutput = null;
		try {
			intValue = Math.abs(Integer.valueOf(value));
			if (intValue > 99) {
				throw new IllegalArgumentException("Unsupported number " + value);
			}
			int mod = intValue % 10;
			int divident = intValue / 10;
			if (divident > 1) {
				tensOutput = TENS[divident];
				if (mod > 0) {
					unitsOutput = unitsProcessor.convertNumberToWord(mod + "");
				}
			} else {
				unitsOutput = unitsProcessor.convertNumberToWord("" + intValue);
			}
		} catch (NumberFormatException nfex) {
			LOGGER.error(nfex.getMessage(), nfex);
			throw new IllegalArgumentException("Value isn't a valid number " + value);
		}
		if (tensOutput != null && unitsOutput != null) {
			return tensOutput + " " + unitsOutput;
		} else if (tensOutput != null && unitsOutput == null) {
			return tensOutput;
		} else {
			return unitsOutput;
		}
	}

}
