import java.util.Vector;

public class Queen extends Piece {

    Queen(boolean color) {
        super(color);
    }

    @Override
    boolean isValidMove(Piece[][] board, int startX, int startY, int endX, int endY) {
        if (board[endX][endY] != null) {
            if (this.color == board[endX][endY].color) {
                return false;
            }
        }
        if (startX == endX) {
            if (startY < endY) {
                for (int i = startY + 1; i <= endY; i++) {
                    if (board[startX][i] != null) {
                        if (board[startX][i].color == color) {
                            return false;
                        }
                    }
                }
            } else {
                for (int i = startY - 1; i >= endY; i--) {
                    if (board[startX][i] != null) {
                        if (board[startX][i].color == color) {
                            return false;
                        }
                    }
                }
            }
        } else if (startY == endY) {
            if (startX < endX) {
                for (int i = startX + 1; i <= endX; i++) {
                    if (board[i][startY] != null) {
                        if (board[i][startY].color == color) {
                            return false;
                        }
                    }
                }
            } else {
                for (int i = startX - 1; i >= endX; i--) {
                    if (board[i][startY] != null) {
                        if (board[i][startY].color == color) {
                            return false;
                        }
                    }
                }
            }
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
                j--;
            }
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

    Vector<Integer> possibleMoves(Piece[][] board, int startX, int startY) {
        Vector<Integer> list = new Vector<Integer>();
        for (int i = startY + 1; i < 8; i++) {
            if (isValidMove(board, startX, startY, startX, i)) {
                list.add((startX * 10) + i);
                if (board[startX][i] != null) {
                    break;
                }
            } else {
                break;
            }
        }
        for (int i = startY - 1; i >= 0; i--) {
            if (isValidMove(board, startX, startY, startX, i)) {
                list.add((startX * 10) + i);
                if (board[startX][i] != null) {
                    break;
                }
            } else {
                break;
            }
        }
        for (int i = startX + 1; i < 8; i++) {
            if (isValidMove(board, startX, startY, i, startY)) {
                list.add((i * 10) + startY);
                if (board[i][startY] != null) {
                    break;
                }
            } else {
                break;
            }
        }
        for (int i = startX - 1; i >= 0; i--) {
            if (isValidMove(board, startX, startY, i, startY)) {
                list.add((i * 10) + startY);
                if (board[i][startY] != null) {
                    break;
                }
            } else {
                break;
            }
        }
        int j = startY + 1;
        for (int i = startX + 1; i < 8; i++) {
            if (j < 8) {
                if (isValidMove(board, startX, startY, i, j)) {
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
                if (isValidMove(board, startX, startY, i, j)) {

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
                if (isValidMove(board, startX, startY, i, j)) {
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
                if (isValidMove(board, startX, startY, i, j)) {
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