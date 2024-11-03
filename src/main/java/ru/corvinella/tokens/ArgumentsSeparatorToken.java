package ru.corvinella.tokens;

import ru.corvinella.tokens.types.TokenType;

public class ArgumentsSeparatorToken extends Token<Object> {
    public ArgumentsSeparatorToken(int tracer) {
        super(0, TokenType.ArgumentsSeparator, tracer);
    }
}
