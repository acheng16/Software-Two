import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumberSecondary;

/**
 * @author Andrew Cheng and Stacey Frye
 */

/**
 * {@code NaturalNumber} represented as a {@code String} with implementations of
 * primary methods.
 * 
 * @convention <pre>
 * {@code
 * [all characters of $this.rep are '0' through '9', with no leading '0']
 * }
 * </pre>
 * @correspondence <pre>
 * {@code 
 * this = [if $this.rep = "" then 0; else the decimal number whose ordinary
 *         depiction is $this.rep]
 * }
 * </pre>
 */
public class NaturalNumber3 extends NaturalNumberSecondary {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Constant 10 (so it's not a "magic number" in the code).
     */
    private static final int TEN = 10;

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
     * Default constructor.
     */
    public NaturalNumber3() {
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

        this.createNewRep();

        if (i > 0) {
            this.rep = Integer.toString(i);
        } else {
            this.rep = "";
        }

    }

    /**
     * Constructor from {@code String}
     * 
     * @param s
     *            {@code String} to initialize from
     */
    public NaturalNumber3(String s) {
        assert s != null : "Violation of: s is not null";
        assert s.matches("0|[1-9]\\d*") : ""
                + "Violation of: there exists n: NATURAL (s = TO_STRING(n))";

        this.createNewRep();

        if (Integer.parseInt(s) != 0) {
            this.rep = s;
        } else {
            this.rep = "";
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

        this.createNewRep();

        if (!n.isZero()) {
            this.rep = n.toString();
        } else {
            this.rep = "";
        }

    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @Override
    public final NaturalNumber newInstance() {
        try {
            return this.getClass().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError("Cannot construct object of type "
                    + this.getClass());
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
        assert k < TEN : "Violation of: k < 10";

        if (this.rep != ("")) {
            String str = String.valueOf(k);
            this.rep = this.rep.concat(str);
        } else if (this.rep != ("") && k > 0) {
            String str = String.valueOf(k);
            this.rep = this.rep.concat(str);
        }
    }

    @Override
    public final int divideBy10() {

        String newRep = "";
        String strRem = "";

        if (!this.rep.isEmpty()) {
            strRem = this.rep.substring(this.rep.length() - 1);
            newRep = this.rep.substring(0, this.rep.length() - 1);
            this.rep = newRep;
            return Integer.parseInt(strRem);
        }

        return 0;
    }

    @Override
    public final boolean isZero() {

        boolean zero = false;
        if (this.rep.isEmpty()) {
            zero = true;
        }

        return zero;
    }

}
