package ru.corvinella.parser;

public class ParserException extends Exception {
    public ParserException(String errorText, String expression, int tracer) {
        super(String.format("%s at %d in \"%s\".", errorText, tracer, expression));
    }
}
