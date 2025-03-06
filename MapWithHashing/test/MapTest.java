import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;
import components.map.Map.Pair;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Jared Alonso
 *
 */
public abstract class MapTest {

    /**
     * Invokes the appropriate {@code Map} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new map
     * @ensures constructorTest = {}
     */
    protected abstract Map<String, String> constructorTest();

    /**
     * Invokes the appropriate {@code Map} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new map
     * @ensures constructorRef = {}
     */
    protected abstract Map<String, String> constructorRef();

    /**
     * Creates and returns a {@code Map<String, String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsTest = [pairs in args]
     */
    private Map<String, String> createFromArgsTest(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(
                    args[i]) : "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     * Creates and returns a {@code Map<String, String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsRef = [pairs in args]
     */
    private Map<String, String> createFromArgsRef(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorRef();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(
                    args[i]) : "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /*
     * Test no argument constructor
     */
    @Test
    public void constructorTest1() {
        Map<String, String> test = this.constructorTest();
        Map<String, String> ref = this.constructorRef();
        assertEquals(ref, test);
    }

    /*
     * Test add
     */
    @Test
    public void addTest1() {
        Map<String, String> test = this.createFromArgsTest();
        Map<String, String> ref = this.createFromArgsRef("3", "4");
        test.add("3", "4");
        assertEquals(ref, test);
    }

    /*
     * Test add
     */
    @Test
    public void addTest2() {
        Map<String, String> test = this.createFromArgsTest("7", "8");
        Map<String, String> ref = this.createFromArgsRef("5", "6", "7", "8");
        test.add("5", "6");
        assertEquals(ref, test);
    }

    /*
     * Test remove
     */
    @Test
    public void removeTest1() {
        Map<String, String> test = this.createFromArgsTest("6", "7");
        Map<String, String> ref = this.createFromArgsRef("6", "7");
        test.remove("6");
        ref.remove("6");
        assertEquals(ref, test);
    }

    /*
     * Test remove
     */
    @Test
    public void removeTest2() {
        Map<String, String> test = this.createFromArgsTest("7", "8", "9", "10");
        Map<String, String> ref = this.createFromArgsRef("7", "8", "9", "10");
        test.remove("7");
        ref.remove("7");
        assertEquals(ref, test);
    }

    /*
     * Test removeAny
     */
    @Test
    public void testRemoveAny1() {
        Map<String, String> test = this.createFromArgsTest("7", "8", "9", "10");
        Map<String, String> ref = this.createFromArgsRef("9", "10");
        String key = "7";
        String value = "8";
        Pair<String, String> removedPair = test.removeAny();
        assertEquals(removedPair.key(), key);
        assertEquals(removedPair.value(), value);
        assertEquals(ref, test);
    }

    /*
     * Test removeAny
     */
    @Test
    public void testRemoveAny2() {
        Map<String, String> test = this.createFromArgsTest("7", "8");
        Map<String, String> ref = this.createFromArgsRef();
        String key = "7";
        String value = "8";
        Pair<String, String> removedPair = test.removeAny();
        assertEquals(key, removedPair.key());
        assertEquals(value, removedPair.value());
        assertEquals(ref, test);
    }

    /*
     * Test value
     */
    @Test
    public void testValue1() {
        Map<String, String> test = this.createFromArgsTest("7", "8", "9", "10");
        Map<String, String> ref = this.createFromArgsRef("7", "8", "9", "10");
        String fromTest = test.value("7");
        String fromRef = ref.value("7");
        assertEquals(ref, test);
        assertEquals(fromRef, fromTest);
    }

    /*
     * Test value
     */
    @Test
    public void testValue2() {
        Map<String, String> test = this.createFromArgsTest("7", "8", "9", "10");
        Map<String, String> ref = this.createFromArgsRef("7", "8", "9", "10");
        String fromTest = test.value("7");
        String fromRef = ref.value("7");
        String fromTest2 = test.value("9");
        String fromRef2 = ref.value("9");
        assertEquals(ref, test);
        assertEquals(fromRef, fromTest);
        assertEquals(fromRef2, fromTest2);
    }

    /*
     * Test hasKey
     */
    @Test
    public void testhasKey1() {
        Map<String, String> test = this.createFromArgsTest("7", "8", "9", "10");
        Map<String, String> ref = this.createFromArgsRef("7", "8", "9", "10");
        boolean hasKey = test.hasKey("9");
        boolean expected = true;
        assertEquals(ref, test);
        assertEquals(expected, hasKey);
    }

    /*
     * Test hasKey
     */
    @Test
    public void testhasKey2() {
        Map<String, String> test = this.createFromArgsTest("7", "8", "9", "10");
        Map<String, String> ref = this.createFromArgsRef("7", "8", "9", "10");
        boolean hasKey1 = test.hasKey("7");
        boolean hasKey2 = test.hasKey("8");
        boolean hasKey3 = test.hasKey("9");
        boolean expected1 = true;
        boolean expected2 = false;
        boolean expected3 = true;
        assertEquals(ref, test);
        assertEquals(expected1, hasKey1);
        assertEquals(expected2, hasKey2);
        assertEquals(expected3, hasKey3);
    }

    /*
     * Test size
     */
    @Test
    public void testsize1() {
        Map<String, String> test = this.createFromArgsTest("7", "8", "9", "10");
        Map<String, String> ref = this.createFromArgsRef("7", "8", "9", "10");
        assertEquals(ref.size(), test.size());
    }

    /*
     * Test size
     */
    @Test
    public void testsize2() {
        Map<String, String> test = this.createFromArgsTest();
        Map<String, String> ref = this.createFromArgsRef();
        assertEquals(ref.size(), test.size());
    }
}
