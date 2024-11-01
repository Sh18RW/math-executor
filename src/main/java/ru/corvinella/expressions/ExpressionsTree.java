package ru.corvinella.expressions;

import ru.corvinella.expressions.entries.Expression;
import ru.corvinella.expressions.entries.NumberExpression;
import ru.corvinella.expressions.entries.OperationExpression;
import ru.corvinella.expressions.entries.SequenceExpression;
import ru.corvinella.tokens.NumberToken;
import ru.corvinella.tokens.OperationToken;
import ru.corvinella.tokens.OperationType;
import ru.corvinella.tokens.Token;

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
            makeToken(token);
        }

        root = expressionStates.get(0).pointer;
    }

    private void makeToken(Token<?> token) {
        Expression expressionToAdd = null;
        ExpressionState expressionState = getExpressionState();
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
                        while (expressionState.readingType != ExpressionState.ReadingType.Sum) {
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
                NumberToken numberToken = (NumberToken) token;
                if(expressionState.waitedType == ExpressionState.ReadingTokenType.Operation) {
                    OperationToken multiplyToken = new OperationToken(OperationType.Multiply, numberToken.getTracer());
                    OperationExpression multiplyExpression = new OperationExpression(multiplyToken);
                    expressionState.appendExpression(multiplyExpression);
                }

                boolean isNegative = expressionState.negativeValueNext;
                if (isNegative) {
                    expressionState.negativeValueNext = false;
                }

                expressionToAdd = new NumberExpression(isNegative, numberToken);
                expressionState.waitedType = ExpressionState.ReadingTokenType.Operation;

                break;
            default:
                throw new IllegalStateException(
                        String.format(
                                "%s is not currently supported at %d.",
                                token.getType(),
                                token.getTracer()));
        }

        expressionState.appendExpression(expressionToAdd);
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
