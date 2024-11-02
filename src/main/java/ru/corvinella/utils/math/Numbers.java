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
                    String.format("An argument rate must be greater or equal 0, but it equals %f!", rate));
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

    /**
     * Makes double some prettier by removing some numbers after point.
     * @param number number to make prettier.
     * @param maxLength max numbers after point.
     * @return number with minimum numbers after point.
     */
    public static Double makeLookPrettier(Double number, int maxLength) {
        Long longValue = number.longValue();
        double multiplier = Math.pow(10, Math.min(countNumbersAfterPoint(number), maxLength));

        if (maxLength == 0) {
            return (double) longValue;
        }

        double afterPoint = number - longValue;
        afterPoint *= multiplier;

        if (lessThan(5.0, (afterPoint - (long) afterPoint) * 10, true)) {
            afterPoint += 1;
        }

        return longValue.doubleValue() + ((double) ((long) afterPoint) / multiplier);
    }

    /**
     * Equals to {@code checking < max} but with a rate. The rate equals to {@link Numbers#DEFAULT_RATE}.
     * @param checking a lower checking number.
     * @param max a greater number.
     * @param includeMax checks for equaling {@code checking} to {@code max}.
     * @return true if {@code checking - rate} lower than {@code max + rate} or if {@code includeMax} true, {@code checking} ~ {@code max}.
     */
    public static boolean lessThan(Double checking, Double max, boolean includeMax) {
        return lessThan(checking, max, Numbers.DEFAULT_RATE, includeMax);
    }

    /**
     * Equals to {@code checking < max} but with a rate.
     * @param checking a lower checking number.
     * @param max a greater number.
     * @param rate a checking rate.
     * @param includeMax checks for equaling {@code checking} to {@code max}.
     * @return true if {@code checking - rate} lower than {@code max + rate} or if {@code includeMax} true, {@code checking} ~ {@code max}.
     */
    public static boolean lessThan(Double checking, Double max, Double rate, boolean includeMax) {
        if (rate < 0) {
            throw new IllegalArgumentException(
                    String.format("An argument rate must be greater or equal 0, but it equals %f!", rate));
        }

        return (includeMax && equals(checking, max, rate)) || checking - rate < max + rate;
    }

    /**
     * {@code countTensNumbers(1)} equals 1.
     * {@code countTensNumbers(1000)} equals 3.
     * @return tens count in a number.
     */
    public static int countTensNumbers(double number) {
        int result = 0;
        number = Math.abs(number * 10);
        while (number >= 10) {
            result += 1;
            number /= 10;
        }

        return result;
    }

    /**
     * @return count of numbers after a dot in a number.
     */
    public static int countNumbersAfterPoint(double number) {
        int result = 0;
        double multiplier = 1;

        while (Math.abs((number * multiplier) - (long) (number * multiplier)) > 0) {
            result += 1;
            multiplier *= 10;
        }

        return result;
    }
}
