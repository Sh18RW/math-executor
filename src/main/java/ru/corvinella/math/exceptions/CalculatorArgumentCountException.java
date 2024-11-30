package ru.corvinella.math.exceptions;

import ru.corvinella.tokens.Token;

/**
 * @author sh18rw
 */
public class CalculatorArgumentCountException extends CalculatorException {
    private final int expectedCount;
    private final int gotCount;
    public CalculatorArgumentCountException(int expectedCount, int gotCount, Token<?> atToken) {
        super(String.format("Waited for %d arguments, but got %d", expectedCount, gotCount), atToken);

        this.expectedCount = expectedCount;
        this.gotCount = gotCount;
    }

    public int getExpectedCount() {
        return expectedCount;
    }

    public int getGotCount() {
        return gotCount;
    }
}
