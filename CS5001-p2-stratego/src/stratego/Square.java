package stratego;

import stratego.pieces.Piece;

/**
 * Square is the class which represents the squares on the board of the game.
 */
public class Square {
    private Game game;
    private int row, col;
    private boolean isWater;
    private Piece piece;

    /**
     * Constructor method of the class which feed the following values to a Square object.
     * It also adds the created square to the list of all squares.
     *
     * @param game    is the game instance which can be null as well.
     * @param row     is the int value of the row of the square on the board.
     * @param col     is the int value of the column of the square on the board.
     * @param isWater is the boolean value which states if the square has water or not.
     */
    public Square(Game game, int row, int col, boolean isWater) {
        this.game = game;
        this.row = row;
        this.col = col;
        this.isWater = isWater;
        Game.getAllSquares().add(this);
    }

    /**
     * placePiece takes in the piece object and places it in this square.
     *
     * @param piece is the instance of Piece.
     * @throws IllegalArgumentException if the argument passed is not valid.
     */
    public void placePiece(Piece piece) throws IllegalArgumentException {
        if (this.canBeEntered()) {
            this.piece = piece;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * removePiece removes the pieces from this square.
     * It also removes the same from the list of all squares as well.
     */
    public void removePiece() {
        this.piece = null;
        Game.getAllSquares().remove(this);
    }

    /**
     * getGame is the getter method of the instance of Game which is attribute of this class.
     *
     * @return game which can also be null.
     */
    public Game getGame() {
        return game;
    }

    /**
     * getPiece is the getter method of the instance of Piece which is attribute of this class.
     *
     * @return piece is the type of Piece.
     */
    public Piece getPiece() {
        return this.piece;
    }

    /**
     * setPiece is the setter method of the piece attribute.
     * It also adds this square to the list of squares when called.
     *
     * @param piece is the instance of Piece class.
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
        Game.getAllSquares().add(this);
    }

    /**
     * getIsWater is a public method which says if this square has water in it or not.
     *
     * @return isWater which is boolean value.
     */
    public boolean getIsWater() {
        return isWater;
    }

    /**
     * getRow is a getter method of the row attribute.
     *
     * @return int value of row.
     */
    public int getRow() {
        return this.row;
    }

    /**
     * getCol is the getter method for the col attribute.
     *
     * @return col which is of int value of column.
     */
    public int getCol() {
        return this.col;
    }

    /**
     * canBeEntered is a public method which says if this square can be entered by a piece or not.
     *
     * @return boolean value whether the square can be entered or not.
     */
    public boolean canBeEntered() {
        return this.piece == null && !isWater ? true : false;
    }
}
