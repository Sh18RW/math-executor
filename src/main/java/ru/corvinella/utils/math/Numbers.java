package ru.corvinella.utils.math;

/**
 * My utils to work with numbers.
 * @author sh18rw
 * @version 1.0
 */
public class Numbers {
    public static final Double DEFAULT_RATE = 0.1e-5;

    /**
     * Checks  for equaling numbers with some rate.
     * @param first first number.
     * @param second second number.
     * @param rate maximum diff between two numbers.
     * @return true if diff is lower than a rate.
     */
    public static boolean equals(Double first, Double second, Double rate) {
        if (rate < 0) {
            throw new IllegalArgumentException(
                    String.format("Argument rate must be greater or equal 0, but it equals %f!", rate));
        }

        return Math.abs(first - second) <= rate;
    }

    /**
     * Checks  for equaling numbers with some rate.
     * Calls {@link Numbers#equals(Double, Double, Double)} with {@link Numbers#DEFAULT_RATE}.
     * @param first first number.
     * @param second second number.
     * @return true if diff is lower than the {@link Numbers#DEFAULT_RATE}.
     */
    public static boolean equals(Double first, Double second) {
        return Numbers.equals(first, second, Numbers.DEFAULT_RATE);
    }
}
