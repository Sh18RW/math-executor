package ru.corvinella;

import org.junit.Test;
import ru.corvinella.parser.Parser;
import ru.corvinella.parser.exceptions.ParserIllegalTokenValueException;
import ru.corvinella.parser.exceptions.ParserUnknownEntityException;
import ru.corvinella.tokens.*;
import ru.corvinella.tokens.types.OperationType;
import ru.corvinella.tokens.types.ParenthesisType;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class ParserTest {
    @Test
    public void testSimpleNumberParsing() {
        assertEquals(Collections.singletonList(new NumberToken(0.0, 0)), packParser("0"));
        assertEquals(Collections.singletonList(new NumberToken(123.0, 0)), packParser("123"));
        assertEquals(Collections.singletonList(new NumberToken(101010101.90009, 0)), packParser("101010101.90009"));
        assertNotEquals(Collections.singletonList(new NumberToken(-1.0, 0)), packParser("-1"));
    }

    @Test
    public void testSimpleOperationParsing() {
        assertEquals(Collections.singletonList(new OperationToken(OperationType.Plus, 0)), packParser("+"));
        assertEquals(Collections.singletonList(new OperationToken(OperationType.Minus, 0)), packParser("-"));
        assertEquals(Collections.singletonList(new OperationToken(OperationType.Multiply, 0)), packParser("*"));
        assertEquals(Collections.singletonList(new OperationToken(OperationType.Divide, 0)), packParser("/"));
        assertEquals(Collections.singletonList(new OperationToken(OperationType.Degree, 0)), packParser("^"));
    }

    @Test
    public void testSimpleWords() {
        assertEquals(Collections.singletonList(new WordToken("pi", 0)), packParser("Pi"));
        assertEquals(Arrays.asList(
                new WordToken("log", 0),
                new ArgumentsParenthesisToken(ParenthesisType.Open, 0),
                new NumberToken(2.0, 0),
                new ArgumentsSeparatorToken(0),
                new NumberToken(4.0, 0),
                new ArgumentsParenthesisToken(ParenthesisType.Close, 0)
        ), packParser("log(2, 4)"));
    }

    @Test
    public void testSimpleExpressionParsing() {
        assertEquals(Arrays.asList(
                new NumberToken(2.0, 0),
                new OperationToken(OperationType.Plus, 0),
                new NumberToken(2.0, 0)
        ), packParser("2 + 2"));
        assertEquals(Arrays.asList(
                new NumberToken(1.0, 0),
                new OperationToken(OperationType.Plus, 0),
                new NumberToken(2.0, 0),
                new OperationToken(OperationType.Multiply, 0),
                new NumberToken(3.0, 0),
                new OperationToken(OperationType.Divide, 0),
                new NumberToken(4.0, 0),
                new OperationToken(OperationType.Minus, 0),
                new NumberToken(5.0, 0),
                new OperationToken(OperationType.Plus, 0),
                new OperationToken(OperationType.Minus, 0),
                new NumberToken(6.0, 0),
                new OperationToken(OperationType.Degree, 0),
                new NumberToken(7.0, 0)
        ), packParser("1+2*3/4-5+-6^7"));
        assertEquals(Arrays.asList(
                new ParenthesisToken(ParenthesisType.Open, 0),
                new NumberToken(2.0, 0),
                new OperationToken(OperationType.Plus, 0),
                new NumberToken(2.0, 0),
                new ParenthesisToken(ParenthesisType.Close, 0),
                new OperationToken(OperationType.Multiply, 0),
                new NumberToken(2.0, 0)
        ), packParser("(2 + 2) * 2"));
    }

    private final List<Token<?>> packParser(String expression) {
        Parser parser = new Parser(expression);

        try {
            parser.parse();
        } catch (ParserUnknownEntityException | ParserIllegalTokenValueException e) {
            return null;
        }

        return parser.getResult();
    }
}
