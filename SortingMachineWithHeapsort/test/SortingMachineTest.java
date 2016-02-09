import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 * 
 * @author Put your name here
 * 
 */
public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * implementation under test and returns the result.
     * 
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires <pre>
     * {@code IS_TOTAL_PREORDER([relation computed by order.compare method])}
     * </pre>
     * @ensures <pre>
     * {@code constructorTest = (true, order, {})}
     * </pre>
     */
    protected abstract SortingMachine<String> constructorTest(
            Comparator<String> order);

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * reference implementation and returns the result.
     * 
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires <pre>
     * {@code IS_TOTAL_PREORDER([relation computed by order.compare method])}
     * </pre>
     * @ensures <pre>
     * {@code constructorRef = (true, order, {})}
     * </pre>
     */
    protected abstract SortingMachine<String> constructorRef(
            Comparator<String> order);

    /**
     * 
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type with the given entries and mode.
     * 
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires <pre>
     * {@code IS_TOTAL_PREORDER([relation computed by order.compare method])}
     * </pre>
     * @ensures <pre>
     * {@code createFromArgsTest = (insertionMode, order, [multiset of entries in args])}
     * </pre>
     */
    private SortingMachine<String> createFromArgsTest(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorTest(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * 
     * Creates and returns a {@code SortingMachine<String>} of the reference
     * implementation type with the given entries and mode.
     * 
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires <pre>
     * {@code IS_TOTAL_PREORDER([relation computed by order.compare method])}
     * </pre>
     * @ensures <pre>
     * {@code createFromArgsRef = (insertionMode, order, [multiset of entries in args])}
     * </pre>
     */
    private SortingMachine<String> createFromArgsRef(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorRef(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * Comparator<String> implementation to be used in all test cases. Compare
     * {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    }

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final StringLT ORDER = new StringLT();

    /*
     * Test default constructor
     */

    @Test
    public final void testConstructor() {
        /*
         * Set up variables and call method to test
         */
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);
        /*
         * Assert values are true
         */
        assertEquals(mExpected, m);
    }

    /*
     * Test kernel methods
     */

    @Test
    public final void testAddEmpty() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green");
        /*
         * Call method to test
         */
        m.add("green");
        /*
         * Assert values are true
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddNonEmpty1() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "blue", "green");
        /*
         * Call method to test
         */
        m.add("green");
        /*
         * Assert values are true
         */
        assertEquals(mExpected, m);
    }

    public final void testAddNonEmpty2() {
        /*
         * Set up variables; multiple entries
         */
        SortingMachine<String> m = this
                .createFromArgsTest(ORDER, true, "maroon", "green", "maroon",
                        "violet", "red", "scarlet", "gold");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "maroon", "green", "maroon", "violet", "red", "scarlet",
                "gold", "red");
        /*
         * Call method to test
         */
        m.add("red");
        /*
         * Assert values are true
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddNonEmpty3() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "blue",
                "green", "red", "purple", "orange");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "zebra", "blue", "green", "red", "purple", "orange");
        /*
         * Call method to test
         */
        m.add("zebra");
        /*
         * Call method to test and assert values are true
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testChangeToExtractionModeEmpty() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        /*
         * Call method to test
         */
        m.changeToExtractionMode();
        /*
         * Assert values are true
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testChangeToExtractionModeNonEmpty1() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "blue");
        /*
         * Call method to test
         */
        m.changeToExtractionMode();
        /*
         * Assert values are true
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testChangeToExtractionModeNonEmpty2() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "monkey", "giraffe", "bird", "alligator", "lion", "cheetah",
                "penguin", "elephant");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "monkey", "giraffe", "bird", "alligator", "lion", "cheetah",
                "penguin", "elephant");
        /*
         * Call method to test
         */
        m.changeToExtractionMode();
        /*
         * Assert values are true
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testChangeToExtractionModeNonEmpty3() {
        /*
         * Set up variables; multiple entries
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "monkey", "monkey", "bird", "bird", "lion", "lion", "penguin",
                "penguin");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "monkey", "monkey", "bird", "bird", "lion", "lion", "penguin",
                "penguin");
        /*
         * Call method to test
         */
        m.changeToExtractionMode();
        /*
         * Assert values are true
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testRemoveFirstToEmpty() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this
                .createFromArgsTest(ORDER, false, "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "blue");
        /*
         * Call method to test
         */
        String r = m.removeFirst();
        String rExpected = mExpected.removeFirst();
        /*
         * Assert values are true
         */
        assertEquals(mExpected, m);
        assertEquals(rExpected, r);
    }

    @Test
    public final void testRemoveFirstToNonEmpty1() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "blue", "green");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green");
        /*
         * Call method to test
         */
        String r = m.removeFirst();
        String rExpected = "blue";
        /*
         * Assert values are true
         */
        assertEquals(mExpected, m);
        assertEquals(rExpected, r);
    }

    @Test
    public final void testRemoveFirstToNonEmpty2() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "keyboard", "mouse", "screen", "router", "tower", "monitor",
                "cables", "internet");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "keyboard", "mouse", "screen", "router", "tower", "monitor",
                "internet");
        /*
         * Call method to test
         */
        String r = m.removeFirst();
        String rExpected = "cables";
        /*
         * Assert values are true
         */
        assertEquals(mExpected, m);
        assertEquals(rExpected, r);
    }

    @Test
    public final void testRemoveToNonEmpty3() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "blue", "green", "red", "purple", "orange");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "blue", "green", "red", "purple", "orange");
        /*
         * Call method to test
         */
        String s = m.removeFirst();
        mExpected.order();
        String sExpected = mExpected.removeFirst();
        /*
         * Call method to test and assert values are true
         */
        assertEquals(mExpected, m);
        assertEquals(sExpected, s);
    }

    @Test
    public final void testRemoveToNonEmpty4() {
        /*
         * Set up variables; multiple entries
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "house", "house", "blouse", "blouse", "orange");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "house", "house", "blouse", "blouse", "orange");
        /*
         * Call method to test
         */
        String s = m.removeFirst();
        mExpected.order();
        String sExpected = mExpected.removeFirst();
        /*
         * Call method to test and assert values are true
         */
        assertEquals(mExpected, m);
        assertEquals(sExpected, s);
    }

    @Test
    public final void testIsInInsertionModeTrueNonEmpty() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "blue");
        /*
         * Call method to test and assert values are true
         */
        assertEquals(mExpected, m);
        assertEquals(true, m.isInInsertionMode());
    }

    @Test
    public final void testIsInInsertionModeFalseNonEmpty() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this
                .createFromArgsTest(ORDER, false, "blue");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "blue");
        /*
         * Call method to test and assert values are true
         */
        assertEquals(false, m.isInInsertionMode());
    }

    @Test
    public final void testIsInInsertionModeTrueEmpty() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        /*
         * Call method to test and assert values are true
         */
        assertEquals(mExpected, m);
        assertEquals(true, m.isInInsertionMode());
    }

    @Test
    public final void testIsInInsertionModeFalseEmpty() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        /*
         * Call method to test and assert values are true
         */
        assertEquals(false, m.isInInsertionMode());
    }

    @Test
    public final void testSizeEmptyInsertionMode() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        /*
         * Call method to test
         */
        int s = m.size();
        int sExpected = 0;
        /*
         * Call method to test and assert values are true
         */
        assertEquals(mExpected, m);
        assertEquals(sExpected, s);
    }

    @Test
    public final void testSizeNonEmptyInsertionMode() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "blue",
                "green", "red", "purple", "orange");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "blue", "green", "red", "purple", "orange");
        /*
         * Call method to test
         */
        int s = m.size();
        int sExpected = 5;
        /*
         * Call method to test and assert values are true
         */
        assertEquals(mExpected, m);
        assertEquals(sExpected, s);
    }

    @Test
    public final void testSizeNonEmptyExtractionMode1() {
        /*
         * Set up variables; multiple entries
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "pipe", "people", "paper", "purple", "police", "pain", "pants",
                "paper");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "pipe", "people", "paper", "purple", "police", "pain", "pants",
                "paper");
        /*
         * Call method to test
         */
        int s = m.size();
        int sExpected = 8;
        /*
         * Call method to test and assert values are true
         */
        assertEquals(mExpected, m);
        assertEquals(sExpected, s);
    }

    @Test
    public final void testSizeNonEmptyExtractionMode2() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false,
                "blue", "green", "red", "purple", "orange");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "blue", "green", "red", "purple", "orange");
        /*
         * Call method to test
         */
        int s = m.size();
        int sExpected = 5;
        /*
         * Call method to test and assert values are true
         */
        assertEquals(mExpected, m);
        assertEquals(sExpected, s);
    }

    @Test
    public final void testSizeEmptyExtractionMode() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        /*
         * Call method to test
         */
        int s = m.size();
        int sExpected = 0;
        /*
         * Call method to test and assert values are true
         */
        assertEquals(mExpected, m);
        assertEquals(sExpected, s);
    }

    @Test
    public final void testSizeExtractionEmpty() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        /*
         * Call method to test
         */
        int s = m.size();
        int sExpected = 0;
        /*
         * Call method to test and assert values are true
         */
        assertEquals(mExpected, m);
        assertEquals(sExpected, s);
    }

    @Test
    public final void testOrderEmptyInsertionMode() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true);
        /*
         * Call method to test
         */
        m.order();
        /*
         * Call method to test and assert values are true
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testOrderEmptyExtractionMode() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        /*
         * Call method to test
         */
        m.order();
        /*
         * Call method to test and assert values are true
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testOrderNonEmptyInsertionMode() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "red",
                "purple", "orange");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "orange", "purple", "red");
        /*
         * Call method to test
         */
        m.order();
        /*
         * Call method to test and assert values are true
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testOrderNonEmptyExtractionMode() {
        /*
         * Set up variables
         */
        SortingMachine<String> m = this.createFromArgsTest(ORDER, false, "red",
                "purple", "orange");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "orange", "purple", "red");
        /*
         * Call method to test
         */
        m.order();
        /*
         * Call method to test and assert values are true
         */
        assertEquals(mExpected, m);
    }

    @Test
    public final void testCombination() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "blue",
                "green", "red", "purple", "orange");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "zebra", "blue", "green", "red", "purple", "orange");

        m.add("zebra");
        m.changeToExtractionMode();
        mExpected.changeToExtractionMode();

        m.removeFirst();
        mExpected.removeFirst();

        int l = m.size();
        int lE = mExpected.size();

        assertEquals(mExpected, m);
        assertEquals(lE, l);
    }
}
