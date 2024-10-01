import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;

/**
 * JUnit test fixture for {@code NaturalNumber}'s constructors and kernel
 * methods.
 *
 * @author Roble Gure
 *
 */
public abstract class NaturalNumberTest {

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new number
     * @ensures constructorTest = 0
     */
    protected abstract NaturalNumber constructorTest();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorTest = i
     */
    protected abstract NaturalNumber constructorTest(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorTest)
     */
    protected abstract NaturalNumber constructorTest(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorTest = n
     */
    protected abstract NaturalNumber constructorTest(NaturalNumber n);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @return the new number
     * @ensures constructorRef = 0
     */
    protected abstract NaturalNumber constructorRef();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorRef = i
     */
    protected abstract NaturalNumber constructorRef(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorRef)
     */
    protected abstract NaturalNumber constructorRef(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorRef = n
     */
    protected abstract NaturalNumber constructorRef(NaturalNumber n);

    // TODO - add test cases for four constructors, multiplyBy10, divideBy10, isZero

    /**
     * Test int constructor see if it can hold zero.
     */
    @Test
    public void constructorInt() {
        NaturalNumber test = this.constructorTest(0);
        NaturalNumber testExpected = this.constructorRef(0);
        assertEquals(testExpected, test);

    }

    /**
     * Test int constructor see if it can hold max value.
     */
    @Test
    public void constructorInt2() {
        NaturalNumber test = this.constructorTest(Integer.MAX_VALUE);
        NaturalNumber testExpected = this.constructorRef(Integer.MAX_VALUE);
        assertEquals(testExpected, test);

    }

    /**
     * Test int constructor see if it can hold big int value.
     */
    @Test
    public void constructorInt3() {
        NaturalNumber test = this.constructorTest(467599448);
        NaturalNumber testExpected = this.constructorRef(467599448);
        assertEquals(testExpected, test);

    }

    /**
     * Test string constructor with an empty value.
     */
    @Test
    public void constructorString1() {
        NaturalNumber test = this.constructorTest("0");
        NaturalNumber testExpected = this.constructorRef("0");
        assertEquals(testExpected, test);

    }

    /**
     * Test string constructor with a large value
     */
    @Test
    public void constructorString2() {
        NaturalNumber test = this
                .constructorTest("662627272773737882882828282773");
        NaturalNumber testExpected = this
                .constructorRef("662627272773737882882828282773");
        assertEquals(testExpected, test);

    }

    /**
     * Test natural number constructor to see if it can hold zero.
     */
    @Test
    public void constructorNaturalNumber() {
        NaturalNumber test = this.constructorTest();
        NaturalNumber testExpected = this.constructorRef();
        assertEquals(testExpected, test);

    }

    /**
     * Test natural number constructor to see if it can hold the int max value.
     */
    @Test
    public void constructorNaturalNumber2() {
        NaturalNumber test = this.constructorTest(Integer.MAX_VALUE);
        NaturalNumber testExpected = this.constructorRef(Integer.MAX_VALUE);
        assertEquals(testExpected, test);

    }

    /**
     * Test natural number constructor to see if it can hold a big int value.
     */
    @Test
    public void constructorNaturalNumber3() {
        NaturalNumber test = this.constructorTest(663773289);
        NaturalNumber testExpected = this.constructorRef(663773289);
        assertEquals(testExpected, test);

    }

    /**
     * Test isZero constructor with 0.
     */
    @Test
    public void isZeroInt() {
        NaturalNumber test = this.constructorTest(0);
        NaturalNumber testExpected = this.constructorRef(0);

        assertEquals(testExpected.isZero(), test.isZero());
    }

    /**
     * Test isZero constructor with int max.
     */
    @Test
    public void isZeroInt2() {
        NaturalNumber test = this.constructorTest(Integer.MAX_VALUE);
        NaturalNumber testExpected = this.constructorRef(Integer.MAX_VALUE);

        assertEquals(testExpected.isZero(), test.isZero());
    }

    /**
     * Test isZero constructor with very large number.
     */
    @Test
    public void isZeroInt3() {
        NaturalNumber test = this.constructorTest(737372392);
        NaturalNumber testExpected = this.constructorRef(737372392);

        assertEquals(testExpected.isZero(), test.isZero());
    }

    /**
     * Test isZero constructor with empty string.
     */
    @Test
    public void isZeroString() {
        NaturalNumber test = this.constructorTest("0");
        NaturalNumber testExpected = this.constructorRef("0");

        assertEquals(testExpected.isZero(), test.isZero());
    }

    /**
     * Test isZero constructor with string 2.
     */
    @Test
    public void isZeroString2() {
        NaturalNumber test = this.constructorTest("2");
        NaturalNumber testExpected = this.constructorRef("2");

        assertEquals(testExpected.isZero(), test.isZero());
    }

    /**
     * Test isZero constructor to see if it verifies 0.
     */
    @Test
    public void isZeroNaturalNumber() {
        NaturalNumber test = this.constructorTest();
        NaturalNumber testExpected = this.constructorRef();

        assertEquals(testExpected.isZero(), test.isZero());
    }

    /**
     * Test isZero constructor to see if it verifies the max int.
     */
    @Test
    public void isZeroNaturalNumber2() {
        NaturalNumber test = this.constructorTest(Integer.MAX_VALUE);
        NaturalNumber testExpected = this.constructorRef(Integer.MAX_VALUE);

        assertEquals(testExpected.isZero(), test.isZero());
    }

    /**
     * Test isZero constructor to see if it verifies a large int.
     */
    @Test
    public void isZeroNaturalNumber3() {
        NaturalNumber test = this.constructorTest(793929293);
        NaturalNumber testExpected = this.constructorRef(793929293);

        assertEquals(testExpected.isZero(), test.isZero());
    }

    /**
     * Test multiplyBy10 constructor with string of zero.
     */
    @Test
    public void multiplyBy10String() {
        NaturalNumber test = this.constructorTest("0");
        NaturalNumber testExpected = this.constructorRef("0");
        testExpected.multiplyBy10(0);
        test.multiplyBy10(0);
        assertEquals(testExpected, test);
    }

    /**
     * Test multiplyBy10 constructor with large string.
     */
    @Test
    public void multiplyBy10String2() {
        NaturalNumber test = this.constructorTest("8928983983");
        NaturalNumber testExpected = this.constructorRef("8928983983");
        testExpected.multiplyBy10(4);
        test.multiplyBy10(4);
        assertEquals(testExpected, test);
    }

    /**
     * Test multiplyBy10 constructor with 0.
     */
    @Test
    public void multiplyBy10Int() {
        NaturalNumber test = this.constructorTest(0);
        NaturalNumber testExpected = this.constructorRef(0);
        testExpected.multiplyBy10(3);
        test.multiplyBy10(3);
        assertEquals(testExpected, test);
    }

    /**
     * Test multiplyBy10 constructor with large ints.
     */
    @Test
    public void multiplyBy10Int2() {
        NaturalNumber test = this.constructorTest(337);
        NaturalNumber testExpected = this.constructorRef(337);
        testExpected.multiplyBy10(4);
        test.multiplyBy10(4);
        assertEquals(testExpected, test);
    }

    /**
     * Test multiplyBy10 constructor with large ints.
     */
    @Test
    public void multiplyBy10Int3() {
        NaturalNumber test = this.constructorTest(373393);
        NaturalNumber testExpected = this.constructorRef(373393);
        testExpected.multiplyBy10(5);
        test.multiplyBy10(5);
        assertEquals(testExpected, test);
    }

    /**
     * Test multiplyBy10 with zero.
     */
    @Test
    public void multiplyBy10NaturalNumber() {
        NaturalNumber test = this.constructorTest(new NaturalNumber3());
        NaturalNumber testExpected = this.constructorRef(new NaturalNumber3());
        testExpected.multiplyBy10(4);
        test.multiplyBy10(4);
        assertEquals(testExpected, test);
    }

    /**
     * Test multiplyBy10 constructor with int max.
     */
    @Test
    public void multiplyBy10NaturalNumber2() {
        NaturalNumber test = this
                .constructorTest(new NaturalNumber3(Integer.MAX_VALUE));
        NaturalNumber testExpected = this
                .constructorRef(new NaturalNumber3(Integer.MAX_VALUE));
        testExpected.multiplyBy10(5);
        test.multiplyBy10(5);
        assertEquals(testExpected, test);
    }

    /**
     * Test multiplyBy10 constructor with a large int.
     */
    @Test
    public void multiplyBy10NaturalNumber3() {
        NaturalNumber test = this
                .constructorTest(new NaturalNumber3(827929797));
        NaturalNumber testExpected = this
                .constructorRef(new NaturalNumber3(827929797));
        testExpected.multiplyBy10(7);
        test.multiplyBy10(7);
        assertEquals(testExpected, test);
    }

    /**
     * Test divideBy10 constructor with 0.
     */
    public void divideBy10Int() {
        NaturalNumber test = this.constructorTest(0);
        NaturalNumber testExpected = this.constructorRef(0);
        int nums = test.divideBy10();
        int numsExpected = testExpected.divideBy10();
        assertEquals(testExpected, test);
        assertEquals(numsExpected, nums);
    }

    /**
     * Test divideBy10 constructor with int max.
     */
    public void divideBy10Int2() {
        NaturalNumber test = this.constructorTest(Integer.MAX_VALUE);
        NaturalNumber testExpected = this.constructorRef(Integer.MAX_VALUE);
        int nums = test.divideBy10();
        int numsExpected = testExpected.divideBy10();
        assertEquals(testExpected, test);
        assertEquals(numsExpected, nums);
    }

    /**
     * Test divideBy10 constructor with a large int.
     */
    public void divideBy10Int3() {
        NaturalNumber test = this.constructorTest(828729723);
        NaturalNumber testExpected = this.constructorRef(828729723);
        int nums = test.divideBy10();
        int numsExpected = testExpected.divideBy10();
        assertEquals(testExpected, test);
        assertEquals(numsExpected, nums);
    }

    /**
     * Test divideBy10 constructor with an empty string.
     */
    public void divideBy10String() {
        NaturalNumber test = this.constructorTest("");
        NaturalNumber testExpected = this.constructorRef("");
        int nums = test.divideBy10();
        int numsExpected = testExpected.divideBy10();
        assertEquals(testExpected, test);
        assertEquals(numsExpected, nums);
    }

    /**
     * Test divideBy10 constructor with a large string.
     */
    public void divideBy10String2() {
        NaturalNumber test = this.constructorTest("792793091792922");
        NaturalNumber testExpected = this.constructorRef("792793091792922");
        int nums = test.divideBy10();
        int numsExpected = testExpected.divideBy10();
        assertEquals(testExpected, test);
        assertEquals(numsExpected, nums);
    }

    /**
     * Test divideBy10 constructor with zero.
     */
    public void divideBy10NaturalNumber() {
        NaturalNumber test = this.constructorTest(new NaturalNumber3());
        NaturalNumber testExpected = this.constructorRef(new NaturalNumber3());
        int nums = test.divideBy10();
        int numsExpexted = testExpected.divideBy10();
        assertEquals(testExpected, test);
        assertEquals(numsExpexted, nums);
    }

    /**
     * Test divideBy10 constructor with int max.
     */
    public void divideBy10NaturalNumber2() {
        NaturalNumber test = this
                .constructorTest(new NaturalNumber3(Integer.MAX_VALUE));
        NaturalNumber testExpected = this
                .constructorRef(new NaturalNumber3(Integer.MAX_VALUE));
        int nums = test.divideBy10();
        int numsExpexted = testExpected.divideBy10();
        assertEquals(testExpected, test);
        assertEquals(numsExpexted, nums);
    }

    /**
     * Test divideBy10 constructor with a large int.
     */
    public void divideBy10NaturalNumber3() {
        NaturalNumber test = this.constructorTest(new NaturalNumber3(92898220));
        NaturalNumber testExpected = this
                .constructorRef(new NaturalNumber3(92898220));
        int nums = test.divideBy10();
        int numsExpexted = testExpected.divideBy10();
        assertEquals(testExpected, test);
        assertEquals(numsExpexted, nums);
    }
}
