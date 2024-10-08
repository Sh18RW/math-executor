package ru.corvinella.parser;

public class ParserUnknownEntityException extends ParserException {
    public ParserUnknownEntityException(String symbol, String expression, int tracer) {
        super(String.format("Unknown \"%s\" character", symbol), expression, tracer);
    }
}
