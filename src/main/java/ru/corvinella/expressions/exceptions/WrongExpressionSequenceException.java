package ru.corvinella.expressions.exceptions;

import ru.corvinella.tokens.Token;

public class WrongExpressionSequenceException extends ExpressionException {
    public WrongExpressionSequenceException(String message, Token<?> atToken) {
        super(message, atToken);
    }
}
