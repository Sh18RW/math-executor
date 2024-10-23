package ru.corvinella.expressions.entries;

import ru.corvinella.tokens.Token;

import java.util.LinkedList;
import java.util.List;

/**
 * @author sh18rw
 */
public class OperationSequence extends Expression {
    private final List<Token<?>> tokenList;

    public OperationSequence() {
        this.tokenList = new LinkedList<>();
    }

    @Override
    public void append(Token<?> token) {
        tokenList.add(token);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("OperationSequence[");
        tokenList.forEach(e -> {
            stringBuilder.append(e.toString());
            stringBuilder.append(' ');
        });
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append(']');

        return stringBuilder.toString();
    }
}
