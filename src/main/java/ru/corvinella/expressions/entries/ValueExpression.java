package ru.corvinella.expressions.entries;

/**
 * @author sh18rw
 */
public abstract class ValueExpression extends Expression {
    private final boolean isNegative;

    public ValueExpression(boolean isNegative) {
        this.isNegative = isNegative;
    }

    public boolean isNegative() {
        return isNegative;
    }
}
