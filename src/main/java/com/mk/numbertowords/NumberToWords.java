/**
 *
 */
package com.mk.numbertowords;

import com.mk.numbertowords.processor.NumberToWordProcessor;
import com.mk.numbertowords.processor.impl.GreaterThanHundredProcessor;

/**
 * @author muffa
 *
 */
public class NumberToWords {

	public static void main(String[] args) {
		NumberToWordProcessor numberToWords = new GreaterThanHundredProcessor(9);
		System.out.println(numberToWords.convertNumberToWord(1111111111 + ""));
	}
}
