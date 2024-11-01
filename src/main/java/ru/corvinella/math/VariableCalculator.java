package ru.corvinella.math;

import ru.corvinella.expressions.entries.*;

/**
 * @author sh18rw
 */
public class VariableCalculator implements ICalculator<ValueExpression> {
    private final static FunctionCalculator functionCalculator;
    private final static ConstantCalculator constantCalculator;

    static {
        functionCalculator = new FunctionCalculator();
        constantCalculator = new ConstantCalculator();
    }

    @Override
    public Double calculate(ValueExpression expression) {
        if (expression instanceof NumberExpression) {
            NumberExpression numberExpression = (NumberExpression) expression;

            Double value = numberExpression.getToken().getValue();

            if (numberExpression.isNegative()) {
                value *= -1;
            }

            return value;
        } else if (expression instanceof FunctionExpression) {
            FunctionExpression functionExpression = (FunctionExpression) expression;

            return functionCalculator.calculate(functionExpression);
        } else if (expression instanceof ConstantExpression) {
            ConstantExpression constantExpression = (ConstantExpression) expression;

            return constantCalculator.calculate(constantExpression);
        } else if (expression instanceof SequenceExpression) {
            SequenceExpression sequenceExpression = (SequenceExpression) expression;

            return Calculator.getInstance().calculate(sequenceExpression);
        }

        // TODO: make an specific exception for calculator
        throw new IllegalArgumentException();
    }
}
