import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.TreeMap;

/**
 * Program that reads in a text file and outputs an HTML file with each words'
 * occurrence and based on that a unique font Size.
 * 
 * @author Andrew Cheng and Stacey Frye
 */
public final class TagCloudUsingJava {

    final static double fMax = 37;

    /**
     * Returns a negative integer, zero, or a positive integer as the first
     * argument is less than, equal to, or greater than the second.
     * 
     */
    private static class IntegerCompare implements Comparator<String> {

        Map<String, Integer> compareMap;

        IntegerCompare(Map<String, Integer> compareMap) {
            this.compareMap = compareMap;
        }

        @Override
        public int compare(String str1, String str2) {
            int firstInt = this.compareMap.get(str1);
            int secondInt = this.compareMap.get(str2);
            if (secondInt > firstInt) {
                return 1;
            } else if (firstInt > secondInt) {
                return -1;
            } else {
                return str2.compareTo(str1);
            }
        }
    }

    /**
     * Default constructor--private to prevent instantiation.
     */
    private TagCloudUsingJava() {
    }

    /**
     * Separators
     */
    private static final String SEPARATORS = " \t,.-:;/\"!?_@#$%&*[]()";

    /**
     * Puts all words from a file into a {@code Map}.
     * 
     * @param fileInput
     *            {@code BufferedReader} input stream
     * 
     * 
     *            <pre>
     * {@code @return {@code Map<String,Integer>} containing all words and their counts from input}
     * {@code fileInput} is empty at end
     * </pre>
     * @throws IOException
     */
    public static Map<String, Integer> retrieveWords(BufferedReader fileInput)
            throws IOException {
        assert fileInput.ready() : "Error: FileStream has to be open";
        Map<String, Integer> wordMap = new HashMap<String, Integer>();
        String word = "";
        try {
            String line = fileInput.readLine();
            while (line != null) {
                line = line.toLowerCase();
                int length = line.length();
                for (int i = 0; i < length; i++) {
                    //from lab we did
                    if (!SEPARATORS
                            .contains(Character.toString(line.charAt(i)))) {
                        Character letter = line.charAt(i);
                        word = word.concat(letter.toString());
                    } else if (!word.equals("")) {
                        if (wordMap.containsKey(word)) {
                            int value = wordMap.get(word);
                            value++;
                            wordMap.put(word, value);
                        } else {
                            wordMap.put(word, 1);
                        }
                        word = "";

                    }
                }
                if (!word.equals("")) {
                    if (wordMap.containsKey(word)) {
                        //removes and re-added the the incremented value
                        int value = wordMap.get(word);
                        value++;
                        wordMap.put(word, value);
                    } else {
                        wordMap.put(word, 1);
                    }
                    word = "";
                }
                line = fileInput.readLine();
            }
        } catch (IOException e) {
            System.err.print("Error reading stream from file " + e);
        }
        return wordMap;
    }

    /**
     * Takes a map of words and their counts and sorts the top N most occurring
     * in alphabetical order and returns a Queue containing their corresponding
     * fonts sizes
     * 
     * @param wordMap
     *            {@code Map<String,Integer>} containing all words and their
     *            counts
     * @param nWords
     *            {@code Integer} containing the number of words to display in
     *            tagCloud
     * @param wordSort
     *            A {@code TreeMap} that will sort word entries and their counts
     * 
     * @return A {@code Queue} of the fonts
     * 
     * @updates wordSort
     * @ensures <pre>
     * {@code Queue} fontSizes is proportional to their occurrences and in order.
     * </pre>
     */
    public static Queue<Integer> wordFontSort(Map<String, Integer> wordMap,
            int nWords, Map<String, Integer> wordSort) {

        IntegerCompare ic = new IntegerCompare(wordMap);
        TreeMap<String, Integer> countTree = new TreeMap<String, Integer>(ic);
        Queue<Integer> fontSizes = new LinkedList<Integer>();
        wordSort.clear();

        Set<Entry<String, Integer>> wordSet = wordMap.entrySet();
        //put the map into a set
        for (Entry<String, Integer> entry : wordSet) {
            countTree.put(entry.getKey(), entry.getValue());
        }

        double max = countTree.firstEntry().getValue();
        double min = countTree.lastEntry().getValue();

        int counter = 0;
        while (counter < nWords && !countTree.isEmpty()) {
            Entry<String, Integer> countEntry = countTree.firstEntry();
            String key = countEntry.getKey();
            int value = countEntry.getValue();
            wordSort.put(key, value);
            countTree.remove(key);
            counter++;
        }

        for (Map.Entry<String, Integer> entry : wordSort.entrySet()) {
            int fontSize = 0;
            int currentCount = entry.getValue();
            if (currentCount > min) {
                double font = Math.ceil((fMax * (currentCount - min))
                        / (max - min));
                fontSize = (int) font;
                fontSize += 9;
            }
            fontSizes.add(fontSize);
        }

        return fontSizes;
    }

    /**
     * Creates the HTML code with the header, word name, and appropriate
     * FontSize, the footer.
     * 
     * @param sortWords
     *            {code TreeMap} that contains the top N words sorted
     *            alphabetically with their counts
     * 
     * @param fontSizes
     *            {@code Queue} containing fontSizes retrieved from entries in
     *            TreeMap
     * 
     * 
     * @param output
     *            {@code PrintWrtier}output stream
     * 
     */
    public static void createBody(Queue<Integer> fontSizes,
            TreeMap<String, Integer> sortWords, PrintWriter output,
            String fileLocation, int nWords) {

        assert !fontSizes.isEmpty() : "fontSizes cannot be empty";
        assert !sortWords.isEmpty() : "sortWords cannot be empty";
        assert !fileLocation.equals("") : "fileLocation cannot be an empty string";

        output.println("<html>");
        output.println("<head>");
        output.println("<title>Top " + nWords + " words in data/"
                + fileLocation + "</title>");
        output.println("<link href=" + '"' + "doc/tagcloud.css" + '"' + " rel="
                + '"' + "stylesheet" + '"' + " type =" + '"' + "text/css" + '"'
                + ">");

        output.println("</head>");
        output.println("<body>");
        output.println("<h2>Top " + nWords + " words in " + fileLocation
                + "</h2>");
        output.println("<hr>");
        output.println("<div class = " + '"' + "cdiv" + '"' + ">");
        output.println("<p class =" + '"' + "cbox" + '"' + ">");
        while (sortWords.size() > 0) {
            output.println("<span style=" + '"' + "cursor:default" + '"'
                    + " class=" + '"' + 'f' + fontSizes.remove() + '"'
                    + " title=" + '"' + "count: "
                    + sortWords.firstEntry().getValue() + '"' + ">"
                    + sortWords.firstEntry().getKey() + "</span>");

            sortWords.remove(sortWords.firstEntry().getKey());

        }
        output.println("</p>");
        output.println("</div>");
        output.println("</body>");
        output.println("</html>");
    }

    /**
     * Main method.
     * 
     * @param args
     *            the command line arguments; unused here
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(
                System.in));
        BufferedReader fileInput = null;
        PrintWriter output = null;

        System.out.print("Enter the location of the input file: ");
        String fileLocation = "";
        try {
            fileLocation = input.readLine();
        } catch (IOException e) {
            System.err.println("Error reading stream from system input " + e);
            return;
        }

        try {
            fileInput = new BufferedReader(new FileReader(fileLocation));
        } catch (IOException e) {
            System.err.println("Error opening reader to file location " + e);
            return;
        }

        System.out.print("Enter a name for the output html file: ");
        String fileName = "";
        try {
            fileName = input.readLine();
        } catch (IOException e) {
            System.err.println("Error reading stream from system input " + e);
            return;
        }

        System.out.print("# of words to include in tag cloud: ");
        int nWords = 0;
        try {
            nWords = Integer.parseInt(input.readLine());
        } catch (IOException e) {
            System.err.println("Error reading stream from system input " + e);
            return;
        }

        output = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));

        Map<String, Integer> words = retrieveWords(fileInput);

        TreeMap<String, Integer> sortWords = new TreeMap<String, Integer>();

        Queue<Integer> fontSizes = wordFontSort(words, nWords, sortWords);

        createBody(fontSizes, sortWords, output, fileName, nWords);

        fileInput.close();
        input.close();
        output.close();
    }

}