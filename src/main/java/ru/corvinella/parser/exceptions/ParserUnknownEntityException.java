package ru.corvinella.parser.exceptions;

public class ParserUnknownEntityException extends ParserException {
    /**
     * Exception signalise that in expression unknown symbol and parser can't work with it.
     * @param symbol a symbol which caused an exception.
     * @param expression an expression which caused an exception.
     * @param tracer an index of symbol which caused an exception.
     */
    public ParserUnknownEntityException(String symbol, String expression, int tracer) {
        super(String.format("Unknown \"%s\" character", symbol), expression, tracer);
    }
}
