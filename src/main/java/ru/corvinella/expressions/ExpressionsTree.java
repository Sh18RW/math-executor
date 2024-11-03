package ru.corvinella.expressions;

import ru.corvinella.expressions.entries.*;
import ru.corvinella.tokens.*;
import ru.corvinella.tokens.types.OperationType;
import ru.corvinella.tokens.types.ParenthesisType;

import java.util.LinkedList;
import java.util.List;

public class ExpressionsTree {
    private Expression root;

    private final List<Token<?>> tokens;
    private final List<ExpressionState> expressionStates;


    public ExpressionsTree(List<Token<?>> tokens) {
        expressionStates = new LinkedList<>();
        expressionStates.add(new ExpressionState(
                new SequenceExpression(), ExpressionState.ReadingType.Sum));
        this.tokens = tokens;
    }

    public void build() {
        for (Token<?> token: tokens) {
            processToken(token);
        }

        root = expressionStates.get(0).pointer;
    }

    private void processToken(Token<?> token) {
        Expression expressionToAdd = null;
        boolean appendExpression = true;
        ExpressionState expressionState = getExpressionState();

        // la pasta. I am not sure how to make it look beauty.
        // it should work, but what cost?
        switch (token.getType()) {
            case Operation:
                OperationToken operationToken = (OperationToken) token;
                if (expressionState.waitedType == ExpressionState.ReadingTokenType.Number) {
                    if (operationToken.getValue() == OperationType.Minus) {
                        expressionState.negativeValueNext = true;
                        return;
                    }

                    // TODO: make expression tree exception
                    throw new IllegalStateException();
                }

                boolean appendOperation = true;

                switch (operationToken.getValue()) {
                    case Plus:
                    case Minus:
                        while (expressionState.readingType != ExpressionState.ReadingType.Sum
                                && expressionState.readingType != ExpressionState.ReadingType.Argument) {
                            endCurrentState();
                            expressionState = getExpressionState();
                        }
                        break;
                    case Multiply:
                    case Divide:
                        if (expressionState.readingType == ExpressionState.ReadingType.Multiply) {
                            break;
                        }
                    case Degree:
                        assert expressionState.pointer instanceof SequenceExpression; // it shouldn't happen

                        Expression previousExpression = ((SequenceExpression) expressionState.pointer).popExpression();

                        SequenceExpression newSequenceExpression = new SequenceExpression();
                        newSequenceExpression.append(previousExpression);

                        ExpressionState newState = new ExpressionState(newSequenceExpression,
                                operationToken.getValue() == OperationType.Degree ? ExpressionState.ReadingType.Degree : ExpressionState.ReadingType.Multiply);
                        newState.waitedType = ExpressionState.ReadingTokenType.Number;
                        expressionStates.add(newState);
                        expressionToAdd = newSequenceExpression;

                        OperationExpression operationExpression = new OperationExpression(operationToken);
                        newSequenceExpression.append(operationExpression);

                        appendOperation = false;

                        break;
                }

                if (appendOperation) {
                    expressionToAdd = new OperationExpression(operationToken);
                    expressionState.waitedType = ExpressionState.ReadingTokenType.Number;
                }

                break;
            case Number:
            case Word:
                if (expressionState.waitedType == ExpressionState.ReadingTokenType.Operation) {
                    processToken(new OperationToken(OperationType.Multiply, token.getTracer()));

                    expressionState = getExpressionState();
                }

                boolean isNumber = token instanceof NumberToken;

                boolean isNegative = expressionState.negativeValueNext;
                if (isNegative) {
                    expressionState.negativeValueNext = false;
                }

                expressionState.waitedType = ExpressionState.ReadingTokenType.Operation;

                if (isNumber) {
                    expressionToAdd = new NumberExpression(isNegative, (NumberToken) token);
                } else {
                    WordToken wordToken = (WordToken) token;

                    switch (wordToken.getValue()) {
                        case Pi:
                        case E:
                            expressionToAdd = new ConstantExpression(wordToken);
                            break;
                        case Log:
                        case Sin:
                        case Cos:
                        case Tg:
                        case Ctg:
                            FunctionExpression functionExpression = new FunctionExpression(wordToken);
                            ArgumentsExpression argumentsExpression = new ArgumentsExpression();
                            ExpressionState argumentsState = new ExpressionState(argumentsExpression, ExpressionState.ReadingType.Argument);
                            expressionStates.add(argumentsState);
                            SequenceExpression sequenceExpression = new SequenceExpression();
                            ExpressionState newState = new ExpressionState(sequenceExpression, ExpressionState.ReadingType.Sum);
                            newState.ignoreOpenParenthesis = true;
                            expressionStates.add(newState);
                            argumentsExpression.appendExpression(sequenceExpression);

                            functionExpression.addArguments(argumentsExpression);
                            expressionToAdd = functionExpression;
                            break;
                        default:
                            // TODO: a normal exception
                            throw new IllegalStateException();
                    }
                }

                // it means that a function has a single argument
                if (expressionState.ignoreOpenParenthesis) {
                    endCurrentState(); // closing sequence
                    endCurrentState(); // closing arguments
                }

                break;
            case ArgumentsSeparator:

                while (expressionState.readingType != ExpressionState.ReadingType.Argument) {
                    endCurrentState();
                    expressionState = getExpressionState();
                }

                expressionToAdd = new SequenceExpression();
                expressionStates.add(new ExpressionState(expressionToAdd, ExpressionState.ReadingType.Sum));

                break;
            case ArgumentsParenthesis:
                appendExpression = false;
                ArgumentsParenthesisToken argumentsParenthesisToken = (ArgumentsParenthesisToken) token;

                if (argumentsParenthesisToken.getValue() == ParenthesisType.Open) {
                    assert expressionState.ignoreOpenParenthesis;

                    expressionState.ignoreOpenParenthesis = false;
                } else {
                    // move to arguments expression
                    while (expressionState.readingType != ExpressionState.ReadingType.Argument) {
                        endCurrentState();
                        expressionState = getExpressionState();
                    }

                    endCurrentState();
                }

                break;
            case Parenthesis:
                ParenthesisToken parenthesisToken = (ParenthesisToken) token;

                if (parenthesisToken.getValue() == ParenthesisType.Open) {
                    expressionToAdd = new SequenceExpression();

                    expressionState.waitedType = ExpressionState.ReadingTokenType.Operation;

                    ExpressionState newState = new ExpressionState(expressionToAdd, ExpressionState.ReadingType.Sum);
                    newState.openedWithParenthesis = true;
                    expressionStates.add(newState);
                } else {
                    appendExpression = false;

                    while (!expressionState.openedWithParenthesis) {
                        endCurrentState();
                        expressionState = getExpressionState();
                    }

                    endCurrentState();
                }

                break;
            default:
                throw new IllegalStateException(
                        String.format(
                                "%s is not currently supported at %d.",
                                token.getType(),
                                token.getTracer()));
        }

        if (appendExpression) {
            expressionState.appendExpression(expressionToAdd);
        }
    }

    private ExpressionState getExpressionState() {
        return expressionStates.get(expressionStates.size() - 1);
    }

    private void endCurrentState() {
        if (expressionStates.size() <= 1) {
            throw new IllegalStateException(
                    String.format("For some reason an current expression state was ended, but expressions size is %d.",
                            expressionStates.size()));
        }

        expressionStates.remove(expressionStates.size() - 1);
    }

    public final Expression getRoot() {
        if (root == null) {
            throw new IllegalStateException("Tried to get root of expression tree, but root is null. Please check that you called it before ExpressionTree#build().");
        }

        return root;
    }
}
