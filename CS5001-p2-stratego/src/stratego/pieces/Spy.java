package stratego.pieces;

import stratego.CombatResult;
import stratego.Player;
import stratego.Square;

/**
 * Spy is a Piece which extend StepMover.
 */
public class Spy extends StepMover {
    /**
     * Constructor method which uses the super constructor to initialise.
     *
     * @param owner  object of the Player who owns this piece in the game.
     * @param square instance of Square where this piece stands.
     */
    public Spy(Player owner, Square square) {
        super(owner, square);
    }

    /**
     * when a Spy attacks Marshal, it should win which is the reason this method is overridden here.
     *
     * @param targetPiece is the object of the target piece.
     * @return CombatResult if it has won or not.
     */
    @Override
    public CombatResult resultWhenAttacking(Piece targetPiece) {
        /**
         * rank 10 is Marhal piece.
         */
        if (targetPiece.getRank() == 10) {
            return CombatResult.WIN;
        } else {
            return CombatResult.LOSE;
        }
    }
}
