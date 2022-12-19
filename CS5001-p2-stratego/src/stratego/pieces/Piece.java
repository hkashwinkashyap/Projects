package stratego.pieces;

import stratego.CombatResult;
import stratego.Player;
import stratego.Square;

import java.util.List;

/**
 * Piece is an abstract class which has two abstract methods along with concrete methods.
 */
public abstract class Piece {
    private Player owner;
    private Square square;
    private int rank;

    /**
     * Constructor of the class which also calls a method of Square class to connect the piece in the square.
     *
     * @param owner  instance of the Player who owns this piece.
     * @param square is an object of Square class.
     * @param rank   is an int of the rank of the piece.
     */
    public Piece(Player owner, Square square, int rank) {
        this.owner = owner;
        this.square = square;
        this.rank = rank;
        this.square.setPiece(this);
    }

    /**
     * Constructor overloading with just two parameters.
     *
     * @param owner  is an object of Player.
     * @param square is an object of Square.
     */
    public Piece(Player owner, Square square) {
        this.owner = owner;
        this.square = square;
        this.square.setPiece(this);
    }

    /**
     * abstract method which is defined in the subclasses which inherit this class.
     *
     * @return no return.
     */
    public abstract List<Square> getLegalMoves();

    /**
     * abstract method which should give all the possible attacks of a piece.
     *
     * @return no return.
     */
    public abstract List<Square> getLegalAttacks();

    /**
     * move takes in Square and moves this piece to the given square.
     *
     * @param toSquare is the object of the square where this piece is to be moved.
     */
    public void move(Square toSquare) {
        if (toSquare.canBeEntered()) {
            square.setPiece(null);
            toSquare.setPiece(this);
            this.setSquare(toSquare);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * attack is a public method which takes the object of the square and attacks on the piece placed in that sqaure.
     *
     * @param targetSquare is the object of the square where the attack is to be made.
     */
    public void attack(Square targetSquare) {
        if (resultWhenAttacking(targetSquare.getPiece()) == CombatResult.WIN) {
            targetSquare.getPiece().setSquare(null);
            targetSquare.setPiece(null);
            targetSquare.setPiece(this);
            this.getSquare().setPiece(null);
            this.setSquare(targetSquare);
        } else if (resultWhenAttacking(targetSquare.getPiece()) == CombatResult.LOSE) {
            this.getSquare().setPiece(null);
            this.setSquare(null);
        } else {
            this.beCaptured();
            targetSquare.getPiece().beCaptured();
        }
    }

    /**
     * resultWhenAttacking takes in targetPiece and gives the result when this piece attacks the target piece.
     *
     * @param targetPiece is the object of the target piece.
     * @return CombatResult whether it's a win or lose or a draw.
     */
    public CombatResult resultWhenAttacking(Piece targetPiece) {
        if (targetPiece instanceof Bomb) {
            /**
             * When Miner attacks a Bomb, it wins.
             */
            if (this.getRank() == 3) {
                return CombatResult.WIN;
            } else {
                return CombatResult.DRAW;
            }
        } else if (targetPiece instanceof Flag) {
            targetPiece.getOwner().loseGame();
            return CombatResult.WIN;
        } else if (targetPiece instanceof Spy) {
            return CombatResult.WIN;
        } else if (this instanceof Spy) {
            /**
             * When Marshal attacks Spy, Marshal wins.
             */
            if (targetPiece.getRank() == 10) {
                return CombatResult.WIN;
            } else {
                return CombatResult.LOSE;
            }
        } else if (this.getRank() > targetPiece.getRank()) {
            return CombatResult.WIN;
        } else if (this.getRank() < targetPiece.getRank()) {
            return CombatResult.LOSE;
        } else if (this.getRank() == targetPiece.getRank()) {
            return CombatResult.DRAW;
        }
        return null;
    }

    /**
     * beCaptured is a public method which removes the piece in the square and sets the current square to null.
     */
    public void beCaptured() {
        this.square.removePiece();
        this.setSquare(null);
    }

    /**
     * getOwner is a getter of player attribute of the class.
     *
     * @return owner is the object of Player class.
     */
    public Player getOwner() {
        return this.owner;
    }

    /**
     * getSquare is a getter method of square attribute of the class.
     *
     * @return square is the object of Square class.
     */
    public Square getSquare() {
        return this.square;
    }

    /**
     * setSquare is a setter method of square attribute.
     *
     * @param square is the object of Square which needs to set.
     */
    public void setSquare(Square square) {
        this.square = square;
    }

    /**
     * getRank is the getter method of the rank attribute.
     *
     * @return int value of the rank of the piece.
     */
    public int getRank() {
        return this.rank;
    }
}
