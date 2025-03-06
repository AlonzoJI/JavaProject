import components.map.Map;
import components.program.Program;
import components.program.Program1;
import components.queue.Queue;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.utilities.Reporter;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary method {@code parse} for {@code Program}.
 *
 * @author Put your name here
 *
 */
public final class Program1Parse1 extends Program1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Parses a single BL instruction from {@code tokens} returning the
     * instruction name as the value of the function and the body of the
     * instruction in {@code body}.
     *
     * @param tokens
     *            the input tokens
     * @param body
     *            the instruction body
     * @return the instruction name
     * @replaces body
     * @updates tokens
     * @requires <pre>
     * [<"INSTRUCTION"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [an instruction string is a proper prefix of #tokens]  and
     *    [the beginning name of this instruction equals its ending name]  and
     *    [the name of this instruction does not equal the name of a primitive
     *     instruction in the BL language] then
     *  parseInstruction = [name of instruction at start of #tokens]  and
     *  body = [Statement corresponding to the block string that is the body of
     *          the instruction string at start of #tokens]  and
     *  #tokens = [instruction string at start of #tokens] * tokens
     * else
     *  [report an appropriate error message to the console and terminate client]
     * </pre>
     */
    private static String parseInstruction(Queue<String> tokens,
            Statement body) {
        assert tokens != null : "Violation of: tokens is not null";
        assert body != null : "Violation of: body is not null";
        assert tokens.length() > 0 && tokens.front().equals("INSTRUCTION") : ""
                + "Violation of: <\"INSTRUCTION\"> is proper prefix of tokens";

        Set<String> prims = new Set1L<>();
        prims.add("move");
        prims.add("turnleft");
        prims.add("turnright");
        prims.add("infect");
        prims.add("skip");

        String instr = tokens.dequeue();
        Reporter.assertElseFatalError(instr.equals("INSTRUCTION"),
                "ERROR: Expected 'INSTRUCTION', found '" + instr + "'.");

        String instrName = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(instrName),
                "ERROR: Instruction name '" + instrName
                        + "' is not a valid identifier.");
        Reporter.assertElseFatalError(!prims.contains(instrName),
                "ERROR: Instruction name must not be a primitive name: '"
                        + instrName + "'.");

        String instructionIS = tokens.dequeue();
        Reporter.assertElseFatalError(instructionIS.equals("IS"),
                "ERROR: Expected 'IS', but found '" + instructionIS + "'.");

        body.parseBlock(tokens);

        String instructionEND = tokens.dequeue();
        Reporter.assertElseFatalError(instructionEND.equals("END"),
                "ERROR: Expected 'END', but found '" + instructionEND + "'.");

        String endInstrName = tokens.dequeue();
        Reporter.assertElseFatalError(instrName.equals(endInstrName),
                "ERROR: Expected ending instruction name '" + instrName
                        + "', but found '" + endInstrName + "'.");

        return instrName;
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Program1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(SimpleReader in) {
        assert in != null : "Violation of: in is not null";
        assert in.isOpen() : "Violation of: in.is_open";
        Queue<String> tokens = Tokenizer.tokens(in);
        this.parse(tokens);
    }

    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        String currentProgram = tokens.dequeue();
        Reporter.assertElseFatalError(currentProgram.equals("PROGRAM"),
                "ERROR: Expected 'PROGRAM', but found '" + currentProgram
                        + "'.");

        String programName = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(programName),
                "ERROR: Program name '" + programName
                        + "' is not a valid identifier.");

        String programIS = tokens.dequeue();
        Reporter.assertElseFatalError(programIS.equals("IS"),
                "ERROR: Expected 'IS', but found '" + programIS + "'.");

        Map<String, Statement> tokensInfo = this.newContext();
        String checkStarterToken = tokens.front();

        while (checkStarterToken.equals("INSTRUCTION")) {
            Statement block = this.newBody();
            String instrName = parseInstruction(tokens, block);

            for (Map.Pair<String, Statement> val : tokensInfo) {
                Reporter.assertElseFatalError(!val.key().equals(instrName),
                        "ERROR: Instruction '" + instrName
                                + "' is already defined.");
            }

            tokensInfo.add(instrName, block);
            checkStarterToken = tokens.front();
        }

        Reporter.assertElseFatalError(checkStarterToken.equals("BEGIN"),
                "ERROR: Expected 'BEGIN', but found '" + checkStarterToken
                        + "'.");

        Statement block = this.newBody();
        tokens.dequeue();
        block.parseBlock(tokens);

        String tokenEND = tokens.dequeue();
        Reporter.assertElseFatalError(tokenEND.equals("END"),
                "ERROR: Expected 'END', but found '" + tokenEND + "'.");

        String programNameEND = tokens.dequeue();
        Reporter.assertElseFatalError(programNameEND.equals(programName),
                "ERROR: Expected end program name '" + programName
                        + "', but found '" + programNameEND + "'.");

        Reporter.assertElseFatalError(
                tokens.front().equals("### END OF INPUT ###"),
                "ERROR: Expected end of input, but found '" + tokens.front()
                        + "'.");

        this.swapContext(tokensInfo);
        this.setName(programName);
        this.swapBody(block);
    }

    /*
     * Main test method -------------------------------------------------------
     */

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Get input file name
         */
        out.print("Enter valid BL program file name: ");
        String fileName = in.nextLine();
        /*
         * Parse input file
         */
        out.println("*** Parsing input file ***");
        Program p = new Program1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        p.parse(tokens);
        /*
         * Pretty print the program
         */
        out.println("*** Pretty print of parsed program ***");
        p.prettyPrint(out);

        in.close();
        out.close();
    }

}
