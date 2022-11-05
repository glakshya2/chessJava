import java.util.*;

public class Bishop extends Piece{
    Bishop(boolean color){
        super(color);
    }

    boolean isValidMove(Piece[][] board, int startX, int startY, int endX, int endY){
        if (this.color == board[endX][endY].color) {
            return false;
        }
        if (startX < endX && startY < endY) {
            int j = startY + 1;
            for (int i = startX + 1; i < endX; i++) {
                if (board[i][j] != null) {
                    return false;
                }
                j++;
            }
        } else if (startX > endX && startY > endY) {
            int j = startY - 1;
            for (int i = startX - 1; i > endX; i--) {
                if (board[i][j] != null) {
                    return false;
                }
            }
            j--;
        } else if (startX < endX && startY > endY) {
            int j = startY - 1;
            for (int i = startX + 1; i < endX; i++) {
                if (board[i][j] != null) {
                    return false;
                }
                j--;
            }
        } else if (startX > endX && startY < endY) {
            int j = startY + 1;
            for (int i = startX - 1; i > endX; i--) {
                if (board[i][j] != null) {
                    return false;
                }
                j++;
            }
        }
        return true;
    }

    Vector<Integer> possibleMoves(Piece[][] board, int startX, int startY){
        Vector<Integer> list = new Vector<Integer>();
        int j = startY + 1;
        for (int i = startX + 1; i < 8; i++) {
            if (isValidMove(board, startX, startY, i, j)) {
                list.add((i * 10) + j);
                j++;
            } else {
                break;
            }
        }
        j = startY + 1;
        for (int i = startX - 1; i >= 0; i--) {
            if (isValidMove(board, startX, startY, i, j)) {
                list.add((i * 10) + j);
                j++;
            } else {
                break;
            }
        }
        j = startY - 1;
        for (int i = startX + 1; i < 8; i++) {
            if (isValidMove(board, startX, startY, i, j)) {
                list.add((i * 10) + j);
                j--;
            } else {
                break;
            }
        }
        j = startY - 1;
        for (int i = startX - 1; i >= 0; i--) {
            if (isValidMove(board, startX, startY, i, j)) {
                list.add((i * 10) + j);
                j--;
            } else {
                break;
            }
        }
        return list;

    }
}
