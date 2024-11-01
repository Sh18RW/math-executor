package ru.corvinella;

import org.junit.Test;
import ru.corvinella.expressions.ExpressionsTree;
import ru.corvinella.expressions.entries.Expression;
import ru.corvinella.expressions.entries.NumberExpression;
import ru.corvinella.expressions.entries.OperationExpression;
import ru.corvinella.expressions.entries.SequenceExpression;
import ru.corvinella.parser.Parser;
import ru.corvinella.parser.ParserIllegalTokenValueException;
import ru.corvinella.parser.ParserUnknownEntityException;
import ru.corvinella.tokens.NumberToken;
import ru.corvinella.tokens.OperationToken;
import ru.corvinella.tokens.OperationType;

import static org.junit.Assert.assertEquals;

/**
 * @author sh18rw
 */
public class ExpressionsTest {
    @Test
    public void testSimpleNumberExpressions() throws ParserIllegalTokenValueException, ParserUnknownEntityException {
        assertExpression(makeSequence(makeNumber(10.0)), "10");
        assertExpression(makeSequence(makeNumber(1.213)), "1.213");
        assertExpression(makeSequence(makeNumber(-20.0)), "-20");
        assertExpression(makeSequence(makeNumber(-13.1235846)), "-13.1235846");
    }

    @Test
    public void testSimpleSequenceExpressions() throws ParserIllegalTokenValueException, ParserUnknownEntityException {
        assertExpression(
                makeSequence(makeNumber(1.0), makeOperation(OperationType.Plus), makeNumber(2.0)),
                "1 + 2");
        assertExpression(
                makeSequence(makeSequence(makeNumber(2.0), makeOperation(OperationType.Multiply), makeNumber(3.0))),
                "2 * 3"
        );
        assertExpression(
                makeSequence(makeSequence(makeNumber(4.0), makeOperation(OperationType.Degree), makeNumber(0.5))),
                "4 ^ 0.5");
        assertExpression(
                makeSequence(makeNumber(-10.0), makeOperation(OperationType.Minus), makeNumber(-20.0)),
                "-10 - -20");
    }

    @Test
    public void testSimpleSequencesExpressions() throws ParserIllegalTokenValueException, ParserUnknownEntityException {
        assertExpression(
                makeSequence(
                        makeNumber(2.0),
                        makeOperation(OperationType.Plus),
                        makeSequence(
                                makeNumber(2.0),
                                makeOperation(OperationType.Multiply),
                                makeNumber(2.0),
                                makeOperation(OperationType.Divide),
                                makeNumber(2.0)
                        ),
                        makeOperation(OperationType.Minus),
                        makeNumber(2.0)
                ),
                "2 + 2 * 2 / 2 - 2"
        );
        assertExpression(
                makeSequence(
                        makeNumber(2.0),
                        makeOperation(OperationType.Plus),
                        makeSequence(
                                makeNumber(2.0),
                                makeOperation(OperationType.Multiply),
                                makeSequence(
                                        makeNumber(3.0),
                                        makeOperation(OperationType.Degree),
                                        makeNumber(4.0)
                                )),
                        makeOperation(OperationType.Minus),
                        makeSequence(
                                makeNumber(1.0),
                                makeOperation(OperationType.Multiply),
                                makeNumber(2.0)
                        )
                ),
                "2 + 2 * 3 ^ 4 - 1 * 2");
    }

    private void assertExpression(SequenceExpression expected, String expression) throws ParserIllegalTokenValueException, ParserUnknownEntityException {
        Expression parsed = makeExpression(expression);

        assertEquals(expected, parsed);
    }

    private Expression makeExpression(String expression) throws ParserIllegalTokenValueException, ParserUnknownEntityException {
        Parser parser = new Parser(expression);
        parser.parse();
        ExpressionsTree expressionsTree = new ExpressionsTree(parser.getResult());
        expressionsTree.build();
        return expressionsTree.getRoot();
    }

    private SequenceExpression makeSequence(Expression... expressions) {
        SequenceExpression sequenceExpression = new SequenceExpression();

        for (Expression expression : expressions) {
            sequenceExpression.append(expression);
        }

        return sequenceExpression;
    }

    private NumberExpression makeNumber(Double number) {
        boolean isNegative = number < 0;

        if (isNegative) {
            number *= -1;
        }

        return new NumberExpression(isNegative, new NumberToken(number, 0));
    }

    private OperationExpression makeOperation(OperationType operationType) {
        return new OperationExpression(new OperationToken(operationType, 0));
    }
}
