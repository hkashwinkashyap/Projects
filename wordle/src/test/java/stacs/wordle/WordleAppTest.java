package stacs.wordle;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class WordleAppTest {
    private static ArrayList<String> wordList = new ArrayList<>();

    /**
     * This method is called before all the tests are run and loads the word list
     *
     * @throws FileNotFoundException
     */
    @Test
    @BeforeAll
    public static void shouldLoadWordlist() throws FileNotFoundException {
        assertEquals(0, wordList.size());
        wordList = WordleApp.loadWordList("src/test/resources/wordlist-test.txt");
        assertNotEquals(0, wordList.size());
    }

    /**
     * This method checks the size of the test word list
     */
    @Test
    public void checkTheSizeOfWordList() {
        assertEquals(3, wordList.size());
    }

    /**
     * confirms the exception when the file is not found
     */
    @Test
    public void shouldThrowExceptionWhenFileNotFound() {
        assertThrows(FileNotFoundException.class, () -> {
            WordleApp.loadWordList("src/test/resources/wordlist-test1.txt");
        });
    }

    /**
     * checks if the random word is in the word list
     */
    @Test
    public void shouldGiverandomlyChosenWord() {
        String randomlyChosenWord = WordleApp.giveRandomWord(wordList);
        assertTrue(wordList.contains(randomlyChosenWord));
    }

    /**
     * following two methods check the method call for the initiateGame method
     */
    @Test
    public void initiateGameMethodCallTest() {
        assertEquals(false, WordleApp.initiateGame(false, 0, wordList));
    }

    @Test
    public void initiateGameMethodCallTest2() {
        assertEquals(false, WordleApp.initiateGame(true, 0, wordList));
    }

    /**
     * following two methods check the correctness of the guess
     */
    @Test
    public void shouldGiveExactWord() {
        String randomlyChosenWord = WordleApp.giveRandomWord(wordList);
        String userInput = randomlyChosenWord;
        String exactWordOrNot = WordleApp.exactWordOrNot(userInput, randomlyChosenWord);
        assertEquals("Yes", exactWordOrNot);
    }

    @Test
    public void shouldNotGiveExactWord() {
        String userInput = WordleApp.giveRandomWord(wordList);
        String randomlyChosenWord = userInput.substring(0, 4) + "a";
        String exactWordOrNot = WordleApp.exactWordOrNot(userInput, randomlyChosenWord);
        assertEquals("No", exactWordOrNot);
    }

    /**
     * following three methods check the score for the guess method thoroughly
     */
    @Test
    public void shouldGiveScoreForGuess() {
        String randomlyChosenWord = WordleApp.giveRandomWord(wordList);
        String userInput = randomlyChosenWord;
        int score = WordleApp.getScoreForGuess(userInput, randomlyChosenWord);
        assertEquals(0, score);
    }

    @Test
    public void shouldGiveScoreForGuessingOneAlphabetWrong() {
        String randomlyChosenWord = WordleApp.giveRandomWord(wordList);
        String userInput = randomlyChosenWord.substring(0, 3) + "t";
        int score = WordleApp.getScoreForGuess(userInput, randomlyChosenWord);
        assertEquals(5, score);
    }

    @Test
    public void shouldGiveScoreForGuessingOneAlphabetRight() {
        String randomlyChosenWord = WordleApp.giveRandomWord(wordList);
        String userInput = "f" + randomlyChosenWord.charAt(1) + "ijk";
        int score = WordleApp.getScoreForGuess(userInput, randomlyChosenWord);
        assertEquals(20, score);
    }

    /**
     * following three methods check the feedback for the guess
     */
    @Test
    public void shouldGiveFeedback() {
        String randomlyChosenWord = WordleApp.giveRandomWord(wordList);
        String userInput = randomlyChosenWord.substring(0, 4) + "k";
        String feedback = WordleApp.getFeedback(userInput, randomlyChosenWord);
        assertEquals(WordleApp.BLUE_BACKGROUND_FOR_TERMINAL + " " + userInput.charAt(0) + " " + WordleApp.BACKGROUND_FOR_TERMINAL_RESET + " " +
                WordleApp.BLUE_BACKGROUND_FOR_TERMINAL + " " + userInput.charAt(1) + " " + WordleApp.BACKGROUND_FOR_TERMINAL_RESET + " " +
                WordleApp.BLUE_BACKGROUND_FOR_TERMINAL + " " + userInput.charAt(2) + " " + WordleApp.BACKGROUND_FOR_TERMINAL_RESET + " " +
                WordleApp.BLUE_BACKGROUND_FOR_TERMINAL + " " + userInput.charAt(3) + " " + WordleApp.BACKGROUND_FOR_TERMINAL_RESET + " " +
                WordleApp.RED_BACKGROUND_FOR_TERMINAL + " " + userInput.charAt(4) + " " + WordleApp.BACKGROUND_FOR_TERMINAL_RESET + " ", feedback);
    }

    @Test
    public void shouldCheckAndPrintFeedback() {
        String randomlyChosenWord = WordleApp.giveRandomWord(wordList);
        String userInput = randomlyChosenWord.substring(0, 4) + "z";
        String feedback = WordleApp.checkAndPrintFeedback(userInput, randomlyChosenWord);
        assertEquals("Correctness of your guess: " + WordleApp.getFeedback(userInput, randomlyChosenWord), feedback);
    }

    @Test
    public void shouldCheckAndPrintFeedbackGameOver() {
        String randomlyChosenWord = WordleApp.giveRandomWord(wordList);
        String userInput = randomlyChosenWord;
        String feedback = WordleApp.checkAndPrintFeedback(userInput, randomlyChosenWord);
        assertEquals("Game Over", feedback);
    }

    /**
     * following three methods check continue to complete the game method with different inputs
     */
    @Test
    public void continueToCompleteGameWith1ChanceTest() {
        String randomlyChosenWord = WordleApp.giveRandomWord(wordList);
        String userInput = randomlyChosenWord.substring(0, 4) + "z";
        assertEquals(true, WordleApp.continueToCompleteTheGame(userInput, randomlyChosenWord, 50, 1));
    }

    @Test
    public void continueToCompleteGameWithNoChancesTest() {
        String randomlyChosenWord = WordleApp.giveRandomWord(wordList);
        String userInput = randomlyChosenWord;
        assertEquals(false, WordleApp.continueToCompleteTheGame(userInput, randomlyChosenWord, 50, 0));
    }

    @Test
    public void continueToCompleteGameWithMoreThan1ChanceTest() {
        String randomlyChosenWord = WordleApp.giveRandomWord(wordList);
        String userInput = randomlyChosenWord.substring(0, 4) + "z";
        assertEquals(true, WordleApp.continueToCompleteTheGame(userInput, randomlyChosenWord, 380, 5));
    }

    /**
     * checks the method call if the user wants to play again
     */
    @Test
    public void userDoesntWantToPlayAgainTest() {
        assertEquals(false, WordleApp.doesTheUserWantsToContinue(false));
    }

    /**
     * checks if the game quits as expected
     */
    @Test
    public void quitGameTest() {
        WordleApp.startNewGame(false);
        assertEquals(false, WordleApp.isGameOn());
    }

    /**
     * checks for un-acceptable inputs and handling them
     */
    @Test
    public void notAValidInputTest() {
        assertEquals(false, WordleApp.isValidInput("a 12c"));
        assertEquals(false, WordleApp.isValidInput("1/;'asd"));
    }

    /**
     * following three methods check the game over method
     */
    @Test
    public void isGameOverTrueTest() {
        String randomlyChosenWord = WordleApp.giveRandomWord(wordList);
        String userInput = randomlyChosenWord;
        assertEquals(true, WordleApp.isGameOver(userInput, randomlyChosenWord, 380, 5));
    }

    @Test
    public void isGameOverFalseTest() {
        String randomlyChosenWord = WordleApp.giveRandomWord(wordList);
        String userInput = randomlyChosenWord.substring(0, 4) + "z";
        assertEquals(false, WordleApp.isGameOver(userInput, randomlyChosenWord, 380, 4));
    }

    @Test
    public void isGameOverTrueNoMoreChancesTest() {
        String randomlyChosenWord = WordleApp.giveRandomWord(wordList);
        String userInput = randomlyChosenWord.substring(0, 4) + "z";
        assertEquals(true, WordleApp.isGameOver(userInput, randomlyChosenWord, 50, 0));
    }
}
