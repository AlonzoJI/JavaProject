import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Roble Gure
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsRef = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    // TODO - add test cases for constructor, add, remove, removeAny, contains, and size

    /*
     * Test for constructor
     */
    @Test
    public void constructorTest1() {
        Set<String> test = this.constructorTest();
        Set<String> ref = this.constructorRef();
        assertEquals(test, ref);
    }

    /*
     * Test for add
     */
    @Test
    public void addTest1() {
        Set<String> test = this.createFromArgsTest();
        Set<String> ref = this.createFromArgsRef("");
        test.add("");
        assertEquals(test, ref);
    }

    /*
     * Test for add
     */
    @Test
    public void addTest2() {
        Set<String> test = this.createFromArgsTest();
        Set<String> ref = this.createFromArgsRef("a");
        test.add("a");
        assertEquals(test, ref);
    }

    /*
     * Test for add
     */
    @Test
    public void addTest3() {
        Set<String> test = this.createFromArgsTest();
        Set<String> ref = this.createFromArgsTest("@#$%&");
        test.add("@#$%&");
        assertEquals(test, ref);
    }

    /*
     * Test for add
     */
    @Test
    public void addTest4() {
        Set<String> test = this.createFromArgsTest("a", "b");
        Set<String> ref = this.createFromArgsTest("a", "b", "c");
        test.add("c");
        assertEquals(test, ref);
    }

    /*
     * Test for remove
     */
    @Test
    public void removeTest1() {
        Set<String> test = this.createFromArgsTest();
        Set<String> ref = this.createFromArgsTest();
        assertEquals(test, ref);
    }

    /*
     * Test for remove
     */
    @Test
    public void removeTest2() {
        Set<String> test = this.createFromArgsTest("a");
        Set<String> ref = this.createFromArgsTest();
        test.remove("a");
        assertEquals(test, ref);
    }

    /*
     * Test for remove
     */
    @Test
    public void removeTest3() {
        Set<String> test = this.createFromArgsTest("a", "b", "c");
        Set<String> ref = this.createFromArgsTest("b", "c");
        test.remove("a");
        assertEquals(test, ref);
    }

    /*
     * Test for remove
     */
    @Test
    public void removeTest4() {
        Set<String> test = this.createFromArgsTest("a", "b");
        Set<String> ref = this.createFromArgsTest("b");
        test.remove("a");
        assertEquals(test, ref);
    }

    /*
     * Test for removeAny
     */
    @Test
    public void removeAnyTest1() {
        Set<String> test = this.createFromArgsTest("a", "b", "c");
        Set<String> ref = this.createFromArgsRef("a", "b", "c");
        String temp = test.removeAny();
        assertEquals(true, ref.contains(temp));
        ref.remove(temp);
        assertEquals(test, ref);
    }

    /*
     * Test for removeAny
     */
    @Test
    public void removeAnyTest2() {
        Set<String> test = this.createFromArgsTest("a");
        Set<String> ref = this.createFromArgsRef("a");
        String temp = test.removeAny();
        assertEquals(true, ref.contains(temp));
        ref.remove(temp);
        assertEquals(test, ref);
    }

    /*
     * Test for removeAny
     */
    @Test
    public void removeAnyTest3() {
        Set<String> test = this.createFromArgsTest("a", "b", "c", "d", "e", "f",
                "g", "h", "i", "j");
        Set<String> ref = this.createFromArgsRef("a", "b", "c", "d", "e", "f",
                "g", "h", "i", "j");
        String temp = test.removeAny();
        assertEquals(true, ref.contains(temp));
        ref.remove(temp);
        assertEquals(test, ref);
    }

    /*
     * Test for contains
     */
    @Test
    public void containsTest1() {
        Set<String> test = this.createFromArgsTest();
        Set<String> ref = this.createFromArgsRef();
        String temp = "a";
        assertEquals(test.contains(temp), ref.contains(temp));
        assertEquals(test, ref);
    }

    /*
     * Test for contains
     */
    @Test
    public void containsTest2() {
        Set<String> test = this.createFromArgsTest("a");
        Set<String> ref = this.createFromArgsRef("a");
        assertEquals(test.contains("a"), ref.contains("a"));
        assertEquals(test, ref);
    }

    /*
     * Test for contains
     */
    @Test
    public void containsTest3() {
        Set<String> test = this.createFromArgsTest("a", "b", "c");
        Set<String> ref = this.createFromArgsRef("a", "b", "c");
        assertEquals(test.contains("a"), ref.contains("a"));
        assertEquals(test, ref);
    }

    /*
     * Test for contains
     */
    @Test
    public void containsTest4() {
        Set<String> test = this.createFromArgsTest("a", "b", "c");
        Set<String> ref = this.createFromArgsRef("a", "b", "c");
        assertEquals(test.contains("d"), ref.contains("d"));
        assertEquals(test, ref);
    }

    /*
     * Test for size
     */
    @Test
    public void sizeTest1() {
        Set<String> test = this.createFromArgsTest();
        Set<String> ref = this.createFromArgsRef();
        assertEquals(test.size(), ref.size());
    }

    /*
     * Test for size
     */
    @Test
    public void sizeTest2() {
        Set<String> test = this.createFromArgsTest("a");
        Set<String> ref = this.createFromArgsRef("a");
        assertEquals(test.size(), ref.size());
    }

    /*
     * Test for size
     */
    @Test
    public void sizeTest3() {
        Set<String> test = this.createFromArgsTest("a", "b", "c");
        Set<String> ref = this.createFromArgsRef("a", "b", "c");
        assertEquals(test.size(), ref.size());
    }

}
