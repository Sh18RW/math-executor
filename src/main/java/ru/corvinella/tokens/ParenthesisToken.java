package ru.corvinella.tokens;

public class ParenthesisToken extends Token<ParenthesisType> {
    public ParenthesisToken(ParenthesisType value) {
        super(value, TokenType.Parenthesis);
    }
}
