package ru.corvinella;

import org.junit.Test;
import ru.corvinella.expressions.ExpressionsTree;
import ru.corvinella.expressions.entries.SequenceExpression;
import ru.corvinella.math.Calculator;
import ru.corvinella.math.SequenceCalculator;
import ru.corvinella.parser.Parser;
import ru.corvinella.parser.ParserIllegalTokenValueException;
import ru.corvinella.parser.ParserUnknownEntityException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * @author sh18rw
 */
public class CalculatorTest {
    @Test
    public void testCalculator() throws ParserIllegalTokenValueException, ParserUnknownEntityException {
        makeTest("2 + 2", 4.0);
        makeTest("2 + 2 * 2", 6.0);
        makeTest("2 + 2 * 3 - 2 / 1 * 2 + 2 ^ 2 - 1", 7.0);
        makeTest("log(2, 4)", 2.0);
        makeTest("log(2, 8) * -0.5", -1.5);
        makeTest("(2 + (2 * 2 - (2))) * -2", -8.0);
        makeTest("(4 * 2) / (16 ^ 0)", 8.0);
        makeTest("log(2, 8)", 3.0);
        makeTest("2 + (2 + 2) / (2 ^ 2)", 3.0);
        makeTest("2 * (3 * (5 - 4) / 6)", 1.0);
        makeTest("Pi*2", Math.PI*2);
    }

    public void makeTest(String expression, Double expected) throws ParserIllegalTokenValueException, ParserUnknownEntityException {
        Parser parser = new Parser(expression);
        parser.parse();
        ExpressionsTree expressionsTree = new ExpressionsTree(parser.getResult());
        expressionsTree.build();

        assertEquals(expected, Calculator.getInstance().calculate((SequenceExpression) expressionsTree.getRoot()));
    }
}
