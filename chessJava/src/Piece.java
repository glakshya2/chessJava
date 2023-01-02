import java.util.Vector;

public abstract class Piece {

    public boolean color; // true for white, false for black
    public boolean isFirstMove = true;
    
    Piece(boolean color) {
        this.color = color;
    }

    public boolean isRightCastlingPossible(Board gameBoard, boolean turn) {
        if (MyFrame.isCheck(gameBoard.board, gameBoard.returnKingX(turn), gameBoard.returnKingY(turn), turn)) {
            return false;
        }
        if (turn) {
            if (gameBoard.board[4][0] != null) {
                if (gameBoard.board[4][0].getClass().getSimpleName().equals("King")) {
                    if (gameBoard.board[4][0].isFirstMove) {
                        if (gameBoard.board[7][0] != null) {
                            if (gameBoard.board[7][0].getClass().getSimpleName().equals("Rook")) {
                                if (gameBoard.board[7][0].isFirstMove) {
                                    if (gameBoard.board[5][0] == null && gameBoard.board[6][0] == null) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            if (gameBoard.board[4][7] != null) {
                if (gameBoard.board[4][7].getClass().getSimpleName().equals("King")) {
                    if (gameBoard.board[4][7].isFirstMove) {
                        if (gameBoard.board[7][7] != null) {
                            if (gameBoard.board[7][7].getClass().getSimpleName().equals("Rook")) {
                                if (gameBoard.board[7][7].isFirstMove) {
                                    if (gameBoard.board[5][7] == null && gameBoard.board[6][7] == null) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isLeftCastlingPossible(Board gameBoard, boolean turn) {
        if (turn) {
            if (gameBoard.board[4][0] != null) {
                if (gameBoard.board[4][0].getClass().getSimpleName().equals("King")) {
                    if (gameBoard.board[4][0].isFirstMove) {
                        if (gameBoard.board[0][0] != null) {
                            if (gameBoard.board[0][0].getClass().getSimpleName().equals("Rook")) {
                                if (gameBoard.board[0][0].isFirstMove) {
                                    if (gameBoard.board[1][0] == null && gameBoard.board[2][0] == null
                                            && gameBoard.board[3][0] == null) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            if (gameBoard.board[4][7] != null) {
                if (gameBoard.board[4][7].getClass().getSimpleName().equals("King")) {
                    if (gameBoard.board[4][7].isFirstMove) {
                        if (gameBoard.board[0][7] != null) {
                            if (gameBoard.board[0][7].getClass().getSimpleName().equals("Rook")) {
                                if (gameBoard.board[0][7].isFirstMove) {
                                    if (gameBoard.board[1][7] == null && gameBoard.board[2][7] == null
                                            && gameBoard.board[3][7] == null) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public void updateFirstMove() {
        isFirstMove = false;
    }

    public boolean isColor() {
        return color;
    }

    public void setColor(boolean color) {
        this.color = color;
    }

    public boolean isFirstMove(){
        return isFirstMove;
    }

    public void setFirstMove(boolean isFirstMove) {
        this.isFirstMove = isFirstMove;
    }

    // returns true if move provided is valid
    boolean isValidMove(Piece[][] board, int startX, int startY, int endX, int endY) {
        if (board[endX][endY] == null || this.color != board[endX][endY].color) {
            return true;
        }
        return false;

    }

    // return vector containing list of all possible moves at given point of time
    // int the form of xcoord*10 + ycoord
    abstract Vector<Integer> possibleMoves(Piece[][] board, int startX, int startY);
}