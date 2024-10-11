package ru.corvinella.tokens;

public class WordToken extends Token<WordType> {
    public WordToken(WordType value, int tracer) {
        super(value, TokenType.Word, tracer);
    }
}
