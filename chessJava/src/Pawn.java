import java.util.Vector;

public class Pawn extends Piece {
    
    //public boolean isFirstMove = true;

    Pawn(boolean color) {
        super(color);
    }

    @Override
    public void updateFirstMove() {
        super.updateFirstMove();
    }

    @Override
    boolean isValidMove(Board currentBoard, int startX, int startY, int endX, int endY) {
        boolean isValid;
        if (endY == startY + 2) {
            if (currentBoard.board[startX][startY + 1] != null) {
                return false;
            }
            if (currentBoard.board[endX][endY] != null) {
                return false;
            }
        }
        isValid = super.isValidMove(currentBoard, startX, startY, endX, endY);
        return isValid;
    }

    Vector<Integer> possibleMoves(Board currentBoard, int startX, int startY) {
        Vector<Integer> list = new Vector<Integer>();
        if (startY + 1 < 8) {
            if (currentBoard.board[startX][startY + 1] == null) {
                if (isValidMove(currentBoard, startX, startY, startX, startY + 1)) {
                    list.add((startX * 10) + (startY + 1));
                }
            }
        }
        if (isFirstMove) {
            if (startY + 2 < 8) {
                if (isValidMove(currentBoard, startX, startY, startX, startY + 2)) {
                    list.add((startX * 10) + (startY + 2));
                }
            }
        }
        if (startY + 1 < 8 && startX + 1 < 8) {
            if (currentBoard.board[startX + 1][startY + 1] != null) {
                if (isValidMove(currentBoard, startX, startY, startX + 1, startY + 1)) {
                    list.add(((startX + 1) * 10) + (startY + 1));
                }
            }
        }
        if (startY + 1 < 8 && startX - 1 >= 0) {
            if (currentBoard.board[startX - 1][startY + 1] != null) {
                if (isValidMove(currentBoard, startX, startY, startX - 1, startY + 1)) {
                    list.add(((startX - 1) * 10) + (startY + 1));
                }
            }
        }
        return list;
    }
}