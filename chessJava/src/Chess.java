import java.util.Vector;
//TODO: 2. pawn can not kill king but can check and checkmate king
//TODO: 3. king elephant swap 
//TODO: 4. pawn reach other end upgrade

public class Chess {
    static boolean isCheck(Piece[][] board, int kingX, int kingY) {
        boolean color = board[kingX][kingY].color;
        int king = (kingX * 10) + kingY;
        for (int i = 0; i < 8; i++) {
            Vector<Integer> list = new Vector<Integer>();
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null) {
                    if (color != board[i][j].color) {
                        list = board[i][j].possibleMoves(board, i, j);
                        for (int k = 0; k < list.size(); k++) {
                            if (list.get(k) == king) {
                                return true;
                            }
                        }
                        list.clear();
                    }
                }
            }
        }
        return false;
    }

    static Vector<Integer> checkList(Piece[][] board, int kingX, int kingY) {
        boolean color = board[kingX][kingY].color;
        int king = (kingX * 10) + kingY;
        Vector<Integer> list = new Vector<Integer>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; i < 8; j++) {
                if (board[i][j] != null) {
                    if (color != board[i][j].color) {
                        Vector<Integer> list1 = new Vector<Integer>();
                        list1 = board[i][j].possibleMoves(board, i, j);
                        for (int k = 0; k < list1.size(); k++) {
                            if (list1.get(k) == king) {
                                list.add((i * 10) + j);
                                break;
                            }
                        }
                    }
                }
            }
        }
        return list;
    }

    static boolean isCheckMate(Piece[][] board, int kingX, int kingY) {
        boolean color = board[kingX][kingY].color;
        boolean flag = true;
        Vector<Integer> list = new Vector<Integer>();
        list = board[kingX][kingY].possibleMoves(board, kingX, kingY);
        for (int i = 0; i < list.size(); i++) {
            Board temp = new Board();
            temp.board = board;
            temp.updateBoard(kingX, kingY, (list.get(i) / 10), (list.get(i) % 10));
            if (!isCheck(temp.board, list.get(i) / 10, list.get(i) % 10)) {
                return false;
            }
        }
        list = checkList(board, kingX, kingY);
        if (list.size() == 1) {
            for (int i = 0; i < 8; i++) {
                Vector<Integer> list1 = new Vector<Integer>();
                for (int j = 0; j < 8; j++) {
                    if (board[i][j] != null) {
                        if (board[i][j].color == color) {
                            list1 = board[i][j].possibleMoves(board, i, j);
                            for (int k = 0; k < list.size(); k++) {
                                for (int l = 0; l < list1.size(); l++) {
                                    if (list.get(k) == list1.get(l)) {
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < 8; i++) {
                Vector<Integer> list1 = new Vector<Integer>();
                for (int j = 0; j < 8; j++) {
                    if (board[i][j] != null) {
                        if (board[i][j].color == color) {
                            list1 = board[i][j].possibleMoves(board, i, j);
                            for (int k = 0; k < list1.size(); k++) {
                                Board temp = new Board();
                                temp.board = board;
                                temp.updateBoard(i, j, (list1.get(k) / 10), (list1.get(k) % 10));
                                if (!isCheck(temp.board, list.get(i) / 10, list.get(i) % 10)) {
                                    return false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return flag;
    }

    static boolean isStaleMate(Piece[][] board, boolean color, int kingX, int kingY) {
        if (!isCheck(board, kingX, kingY)) {
            return false;
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null) {
                    if (board[i][j].color == color) {
                        Vector<Integer> list = board[i][j].possibleMoves(board, i, j);
                        if (list.size() != 0) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Board gameBoard = new Board();
        gameBoard.set();
        myFrame a = new myFrame();
    }
}