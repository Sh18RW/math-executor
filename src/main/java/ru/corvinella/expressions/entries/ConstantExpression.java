package ru.corvinella.expressions.entries;

import ru.corvinella.tokens.WordToken;

import java.util.Arrays;
import java.util.List;

/**
 * @author sh18rw
 */
public class ConstantExpression extends ValueExpression {
    public static final List<String> supportedConstants = Arrays.asList("pi", "e");
    private final WordToken token;

    public ConstantExpression(WordToken wordToken) {
        this.token = wordToken;

        if (!supportedConstants.contains(token.getValue())) {
            throw new IllegalStateException();
        }
    }

    public String getConstantName() {
        return token.getValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ConstantExpression)) {
            return false;
        }

        ConstantExpression constantExpression = (ConstantExpression) obj;

        return constantExpression.getConstantName().equals(this.getConstantName());
    }
}
