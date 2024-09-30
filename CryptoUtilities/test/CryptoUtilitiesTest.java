import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * @author Jared Alonzo
 *
 */
public class CryptoUtilitiesTest {

    /*
     * Tests of reduceToGCD
     */

    @Test
    public void testReduceToGCD_0_0() {
        NaturalNumber n = new NaturalNumber2(0);
        NaturalNumber nExpected = new NaturalNumber2(0);
        NaturalNumber m = new NaturalNumber2(0);
        NaturalNumber mExpected = new NaturalNumber2(0);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals(nExpected, n);
        assertEquals(mExpected, m);
    }

    @Test
    public void testReduceToGCD_30_21() {
        final NaturalNumber n = new NaturalNumber2(30);
        final NaturalNumber nExpected = new NaturalNumber2(3);
        final NaturalNumber m = new NaturalNumber2(21);
        final NaturalNumber mExpected = new NaturalNumber2(0);
        CryptoUtilities.reduceToGCD(n, m);
        assertEquals(nExpected, n);
        assertEquals(mExpected, m);
    }

    /*
     * Tests of isEven
     */

    @Test
    public void testIsEven_0() {
        NaturalNumber n = new NaturalNumber2(0);
        NaturalNumber nExpected = new NaturalNumber2(0);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(nExpected, n);
        assertEquals(true, result);
    }

    @Test
    public void testIsEven_1() {
        NaturalNumber n = new NaturalNumber2(1);
        NaturalNumber nExpected = new NaturalNumber2(1);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(nExpected, n);
        assertEquals(false, result);
    }

    /*
     * Tests of powerMod
     */

    @Test
    public void testPowerMod_0_0_2() {
        NaturalNumber n = new NaturalNumber2(0);
        NaturalNumber nExpected = new NaturalNumber2(1);
        NaturalNumber p = new NaturalNumber2(0);
        NaturalNumber pExpected = new NaturalNumber2(0);
        NaturalNumber m = new NaturalNumber2(2);
        NaturalNumber mExpected = new NaturalNumber2(2);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals(nExpected, n);
        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    @Test
    public void testPowerMod_17_18_19() {
        final NaturalNumber n = new NaturalNumber2(17);
        final NaturalNumber nExpected = new NaturalNumber2(1);
        final NaturalNumber p = new NaturalNumber2(18);
        final NaturalNumber pExpected = new NaturalNumber2(18);
        final NaturalNumber m = new NaturalNumber2(19);
        final NaturalNumber mExpected = new NaturalNumber2(19);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals(nExpected, n);
        assertEquals(pExpected, p);
        assertEquals(mExpected, m);
    }

    @Test
    public void testIsEven_12345678() {
        final NaturalNumber n = new NaturalNumber2(12345678);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(true, result);
    }

    @Test
    public void testIsEven_12345679() {
        final NaturalNumber n = new NaturalNumber2(12345679);
        boolean result = CryptoUtilities.isEven(n);
        assertEquals(false, result);
    }

    @Test
    public void testPowerMod_1_2_3() {
        NaturalNumber n = new NaturalNumber2(1);
        NaturalNumber p = new NaturalNumber2(2);
        final NaturalNumber m = new NaturalNumber2(3);
        CryptoUtilities.powerMod(n, p, m);
        assertEquals(new NaturalNumber2(1), n);
    }

    @Test
    public void testIsWitnessToCompositeness_Prime() {
        NaturalNumber w = new NaturalNumber2(2);
        final NaturalNumber n = new NaturalNumber2(5);
        boolean result = CryptoUtilities.isWitnessToCompositeness(w, n);
        assertEquals(false, result);
    }

    @Test
    public void testIsPrime1_PrimeNumber() {
        final NaturalNumber n = new NaturalNumber2(5);
        boolean result = CryptoUtilities.isPrime1(n);
        assertEquals(true, result);
    }

    @Test
    public void testIsPrime1_CompositeNumber() {
        final NaturalNumber n = new NaturalNumber2(4);
        boolean result = CryptoUtilities.isPrime1(n);
        assertEquals(false, result);
    }

    @Test
    public void testIsPrime2_PrimeNumber() {
        final NaturalNumber n = new NaturalNumber2(11);
        boolean result = CryptoUtilities.isPrime2(n);
        assertEquals(true, result);
    }

    @Test
    public void testIsPrime2_CompositeNumber() {
        final NaturalNumber n = new NaturalNumber2(10);
        boolean result = CryptoUtilities.isPrime2(n);
        assertEquals(false, result);
    }

    @Test
    public void testGenerateNextLikelyPrime_FromComposite() {
        final NaturalNumber n = new NaturalNumber2(14);
        final NaturalNumber expect = new NaturalNumber2(17);
        CryptoUtilities.generateNextLikelyPrime(n);
        assertEquals(expect, n);
    }

}
