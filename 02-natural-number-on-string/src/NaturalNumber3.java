import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumberSecondary;

/**
 * {@code NaturalNumber} represented as a {@code String} with implementations of
 * primary methods.
 *
 * @convention <pre>
 * [all characters of $this.rep are '0' through '9']  and
 * [$this.rep does not start with '0']
 * </pre>
 * @correspondence <pre>
 * this = [if $this.rep = "" then 0
 *         else the decimal number whose ordinary depiction is $this.rep]
 * </pre>
 *
 * @author Jared Alonzo
 *
 */
public class NaturalNumber3 extends NaturalNumberSecondary {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Representation of {@code this}.
     */
    private String rep;

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {

        this.rep = "";

    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public NaturalNumber3() {

        /*
         * initialize as an empty string.
         */
        this.createNewRep();

    }

    /**
     * Constructor from {@code int}.
     *
     * @param i
     *            {@code int} to initialize from
     */
    public NaturalNumber3(int i) {
        assert i >= 0 : "Violation of: i >= 0";

        /*
         * Initialize this.rep to be an empty string.
         */
        this.createNewRep();
        /*
         * Concatenate the value of i with this.rep if i is greater than 0.
         */
        if (i > 0) {
            this.rep += i;
        }

    }

    /**
     * Constructor from {@code String}.
     *
     * @param s
     *            {@code String} to initialize from
     */
    public NaturalNumber3(String s) {
        assert s != null : "Violation of: s is not null";
        assert s.matches("0|[1-9]\\d*") : ""
                + "Violation of: there exists n: NATURAL (s = TO_STRING(n))";

        /*
         * Initialize this.rep to be an empty string.
         */
        this.createNewRep();
        /*
         * If s is empty, set this.rep to "0". Otherwise, concatenate s to
         * this.rep.
         */
        // For the record, I don't understand why 'anObject' is a thing
        if (s.equals("")) {
            this.rep += "0";
        } else {
            this.rep += s;
        }
    }

    /**
     * Constructor from {@code NaturalNumber}.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     */
    public NaturalNumber3(NaturalNumber n) {
        assert n != null : "Violation of: n is not null";

        /*
         * initialize this to be an empty string.
         */
        this.createNewRep();
        /*
         * adds n to this.rep.
         */
        this.rep += n;

    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @Override
    public final NaturalNumber newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public final void clear() {
        this.createNewRep();
    }

    @Override
    public final void transferFrom(NaturalNumber source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof NaturalNumber3 : ""
                + "Violation of: source is of dynamic type NaturalNumberExample";
        /*
         * This cast cannot fail since the assert above would have stopped
         * execution in that case.
         */
        NaturalNumber3 localSource = (NaturalNumber3) source;
        this.rep = localSource.rep;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void multiplyBy10(int k) {
        assert 0 <= k : "Violation of: 0 <= k";
        assert k < RADIX : "Violation of: k < 10";
        /*
         * If this.rep has a length greater than 0 or if k is greater than 0,
         * concatenate k with this.rep.
         */
        if (this.rep.length() > 0 || k > 0) {
            this.rep += k;
        }
    }

    @Override
    public final int divideBy10() {

        // Initialized to hold the numeric value of the last character in this.rep
        int end = 0;
        /*
         * If this.rep has a length greater than 0, "end" is assigned the
         * numeric value of the last character in this.rep. The last character
         * is then removed from this.rep.
         */
        if (this.rep.length() > 0) {
            end = Character
                    .getNumericValue(this.rep.charAt(this.rep.length() - 1));
            this.rep = this.rep.substring(0, this.rep.length() - 1);

        }
        // Returns the numeric value of the character removed from this.rep
        return end;
    }

    @Override
    public final boolean isZero() {

        /*
         * isZeroBoolean is used to check if this.rep represents the value 0.
         */
        boolean isZeroBoolean = false;
        /*
         * If this.rep is empty, isZeroBoolean is set to true. Otherwise, if the
         * first character of this.rep is '0', isZeroBoolean is also set to
         * true.
         */
        if (this.rep.length() == 0) {
            isZeroBoolean = true;
        } else if (this.rep.charAt(0) == '0') {
            isZeroBoolean = true;

        }
        return isZeroBoolean;
    }

}
