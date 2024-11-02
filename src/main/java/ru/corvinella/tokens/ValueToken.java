package ru.corvinella.tokens;

/**
 * @author sh18rw
 */
public abstract class ValueToken <T> extends Token<T>{
    public ValueToken(T value, TokenType type, int tracer) {
        super(value, type, tracer);
    }

}
