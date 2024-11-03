package ru.corvinella.tokens;

import ru.corvinella.tokens.types.ParenthesisType;
import ru.corvinella.tokens.types.TokenType;

public class ArgumentsParenthesisToken extends Token<ParenthesisType> {
    public ArgumentsParenthesisToken(ParenthesisType value, int tracer) {
        super(value, TokenType.ArgumentsParenthesis, tracer);
    }
}
