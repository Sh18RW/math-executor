package ru.corvinella.tokens;

import ru.corvinella.tokens.types.OperationType;
import ru.corvinella.tokens.types.TokenType;

public class OperationToken extends Token<OperationType> {
    public OperationToken(OperationType value, int tracer) {
        super(value, TokenType.Operation, tracer);
    }
}
