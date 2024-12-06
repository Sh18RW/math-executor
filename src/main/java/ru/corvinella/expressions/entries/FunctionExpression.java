package ru.corvinella.expressions.entries;

import ru.corvinella.tokens.WordToken;

import java.util.Arrays;
import java.util.List;

/**
 * @author sh18rw
 */
public class FunctionExpression extends ValueExpression {
    public static final List<String> supportedFunctions = Arrays.asList("log", "sin", "cos", "tg", "ctg");

    private final WordToken token;
    private ArgumentsExpression arguments;

    public FunctionExpression(WordToken token) {
        this.token = token;

        if (!supportedFunctions.contains(token.getValue())) {
            throw new IllegalStateException();
        }
    }

    public void addArguments(ArgumentsExpression argumentsExpression) {
        this.arguments = argumentsExpression;
    }

    public String getFunctionName() {
        return token.getValue();
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

        return functionExpression.getFunctionName().equals(this.getFunctionName())
                && functionExpression.arguments.equals(this.arguments);
    }

    @Override
    public String toString() {
        return "Function["
                + getFunctionName()
                + " "
                + arguments.toString();
    }
}
