package ru.corvinella.parser;

import java.util.LinkedList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.corvinella.tokens.ArgumentsParenthesisToken;
import ru.corvinella.tokens.ArgumentsSeparator;
import ru.corvinella.tokens.NumberToken;
import ru.corvinella.tokens.OperationToken;
import ru.corvinella.tokens.OperationType;
import ru.corvinella.tokens.ParenthesisToken;
import ru.corvinella.tokens.ParenthesisType;
import ru.corvinella.tokens.Token;
import ru.corvinella.tokens.TokenType;
import ru.corvinella.tokens.WordToken;
import ru.corvinella.tokens.WordType;

/**
 * Parses input expressions to list of {@link ru.corvinella.tokens.Token}.
 * Only parses expression, it doesn't check for correct sequence.
 * Example to use:
 * <pre>
 * Parser parser = new Parser("2 + 2 * 2");
 * parser.parse(); // throws exceptions
 * parser.getResult();
 * </pre>
 * Supported symbols:
 * <ul>
 * <li>Numbers {@code 0-9} and {@code .}</li>
 * <li>Plus {@code +}, minus {@code -}, multiply {@code *}, divide {@code /}, degree {@code ^}</li>
 * <li>Constants {@code Pi}, {@code e}</li>
 * <li>Functions {@code log}, like this: {@code log(2, 4)} or {@code log(2; 4)} (logarithm of 4 on base 2)</li>
 * </ul>
 * 
 * @author sh18rw
 * @version 1.0
 */
public class Parser {
    private static final String numberEntitiesSymbols = "0123456789.";
    private static final String operationEntitiesSymbols = "+-*/^";
    private static final String parenthesisEntitiesSymbols = "()";
    private static final String wordEntitiesSymbols = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
    private static final String argumentsSeparator = ",;";

    private final String expression;
    private final List<Token<?>> result;
    private final Map<String, IProcessable> symbolDefinitions;

    private StringBuilder currentParsingEntity;
    private TokenType currentParsingEntityType;
    private boolean isProcessed;
    private int index;

    public Parser(String expression) {
        this.expression = expression.toLowerCase();
        this.result = new LinkedList<>();

        this.index = 0;
        this.isProcessed = false;
        this.currentParsingEntity = new StringBuilder();
        this.currentParsingEntityType = TokenType.Number;

        this.symbolDefinitions = new HashMap<>();

        this.symbolDefinitions.put(numberEntitiesSymbols, this::parseNumber);
        this.symbolDefinitions.put(operationEntitiesSymbols, this::parseOperation);
        this.symbolDefinitions.put(parenthesisEntitiesSymbols, this::parseParenthesis);
        this.symbolDefinitions.put(wordEntitiesSymbols, this::parseWord);
        this.symbolDefinitions.put(argumentsSeparator, this::parseArgumentSeparator);
    }

    /**
     * Parses input expression and writes the result into the {@link Parser#result}.
     * @throws ParserUnknownEntityException if parser sees an unknown symbol that is not supported.
     * @throws ParserIllegalTokenValueException if parser can't cast some value to current parsing token type.
     */
    public void parse() throws ParserUnknownEntityException, ParserIllegalTokenValueException {
        for (;index < expression.length();index++) {
            String parsingCharacter = "" + expression.charAt(index);

            if (parsingCharacter.equals(" ")) {
                continue;
            }

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
            if (currentParsingEntity.length() != 0) {
                packToken();
            }

            currentParsingEntityType = TokenType.Number;
        }
    }

    private final void parseOperation() throws ParserIllegalTokenValueException {
        if (currentParsingEntity.length() != 0) {
            packToken();
        }

        currentParsingEntityType = TokenType.Operation;
    }

    private final void parseParenthesis() throws ParserIllegalTokenValueException {
        if (currentParsingEntity.length() != 0) {
            Token<?> token = packToken();

            if (token.getType() == TokenType.Word) {
                currentParsingEntityType = TokenType.ArgumentsParenthesis;
            }
            else {
                currentParsingEntityType = TokenType.Parenthesis;
            }
        }
    }

    public final void parseWord() throws ParserIllegalTokenValueException {
        if (currentParsingEntityType != TokenType.Word) {
            if (currentParsingEntity.length() != 0) {
                packToken();
            }

            currentParsingEntityType = TokenType.Word;
        }
    }

    public final void parseArgumentSeparator() throws ParserIllegalTokenValueException {
        if (currentParsingEntity.length() != 0) {
            packToken();
        }
        
        currentParsingEntityType = TokenType.ArgumentsSeparator;
    }

    private final Token<?> packToken() throws ParserIllegalTokenValueException {
        Token<?> token;

        if (currentParsingEntity.length() == 0) {
            throw new IllegalStateException("Parser#packToken() method was called when Parser#currentParsingEntity is empty. It shouldn't happen.");
        }
        
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
            case ArgumentsParenthesis:
            case Parenthesis:
                ParenthesisType parenthesisType;
                switch (currentParsingEntity.toString()) {
                    case "(":
                        parenthesisType = ParenthesisType.Open;
                        break;
                    case ")":
                        parenthesisType = ParenthesisType.Close;
                        break;
                    default:
                        throw new ParserIllegalTokenValueException(currentParsingEntity.toString(), currentParsingEntityType, expression, index);
                }

                if (currentParsingEntityType == TokenType.Parenthesis) {
                    token = new ParenthesisToken(parenthesisType, index);
                } else {
                    token = new ArgumentsParenthesisToken(parenthesisType, index);
                }
                break;
            case Word:
                WordType wordType = getWordTypeFromString(currentParsingEntity.toString());
                token = new WordToken(wordType, index);
                break;
            case ArgumentsSeparator:
                token = new ArgumentsSeparator(index);
                break;
            default:
                // I am not sure that it can even happen.
                throw new IllegalStateException(
                    String.format("\"%s\" has not realisation.",
                    currentParsingEntityType));
        }

        result.add(token);
        currentParsingEntity = new StringBuilder();

        return token;
    }

    private final OperationType getOperationTypeFromString(String operation) throws ParserIllegalTokenValueException {
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

    private final WordType getWordTypeFromString(String word) throws ParserIllegalTokenValueException {
        switch (word) {
            // Constants
            case "pi":
                return WordType.Pi;
            case "e":
                return WordType.E;
            
            // Functions
            case "log":
                return WordType.Log;
            default:
                throw new ParserIllegalTokenValueException(word, TokenType.Word, expression, index);
        }
    }

    private interface IProcessable {
        public abstract void process() throws ParserIllegalTokenValueException;
    }
}