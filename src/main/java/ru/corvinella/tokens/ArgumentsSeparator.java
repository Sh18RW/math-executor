package ru.corvinella.tokens;

public class ArgumentsSeparator extends Token<Object> {
    public ArgumentsSeparator(int tracer) {
        super(0, TokenType.ArgumentsSeparator, tracer);
    }
}
