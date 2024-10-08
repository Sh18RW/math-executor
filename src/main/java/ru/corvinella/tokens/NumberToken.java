package ru.corvinella.tokens;

public class NumberToken extends Token<Double> {
    public NumberToken(Double value, int tracer) {
        super(value, TokenType.Number, tracer);
    }
}
