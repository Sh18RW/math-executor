package ru.corvinella.expressions.entries;

import ru.corvinella.tokens.NumberToken;

/**
 * @author sh18rw
 */
public class NumberExpression extends ValueExpression {
    private final NumberToken token;

    public NumberExpression(boolean isNegative, NumberToken token) {
        super(isNegative);
        this.token = token;
    }

    public NumberToken getToken() {
        return token;
    }

    @Override
    public String toString() {

        return "Number[" +
                (isNegative() ? "negative" : "positive") +
                " " +
                token.getValue() +
                "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof NumberExpression)) {
            return false;
        }

        NumberExpression numberExpression = (NumberExpression) obj;

        return numberExpression.token.equals(this.token)
                && (numberExpression.isNegative() == this.isNegative());
    }
}
