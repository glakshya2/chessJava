import java.util.*;

public class Pawn extends Piece {
    public boolean isFirstMove = true;

    Pawn(boolean color) {
        super(color);
    }

    public void updateFirstMove(){
        isFirstMove = false;
    }

    boolean isValidMove(Piece[][] board, int startX, int startY, int endX, int endY) {
        boolean isValid;
        if (endY == startY + 2) {
            if (board[startX][startY + 1] != null) {
                    return false;
            }
        }
        isValid = super.isValidMove(board, startX, startY, endX, endY);
        return isValid;
    }

    Vector<Integer> possibleMoves(Piece[][] board, int startX, int startY) {
        Vector<Integer> list = new Vector<Integer>();
        if (startY + 1 < 8) {
            if (board[startX][startY + 1] == null) {
                if (isValidMove(board, startX, startY, startX, startY + 1)) {
                    list.add((startX * 10) + (startY + 1));
                }
            }
        }
        if (isFirstMove) {
            if (isValidMove(board, startX, startY, startX, startY + 2)) {
                list.add((startX * 10) + (startY + 2));
            }
        }
        if (startY + 1 < 8 && startX + 1 < 8) {
            if (board[startX + 1][startY + 1] != null) {
                if (isValidMove(board, startX, startY, startX + 1, startY + 1)) {
                    list.add(((startX + 1) * 10) + (startY + 1));
                }
            }
        }
        if (startY + 1 < 8 && startX - 1 > 0) {
            if (board[startX - 1][startY + 1] != null) {
                if (isValidMove(board, startX, startY, startX - 1, startY + 1)) {
                    list.add(((startX - 1) * 10) + (startY + 1));
                }
            }
        }
        return list;
    }
}
