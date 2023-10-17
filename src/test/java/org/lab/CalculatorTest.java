package org.lab;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for Calculator class.
 */
public class CalculatorTest {
    @Test
    public void enterExpression() {
        final String expression = "1+2/3";
        Calculator calc = new Calculator();
        calc.enterExpression(expression);
        assertEquals(expression, calc.toString());
    }

    @Test
    public void calculate() {
        final String expression = "(145 / (23 - 18) - 2) / 3";
        final Double answer = 9.0;
        Calculator calc = new Calculator();
        calc.enterExpression(expression);
        final Double calculated = Double.parseDouble(calc.calculate());
        assertEquals(calculated, answer);
    }
}
