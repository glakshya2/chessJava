import java.util.Vector;

public class King extends Piece {

    King(boolean color) {
        super(color);
    }

    @Override
    boolean isValidMove(Board currentBoard, int startX, int startY, int endX, int endY) {

        if (MyFrame.isCheck(currentBoard, endX, endY, color)) {
            return false;
        }

        boolean valid = super.isValidMove(currentBoard, startX, startY, endX, endY);

        if (endX + 1 < 8 && endX + 1 != startX) {
            if (currentBoard.board[endX + 1][endY] != null) {
                if (currentBoard.board[endX + 1][endY].getClass().getSimpleName().equals("King")) {
                    valid = false;
                }
            }
        }
        if (endX - 1 >= 0 && endX - 1 != startX) {
            if (currentBoard.board[endX - 1][endY] != null) {
                if (currentBoard.board[endX - 1][endY].getClass().getSimpleName().equals("King")) {
                    valid = false;
                }
            }
        }
        if (endY + 1 < 8 && endY + 1 != startY) {
            if (currentBoard.board[endX][endY + 1] != null) {
                if (currentBoard.board[endX][endY + 1].getClass().getSimpleName().equals("King")) {
                    valid = false;
                }
            }
        }
        if (endY - 1 >= 0 && endY - 1 != startY) {
            if (currentBoard.board[endX][endY - 1] != null) {
                if (currentBoard.board[endX][endY - 1].getClass().getSimpleName().equals("King")) {
                    valid = false;
                }
            }
        }
        if (endX + 1 < 8 && endY + 1 < 8 && endX + 1 != startX && endY + 1 != startY) {
            if (currentBoard.board[endX + 1][endY + 1] != null) {
                if (currentBoard.board[endX + 1][endY + 1].getClass().getSimpleName().equals("King")) {
                    valid = false;
                }
            }
        }
        if (endX + 1 < 8 && endY - 1 >= 0 && endX + 1 != startX && endY - 1 != startY) {
            if (currentBoard.board[endX + 1][endY - 1] != null) {
                if (currentBoard.board[endX + 1][endY - 1].getClass().getSimpleName().equals("King")) {
                    valid = false;
                }
            }
        }
        if (endX - 1 >= 0 && endY + 1 < 8 && endX - 1 != startX && endY + 1 != startY) {
            if (currentBoard.board[endX - 1][endY + 1] != null) {
                if (currentBoard.board[endX - 1][endY + 1].getClass().getSimpleName().equals("King")) {
                    valid = false;
                }
            }
        }
        if (endX - 1 >= 0 && endY - 1 >= 0 && endX - 1 != startX && endY - 1 != startY) {
            if (currentBoard.board[endX - 1][endY - 1] != null) {
                if (currentBoard.board[endX - 1][endY - 1].getClass().getSimpleName().equals("King")) {
                    valid = false;
                }
            }
        }
        return valid;
    }

    Vector<Integer> possibleMoves(Board currentBoard, int startX, int startY) {
        Vector<Integer> list = new Vector<Integer>();
        if (startY + 1 < 8) {
            if (isValidMove(currentBoard, startX, startY, startX, startY + 1)) {
                list.add((startX * 10) + (startY + 1));
            }
        }
        if (startY - 1 >= 0) {
            if (isValidMove(currentBoard, startX, startY, startX, startY - 1)) {
                list.add((startX * 10) + (startY - 1));
            }
        }
        if (startX + 1 < 8) {
            if (isValidMove(currentBoard, startX, startY, startX + 1, startY)) {
                list.add(((startX + 1) * 10) + startY);
            }
        }
        if (startX - 1 >= 0) {
            if (isValidMove(currentBoard, startX, startY, startX - 1, startY)) {
                list.add(((startX - 1) * 10) + startY);
            }
        }
        if (startX + 1 < 8 && startY + 1 < 8) {
            if (isValidMove(currentBoard, startX, startY, startX + 1, startY + 1)) {
                list.add(((startX + 1) * 10) + (startY + 1));
            }
        }
        if (startX - 1 >= 0 && startY + 1 < 8) {
            if (isValidMove(currentBoard, startX, startY, startX - 1, startY + 1)) {
                list.add(((startX - 1) * 10) + (startY + 1));

            }
        }
        if (startX + 1 < 8 && startY - 1 >= 0) {
            if (isValidMove(currentBoard, startX, startY, startX + 1, startY - 1)) {
                list.add(((startX + 1) * 10) + (startY - 1));
            }
        }
        if (startX - 1 >= 0 && startY - 1 >= 0) {
            if (isValidMove(currentBoard, startX, startY, startX - 1, startY - 1)) {
                list.add(((startX - 1) * 10) + (startY - 1));
            }
        }
        return list;
    }
}