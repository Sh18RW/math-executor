package ru.corvinella.tokens;

public class ArgumentsParenthesisToken extends Token<ParenthesisType> {
    public ArgumentsParenthesisToken(ParenthesisType value, int tracer) {
        super(value, TokenType.ArgumentsParenthesis, tracer);
    }
}
