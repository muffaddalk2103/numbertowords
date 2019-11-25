/**
 *
 */
package com.mk.numbertowords.processor.impl;

import java.text.MessageFormat;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mk.numbertowords.processor.NumberToWordProcessor;

/**
 * This class acts as Service class for the REST controller. Pre-processes the
 * value before converting it
 *
 * @author muffa
 *
 */
public class NumberToWordProcessorImpl implements NumberToWordProcessor {
	private static final Logger LOGGER = LoggerFactory.getLogger(NumberToWordProcessorImpl.class);
	private ResourceBundle mybundle;
	private NumberToWordProcessor numberToWordProcessor;
	private NumberToWordProcessor hundredsProcessor;
	private NumberToWordProcessor tensProcessor;

	/**
	 * Initializes {@link HundredPlusProcessor}, {@link HundredsProcessor} &
	 * {@link TensProcessor}. Also initializes resource bundle.
	 */
	public NumberToWordProcessorImpl() {
		mybundle = ResourceBundle.getBundle("application");
		numberToWordProcessor = new HundredPlusProcessor(9);
		hundredsProcessor = new HundredsProcessor("AND ");
		tensProcessor = new TensProcessor();
	}

	/**
	 * Formats value before processing it. Divides values into Tens, Hundreds and
	 * Greater than Hundreds.
	 */
	@Override
	public String convertNumberToWord(String value) {
		LOGGER.info("inside convertNumberToWord");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("processing value " + value);
		}
		try {
			int intValue = Integer.parseInt(value);
			value = String.valueOf(intValue);// removes leading zeroes
			String data;
			String highValue = "";
			/* handles positive as well as negative values */
			if ((value.charAt(0) != '-' && value.length() > 3) || (value.charAt(0) == '-' && value.length() > 4)) {
				data = value.substring(value.length() - 3);/* gets last three digits and processes them separately */
				highValue = value.substring(0, value.length() - 3);/* gets all digits except for last 3 digits */
				/*
				 * If last 3 digits are zeroes process the value in on flow otherwise process
				 * last 3 digits separately. This helps in adding the "AND" separator correctly
				 */
				if (!data.equals("000")) {
					highValue = highValue + "000";
				} else {
					highValue = value;
					data = "";
				}
			} else {
				data = value;
			}
			String output;
			if ("".equals(highValue)) {
				if (Math.abs(intValue) < 100) {
					output = tensProcessor.convertNumberToWord(
							data);/* Process values less than 100 separately to avoid "AND" prefix */
				} else {
					output = hundredsProcessor.convertNumberToWord(data);/* executes for any values between 100 & 999 */
				}
			} else {
				output = numberToWordProcessor.convertNumberToWord(highValue);
				if (!"".equals(data)) {
					output = output + hundredsProcessor.convertNumberToWord(data);
				}
			}
			if (intValue < 0) {
				output = "NEGATIVE " + output;
			}
			return output;
		} catch (NumberFormatException nfex) {
			LOGGER.error(nfex.getMessage(), nfex);
			throw new IllegalArgumentException(MessageFormat.format(mybundle.getString("invalid.integer.message"),
					value, Integer.MIN_VALUE, Integer.MAX_VALUE));
		}
	}
}
