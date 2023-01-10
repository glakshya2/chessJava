import java.util.Vector;

public class Bishop extends Piece {

    Bishop(boolean color) {
        super(color);
    }

    @Override
    boolean isValidMove(Board currentBoard, int startX, int startY, int endX, int endY) {
        if (currentBoard.board[endX][endY] != null) {
            if (this.color == currentBoard.board[endX][endY].color) {
                return false;
            }
        }
        if (startX < endX && startY < endY) {
            int j = startY + 1;
            for (int i = startX + 1; i < endX; i++) {
                if (currentBoard.board[i][j] != null) {
                    return false;
                }
                j++;
            }
        } else if (startX > endX && startY > endY) {
            int j = startY - 1;
            for (int i = startX - 1; i > endX; i--) {
                if (currentBoard.board[i][j] != null) {
                    return false;
                }
                j--;
            }
        } else if (startX < endX && startY > endY) {
            int j = startY - 1;
            for (int i = startX + 1; i < endX; i++) {
                if (currentBoard.board[i][j] != null) {
                    return false;
                }
                j--;
            }
        } else if (startX > endX && startY < endY) {
            int j = startY + 1;
            for (int i = startX - 1; i > endX; i--) {
                if (currentBoard.board[i][j] != null) {
                    return false;
                }
                j++;
            }
        }
        return true;
    }

    Vector<Integer> possibleMoves(Board currentBoard, int startX, int startY) {
        Vector<Integer> list = new Vector<Integer>();
        int j = startY + 1;
        for (int i = startX + 1; i < 8; i++) {
            if (j < 8) {
                if (isValidMove(currentBoard, startX, startY, i, j)) {
                    list.add((i * 10) + j);
                    j++;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        j = startY + 1;
        for (int i = startX - 1; i >= 0; i--) {
            if (j < 8) {
                if (isValidMove(currentBoard, startX, startY, i, j)) {
                    list.add((i * 10) + j);
                    j++;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        j = startY - 1;
        for (int i = startX + 1; i < 8; i++) {
            if (j >= 0) {
                if (isValidMove(currentBoard, startX, startY, i, j)) {
                    list.add((i * 10) + j);
                    j--;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        j = startY - 1;
        for (int i = startX - 1; i >= 0; i--) {
            if (j >= 0) {
                if (isValidMove(currentBoard, startX, startY, i, j)) {
                    list.add((i * 10) + j);
                    j--;
                } else {
                    break;
                }
            }
        }
        return list;
    }
}