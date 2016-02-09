import components.map.Map;
import components.map.Map.Pair;
import components.program.Program;
import components.program.Program1;
import components.queue.Queue;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.utilities.Reporter;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary method {@code parse} for {@code Program}.
 * 
 * @author Andrew Cheng and Stacey Frye
 * 
 */
public final class Program1Parse1 extends Program1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Parses a single BL instruction from {@code tokens} returning the
     * instruction name as the value of the function and the body of the
     * instruction in {@code body}.
     * 
     * @param tokens
     *            the input tokens
     * @param body
     *            the instruction body
     * @return the instruction name
     * @replaces {@code body}
     * @updates {@code tokens}
     * @requires <pre>
     * {@code [<"INSTRUCTION"> is a proper prefix of tokens]}
     * </pre>
     * @ensures <pre>
     * {@code if [an instruction string is a proper prefix of #tokens] then
     *  parseInstruction = [name of instruction at start of #tokens]  and
     *  body = [Statement corresponding to statement string of body of
     *          instruction at start of #tokens]  and
     *  #tokens = [instruction string at start of #tokens] * tokens
     * else
     *  [reports an appropriate error message to the console and terminates client]}
     * </pre>
     */
    private static String parseInstruction(Queue<String> tokens, Statement body) {
        assert tokens != null : "Violation of: tokens is not null";
        assert body != null : "Violation of: body is not null";
        assert tokens.length() > 0 && tokens.front().equals("INSTRUCTION") : ""
                + "Violation of: <\"INSTRUCTION\"> is proper prefix of tokens";

        String[] primitives = { "move", "turnleft", "turnright", "infect",
                "skip" };
        String Instruction = tokens.dequeue();
        Reporter.assertElseFatalError(Instruction.equals("INSTRUCTION"),
                "Error: Keyword" + "\"" + "INSTRUCTION" + "\""
                        + "expected, found: " + "\"" + Instruction + "\"");

        String instructionName = tokens.dequeue();
        for (String element : primitives) {
            Reporter.assertElseFatalError(!element.equals(instructionName),
                    "Error: New instruction name must not be name "
                            + "of primitive instruction " + "\"" + element
                            + "\"");

        }
        String is = tokens.dequeue();
        Reporter.assertElseFatalError(is.equals("IS"), "Error: Keyword" + "\""
                + "IS" + "\"" + "expected, found: " + "\"" + is + "\"");

        body.parseBlock(tokens);

        String end = tokens.dequeue();
        Reporter.assertElseFatalError(end.equals("END"), "Error: Keyword "
                + "\"" + "END" + "\"" + "expected, found: " + "\"" + end + "\"");

        String endInstruction = tokens.dequeue();

        Reporter.assertElseFatalError(instructionName.equals(endInstruction),
                "Error: IDENTIFIER" + "\"" + instructionName + "\""
                        + "at end of instruction" + "\"" + endInstruction
                        + "\"" + "must equal instruction name");

        return instructionName;
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * Default constructor.
     */
    public Program1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(SimpleReader in) {
        assert in != null : "Violation of: in is not null";
        assert in.isOpen() : "Violation of: in.is_open";
        Queue<String> tokens = Tokenizer.tokens(in);
        this.parse(tokens);
    }

    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        Program newProgram = new Program1Parse1();
        String programToken = tokens.dequeue();

        // 1st token should be "PROGRAM"
        Reporter.assertElseFatalError(programToken.equals("PROGRAM"),
                "Error: Keyword \"PROGRAM\" expected, found: \"" + programToken
                        + "\"");

        String programIdentifier = tokens.dequeue();
        String is = tokens.dequeue();

        // Check to for "IS"
        Reporter.assertElseFatalError(is.equals("IS"),
                "Error: Keyword \"IS\" expected, found: \"" + is + "\"");

        //Map contains all Instructions, could be empty.
        Map<String, Statement> context = newProgram.newContext();

        //Either Instruction or Begin
        String instrOrBeginToken = tokens.front();

        while (instrOrBeginToken.equals("INSTRUCTION")) {
            Statement body = newProgram.newBody();
            String instructionName = parseInstruction(tokens, body);
            for (Pair<String, Statement> element : context) {

                Reporter.assertElseFatalError(
                        !element.key().equals(instructionName),
                        "Error: Instruction \"" + instructionName
                                + "\" cannot be already defined");

            }
            context.add(instructionName, body);
            instrOrBeginToken = tokens.front();

        }

        Reporter.assertElseFatalError(instrOrBeginToken.equals("BEGIN"),
                "Error: Keyword \"BEGIN\" expected, found: \""
                        + instrOrBeginToken + "\"");

        instrOrBeginToken = tokens.dequeue();
        Statement programBody = newProgram.newBody();
        programBody.parseBlock(tokens);

        String endToken = tokens.dequeue();

        // Check for "END"
        Reporter.assertElseFatalError(endToken.equals("END"),
                "Error: Keyword \"END\" expected, found: \"" + endToken + "\"");

        String endProgramIdentifier = tokens.dequeue();
        // ID Has to equal
        Reporter.assertElseFatalError(
                endProgramIdentifier.equals(programIdentifier),
                "Error: IDENTIFIER \"" + endProgramIdentifier
                        + "\" at end of instruction \"" + programIdentifier
                        + "\" must eqaul instruction name");

        //Checks for end of program.
        Reporter.assertElseFatalError(
                tokens.front().equals("### END OF INPUT ###"),
                "Error: END-OF-INPUT expected, found: " + "\"" + tokens.front()
                        + "\"");

        this.replaceName(programIdentifier);
        this.replaceBody(programBody);
        this.replaceContext(context);

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
        out.print("Enter valid BL program file name: ");
        String fileName = in.nextLine();
        /*
         * Parse input file
         */
        out.println("*** Parsing input file ***");
        Program p = new Program1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        p.parse(tokens);
        /*
         * Pretty print the program
         */
        out.println("*** Pretty print of parsed program ***");
        p.prettyPrint(out, 0);

        in.close();
        out.close();
    }

}