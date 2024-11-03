package ru.corvinella.tokens;

import ru.corvinella.tokens.types.ParenthesisType;
import ru.corvinella.tokens.types.TokenType;

public class ParenthesisToken extends ValueToken<ParenthesisType> {
    public ParenthesisToken(ParenthesisType value, int tracer) {
        super(value, TokenType.Parenthesis, tracer);
    }
}
