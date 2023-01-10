import java.util.Vector;

public abstract class Piece {

    public boolean color; // true for white, false for black
    public boolean isFirstMove = true;
    
    Piece(boolean color) {
        this.color = color;
    }

    public void updateFirstMove() {
        isFirstMove = false;
    }

    public boolean isColor() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }

    public boolean isFirstMove(){
        return isFirstMove;
    }

    public void setFirstMove(boolean isFirstMove) {
        this.isFirstMove = isFirstMove;
    }

    // returns true if move provided is valid
    boolean isValidMove(Board currentBoard, int startX, int startY, int endX, int endY) {
        if (currentBoard.board[endX][endY] == null || this.color != currentBoard.board[endX][endY].color) {
            return true;
        }
        return false;

    }

    // return vector containing list of all possible moves at given point of time
    // int the form of xcoord*10 + ycoord
    abstract Vector<Integer> possibleMoves(Board currentBoard, int startX, int startY);
}