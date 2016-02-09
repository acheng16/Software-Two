import java.util.Comparator;

import components.map.Map;
import components.map.Map.Pair;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Program that reads in a text file and outputs an HTML file with each words'
 * occurrence in the text file.
 * 
 * @author Andrew Cheng
 * 
 */

public class WordCounter {

    /**
     * Makes a HTML page that pulls from a {@code Map} of words and its number
     * of occurrences
     * 
     * @param outFile
     *            the output text file we are writing to with
     *            {@code SimpleWriter}
     * @param words
     *            the {@code Map} containing all the words and its occurrences
     *            in the input text file
     * @param keyQueue
     *            the {@code Queue} containing the {@code words} 's keys
     * @param inFile
     *            the input text file read in by {@code SimpleReader}
     * @clears {@code keyQueue}, {@code words}
     * @ensures <pre>
     * {@code HTML code table elements = entries(word)}
     * </pre>
     * 
     */
    public static void tableMaker(SimpleWriter outFile,
            Map<String, Integer> words, Queue<String> keyQueue,
            SimpleReader inFile) {
        assert words != null : "Violation of: words is not null";
        assert keyQueue != null : "Violation of: keyQueue is not null";
        assert outFile.isOpen() : "Violation of: outFile is open";
        assert inFile.isOpen() : "Violation of: inFile is open";

        //WebPage Title
        outFile.println("<html>");
        outFile.println("<head>");
        outFile.println("<title>Words Counted in " + inFile.name() + "</title>");

        outFile.println("<body>");
        //Header
        outFile.println("<h2>Words Counted in " + inFile.name() + "</h2>");
        outFile.println("<hr />");
        //Start of Table
        outFile.println("<table border=\"1\">");
        outFile.println("<tr>");
        outFile.println("<th>Words</th>");
        outFile.println("<th>Counts</th>");
        outFile.println("</tr>");
        //Loops through the Map, writing out the Keys and its respective Values
        while (words.iterator().hasNext()) {
            Pair<String, Integer> tempPair = words.remove(keyQueue.dequeue());
            outFile.println("<tr>");
            outFile.println("<td>" + tempPair.key() + "</td>");
            outFile.println("<td>" + tempPair.value() + "</td>");
            outFile.println("</tr>");
        }
        outFile.println("</table>");
        outFile.println("</body>");
        outFile.println("</html>");

    }

    /**
     * Compare {@code String}s in lexicographic order. Regardless of case.
     */
    private static class StringLT implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            return o1.toLowerCase().compareTo(o2.toLowerCase());
        }
    }

    /**
     * Generates the set of characters in the given {@code String} into the
     * given {@code Set}.
     * 
     * @param str
     *            the given {@code String}
     * @param strSet
     *            the {@code Set} to be replaced
     * @replaces {@code strSet}
     * @ensures <pre>
     * {@code strSet = entries(str)}
     * </pre>
     */
    private static void generateElements(String str, Set<Character> strSet) {
        assert str != null : "Violation of: str is not null";
        assert strSet != null : "Violation of: strSet is not null";

        strSet.clear();
        for (int i = 0; i < str.length() - 1; i++) {

            strSet.add(str.charAt(i));

        }

    }

    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code separators}) or "separator string" (maximal length string of
     * characters in {@code separators}) in the given {@code text} starting at
     * the given {@code position}.
     * 
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @param separators
     *            the {@code Set} of separator characters
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires <pre>
     * {@code 0 <= position < |text|}
     * </pre>
     * @ensures <pre>
     * {@code nextWordOrSeparator =
     *   text[position, position + |nextWordOrSeparator|)  and
     * if entries(text[position, position + 1)) intersection separators = {}
     * then
     *   entries(nextWordOrSeparator) intersection separators = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      intersection separators /= {})
     * else
     *   entries(nextWordOrSeparator) is subset of separators  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      is not subset of separators)}
     * </pre>
     */
    private static String nextWordOrSeparator(String text, int position,
            Set<Character> separators) {
        assert text != null : "Violation of: text is not null";
        assert separators != null : "Violation of: separators is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position < text.length() : "Violation of: position < |text|";

        int i = position;

        if (!separators.contains(text.charAt(position))) {
            while (i < text.length() && !separators.contains(text.charAt(i))) {
                i++;
            }
        } else {

            while (i < text.length() && separators.contains(text.charAt(i))) {
                i++;
            }

        }
        return text.substring(position, i);
    }

    /**
     * Processing through the input textFile ({@code SimpleReader}) and assigns
     * the word and its occurrences in a {@code Map}.
     * 
     * @param inFile
     *            the input text file ({@code SimpleReader})
     * @param words
     *            the {@code Map} containing all the words and its occurrences
     * @updates {@code words}
     * @ensures <pre>
     * inFile's words = {@code Map}'s Key(words) and Value(occurrences)
     * </pre>
     */
    public static void wordProcess(SimpleReader inFile,
            Map<String, Integer> words) {

        assert inFile.isOpen() : "Violation of : inFile is open";
        assert words != null : "Violation of: words is not null";

        words.clear();

        //Starting position
        int position = 0;

        Set<Character> exclusionSet = new Set1L<Character>();

        //Characters that are considering separators
        String exclusionCharacters = " \t,.-;'/\"@#$%&()";

        generateElements(exclusionCharacters, exclusionSet);

        //Loops until all lines have been read from input text file
        while (!inFile.atEOS()) {

            String line = inFile.nextLine();
            position = 0;
            //Loops through a line from the text file until all characters have been considered
            while (position < line.length()) {
                String token = nextWordOrSeparator(line, position, exclusionSet);
                //if the token is a word
                if (!exclusionSet.contains(token.charAt(0))) {
                    //if the map does not have the token word, add to Map
                    if (!words.hasKey(token)) {
                        words.add(token, 1);
                    }
                    //increase value of its respective key when key is found within the map.
                    else {
                        int wordValue = words.value(token);
                        wordValue++;
                        words.replaceValue(token, wordValue);
                    }
                }
                //Increase position to consider the next word or separator in the line.
                position += token.length();
            }

        }

    }

    /**
     * Sorts through the {@code Map} using {@code Queue}'s sorting function
     * 
     * @param words
     *            the {@code Map} containing all the words and its occurrences
     * @param order
     *            the {@code Comparator} that allows us to compare words
     *            lexicographically ignoring case
     * @param keyQueue
     *            the {@code Queue} that will contain all the words sorted.
     * @ensures <pre>
     * {@code Map} of words is in order based on the {@code Queue} keyQueue.
     * </pre>
     * 
     */

    public static void wordSort(Map<String, Integer> words,
            Comparator<String> order, Queue<String> keyQueue) {
        assert words != null : "Violation of : words is not null";
        assert order != null : "Violation of : words is not null";
        assert keyQueue != null : "Violation of : words is not null";

        Map<String, Integer> tempMap = words.newInstance();
        Queue<String> tempQueue = keyQueue.newInstance();
        keyQueue.clear();

        //loops through all elements of words.
        while (words.iterator().hasNext()) {
            //removes one by one all elements of words until it is empty
            Pair<String, Integer> tempPair = words.removeAny();

            //sets up temporary Map and Queue
            tempMap.add(tempPair.key(), tempPair.value());
            keyQueue.enqueue(tempPair.key());
        }

        //sort the Queue
        keyQueue.sort(order);

        //Loops through all elements of queue adding them to the Map in alphabetical order ignoring the case
        while (keyQueue.iterator().hasNext()) {
            String tempKey = keyQueue.dequeue();
            Pair<String, Integer> orderedPair = tempMap.remove(tempKey);
            words.add(orderedPair.key(), orderedPair.value());
            tempQueue.enqueue(tempKey);
        }
        keyQueue.transferFrom(tempQueue);

    }

    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();
        SimpleReader in = new SimpleReader1L();

        Map<String, Integer> words = new Map1L<String, Integer>();
        Comparator<String> order = new StringLT();
        Queue<String> keyQueue = new Queue1L<String>();

        out.println("Please enter the name of the input file: ");
        //Reads in file
        SimpleReader inFile = new SimpleReader1L("data/" + in.nextLine());

        out.println("Please enter the name of the output file: ");
        //Creates output file
        SimpleWriter outFile = new SimpleWriter1L("data/" + in.nextLine());

        wordProcess(inFile, words);
        wordSort(words, order, keyQueue);
        tableMaker(outFile, words, keyQueue, inFile);

        in.close();
        out.close();
        inFile.close();
        outFile.close();

    }

}
