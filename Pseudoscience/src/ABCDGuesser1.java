import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.FormatChecker;

/**
 * A program that finds the best approximation for given input values by varying
 * A, B, C, and D from a predefined set of values.
 *
 * @author Jared Alonzo
 *
 */
public final class ABCDGuesser1 {

    /**
     * Java doc to prevent a warning.
     */
    private ABCDGuesser1() {

    }

    /**
     * Repeatedly asks the user for a positive real number until the user enters
     * one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number entered by the user
     */
    private static double getPositiveDouble(SimpleReader in, SimpleWriter out) {
        double positiveDouble = 0;
        boolean keepAsking = true;

        while (keepAsking == true) {
            out.print("Please enter a positive real number: ");
            String input = in.nextLine();

            if (FormatChecker.canParseDouble(input)) {
                positiveDouble = Double.parseDouble(input);
                if (positiveDouble > 0) {
                    keepAsking = false;
                } else {
                    out.println("Please enter a positive real number.");
                }
            } else {
                out.println("Invalid input. Please enter a valid real number.");
            }
        }
        return positiveDouble;
    }

    /**
     * Repeatedly asks the user for a positive real number not equal to 1.0
     * until the user enters one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number not equal to 1.0 entered by the user
     */
    private static double getPositiveDoubleNotOne(SimpleReader in,
            SimpleWriter out) {
        double positiveDoubleNotOne = 0;
        boolean validUserInput = false;

        while (!validUserInput) {
            out.print("Please enter a positive real number not equal to 1.0: ");
            String input = in.nextLine();

            if (FormatChecker.canParseDouble(input)) {
                positiveDoubleNotOne = Double.parseDouble(input);
                if (positiveDoubleNotOne > 0 && positiveDoubleNotOne != 1.0) {
                    validUserInput = true;
                } else {
                    out.println(
                            "Please enter a positive real number not equal to 1.0.");
                }
            } else {
                out.println("Invalid input. Please enter a valid real number.");
            }
        }
        return positiveDoubleNotOne;
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

        double valueOfMu = getPositiveDouble(in, out);
        double valueOfW = getPositiveDoubleNotOne(in, out);
        double valueOfX = getPositiveDoubleNotOne(in, out);
        double valueOfY = getPositiveDoubleNotOne(in, out);
        double valueOfZ = getPositiveDoubleNotOne(in, out);
        final double multipleToGetPercentage = 100.0;

        /**
         * The charming theory digits.
         */
        final double[] CHARMING_THEORY = { -5, -4, -3, -2, -1, -1.0 / 2,
                -1.0 / 3, -1.0 / 4, 0, 1.0 / 4, 1.0 / 3, 1.0 / 2, 1, 2, 3, 4,
                5 };

        double bestApproximation = Double.MAX_VALUE;
        double bestApproxA = 0, bestApproxB = 0, bestApproxC = 0,
                bestApproxD = 0;

        int aCount = 0;
        int bCount = 0;
        int cCount = 0;
        int dCount = 0;

        while (aCount < CHARMING_THEORY.length) {
            double a = CHARMING_THEORY[aCount];
            while (bCount < CHARMING_THEORY.length) {
                double b = CHARMING_THEORY[bCount];
                while (cCount < CHARMING_THEORY.length) {
                    double c = CHARMING_THEORY[cCount];
                    while (dCount < CHARMING_THEORY.length) {
                        double d = CHARMING_THEORY[dCount];

                        double approximation = Math.pow(valueOfW, a)
                                * Math.pow(valueOfX, b) * Math.pow(valueOfY, c)
                                * Math.pow(valueOfZ, d);
                        double error = Math
                                .abs((valueOfMu - approximation) / valueOfMu)
                                * multipleToGetPercentage;

                        if (error < Math.abs(
                                (valueOfMu - bestApproximation) / valueOfMu)
                                * multipleToGetPercentage) {
                            bestApproximation = approximation;
                            bestApproxA = a;
                            bestApproxB = b;
                            bestApproxC = c;
                            bestApproxD = d;
                        }
                        dCount++;
                    }
                    dCount = 0;
                    cCount++;
                }
                cCount = 0;
                bCount++;
            }
            bCount = 0;
            aCount++;
        }

        out.println("A: " + bestApproxA + ", B: " + bestApproxB + ", C: "
                + bestApproxC + ", D: " + bestApproxD);
        out.println("Best Approximation: " + bestApproximation);
        out.println(
                "Approximation of Relative Error: "
                        + String.format("%.2f",
                                (Math.abs(valueOfMu - bestApproximation)
                                        / valueOfMu) * multipleToGetPercentage)
                        + "%");

        in.close();
        out.close();

    }
}
