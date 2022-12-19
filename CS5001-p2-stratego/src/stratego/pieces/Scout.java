package stratego.pieces;

import stratego.Game;
import stratego.Player;
import stratego.Square;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Scout is a Piece which behaves in a different way when compared to other Pieces.
 */
public class Scout extends Piece {
    private int rank = 2;

    /**
     * Constructor calling the constructor of super class.
     *
     * @param owner  instance of the Player who owns this piece.
     * @param square instance of the square this piece is in.
     */
    public Scout(Player owner, Square square) {
        super(owner, square);
    }

    /**
     * Overrides because the constructor is a different from that of the default constructor as this does not take rank in constructor.
     *
     * @return int value of rank of the piece.
     */
    @Override
    public int getRank() {
        return rank;
    }

    /**
     * getSquare method is implemented here also as game might be null.
     *
     * @param row int value of row.
     * @param col int value of column of the square.
     * @return square if it already exits, else returns a new square.
     * @throws IndexOutOfBoundsException if the row or col is given out of the board's boundary.
     */
    private Square getSquare(int row, int col) throws IndexOutOfBoundsException {
        List<Integer> rowList = Arrays.stream(Game.WATER_ROWS).boxed().toList();
        List<Integer> colList = Arrays.stream(Game.WATER_COLS).boxed().toList();
        boolean isWater = false;
        if (rowList.contains(row) && colList.contains(col)) {
            isWater = true;
        }
        List<Square> presentSquare = Game.getAllSquares().stream().filter(square -> square.getRow() == row && square.getCol() == col).toList();
        if (presentSquare.size() >= 1) {
            return presentSquare.get(0);
        }
        Square newSquare = new Square(this.getSquare().getGame(), row, col, isWater);
        return newSquare;
    }

    /**
     * overriding as the super class has abstract method.
     * this method gives all the possible moves that a Scout can make according to the rules of the game.
     *
     * @return list of squares to move.
     */
    @Override
    public List<Square> getLegalMoves() {
        int row = this.getSquare().getRow();
        int col = this.getSquare().getCol();
        List<Square> possibleMoveSquares = new ArrayList<>();
        int n = 1;
        if (row == 0 && col == 0) {
            while (getSquare(row + n, col).canBeEntered()) {
                possibleMoveSquares.add(getSquare(row + n, col));
                n++;
            }
            n = 1;
            while (getSquare(row, col + n).canBeEntered()) {
                possibleMoveSquares.add(getSquare(row, col + n));
                n++;
            }
        } else if (row == Game.HEIGHT - 1 && col == Game.WIDTH - 1) {
            n = 1;
            while (getSquare(row - n, col).canBeEntered()) {
                possibleMoveSquares.add(getSquare(row - n, col));
                n++;
            }
            n = 1;
            while (getSquare(row, col - n).canBeEntered()) {
                possibleMoveSquares.add(getSquare(row, col - n));
                n++;
            }
        } else if (row < Game.HEIGHT - 1 && col == Game.WIDTH - 1) {
            n = 1;
            while (getSquare(row - n, col).canBeEntered()) {
                possibleMoveSquares.add(getSquare(row - n, col));
                n++;
            }
            n = 1;
            while (getSquare(row, col - n).canBeEntered()) {
                possibleMoveSquares.add(getSquare(row, col - n));
                n++;
            }
            n = 1;
            while (getSquare(row + n, col).canBeEntered()) {
                possibleMoveSquares.add(getSquare(row + n, col));
                n++;
            }
        } else if (row == Game.HEIGHT - 1 && col < Game.WIDTH - 1) {
            n = 1;
            while (getSquare(row - n, col).canBeEntered()) {
                possibleMoveSquares.add(getSquare(row - n, col));
                n++;
            }
            n = 1;
            while (getSquare(row, col - n).canBeEntered()) {
                possibleMoveSquares.add(getSquare(row, col - n));
                n++;
            }
            n = 1;
            while (getSquare(row, col + n).canBeEntered()) {
                possibleMoveSquares.add(getSquare(row, col + n));
                n++;
            }
        } else if (row != 0 && col == 0 && row < Game.HEIGHT - 1) {
            n = 1;
            while (getSquare(row, col + n).canBeEntered()) {
                possibleMoveSquares.add(getSquare(row, col + n));
                n++;
            }
            n = 1;
            while (getSquare(row + n, col).canBeEntered()) {
                possibleMoveSquares.add(getSquare(row + n, col));
                n++;
            }
            n = 1;
            while (getSquare(row - n, col).canBeEntered()) {
                possibleMoveSquares.add(getSquare(row - n, col));
                n++;
            }
        } else if (row == 0 && col != 0 && col < Game.WIDTH - 1) {
            n = 1;
            while (getSquare(row, col + n).canBeEntered()) {
                possibleMoveSquares.add(getSquare(row, col + n));
                n++;
            }
            n = 1;
            while (getSquare(row, col - n).canBeEntered()) {
                possibleMoveSquares.add(getSquare(row, col - n));
                n++;
            }
            n = 1;
            while (getSquare(row + n, col).canBeEntered()) {
                possibleMoveSquares.add(getSquare(row + n, col));
                n++;
            }
        } else {
//            going down
            n = 1;
            while (getSquare(row + n, col).canBeEntered() && (row + n) < Game.HEIGHT) {
                possibleMoveSquares.add(getSquare(row + n, col));
                n++;
            }
//            going up
            n = 1;
            while (getSquare(row - n, col).canBeEntered() && (row - n) >= 0) {
                possibleMoveSquares.add(getSquare(row - n, col));
                n++;
            }
//            going left
            n = 1;
            while (getSquare(row, col - n).canBeEntered() && (col - n) >= 0) {
                possibleMoveSquares.add(getSquare(row, col - n));
                n++;
            }
//            going right
            n = 1;
            while (getSquare(row, col + n).canBeEntered() && (col + n) < Game.WIDTH) {
                possibleMoveSquares.add(getSquare(row, col + n));
                n++;
            }
        }
        for (int i = 0; i < possibleMoveSquares.size(); i++) {
            if (possibleMoveSquares.get(i).getPiece() != null) {
                possibleMoveSquares.remove(possibleMoveSquares.get(i));
            }
            if (!possibleMoveSquares.get(i).canBeEntered()) {
                possibleMoveSquares.remove(possibleMoveSquares.get(i));
            }
        }
        return possibleMoveSquares;
    }

    /**
     * this is also an abstract method in its super class.
     * returns all the possible attacks Scout can make at a given instance.
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
        possibleAttackSquares = possibleAttackSquares.stream().filter(square -> square.getPiece() != null).toList();
        for (int i = 0; i < possibleAttackSquares.size(); i++) {
            if (possibleAttackSquares.get(i).getPiece() == null && possibleAttackSquares.get(i).getRow() != 0 && possibleAttackSquares.get(i).getCol() != 0) {
                possibleAttackSquares.remove(possibleAttackSquares.get(i));
            } else if (possibleAttackSquares.get(i).canBeEntered() && possibleAttackSquares.get(i).getPiece().getOwner() == this.getOwner() && possibleAttackSquares.get(i).getGame() == this.getSquare().getGame()) {
                possibleAttackSquares.remove(possibleAttackSquares.get(i));
            }
        }
        return possibleAttackSquares;
    }
}
