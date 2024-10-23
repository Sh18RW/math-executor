package ru.corvinella.expressions;

import ru.corvinella.expressions.entries.Expression;
import ru.corvinella.expressions.entries.SequenceExpression;
import ru.corvinella.tokens.TokenType;

/**
 * @author sh18rw
 */
public class ExpressionState {
    public Expression pointer;
    public ReadingTokenType waitedType;
    public final ReadingType readingType;

    public boolean negativeValueNext;
    public boolean awaitsArguments;

    public boolean expressionStart;

    public ExpressionState(Expression pointer, ReadingType readingType) {
        this.pointer = pointer;
        this.negativeValueNext = false;
        this.expressionStart = true;
        this.awaitsArguments = false;
        this.readingType = readingType;
        this.waitedType = ReadingTokenType.Number;
    }

    public void appendExpression(Expression expression) {
        assert pointer instanceof SequenceExpression;
        ((SequenceExpression) pointer).append(expression);
    }

    public enum ReadingType {
        Argument, // reading until close parenthesis or argument separator
        Sum, // reading minus and plus operations
        Multiply, // reading multiply and divide operations
        Degree, // reading only one expression
        Parenthesis, // reading until close parenthesis
    }

    public enum ReadingTokenType {
        Number, // any functions, constants, number
        Operation, // any operations
    }
}
