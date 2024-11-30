package ru.corvinella.expressions.exceptions;

import ru.corvinella.tokens.Token;
import ru.corvinella.utils.exceptions.ExceptionAtToken;

public abstract class ExpressionException extends ExceptionAtToken {
    public ExpressionException(String message, Token<?> atToken) {
        super(String.format("%s at %d, token %s.", message, atToken.getTracer(), atToken), atToken);
    }
}
