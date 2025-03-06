import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachine2;

/**
 * Generates a tag cloud from a text file based on word frequencies.
 *
 * @author Jared Alonzo and Roble Gure
 */
public final class TagCloudGenerator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private TagCloudGenerator() {
    }

    /**
     * Maximum font value for tags in tag cloud.
     */
    private static final int FONT_MAX = 48;

    /**
     * Minimum font value for tags in tag cloud.
     */
    private static final int FONT_MIN = 11;

    /**
     * Outputs the opening tags in the generated HTML file.
     *
     * @param title
     *            the title of the page
     * @param out
     *            the output stream
     * @updates out.content
     * @requires out.isOpen
     * @ensures out.content = #out.content * [the HTML opening tags]
     */
    private static void outputHeader(String title, SimpleWriter out) {
        assert out != null : "Violation of: out is not null";
        assert out.isOpen() : "Violation of: out.isOpen";

        out.println("<html>\n\t<head>\n\t<title>" + title
                + "</title>\n<link href=\"http://web.cse.ohio-state.edu/"
                + "software/2231/web-sw2/assignments/projects/tag-cloud-"
                + "generator/data/tagcloud.css\" rel=\"stylesheet\" "
                + "type=\"text/css\">\n<link href=\"tagcloud.css\" "
                + "rel=\"stylesheet\" type=\"text/css\">\n"
                + "</head>\n<body>\n");
    }

    /**
     * Acquires a valid number of words for the tag cloud.
     *
     * @param words
     *            the map of unique words and their counts
     * @param out
     *            the output stream for error messages
     * @param in
     *            the input stream for user inputs
     * @return the valid number of words for the tag cloud
     */
    private static int getValidInput(Map<String, Integer> words,
            SimpleWriter out, SimpleReader in) {
        out.print("Input number of words to include in tag cloud: ");
        int n = in.nextInteger();
        while (n < 0 || n > words.size()) {
            if (n < 0) {
                out.println("Must be a positive integer.");
            } else {
                out.println(
                        "Must not exceed the number of unique words in the input file.");
            }
            out.print("Input number of words to include in tag cloud: ");
            n = in.nextInteger();
        }
        return n;
    }

    /**
     * Comparator to sort Strings in alphabetical order.
     */
    private static class StringLT implements Comparator<String> {
        @Override
        public int compare(String a, String b) {
            return a.compareToIgnoreCase(b);
        }
    }

    /**
     * Comparator to sort Map.Pairs by integer values in descending order.
     */
    private static class CountLT
            implements Comparator<Map.Pair<String, Integer>> {
        @Override
        public int compare(Map.Pair<String, Integer> a,
                Map.Pair<String, Integer> b) {
            return Integer.compare(b.value(), a.value());
        }
    }

    /**
     * Returns the first word or separator string found in text starting at the
     * given position.
     *
     * @param text
     *            the string to parse
     * @param position
     *            the starting position in the string
     * @param separatorSet
     *            the set of separator characters
     * @return the first word or separator string found
     * @requires 0 <= position < |text|
     * @ensures nextWordOrSeparator is the next word or separator string in text
     */
    private static String nextWordOrSeparator(String text, int position,
            Set<Character> separatorSet) {
        StringBuilder word = new StringBuilder();
        boolean isSeparator = separatorSet.contains(text.charAt(position));

        while (position < text.length() && separatorSet
                .contains(text.charAt(position)) == isSeparator) {
            word.append(text.charAt(position));
            position++;
        }

        return word.toString();
    }

    /**
     * Constructs a map of word-count pairs from the input text file.
     *
     * @param input
     *            the input stream to read from
     * @return a map of unique words and their counts
     * @requires input.isOpen
     * @ensures input.isOpen and the map contains word-count pairs from the
     *          input
     */
    private static Map<String, Integer> wordCountMap(SimpleReader input) {
        assert input.isOpen() : "Violation of: input.isOpen";

        Map<String, Integer> countMap = new Map1L<>();
        Set<Character> separators = new Set1L<>();
        String separatorChars = " \t\n\r,-.!?[]\";:/()_*";
        for (char c : separatorChars.toCharArray()) {
            separators.add(c);
        }

        while (!input.atEOS()) {
            String line = input.nextLine();
            int position = 0;
            while (position < line.length()) {
                String term = nextWordOrSeparator(line, position, separators);
                if (!term.trim().isEmpty() && !countMap.hasKey(term)) {
                    countMap.add(term, 1);
                } else if (!term.trim().isEmpty()) {
                    countMap.replaceValue(term, countMap.value(term) + 1);
                }
                position += term.length();
            }
        }

        return countMap;
    }

    /**
     * Outputs an HTML formatted tag for a word in the tag cloud.
     *
     * @param out
     *            the output stream for HTML
     * @param fSize
     *            the font size for the word
     * @param count
     *            the word's frequency
     * @param word
     *            the word itself
     */
    private static void printTagInCloud(SimpleWriter out, int fSize, int count,
            String word) {
        out.println("<span style=\"cursor:default\" class=\"f" + fSize
                + "\" title=\"count: " + count + "\">" + word + "</span>");
    }

    /**
     * Calculates an appropriate font size for a word based on its frequency.
     *
     * @param max
     *            the maximum word frequency
     * @param min
     *            the minimum word frequency
     * @param count
     *            the word's frequency
     * @return the calculated font size
     */
    private static int fontSize(int max, int min, float count) {
        float range = FONT_MAX - FONT_MIN;
        if (max > min) {
            return (int) (range * (count - min) / (max - min) + FONT_MIN);
        } else {
            return FONT_MAX;
        }
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.print("Input name of text file to count words from: ");
        String inputFile = in.nextLine();
        SimpleReader inFile = new SimpleReader1L(inputFile);

        out.print("Input name for the HTML output file: ");
        String outputFile = in.nextLine();
        SimpleWriter htmlOut = new SimpleWriter1L(outputFile);

        Map<String, Integer> data = wordCountMap(inFile);
        int n = getValidInput(data, out, in);

        if (n > 0) {
            Queue<String> ordered = new Queue1L<>();
            SortingMachine<Map.Pair<String, Integer>> cSort = new SortingMachine2<>(
                    new CountLT());

            for (Map.Pair<String, Integer> pair : data) {
                cSort.add(pair);
            }
            cSort.changeToExtractionMode();

            for (int i = 0; i < n; i++) {
                ordered.enqueue(cSort.removeFirst().key());
            }

            int maxCount = data.value(ordered.front());
            int minCount = maxCount;
            for (String word : ordered) {
                minCount = data.value(word);
            }

            SortingMachine<String> sSort = new SortingMachine2<>(
                    new StringLT());
            while (ordered.length() > 0) {
                sSort.add(ordered.dequeue());
            }
            sSort.changeToExtractionMode();

            outputHeader("Top " + n + " words in " + inputFile, htmlOut);

            while (sSort.size() > 0) {
                String word = sSort.removeFirst();
                int size = fontSize(maxCount, minCount, data.value(word));
                printTagInCloud(htmlOut, size, data.value(word), word);
            }
        } else {
            outputHeader("Top 0 words in " + inputFile, htmlOut);
        }

        htmlOut.println("</body>\n</html>");
        in.close();
        out.close();
        inFile.close();
        htmlOut.close();
    }
}
