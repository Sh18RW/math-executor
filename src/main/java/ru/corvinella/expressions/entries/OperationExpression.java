package ru.corvinella.expressions.entries;

import ru.corvinella.tokens.OperationToken;

/**
 * @author sh18rw
 */
public class OperationExpression extends Expression {
    private final OperationToken token;

    public OperationExpression(OperationToken token) {
        this.token = token;
    }

    public OperationToken getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "Operation["
                + token.getValue()
                + "]";
    }
}
