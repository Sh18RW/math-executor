package ru.corvinella.math;

import ru.corvinella.expressions.entries.Expression;
import ru.corvinella.expressions.entries.OperationExpression;
import ru.corvinella.expressions.entries.SequenceExpression;
import ru.corvinella.expressions.entries.ValueExpression;
import ru.corvinella.tokens.OperationType;

/**
 * @author sh18rw
 */
public class SequenceCalculator implements ICalculator<SequenceExpression> {
    private static final SequenceCalculator instance;
    private static final VariableCalculator variableCalculator;

    static {
        instance = new SequenceCalculator();
        variableCalculator = new VariableCalculator();
    }
    public static SequenceCalculator getInstance() {
        return instance;
    }

    @Override
    public Double calculate(SequenceExpression expression) {
        Double result = 0.0;
        OperationType currentOperation = OperationType.Plus;

        for (Expression e : expression.getExpressions()) {
            if (e instanceof ValueExpression) {
                Double value = variableCalculator.calculate((ValueExpression) e);

                switch (currentOperation) {
                    case Plus:
                        result += value;
                        break;
                    case Minus:
                        result -= value;
                        break;
                    case Multiply:
                        result *= value;
                        break;
                    case Divide:
                        result /= value;
                        break;
                    case Degree:
                        result = Math.pow(result, value);
                        break;
                }
            } else if (e instanceof OperationExpression) {
                currentOperation = ((OperationExpression) e).getToken().getValue();
            }
        }

        return result;
    }
}
