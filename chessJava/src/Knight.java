import java.util.Vector;

public class Knight extends Piece {
    
    Knight(boolean color) {
        super(color);
    }

    @Override
    boolean isValidMove(Board currentBoard, int startX, int startY, int endX, int endY) {
        return super.isValidMove(currentBoard, startX, startY, endX, endY);
    }

    Vector<Integer> possibleMoves(Board currentBoard, int startX, int startY) {
        Vector<Integer> list = new Vector<Integer>();
        if (startX + 1 < 8 && startY + 2 < 8) {
            if (isValidMove(currentBoard, startX, startY, startX + 1, startY + 2)) {
                list.add(((startX + 1) * 10) + (startY + 2));
            }
        }
        if (startX + 2 < 8 && startY + 1 < 8) {
            if (isValidMove(currentBoard, startX, startY, startX + 2, startY + 1)) {
                list.add(((startX + 2) * 10) + (startY + 1));
            }
        }
        if (startX + 1 < 8 && startY - 2 >= 0) {
            if (isValidMove(currentBoard, startX, startY, startX + 1, startY - 2)) {
                list.add(((startX + 1) * 10) + (startY - 2));
            }
        }
        if (startX + 2 < 8 && startY - 1 >= 0) {
            if (isValidMove(currentBoard, startX, startY, startX + 2, startY - 1)) {
                list.add(((startX + 2) * 10) + (startY - 1));
            }
        }
        if (startX - 1 >= 0 && startY + 2 < 8) {
            if (isValidMove(currentBoard, startX, startY, startX - 1, startY + 2)) {
                list.add(((startX - 1) * 10) + (startY + 2));
            }
        }
        if (startX - 2 >= 0 && startY + 1 < 8) {
            if (isValidMove(currentBoard, startX, startY, startX - 2, startY + 1)) {
                list.add(((startX - 2) * 10) + (startY + 1));
            }
        }
        if (startX - 1 >= 0 && startY - 2 >= 0) {
            if (isValidMove(currentBoard, startX, startY, startX - 1, startY - 2)) {
                list.add(((startX - 1) * 10) + (startY - 2));
            }
        }
        if (startX - 2 >= 0 && startY - 1 >= 0) {
            if (isValidMove(currentBoard, startX, startY, startX - 2, startY - 1)) {
                list.add(((startX - 2) * 10) + (startY - 1));
            }
        }
        return list;
    }
}