import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.TreeSet;

/**
 * Generates a tag cloud from a text file based on word frequencies.
 *
 * @author Jared Alonzo and Roble Gure
 */
public final class TagCloud {

    /**
     * Maximum font value for tags in the tag cloud.
     */
    private static final int FONT_MAX = 48;

    /**
     * Minimum font value for tags in the tag cloud.
     */
    private static final int FONT_MIN = 11;

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private TagCloud() {
    }

    /**
     * Writes the opening tags for the HTML file.
     *
     * @param title
     *            the title of the HTML page
     * @param out
     *            the writer for the output HTML file
     * @throws IOException
     *             if an error occurs while writing to the file
     */
    private static void outputHeader(String title, PrintWriter out)
            throws IOException {
        out.println("<html>");
        out.println("\t<head>");
        out.println("\t\t<title>" + title + "</title>");
        out.println(
                "\t\t<link href=\"tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");
        out.println("\t</head>");
        out.println("\t<body>");
    }

    /**
     * Prompts the user to specify a valid number of words for the tag cloud.
     *
     * @param words
     *            the map of word counts
     * @param in
     *            the input reader
     * @return a valid number of words for the tag cloud
     * @throws IOException
     *             if an error occurs while reading input
     */
    private static int getValidInput(Map<String, Integer> words,
            BufferedReader in) throws IOException {
        System.out.print(
                "Enter the number of words to include in the tag cloud: ");
        int n = Integer.parseInt(in.readLine());
        while (n < 0 || n > words.size()) {
            if (n < 0) {
                System.out.println("The number must be positive.");
            } else {
                System.out.println(
                        "The number must not exceed the unique word count.");
            }
            System.out.print(
                    "Enter the number of words to include in the tag cloud: ");
            n = Integer.parseInt(in.readLine());
        }
        return n;
    }

    /**
     * Comparator to sort words alphabetically (case insensitive).
     */
    private static class StringLT implements Comparator<String> {
        @Override
        public int compare(String a, String b) {
            return a.compareToIgnoreCase(b);
        }
    }

    /**
     * Comparator to sort word-frequency pairs by frequency in descending order.
     */
    private static class CountLT
            implements Comparator<Map.Entry<String, Integer>> {
        @Override
        public int compare(Map.Entry<String, Integer> a,
                Map.Entry<String, Integer> b) {
            return Integer.compare(b.getValue(), a.getValue());
        }
    }

    /**
     * Extracts the next word or separator substring from a given position in a
     * line.
     *
     * @param text
     *            the input line of text
     * @param position
     *            the starting position
     * @param separators
     *            the set of separator characters
     * @return the next word or separator substring
     */
    private static String nextWordOrSeparator(String text, int position,
            Set<Character> separators) {
        StringBuilder token = new StringBuilder();
        boolean isSeparator = separators.contains(text.charAt(position));
        while (position < text.length()
                && separators.contains(text.charAt(position)) == isSeparator) {
            token.append(text.charAt(position));
            position++;
        }
        return token.toString();
    }

    /**
     * Reads the input file and counts word frequencies.
     *
     * @param fileName
     *            the name of the input text file
     * @return a map of words and their respective counts
     * @throws IOException
     *             if an error occurs while reading the file
     */
    private static Map<String, Integer> wordCountMap(String fileName)
            throws IOException {
        Map<String, Integer> wordCounts = new HashMap<>();
        Set<Character> separators = new TreeSet<>();
        String separatorChars = " \t\n\r,-.!?[]\";:/()_*";
        for (char c : separatorChars.toCharArray()) {
            separators.add(c);
        }

        try (BufferedReader input = new BufferedReader(
                new FileReader(fileName))) {
            String line;
            while ((line = input.readLine()) != null) {
                int position = 0;
                while (position < line.length()) {
                    String token = nextWordOrSeparator(line, position,
                            separators);
                    if (!token.trim().isEmpty()
                            && !separators.contains(token.charAt(0))) {
                        wordCounts.put(token,
                                wordCounts.getOrDefault(token, 0) + 1);
                    }
                    position += token.length();
                }
            }
        }
        return wordCounts;
    }

    /**
     * Writes an HTML tag representing a word and its frequency.
     *
     * @param out
     *            the writer for the HTML file
     * @param fSize
     *            the font size for the word
     * @param count
     *            the frequency of the word
     * @param word
     *            the word to be displayed
     */
    private static void printTag(PrintWriter out, int fSize, int count,
            String word) {
        out.println("<span style=\"cursor:default\" class=\"f" + fSize
                + "\" title=\"count: " + count + "\">" + word + "</span>");
    }

    /**
     * Calculates an appropriate font size for a word based on its frequency.
     *
     * @param max
     *            the highest frequency in the dataset
     * @param min
     *            the lowest frequency in the dataset
     * @param count
     *            the frequency of the word
     * @return the calculated font size
     */
    private static int fontSize(int max, int min, int count) {
        if (max == min) {
            return FONT_MAX;
        }
        return FONT_MIN + (FONT_MAX - FONT_MIN) * (count - min) / (max - min);
    }

    /**
     * Main method to generate a tag cloud from an input file.
     *
     * @param args
     *            command-line arguments
     */
    public static void main(String[] args) {
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(System.in))) {
            System.out.print("Enter the input text file: ");
            String inputFile = in.readLine();

            System.out.print("Enter the output HTML file: ");
            String outputFile = in.readLine();

            Map<String, Integer> wordCounts = wordCountMap(inputFile);
            int n = getValidInput(wordCounts, in);

            try (PrintWriter htmlOut = new PrintWriter(
                    new BufferedWriter(new FileWriter(outputFile)))) {
                PriorityQueue<Map.Entry<String, Integer>> sortedWords = new PriorityQueue<>(
                        new CountLT());
                sortedWords.addAll(wordCounts.entrySet());

                int maxFreq = sortedWords.peek().getValue();
                int minFreq = maxFreq;

                for (Map.Entry<String, Integer> entry : sortedWords) {
                    minFreq = Math.min(minFreq, entry.getValue());
                }

                outputHeader("Top " + n + " Words", htmlOut);

                for (int i = 0; i < n && !sortedWords.isEmpty(); i++) {
                    Map.Entry<String, Integer> entry = sortedWords.poll();
                    int fontSize = fontSize(maxFreq, minFreq, entry.getValue());
                    printTag(htmlOut, fontSize, entry.getValue(),
                            entry.getKey());
                }

                htmlOut.println("</body>");
                htmlOut.println("</html>");
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
