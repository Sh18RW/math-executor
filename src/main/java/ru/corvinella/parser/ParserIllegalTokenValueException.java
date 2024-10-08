package ru.corvinella.parser;

import ru.corvinella.tokens.TokenType;

public class ParserIllegalTokenValueException extends ParserException{
    public ParserIllegalTokenValueException(String value, TokenType parsingType, String expression, int tracer) {
        super(String.format("Failed to cast \"%s\" to %s type", value, parsingType), expression, tracer);
    }
}
