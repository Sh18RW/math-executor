package ru.corvinella;

import org.junit.Test;
import ru.corvinella.utils.math.Numbers;

import static org.junit.Assert.assertEquals;

/**
 * @author sh18rw
 */
public class NumbersUtilTest {
    @Test
    public void testMakePrettier() {
        makePrettierTest(1.15, 0, "1");
        makePrettierTest(1.13, 1, "1.1");
        makePrettierTest(1.15, 1, "1.2");
        makePrettierTest(1.15, 3, "1.15");
        makePrettierTest(1.2345478, 4, "1.2345");
        makePrettierTest(1.2345478, 5, "1.23455");
        makePrettierTest(1.23454783747746123678, 10, "1.2345478375");
        makePrettierTest(1.2345478374, 15, "1.2345478374");
        makePrettierTest(1.0, 10, "1");
        makePrettierTest(-10.123, 2, "-10.12");
        makePrettierTest(1.100293, 3, "1.1");
        makePrettierTest(1.000293, 2, "1");
    }

    @Test
    public void testCountingTensNumbers() {
        makeTensNumbersTest(1.0, 1);
        makeTensNumbersTest(10.0, 2);
        makeTensNumbersTest(-1000.0, 4);
        makeTensNumbersTest(-11234455.01246, 8);
        makeTensNumbersTest(10E6, 8);
    }

    @Test
    public void testCountingNumberAfterPoint() {
        makeNumbersAfterPointTest(1.0, 0);
        makeNumbersAfterPointTest(1.100293, 6);
        makeNumbersAfterPointTest(-1000., 0);
        makeNumbersAfterPointTest(-10101.101, 3);

    }

    private void makePrettierTest(Double number, int maxLength, String expected) {
        assertEquals(expected, Numbers.getPrettierDouble(number, maxLength));
    }

    private void makeTensNumbersTest(Double number, int expected) {
        assertEquals(expected, Numbers.countTensNumbers(number));
    }

    private void makeNumbersAfterPointTest(Double number, int expected) {
        assertEquals(expected, Numbers.countNumbersAfterPoint(number));
    }
}
