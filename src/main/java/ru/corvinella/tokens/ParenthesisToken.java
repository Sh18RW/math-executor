package ru.corvinella.tokens;

public class ParenthesisToken extends ValueToken<ParenthesisType> {
    public ParenthesisToken(ParenthesisType value, int tracer) {
        super(value, TokenType.Parenthesis, tracer);
    }
}
