package ru.corvinella.expressions.entries;

import ru.corvinella.tokens.NumberToken;

/**
 * @author sh18rw
 */
public class NumberExpression extends ValueExpression {
    private final boolean isNegative;
    private final NumberToken token;

    public NumberExpression(boolean isNegative, NumberToken token) {
        this.isNegative = isNegative;
        this.token = token;
    }

    public boolean isNegative() {
        return isNegative;
    }

    public NumberToken getToken() {
        return token;
    }

    @Override
    public String toString() {

        return "Number[" +
                token.getValue() +
                "]";
    }
}
