package stratego.pieces;

import stratego.Player;
import stratego.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * Bomb class which is an ImmobilePiece which in turn extends the Piece class.
 */
public class Bomb extends ImmobilePiece {
    /**
     * Constructor method which uses super class' constructor.
     *
     * @param owner  instance of the Player who owns this piece.
     * @param square instance of the Square it's in.
     */
    public Bomb(Player owner, Square square) {
        super(owner, square);
    }

    /**
     * Overrides the abstract method in the super class.
     *
     * @return always nothing as this Piece cannot move.
     */
    @Override
    public List<Square> getLegalMoves() {
        List<Square> noMoves = new ArrayList<Square>();
        noMoves.clear();
        return noMoves;
    }

    /**
     * Overrides the abstract method in the super class and gives the list of all possible attacks.
     *
     * @return always nothing as this Piece cannot attack.
     */
    @Override
    public List<Square> getLegalAttacks() {
        List<Square> noMoves = new ArrayList<Square>();
        noMoves.clear();
        return noMoves;
    }
}
