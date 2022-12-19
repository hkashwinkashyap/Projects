import java.util.HashMap;
import java.util.Map;

/**
 * PrintTheCount is a public class with the following attributes which are declared by the constructor.
 * It prints the words and it's counts.
 */
public class PrintTheCount {
    private String[] fileAndWords;
    private int widthOfDisplayColumn;
    private String totalRow;
    private Map<String, Integer> wordCountMap = new HashMap<>();
    private String totalStringValue;
    private String countRow;

    /**
     * PrintTheCount() is the constructor which takes in and declares the following attributes.
     *
     * @param fileAndWords         is the String array which has the filename(path) and the words that should be counted entered by the user.
     * @param widthOfDisplayColumn is the int value of the maximum value of the display column.
     * @param totalRow             is the String value of the total column row element.
     * @param wordCountMap         is the hash map which has the words as the keys and their counts as their values stored in it.
     * @param totalStringValue     is the String value of the sum of all the counts of the words given by the user to count from the text.
     * @param countRow             is the String value of the count column row element.
     */
    public PrintTheCount(String[] fileAndWords, int widthOfDisplayColumn, String totalRow, Map<String, Integer> wordCountMap, String totalStringValue, String countRow) {
        this.fileAndWords = fileAndWords;
        this.widthOfDisplayColumn = widthOfDisplayColumn;
        this.countRow = countRow;
        this.wordCountMap = wordCountMap;
        this.totalStringValue = totalStringValue;
        this.totalRow = totalRow;
    }

    /**
     * printWordsAndCounts prints the words and counts of those words accordingly.
     */
    protected void printWordsAndCounts() {
        for (int i = 1; i < fileAndWords.length; i++) {
            System.out.print("| " + fileAndWords[i]);
            if (widthOfDisplayColumn < totalRow.length()) {
                for (int j = 0; j < totalRow.length() - fileAndWords[i].length() - 1; j++) {
                    System.out.print(" ");
                }
            } else {
                for (int j = 0; j < widthOfDisplayColumn - fileAndWords[i].length() - 1; j++) {
                    System.out.print(" ");
                }
            }
            System.out.print("| ");
            if (totalStringValue.length() < countRow.length()) {
                for (int j = 0; j < countRow.length() - 1 - wordCountMap.get(fileAndWords[i]).toString().length() - 1; j++) {
                    System.out.print(" ");
                }
            } else {
                for (int j = 0; j < totalStringValue.length() - 1 - wordCountMap.get(fileAndWords[i]).toString().length() - 1; j++) {
                    System.out.print(" ");
                }
            }
            System.out.print(wordCountMap.get(fileAndWords[i]) + " |");
            System.out.println();
        }
    }
}
