import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Word Counter class has a main function which takes in the filename and the word(s) to be counted in the text file and gives the times those words have been appeared in the given text file.
 *
 * @author Ashwin Kashyap.
 * @return void. It just prints the output in the required format without returning anything.
 */
public class WordCounter {
    /**
     * Main method which takes a String array which takes in the Filename and the Words to be counted.
     *
     * @param args which is passed through
     */
    public static void main(String[] args) {
        /**
         * If the input given does not have the filename and the words to be searched, this gives the required sequence that is accepted.
         */
        if (args.length < 2) {
            System.out.println("Usage: java WordCounter <filename> <searchTerms>");
        }
        /**
         * The else part has a try catch block as the count method in the WordCounterRunner class declares that it throws an exception which is file not found type.
         * The catch block catches if there is any exception and handles it as required.
         * The count method is defined as a static method in the WordCounterRunner class, which is the reason we call it by its class name in contrast to create an object.
         */
        else {
            try {
                count(args);
            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + args[0]);
            }
        }
    }

    /**
     * count is a static method which implements the word counting and also displays it as specified.
     *
     * @param fileAndWords is a String array which has filename and the words.
     * @throws FileNotFoundException is thrown when the file is not available in the specified path.
     */
    public static void count(String[] fileAndWords) throws FileNotFoundException {
        /**
         * textFile is an instance of File class which takes in the filename.
         */
        File textFile = new File(fileAndWords[0]);
        Scanner textScanner = new Scanner(textFile);
        /**
         * wordCountMap stores the word and the count of times that word has appeared.
         */
        Map<String, Integer> wordCountMap = new HashMap<String, Integer>();
        /**
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
        /**
         * Calculating the Total Sum of number of times all the given words appear in the text file
         */
        int total = 0;
        for (int i = 1; i <= wordCountMap.size(); i++) {
            total += wordCountMap.get(fileAndWords[i]);
        }
        String totalStringValue = String.valueOf(total + "  ");
        /**
         * Printing the results in two patterns. One if just one word is counted and the other if more than one word is counted.
         */
        if (wordCountMap.size() < 2) {
            if (wordCountMap.get(fileAndWords[1]) == 1) {
                System.out.println("The word '" + fileAndWords[1] + "' appears " + wordCountMap.get(fileAndWords[1]) + " time.");
            } else {
                System.out.println("The word '" + fileAndWords[1] + "' appears " + wordCountMap.get(fileAndWords[1]) + " times.");
            }
        } else {
            int widthOfDisplayColumn = 0;
            for (int i = 1; i < fileAndWords.length; i++) {
                widthOfDisplayColumn = Math.max(widthOfDisplayColumn, fileAndWords[i].length());
            }
            widthOfDisplayColumn += 2;
            String totalRow = "| TOTAL";
            String countRow = "COUNT |";
            /**
             * Prints Header Border.
             */
            printBorder(widthOfDisplayColumn, totalRow, totalStringValue, countRow);
            /**
             * Prints the Header Row.
             */
            System.out.print("| WORD  ");
            for (int i = 0; i < widthOfDisplayColumn - totalRow.length(); i++) {
                System.out.print(" ");
            }
            System.out.print("| ");
            if (countRow.length() < totalStringValue.length()) {
                for (int i = 0; i < totalStringValue.length() - countRow.length(); i++) {
                    System.out.print(" ");
                }
            }
            System.out.print(countRow);
            System.out.println();
            /**
             * Prints Header Border.
             */
            printBorder(widthOfDisplayColumn, totalRow, totalStringValue, countRow);
            /**
             * Print Words and its count.
             */
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
            /**
             * Prints Footer Border.
             */
            printBorder(widthOfDisplayColumn, totalRow, totalStringValue, countRow);
            /**
             * Prints the Footer Row.
             */
            System.out.print(totalRow);
            if (widthOfDisplayColumn < totalRow.length()) {
                System.out.print(" ");
            } else {
                for (int i = 0; i <= widthOfDisplayColumn - totalRow.length(); i++) {
                    System.out.print(" ");
                }
            }
            System.out.print("| ");
            if (totalStringValue.length() < countRow.length()) {
                for (int i = 0; i < countRow.length() - totalStringValue.length(); i++) {
                    System.out.print(" ");
                }
            } else {
                for (int i = 0; i < totalStringValue.length() - totalStringValue.length(); i++) {
                    System.out.print(" ");
                }
            }
            System.out.print(total + " |");
            System.out.println();
            /**
             * Prints Footer Border.
             */
            printBorder(widthOfDisplayColumn, totalRow, totalStringValue, countRow);
        }
    }

    /**
     * printBorder is a static method which takes in the following parameters and prints the Border of a table as per required.
     *
     * @param widthOfDisplayColumn is the int value of the width of the display column.
     * @param totalRow             is the String of the total row element.
     * @param totalStringValue     is the String value of the total number of all counts.
     * @param countRow             is the String value of the count row element.
     */
    public static void printBorder(int widthOfDisplayColumn, String totalRow, String totalStringValue, String countRow) {
        System.out.print("|");
        if (widthOfDisplayColumn < totalRow.length()) {
            for (int i = 0; i < totalRow.length() - 1; i++) {
                System.out.print("-");
            }
            System.out.print("-");
        } else {
            for (int i = 0; i < widthOfDisplayColumn; i++) {
                System.out.print("-");
            }
        }
        System.out.print("|");
        if (totalStringValue.length() < countRow.length()) {
            for (int i = 0; i < countRow.length(); i++) {
                System.out.print("-");
            }
        } else {
            for (int i = 0; i < totalStringValue.length(); i++) {
                System.out.print("-");
            }
        }
        System.out.print("|");
        System.out.println();
    }
}
