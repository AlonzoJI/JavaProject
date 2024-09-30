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

/**
 * Glossary - Project 10.
 *
 * @author Jared Alonzo
 *
 */
public final class Glossary {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Glossary() {
    }

    /**
     * Removes and returns the minimum value from {@code q} according to the
     * ordering provided by the {@code compare} method from {@code order}.
     *
     * @param q
     *            the queue
     * @param order
     *            ordering by which to compare entries
     * @return the minimum value from {@code q}
     * @updates q
     * @requires <pre>
     * q /= empty_string  and
     *  [the relation computed by order.compare is a total preorder]
     * </pre>
     * @ensures <pre>
     * perms(q * <removeMin>, #q)  and
     *  for all x: string of character
     *      where (x is in entries (q))
     *    ([relation computed by order.compare method](removeMin, x))
     * </pre>
     */
    public static String removeMinimumValue(Queue<String> q,
            Comparator<String> order) {
        assert q != null : "Violation of: q is not null";
        assert order != null : "Violation of: order is not null";

        Queue<String> temp = q.newInstance();
        temp.transferFrom(q);
        String minVal = temp.front();

        while (temp.length() > 0) {
            String currentVal = temp.dequeue();
            if (order.compare(currentVal, minVal) < 0) {
                q.enqueue(minVal);
                minVal = currentVal;
            } else {
                q.enqueue(currentVal);
            }
        }
        return minVal;
    }

    /**
     * Finds, removes, and returns the minimum element from the queue according
     * to the given comparator.
     *
     * @param q
     *            The queue to search through.
     * @param order
     *            The comparator to determine the order of elements.
     * @return The minimum element found in the queue.
     */
    private static String findAndRemoveMin(Queue<String> q,
            Comparator<String> order) {
        String min = q.dequeue();
        Queue<String> temp = q.newInstance();

        while (q.length() > 0) {
            String current = q.dequeue();
            if (order.compare(current, min) < 0) {
                temp.enqueue(min);
                min = current;
            } else {
                temp.enqueue(current);
            }
        }

        q.transferFrom(temp);

        return min;
    }

    /**
     * Sorts {@code q} according to the ordering provided by the {@code compare}
     * method from {@code order}.
     *
     * @param q
     *            the queue
     * @param order
     *            ordering by which to sort
     * @updates q
     * @requires [the relation computed by order.compare is a total preorder]
     * @ensures q = [#q ordered by the relation computed by order.compare]
     */
    public static void sortQueue(Queue<String> q, Comparator<String> order) {
        Queue<String> tempQueue = q.newInstance();
        while (!(tempQueue.length() == 0)) {
            String min = findAndRemoveMin(q, order);
            tempQueue.enqueue(min);
        }
        q.transferFrom(tempQueue);
    }

    /**
     * Generates the set of characters in the given {@code String} into the
     * given {@code Set}.
     *
     * @param str
     *            the given {@code String}
     * @param charSet
     *            the {@code Set} to be replaced
     * @replaces charSet
     * @ensures charSet = entries(str)
     */
    public static void generateAndAddElements(String str,
            Set<Character> charSet) {
        charSet.clear();
        for (Character character : str.toCharArray()) {
            if (!charSet.contains(character)) {
                charSet.add(character);
            }
        }
    }

    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code separators}) or "separator string" (maximal length string of
     * characters in {@code separators}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @param separators
     *            the {@code Set} of separator characters
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires 0 <= position < |text|
     * @ensures <pre>
     * nextWordOrSeparator =
     *   text[position, position + |nextWordOrSeparator|)  and
     * if entries(text[position, position + 1)) intersection separators = {}
     * then
     *   entries(nextWordOrSeparator) intersection separators = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      intersection separators /= {})
     * else
     *   entries(nextWordOrSeparator) is subset of separators  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      is not subset of separators)
     * </pre>
     */
    public static String nextWordOrSeparator(String text, int position,
            Set<Character> separators) {
        assert text != null : "Violation of: text is not null";
        assert separators != null : "Violation of: separators is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position < text.length() : "Violation of: position < |text|";

        int endPosition = position;

        if (separators.contains(text.charAt(position))) {
            while (endPosition < text.length()
                    && separators.contains(text.charAt(endPosition))) {
                endPosition++;
            }
        } else {
            while (endPosition < text.length()
                    && !separators.contains(text.charAt(endPosition))) {
                endPosition++;
            }
        }

        return text.substring(position, endPosition);
    }

    /**
     * Takes the the term and a map containing all terms and definitions and the
     * output folder. Outputs an html file with the term and definition in the
     * output folder.
     *
     * @param term
     *            the term for which the page is being created
     * @param m
     *            the map containing all the terms and definitions
     * @param folder
     *            the output folder of all the html files
     * @updates out.content
     * @requires [term has no spaces] and out.is_open
     * @ensures <pre>
     * out.content = #out.content *
     *   [an HTML page with the term and definition]
     * </pre>
     */
    public static void generatePage(String term, Map<String, String> m,
            String folder) {
        SimpleWriter out = new SimpleWriter1L(folder + "//" + term + ".html");

        out.println("<html>\n<head>\n\t<title>" + term + "</title>\n</head>");

        out.println("<body>\n<h2><b><i><font color=\"red\">" + term
                + "</font></i></b></h2>\n");

        out.print("<blockquote>");

        int currentPosition = 0;
        String definition = m.value(term);
        Set<Character> separators = new Set1L<Character>();
        generateAndAddElements(" \t,.", separators);
        while (currentPosition < definition.length() - 1) {
            String token = nextWordOrSeparator(definition, currentPosition,
                    separators);
            if (m.hasKey(token)) {
                out.println("<a href=\"" + token + ".html\">" + token + "</a>");
            } else {
                out.println(token);
            }
            currentPosition += token.length();
        }

        out.print("</blockquote>\n");

        out.println("<hr />\r\n<p>Return to <a href=\"index.html\">index</a>."
                + "</p>\r\n</body>\r\n</html>");

        out.close();
    }

    /**
     * Reads terms and their definitions from a specified file. Each term is
     * read as a single line, followed by its definition, which can span
     * multiple lines until an empty line is encountered.
     *
     * @param fileName
     *            The path to the input file containing terms and definitions.
     * @return A map where each key is a term (String) and its value is the
     *         definition (String).
     */
    private static Map<String, String> readTermsAndDefinitions(
            String fileName) {
        Map<String, String> terms = new Map1L<>();
        SimpleReader fileReader = new SimpleReader1L(fileName);

        while (!fileReader.atEOS()) {
            String term = fileReader.nextLine().trim();
            StringBuilder definition = new StringBuilder();

            if (!fileReader.atEOS()) {
                String line = fileReader.nextLine();

                boolean continueReading = !line.isEmpty();

                while (continueReading) {
                    definition.append(line).append(' ');
                    if (fileReader.atEOS()) {
                        continueReading = false;
                    } else {
                        line = fileReader.nextLine();
                        continueReading = !line.isEmpty();
                    }
                }
            }

            terms.add(term, definition.toString().trim());
        }
        fileReader.close();
        return terms;
    }

    /**
     * Generates an index HTML page listing all terms in the glossary
     * alphabetically. Each term in the list is a hyperlink to a separate HTML
     * page with its definition.
     *
     * @param glossary
     *            A map containing terms as keys and their definitions as
     *            values.
     * @param folder
     *            The path to the output folder where the index.html will be
     *            saved. It assumes that the folder path ends with a slash.
     */
    private static void generateIndexPage(Map<String, String> glossary,
            String folder) {
        SimpleWriter out = new SimpleWriter1L(folder + "index.html");

        out.println("<!DOCTYPE html>");
        out.println("<h2>Glossary</h2>");
        out.println("<hr />");
        out.println("<h3>Index</h3>");
        out.println("<ul>");

        Queue<String> sortedTerms = new Queue1L<>();
        for (Map.Pair<String, String> term : glossary) {
            sortedTerms.enqueue(term.key());
        }
        sortedTerms.sort(String.CASE_INSENSITIVE_ORDER);

        while (sortedTerms.length() > 0) {
            String term = sortedTerms.dequeue();
            out.println(
                    "<li><a href=\"" + term + ".html\">" + term + "</a></li>");
        }

        out.println("</ul>");
        out.println("</body></html>");
        out.close();
    }

    /**
     * Generates individual HTML pages for each term in the glossary. On each
     * page, the term is displayed in red, boldface, italics at the top,
     * followed by its definition. If other terms from the glossary appear in
     * the definition, they are hyperlinked to their respective pages.
     *
     * @param glossary
     *            A map containing terms as keys and their definitions as
     *            values.
     * @param folder
     *            The path to the output folder where the term pages will be
     *            saved. It assumes that the folder path ends with a slash.
     */
    private static void generateTermPages(Map<String, String> glossary,
            String folder) {
        for (Map.Pair<String, String> entry : glossary) {
            SimpleWriter termPage = new SimpleWriter1L(
                    folder + entry.key() + ".html");
            termPage.println("<!DOCTYPE html>");
            termPage.println("<html><head><title>" + entry.key()
                    + "</title></head><body>");
            termPage.println(
                    "<h1 style=\"color: red; font-weight: bold; font-style: italic;\">"
                            + entry.key() + "</h1>");

            String definition = entry.value();

            for (Map.Pair<String, String> otherEntry : glossary) {
                String otherTerm = otherEntry.key();
                if (!otherTerm.equals(entry.key())) {
                    String link = "<a href=\"" + otherTerm + ".html\">"
                            + otherTerm + "</a>";
                    definition = definition
                            .replaceAll("\\b" + otherTerm + "\\b", link);
                }
            }

            termPage.println("<p>" + definition + "</p>");
            termPage.println("<hr />"); // Adding horizontal rule after the definition
            termPage.println(
                    "<p>Return to <a href=\"index.html\">Index</a>.</p>");
            termPage.println("</body></html>");
            termPage.close();
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

        out.print("Enter the input file name: ");
        String inputFile = in.nextLine();
        out.print("Enter the output folder name: ");
        String outputFolder = in.nextLine();

        if (!outputFolder.endsWith("/")) {
            outputFolder += "/";
        }

        Map<String, String> glossary = readTermsAndDefinitions(inputFile);
        generateIndexPage(glossary, outputFolder);
        generateTermPages(glossary, outputFolder);

        in.close();
        out.close();
    }

}
