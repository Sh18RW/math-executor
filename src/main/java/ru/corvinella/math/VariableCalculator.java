package ru.corvinella.math;

import ru.corvinella.expressions.entries.*;
import ru.corvinella.math.exceptions.CalculatorException;

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
    public Double calculate(ValueExpression expression) throws CalculatorException {
        Double value = null;
        if (expression instanceof NumberExpression) {
            NumberExpression numberExpression = (NumberExpression) expression;

            value = numberExpression.getToken().getValue();
        } else if (expression instanceof FunctionExpression) {
            FunctionExpression functionExpression = (FunctionExpression) expression;

            value = functionCalculator.calculate(functionExpression);
        } else if (expression instanceof ConstantExpression) {
            ConstantExpression constantExpression = (ConstantExpression) expression;

            value = constantCalculator.calculate(constantExpression);
        } else if (expression instanceof SequenceExpression) {
            SequenceExpression sequenceExpression = (SequenceExpression) expression;

            value = Calculator.getInstance().calculate(sequenceExpression);
        }

        if (value != null) {
            if (expression.isNegative()) {
                value *= -1;
            }

            return value;
        }

        // It should not ever happen.
        throw new IllegalStateException("For some reason there is an unimplemented processor for value expression.");
    }
}
