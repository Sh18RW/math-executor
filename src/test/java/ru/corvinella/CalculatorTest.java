package ru.corvinella;

import org.junit.Test;
import ru.corvinella.expressions.ExpressionsTree;
import ru.corvinella.expressions.entries.SequenceExpression;
import ru.corvinella.expressions.exceptions.ExpressionException;
import ru.corvinella.math.Calculator;
import ru.corvinella.math.exceptions.CalculatorException;
import ru.corvinella.parser.Parser;
import ru.corvinella.parser.exceptions.ParserIllegalTokenValueException;
import ru.corvinella.parser.exceptions.ParserUnknownEntityException;
import ru.corvinella.utils.math.Numbers;

import static org.junit.Assert.fail;

/**
 * @author sh18rw
 */
public class CalculatorTest {
    @Test
    public void testCalculator() throws ParserIllegalTokenValueException, ParserUnknownEntityException, ExpressionException, CalculatorException {
        makeTest("2 + 2", 4.0);
        makeTest("2 + 2 * 2", 6.0);
        makeTest("2 + 2 * 3 - 2 / 1 * 2 + 2 ^ 2 - 1", 7.0);
        makeTest("log(2, 4)", 2.0);
        makeTest("log(2, 8) * -0.5", -1.5);
        makeTest("(2 + (2 * 2 - (2))) * -2", -8.0);
        makeTest("(4 * 2) / (16 ^ 0)", 8.0);
        makeTest("Log(2, 8)", 3.0);
        makeTest("2 + (2 + 2) / (2 ^ 2)", 3.0);
        makeTest("2 * (3 * (5 - log(2, 16)) / 6)", 1.0);
        makeTest("Pi*2", Math.PI*2);
        makeTest("14 / (7 / (3 + (1 + (3/log(2, 4))) * 2 / 2 * 2) * (5 ^ 0 + 1))", 8.0);
        makeTest("1 + 2log(2;4) / 3 - 4", 1.0 + 2.0 * 2.0 / 3.0 - 4.0);
        makeTest("3*e", 3 * Math.E);
    }

    @Test
    public void testCalculatorAdditional() throws ParserIllegalTokenValueException, ParserUnknownEntityException, ExpressionException, CalculatorException {
        makeTest("tg0", 0.0);
        makeTest("tg(Pi/4)", 1.0);
        makeTest("-tg(Pi/4)", -1.0);
        makeTest("tg(log(100, tg(Pi/log(2, 4^(1+1)))))", 0.0);
        makeTest("5^-2", 0.04);
        makeTest("tg(Pi/3)", 1.0, false);
        makeTest("tg3.14", 0.0, false);
    }

    public void makeTest(String expression, Double expected) throws ParserIllegalTokenValueException, ParserUnknownEntityException, ExpressionException, CalculatorException {
        makeTest(expression, expected, true);
    }

    public void makeTest(String expression, Double expected, boolean checkForEqualing) throws ParserIllegalTokenValueException, ParserUnknownEntityException, ExpressionException, CalculatorException {
        Parser parser = new Parser(expression);
        parser.parse();
        ExpressionsTree expressionsTree = new ExpressionsTree(parser.getResult());
        expressionsTree.build();

        if (checkForEqualing) {
            assertTrue(expected, Calculator.getInstance().calculate((SequenceExpression) expressionsTree.getRoot()));
        } else {
            assertFalse(expected, Calculator.getInstance().calculate((SequenceExpression) expressionsTree.getRoot()));
        }
    }

    private void assertTrue(Double expected, Double actual) {
        if (Numbers.equals(expected, actual)) {
            return;
        }

        fail(String.format("Expected for %f but got %f!", expected, actual));
    }

    private void assertFalse(Double expected, Double actual) {
        if (!Numbers.equals(expected, actual)) {
            return;
        }

        // I am not an English man, I don't know how to write this.
        fail(String.format("Waited for not expected %f which mustn't equal to %s!", actual, expected));
    }
}
