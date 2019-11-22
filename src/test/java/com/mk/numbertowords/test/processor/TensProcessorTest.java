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
import com.mk.numbertowords.processor.impl.TensProcessor;

/**
 * @author muffa
 *
 */
@TestInstance(Lifecycle.PER_CLASS)
public class TensProcessorTest {

	private NumberToWordProcessor numerToWordProcessor;

	@BeforeAll
	public void setup() {
		numerToWordProcessor = new TensProcessor();
	}

	@Test
	public void testConvertToNumber() {
		Assertions.assertEquals(numerToWordProcessor.convertNumberToWord("19"), "NINETEEN");
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
		Assertions.assertEquals(numerToWordProcessor.convertNumberToWord("-9"), "NINE");
	}

	@Test
	public void testConvertToNumberWhenNumberIsNegativeAndHasTens() {
		Assertions.assertEquals(numerToWordProcessor.convertNumberToWord("-30"), "THIRTY");
	}

	@Test
	public void testConvertToNumberWhenValueBeyond99() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> numerToWordProcessor.convertNumberToWord("100"));
	}

	@Test
	public void testConvertToNumberWhenValueHasTens() {
		Assertions.assertEquals(numerToWordProcessor.convertNumberToWord("45"), "FORTY FIVE");
	}

	@Test
	public void testConvertToNumberWhenValueIsNotANumber() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> numerToWordProcessor.convertNumberToWord("a20"));
	}
}
