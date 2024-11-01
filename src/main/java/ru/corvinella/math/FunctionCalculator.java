package ru.corvinella.math;

import ru.corvinella.expressions.entries.ArgumentsExpression;
import ru.corvinella.expressions.entries.FunctionExpression;
import ru.corvinella.expressions.entries.SequenceExpression;

/**
 * @author sh18rw
 */
public class FunctionCalculator implements ICalculator<FunctionExpression> {
    @Override
    public Double calculate(FunctionExpression expression) {
        switch (expression.getFunctionType()) {
            case Log:
                ArgumentsExpression argumentsExpression = expression.getArguments();
                if (argumentsExpression.getArgumentsCount() != 2) {
                    // TODO: make an specific exception for calculator
                    throw new IllegalArgumentException();
                }

                Double first = Calculator.getInstance().calculate((SequenceExpression) argumentsExpression.getArgument(0));
                Double second = Calculator.getInstance().calculate((SequenceExpression) argumentsExpression.getArgument(1));

                if (first <= 0 || first == 1) {
                    // TODO: make an specific exception for calculator
                    throw new IllegalArgumentException();
                }

                if (second <= 0) {
                    // TODO: make an specific exception for calculator
                    throw new IllegalArgumentException();
                }

                return Math.log(second) / Math.log(first);
        }

        // TODO: make an specific exception for calculator
        throw new IllegalArgumentException();
    }
}
