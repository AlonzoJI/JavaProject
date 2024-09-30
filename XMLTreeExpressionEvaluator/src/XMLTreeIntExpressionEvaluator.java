import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.Reporter;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to evaluate XMLTree expressions of {@code int}.
 *
 * @author Jared Alonzo
 *
 */
public final class XMLTreeIntExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeIntExpressionEvaluator() {
    }

    /**
     * Evaluate the given expression.
     *
     * @param exp
     *            the {@code XMLTree} representing the expression
     * @return the value of the expression
     * @requires <pre>
     * [exp is a subtree of a well-formed XML arithmetic expression]  and
     *  [the label of the root of exp is not "expression"]
     * </pre>
     * @ensures evaluate = [the value of the expression]
     */
    private static int evaluate(XMLTree exp) {
        assert exp != null : "Violation of: exp is not null";

        int result = 0;

        if (exp.numberOfChildren() == 0) {
            result = Integer.parseInt(exp.attributeValue("value"));
        } else {
            String operator = exp.label();
            XMLTree leftChild = exp.child(0);
            XMLTree rightChild = exp.child(1);

            int leftValue = evaluate(leftChild);
            int rightValue = evaluate(rightChild);

            if (operator.equals("plus")) {
                result = leftValue + rightValue;
            } else if (operator.equals("minus")) {
                result = leftValue - rightValue;
            } else if (operator.equals("times")) {
                result = leftValue * rightValue;
            } else if (operator.equals("divide")) {
                if (rightValue == 0) {
                    Reporter.fatalErrorToConsole(
                            "Precondition violated: divide by zero");
                } else {
                    result = leftValue / rightValue;
                }
            } else {
                Reporter.fatalErrorToConsole("Unknown operator: " + operator);
            }
        }

        return result;
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

        out.print("Enter the name of an expression XML file: ");
        String file = in.nextLine();
        while (!file.equals("")) {
            XMLTree exp = new XMLTree1(file);
            out.println(evaluate(exp.child(0)));
            out.print("Enter the name of an expression XML file: ");
            file = in.nextLine();
        }

        in.close();
        out.close();
    }

}