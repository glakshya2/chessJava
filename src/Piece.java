import java.util.Vector;

public abstract class Piece {

    public boolean color; // true for white, false for black
    public boolean isFirstMove = true;

    Piece(boolean color) {
        this.color = color;
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

    public boolean isFirstMove() {
        return isFirstMove;
    }

    public void setFirstMove(boolean isFirstMove) {
        this.isFirstMove = isFirstMove;
    }

    public boolean isLeftCastlingPossible(Board currentBoard, Vector<Board> boardHistory) {
        return false;
    }

    public boolean isRightCastlingPossible(Board currentBoard, Vector<Board> boardHistory) {
        return false;
    }

    // returns true if move provided is valid
    boolean isValidMove(Board currentBoard, int startX, int startY, int endX, int endY, Vector<Board> boardHistory) {
        if (currentBoard.board[endX][endY] == null || this.color != currentBoard.board[endX][endY].color) {
            return true;
        }
        return false;
    }

    // return vector containing list of all possible moves at given point of time
    // int the form of xcoord*10 + ycoord
    abstract Vector<Integer> possibleMoves(Board currentBoard, int startX, int startY, Vector<Board> boardHistory);


    public boolean equals(Piece x, Board thisBoard, Board xBoard, int posX, int posY, Vector<Board> boardHistory) {
        String thisName = this.getClass().getSimpleName();
        String xName = x.getClass().getSimpleName();
        if(!thisName.equals(xName)){
            return false;
        }
        Vector<Integer> thisPiece = possibleMoves(thisBoard, posX, posY, boardHistory);
        Vector<Integer> xPiece = x.possibleMoves(xBoard, posX, posY, boardHistory);
        if(thisPiece.size()!=xPiece.size()){
            return false;
        }
        for(int i = 0; i<thisPiece.size(); i++){
            int pos = thisPiece.elementAt(i);
            if(!xPiece.contains(pos)){
                return false;
            }
        }
        return true;
    }

    public boolean isEnpassantPossible(Vector<Board> boardHistory, int startX, int startY, int direction){
        return false;
    }
}