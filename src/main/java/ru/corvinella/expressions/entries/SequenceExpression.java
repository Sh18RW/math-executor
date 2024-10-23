package ru.corvinella.expressions.entries;

import java.util.LinkedList;
import java.util.List;

/**
 * @author sh18rw
 */
public class SequenceExpression extends Expression {
    private final List<Expression> expressionsList;

    public SequenceExpression() {
        this.expressionsList = new LinkedList<>();
    }

    public void append(Expression expression) {
        expressionsList.add(expression);
    }
    public Expression popExpression() {
        return expressionsList.remove(expressionsList.size() - 1);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Sequence[");
        expressionsList.forEach(e -> {
            stringBuilder.append(e.toString());
            stringBuilder.append(' ');
        });
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append(']');

        return stringBuilder.toString();
    }
}
