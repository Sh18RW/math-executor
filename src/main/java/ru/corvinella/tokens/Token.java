package ru.corvinella.tokens;

public abstract class Token <T> {
    private final T value;
    private final int tracer;
    private final TokenType type;

    public Token(T value, TokenType type, int tracer) {
        this.type = type;
        this.value = value;
        this.tracer = tracer;
    }

    public T getValue() {
        return value;
    }
    
    public final int getTracer() {
        return tracer;
    }

    public final TokenType getType() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Token<?>) {
            return ((Token<?>) obj).getValue().equals(getValue());
        }

        return false;
    }
}
