package ru.corvinella.tokens;

import ru.corvinella.tokens.types.TokenType;
import ru.corvinella.tokens.types.WordType;

public class WordToken extends ValueToken<WordType> {
    public WordToken(WordType value, int tracer) {
        super(value, TokenType.Word, tracer);
    }
}
