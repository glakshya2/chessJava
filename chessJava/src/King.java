import java.util.*;

public class King extends Piece {
    

    King(boolean color) {
        super(color);
    }

    boolean isValidMove(Piece[][] board, int startX, int startY, int endX, int endY) {
        
        if(MyFrame.isCheck(board, endX, endY)){
            return false;
        }

        boolean valid = super.isValidMove(board, startX, startY, endX, endY);

        if(endX+1<8){
            if(board[endX+1][endY].getClass().getSimpleName()=="King"){
                valid = false;
            }    
        }
        if(endX-1>=0){
            if(board[endX-1][endY].getClass().getSimpleName()=="King"){
                valid = false;
            }
        }
        return valid;
    }

    Vector<Integer> possibleMoves(Piece[][] board, int startX, int startY) {
        Vector<Integer> list = new Vector<Integer>();
        if (isValidMove(board, startX, startY, startX, startY + 1) && startY + 1 < 8) {
            list.add((startX * 10) + (startY + 1));
        }
        if (isValidMove(board, startX, startY, startX, startY - 1) && startY - 1 >= 0) {
            list.add((startX * 10) + (startY - 1));
        }
        if (isValidMove(board, startX, startY, startX + 1, startY) && startX + 1 < 8) {
            list.add(((startX + 1) * 10) + startY);
        }
        if (isValidMove(board, startX, startY, startX - 1, startY) && startX - 1 >= 0) {
            list.add(((startX - 1) * 10) + startY);
        }
        if (isValidMove(board, startX, startY, startX + 1, startY + 1) && startX + 1 < 8 && startY + 1 < 8) {
            list.add(((startX + 1) * 10) + (startY + 1));
        }
        if (isValidMove(board, startX, startY, startX - 1, startY + 1) && startX - 1 >= 0 && startY + 1 < 8) {
            list.add(((startX - 1) * 10) + (startY + 1));
        }
        if (isValidMove(board, startX, startY, startX + 1, startY - 1) && startX + 1 < 8 && startY - 1 >= 0) {
            list.add(((startX + 1) * 10) + (startY - 1));
        }
        if (isValidMove(board, startX, startY, startX - 1, startY - 1) && startX - 1 >= 0 && startY - 1 >= 0) {
            list.add(((startX - 1) * 10) + (startY - 1));
        }
        return list;
    }
}