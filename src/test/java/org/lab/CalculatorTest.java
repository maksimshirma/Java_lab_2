package org.lab;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Unit test for Calculator class.
 */
public class CalculatorTest {
    /**
     * Test enterExpression.
     * */
    @Test
    public void enterExpression() {
        final String expression = "1+2/3";
        Calculator calc = new Calculator();
        calc.enterExpression(expression);
        assertEquals(expression, calc.toString());
    }

    /**
     * Test calculate.
     * */
    @Test
    public void calculate() {
        final String expression1 = "(145 / (23 - 18) - 2) / 3";
        final String expression2 = "123 /100 * (4 - 2)";
        final String expression3 = "(168 + 2362) - 123 / 100 * (4 - 2)";
        final Double answer1 = 9.0;
        final Double answer2 = 2.46;
        final Double answer3 = 2527.54;

        Calculator calc = new Calculator();

        calc.enterExpression(expression1);
        Double calculated1 = Double.parseDouble(calc.calculate());
        assertEquals(answer1, calculated1);

        calc.enterExpression(expression2);
        Double calculated2 = Double.parseDouble(calc.calculate());
        assertEquals(calculated2, answer2);

        calc.enterExpression(expression3);
        Double calculated3 = Double.parseDouble(calc.calculate());
        assertEquals(calculated3, answer3);
    }
}
