package ru.corvinella.math;

import ru.corvinella.expressions.entries.ArgumentsExpression;
import ru.corvinella.expressions.entries.FunctionExpression;
import ru.corvinella.expressions.entries.SequenceExpression;
import ru.corvinella.math.exceptions.CalculatorArgumentCountException;
import ru.corvinella.math.exceptions.CalculatorException;
import ru.corvinella.math.exceptions.CalculatorIllegalArgumentException;

import java.util.HashMap;
import java.util.Map;

/**
 * Supported functions equals to {@link FunctionExpression#supportedFunctions}.
 * @author sh18rw
 */
public class FunctionCalculator implements ICalculator<FunctionExpression> {
    private final Map<String, CalculatorFunction> processors;

    public FunctionCalculator() {
        processors = new HashMap<>();

        processors.put("log", this::calculateLog);
        processors.put("sin", this::calculateSin);
        processors.put("cos", this::calculateCos);
        processors.put("tg", this::calculateTg);
        processors.put("ctg", this::calculateCtg);

        if (!processors.keySet().containsAll(FunctionExpression.supportedFunctions)) {
            throw new IllegalStateException("Developer not implemented all functions, please contact them and rollback version until it is fixed.");
        }
    }

    @Override
    public Double calculate(FunctionExpression expression) throws CalculatorException {
        ArgumentsExpression argumentsExpression = expression.getArguments();

        return processors.get(expression.getFunctionName()).apply(argumentsExpression);
    }

    private Double calculateLog(ArgumentsExpression argumentsExpression) throws CalculatorException {
        if (argumentsExpression.getArgumentsCount() != 2) {
            throw new CalculatorArgumentCountException(2, argumentsExpression.getArgumentsCount(), argumentsExpression.getToken());
        }

        Double first = Calculator.getInstance().calculate((SequenceExpression) argumentsExpression.getArgument(0));
        Double second = Calculator.getInstance().calculate((SequenceExpression) argumentsExpression.getArgument(1));

        if (first <= 0 || first == 1) {
            throw new CalculatorIllegalArgumentException("The first argument of logarithm must be greater than zero except one.", argumentsExpression.getToken());
        }

        if (second <= 0) {
            throw new CalculatorIllegalArgumentException("The second argument of logarithm must be greater than zero.", argumentsExpression.getToken());
        }

        return Math.log(second) / Math.log(first);
    }

    private Double calculateSin(ArgumentsExpression argumentsExpression) throws CalculatorException {
        if (argumentsExpression.getArgumentsCount() != 1) {
            throw new CalculatorArgumentCountException(1, argumentsExpression.getArgumentsCount(), argumentsExpression.getToken());
        }

        Double radians = Calculator.getInstance().calculate((SequenceExpression) argumentsExpression.getArgument(0));

        return Math.sin(radians);
    }

    private Double calculateCos(ArgumentsExpression argumentsExpression) throws CalculatorException {
        if (argumentsExpression.getArgumentsCount() != 1) {
            throw new CalculatorArgumentCountException(1, argumentsExpression.getArgumentsCount(), argumentsExpression.getToken());
        }

        Double radians = Calculator.getInstance().calculate((SequenceExpression) argumentsExpression.getArgument(0));

        return Math.cos(radians);
    }

    private Double calculateTg(ArgumentsExpression argumentsExpression) throws CalculatorException {
        if (argumentsExpression.getArgumentsCount() != 1) {
            throw new CalculatorArgumentCountException(1, argumentsExpression.getArgumentsCount(), argumentsExpression.getToken());
        }

        Double radians = Calculator.getInstance().calculate((SequenceExpression) argumentsExpression.getArgument(0));

        // checks for tg(x), where x != Pi/2 + Pi*k, where k is ℤ
        // tg(x) = sin(x) / cos(x) => cos(x) != 0
        if (radians % (Math.PI) == Math.PI / 2) {
            throw new CalculatorIllegalArgumentException("The argument of tangent must not be equal to Pi/2 + Pi+k, where ℤ.", argumentsExpression.getToken());
        }

        return Math.tan(radians);
    }

    private Double calculateCtg(ArgumentsExpression argumentsExpression) throws CalculatorException {
        if (argumentsExpression.getArgumentsCount() != 1) {
            throw new CalculatorArgumentCountException(1, argumentsExpression.getArgumentsCount(), argumentsExpression.getToken());
        }

        Double radians = Calculator.getInstance().calculate((SequenceExpression) argumentsExpression.getArgument(0));

        // checks for tg(x), where x != Pi/2 + Pi*k, where k is ℤ
        // tg(x) = sin(x) / cos(x) => cos(x) != 0
        if (radians % (Math.PI) == Math.PI / 2) {
            throw new CalculatorIllegalArgumentException("The argument of cotangent must not be equal to Pi/2 + Pi+k, where ℤ.", argumentsExpression.getToken());
        }

        return 1.0 / Math.tan(radians);
    }

    private interface CalculatorFunction {
        public Double apply(ArgumentsExpression argumentsExpression) throws CalculatorException;
    }
}
