import java.util.Vector;
public class Board {

    public Piece[][] board = new Piece[8][8];

    public void setBoard(Piece[][] p) {
        this.board = p;
    }

    public Piece[][] getBoard() {
        return board;
    }

    public boolean equals(Board x, Vector<Board> boardHistory) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (this.board[i][j] == null && x.board[i][j] == null) {
                    continue;
                } else if ((this.board[i][j] == null && x.board[i][j] != null)
                        || this.board[i][j] != null && x.board[i][j] == null) {
                    return false;
                } else if (!this.board[i][j].equals(x.board[i][j], this, x, i, j, boardHistory)) {
                    return false;
                }
            }
        }
        return true;
    }

    // update board when move is performed
    public void updateBoard(int startX, int startY, int endX, int endY) {
        board[startX][startY].updateFirstMove();
        board[endX][endY] = board[startX][startY];
        board[startX][startY] = null;
    }


    public void setNull(int posX, int posY){
        board[posX][posY] = null;
    }

    // Returns a flipped board for calculation of possible moves of black pieces
    public Piece[][] flipBoard() {
        Piece[][] flipped = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                flipped[7 - i][7 - j] = board[i][j];
            }
        }
        return flipped;
    }

    // Returns x coord of king of given color
    public int returnKingX(boolean color) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null) {
                    if (board[i][j].color == color) {
                        if (board[i][j].getClass().getSimpleName().equals("King")) {
                            return i;
                        }
                    }
                }
            }
        }
        return -1;
    }

    // Returns y coord of king of given color
    public int returnKingY(boolean color) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null) {
                    if (board[i][j].color == color) {
                        if (board[i][j].getClass().getSimpleName().equals("King")) {
                            return j;
                        }
                    }
                }
            }
        }
        return -1;
    }

    public Piece returnPiece(String name, boolean color) {
        if (name.equals("Pawn")) {
            return new Pawn(color);
        } else if (name.equals("Bishop")) {
            return new Bishop(color);
        } else if (name.equals("King")) {
            return new King(color);
        } else if (name.equals("Knight")) {
            return new Knight(color);
        } else if (name.equals("Queen")) {
            return new Queen(color);
        } else {
            return new Rook(color);
        }
    }

    public void createCopy(Board x) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (x.board[i][j] != null) {
                    board[i][j] = returnPiece(x.board[i][j].getClass().getSimpleName(), x.board[i][j].isColor());
                    board[i][j].setFirstMove(x.board[i][j].isFirstMove());
                } else {
                    board[i][j] = null;
                }
            }
        }
    }

    // Set positions of pieces to their initial positions
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
            for (int j = 2; j < 6; j++) {
                board[i][j] = null;
            }
        }
    }
}