package stratego.pieces;

import stratego.Player;
import stratego.Square;

/**
 * ImmobilePiece is an abstract class under which the pieces that cannot move come.
 */
public abstract class ImmobilePiece extends Piece {
    /**
     * Constructor using the super class' contrcutor.
     *
     * @param owner  instance of the Player who owns this piece.
     * @param square instance of the Square.
     * @param rank   int value of the rank of the piece.
     */
    public ImmobilePiece(Player owner, Square square, int rank) {
        super(owner, square, rank);
    }

    /**
     * Constructor overloading which also uses the super class' constructor.
     *
     * @param owner  instance of the Player who owns this piece.
     * @param square object of the Square class.
     */
    public ImmobilePiece(Player owner, Square square) {
        super(owner, square);
    }
}
