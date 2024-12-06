package ru.corvinella.parser.exceptions;

import ru.corvinella.tokens.types.TokenType;

public class ParserIllegalTokenValueException extends ParserException{
    private final TokenType parsingType;
    private final String tokenValue;

    /**
     * Exception signalise that some token has illegal value.
     * @param value a value which caused an exception.
     * @param parsingType a currently parsing type.
     * @param expression an expression which caused exception.
     * @param tracer an index of a symbol which caused exception.
     */
    public ParserIllegalTokenValueException(String value, TokenType parsingType, String expression, int tracer) {
        super(String.format("Failed to cast \"%s\" to %s type", value, parsingType), expression, tracer);

        this.parsingType = parsingType;
        this.tokenValue = value;
    }

    public TokenType getParsingType() {
        return parsingType;
    }

    public String getTokenValue() {
        return tokenValue;
    }
}
