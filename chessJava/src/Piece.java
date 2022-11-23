import java.util.Vector;

public abstract class Piece {

    public boolean color; // true for white, false for black

    Piece(boolean color) {
        this.color = color;
    }

    // returns true if move provided is valid
    boolean isValidMove(Piece[][] board, int startX, int startY, int endX, int endY) {
        if (board[endX][endY] == null || this.color != board[endX][endY].color) {
            return true;
        }
        return false;

    }

    // return vector containing list of all possible moves at given point of time
    // int the form of xcoord*10 + ycoord
    abstract Vector<Integer> possibleMoves(Piece[][] board, int startX, int startY);

    public void updateFirstMove() {
        // Only used in Pawn.java to change boolean value of isFirstMove to false on
        // moving pawn piece for first time.
    }
}