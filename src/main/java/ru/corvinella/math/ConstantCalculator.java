package ru.corvinella.math;

import ru.corvinella.expressions.entries.ConstantExpression;

/**
 * @author sh18rw
 */
public class ConstantCalculator implements ICalculator<ConstantExpression> {
    @Override
    public Double calculate(ConstantExpression expression) {
        switch (expression.getConstantType()) {
            case Pi:
                return Math.PI;
        }

        return 0.0;
    }
}
