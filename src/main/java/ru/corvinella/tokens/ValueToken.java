package ru.corvinella.tokens;

import ru.corvinella.tokens.types.TokenType;

/**
 * @author sh18rw
 */
public abstract class ValueToken <T> extends Token<T>{
    public ValueToken(T value, TokenType type, int tracer) {
        super(value, type, tracer);
    }

}
