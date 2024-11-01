package ru.corvinella.tokens;

public class NumberToken extends ValueToken<Double> {
    public NumberToken(Double value, int tracer) {
        super(value, TokenType.Number, tracer);
    }
}
