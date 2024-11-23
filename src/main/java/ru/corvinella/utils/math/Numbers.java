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
     * @param first the first number.
     * @param second the second number.
     * @param rate a maximum diff between two numbers.
     * @return true if a diff is lower than a rate.
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
     * @param first the first number.
     * @param second the second number.
     * @return true if a diff is lower than the {@link Numbers#DEFAULT_RATE}.
     */
    public static boolean equals(Double first, Double second) {
        return Numbers.equals(first, second, Numbers.DEFAULT_RATE);
    }

    /**
     * Makes double some prettier by removing some numbers after point.
     * @param number number to make prettier.
     * @param maxLength max numbers after a point.
     * @return a string with number and minimum numbers after point.
     */
    public static String getPrettierDoubleAsString(Double number, int maxLength) {
        StringBuilder prettier = new StringBuilder(String.format("%." + maxLength + "f", number));
        while (true) {
            char chatAtTheEnd = prettier.charAt(prettier.length() - 1);

            if (chatAtTheEnd != '0') {
                if (chatAtTheEnd == '.') {
                    prettier.deleteCharAt(prettier.length() - 1);
                }

                break;
            }

            prettier.deleteCharAt(prettier.length() - 1);
        }

        return prettier.toString();
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
     * {@code countTensNumbers(1000)} equals 4.
     * @return tens count in a number.
     */
    public static int countTensNumbers(double number) {
        int result = 0;
        number = Math.abs(number);
        while (number >= 1) {
            result += 1;
            number /= 10;
        }

        return result;
    }

    /**
     * @return count of numbers after a dot in a number.
     */
    public static int countNumbersAfterPoint(double number) {
        String string = Double.toString(number);
        int indexOfDot = string.indexOf('.');
        if (indexOfDot == -1) {
            return 0;
        }

        if (string.length() == indexOfDot + 2 && string.charAt(indexOfDot + 1) == '0') {
            return 0;
        }

        return string.length() - indexOfDot - 1;
    }
}
