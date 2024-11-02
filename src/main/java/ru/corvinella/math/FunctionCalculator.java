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
        ArgumentsExpression argumentsExpression = expression.getArguments();

        switch (expression.getFunctionType()) {
            case Log:
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
            case Sin:
            case Cos:
            case Tg:
            case Ctg:
                return calculateTrigonometric(expression);
        }

        // TODO: make an specific exception for calculator
        throw new IllegalArgumentException();
    }

    private Double calculateTrigonometric(FunctionExpression functionExpression) {
        ArgumentsExpression argumentsExpression = functionExpression.getArguments();

        if (argumentsExpression.getArgumentsCount() != 1) {
            // TODO: make an specific exception for calculator
            throw new IllegalArgumentException();
        }


        Double radians = Calculator.getInstance().calculate((SequenceExpression) argumentsExpression.getArgument(0));

        switch (functionExpression.getFunctionType()) {
            case Sin:
                return Math.sin(radians);
            case Cos:
                return Math.cos(radians);
            case Tg:
                // checks for tg(x), where x != Pi/2 + Pi*k, where k is Z
                // tg(x) = sin(x) / cos(x) => cos(x) != 0
                if (radians % (Math.PI) == Math.PI / 2) {
                    // TODO: make an specific exception for calculator
                    throw new IllegalArgumentException();
                }
                return Math.tan(radians);
            case Ctg:
                // checks for ctg(x), where x != Pi*k, where k is Z
                // ctg(x) = cos(x) / sin(x) => sin(x) != 0
                if (radians % (Math.PI) == 0) {
                    // TODO: make an specific exception for calculator
                    throw new IllegalArgumentException();
                }
                return 1.0 / Math.tan(radians);
            default:
                // TODO: make an specific exception for calculator
                throw new IllegalArgumentException();
        }
    }
}
