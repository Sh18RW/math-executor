package ru.corvinella.math.exceptions;

import ru.corvinella.tokens.Token;

/**
 * @author sh18rw
 */
public class CalculatorIllegalArgumentException extends CalculatorException {
    public CalculatorIllegalArgumentException(String text, Token<?> atToken) {
        super(text, atToken);
    }
}
