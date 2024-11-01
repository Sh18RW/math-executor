package ru.corvinella.expressions.entries;

import java.util.LinkedList;
import java.util.List;

/**
 * @author sh18rw
 */
public class ArgumentsExpression extends Expression {
    private final List<ValueExpression> arguments;

    public ArgumentsExpression() {
        this.arguments = new LinkedList<>();
    }

    public final void appendExpression(ValueExpression valueExpression) {
        arguments.add(valueExpression);
    }

    public final int getArgumentsCount() {
        return arguments.size();
    }

    public final ValueExpression getArgument(int index) {
        return arguments.get(index);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Arguments[");
        for (Expression expression : arguments) {
            stringBuilder.append(expression.toString());
            stringBuilder.append(' ');
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.append(']');

        return stringBuilder.toString();
    }
}
