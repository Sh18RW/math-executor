package ru.corvinella.expressions.entries;

import ru.corvinella.tokens.WordToken;

/**
 * @author sh18rw
 */
public class ConstantExpression extends ValueExpression {
    private final Constant constantType;
    private final WordToken token;

    public ConstantExpression(WordToken wordToken) {
        this.token = wordToken;

        switch (wordToken.getValue()) {
            case Pi:
                constantType = Constant.Pi;
                break;
            default:
                throw new IllegalStateException(
                        String.format("For some reason there is no realisation for %s constant.", token.getValue()));
        }
    }

    public Constant getConstantType() {
        return constantType;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ConstantExpression)) {
            return false;
        }

        ConstantExpression constantExpression = (ConstantExpression) obj;

        return constantExpression.constantType == this.constantType;
    }

    public enum Constant {
        Pi,
        E,
    }
}
