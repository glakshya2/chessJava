import java.util.Vector;

public class Rook extends Piece {

    Rook(boolean color) {
        super(color);
    }

    @Override
    public void updateFirstMove() {
        super.updateFirstMove();
    }

    @Override
    boolean isValidMove(Board currentBoard, int startX, int startY, int endX, int endY, Vector<Board> boardHistory) {
        if (currentBoard.board[endX][endY] != null) {
            if (this.color == currentBoard.board[endX][endY].color) {
                return false;
            }
        }
        if (startX == endX) {
            if (startY < endY) {
                for (int i = startY + 1; i <= endY; i++) {
                    if (currentBoard.board[startX][i] != null) {
                        if (currentBoard.board[startX][i].color == color) {
                            return false;
                        }
                    }
                }
            } else {
                for (int i = startY - 1; i >= endY; i--) {
                    if (currentBoard.board[startX][i] != null) {
                        if (currentBoard.board[startX][i].color == color) {
                            return false;
                        }
                    }
                }
            }
        } else if (startY == endY) {
            if (startX < endX) {
                for (int i = startX + 1; i <= endX; i++) {
                    if (currentBoard.board[i][startY] != null) {
                        if (currentBoard.board[i][startY].color == color) {
                            return false;
                        }
                    }
                }
            } else {
                for (int i = startX - 1; i >= endX; i--) {
                    if (currentBoard.board[i][startY] != null) {
                        if (currentBoard.board[i][startY].color == color) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    Vector<Integer> possibleMoves(Board currentBoard, int startX, int startY, Vector<Board> boardHistory) {
        Vector<Integer> list = new Vector<Integer>();
        for (int i = startY + 1; i < 8; i++) {
            if (isValidMove(currentBoard, startX, startY, startX, i, boardHistory)) {
                list.add((startX * 10) + i);
                if (currentBoard.board[startX][i] != null) {
                    break;
                }
            } else {
                break;
            }
        }
        for (int i = startY - 1; i >= 0; i--) {
            if (isValidMove(currentBoard, startX, startY, startX, i, boardHistory)) {
                list.add((startX * 10) + i);
                if (currentBoard.board[startX][i] != null) {
                    break;
                }
            } else {
                break;
            }
        }
        for (int i = startX + 1; i < 8; i++) {
            if (isValidMove(currentBoard, startX, startY, i, startY, boardHistory)) {
                list.add((i * 10) + startY);
                if (currentBoard.board[i][startY] != null) {
                    break;
                }
            } else {
                break;
            }
        }
        for (int i = startX - 1; i >= 0; i--) {
            if (isValidMove(currentBoard, startX, startY, i, startY, boardHistory)) {
                list.add((i * 10) + startY);
                if (currentBoard.board[i][startY] != null) {
                    break;
                }
            } else {
                break;
            }
        }
        return list;
    }
}