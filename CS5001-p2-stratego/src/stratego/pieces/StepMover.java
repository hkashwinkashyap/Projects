package stratego.pieces;

import stratego.Game;
import stratego.Player;
import stratego.Square;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * StepMover is the class which is used by the pieces to move through-out the board.
 */
public class StepMover extends Piece {
    /**
     * Constructor method of the class which uses the constructor as it's super class.
     *
     * @param owner  instance of the Player who owns this piece.
     * @param square instance of Square.
     * @param rank   int value of the rank of the piece.
     */
    public StepMover(Player owner, Square square, int rank) {
        super(owner, square, rank);
    }

    /**
     * Constructor overloading with fever required parameters.
     *
     * @param owner  instance of Player.
     * @param square instance of Sqaure.
     */
    public StepMover(Player owner, Square square) {
        super(owner, square);
    }

    /**
     * getSquare method is implemented here for loose coupling this and Game class as game can be null as well.
     *
     * @param row int value of row.
     * @param col int value of column of the square.
     * @return square if it already exits, else returns a new square.
     * @throws IndexOutOfBoundsException if the row or col is given out of the board's boundary.
     */
    private Square getSquare(int row, int col) throws IndexOutOfBoundsException {
        if (row >= Game.HEIGHT || col >= Game.WIDTH) {
            throw new IndexOutOfBoundsException();
        } else {
            List<Integer> rowList = Arrays.stream(Game.WATER_ROWS).boxed().toList();
            List<Integer> colList = Arrays.stream(Game.WATER_COLS).boxed().toList();
            boolean isWater = false;
            if (rowList.contains(row) && colList.contains(col)) {
                isWater = true;
            }
            /**
             * checking if the square already exits or not.
             */
            List<Square> presentSquare = Game.getAllSquares().stream().filter(square -> square.getRow() == row && square.getCol() == col).toList();
            if (presentSquare.size() >= 1) {
                return presentSquare.get(0);
            }
            Square newSquare = new Square(this.getSquare().getGame(), row, col, isWater);
            return newSquare;
        }
    }

    /**
     * overriding as the super class has this method as an abstract method.
     * this method gives all the possible moves that a piece can make according to the rules of the game.
     *
     * @return list of squares to move.
     */
    @Override
    public List<Square> getLegalMoves() {
        int row = this.getSquare().getRow();
        int col = this.getSquare().getCol();
        List<Square> possibleMoveSquares = new ArrayList<>();
        if (row == 0 && col == 0) {
            possibleMoveSquares.add(getSquare(row, col + 1));
            possibleMoveSquares.add(getSquare(row + 1, col));
        } else if (row == Game.HEIGHT - 1 && col == Game.WIDTH - 1) {
            possibleMoveSquares.add(getSquare(row - 1, col));
            possibleMoveSquares.add(getSquare(row, col - 1));
        } else if (row == Game.HEIGHT - 1 && col < Game.WIDTH - 1) {
            possibleMoveSquares.add(getSquare(row - 1, col));
            possibleMoveSquares.add(getSquare(row, col + 1));
            possibleMoveSquares.add(getSquare(row, col - 1));
        } else if (row < Game.HEIGHT - 1 && col == Game.WIDTH - 1) {
            possibleMoveSquares.add(getSquare(row + 1, col));
            possibleMoveSquares.add(getSquare(row, col - 1));
            possibleMoveSquares.add(getSquare(row + 1, col));
        } else if (row != 0 && col == 0 && row < Game.HEIGHT - 1) {
            possibleMoveSquares.add(getSquare(row + 1, col));
            possibleMoveSquares.add(getSquare(row - 1, col));
            possibleMoveSquares.add(getSquare(row, col + 1));
        } else if (row == 0 && col != 0 && col < Game.WIDTH - 1) {
            possibleMoveSquares.add(getSquare(row + 1, col));
            possibleMoveSquares.add(getSquare(row, col - 1));
            possibleMoveSquares.add(getSquare(row, col + 1));
        } else {
            possibleMoveSquares.add(getSquare(row - 1, col));
            possibleMoveSquares.add(getSquare(row + 1, col));
            possibleMoveSquares.add(getSquare(row, col - 1));
            possibleMoveSquares.add(getSquare(row, col + 1));
        }
        for (int i = 0; i < possibleMoveSquares.size(); i++) {
            if (possibleMoveSquares.get(i).getPiece() != null) {
                possibleMoveSquares.remove(possibleMoveSquares.get(i));
            }
            if (!possibleMoveSquares.get(i).canBeEntered() && this.getSquare().getGame() != possibleMoveSquares.get(i).getGame()) {
                possibleMoveSquares.remove(possibleMoveSquares.get(i));
            }
        }
        return possibleMoveSquares;
    }

    /**
     * this is also an abstract method in its super class.
     * it gives all the possible attacks a piece can make at a given instance.
     *
     * @return list of squares to attack.
     */
    @Override
    public List<Square> getLegalAttacks() {
        int row = this.getSquare().getRow();
        int col = this.getSquare().getCol();
        List<Square> possibleAttackSquares = new ArrayList<>();
        if (row == 0 && col == 0) {
            possibleAttackSquares.add(getSquare(row, col + 1));
            possibleAttackSquares.add(getSquare(row + 1, col));
        } else if (row == Game.HEIGHT - 1 && col == Game.WIDTH - 1) {
            possibleAttackSquares.add(getSquare(row - 1, col));
            possibleAttackSquares.add(getSquare(row, col - 1));
        } else if (row == Game.HEIGHT - 1 && col < Game.WIDTH - 1) {
            possibleAttackSquares.add(getSquare(row - 1, col));
            possibleAttackSquares.add(getSquare(row, col + 1));
            possibleAttackSquares.add(getSquare(row, col - 1));
        } else if (row < Game.HEIGHT - 1 && col == Game.WIDTH - 1) {
            possibleAttackSquares.add(getSquare(row + 1, col));
            possibleAttackSquares.add(getSquare(row, col - 1));
            possibleAttackSquares.add(getSquare(row - 1, col));
        } else if (row != 0 && col == 0 && row < Game.HEIGHT - 1) {
            possibleAttackSquares.add(getSquare(row + 1, col));
            possibleAttackSquares.add(getSquare(row - 1, col));
            possibleAttackSquares.add(getSquare(row, col + 1));
        } else if (row == 0 && col != 0 && col < Game.WIDTH - 1) {
            possibleAttackSquares.add(getSquare(row + 1, col));
            possibleAttackSquares.add(getSquare(row, col - 1));
            possibleAttackSquares.add(getSquare(row, col + 1));
        } else {
            possibleAttackSquares.add(getSquare(row - 1, col));
            possibleAttackSquares.add(getSquare(row + 1, col));
            possibleAttackSquares.add(getSquare(row, col - 1));
            possibleAttackSquares.add(getSquare(row, col + 1));
        }
        for (int i = 0; i < possibleAttackSquares.size(); i++) {
            if (possibleAttackSquares.get(i).getPiece() == null || possibleAttackSquares.get(i).getPiece().getOwner() == this.getOwner() || possibleAttackSquares.get(i).getIsWater() || this.getSquare().getGame() != possibleAttackSquares.get(i).getGame()) {
                possibleAttackSquares.remove(possibleAttackSquares.get(i));
            }
        }
        return possibleAttackSquares;
    }
}
