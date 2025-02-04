package ru.corvinella.math;

import ru.corvinella.expressions.entries.Expression;
import ru.corvinella.expressions.entries.OperationExpression;
import ru.corvinella.expressions.entries.SequenceExpression;
import ru.corvinella.expressions.entries.ValueExpression;
import ru.corvinella.math.exceptions.CalculatorException;
import ru.corvinella.tokens.types.OperationType;

/**
 * @author sh18rw
 */
public class SequenceCalculator implements ICalculator<SequenceExpression> {
    private static final VariableCalculator variableCalculator;

    static {
        variableCalculator = new VariableCalculator();
    }

    @Override
    public Double calculate(SequenceExpression expression) throws CalculatorException {
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
