package ru.corvinella.math.exceptions;

import ru.corvinella.tokens.Token;
import ru.corvinella.utils.exceptions.ExceptionAtToken;

/**
 * @author sh18rw
 */
public class CalculatorException extends ExceptionAtToken {
    public CalculatorException(String text, Token<?> atToken) {
        super(text, atToken);
    }
}
