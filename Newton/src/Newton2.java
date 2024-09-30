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
public final class Newton2 {

    /**
     * Private constructor to prevent instantiation of the class.
     */
    private Newton2() {
    }

    /**
     * Computes estimate of square root of x to within relative error 0.01%.
     *
     * @param x
     *            positive number to compute square root of
     * @return estimate of square root
     */
    private static double sqrt(double x) {
        final double EPSILON = 0.0001;
        double r = x;

        while (Math.abs(r * r - x) / x > EPSILON * EPSILON) {
            if (x != 0) {
                r = (x / r + r) / 2;
            } else if (x == 0) {
                r = 0;
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

        String goAgain = "y";

        while (goAgain.equalsIgnoreCase("y")) {
            out.print("Do you want to calculate the square root of your "
                    + "desired number? (y/n): ");
            goAgain = in.nextLine();

            if (goAgain.equalsIgnoreCase("y")) {
                out.print("Please input your positive double: ");
                double x = in.nextDouble();

                if (x > 0) {
                    double result = sqrt(x);
                    out.println("The square root for your positive double: "
                            + result);
                } else if (x == 0) {
                    double result = 0;
                    out.println("The square root of zero is: " + result);
                } else {

                    out.println(
                            "Invalid input. Please enter a positive double.");
                }
            } else if (goAgain.equalsIgnoreCase("n")) {
                out.println("Bye for now!");
            }
        }

        // Close input and output streams
        in.close();
        out.close();
    }
}
