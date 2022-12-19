package stratego.pieces;

import stratego.CombatResult;
import stratego.Player;
import stratego.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * Flag is a Piece extending ImmobilePiece.
 */
public class Flag extends ImmobilePiece {
    /**
     * Constructor which calls the super class' constructor.
     *
     * @param owner  instance of the Player who owns this piece.
     * @param square instance of the Square where this piece is in.
     */
    public Flag(Player owner, Square square) {
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
     * Overrides the abstract method in the super class.
     *
     * @return always nothing as this Piece cannot attack.
     */
    @Override
    public List<Square> getLegalAttacks() {
        List<Square> noMoves = new ArrayList<Square>();
        noMoves.clear();
        return noMoves;
    }

    /**
     * this method when called, the opponent player wins as per the rules of the game.
     */
    @Override
    public void beCaptured() {
        this.getOwner().setGameResult(CombatResult.LOSE);
    }
}
