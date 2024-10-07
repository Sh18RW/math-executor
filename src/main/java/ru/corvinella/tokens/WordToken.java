package ru.corvinella.tokens;

public class WordToken extends Token<String> {
    public WordToken(String value) {
        super(value, TokenType.Word);
        throw new IllegalStateException("Words are not implemented yet!");
    }
}
