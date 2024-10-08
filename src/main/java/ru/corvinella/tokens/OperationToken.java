package ru.corvinella.tokens;

public class OperationToken extends Token<OperationType> {
    public OperationToken(OperationType value, int tracer) {
        super(value, TokenType.Operation, tracer);
    }
}
