package stacs.wordle;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class WordleApp {
    /**
     * tells if the game is on or not
     */
    private static boolean gameOn = true;
    /**
     * scanner to take user input
     */
    private static Scanner scanner = new Scanner(System.in);
    /**
     * score of the user
     */
    private static int score;
    /**
     * word list which is loaded from the file
     */
    private static ArrayList<String> wordList;
    /**
     * the string equivalent of ANSI escape codes for terminal to
     * print the background color of the terminal
     * blue for correct letter in correct position
     */
    public static final String BLUE_BACKGROUND_FOR_TERMINAL = "\u001B[44m";
    /**
     * red for letter not in the word
     */
    public static final String RED_BACKGROUND_FOR_TERMINAL = "\u001B[41m";
    /**
     * grey for correct letter in wrong position
     */
    public static final String GREY_BACKGROUND_FOR_TERMINAL = "\u001B[47m";
    /**
     * reset the background color of the terminal
     */
    public static final String BACKGROUND_FOR_TERMINAL_RESET = "\u001B[0m";

    /**
     * Returns if the game is on or not
     *
     * @return boolean
     */
    public static boolean isGameOn() {
        return gameOn;
    }

    /**
     * Starts the game
     *
     * @param args
     */
    public static void main(String[] args) {
        wordList = new ArrayList<>();
        try {
            wordList = loadWordList("src/main/resources/wordlist.txt");
        } catch (FileNotFoundException e) {
            System.out.println("File not found. Please check the path and try again.");
        }
        printStarters();
        boolean gameOver = initiateGame(true, 6, wordList);
        while (gameOn) {
            boolean playAgain = doesTheUserWantsToContinue(gameOver);
            startNewGame(playAgain);
        }
    }

    /**
     * loads the word list from the file and returns the list of words.
     *
     * @param wordListPath
     * @return
     * @throws FileNotFoundException
     */
    protected static ArrayList<String> loadWordList(String wordListPath) throws FileNotFoundException {
        ArrayList<String> wordList = new ArrayList<String>();
        Scanner wordListScanner = new Scanner(new FileReader(wordListPath));
        while (wordListScanner.hasNextLine()) {
            wordList.add(wordListScanner.nextLine());
        }
        return wordList;
    }


    /**
     * Prints how abouts of the game.
     */
    private static void printStarters() {
        System.out.println("Hello! Bored of the same old word games? Try this one out!\n" +
                "This is also an old game, however you just have to pass the time.\n" +
                "So, you have 6 chances to guess the 5 letter word.\n" +
                "And while after every guess, you will be given a hint as follows:\n");
        System.out.println(BLUE_BACKGROUND_FOR_TERMINAL + "   " + BACKGROUND_FOR_TERMINAL_RESET + " means the letter is in the word and in correct position.\n" +
                GREY_BACKGROUND_FOR_TERMINAL + "   " + BACKGROUND_FOR_TERMINAL_RESET + " means the letter is in the word but not in correct position.\n" +
                RED_BACKGROUND_FOR_TERMINAL + "   " + BACKGROUND_FOR_TERMINAL_RESET + " means the letter is not in the word.\n");
    }

    /**
     * initialises the game and takes the user input.
     * and based on the input, calls the appropriate methods.
     *
     * @param gameOn
     * @param numberOfChancesLeft
     * @param wordList
     * @return
     */
    protected static boolean initiateGame(boolean gameOn, int numberOfChancesLeft, ArrayList<String> wordList) {
        score = 380;
        String randomlyChosenWord = giveRandomWord(wordList);
        System.out.println("Start guessing the 5 letter word: ");
        while (gameOn && numberOfChancesLeft > 0) {
            String userInput = scanner.nextLine();
            if (isValidInput(userInput)) {
                numberOfChancesLeft--;
                if (isGameOver(userInput, randomlyChosenWord, score, numberOfChancesLeft)) {
                    return true;
                } else if (continueToCompleteTheGame(userInput, randomlyChosenWord, score, numberOfChancesLeft) == true) {
                    score = computeScoreForTheLevel(score, numberOfChancesLeft, userInput, randomlyChosenWord);
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * returns a random word from the word list.
     *
     * @param wordList
     * @return
     */
    protected static String giveRandomWord(ArrayList<String> wordList) {
        int randomIndex = (int) (Math.random() * wordList.size());
        return wordList.get(randomIndex);
    }

    /**
     * returns a boolean if the user input word is a valid input or not
     * meaning it should be a 5 letter word, and should not contain any numbers, special characters and spaces.
     *
     * @param userInput
     * @return
     */
    protected static boolean isValidInput(String userInput) {
        if (!userInput.matches("^[a-zA-Z]{5}$")) {
            System.out.println("Please enter a 5 letter word.");
            return false;
        } else {
            return true;
        }
    }


    /**
     * if the user guesses the correct word or if the chances are exhausted, the game is over.
     *
     * @param userInput
     * @param randomlyChosenWord
     * @param score
     * @param numberOfChancesLeft
     * @return
     */
    protected static boolean isGameOver(String userInput, String randomlyChosenWord, int score, int numberOfChancesLeft) {
        if (checkAndPrintFeedback(userInput, randomlyChosenWord) == "Game Over") {
            score -= getScoreForGuess(userInput, randomlyChosenWord);
            System.out.println("Aha! You got it!");
            System.out.println("And here is your score: " + score);
            return true;
        } else if (numberOfChancesLeft == 0) {
            System.out.println("Game Over! You have run out of chances.");
            System.out.println("The word was: " + randomlyChosenWord);
            System.out.println("And here is your score: " + score);
            return true;
        } else {
            return false;
        }
    }

    /**
     * if the user input is not the correct word, this method is called.
     *
     * @param userInput
     * @param randomWord
     * @param score
     * @param numberOfChancesLeft
     * @return
     */
    protected static boolean continueToCompleteTheGame(String userInput, String randomWord, int score, int numberOfChancesLeft) {
        System.out.println("Oh no! That isn't the correct word. Try again.");
        System.out.println(checkAndPrintFeedback(userInput, randomWord));
        if (isGameOver(userInput, randomWord, score, numberOfChancesLeft)) {
            return false;
        } else {
            String numberOfChanceOrChances = numberOfChancesLeft == 1 ? " chance" : " chances";
            System.out.println("Don't worry! You have another " + numberOfChancesLeft + numberOfChanceOrChances + " left. Go on: ");
            return true;
        }
    }

    /**
     * computes the score for the level
     *
     * @param currentScore
     * @param numberOfChancesLeft
     * @param userInput
     * @param randomlyChosenWord
     * @return
     */
    protected static int computeScoreForTheLevel(int currentScore, int numberOfChancesLeft, String userInput, String randomlyChosenWord) {
        currentScore--;
        currentScore -= (6 - numberOfChancesLeft) * getScoreForGuess(userInput, randomlyChosenWord);
        return currentScore;
    }

    /**
     * gives the score to be subtracted from the total score based on the user input.
     *
     * @param userInput
     * @param randomWord
     * @return
     */
    protected static int getScoreForGuess(String userInput, String randomWord) {
        int score = 0;
        for (int i = 0; i < userInput.length(); i++) {
            if (userInput.charAt(i) == randomWord.charAt(i)) {
                score += 0;
            } else if (randomWord.contains(String.valueOf(userInput.charAt(i)))) {
                score += 2;
            } else {
                score += 5;
            }
        }
        return score;
    }

    /**
     * checks if the user input correct by calling the exactWordOrNot method.
     * and gives feedback by calling the getFeedback method.
     *
     * @param userInput
     * @param randomWord
     * @return
     */
    protected static String checkAndPrintFeedback(String userInput, String randomWord) {
        String exactWordOrNot = exactWordOrNot(userInput, randomWord);
        if (exactWordOrNot.equals("Yes")) {
            return "Game Over";
        } else {
            return ("Correctness of your guess: " + getFeedback(userInput, randomWord));
        }
    }

    /**
     * checks if the user input is the correct word.
     *
     * @param userInput
     * @param randomWord
     * @return
     */
    protected static String exactWordOrNot(String userInput, String randomWord) {
        String exactWordOrNot = "";
        if (userInput.equals(randomWord)) {
            exactWordOrNot = "Yes";
        } else {
            exactWordOrNot = "No";
        }
        return exactWordOrNot;
    }

    /**
     * gives the color feedback of the word based on the user input.
     *
     * @param userInput
     * @param randomWord
     * @return
     */
    protected static String getFeedback(String userInput, String randomWord) {
        String feedback = "";
        Map<Character, Integer> letterCountOfRandomWord = new HashMap<>();
        for (int i = 0; i < randomWord.length(); i++) {
            if (letterCountOfRandomWord.containsKey(randomWord.charAt(i))) {
                letterCountOfRandomWord.put(randomWord.charAt(i), letterCountOfRandomWord.get(randomWord.charAt(i)) + 1);
            } else {
                letterCountOfRandomWord.put(randomWord.charAt(i), 1);
            }
        }
        Map<Character, Boolean> letterCheckedOfRandomWord = new HashMap<>();
        for (char c : letterCountOfRandomWord.keySet()) {
            letterCheckedOfRandomWord.put(c, false);
        }
        List<Integer> indexOfBlue = new ArrayList<>();
        List<Integer> indexOfGrey = new ArrayList<>();
        List<Integer> indexOfRed = new ArrayList<>();
        for(int i=0; i<userInput.length();i++){
            if(userInput.charAt(i) == randomWord.charAt(i)){
                indexOfBlue.add(i);
                if (letterCountOfRandomWord.get(userInput.charAt(i)) == 1) {
                    letterCheckedOfRandomWord.put(userInput.charAt(i), true);
                    letterCountOfRandomWord.put(userInput.charAt(i), letterCountOfRandomWord.get(userInput.charAt(i)) - 1);
                }
                else {
                    letterCountOfRandomWord.put(userInput.charAt(i), letterCountOfRandomWord.get(userInput.charAt(i)) - 1);
                }
            }
        }
        for(int i=0; i<userInput.length();i++){
            String letter = String.valueOf(userInput.charAt(i));
            if(randomWord.contains(letter) && !letterCheckedOfRandomWord.get(userInput.charAt(i))){
                indexOfGrey.add(i);
                letterCountOfRandomWord.put(userInput.charAt(i), letterCountOfRandomWord.get(userInput.charAt(i)) - 1);
                if (letterCountOfRandomWord.get(userInput.charAt(i)) == 0) {
                    letterCheckedOfRandomWord.put(userInput.charAt(i), true);
                }
            }
            else if(!randomWord.contains(letter)){
                indexOfRed.add(i);
            }
        }
        for (int i = 0; i < userInput.length(); i++) {
            if (indexOfBlue.contains(i)) {
                feedback += BLUE_BACKGROUND_FOR_TERMINAL + " " + userInput.charAt(i) + " " + BACKGROUND_FOR_TERMINAL_RESET + " ";
            } else if (indexOfGrey.contains(i)) {
                feedback += GREY_BACKGROUND_FOR_TERMINAL + " " + userInput.charAt(i) + " " + BACKGROUND_FOR_TERMINAL_RESET + " ";
            } else {
                feedback += RED_BACKGROUND_FOR_TERMINAL + " " + userInput.charAt(i) + " " + BACKGROUND_FOR_TERMINAL_RESET + " ";
            }
        }
        return feedback;
    }

    /**
     * takes a boolean which says if the current game is over or not
     * and returns the user's choice to play again or not
     *
     * @param afterAGame
     * @return boolean
     */
    protected static boolean doesTheUserWantsToContinue(boolean afterAGame) {
        System.out.println("Do you want to play again? Type in (y/n)");
        while (afterAGame) {
            String playAgain = scanner.nextLine();
            if (playAgain.equals("n")) {
                return false;
            } else if (!playAgain.equals("y") && !playAgain.equals("N")) {
                System.out.println("Please enter 'y' if you want to play again or 'n' to quit.");
            } else if (playAgain.equals("y")) {
                return true;
            }
        }
        return false;
    }

    /**
     * takes a boolean which says if the user wants to play again or not
     * and starts or quits the game accordingly
     *
     * @param startNewGame
     */
    protected static void startNewGame(boolean startNewGame) {
        if (startNewGame == true) {
            initiateGame(true, 6, WordleApp.wordList);
        } else {
            System.out.println("Hope you had a fun time! See you later!");
            quitGame();
        }
    }

    /**
     * terminates the game when called.
     */
    protected static void quitGame() {
        gameOn = false;
    }
}
