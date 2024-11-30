package ru.corvinella.expressions.entries;

import ru.corvinella.tokens.ParenthesisToken;
import ru.corvinella.tokens.Token;

import java.util.LinkedList;
import java.util.List;

/**
 * @author sh18rw
 */
public class ArgumentsExpression extends Expression {
    private final List<Expression> arguments;
    private final Token<?> token;

    public ArgumentsExpression(Token<?> token) {
        this.arguments = new LinkedList<>();
        this.token = token;
    }

    public final void appendExpression(Expression valueExpression) {
        arguments.add(valueExpression);
    }

    public final int getArgumentsCount() {
        return arguments.size();
    }

    public final Expression getArgument(int index) {
        return arguments.get(index);
    }

    public Token<?> getToken() {
        return token;
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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ArgumentsExpression)) {
            return false;
        }

        ArgumentsExpression argumentsExpression = (ArgumentsExpression) obj;

        return argumentsExpression.arguments.equals(this.arguments);
    }
}
