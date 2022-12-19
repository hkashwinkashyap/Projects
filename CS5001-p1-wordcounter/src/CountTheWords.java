import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * CountTheWords is a public class which takes in a String array of filename and words to be counted.
 * Removes the duplicated words, computes their count, and displays them in required format.
 */
public class CountTheWords {
    private String[] fileAndWordsWithDuplication;

    /**
     * CountTheWords is a constructor which declares the String array containing the filename and the words to be counted.
     *
     * @param fileAndWordsWithDuplication is a String array which is passed in by the user to compute the word count from the file given.
     */
    public CountTheWords(String[] fileAndWordsWithDuplication) {
        this.fileAndWordsWithDuplication = fileAndWordsWithDuplication;
    }

    /**
     * count is a protected method which implements the word counting and also displays it as specified.
     */
    protected void count() {
        /**
         * Removes the duplicate words and returns the String array.
         */
        DuplicateRemover duplicateRemover = new DuplicateRemover(fileAndWordsWithDuplication);
        String[] fileAndWords = duplicateRemover.removeDuplicates();

        Map<String, Integer> wordCountMap = new HashMap<>();
        try {
            CountCompute countCompute = new CountCompute(fileAndWords, wordCountMap);
            wordCountMap = countCompute.computeCount();
            int total = 0;
            for (int i = 1; i <= fileAndWords.length - 1; i++) {
                total += wordCountMap.get(fileAndWords[i]);
            }
            String totalStringValue = String.valueOf(total + "  ");
            /**
             * Printing the results in two patterns. One if just one word is counted and the other if more than one word is counted.
             */
            if (wordCountMap.size() < 2) {
                String time = new String();
                try {
                    if (wordCountMap.get(fileAndWords[1]) == 1) {
                        time = " time.";
                    } else {
                        time = " times.";
                    }
                    System.out.println("The word '" + fileAndWords[1] + "' appears " + wordCountMap.get(fileAndWords[1]) + time);
                } catch (NullPointerException e) {
                    System.out.println(e);
                }
            } else {
                int widthOfDisplayColumn = 0;
                for (int i = 1; i < fileAndWords.length; i++) {
                    widthOfDisplayColumn = Math.max(widthOfDisplayColumn, fileAndWords[i].length());
                }
                widthOfDisplayColumn += 2;
                String totalRow = "| TOTAL";
                String countRow = "| COUNT";

                PrintLayout printLayout = new PrintLayout(totalRow, widthOfDisplayColumn, countRow, totalStringValue, total);
                /**
                 * Prints Header Border.
                 */
                printLayout.printBorder();
                /**
                 * Prints the Header Row.
                 */
                printLayout.printHeaderOrFooter(1);
                /**
                 * Prints Header Border.
                 */
                printLayout.printBorder();
                /**
                 * Print Words and its count.
                 */
                PrintTheCount printTheCount = new PrintTheCount(fileAndWords, widthOfDisplayColumn, totalRow, wordCountMap, totalStringValue, countRow);
                printTheCount.printWordsAndCounts();
                /**
                 * Prints Footer Border.
                 */
                printLayout.printBorder();
                /**
                 * Prints the Footer Row.
                 */
                printLayout.printHeaderOrFooter(2);
                /**
                 * Prints Footer Border.
                 */
                printLayout.printBorder();
            }
        } catch (FileNotFoundException f) {
            System.out.println("File not found: " + fileAndWordsWithDuplication[0]);
        }
    }
}
