import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

public class StringReassemblyTest {

    private static Set<String> createFromArgs(String... args) {
        Set<String> set = new Set1L<String>();
        for (String s : args) {
            set.add(s);
        }
        return set;
    }

    @Test
    public void testCombinationNormal() {
        String str1 = "Hello S";
        String str2 = "lo Solomon";
        final int overlap = 4;
        String expected = "Hello Solomon";

        String result = StringReassembly.combination(str1, str2, overlap);

        assertEquals(expected, result);
    }

    @Test
    public void testAddToSetWithSubstring() {
        Set<String> strSet = new Set1L<>();
        Set<String> expSet = new Set1L<>();

        strSet.add("Eclipse");
        expSet.add("Eclipse");
        String input = "Ec";

        StringReassembly.addToSetAvoidingSubstrings(strSet, input);
        assertEquals(expSet, strSet);
    }

    @Test
    public void testAddToSetWithSuperstring() {
        Set<String> strSet = new Set1L<>();
        Set<String> expSet = new Set1L<>();

        strSet.add("Ja");
        expSet.add("Jared");
        String input = "Jared";

        StringReassembly.addToSetAvoidingSubstrings(strSet, input);
        assertEquals(expSet, strSet);
    }

    @Test
    public void testAddToSetWithDuplicate() {
        Set<String> strSet = new Set1L<>();
        Set<String> expSet = new Set1L<>();

        strSet.add("SameWord");
        expSet.add("SameWord");
        String input = "SameWord";

        StringReassembly.addToSetAvoidingSubstrings(strSet, input);
        assertEquals(expSet, strSet);
    }

    @Test
    public void testPrintWithLineSeparators1() {
        SimpleWriter out = new SimpleWriter1L("PrintWithLineSeperator1.txt");
        String str = "Logan~MiddleName~Frank";
        StringReassembly.printWithLineSeparators(str, out);
        SimpleReader in = new SimpleReader1L("PrintWithLineSeperator1.txt");
        String str1exp = "Logan";
        String str2exp = "MiddleName";
        String str3exp = "Frank";

        assertEquals(str1exp, in.nextLine());
        assertEquals(str2exp, in.nextLine());
        assertEquals(str3exp, in.nextLine());

        in.close();
        out.close();
    }

    @Test
    public void testPrintWithLineSeparators2() {
        SimpleWriter out = new SimpleWriter1L("PrintWithLineSeperator2.txt");
        String str = "Jared~~Alonzo";
        StringReassembly.printWithLineSeparators(str, out);
        SimpleReader in = new SimpleReader1L("PrintWithLineSeperator2.txt");
        String str1exp = "Jared";
        String str2exp = "";
        String str3exp = "Alonzo";

        assertEquals(str1exp, in.nextLine());
        assertEquals(str2exp, in.nextLine());
        assertEquals(str3exp, in.nextLine());

        in.close();
        out.close();
    }

    @Test
    public void testPrintWithLineSeperator3() {
        SimpleWriter out = new SimpleWriter1L("PrintWithLineSeperator3.txt");
        String str = "~~CSE~2221";
        StringReassembly.printWithLineSeparators(str, out);
        SimpleReader in = new SimpleReader1L("PrintWithLineSeperator3.txt");

        String str1exp = "";
        String str2exp = "";
        String str3exp = "CSE";
        String str4exp = "2221";

        assertEquals(str1exp, in.nextLine());
        assertEquals(str2exp, in.nextLine());
        assertEquals(str3exp, in.nextLine());
        assertEquals(str4exp, in.nextLine());

        in.close();
        out.close();
    }

    @Test
    public void testLinesFromInput1() {
        SimpleReader in = new SimpleReader1L("LinesFromInput1.txt");
        Set<String> actualSet = StringReassembly.linesFromInput(in);
        in.close();

        Set<String> expectedSet = new Set1L<>();
        expectedSet.add("The");
        expectedSet.add("Ohio");
        expectedSet.add("State");
        expectedSet.add("University");

        assertEquals(expectedSet, actualSet);
    }

}
