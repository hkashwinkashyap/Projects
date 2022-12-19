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
            CountTheWords countTheWords = new CountTheWords(args);
            countTheWords.count();
        }
    }
}
