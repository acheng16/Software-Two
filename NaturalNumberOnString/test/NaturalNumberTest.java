import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;

/**
 * JUnit test fixture for {@code NaturalNumber}'s constructors and kernel
 * methods.
 * 
 * @author Andrew Cheng and Stacey Frye
 * 
 */
public abstract class NaturalNumberTest {

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     * 
     * @return the new number
     * @ensures <pre>
     * {@code constructorTest = 0}
     * </pre>
     */
    protected abstract NaturalNumber constructorTest();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     * 
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires <pre>
     * {@code i >= 0}
     * </pre>
     * @ensures <pre>
     * {@code constructorTest = i}
     * </pre>
     */
    protected abstract NaturalNumber constructorTest(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     * 
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires <pre>
     * {@code there exists n: NATURAL (s = TO_STRING(n))}
     * </pre>
     * @ensures <pre>
     * {@code s = TO_STRING(constructorTest)}
     * </pre>
     */
    protected abstract NaturalNumber constructorTest(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     * 
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures <pre>
     * {@code constructorTest = n}
     * </pre>
     */
    protected abstract NaturalNumber constructorTest(NaturalNumber n);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     * 
     * @return the new number
     * @ensures <pre>
     * {@code constructorRef = 0}
     * </pre>
     */
    protected abstract NaturalNumber constructorRef();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     * 
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires <pre>
     * {@code i >= 0}
     * </pre>
     * @ensures <pre>
     * {@code constructorRef = i}
     * </pre>
     */
    protected abstract NaturalNumber constructorRef(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     * 
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires <pre>
     * {@code there exists n: NATURAL (s = TO_STRING(n))}
     * </pre>
     * @ensures <pre>
     * {@code s = TO_STRING(constructorRef)}
     * </pre>
     */
    protected abstract NaturalNumber constructorRef(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     * 
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures <pre>
     * {@code constructorRef = n}
     * </pre>
     */
    protected abstract NaturalNumber constructorRef(NaturalNumber n);

    /*
     * Test cases for constructors
     */

    @Test
    public final void testDefaultConstructor() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest();
        NaturalNumber nExpected = this.constructorRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /*
     * Test cases for kernel methods
     */

    @Test
    public final void testMultiplyBy10UsingInts() {
        /*
         * Set up variables
         */
        int first = 242;
        int ans = 2420;
        NaturalNumber n = this.constructorTest(first);
        NaturalNumber nExpected = this.constructorRef(ans);
        /*
         * Call method under test
         */
        n.multiplyBy10(0);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testMultiplyBy10UsingMaxInts() {
        /*
         * Set up variables
         */
        int first = Integer.MAX_VALUE;
        NaturalNumber n = this.constructorTest(first);
        NaturalNumber nExpected = this.constructorRef(first);
        nExpected.multiplyBy10(0);
        /*
         * Call method under test
         */
        n.multiplyBy10(0);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testMultiplyBy10UsingZeroInts() {
        /*
         * Set up variables
         */
        int first = 0;
        int ans = 0;
        NaturalNumber n = this.constructorTest(first);
        NaturalNumber nExpected = this.constructorRef(ans);
        /*
         * Call method under test
         */
        n.multiplyBy10(0);
        nExpected.multiplyBy10(0);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testMultiplyBy10UsingString() {
        /*
         * Set up variables
         */
        String first = "712546";
        String ans = "7125460";
        NaturalNumber n = this.constructorTest(first);
        NaturalNumber nExpected = this.constructorRef(ans);
        /*
         * Call method under test
         */
        n.multiplyBy10(0);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testMultiplyBy10UsingMaxString() {
        /*
         * Set up variables
         */
        String first = Integer.toString(Integer.MAX_VALUE);
        String ans = "21474836470";
        NaturalNumber n = this.constructorTest(first);
        NaturalNumber nExpected = this.constructorRef(ans);
        /*
         * Call method under test
         */
        n.multiplyBy10(0);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testMultiplyBy10UsingNN() {
        /*
         * Set up variables
         */
        int firstInt = 2167;
        NaturalNumber first = this.constructorTest(firstInt);
        int ansInt = 21670;
        NaturalNumber ans = this.constructorTest(ansInt);

        NaturalNumber n = this.constructorTest(first);
        NaturalNumber nExpected = this.constructorRef(ans);
        /*
         * Call method under test
         */
        n.multiplyBy10(0);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testMultiplyBy10UsingMaxIntNN() {
        /*
         * Set up variables
         */
        int firstInt = Integer.MAX_VALUE;
        NaturalNumber first = this.constructorTest(firstInt);
        NaturalNumber ans = this.constructorTest(firstInt);
        ans.multiplyBy10(0);

        NaturalNumber n = this.constructorTest(first);
        NaturalNumber nExpected = this.constructorRef(ans);
        /*
         * Call method under test
         */
        n.multiplyBy10(0);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    @Test
    public final void testDivideBy10UsingInts() {
        /*
         * Set up variables
         */
        int first = 242;
        int ans = 24;
        int remExpected = 2;
        NaturalNumber n = this.constructorTest(first);
        NaturalNumber nExpected = this.constructorRef(ans);
        /*
         * Call method under test
         */
        int rem = n.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertEquals(remExpected, rem);
    }

    @Test
    public final void testDivideBy10UsingZeroInts() {
        /*
         * Set up variables
         */
        int first = 0;
        int ans = 0;
        int remExpected = 0;
        NaturalNumber n = this.constructorTest(first);
        NaturalNumber nExpected = this.constructorRef(ans);
        /*
         * Call method under test
         */
        int rem = n.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertEquals(remExpected, rem);
    }

    @Test
    public final void testDivideBy10UsingOneInts() {
        /*
         * Set up variables
         */
        int first = 7;
        int ans = 0;
        int remExpected = 7;
        NaturalNumber n = this.constructorTest(first);
        NaturalNumber nExpected = this.constructorRef(ans);
        /*
         * Call method under test
         */
        int rem = n.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertEquals(remExpected, rem);
    }

    @Test
    public final void testDivideBy10UsingString() {
        /*
         * Set up variables
         */
        String first = "712546";
        String ans = "71254";
        String remExpected = "6";
        NaturalNumber n = this.constructorTest(first);
        NaturalNumber nExpected = this.constructorRef(ans);
        /*
         * Call method under test
         */
        int r = n.divideBy10();
        String rem = Integer.toString(r);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertEquals(remExpected, rem);
    }

    @Test
    public final void testDivideBy10UsingOneString() {
        /*
         * Set up variables
         */
        String first = "5";
        String ans = "0";
        String remExpected = "5";
        NaturalNumber n = this.constructorTest(first);
        NaturalNumber nExpected = this.constructorRef(ans);
        /*
         * Call method under test
         */
        int r = n.divideBy10();
        String rem = Integer.toString(r);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertEquals(remExpected, rem);
    }

    @Test
    public final void testDivideBy10UsingZeroString() {
        /*
         * Set up variables
         */
        String first = "0";
        String ans = "0";
        String remExpected = "0";
        NaturalNumber n = this.constructorTest(first);
        NaturalNumber nExpected = this.constructorRef(ans);
        /*
         * Call method under test
         */
        int r = n.divideBy10();
        String rem = Integer.toString(r);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertEquals(remExpected, rem);
    }

    @Test
    public final void testDivideBy10UsingNN() {
        /*
         * Set up variables
         */
        int firstInt = 2167;
        NaturalNumber first = this.constructorTest(firstInt);
        int ansInt = 216;
        NaturalNumber ans = this.constructorTest(ansInt);
        int remExpected = 7;

        NaturalNumber n = this.constructorTest(first);
        NaturalNumber nExpected = this.constructorRef(ans);
        /*
         * Call method under test
         */
        int rem = n.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertEquals(remExpected, rem);
    }

    @Test
    public final void testDivideBy10UsingPrimeNN() {
        /*
         * Set up variables
         */
        int firstInt = 73;
        NaturalNumber first = this.constructorTest(firstInt);
        int ansInt = 7;
        NaturalNumber ans = this.constructorTest(ansInt);
        int remExpected = 3;

        NaturalNumber n = this.constructorTest(first);
        NaturalNumber nExpected = this.constructorRef(ans);
        /*
         * Call method under test
         */
        int rem = n.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertEquals(remExpected, rem);
    }

    @Test
    public final void testDivideBy10UsingZeroNN() {
        /*
         * Set up variables
         */
        int firstInt = 0;
        NaturalNumber first = this.constructorTest(firstInt);
        int ansInt = 0;
        NaturalNumber ans = this.constructorTest(ansInt);
        int remExpected = 0;

        NaturalNumber n = this.constructorTest(first);
        NaturalNumber nExpected = this.constructorRef(ans);
        /*
         * Call method under test
         */
        int rem = n.divideBy10();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertEquals(remExpected, rem);
    }

    @Test
    public final void testIsZeroUsingIntsNonZero() {
        /*
         * Set up variables
         */
        int first = 242;
        NaturalNumber n = this.constructorTest(first);
        NaturalNumber nExpected = this.constructorRef(first);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertFalse(n.isZero());
    }

    @Test
    public final void testIsZeroUsingIntsZero() {
        /*
         * Set up variables
         */
        int first = 0;
        NaturalNumber n = this.constructorTest(first);
        NaturalNumber nExpected = this.constructorRef(first);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertTrue(n.isZero());
    }

    @Test
    public final void testIsZeroUsingStringNonZero() {
        /*
         * Set up variables
         */
        String first = "712546";
        NaturalNumber n = this.constructorTest(first);
        NaturalNumber nExpected = this.constructorRef(first);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertFalse(n.isZero());
    }

    @Test
    public final void testIsZeroUsingStringZero() {
        /*
         * Set up variables
         */
        String first = "0";
        NaturalNumber n = this.constructorTest(first);
        NaturalNumber nExpected = this.constructorRef(first);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertTrue(n.isZero());
    }

    @Test
    public final void testIsZeroUsingNNNonZero() {
        /*
         * Set up variables
         */
        int firstInt = 2167;
        NaturalNumber first = this.constructorTest(firstInt);

        NaturalNumber n = this.constructorTest(first);
        NaturalNumber nExpected = this.constructorRef(first);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertFalse(n.isZero());
    }

    @Test
    public final void testIsZeroUsingNNZero() {
        /*
         * Set up variables
         */
        int firstInt = 0;
        NaturalNumber first = this.constructorTest(firstInt);

        NaturalNumber n = this.constructorTest(first);
        NaturalNumber nExpected = this.constructorRef(first);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertTrue(n.isZero());
    }

    @Test
    public final void testConstructorInt() {
        NaturalNumber n = this.constructorTest(0);
        NaturalNumber nExpected = this.constructorRef(0);
        assertEquals(n, nExpected);
    }
}
