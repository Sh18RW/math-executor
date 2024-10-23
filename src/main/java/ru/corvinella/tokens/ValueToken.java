package ru.corvinella.tokens;

/**
 * @author sh18rw
 */
public abstract class ValueToken <T> extends Token<T>{
    private boolean isNegative;
    public ValueToken(T value, TokenType type, int tracer) {
        super(value, type, tracer);
    }

    public void makeNegative() {
        this.isNegative = true;
    }

    public final boolean isNegative() {
        return this.isNegative;
    }
}
