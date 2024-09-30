import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Comparator;

import org.junit.Test;

import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;

/**
 * Test class for {@link Glossary}. Tests the various functionalities of the
 * Glossary class, ensuring that all major methods operate correctly under
 * expected conditions and edge cases.
 */
public class GlossaryTest {

    /**
     * A comparator for strings using natural ordering. This comparator is used
     * to sort queues of strings in natural alphabetical order throughout the
     * tests.
     */
    private static final Comparator<String> STRING_COMPARATOR = Comparator
            .naturalOrder();

    /**
     * Tests the generation and addition of elements from a string with repeated
     * characters. Ensures that each unique character is added exactly once.
     */
    @Test
    public void testGenerateAndAddElementsNormalString() {
        Set<Character> charSet = new Set1L<>();
        final int three = 3;
        Glossary.generateAndAddElements("banana", charSet);
        assertEquals("Set size should match number of unique characters.",
                three, charSet.size());
        assertTrue("Set should contain 'b'.", charSet.contains('b'));
        assertTrue("Set should contain 'a'.", charSet.contains('a'));
        assertTrue("Set should contain 'n'.", charSet.contains('n'));
    }

    /**
     * Tests the generation and addition of elements from an empty string.
     * Ensures that the set remains empty.
     */
    @Test
    public void testGenerateAndAddElementsEmptyString() {
        Set<Character> charSet = new Set1L<>();
        Glossary.generateAndAddElements("", charSet);
        assertEquals("Set should be empty for empty string input.", 0,
                charSet.size());
    }

    /**
     * Tests the generation and addition of elements from a string where all
     * characters are unique. Verifies that the set size matches the string
     * length and contains all characters.
     */
    @Test
    public void testGenerateAndAddElementsAllUnique() {
        Set<Character> charSet = new Set1L<>();
        final int four = 4;
        Glossary.generateAndAddElements("abcd", charSet);
        assertEquals("Set size should be equal to string length.", four,
                charSet.size());
        assertTrue("Set should contain 'a'.", charSet.contains('a'));
        assertTrue("Set should contain 'b'.", charSet.contains('b'));
        assertTrue("Set should contain 'c'.", charSet.contains('c'));
        assertTrue("Set should contain 'd'.", charSet.contains('d'));
    }

    /**
     * Tests the generation and addition of elements from a string that includes
     * special characters. Verifies that the set accurately reflects the unique
     * characters in the string, including spaces and symbols.
     */
    @Test
    public void testGenerateAndAddElementsSpecialCharacters() {
        Set<Character> charSet = new Set1L<>();
        final int six = 6;
        Glossary.generateAndAddElements("a! b@c", charSet);
        assertEquals(
                "Set size should include unique special characters and spaces.",
                six, charSet.size());
        assertTrue("Set should contain ' '.", charSet.contains(' '));
        assertTrue("Set should contain '!'.", charSet.contains('!'));
        assertTrue("Set should contain '@'.", charSet.contains('@'));
        assertTrue("Set should contain 'c'.", charSet.contains('c'));
    }

    /**
     * Tests the retrieval of the first word from a string using defined
     * separators at the start of the text. Verifies that the method accurately
     * identifies the first word.
     */
    @Test
    public void testNextWordAtStart() {
        Set<Character> separators = new Set1L<>();
        separators.add(' ');
        separators.add(',');
        String text = "apple, banana, cherry";
        String result = Glossary.nextWordOrSeparator(text, 0, separators);
        assertEquals("The first word should be 'apple'", "apple", result);
    }

    /**
     * Tests the identification of a word starting from the middle of a string.
     * Verifies that the method correctly identifies words separated by
     * specified characters.
     */
    @Test
    public void testWordInMiddle() {
        Set<Character> separators = new Set1L<>();
        separators.add(' ');
        separators.add(',');
        String text = "apple, banana, cherry";
        final int seven = 7;
        String result = Glossary.nextWordOrSeparator(text, seven, separators);
        assertEquals("The word starting at position 7 should be 'banana'",
                "banana", result);
    }

    /**
     * Tests the behavior when attempting to parse an empty string with
     * specified separators. Expected to trigger an assertion error due to
     * precondition violations.
     */
    @Test(expected = AssertionError.class)
    public void testEmptyString() {
        Set<Character> separators = new Set1L<>();
        separators.add(' ');
        separators.add(',');
        String text = "";
        Glossary.nextWordOrSeparator(text, 0, separators);
    }

    /**
     * Tests sorting behavior on an empty queue to ensure stability and that no
     * modifications occur.
     */
    @Test
    public void testSortQueueWithEmptyQueue() {
        Queue<String> queue = new Queue1L<>();
        Glossary.sortQueue(queue, STRING_COMPARATOR);
        assertTrue("Queue should remain empty after sorting.",
                queue.length() == 0);
    }

}
