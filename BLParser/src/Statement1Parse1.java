import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.statement.Statement1;
import components.utilities.Reporter;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary methods {@code parse} and
 * {@code parseBlock} for {@code Statement}.
 * 
 * @author Andrew Cheng and Stacey Frye
 * 
 */
public final class Statement1Parse1 extends Statement1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Converts {@code c} into the corresponding {@code Condition}.
     * 
     * @param c
     *            the condition to convert
     * @return the {@code Condition} corresponding to {@code c}
     * @requires <pre>
     * {@code [c is a condition string]}
     * </pre>
     * @ensures <pre>
     * {@code parseCondition = [Condition corresponding to c]}
     * </pre>
     */
    private static Condition parseCondition(String c) {
        assert c.equals("next-is-empty") || c.equals("next-is-not-empty")
                || c.equals("next-is-enemy") || c.equals("next-is-not-enemy")
                || c.equals("next-is-friend") || c.equals("next-is-not-friend")
                || c.equals("next-is-wall") || c.equals("next-is-not-wall")
                || c.equals("random") || c.equals("true") : ""
                + "Violation of: c is a condition string";
        Condition result;
        if (c.equals("next-is-empty")) {
            result = Condition.NEXT_IS_EMPTY;
        } else if (c.equals("next-is-not-empty")) {
            result = Condition.NEXT_IS_NOT_EMPTY;
        } else if (c.equals("next-is-enemy")) {
            result = Condition.NEXT_IS_ENEMY;
        } else if (c.equals("next-is-not-enemy")) {
            result = Condition.NEXT_IS_NOT_ENEMY;
        } else if (c.equals("next-is-friend")) {
            result = Condition.NEXT_IS_FRIEND;
        } else if (c.equals("next-is-not-friend")) {
            result = Condition.NEXT_IS_NOT_FRIEND;
        } else if (c.equals("next-is-wall")) {
            result = Condition.NEXT_IS_WALL;
        } else if (c.equals("next-is-not-wall")) {
            result = Condition.NEXT_IS_NOT_WALL;
        } else if (c.equals("random")) {
            result = Condition.RANDOM;
        } else { // c.equals("true")
            result = Condition.TRUE;
        }
        return result;
    }

    /**
     * Parses an IF or IF_ELSE statement from {@code tokens} into {@code s}.
     * 
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces {@code s}
     * @updates {@code tokens}
     * @requires <pre>
     * {@code [<"IF"> is a proper prefix of tokens]}
     * </pre>
     * @ensures <pre>
     * {@code if [an if string is a proper prefix of #tokens] then
     *  s = [IF or IF_ELSE Statement corresponding to if string at start of #tokens]  and
     *  #tokens = [if string at start of #tokens] * tokens
     * else
     *  [reports an appropriate error message to the console and terminates client]}
     * </pre>
     */
    private static void parseIf(Queue<String> tokens, Statement s) {
        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0 && tokens.front().equals("IF") : ""
                + "Violation of: <\"IF\"> is proper prefix of tokens";

        String tokenIf = tokens.dequeue();

        Reporter.assertElseFatalError(Tokenizer.isCondition(tokens.front()),
                "Error: Violation of condition after IF not a valid condition");
        Condition conditionIf = parseCondition(tokens.dequeue());

        Reporter.assertElseFatalError(tokens.front().equals("THEN"),
                "Error: Expected THEN, found: " + "\"" + tokens.front() + "\"");
        String thenToken = tokens.dequeue();

        Statement ifStatement = s.newInstance();
        ifStatement.parseBlock(tokens);

        Reporter.assertElseFatalError(tokens.front().equals("ELSE")
                || tokens.front().equals("END"),
                "Error: Expected ELSE or END, found " + tokens.front());

        if (tokens.front().equals("ELSE")) {

            String elseToken = tokens.dequeue();
            Statement elseStatement = s.newInstance();
            elseStatement.parseBlock(tokens);
            s.assembleIfElse(conditionIf, ifStatement, elseStatement);

            Reporter.assertElseFatalError(tokens.front().equals("END"),
                    "Error: Expected END, found: " + "\"" + tokens.front()
                            + "\"");
            String endToken = tokens.dequeue();

        } else {
            s.assembleIf(conditionIf, ifStatement);
            Reporter.assertElseFatalError(tokens.front().equals("END"),
                    "Error: Expected END, found: " + "\"" + tokens.front()
                            + "\"");
            String end = tokens.dequeue();
        }

        String endIfToken = tokens.dequeue();
        Reporter.assertElseFatalError(endIfToken.equals("IF"),
                "Error: Expected IF, found " + "\"" + endIfToken + "\"");

    }

    /**
     * Parses a WHILE statement from {@code tokens} into {@code s}.
     * 
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces {@code s}
     * @updates {@code tokens}
     * @requires <pre>
     * {@code [<"WHILE"> is a proper prefix of tokens]}
     * </pre>
     * @ensures <pre>
     * {@code if [a while string is a proper prefix of #tokens] then
     *  s = [WHILE Statement corresponding to while string at start of #tokens]  and
     *  #tokens = [while string at start of #tokens] * tokens
     * else
     *  [reports an appropriate error message to the console and terminates client]}
     * </pre>
     */
    private static void parseWhile(Queue<String> tokens, Statement s) {
        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0 && tokens.front().equals("WHILE") : ""
                + "Violation of: <\"WHILE\"> is proper prefix of tokens";

        String whileToken = tokens.dequeue();

        Reporter.assertElseFatalError(Tokenizer.isCondition(tokens.front()),
                "Error: Violation of condition after WHILE is not a vlid condition");

        Condition whileCondition = parseCondition(tokens.dequeue());

        Reporter.assertElseFatalError(tokens.front().equals("DO"),
                "Error: Expected DO, found: " + tokens.front());

        String doTokenString = tokens.dequeue();

        Statement whileStatement = s.newInstance();
        whileStatement.parseBlock(tokens);
        s.assembleWhile(whileCondition, whileStatement);

        Reporter.assertElseFatalError(tokens.front().equals("END"),
                "Error: Expected END, found: " + "\"" + tokens.front() + "\"");

        String endWhile = tokens.dequeue();

        Reporter.assertElseFatalError(whileToken.equals("WHILE"),
                "Error: Does not contain While after END");

        whileToken = tokens.dequeue();

    }

    /**
     * Parses a CALL statement from {@code tokens} into {@code s}.
     * 
     * @param tokens
     *            the input tokens
     * @param s
     *            the parsed statement
     * @replaces {@code s}
     * @updates {@code tokens}
     * @requires <pre>
     * {@code [identifier string is a proper prefix of tokens]}
     * </pre>
     * @ensures <pre>
     * {@code s =
     *   [CALL Statement corresponding to identifier string at start of #tokens]  and
     *  #tokens = [identifier string at start of #tokens] * tokens}
     * </pre>
     */
    private static void parseCall(Queue<String> tokens, Statement s) {
        assert tokens != null : "Violation of: tokens is not null";
        assert s != null : "Violation of: s is not null";
        assert tokens.length() > 0 && Tokenizer.isIdentifier(tokens.front()) : ""
                + "Violation of: identifier string is proper prefix of tokens";

        String callIdentifier = tokens.dequeue();
        s.assembleCall(callIdentifier);

    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * Default constructor.
     */
    public Statement1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        Reporter.assertElseFatalError(
                tokens.front().equals("IF") || tokens.front().equals("WHILE")
                        || Tokenizer.isIdentifier(tokens.front()),
                "Error: Expect a IF, WHILE, or Identifier, found: "
                        + tokens.front());

        if (tokens.front().equals("IF")) {
            parseIf(tokens, this);
        } else if (tokens.front().equals("WHILE")) {
            parseWhile(tokens, this);
        } else {
            parseCall(tokens, this);
        }

    }

    @Override
    public void parseBlock(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        Statement newStatement = this.newInstance();

        for (int i = 0; !tokens.front().equals("END")
                && !tokens.front().equals("ELSE")
                && !tokens.front().equals(Tokenizer.END_OF_INPUT); i++) {

            newStatement.parse(tokens);
            this.addToBlock(i, newStatement);

        }

    }

    /*
     * Main test method -------------------------------------------------------
     */

    /**
     * Main method.
     * 
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Get input file name
         */
        out.print("Enter valid BL statement(s) file name: ");
        String fileName = in.nextLine();
        /*
         * Parse input file
         */
        out.println("*** Parsing input file ***");
        Statement s = new Statement1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        s.parse(tokens); // replace with parseBlock to test other method
        /*
         * Pretty print the statement(s)
         */
        out.println("*** Pretty print of parsed statement(s) ***");
        s.prettyPrint(out, 0);

        in.close();
        out.close();
    }

}
