package ru.corvinella;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.List;

import org.junit.Test;

import ru.corvinella.parser.Parser;
import ru.corvinella.parser.ParserIllegalTokenValueException;
import ru.corvinella.parser.ParserUnknownEntityException;
import ru.corvinella.tokens.ArgumentsParenthesisToken;
import ru.corvinella.tokens.ArgumentsSeparator;
import ru.corvinella.tokens.NumberToken;
import ru.corvinella.tokens.OperationToken;
import ru.corvinella.tokens.OperationType;
import ru.corvinella.tokens.ParenthesisType;
import ru.corvinella.tokens.Token;
import ru.corvinella.tokens.WordToken;
import ru.corvinella.tokens.WordType;

public class ParserTest {
    @Test
    public void testSimpleNumberParsing() {
        assertEquals(List.of(new NumberToken(0.0, 0)), packParser("0"));
        assertEquals(List.of(new NumberToken(123.0, 0)), packParser("123"));
        assertEquals(List.of(new NumberToken(101010101.90009, 0)), packParser("101010101.90009"));
        assertNotEquals(List.of(new NumberToken(-1.0, 0)), packParser("-1"));
    }

    @Test
    public void testSimpleOperationParsing() {
        assertEquals(List.of(new OperationToken(OperationType.Plus, 0)), packParser("+"));
        assertEquals(List.of(new OperationToken(OperationType.Minus, 0)), packParser("-"));
        assertEquals(List.of(new OperationToken(OperationType.Multiply, 0)), packParser("*"));
        assertEquals(List.of(new OperationToken(OperationType.Divide, 0)), packParser("/"));
        assertEquals(List.of(new OperationToken(OperationType.Degree, 0)), packParser("^"));
    }

    @Test
    public void testSimpleWords() {
        assertEquals(List.of(new WordToken(WordType.Pi, 0)), packParser("Pi"));
        assertEquals(List.of(
            new WordToken(WordType.Log, 0),
            new ArgumentsParenthesisToken(ParenthesisType.Open, 0),
            new NumberToken(2.0, 0),
            new ArgumentsSeparator(0),
            new NumberToken(4.0, 0),
            new ArgumentsParenthesisToken(ParenthesisType.Close, 0)
        ), packParser("log(2, 4)"));
    }

    @Test
    public void testSimpleExpressionParsing() {
        assertEquals(List.of(
            new NumberToken(2.0, 0),
            new OperationToken(OperationType.Plus, 0),
            new NumberToken(2.0, 0)
        ), packParser("2 + 2"));
        assertEquals(List.of(
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
    }

    private final List<Token<?>> packParser(String expression) {
        Parser parser = new Parser(expression);

        try {
            parser.parse();
        } catch (ParserUnknownEntityException | ParserIllegalTokenValueException e) {
            e.printStackTrace();
            return null;
        }

        return parser.getResult();
    }
}
