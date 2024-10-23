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

    // single arguments like lg10
    public void addArguments(ValueExpression expression) {
        ArgumentsExpression argumentsExpression = new ArgumentsExpression();
        argumentsExpression.appendExpression(expression);
        addArguments(argumentsExpression);
    }

    public enum Function {
        Log,
    }
}
