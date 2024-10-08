package ru.corvinella.tokens;

public class WordToken extends Token<String> {
    public WordToken(String value, int tracer) {
        super(value, TokenType.Word, tracer);
        throw new IllegalStateException("Words are not implemented yet!");
    }
}
