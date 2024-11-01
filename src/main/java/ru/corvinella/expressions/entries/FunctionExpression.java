package ru.corvinella.expressions.entries;

import ru.corvinella.tokens.WordToken;

/**
 * @author sh18rw
 */
public class FunctionExpression extends ValueExpression {
    private final Function functionType;
    private final WordToken token;
    private ArgumentsExpression arguments;

    public FunctionExpression(WordToken token) {
        this.token = token;

        switch (token.getValue()) {
            case Log:
                functionType = Function.Log;
                break;
            default:
                throw new IllegalStateException(
                        String.format("For some reason there is no realisation for %s function.", token.getValue()));
        }
    }

    public void addArguments(ArgumentsExpression argumentsExpression) {
        this.arguments = argumentsExpression;
    }

    public Function getFunctionType() {
        return functionType;
    }

    public ArgumentsExpression getArguments() {
        return arguments;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FunctionExpression)) {
            return false;
        }

        FunctionExpression functionExpression = (FunctionExpression) obj;

        return functionExpression.functionType.equals(this.functionType)
                && functionExpression.arguments.equals(this.arguments);
    }

    @Override
    public String toString() {
        return "Function["
                + functionType
                + " "
                + arguments.toString();
    }

    public enum Function {
        Log,
    }
}
