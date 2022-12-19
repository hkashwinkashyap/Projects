package stratego.pieces;

import stratego.CombatResult;
import stratego.Player;
import stratego.Square;

/**
 * Miner is the class of a type of Piece.
 */
public class Miner extends StepMover {
    /**
     * rank of this Piece.
     */
    private int rank = 3;

    /**
     * Constructor method which calls the super class' constructor.
     *
     * @param owner  instance of the Player who owns this piece.
     * @param square object of the Square which this piece is in.
     */
    public Miner(Player owner, Square square) {
        super(owner, square);
    }

    /**
     * this overrides the getter as this has a different constructor.
     *
     * @return int value of the rank of the piece.
     */
    @Override
    public int getRank() {
        return rank;
    }

    /**
     * this overriding method takes in the target square and gives the result when this piece attacks the piece in that square.
     *
     * @param targetPiece is the object of the target piece.
     * @return CombatResult stating the result of the attack.
     */
    @Override
    public CombatResult resultWhenAttacking(Piece targetPiece) {
        if (targetPiece instanceof Bomb || targetPiece instanceof Flag) {
            return CombatResult.WIN;
        } else if (this.getRank() > targetPiece.getRank()) {
            return CombatResult.WIN;
        } else if (this.getRank() < targetPiece.getRank()) {
            return CombatResult.LOSE;
        } else {
            return CombatResult.DRAW;
        }
    }
}
