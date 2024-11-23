package ru.corvinella.tokens;

import ru.corvinella.tokens.types.TokenType;

public class NumberToken extends ValueToken<Double> {
    public NumberToken(Double value, int tracer) {
        super(value, TokenType.Number, tracer);
    }
}
