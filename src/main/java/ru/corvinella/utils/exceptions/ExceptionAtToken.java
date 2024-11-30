package ru.corvinella.utils.exceptions;

import ru.corvinella.tokens.Token;

/**
 * @author sh18rw
 */
public abstract class ExceptionAtToken extends Exception {
    private final Token<?> atToken;

    public ExceptionAtToken(String text, Token<?> atToken) {
        super(text);
        this.atToken = atToken;
    }

    public Token<?> getToken() {
        return atToken;
    }
}
