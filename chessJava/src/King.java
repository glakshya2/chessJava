import java.util.Vector;

public class King extends Piece {

    King(boolean color) {
        super(color);
    }

    @Override
    boolean isValidMove(Piece[][] board, int startX, int startY, int endX, int endY) {

        if (MyFrame.isCheck(board, endX, endY, color)) {
            return false;
        }

        boolean valid = super.isValidMove(board, startX, startY, endX, endY);

        if (endX + 1 < 8 && endX + 1 != startX) {
            if (board[endX + 1][endY] != null) {
                if (board[endX + 1][endY].getClass().getSimpleName().equals("King")) {
                    valid = false;
                }
            }
        }
        if (endX - 1 >= 0 && endX - 1 != startX) {
            if (board[endX - 1][endY] != null) {
                if (board[endX - 1][endY].getClass().getSimpleName().equals("King")) {
                    valid = false;
                }
            }
        }
        if (endY + 1 < 8 && endY + 1 != startY) {
            if (board[endX][endY + 1] != null) {
                if (board[endX][endY + 1].getClass().getSimpleName().equals("King")) {
                    valid = false;
                }
            }
        }
        if (endY - 1 >= 0 && endY - 1 != startY) {
            if (board[endX][endY - 1] != null) {
                if (board[endX][endY - 1].getClass().getSimpleName().equals("King")) {
                    valid = false;
                }
            }
        }
        if (endX + 1 < 8 && endY + 1 < 8 && endX + 1 != startX && endY + 1 != startY) {
            if (board[endX + 1][endY + 1] != null) {
                if (board[endX + 1][endY + 1].getClass().getSimpleName().equals("King")) {
                    valid = false;
                }
            }
        }
        if (endX + 1 < 8 && endY - 1 >= 0 && endX + 1 != startX && endY - 1 != startY) {
            if (board[endX + 1][endY - 1] != null) {
                if (board[endX + 1][endY - 1].getClass().getSimpleName().equals("King")) {
                    valid = false;
                }
            }
        }
        if (endX - 1 >= 0 && endY + 1 < 8 && endX - 1 != startX && endY + 1 != startY) {
            if (board[endX - 1][endY + 1] != null) {
                if (board[endX - 1][endY + 1].getClass().getSimpleName().equals("King")) {
                    valid = false;
                }
            }
        }
        if (endX - 1 >= 0 && endY - 1 >= 0 && endX - 1 != startX && endY - 1 != startY) {
            if (board[endX - 1][endY - 1] != null) {
                if (board[endX - 1][endY - 1].getClass().getSimpleName().equals("King")) {
                    valid = false;
                }
            }
        }
        return valid;
    }

    Vector<Integer> possibleMoves(Piece[][] board, int startX, int startY) {
        Vector<Integer> list = new Vector<Integer>();
        if (startY + 1 < 8) {
            if (isValidMove(board, startX, startY, startX, startY + 1)) {
                list.add((startX * 10) + (startY + 1));
            }
        }
        if (startY - 1 >= 0) {
            if (isValidMove(board, startX, startY, startX, startY - 1)) {
                list.add((startX * 10) + (startY - 1));
            }
        }
        if (startX + 1 < 8) {
            if (isValidMove(board, startX, startY, startX + 1, startY)) {
                list.add(((startX + 1) * 10) + startY);
            }
        }
        if (startX - 1 >= 0) {
            if (isValidMove(board, startX, startY, startX - 1, startY)) {
                list.add(((startX - 1) * 10) + startY);
            }
        }
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
        if (startX + 1 < 8 && startY - 1 >= 0) {
            if (isValidMove(board, startX, startY, startX + 1, startY - 1)) {
                list.add(((startX + 1) * 10) + (startY - 1));
            }
        }
        if (startX - 1 >= 0 && startY - 1 >= 0) {
            if (isValidMove(board, startX, startY, startX - 1, startY - 1)) {
                list.add(((startX - 1) * 10) + (startY - 1));
            }
        }
        return list;
    }
}