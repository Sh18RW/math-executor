package ru.corvinella.parser.exceptions;

public class ParserException extends Exception {
    private final String expression;
    private final int tracer;

    /**
     * Basic parser exception.
     * @param errorText a text of the error using in the initialisation of super.
     * @param expression an expression which caused exception.
     * @param tracer an index of a symbol which caused exception.
     */
    public ParserException(String errorText, String expression, int tracer) {
        super(String.format("%s at %d in \"%s\".", errorText, tracer, expression));

        this.expression = expression;
        this.tracer = tracer;
    }

    public String getExpression() {
        return expression;
    }

    public int getTracer() {
        return tracer;
    }
}
