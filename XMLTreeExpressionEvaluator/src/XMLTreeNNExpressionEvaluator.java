import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
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
public final class XMLTreeNNExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeNNExpressionEvaluator() {
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
    private static NaturalNumber evaluate(XMLTree exp) {
        NaturalNumber result = new NaturalNumber2(0);

        if (exp.numberOfChildren() == 0) {
            result.copyFrom(new NaturalNumber2(
                    Integer.parseInt(exp.attributeValue("value"))));
        } else {
            String operator = exp.label();

            XMLTree leftChild = exp.child(0);
            XMLTree rightChild = exp.child(1);

            NaturalNumber leftValue = evaluate(leftChild);
            NaturalNumber rightValue = evaluate(rightChild);

            if (operator.equals("plus")) {
                result = leftValue;
                result.add(rightValue);
            } else if (operator.equals("minus")) {
                if (leftValue.compareTo(rightValue) >= 0) {
                    result = leftValue;
                    result.subtract(rightValue);
                } else {
                    Reporter.fatalErrorToConsole(
                            "Precondition violated: minus");
                }
            } else if (operator.equals("times")) {
                result = rightValue;
                result.multiply(leftValue);
            } else if (operator.equals("divide")) {
                if (rightValue.isZero()) {
                    Reporter.fatalErrorToConsole(
                            "Precondition violated: divide by zero");
                } else {
                    result = leftValue;
                    result.divide(rightValue);
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