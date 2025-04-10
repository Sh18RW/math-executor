package ru.corvinella.expressions.entries;

import java.util.LinkedList;
import java.util.List;

/**
 * @author sh18rw
 */
public class SequenceExpression extends ValueExpression {
    private final List<Expression> expressionsList;

    public SequenceExpression(boolean isNegative) {
        super(isNegative);
        this.expressionsList = new LinkedList<>();
    }

    public void append(Expression expression) {
        expressionsList.add(expression);
    }
    public Expression popExpression() {
        return expressionsList.remove(expressionsList.size() - 1);
    }

    public List<Expression> getExpressions() {
        return expressionsList;
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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SequenceExpression)) {
            return false;
        }

        SequenceExpression sequenceExpression = (SequenceExpression) obj;

        return sequenceExpression.expressionsList.equals(this.expressionsList);
    }
}
