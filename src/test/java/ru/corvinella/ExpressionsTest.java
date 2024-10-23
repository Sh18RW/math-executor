package ru.corvinella;

import org.junit.Test;
import ru.corvinella.expressions.ExpressionsTree;
import ru.corvinella.expressions.entries.Expression;
import ru.corvinella.parser.Parser;
import ru.corvinella.parser.ParserIllegalTokenValueException;
import ru.corvinella.parser.ParserUnknownEntityException;

/**
 * @author sh18rw
 */
public class ExpressionsTest {
    @Test
    public void checkExpression() throws ParserIllegalTokenValueException, ParserUnknownEntityException {
        parseExpression("1 + 2 ^ 3 - 4");
    }

    private Expression parseExpression(String expression) throws ParserIllegalTokenValueException, ParserUnknownEntityException {
        Parser parser = new Parser(expression);
        parser.parse();
        ExpressionsTree expressionsTree = new ExpressionsTree(parser.getResult());
        expressionsTree.build();
        return expressionsTree.getRoot();
    }
}
