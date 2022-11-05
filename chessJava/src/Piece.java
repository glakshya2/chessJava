import java.util.*;

public abstract class Piece {
    public boolean color;//true for white, false for black
    Piece(boolean color){
        this.color = color;
    }
    boolean isValidMove(Piece[][] board, int startX, int startY, int endX, int endY){
        if (board[endX][endY] == null || this.color != board[endX][endY].color) {
            return true;
        } else {
            return false;
        }
    }
    abstract Vector<Integer> possibleMoves(Piece[][] board, int startX, int startY);
}