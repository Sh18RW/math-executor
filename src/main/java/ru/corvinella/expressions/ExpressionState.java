package ru.corvinella.expressions;

import ru.corvinella.expressions.entries.*;
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
    public boolean ignoreOpenParenthesis = false;
    public boolean openedWithParenthesis = false;

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
        if (pointer instanceof SequenceExpression) {
            ((SequenceExpression) pointer).append(expression);
        } else if (pointer instanceof ArgumentsExpression) {
            ((ArgumentsExpression) pointer).appendExpression(expression);
        }
    }

    public enum ReadingType {
        Argument, // reading opening or closing arguments parenthesis or argument separator
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
