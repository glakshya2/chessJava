//import java.util.*;

public class Board {
    public Piece[][] board = new Piece[8][8];
    public int whitePieces = 16;
    public int blackPieces = 16;

    void set() {
        board[0][0] = new Rook(true);
        board[1][0] = new Knight(true);
        board[2][0] = new Bishop(true);
        board[3][0] = new Queen(true);
        board[4][0] = new King(true);
        board[5][0] = new Bishop(true);
        board[6][0] = new Knight(true);
        board[7][0] = new Rook(true);
        for (int i = 0; i < 8; i++) {
            board[i][1] = new Pawn(true);
        }
        board[0][7] = new Rook(false);
        board[1][7] = new Knight(false);
        board[2][7] = new Bishop(false);
        board[3][7] = new Queen(false);
        board[4][7] = new King(false);
        board[5][7] = new Bishop(false);
        board[6][7] = new Knight(false);
        board[7][7] = new Rook(false);
        for (int i = 0; i < 8; i++) {
            board[i][6] = new Pawn(false);
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 3; j < 6; j++) {
                board[i][j] = null;
            }
        }
    }

    void updateBoard(int startX, int startY, int endX, int endY) {
        board[endX][endY] = board[startX][startY];
        board[startX][startY] = null;
    }
}