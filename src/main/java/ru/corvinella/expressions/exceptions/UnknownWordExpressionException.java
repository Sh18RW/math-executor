package ru.corvinella.expressions.exceptions;

import ru.corvinella.tokens.WordToken;

/**
 * @author sh18rw
 */
public class UnknownWordExpressionException extends ExpressionException {
    public UnknownWordExpressionException(WordToken atToken) {
        super("Unknown word token", atToken);
    }
}
