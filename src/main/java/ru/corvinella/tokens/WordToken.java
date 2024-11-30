package ru.corvinella.tokens;

import ru.corvinella.tokens.types.TokenType;

public class WordToken extends ValueToken<String> {
    public WordToken(String value, int tracer) {
        super(value.toLowerCase(), TokenType.Word, tracer);
    }
}
