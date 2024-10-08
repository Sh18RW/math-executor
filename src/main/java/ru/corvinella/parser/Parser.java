package ru.corvinella.parser;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ru.corvinella.tokens.NumberToken;
import ru.corvinella.tokens.OperationToken;
import ru.corvinella.tokens.OperationType;
import ru.corvinella.tokens.Token;
import ru.corvinella.tokens.TokenType;

/**
 * Parses input expression to list of {@link ru.corvinella.tokens.Token}.
 */
public class Parser {
    private static final String numberEntitiesSymbols = "0123456789.";
    private static final String operationEntitiesSymbols = "+-*/^";
    private static final String parenthesisEntitiesSymbols = "()";
    private static final String wordEntitiesSymbols = "qwertyuiopasdfghjklzxcvbnm";

    private final String expression;
    private final List<Token<?>> result;
    private final Map<String, IProcessable> symbolDefinitions;

    private StringBuilder currentParsingEntity;
    private TokenType currentParsingEntityType;
    private String parsingCharacter;
    private boolean isProcessed;
    private int index;

    public Parser(String expression) {
        this.expression = expression;
        this.result = new LinkedList<>();

        this.index = 0;
        this.isProcessed = false;
        this.currentParsingEntity = new StringBuilder();
        this.currentParsingEntityType = TokenType.Number;

        this.symbolDefinitions = new HashMap<>();

        this.symbolDefinitions.put(numberEntitiesSymbols, this::parseNumber);
        this.symbolDefinitions.put(operationEntitiesSymbols, this::parseOperation);
    }

    public void parse() throws ParserUnknownEntityException, ParserIllegalTokenValueException {
        for (;index < expression.length();index++) {
            parsingCharacter = "" + expression.charAt(index);

            boolean hasProcessed = false;

            for (String symbols : symbolDefinitions.keySet()) {
                if (symbols.contains(parsingCharacter)) {
                    symbolDefinitions.get(symbols).process();
                    hasProcessed = true;
                    currentParsingEntity.append(parsingCharacter);
                    break;
                }
            }

            if (!hasProcessed) {
                throw new ParserUnknownEntityException(parsingCharacter, expression, index);
            }
        }

        packToken();

        this.isProcessed = true;
    }

    public final List<Token<?>> getResult() {
        if (isProcessed) {
            return result;
        }

        throw new IllegalStateException("Requested parsing result, but the parse method have not even been called!");
    }

    private final void parseNumber() throws ParserIllegalTokenValueException {
        if (currentParsingEntityType != TokenType.Number) {
            packToken();

            currentParsingEntityType = TokenType.Number;
        }
    }

    private final void parseOperation() throws ParserIllegalTokenValueException {
        if (currentParsingEntity.length() != 0) {
            packToken();
        }

        currentParsingEntityType = TokenType.Operation;
    }

    private final Token<?> packToken() throws ParserIllegalTokenValueException {
        Token<?> token;
        
        switch (currentParsingEntityType) {
            case Number:
                try {
                    Double number = Double.parseDouble(currentParsingEntity.toString());
                    token = new NumberToken(number, index);
                } catch (NumberFormatException e) {
                    throw new ParserIllegalTokenValueException(numberEntitiesSymbols, currentParsingEntityType, expression, index);
                }
                break;
            case Operation:
                OperationType operationType = getOperationTypeFromString(currentParsingEntity.toString());
                token = new OperationToken(operationType, index);
                break;
            default:
                throw new IllegalStateException(); // I am not sure that it can even happen.
        }

        result.add(token);
        currentParsingEntity = new StringBuilder();

        return token;
    }

    private OperationType getOperationTypeFromString(String operation) throws ParserIllegalTokenValueException {
        switch (operation) {
            case "+":
                return OperationType.Plus;
            case "-":
                return OperationType.Minus;
            case "/":
                return OperationType.Divide;
            case "*":
                return OperationType.Multiply;
            case "^":
                return OperationType.Degree;
            default:
                throw new ParserIllegalTokenValueException(operation, TokenType.Operation, expression, index);
        }
    }

    private interface IProcessable {
        public abstract void process() throws ParserIllegalTokenValueException;
    }
}