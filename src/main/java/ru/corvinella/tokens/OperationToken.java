package ru.corvinella.tokens;

public class OperationToken extends Token<OperationType> {
    public OperationToken(OperationType value) {
        super(value, TokenType.Operation);
    }
}
