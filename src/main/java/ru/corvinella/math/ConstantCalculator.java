package ru.corvinella.math;

import ru.corvinella.expressions.entries.ConstantExpression;

import java.util.HashMap;
import java.util.Map;

/**
 * Supported constants equals to {@link ConstantExpression#supportedConstants}.
 * @author sh18rw
 */
public class ConstantCalculator implements ICalculator<ConstantExpression> {
    private final Map<String, Double> processors;

    public ConstantCalculator() {
        processors = new HashMap<>();

        processors.put("pi", Math.PI);
        processors.put("e", Math.E);

        if (!processors.keySet().containsAll(ConstantExpression.supportedConstants)) {
            throw new IllegalStateException();
        }
    }

    @Override
    public Double calculate(ConstantExpression expression) {
        return processors.get(expression.getConstantName());
    }
}
