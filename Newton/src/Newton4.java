import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * This program calculates the square root of a positive double using the
 * Newton-Raphson method.
 *
 * Author: Jared Alonzo
 */
public final class Newton4 {

    /**
     * Private constructor to prevent instantiation of the class.
     */
    private Newton4() {
    }

    /**
     * Computes estimate of square root of x to within relative error specified
     * by epsilon.
     *
     * @param x
     *            positive number to compute square root of
     * @param epsilon
     *            relative error for estimation
     * @return estimate of square root
     */
    private static double sqrt(double x, double epsilon) {
        double r = x;

        while (Math.abs(r * r - x) / x > epsilon * epsilon) {
            if (x != 0) {
                r = (x / r + r) / 2;
            } else if (x == 0) {
                r = 0;
            } else if (x < 0) {
                // Handle the case where x is negative by setting the result to -1.
                r = -1;
            }
        }
        return r;
    }

    /**
     * Main method to interact with the user and perform square root
     * calculations.
     *
     * @param args
     *            the command line arguments (not used)
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        boolean goAgain = true;

        while (goAgain) {
            out.print(
                    "Please input your positive double (or a negative value to quit): ");
            double x = in.nextDouble();

            if (x >= 0) {
                out.print("Enter a value for Epsilon: ");
                double epsilon = in.nextDouble();

                double result = sqrt(x, epsilon);
                out.println(
                        "The square root for your positive double: " + result);
            } else if (x < 0) {
                out.println("Bye for now!");
                goAgain = false;
            }
        }

        // Close input and output streams
        in.close();
        out.close();
    }
}
