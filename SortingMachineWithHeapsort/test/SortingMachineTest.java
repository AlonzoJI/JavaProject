import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 *
 * @author Roble Gure
 *
 */
public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * implementation under test and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorTest = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorTest(
            Comparator<String> order);

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * reference implementation and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorRef = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorRef(
            Comparator<String> order);

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsTest(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorTest(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the reference
     * implementation type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsRef = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsRef(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorRef(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * Comparator<String> implementation to be used in all test cases. Compare
     * {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    }

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final StringLT ORDER = new StringLT();

    /*
     * Sample test cases.
     */

    @Test
    public final void testConstructor() {
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green");
        m.add("green");
        assertEquals(mExpected, m);
    }

    // TODO - add test cases for add, changeToExtractionMode, removeFirst,
    // isInInsertionMode, order, and size

    /*
     * Test for add
     */
    @Test
    public void addTest1() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "apple");
        m.add("apple");
        assertEquals(mExpected, m);
    }

    /*
     * Test for add
     */
    @Test
    public void addTest2() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "apple");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "apple", "banana");
        m.add("banana");
        assertEquals(mExpected, m);
    }

    /*
     * Test for add
     */
    @Test
    public void addTest3() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "apple",
                "banana");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "apple", "banana", "mango");
        m.add("mango");
        assertEquals(mExpected, m);
    }

    /*
     * Test for change to extraction mode
     */
    @Test
    public void changeToExtractionModeTest1() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        m.changeToExtractionMode();
        assertEquals(mExpected, m);
    }

    /*
     * Test for change to extraction mode
     */
    @Test
    public void changeToExtractionModeTest2() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "apple",
                "banana");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "apple", "banana");
        m.changeToExtractionMode();
        assertEquals(mExpected, m);
    }

    /*
     * Test for remove first
     */
    @Test
    public void removeFirstTest1() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "apple");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        String s = m.removeFirst();
        assertEquals("apple", s);
        assertEquals(mExpected, m);
    }

    /*
     * Test for remove first
     */
    @Test
    public void removeFirstTest2() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "apple", "banana");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "banana");
        String s = m.removeFirst();
        assertEquals("apple", s);
        assertEquals(mExpected, m);
    }

    /*
     * Test for is in insertion mode
     */
    @Test
    public void isInInsertionModeTest1() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        assertEquals(mExpected, m);
        assertEquals(m.isInInsertionMode(), mExpected.isInInsertionMode());
    }

    /*
     * Test for is in insertion mode
     */
    @Test
    public void isInInsertionModeTest2() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "apple");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "apple");
        assertEquals(mExpected, m);
        assertEquals(m.isInInsertionMode(), mExpected.isInInsertionMode());
    }

    /*
     * Test for is in insertion mode
     */
    @Test
    public void isInInsertionModeTest3() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "apple");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "apple");
        assertEquals(mExpected, m);
        assertEquals(m.isInInsertionMode(), mExpected.isInInsertionMode());
    }

    /*
     * Test for order
     */
    @Test
    public void orderTest1() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        assertEquals(mExpected, m);
        assertEquals(m.order(), mExpected.order());
    }

    /*
     * Test for order
     */
    @Test
    public void orderTest2() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "apple");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "apple");
        assertEquals(mExpected, m);
        assertEquals(m.order(), mExpected.order());
    }

    /*
     * Test for order
     */
    @Test
    public void orderTest3() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "apple", "banana");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "apple", "banana");
        assertEquals(mExpected, m);
        assertEquals(m.order(), mExpected.order());
    }

    /*
     * Test for size
     */
    @Test
    public void sizeTest1() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        assertEquals(mExpected, m);
        assertEquals(m.size(), mExpected.size());
    }

    /*
     * Test for size
     */
    @Test
    public void sizeTest2() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "apple");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "apple");
        assertEquals(mExpected, m);
        assertEquals(m.size(), mExpected.size());
    }

    /*
     * Test for size
     */
    @Test
    public void sizeTest3() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "apple", "banana");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "apple", "banana");
        assertEquals(mExpected, m);
        assertEquals(m.size(), mExpected.size());
    }

}
