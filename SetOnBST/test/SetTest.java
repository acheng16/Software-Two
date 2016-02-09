import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 * 
 * @author Andrew Cheng and Stacey Frye
 * 
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor for the implementation
     * under test and returns the result.
     * 
     * @return the new set
     * @ensures <pre>
     * {@code constructorTest = {}}
     * </pre>
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor for the reference
     * implementation and returns the result.
     * 
     * @return the new set
     * @ensures <pre>
     * {@code constructorRef = {}}
     * </pre>
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     * 
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires <pre>
     * {@code [every entry in args is unique]}
     * </pre>
     * @ensures <pre>
     * {@code createFromArgsTest = [entries in args]}
     * </pre>
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert !set.contains(s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     * 
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires <pre>
     * {@code [every entry in args is unique]}
     * </pre>
     * @ensures <pre>
     * {@code createFromArgsRef = [entries in args]}
     * </pre>
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert !set.contains(s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /*
     * Test cases for constructors
     */

    @Test
    public final void testDefaultConstructor() {
        /*
         * Set up variables and call method under test
         */
        Set<String> s = this.constructorTest();
        Set<String> sExpected = this.constructorRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    /*
     * Test cases for kernel methods
     */

    @Test
    public final void testAddToEmpty() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef("blood");
        /*
         * Call method under Test
         */
        s.add("blood");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    @Test
    public final void testAddToNonEmpty1() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("Hope", "Floats");
        Set<String> sExpected = this.createFromArgsRef("Hope", "Floats",
                "Movie");
        /*
         * Call method under Test
         */
        s.add("Movie");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    @Test
    public final void testAddToNonEmpty2() {
        /*
         * Set up variables. All right subtrees
         */
        Set<String> s = this.createFromArgsTest("Apple", "Banana", "Carrot",
                "Dairy", "Eggs");
        Set<String> sExpected = this.createFromArgsRef("Apple", "Banana",
                "Carrot", "Dairy", "Eggs", "Flour");
        /*
         * Call method under Test
         */
        s.add("Flour");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    @Test
    public final void testAddToNonEmpty3() {
        /*
         * Set up variables. All left subtrees
         */
        Set<String> s = this.createFromArgsTest("Zulu", "Yankee", "Whiskey");
        Set<String> sExpected = this.createFromArgsRef("Zulu", "Yankee",
                "Whiskey", "X-Ray");
        /*
         * Call method under Test
         */
        s.add("X-Ray");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    @Test
    public final void testAddToNonEmpty4() {
        /*
         * Set up variables. Both left and right subtrees
         */
        Set<String> s = this.createFromArgsTest("jacket", "coat", "sweater",
                "boots", "dress", "pants", "tshirt");
        Set<String> sExpected = this.createFromArgsRef("jacket", "coat",
                "sweater", "boots", "dress", "pants", "tshirt", "socks");
        /*
         * Call method under Test
         */
        s.add("socks");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
    }

    @Test
    public final void testRemoveToEmpty() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("coal");
        Set<String> sExpected = this.createFromArgsRef();
        String rExpected = "coal";
        /*
         * Call method under Test
         */
        String r = s.remove("coal");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(rExpected, r);
    }

    @Test
    public final void testRemoveToNonEmpty1() {
        /*
         * Set up variables. A tree with both left and right subtrees.
         */
        Set<String> s = this.createFromArgsTest("but", "red", "bleed", "not",
                "green");
        Set<String> sExpected = this.createFromArgsRef("but", "red", "bleed",
                "not");
        String rExpected = "green";
        /*
         * Call method under Test. Removing from end of tree
         */
        String r = s.remove("green");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(rExpected, r);
    }

    @Test
    public final void testRemoveToNonEmpty2() {
        /*
         * Set up variables. A tree with both left and right subtrees.
         */
        Set<String> s = this.createFromArgsTest("Mark", "Matt", "Brett",
                "Aaron", "Craig");
        Set<String> sExpected = this.createFromArgsRef("Mark", "Matt", "Aaron",
                "Craig");
        String rExpected = "Brett";
        /*
         * Call method under Test. Removing from middle of tree.
         */
        String r = s.remove("Brett");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(rExpected, r);
    }

    @Test
    public final void testRemoveToNonEmpty3() {
        /*
         * Set up variables. A tree with only left subtree
         */
        Set<String> s = this.createFromArgsTest("scissors", "rock", "paper");
        Set<String> sExpected = this.createFromArgsRef("scissors", "rock");
        String rExpected = "paper";
        /*
         * Call method under Test. Removing from end of tree.
         */
        String r = s.remove("paper");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(rExpected, r);
    }

    @Test
    public final void testRemoveToNonEmpty4() {
        /*
         * Set up variables. A tree with only right subtree
         */
        Set<String> s = this.createFromArgsTest("plot", "precise", "pronto");
        Set<String> sExpected = this.createFromArgsRef("precise", "pronto");
        String rExpected = "plot";
        /*
         * Call method under Test. Removing from beginning of tree.
         */
        String r = s.remove("plot");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(rExpected, r);
    }

    @Test
    public final void testRemoveToNonEmpty5() {
        /*
         * Set up variables. A tree with only right subtree
         */
        Set<String> s = this.createFromArgsTest("merry", "great", "umbrella",
                "eager", "funny", "tuscan", "variety", "yellow");
        Set<String> sExpected = this.createFromArgsRef("merry", "great",
                "eager", "funny", "tuscan", "variety", "yellow");
        String rExpected = "umbrella";
        /*
         * Call method under Test. Removing from end of tree.
         */
        String r = s.remove("umbrella");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(rExpected, r);
    }

    @Test
    public final void testRemoveToNonEmpty6() {
        /*
         * Set up variables. A tree with only left subtree
         */
        Set<String> s = this.createFromArgsTest("thanks", "success", "romeo",
                "quit", "pigmant", "out", "note");
        Set<String> sExpected = this.createFromArgsRef("success", "romeo",
                "quit", "pigmant", "out", "note");
        String rExpected = "thanks";
        /*
         * Call method under Test. Removing from beginning of tree.
         */
        String r = s.remove("thanks");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(rExpected, r);
    }

    @Test
    public final void testRemoveAnyToEmpty() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("coal");
        Set<String> sExpected = this.createFromArgsRef();
        String rExpected = "coal";
        /*
         * Call method under Test
         */
        String r = s.removeAny();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(rExpected, r);
    }

    @Test
    public final void testRemoveAnyToNonEmpty1() {
        /*
         * Set up variables. Both left and right subtrees.
         */
        Set<String> s = this.createFromArgsTest("I", "bleed", "scarlet", "and",
                "grey");
        Set<String> sExpected = this.createFromArgsRef("I", "bleed", "scarlet",
                "and", "grey");
        /*
         * Call method under Test
         */
        String r = s.removeAny();
        String rExpected = sExpected.remove(r);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(rExpected, r);
    }

    @Test
    public final void testRemoveAnyToNonEmpty2() {
        /*
         * Set up variables. Both left and right subtrees.
         */
        Set<String> s = this.createFromArgsTest("art", "any", "bath", "acting",
                "all", "call");
        Set<String> sExpected = this.createFromArgsRef("art", "any", "bath",
                "acting", "all", "call");
        /*
         * Call method under Test
         */
        String r = s.removeAny();
        String rExpected = sExpected.remove(r);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(rExpected, r);
    }

    @Test
    public final void testRemoveAnyToNonEmpty3() {
        /*
         * Set up variables. A Tree with all right subtrees
         */
        Set<String> s = this.createFromArgsTest("hotel", "jack", "money",
                "parking", "stop", "victory");
        Set<String> sExpected = this.createFromArgsRef("hotel", "jack",
                "money", "parking", "stop", "victory");
        /*
         * Call method under Test
         */
        String r = s.removeAny();
        String rExpected = sExpected.remove(r);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(rExpected, r);
    }

    @Test
    public final void testRemoveAnyToNonEmpty4() {
        /*
         * Set up variables. A tree with all left subtrees
         */
        Set<String> s = this.createFromArgsTest("zoo", "xylophone", "tango",
                "question", "lollipop", "frozen", "baby");
        Set<String> sExpected = this.createFromArgsRef("zoo", "xylophone",
                "tango", "question", "lollipop", "frozen", "baby");
        /*
         * Call method under Test
         */
        String r = s.removeAny();
        String rExpected = sExpected.remove(r);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(sExpected, s);
        assertEquals(rExpected, r);
    }

    @Test
    public final void testContainsEmptySet() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef();
        /*
         * Call method and Assert that values of variables match expectations
         */
        assertEquals(false, s.contains("great"));
        assertEquals(sExpected, s);
    }

    @Test
    public final void testContainsTrue() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("reach", "for", "the", "stars");
        Set<String> sExpected = this.createFromArgsRef("reach", "for", "the",
                "stars");
        /*
         * Call method and Assert that values of variables match expectations
         */
        assertEquals(true, s.contains("stars"));
        assertEquals(sExpected, s);
    }

    @Test
    public final void testContainsFalse() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest("The", "Ohio", "State",
                "University");
        Set<String> sExpected = this.createFromArgsRef("The", "Ohio", "State",
                "University");
        /*
         * Call method and Assert that values of variables match expectations
         */
        assertEquals(false, s.contains("*ichigan"));
        assertEquals(sExpected, s);
    }

    @Test
    public final void testSizeEmpty() {
        /*
         * Set up variables
         */
        Set<String> s = this.createFromArgsTest();
        Set<String> sExpected = this.createFromArgsRef();
        /*
         * Call method and Assert that values of variables match expectations
         */
        assertEquals(0, s.size());
        assertEquals(sExpected, s);
    }

    @Test
    public final void testSize1() {
        /*
         * Set up variables. Tree size one.
         */
        Set<String> s = this.createFromArgsTest("Buckeyes");
        Set<String> sExpected = this.createFromArgsRef("Buckeyes");
        /*
         * Call method and Assert that values of variables match expectations
         */
        assertEquals(1, s.size());
        assertEquals(sExpected, s);
    }

    @Test
    public final void testSize2() {
        /*
         * Set up variables. Tree size multiple.
         */
        Set<String> s = this.createFromArgsTest("The", "Ohio", "State",
                "University");
        Set<String> sExpected = this.createFromArgsRef("The", "Ohio", "State",
                "University");
        /*
         * Call method and Assert that values of variables match expectations
         */
        assertEquals(4, s.size());
        assertEquals(sExpected, s);
    }
}
