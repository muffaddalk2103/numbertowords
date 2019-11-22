/**
 *
 */
package com.mk.numbertowords;

import com.mk.numbertowords.processor.NumberToWordProcessor;
import com.mk.numbertowords.processor.impl.HundredsProcessor;

/**
 * @author muffa
 *
 */
public class NumberToWords {

	public static void main(String[] args) {
		NumberToWordProcessor numberToWords = new HundredsProcessor();
		System.out.println(numberToWords.convertNumberToWord("182"));
	}
}
