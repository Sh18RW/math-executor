package ru.corvinella.tokens;

public class NumberToken extends Token<Double> {
    public NumberToken(Double value) {
        super(value, TokenType.Number);
    }
}
