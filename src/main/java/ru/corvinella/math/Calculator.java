package ru.corvinella.math;

import ru.corvinella.expressions.entries.SequenceExpression;
import ru.corvinella.math.exceptions.CalculatorException;

/**
 * @author sh18rw
 */
public class Calculator {
    private static final Calculator instance;

    static {
        instance = new Calculator();
    }

    private final SequenceCalculator sequenceCalculator;

    public static Calculator getInstance() {
        return instance;
    }

    private Calculator() {
        sequenceCalculator = new SequenceCalculator();
    }

    public Double calculate(SequenceExpression expression) throws CalculatorException {
        return sequenceCalculator.calculate(expression);
    }
}
