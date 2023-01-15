import java.util.Vector;

public class Pawn extends Piece {

    // public boolean isFirstMove = true;

    Pawn(boolean color) {
        super(color);
    }

    @Override
    public void updateFirstMove() {
        super.updateFirstMove();
    }

    public boolean isEnpassantPossible(Vector<Board> boardHistory, int startX, int startY, int direction) {
        if(boardHistory.size()<2){
            return false;
        }
        if (direction == -1){
            Board tempBoard = boardHistory.elementAt(boardHistory.size() - 2);
            if(tempBoard.board[startX-1][4]!=null){
                return false;
            }
            if(tempBoard.board[startX-1][5]!=null){
                return false;
            }
            if(tempBoard.board[startX-1][6]==null){
                return false;
            }
            if(!tempBoard.board[startX-1][6].getClass().getSimpleName().equals("Pawn")){
                return false;
            }
            return true;
        } else {
            Board tempBoard = boardHistory.elementAt(boardHistory.size() - 2);
            if(tempBoard.board[startX+1][4]!=null){
                return false;
            }
            if(tempBoard.board[startX+1][5]!=null){
                return false;
            }
            if(tempBoard.board[startX+1][6]==null){
                return false;
            }
            if(!tempBoard.board[startX+1][6].getClass().getSimpleName().equals("Pawn")){
                return false;
            }
            return true;
        }
    }

    @Override
    boolean isValidMove(Board currentBoard, int startX, int startY, int endX, int endY, Vector<Board> boardHIstory) {
        boolean isValid;
        if (endY == startY + 2) {
            if (currentBoard.board[startX][startY + 1] != null) {
                return false;
            }
            if (currentBoard.board[endX][endY] != null) {
                return false;
            }
        }
        isValid = super.isValidMove(currentBoard, startX, startY, endX, endY, boardHIstory);
        return isValid;
    }

    Vector<Integer> possibleMoves(Board currentBoard, int startX, int startY, Vector<Board> boardHistory) {
        Vector<Integer> list = new Vector<Integer>();
        if (startY + 1 < 8) {
            if (currentBoard.board[startX][startY + 1] == null) {
                if (isValidMove(currentBoard, startX, startY, startX, startY + 1, boardHistory)) {
                    list.add((startX * 10) + (startY + 1));
                }
            }
        }
        if (isFirstMove) {
            if (startY + 2 < 8) {
                if (isValidMove(currentBoard, startX, startY, startX, startY + 2, boardHistory)) {
                    list.add((startX * 10) + (startY + 2));
                }
            }
        }
        if (startY + 1 < 8 && startX + 1 < 8) {
            if (currentBoard.board[startX + 1][startY + 1] != null) {
                if (isValidMove(currentBoard, startX, startY, startX + 1, startY + 1, boardHistory)) {
                    list.add(((startX + 1) * 10) + (startY + 1));
                }
            }
        }
        if (startY + 1 < 8 && startX - 1 >= 0) {
            if (currentBoard.board[startX - 1][startY + 1] != null) {
                if (isValidMove(currentBoard, startX, startY, startX - 1, startY + 1, boardHistory)) {
                    list.add(((startX - 1) * 10) + (startY + 1));
                }
            }
        }
        if (startY == 4) {
            if (startX - 1 >= 0) {
                if (currentBoard.board[startX - 1][startY] != null) {
                    if (currentBoard.board[startX - 1][startY].getClass().getSimpleName().equals("Pawn")) {
                        if (currentBoard.board[startX - 1][startY].color != color) {
                            if (isEnpassantPossible(boardHistory, startX, startY, -1)) {
                                list.add(((startX - 1) * 10) + (startY + 1));
                            }
                        }
                    }
                }
            } if (startX + 1 < 8) {
                if (currentBoard.board[startX + 1][startY] != null) {
                    if (currentBoard.board[startX + 1][startY].getClass().getSimpleName().equals("Pawn")) {
                        if (currentBoard.board[startX + 1][startY].color != color) {
                            if (isEnpassantPossible(boardHistory, startX, startY, 1)) {
                                list.add(((startX + 1) * 10) + (startY + 1));
                            }
                        }
                    }
                }
            }
        }
        return list;
    }
}