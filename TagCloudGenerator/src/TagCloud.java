import java.util.Comparator;

import components.map.Map;
import components.map.Map.Pair;
import components.map.Map1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachine1L;

/**
 * Program that reads in a text file and outputs an HTML file with each words'
 * occurrence and based on that a unique font Size.
 * 
 * @author Andrew Cheng and Stacey Frye
 * 
 */

/**
 * Returns a negative integer,zero,or a positive integer as the first arguement
 * is less than,equal to, or greater than the second.
 * 
 */
class StringLT implements Comparator<Pair<String, Integer>> {
    /**
     * @param o1
     * 
     * @param o2
     * 
     * @requires String to be a single world
     * 
     * @ensures String with high beginning letter is first
     */
    @Override
    public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
        return o1.key().compareTo(o2.key());
    }
}

/**
 * Returns a -,+ integer, or zero as the first arg that is less than, greater,
 * or equal to than the second.
 * 
 */
class IntegerLT implements Comparator<Pair<String, Integer>> {

    /**
     * @param o1
     * 
     * @param o2
     * 
     * @returns -,+, or zero based on the comparison
     * @ensures Integer with higher value is set first
     */
    @Override
    public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2) {
        return o2.value().compareTo(o1.value());

    }

}

public class TagCloud {

    /**
     * Makes a HTML page that pulls from a {@code Map} of words and creates a
     * tag cloud.
     * 
     * @param outFile
     *            the output text file we are writing to with
     *            {@code SimpleWriter}
     * @param countedWords
     *            the {@code Map} containing all the words and its occurrences
     *            and its fontSize
     * @param sortedWords
     *            the {@code SortingMachine} containing the {@code Pair}s that
     *            are sorted in alphabetical order
     * @param inFile
     *            the input text file read in by {@code SimpleReader}
     * @param num
     *            the number of words to be generated in the tag cloud
     * @clears {@code sortedWords}
     * @ensures <pre>
     * {@code HTML code table elements = entries(countedWords)}
     * </pre>
     * 
     */
    public static void tableMaker(SimpleWriter outFile,
            Map<Pair<String, Integer>, Integer> countedWords,
            SortingMachine<Pair<String, Integer>> sortedWords,
            SimpleReader inFile, int num) {

        //WebPage Title
        outFile.println("<html>");
        outFile.println("<head> ");
        outFile.println("<title>Top " + num + " words in " + inFile.name()
                + "</title>");
        outFile.println("<link href=" + '"' + "doc/tagcloud.css" + '"'
                + " rel=" + '"' + "stylesheet" + '"' + " type =" + '"'
                + "text/css" + '"' + ">");

        outFile.println("</head>");
        outFile.println("<body>");
        //Header
        outFile.println("<h2>Top " + num + " words in " + inFile.name()
                + "</h2>");
        outFile.println("<hr>");

        //TODO: Make a box filled with the words based off their counts
        outFile.println("<div class=\"cdiv\">");

        outFile.println("<p class =" + '"' + "cbox" + '"' + ">");
        sortedWords.changeToExtractionMode();
        while (sortedWords.size() > 0) {
            Pair<String, Integer> pair = sortedWords.removeFirst();
            outFile.println("<span style=" + '"' + "cursor:default" + '"'
                    + " class=" + '"' + 'f'
                    + countedWords.value(pair).toString() + '"' + " title="
                    + '"' + "count: " + pair.value() + '"' + ">" + pair.key()
                    + "</span>");

        }
        outFile.println("</p>");
        outFile.println("</div>");
        outFile.println("</body>");
        outFile.println("</html>");

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
     * @param nWords
     *            the {@code integer} that is the number of words we want to
     *            show
     * @param words
     *            the {@code Map} containing all the words and its occurrences
     * @return sortWords the {@code SortingMachine} that has a Pair that
     *         contains the sorted words' name as key and the number of
     *         occurrences as the value
     * @clears {@code words}
     * @ensures <pre>
     * inFile's words = {@code Map}'s Key(words) and Value(occurrences)
     * </pre>
     */
    public static SortingMachine<Pair<String, Integer>> wordProcess(
            Map<String, Integer> words, int nWords) {

        assert words != null : "Violation of: words is not null";

        Comparator<Map.Pair<String, Integer>> ss = new StringLT();
        Comparator<Map.Pair<String, Integer>> si = new IntegerLT();

        SortingMachine<Map.Pair<String, Integer>> sortCount = new SortingMachine1L<Map.Pair<String, Integer>>(
                si);
        SortingMachine<Map.Pair<String, Integer>> sortWords = new SortingMachine1L<Map.Pair<String, Integer>>(
                ss);
        //SortingMachine count sort
        while (words.size() != 0) {
            Pair<String, Integer> temp = words.removeAny();
            sortCount.add(temp);
        }

        sortCount.changeToExtractionMode();
        //Sorting Machine alphabetical sort
        int size = sortCount.size();
        for (int i = 0; i < size && i < nWords; i++) {
            Pair<String, Integer> temp = sortCount.removeFirst();
            sortWords.add(temp);
        }

        return sortWords;

    }

    /**
     * Assigns the associated font inside the tagcloud.css file as the value to
     * a key of map that contains the words and the count of its appearance.
     * 
     * @param inFile
     *            the file that outputs to HTML
     * @param words
     *            the {@code Map} that contains the words' name and number of
     *            occurrences
     * @restores words
     * @return fontMap the Map that contains a key of map with words' name and
     *         counts and key with its font size reference.
     */
    public static Map<Pair<String, Integer>, Integer> fontMap(
            SimpleReader inFile, Map<String, Integer> words) {
        Map<Pair<String, Integer>, Integer> fontMap = new Map1L<Pair<String, Integer>, Integer>();
        Comparator<Map.Pair<String, Integer>> sorter = new IntegerLT();
        SortingMachine<Map.Pair<String, Integer>> sortCount = new SortingMachine1L<Map.Pair<String, Integer>>(
                sorter);

        words.clear();

        //Starting position
        int position = 0;

        Set<Character> exclusionSet = new Set1L<Character>();

        //Characters that are considering separators
        String exclusionCharacters = " \t,.-:;/\"!?_@#$%&*[]()";

        generateElements(exclusionCharacters, exclusionSet);

        //Loops until all lines have been read from input text file
        while (!inFile.atEOS()) {

            String line = inFile.nextLine();
            //puts all words in lower case
            line = line.toLowerCase();
            position = 0;

            //Loops through a line from the text file until all characters have
            //been considered
            while (position < line.length()) {
                String token = nextWordOrSeparator(line, position, exclusionSet);
                //if the token is a word
                if (!exclusionSet.contains(token.charAt(0))) {
                    //if the map does not have the token word, add to Map
                    if (!words.hasKey(token)) {
                        words.add(token, 1);
                    }

                    //increase value of its respective key when key is found 
                    //within the map.
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

        int fMax = 37;
        double maxCount = 0;
        double minCount = Integer.MAX_VALUE;
        Map<String, Integer> temp = words.newInstance();
        while (words.size() != 0) {
            Pair<String, Integer> removed = words.removeAny();
            double valueRemoved = removed.value().doubleValue();
            if (valueRemoved > maxCount) {
                maxCount = valueRemoved;
            } else if (valueRemoved < minCount) {
                minCount = valueRemoved;
            }
            sortCount.add(removed);
            temp.add(removed.key(), removed.value());
        }
        words.transferFrom(temp);
        sortCount.changeToExtractionMode();
        while (sortCount.size() > 0) {
            int fontSize = 0;
            Pair<String, Integer> removedCount = sortCount.removeFirst();
            int valueCount = removedCount.value();
            if (valueCount > minCount) {
                double font = Math
                        .ceil((fMax * (valueCount - minCount) / (maxCount - minCount)));
                fontSize = (int) font;
                fontSize += 9;
            }
            fontMap.add(removedCount, fontSize);
        }
        return fontMap;

    }

    /**
     * Main method.
     * 
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();
        SimpleReader in = new SimpleReader1L();

        Map<String, Integer> words = new Map1L<String, Integer>();

        out.print("Please enter the name of the input file: ");
        //Reads in file
        SimpleReader inFile = new SimpleReader1L(in.nextLine());

        out.print("Please enter the name of the output file: ");
        //Creates output file
        SimpleWriter outFile = new SimpleWriter1L(in.nextLine());

        //Asks user for the number of words to be generated in the tag cloud
        out.print("Please enter a positive number of words to be generated in "
                + "the tag cloud: ");
        int num = in.nextInteger();

        Map<Pair<String, Integer>, Integer> fontMap = fontMap(inFile, words);
        SortingMachine<Pair<String, Integer>> wordProcessed = wordProcess(
                words, num);
        tableMaker(outFile, fontMap, wordProcessed, inFile, num);

        in.close();
        out.close();
        inFile.close();
        outFile.close();

    }

}
