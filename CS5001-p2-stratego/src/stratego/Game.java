package stratego;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Game is a public class which is instantiated when a game is started.
 */
public class Game {
    /**
     * HEIGHT and WIDTH along with WATER_ROWS and WATER_COLS are declared as public and static as this should belong to the class.
     * HEIGHT is the maximum height of the board.
     */
    public static final int HEIGHT = 10;
    /**
     * WIDTH is the maximum width if the board.
     */
    public static final int WIDTH = 10;
    /**
     * WATER_ROWS has the row numbers of the squares which have water in them.
     */
    public static final int[] WATER_ROWS = {4, 5};
    /**
     * WATER_COLS has the column numbers of the squares which have water in them.
     */
    public static final int[] WATER_COLS = {2, 3, 6, 7};
    /**
     * allSquares is an arraylist which holds all the squares instantiated.
     */
    private static List<Square> allSquares = new ArrayList<>();
    private Player p0;
    private Player p1;

    /**
     * Constructor of the class which instantiates two players of the game.
     *
     * @param p0 is player 0
     * @param p1 is player 1
     */
    public Game(Player p0, Player p1) {
        this.p0 = p0;
        this.p1 = p1;
    }

    /**
     * getter method of the allSquares.
     *
     * @return list of allSquares present.
     */
    public static List<Square> getAllSquares() {
        return allSquares;
    }

    /**
     * getPlayer is a public method which takes in an int and returns the player associated with that number.
     *
     * @param playerNumber is an int which matches one of the player number.
     * @return player object is returned whose number is same as playerNumber.
     * @throws IllegalArgumentException if the argument passed is other than the existing players.
     */
    public Player getPlayer(int playerNumber) throws IllegalArgumentException {
        switch (playerNumber) {
            case 0:
                return p0;
            case 1:
                return p1;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * getWinner returns the player object who is winner of the game.
     *
     * @return player object either p0 or p1. And returns null is none of the players are lost.
     */
    public Player getWinner() {
        return !p0.hasLost() && !p1.hasLost() ? null : (p0.hasLost() ? p1 : p0);
    }

    /**
     * getSquare takes the int values of the row and column, returns if that square exists in the allSquares.
     * If the square is not instantiated, then it creates a new square with the given row and column, adds it to the allSquares and returns the new square.
     *
     * @param row int value of row of the required square.
     * @param col int value of column of the required square.
     * @return square if it exists or returns a new square.
     * @throws IndexOutOfBoundsException if the row or column asked is out of the board boundaries.
     */
    public Square getSquare(int row, int col) throws IndexOutOfBoundsException {
        if (row >= HEIGHT || col >= WIDTH) {
            throw new IndexOutOfBoundsException();
        } else {
            List<Integer> rowList = Arrays.stream(WATER_ROWS).boxed().toList();
            List<Integer> colList = Arrays.stream(WATER_COLS).boxed().toList();
            boolean isWater = false;
            if (rowList.contains(row) && colList.contains(col)) {
                isWater = true;
            }
            for (Square square : allSquares) {
                if (square.getRow() == row && square.getCol() == col) {
                    return square;
                }
            }
            Square newSquare = new Square(this, row, col, isWater);
            return newSquare;
        }
    }
}
