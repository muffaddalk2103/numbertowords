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
import com.mk.numbertowords.processor.impl.UnitsProcessor;

/**
 * @author muffa
 *
 */
@TestInstance(Lifecycle.PER_CLASS)
public class UnitProcessorTest {

	private NumberToWordProcessor numerToWordProcessor;

	@BeforeAll
	public void setup() {
		numerToWordProcessor = new UnitsProcessor();
	}

	@Test
	public void testConvertToNumber() {
		Assertions.assertEquals(numerToWordProcessor.convertNumberToWord("19"), "NINETEEN");
	}

	@Test
	public void testConvertToNumberWhenNumberIsNegative() {
		Assertions.assertEquals(numerToWordProcessor.convertNumberToWord("-9"), "NINE");
	}

	@Test
	public void testConvertToNumberWhenValueBeyond19() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> numerToWordProcessor.convertNumberToWord("20"));
	}

	@Test
	public void testConvertToNumberWhenValueIsNotANumber() {
		Assertions.assertThrows(IllegalArgumentException.class, () -> numerToWordProcessor.convertNumberToWord("a20"));
	}
}
