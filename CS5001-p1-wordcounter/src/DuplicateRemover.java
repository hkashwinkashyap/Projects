import java.util.LinkedHashMap;

/**
 * DuplicateRemover is a public class with a protected method which removes all the duplicate values in passed argument.
 */
public class DuplicateRemover {
    private String[] fileAndWordsWithDuplication;

    /**
     * DuplicateRemover is the constructor which declares the following attributes.
     *
     * @param fileAndWordsWithDuplication is a String array with filename and duplicate words.
     */
    public DuplicateRemover(String[] fileAndWordsWithDuplication) {
        this.fileAndWordsWithDuplication = fileAndWordsWithDuplication;
    }

    /**
     * removeDuplicates() is a protected method which returns a String array of non-duplicate words along with the filename.
     *
     * @return fileAndWords is a String array which has all the non-duplicate words.
     */
    protected String[] removeDuplicates() {
        LinkedHashMap<String, Integer> eliminateDuplicateInputs = new LinkedHashMap<>();
        for (int i = 0; i < fileAndWordsWithDuplication.length; i++) {
            eliminateDuplicateInputs.put(fileAndWordsWithDuplication[i], 0);
        }
        String[] fileAndWords = eliminateDuplicateInputs.keySet().toArray(new String[eliminateDuplicateInputs.size()]);
        return fileAndWords;
    }
}
