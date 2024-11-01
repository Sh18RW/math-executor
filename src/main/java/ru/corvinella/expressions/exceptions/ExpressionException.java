package ru.corvinella.expressions.exceptions;

import ru.corvinella.tokens.Token;

public abstract class ExpressionException extends Exception {
    public ExpressionException(String message, Token<?> atToken) {
        super(String.format("%s at %d, token %s.", message, atToken.getTracer(), atToken));
    }
}
