/**
 *
 */
package com.mk.numbertowords.test.processor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.mk.numbertowords.processor.NumberToWordProcessor;
import com.mk.numbertowords.processor.impl.NumberToWordProcessorImpl;

/**
 * @author muffa
 *
 */
@TestInstance(Lifecycle.PER_CLASS)
public class NumberToWordProcessorImplTest {

	private NumberToWordProcessor numerToWordProcessor;

	@BeforeAll
	public void setup() {
		numerToWordProcessor = new NumberToWordProcessorImpl();
	}

	@Test
	public void testConvertToNumber() {
		Assertions.assertEquals(numerToWordProcessor.convertNumberToWord("100"), "ONE HUNDRED");
	}

	@Test
	public void testConvertToNumberWhenIntegerMaxValue() {
		Assertions.assertEquals(numerToWordProcessor.convertNumberToWord(Integer.MAX_VALUE + ""),
				"TWO BILLION ONE HUNDRED FORTY SEVEN MILLION FOUR HUNDRED EIGHTY THREE THOUSAND SIX HUNDRED AND FORTY SEVEN");
	}

	@Test
	public void testConvertToNumberWhenIntegerMinValue() {
		Assertions.assertEquals(numerToWordProcessor.convertNumberToWord(Integer.MIN_VALUE + ""),
				"NEGATIVE TWO BILLION ONE HUNDRED FORTY SEVEN MILLION FOUR HUNDRED EIGHTY THREE THOUSAND SIX HUNDRED AND FORTY EIGHT");
	}

	@Test
	public void testConvertToNumberWhenNumberIs10() {
		Assertions.assertEquals(numerToWordProcessor.convertNumberToWord("10"), "TEN");
	}

	@Test
	public void testConvertToNumberWhenNumberIs31() {
		Assertions.assertEquals(numerToWordProcessor.convertNumberToWord("31"), "THIRTY ONE");
	}

	@Test
	public void testConvertToNumberWhenNumberIsNegative() {
		Assertions.assertEquals(numerToWordProcessor.convertNumberToWord("-9"), "NEGATIVE NINE");
	}

	@Test
	public void testConvertToNumberWhenNumberIsNegativeAndHasHundreds() {
		Assertions.assertEquals(numerToWordProcessor.convertNumberToWord("-500"), "NEGATIVE FIVE HUNDRED");
	}

	@Test
	public void testConvertToNumberWhenNumberIsNegativeAndHasTens() {
		Assertions.assertEquals(numerToWordProcessor.convertNumberToWord("-30"), "NEGATIVE THIRTY");
	}

	@Test
	public void testConvertToNumberWhenValueHasHundreds() {
		Assertions.assertEquals(numerToWordProcessor.convertNumberToWord("515"), "FIVE HUNDRED AND FIFTEEN");
	}

	@Test
	public void testConvertToNumberWhenValueHasTens() {
		Assertions.assertEquals(numerToWordProcessor.convertNumberToWord("45"), "FORTY FIVE");
	}

	@Test
	public void testConvertToNumberWhenValueHasThousands() {
		Assertions.assertEquals(numerToWordProcessor.convertNumberToWord("5150"),
				"FIVE THOUSAND ONE HUNDRED AND FIFTY");
	}

	@Test
	public void testConvertToNumberWhenValueIsEmpty() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> numerToWordProcessor.convertNumberToWord(""));
	}

	@Test
	public void testConvertToNumberWhenValueIsNotAnINteger() {
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> numerToWordProcessor.convertNumberToWord("10.00"));
	}

	@Test
	public void testConvertToNumberWhenValueIsNotANumber() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> numerToWordProcessor.convertNumberToWord("a20"));
	}

	@Test
	public void testConvertToNumberWhenValueIsNotInRange() {
		Assertions.assertThrows(IllegalArgumentException.class,
				() -> numerToWordProcessor.convertNumberToWord("100000000000000000000000"));
	}

	@Test
	public void testConvertToNumberWhenValueIsNull() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> numerToWordProcessor.convertNumberToWord(null));
	}
}
