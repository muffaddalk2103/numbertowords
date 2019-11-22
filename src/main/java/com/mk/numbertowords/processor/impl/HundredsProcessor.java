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
public class HundredsProcessor implements NumberToWordProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(HundredsProcessor.class);
	private NumberToWordProcessor tensProcessor;

	public HundredsProcessor() {
		this(new TensProcessor());
	}

	public HundredsProcessor(NumberToWordProcessor tensProcessor) {
		this.tensProcessor = tensProcessor;
	}

	@Override
	public String convertNumberToWord(String value) {
		LOGGER.info("inside convertNumberToWord");
		int intValue;
		String unitsOutput = null;
		String tensOutput = null;
		try {
			intValue = Math.abs(Integer.valueOf(value));
			if (intValue > 999) {
				throw new IllegalArgumentException("Unsupported number " + value);
			}
			int mod = intValue % 100;
			int divident = intValue / 100;
			if (divident > 0) {
				tensOutput = tensProcessor.convertNumberToWord(divident + "");
				if (mod > 0) {
					unitsOutput = tensProcessor.convertNumberToWord(mod + "");
				}
			} else {
				unitsOutput = tensProcessor.convertNumberToWord("" + mod);
			}
		} catch (NumberFormatException nfex) {
			LOGGER.error(nfex.getMessage(), nfex);
			throw new IllegalArgumentException("Value isn't a valid number " + value);
		}
		if (tensOutput != null && unitsOutput != null) {
			return tensOutput + " HUNDRED " + unitsOutput;
		} else if (tensOutput != null && unitsOutput == null) {
			return tensOutput + " HUNDRED";
		} else {
			return unitsOutput;
		}
	}

}
