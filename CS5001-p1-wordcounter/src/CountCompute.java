import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * CountCompute is a public class which has one protected method which calculates the count of the given word(s).
 */
public class CountCompute {
    private String[] fileAndWords;
    private Map<String, Integer> wordCountMap = new HashMap<>();

    /**
     * CountCompute is the constructor which takes in and declares the following attributes which are accessed by it's methods.
     *
     * @param fileAndWords is the String array which has the file name and the words to be counted.
     * @param wordCountMap is the Map of words and it's counts as key-value pairs.
     */
    public CountCompute(String[] fileAndWords, Map<String, Integer> wordCountMap) {
        this.fileAndWords = fileAndWords;
        this.wordCountMap = wordCountMap;
    }

    /**
     * computeCount() is a protected method which scans each and every word in the given text file and returns a map which has the words and it's counts.
     *
     * @return wordCountMap which is a hash map containing the words and it's counts as key-value pairs.
     * @throws FileNotFoundException which is handled by its caller.
     */
    protected Map<String, Integer> computeCount() throws FileNotFoundException {
        /**
         * textFile is an instance of File class which takes in the filename.
         */
        File textFile = new File(fileAndWords[0]);
        Scanner textScanner = new Scanner(textFile);
        /**
         * wordCountMap stores the word and the count of times that word has appeared.
         * Putting all the words that are to counted into the map.
         */
        for (int i = 1; i < fileAndWords.length; i++) {
            wordCountMap.put(fileAndWords[i], 0);
        }
        /**
         * Looping through the text file and splitting each line into words by specifying the pattern in the regex(Regular Expressions).
         * Now mapping these words in each line to the required words and update its count.
         */
        while (textScanner.hasNextLine()) {
            String scannedLine = textScanner.nextLine();
            String[] scannedWords = scannedLine.split("[^(_A-Za-z0-9)]");
            for (String singleWord : scannedWords) {
                if (Arrays.asList(fileAndWords).contains(singleWord)) {
                    wordCountMap.put(singleWord, wordCountMap.get(singleWord) + 1);
                } else {
                    continue;
                }
            }
        }
        textScanner.close();
        return wordCountMap;
    }
}


