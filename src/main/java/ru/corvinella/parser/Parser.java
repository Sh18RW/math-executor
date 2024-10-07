package ru.corvinella.parser;

import java.util.LinkedList;
import java.util.List;

import ru.corvinella.tokens.Token;
import ru.corvinella.tokens.TokenType;

/**
 * Parses input expression to list of {@link ru.corvinella.tokens.Token}.
 */
public class Parser {
    private static final String numberEntitiesSymbols = "0123456789";
    private static final String operationEntitiesSymbols = "+-*/^";
    private static final String parenthesisEntitiesSymbols = "()";
    private static final String wordEntitiesSymbols = "qwertyuiopasdfghjklzxcvbnm";

    private final String expression;
    private final List<Token<?>> result;

    private StringBuilder currentParsingEntity;
    private TokenType currentParsingEntityType;
    private int index;

    public Parser(String expression) {
        this.expression = expression;
        this.result = new LinkedList<>();

        this.index = 0;
        this.currentParsingEntity = new StringBuilder();
    }

    public void parse() {
        for (;index < expression.length();index++) {

        }
    }
}
