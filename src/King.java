import java.util.Vector;

public class King extends Piece {

    King(boolean color) {
        super(color);
    }

    @Override
    public void updateFirstMove() {
        super.updateFirstMove();
    }

    @Override
    public boolean isRightCastlingPossible(Board currentBoard, Vector<Board> currentHistory) {
        if (MyFrame.isCheck(currentBoard, currentBoard.returnKingX(color), currentBoard.returnKingY(color), color, currentHistory)) {
            return false;
        }
        if (color) {
            if (currentBoard.board[4][0] == null) {
                return false;
            }
            if (!currentBoard.board[4][0].getClass().getSimpleName().equals("King")) {
                return false;
            }
            if (!currentBoard.board[4][0].isFirstMove) {
                return false;
            }
            if (currentBoard.board[7][0] == null) {
                return false;
            }
            if (!currentBoard.board[7][0].getClass().getSimpleName().equals("Rook")) {
                return false;
            }
            if (!currentBoard.board[7][0].isFirstMove) {
                return false;
            }
            if (currentBoard.board[5][0] == null && currentBoard.board[6][0] == null) {
                if(!MyFrame.isCheck(currentBoard, 5, 0, color, currentHistory) && !MyFrame.isCheck(currentBoard, 6, 0, color, currentHistory)){
                    return true;
                }
            }
            return false;
        } else {
            if (currentBoard.board[3][0] == null) {
                return false;
            }
            if (!currentBoard.board[3][0].getClass().getSimpleName().equals("King")) {
                return false;
            }
            if (!currentBoard.board[3][0].isFirstMove) {
                return false;
            }
            if (currentBoard.board[0][0] == null) {
                return false;
            }
            if (!currentBoard.board[0][0].getClass().getSimpleName().equals("Rook")) {
                return false;
            }
            if (!currentBoard.board[0][0].isFirstMove) {
                return false;
            }
            if (currentBoard.board[1][0] == null && currentBoard.board[2][0] == null) {
                if(!MyFrame.isCheck(currentBoard, 1, 0, color, currentHistory) && !MyFrame.isCheck(currentBoard, 2, 0, color, currentHistory)){
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public boolean isLeftCastlingPossible(Board currentBoard, Vector<Board> currentHistory){
        if (MyFrame.isCheck(currentBoard, currentBoard.returnKingX(color), currentBoard.returnKingY(color), color, currentHistory)) {
            return false;
        }
        if (color) {
            if (currentBoard.board[4][0] == null) {
                return false;
            }
            if (!currentBoard.board[4][0].getClass().getSimpleName().equals("King")) {
                return false;
            }
            if (!currentBoard.board[4][0].isFirstMove) {
                return false;
            }
            if (currentBoard.board[0][0] == null) {
                return false;
            }
            if (!currentBoard.board[0][0].getClass().getSimpleName().equals("Rook")) {
                return false;
            }
            if (!currentBoard.board[0][0].isFirstMove) {
                return false;
            }
            if (currentBoard.board[1][0] == null && currentBoard.board[2][0] == null && currentBoard.board[3][0]==null) {
                if(!MyFrame.isCheck(currentBoard, 1, 0, color, currentHistory) && !MyFrame.isCheck(currentBoard, 2, 0, color, currentHistory) && !MyFrame.isCheck(currentBoard, 3, 0, color, currentHistory)){
                    return true;
                }
            }
            return false;
        } else {
            if (currentBoard.board[3][0] == null) {
                return false;
            }
            if (!currentBoard.board[3][0].getClass().getSimpleName().equals("King")) {
                return false;
            }
            if (!currentBoard.board[3][0].isFirstMove) {
                return false;
            }
            if (currentBoard.board[7][0] == null) {
                return false;
            }
            if (!currentBoard.board[7][0].getClass().getSimpleName().equals("Rook")) {
                return false;
            }
            if (!currentBoard.board[7][0].isFirstMove) {
                return false;
            }
            if (currentBoard.board[4][0] == null && currentBoard.board[5][0] == null && currentBoard.board[6][0] == null) {
                if(!MyFrame.isCheck(currentBoard, 4, 0, color, currentHistory) && !MyFrame.isCheck(currentBoard, 5, 0, color, currentHistory) && !MyFrame.isCheck(currentBoard, 6, 0, color, currentHistory)){
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    boolean isValidMove(Board currentBoard, int startX, int startY, int endX, int endY, Vector<Board> currentHistory) {

        if (MyFrame.isCheck(currentBoard, endX, endY, color, currentHistory)) {
            return false;
        }

        boolean valid = super.isValidMove(currentBoard, startX, startY, endX, endY, currentHistory);

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

    Vector<Integer> possibleMoves(Board currentBoard, int startX, int startY, Vector<Board> boardHistory) {
        Vector<Integer> list = new Vector<Integer>();
        if (startY + 1 < 8) {
            if (isValidMove(currentBoard, startX, startY, startX, startY + 1, boardHistory)) {
                list.add((startX * 10) + (startY + 1));
            }
        }
        if (startY - 1 >= 0) {
            if (isValidMove(currentBoard, startX, startY, startX, startY - 1, boardHistory)) {
                list.add((startX * 10) + (startY - 1));
            }
        }
        if (startX + 1 < 8) {
            if (isValidMove(currentBoard, startX, startY, startX + 1, startY, boardHistory)) {
                list.add(((startX + 1) * 10) + startY);
            }
        }
        if (startX - 1 >= 0) {
            if (isValidMove(currentBoard, startX, startY, startX - 1, startY, boardHistory)) {
                list.add(((startX - 1) * 10) + startY);
            }
        }
        if (startX + 1 < 8 && startY + 1 < 8) {
            if (isValidMove(currentBoard, startX, startY, startX + 1, startY + 1, boardHistory)) {
                list.add(((startX + 1) * 10) + (startY + 1));
            }
        }
        if (startX - 1 >= 0 && startY + 1 < 8) {
            if (isValidMove(currentBoard, startX, startY, startX - 1, startY + 1, boardHistory)) {
                list.add(((startX - 1) * 10) + (startY + 1));

            }
        }
        if (startX + 1 < 8 && startY - 1 >= 0) {
            if (isValidMove(currentBoard, startX, startY, startX + 1, startY - 1, boardHistory)) {
                list.add(((startX + 1) * 10) + (startY - 1));
            }
        }
        if (startX - 1 >= 0 && startY - 1 >= 0) {
            if (isValidMove(currentBoard, startX, startY, startX - 1, startY - 1, boardHistory)) {
                list.add(((startX - 1) * 10) + (startY - 1));
            }
        }
        if(isLeftCastlingPossible(currentBoard, boardHistory)){
            if(color){
                list.add(20);
            } else {
                list.add(50);
            }
        }
        if(isRightCastlingPossible(currentBoard, boardHistory)){
            if(color){
                list.add(60);
            } else {
                list.add(10);
            }
        }
        return list;
    }
}