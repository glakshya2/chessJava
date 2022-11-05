import java.util.*;

public class Queen extends Piece {

    Queen(boolean color){
        super(color);
    }

    //returns true if given move can be made
    boolean isValidMove(Piece[][] board, int startX, int startY, int endX, int endY) {
        if (this.color == board[endX][endY].color) {
            return false;
        }
        if (startX == endX) {
            if (startY < endY) {
                for (int i = startY + 1; i < endY; i++) {
                    if (board[startX][i] != null) {
                        return false;
                    }
                }
            } else {
                for (int i = startY - 1; i > endY; i--) {
                    if (board[startX][i] != null) {
                        return false;
                    }
                }
            }
        } else if (startY == endY) {
            if (startX < endX) {
                for (int i = startX + 1; i < endX; i++) {
                    if (board[i][startY] != null) {
                        return false;
                    }
                }
            } else {
                for (int i = startX - 1; i > endX; i++) {
                    if (board[i][startY] != null) {
                        return false;
                    }
                }
            }
        } else if (startX < endX && startY < endY) {
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

    //return vector containing list of all possible moves at given point of time int the form of xcoord*10 + ycoord
    Vector<Integer> possibleMoves(Piece[][] board, int startX, int startY) {
        Vector<Integer> list = new Vector<Integer>();
        for (int i = startY + 1; i < 8; i++) {
            if (isValidMove(board, startX, startY, startX, i)) {
                list.add((startX * 10) + i);
            } else {
                break;
            }
        }
        for (int i = startY - 1; i >= 0; i--) {
            if (isValidMove(board, startX, startY, startX, i)) {
                list.add((startX * 10) + i);
            } else {
                break;
            }
        }
        for (int i = startX + 1; i < 8; i++) {
            if (isValidMove(board, startX, startY, i, startY)) {
                list.add((i * 10) + startY);
            } else {
                break;
            }
        }
        for (int i = startX - 1; i >= 0; i--) {
            if (isValidMove(board, startX, startY, i, startY)) {
                list.add((i * 10) + startY);
            } else {
                break;
            }
        }
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
