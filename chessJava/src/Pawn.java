import java.util.*;

public class Pawn extends Piece {
    public boolean movedOnce = false;

    Pawn(boolean color) {
        super(color);
    }

    boolean isValidMove(Piece[][] board, int startX, int startY, int endX, int endY) {
        return super.isValidMove(board, startX, startY, endX, endY);
    }

    Vector<Integer> possibleMoves(Piece[][] board, int startX, int startY) {
        Vector<Integer> list = new Vector<Integer>();
        if (startX + 1 < 8 && startY + 1 < 8) {
            if (isValidMove(board, startX, startY, startX + 1, startY + 1)) {
                list.add(((startX + 1) * 10) + (startY + 1));
            }
        }
        if (startX - 1 >= 0 && startY + 1 < 8) {
            if (isValidMove(board, startX, startY, startX - 1, startY + 1)) {
                list.add(((startX - 1) * 10) + (startY + 1));
            }
        }
        if (startY + 1 < 8) {
            if (isValidMove(board, startX, startY, startX, startY + 1)) {
                list.add((startX) * 10 + (startY + 1));
            }
        }
        if (!movedOnce) {
            if (startY + 2 < 8) {
                if (isValidMove(board, startX, startY, startX, startY + 2)) {
                    list.add((startX * 10) + (startY + 2));
                }
            }
        }
        return list;
    }
}
