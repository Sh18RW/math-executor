package ru.corvinella.math;

import ru.corvinella.expressions.entries.SequenceExpression;
import ru.corvinella.math.exceptions.CalculatorException;

/**
 * @author sh18rw
 */
public class Calculator {
    private static final Calculator instance;
    private static final SequenceCalculator sequenceCalculator;

    static {
        instance = new Calculator();
        sequenceCalculator = new SequenceCalculator();
    }
    public static Calculator getInstance() {
        return instance;
    }

    public Double calculate(SequenceExpression expression) throws CalculatorException {
        return sequenceCalculator.calculate(expression);
    }
}
