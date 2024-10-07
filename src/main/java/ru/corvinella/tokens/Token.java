package ru.corvinella.tokens;

public abstract class Token <T> {
    private final T value;
    private final TokenType type;

    public Token(T value, TokenType type) {
        this.value = value;
        this.type = type;
    }

    public final T getValue() {
        return value;
    }

    public TokenType getType() {
        return type;
    }
}
