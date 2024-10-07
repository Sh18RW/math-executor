package ru.corvinella.parser;

public class ParserUnknownEntityException extends Exception {
    public ParserUnknownEntityException(String errorText, int tracer) {
        super(String.format("%s at %d", errorText, tracer));
    }
}
