import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class MyFrame extends JFrame implements MouseListener {

    static boolean turn = true; // True for white, false for black

    static Board gameBoard = new Board();

    static Vector<Board> boardHistory = new Vector<Board>();

    public static Vector<Board> deepCopy(Vector<Board> currentHistory) {
        Vector<Board> newVector = new Vector<Board>();
        for (int i = 0; i < currentHistory.size(); i++) {
            Board atI = currentHistory.elementAt(i);
            Board temp = new Board();
            temp.createCopy(atI);
            newVector.add(temp);
        }
        return newVector;
    }

    public static Vector<Board> flipHistoryVector(Vector<Board> currentHistory) {
        Vector<Board> flippedHistory = new Vector<Board>();
        for (int i = 0; i < currentHistory.size(); i++) {
            Board newBoard = new Board();
            newBoard.createCopy(currentHistory.elementAt(i));
            newBoard.setBoard(newBoard.flipBoard());
            flippedHistory.add(newBoard);
        }
        return flippedHistory;
    }

    // Returns true if king of given color is under check
    static boolean isCheck(Board currentBoard, int kingX, int kingY, boolean color, Vector<Board> currentHistory) {
        Vector<Board> tempHistory = deepCopy(currentHistory);
        int king = (kingX * 10) + kingY;
        for (int i = 0; i < 8; i++) {
            Vector<Integer> list;
            for (int j = 0; j < 8; j++) {
                if (currentBoard.board[i][j] != null) {
                    if (color != currentBoard.board[i][j].color) {
                        if (currentBoard.board[i][j].getClass().getSimpleName().equals("King")) {
                            continue;
                        }
                        Board temp = new Board();
                        temp.createCopy(currentBoard);
                        if (temp.board[i][j].color) {
                            list = temp.board[i][j].possibleMoves(temp, i, j, tempHistory);
                        } else {
                            Board newBoard = new Board();
                            newBoard.createCopy(temp);
                            newBoard.setBoard(newBoard.flipBoard());
                            tempHistory = flipHistoryVector(tempHistory);
                            list = temp.board[i][j].possibleMoves(newBoard, 7 - i, 7 - j, tempHistory);
                            tempHistory = deepCopy(currentHistory);
                            for (int k = 0; k < list.size(); k++) {
                                int listX = 7 - (list.elementAt(k) / 10);
                                int listY = 7 - (list.elementAt(k) % 10);
                                list.set(k, ((listX * 10) + listY));
                            }
                        }
                        if (list.contains(king)) {
                            return true;
                        }
                        list.clear();
                    }
                }
            }
        }
        return false;
    }

    // Returns a vector containing positions of pieces that the given king is under
    // check from
    static Vector<Integer> checkList(Board currentBoard, int kingX, int kingY, Vector<Board> currentHistory) {
        Vector<Board> tempHistory = deepCopy(currentHistory);
        Board tempBoard = new Board();
        tempBoard.createCopy(currentBoard);
        boolean color = tempBoard.board[kingX][kingY].color;
        int king = (kingX * 10) + kingY;
        Vector<Integer> list = new Vector<Integer>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (tempBoard.board[i][j] != null) {
                    if (color != tempBoard.board[i][j].color) {
                        Vector<Integer> list1 = new Vector<Integer>();
                        if (tempBoard.board[i][j].color) {
                            list1 = tempBoard.board[i][j].possibleMoves(tempBoard, i, j, tempHistory);
                        } else {
                            Board newBoard = new Board();
                            newBoard.createCopy(tempBoard);
                            newBoard.setBoard(newBoard.flipBoard());
                            tempHistory = flipHistoryVector(tempHistory);
                            list1 = tempBoard.board[i][j].possibleMoves(newBoard, 7 - i, 7 - j, tempHistory);
                            tempHistory = deepCopy(currentHistory);
                            for (int k = 0; k < list1.size(); k++) {
                                int listX = 7 - (list1.elementAt(k) / 10);
                                int listY = 7 - (list1.elementAt(k) % 10);
                                list1.set(k, ((listX * 10) + listY));
                            }
                        }
                        if (list1.contains(king)) {
                            list.add((i * 10) + j);

                        }
                    }
                }
            }
        }
        return list;
    }

    // Returns true if there is Check kMate
    static boolean isCheckMate(Board currentBoard, int kingX, int kingY, Vector<Board> currentHistory) {
        Vector<Board> tempHistory = deepCopy(currentHistory);
        Board tempBoard = new Board();
        tempBoard.createCopy(currentBoard);
        boolean color = tempBoard.board[kingX][kingY].color;
        int newKingX = kingX;
        int newKingY = kingY;
        if (!isCheck(tempBoard, kingX, kingY, color, tempHistory)) {
            return false;
        }
        Vector<Integer> list = new Vector<Integer>();
        if (tempBoard.board[kingX][kingY].color) {
            list = tempBoard.board[kingX][kingY].possibleMoves(tempBoard, kingX, kingY, tempHistory);
        } else {
            Board newBoard = new Board();
            newBoard.createCopy(tempBoard);
            newBoard.setBoard(newBoard.flipBoard());
            tempHistory = flipHistoryVector(tempHistory);
            list = tempBoard.board[kingX][kingY].possibleMoves(newBoard, 7 - kingX, 7 - kingY, tempHistory);
            tempBoard.createCopy(currentBoard);
            tempHistory = deepCopy(currentHistory);
            for (int k = 0; k < list.size(); k++) {
                int listX = 7 - (list.elementAt(k) / 10);
                int listY = 7 - (list.elementAt(k) % 10);
                list.set(k, ((listX * 10) + listY));
            }
        }
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                if (isUserCastling(list.elementAt(i) / 10, list.elementAt(i) % 10)) {
                    if (turn) {
                        if (list.elementAt(i) / 10 == 6) {
                            tempBoard.updateBoard(4, 0, 6, 0);
                            tempBoard.updateBoard(7, 0, 5, 0);
                        } else if (list.elementAt(i) / 10 == 2) {
                            tempBoard.updateBoard(4, 0, 2, 0);
                            tempBoard.updateBoard(0, 0, 3, 0);
                        }
                    } else {
                        if (list.elementAt(i) / 10 == 6) {
                            tempBoard.updateBoard(4, 7, 6, 7);
                            tempBoard.updateBoard(7, 7, 5, 7);
                        } else if (list.elementAt(i) / 10 == 2) {
                            tempBoard.updateBoard(4, 7, 2, 7);
                            tempBoard.updateBoard(0, 7, 3, 7);
                        }
                    }
                } else {
                    tempBoard.updateBoard(kingX, kingY, list.elementAt(i) / 10, list.elementAt(i) % 10);
                }
                Board newBoard = new Board();
                newBoard.createCopy(tempBoard);
                tempHistory.add(newBoard);
                if (!isCheck(tempBoard, list.elementAt(i) / 10, list.elementAt(i) % 10, color, tempHistory)) {
                    tempBoard.createCopy(currentBoard);
                    return false;
                }
                tempBoard.createCopy(currentBoard);
                tempHistory = deepCopy(currentHistory);
            }
        }
        tempBoard.createCopy(currentBoard);
        tempHistory = deepCopy(currentHistory);
        boolean evadable = true;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (tempBoard.board[i][j] != null) {
                    if (tempBoard.board[i][j].color == tempBoard.board[kingX][kingY].color) {
                        Vector<Integer> possible = new Vector<Integer>();
                        if (tempBoard.board[i][j].color) {
                            possible = tempBoard.board[i][j].possibleMoves(tempBoard, i, j, tempHistory);
                        } else {
                            Board newBoard = new Board();
                            newBoard.createCopy(tempBoard);
                            newBoard.setBoard(newBoard.flipBoard());
                            tempHistory = flipHistoryVector(tempHistory);
                            possible = tempBoard.board[i][j].possibleMoves(newBoard, 7 - i, 7 - j, tempHistory);
                            tempHistory = deepCopy(currentHistory);
                            for (int k = 0; k < list.size(); k++) {
                                int listX = 7 - (list.elementAt(k) / 10);
                                int listY = 7 - (list.elementAt(k) % 10);
                                list.set(k, ((listX * 10) + listY));
                            }
                        }
                        for (int k = 0; k < possible.size(); k++) {
                            if (!tempBoard.board[i][j].getClass().getSimpleName().equals("Pawn")) {
                                if (isUserCastling(possible.elementAt(k) / 10, possible.elementAt(k) % 10)) {
                                    if (turn) {
                                        if (possible.elementAt(k) / 10 == 6) {
                                            tempBoard.updateBoard(4, 0, 6, 0);
                                            tempBoard.updateBoard(7, 0, 5, 0);
                                        } else if (possible.elementAt(k) / 10 == 2) {
                                            tempBoard.updateBoard(4, 0, 2, 0);
                                            tempBoard.updateBoard(0, 0, 3, 0);
                                        }
                                    } else {
                                        if (possible.elementAt(k) / 10 == 6) {
                                            tempBoard.updateBoard(4, 7, 6, 7);
                                            tempBoard.updateBoard(7, 7, 5, 7);
                                        } else if (list.elementAt(k) / 10 == 2) {
                                            tempBoard.updateBoard(4, 7, 2, 7);
                                            tempBoard.updateBoard(0, 7, 3, 7);
                                        }
                                    }
                                } else {
                                    tempBoard.updateBoard(i, j, possible.elementAt(k) / 10, possible.elementAt(k) % 10);
                                }
                                if (i == kingX && j == kingY) {
                                    newKingX = possible.elementAt(k) / 10;
                                    newKingY = possible.elementAt(k) % 10;
                                }
                                Board history = new Board();
                                history.createCopy(tempBoard);
                                tempHistory.add(history);
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        if (tempBoard.board[l][m] != null) {
                                            if (tempBoard.board[l][m].color != color) {
                                                Vector<Integer> possible2 = new Vector<Integer>();
                                                if (tempBoard.board[l][m].color) {
                                                    possible2 = tempBoard.board[l][m]
                                                            .possibleMoves(tempBoard, l, m, tempHistory);
                                                } else {
                                                    Board newBoard = new Board();
                                                    newBoard.createCopy(tempBoard);
                                                    newBoard.setBoard(newBoard.flipBoard());
                                                    Board temp = new Board();
                                                    temp.createCopy(newBoard);
                                                    tempHistory = flipHistoryVector(tempHistory);
                                                    tempHistory.add(temp);
                                                    possible2 = tempBoard.board[l][m].possibleMoves(newBoard, 7 - l,
                                                            7 - m,
                                                            tempHistory);
                                                    tempHistory = deepCopy(currentHistory);
                                                    for (int n = 0; n < list.size(); n++) {
                                                        int listX = 7 - (list.elementAt(n) / 10);
                                                        int listY = 7 - (list.elementAt(n) % 10);
                                                        list.set(n, ((listX * 10) + listY));
                                                    }
                                                }
                                                if (possible2.contains((newKingX * 10) + newKingY)) {
                                                    evadable = false;
                                                }
                                            }
                                        }
                                    }
                                }
                                tempBoard.createCopy(gameBoard);
                                tempHistory = deepCopy(currentHistory);
                            } else if (isThisEnpassant(tempBoard, i, j, possible.elementAt(k) / 10,
                                    possible.elementAt(k) % 10,
                                    tempHistory)) {
                                if (turn) {
                                    tempBoard.setNull(possible.elementAt(k) / 10, (possible.elementAt(k) % 10) - 1);
                                } else {
                                    tempBoard.setNull(possible.elementAt(k) / 10, (possible.elementAt(k) % 10) + 1);
                                }
                                tempBoard.updateBoard(i, j, possible.elementAt(k) / 10, possible.elementAt(k) % 10);
                                Board history = new Board();
                                history.createCopy(tempBoard);
                                tempHistory.add(history);
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        if (tempBoard.board[l][m] != null) {
                                            if (tempBoard.board[l][m].color != color) {
                                                Vector<Integer> possible2 = new Vector<Integer>();
                                                if (tempBoard.board[l][m].color) {
                                                    possible2 = tempBoard.board[l][m]
                                                            .possibleMoves(tempBoard, l, m, tempHistory);
                                                } else {
                                                    Board newBoard = new Board();
                                                    newBoard.createCopy(tempBoard);
                                                    newBoard.setBoard(newBoard.flipBoard());
                                                    Board temp = new Board();
                                                    temp.createCopy(newBoard);
                                                    tempHistory = flipHistoryVector(tempHistory);
                                                    tempHistory.add(temp);
                                                    possible2 = tempBoard.board[l][m].possibleMoves(newBoard, 7 - l,
                                                            7 - m,
                                                            tempHistory);
                                                    tempHistory = deepCopy(currentHistory);
                                                    for (int n = 0; n < list.size(); n++) {
                                                        int listX = 7 - (list.elementAt(n) / 10);
                                                        int listY = 7 - (list.elementAt(n) % 10);
                                                        list.set(n, ((listX * 10) + listY));
                                                    }
                                                }
                                                if (possible2.contains((newKingX * 10) + newKingY)) {
                                                    evadable = false;
                                                }
                                            }
                                        }
                                    }
                                }
                                tempBoard.createCopy(gameBoard);
                                tempHistory = deepCopy(currentHistory);
                            } else if ((turn && possible.elementAt(k) % 10 == 7)
                                    || ((!turn) && possible.elementAt(k) % 10 == 0)) {
                                tempBoard.updateBoard(i, j, possible.elementAt(k) / 10, possible.elementAt(k) % 10);
                                tempBoard.board[possible.elementAt(k) / 10][possible.elementAt(k) % 10] = new Queen(
                                        tempBoard.board[possible.elementAt(k) / 10][possible.elementAt(k) % 10].color);
                                Board history = new Board();
                                history.createCopy(tempBoard);
                                tempHistory.add(history);
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        if (tempBoard.board[l][m] != null) {
                                            if (tempBoard.board[l][m].color != color) {
                                                Vector<Integer> possible2 = new Vector<Integer>();
                                                if (tempBoard.board[l][m].color) {
                                                    possible2 = tempBoard.board[l][m]
                                                            .possibleMoves(tempBoard, l, m, tempHistory);
                                                } else {
                                                    Board newBoard = new Board();
                                                    newBoard.createCopy(tempBoard);
                                                    newBoard.setBoard(newBoard.flipBoard());
                                                    Board temp = new Board();
                                                    temp.createCopy(newBoard);
                                                    tempHistory = flipHistoryVector(tempHistory);
                                                    tempHistory.add(temp);
                                                    possible2 = tempBoard.board[l][m].possibleMoves(newBoard, 7 - l,
                                                            7 - m,
                                                            tempHistory);
                                                    tempHistory = deepCopy(currentHistory);
                                                    for (int n = 0; n < list.size(); n++) {
                                                        int listX = 7 - (list.elementAt(n) / 10);
                                                        int listY = 7 - (list.elementAt(n) % 10);
                                                        list.set(n, ((listX * 10) + listY));
                                                    }
                                                }
                                                if (possible2.contains((newKingX * 10) + newKingY)) {
                                                    evadable = false;
                                                }
                                            }
                                        }
                                    }
                                }
                                tempBoard.createCopy(gameBoard);
                                tempHistory = deepCopy(currentHistory);
                                tempBoard.board[possible.elementAt(k) / 10][possible.elementAt(k) % 10] = new Rook(
                                        tempBoard.board[possible.elementAt(k) / 10][possible.elementAt(k) % 10].color);
                                history.createCopy(tempBoard);
                                tempHistory.add(history);
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        if (tempBoard.board[l][m] != null) {
                                            if (tempBoard.board[l][m].color != color) {
                                                Vector<Integer> possible2 = new Vector<Integer>();
                                                if (tempBoard.board[l][m].color) {
                                                    possible2 = tempBoard.board[l][m]
                                                            .possibleMoves(tempBoard, l, m, tempHistory);
                                                } else {
                                                    Board newBoard = new Board();
                                                    newBoard.createCopy(tempBoard);
                                                    newBoard.setBoard(newBoard.flipBoard());
                                                    Board temp = new Board();
                                                    temp.createCopy(newBoard);
                                                    tempHistory = flipHistoryVector(tempHistory);
                                                    tempHistory.add(temp);
                                                    possible2 = tempBoard.board[l][m].possibleMoves(newBoard, 7 - l,
                                                            7 - m,
                                                            tempHistory);
                                                    tempHistory = deepCopy(currentHistory);
                                                    for (int n = 0; n < list.size(); n++) {
                                                        int listX = 7 - (list.elementAt(n) / 10);
                                                        int listY = 7 - (list.elementAt(n) % 10);
                                                        list.set(n, ((listX * 10) + listY));
                                                    }
                                                }
                                                if (possible2.contains((newKingX * 10) + newKingY)) {
                                                    evadable = false;
                                                }
                                            }
                                        }
                                    }
                                }
                                tempBoard.createCopy(gameBoard);
                                tempHistory = deepCopy(currentHistory);
                                tempBoard.board[possible.elementAt(k) / 10][possible.elementAt(k) % 10] = new Bishop(
                                        tempBoard.board[possible.elementAt(k) / 10][possible.elementAt(k) % 10].color);
                                history.createCopy(tempBoard);
                                tempHistory.add(history);
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        if (tempBoard.board[l][m] != null) {
                                            if (tempBoard.board[l][m].color != color) {
                                                Vector<Integer> possible2 = new Vector<Integer>();
                                                if (tempBoard.board[l][m].color) {
                                                    possible2 = tempBoard.board[l][m]
                                                            .possibleMoves(tempBoard, l, m, tempHistory);
                                                } else {
                                                    Board newBoard = new Board();
                                                    newBoard.createCopy(tempBoard);
                                                    newBoard.setBoard(newBoard.flipBoard());
                                                    Board temp = new Board();
                                                    temp.createCopy(newBoard);
                                                    tempHistory = flipHistoryVector(tempHistory);
                                                    tempHistory.add(temp);
                                                    possible2 = tempBoard.board[l][m].possibleMoves(newBoard, 7 - l,
                                                            7 - m,
                                                            tempHistory);
                                                    tempHistory = deepCopy(currentHistory);
                                                    for (int n = 0; n < list.size(); n++) {
                                                        int listX = 7 - (list.elementAt(n) / 10);
                                                        int listY = 7 - (list.elementAt(n) % 10);
                                                        list.set(n, ((listX * 10) + listY));
                                                    }
                                                }
                                                if (possible2.contains((newKingX * 10) + newKingY)) {
                                                    evadable = false;
                                                }
                                            }
                                        }
                                    }
                                }
                                tempBoard.createCopy(gameBoard);
                                tempHistory = deepCopy(currentHistory);
                                tempBoard.board[possible.elementAt(k) / 10][possible.elementAt(k) % 10] = new Knight(
                                        tempBoard.board[possible.elementAt(k) / 10][possible.elementAt(k) % 10].color);
                                history.createCopy(tempBoard);
                                tempHistory.add(history);
                                for (int l = 0; l < 8; l++) {
                                    for (int m = 0; m < 8; m++) {
                                        if (tempBoard.board[l][m] != null) {
                                            if (tempBoard.board[l][m].color != color) {
                                                Vector<Integer> possible2 = new Vector<Integer>();
                                                if (tempBoard.board[l][m].color) {
                                                    possible2 = tempBoard.board[l][m]
                                                            .possibleMoves(tempBoard, l, m, tempHistory);
                                                } else {
                                                    Board newBoard = new Board();
                                                    newBoard.createCopy(tempBoard);
                                                    newBoard.setBoard(newBoard.flipBoard());
                                                    Board temp = new Board();
                                                    temp.createCopy(newBoard);
                                                    tempHistory = flipHistoryVector(tempHistory);
                                                    tempHistory.add(temp);
                                                    possible2 = tempBoard.board[l][m].possibleMoves(newBoard, 7 - l,
                                                            7 - m,
                                                            tempHistory);
                                                    tempHistory = deepCopy(currentHistory);
                                                    for (int n = 0; n < list.size(); n++) {
                                                        int listX = 7 - (list.elementAt(n) / 10);
                                                        int listY = 7 - (list.elementAt(n) % 10);
                                                        list.set(n, ((listX * 10) + listY));
                                                    }
                                                }
                                                if (possible2.contains((newKingX * 10) + newKingY)) {
                                                    evadable = false;
                                                }
                                            }
                                        }
                                    }
                                }
                                tempBoard.createCopy(gameBoard);
                                tempHistory = deepCopy(currentHistory);
                            }
                        }
                    }
                }
            }
        }
        if (evadable) {
            return false;
        }
        return true;
    }

    // Returns true if game has reached Stale Mate
    static boolean isStaleMate(Board currentBoard, boolean color, int kingX, int kingY, Vector<Board> currentHistory) {
        Vector<Board> tempHistory = deepCopy(currentHistory);
        int count = 0;
        for (int i = tempHistory.size() - 1; i >= 0; i--) {
            Board tempBoard = tempHistory.elementAt(i);
            if (tempBoard.equals(gameBoard)) {
                count++;
            }
        }
        if (count >= 3) {
            return true;
        }
        if (!isCheck(currentBoard, kingX, kingY, currentBoard.board[kingX][kingY].color, tempHistory)) {
            return false;
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (currentBoard.board[i][j] != null) {
                    if (currentBoard.board[i][j].color == color) {
                        Vector<Integer> list = new Vector<Integer>();
                        if (currentBoard.board[i][j].color) {
                            list = currentBoard.board[i][j].possibleMoves(currentBoard, i, j, tempHistory);
                        } else {
                            Board newBoard = new Board();
                            newBoard.createCopy(newBoard);
                            newBoard.setBoard(newBoard.flipBoard());
                            tempHistory = flipHistoryVector(tempHistory);
                            list = currentBoard.board[i][j].possibleMoves(newBoard, 7 - i, 7 - j, tempHistory);
                            tempHistory = deepCopy(currentHistory);
                            for (int k = 0; k < list.size(); k++) {
                                int listX = 7 - (list.elementAt(k) / 10);
                                int listY = 7 - (list.elementAt(k) % 10);
                                list.set(k, ((listX * 10) + listY));
                            }
                        }
                        if (!list.isEmpty()) {
                            return false;
                        }
                    }
                }
            }
        }

        return false;
    }

    JPanel mainPanel;
    JPanel panel;
    JFrame frame;

    Vector<Integer> highlighted = new Vector<Integer>();

    boolean isSelected = false;

    int selectedX;

    int selectedY;

    // Creates GUI and sets up board
    public MyFrame() {

        gameBoard.set();

        frame = new JFrame("Chess");
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(8, 8));
        int n = 0;
        int a = 1;
        int b = 0;

        for (int i = 0; i < 64; i++) {
            panel = new JPanel();
            LayoutManager overlay = new OverlayLayout(panel);
            panel.setLayout(overlay);
            panel.add(new JLabel(new ImageIcon()));
            panel.setAlignmentX(0.5f);
            panel.setAlignmentY(0.5f);

            panel.addMouseListener(this);
            if (i % 2 == a) {
                panel.setBackground(new Color(46, 138, 87));
            }
            if (i % 2 == b) {
                panel.setBackground(new Color(255, 255, 255));
            }
            if (i % 8 == 7) {
                int temp = a;
                a = b;
                b = temp;
            }
            mainPanel.add(panel, n);
            n++;
        }

        for (int i = 0; i < 16; i++) {
            ((JPanel) mainPanel.getComponent(i)).remove(0);
            String s = gameBoard.board[getX(i)][getY(i)].getClass().getSimpleName();
            boolean color = gameBoard.board[getX(i)][getY(i)].color;
            ImageIcon img = returnImg(s, color);
            ((JPanel) mainPanel.getComponent(i)).add(new JLabel(img));
        }
        for (int i = 48; i < 64; i++) {
            ((JPanel) mainPanel.getComponent(i)).remove(0);
            String s = gameBoard.board[getX(i)][getY(i)].getClass().getSimpleName();
            boolean color = gameBoard.board[getX(i)][getY(i)].color;
            ImageIcon img = returnImg(s, color);
            ((JPanel) mainPanel.getComponent(i)).add(new JLabel(img));
        }
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setSize(640, 640);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
    }

    // Returns an ImageIcon for given piece
    public ImageIcon returnImg(String s, boolean color) {
        if (s.equals("Bishop") && color) {
            return new ImageIcon("images/BishopW.png");
        } else if (s.equals("Bishop") && !color) {
            return new ImageIcon("images/BishopB.png");
        } else if (s.equals("King") && color) {
            return new ImageIcon("images/KingW.png");
        } else if (s.equals("King") && !color) {
            return new ImageIcon("images/KingB.png");
        } else if (s.equals("Knight") && color) {
            return new ImageIcon("images/HorseW.png");
        } else if (s.equals("Knight") && !color) {
            return new ImageIcon("images/HorseB.png");
        } else if (s.equals("Pawn") && color) {
            return new ImageIcon("images/PawnW.png");
        } else if (s.equals("Pawn") && !color) {
            return new ImageIcon("images/PawnB.png");
        } else if (s.equals("Rook") && color) {
            return new ImageIcon("images/RookW.png");
        } else if (s.equals("Rook") && !color) {
            return new ImageIcon("images/RookB.png");
        } else if (s.equals("Queen") && color) {
            return new ImageIcon("images/QueenW.png");
        } else if (s.equals("Queen") && !color) {
            return new ImageIcon("images/QueenB.png");
        } else {
            return null;
        }
    }

    // Returns Component no for updating components in gui
    public int getCompNo(int x, int y) {
        return 63 - (8 * (y + 1)) + (x + 1);
    }

    // Returns x coord of given component
    public int getX(int compNo) {
        return compNo % 8;
    }

    // Returns y coord of given component
    public int getY(int compNo) {
        return 7 - (compNo / 8);
    }

    // Removes all yellow dots used for highlighting possible moves from GUI
    public void clearHighlight() {
        for (int i = 0; i < highlighted.size(); i++) {
            if (gameBoard.board[getX(highlighted.elementAt(i))][getY(highlighted.elementAt(i))] != null) {
                String s = gameBoard.board[getX(highlighted.elementAt(i))][getY(highlighted.elementAt(i))].getClass()
                        .getSimpleName();
                boolean color = gameBoard.board[getX(highlighted.elementAt(i))][getY(highlighted.elementAt(i))].color;
                ImageIcon img = returnImg(s, color);
                ((JPanel) mainPanel.getComponent(highlighted.elementAt(i))).removeAll();
                ((JPanel) mainPanel.getComponent(highlighted.elementAt(i))).add(new JLabel(img));
            } else {
                ((JPanel) mainPanel.getComponent(highlighted.elementAt(i))).removeAll();
                ((JPanel) mainPanel.getComponent(highlighted.elementAt(i))).add(new JLabel(new ImageIcon()));
            }
        }
        mainPanel.validate();
        mainPanel.repaint();
        highlighted.clear();
    }

    public boolean isNotStupid(Board currentBoard, int startX, int startY, int endX, int endY) {
        Board tempBoard = new Board();
        tempBoard.createCopy(currentBoard);
        if (isUserCastling(endX, endY)) {
            if (turn) {
                if (endX == 6) {
                    tempBoard.updateBoard(4, 0, 6, 0);
                    tempBoard.updateBoard(7, 0, 5, 0);
                } else if (endX == 2) {
                    tempBoard.updateBoard(4, 0, 2, 0);
                    tempBoard.updateBoard(0, 0, 3, 0);
                }
            } else {
                if (endX == 6) {
                    tempBoard.updateBoard(4, 7, 6, 7);
                    tempBoard.updateBoard(7, 7, 5, 7);
                } else if (endX == 2) {
                    tempBoard.updateBoard(4, 7, 2, 7);
                    tempBoard.updateBoard(0, 7, 3, 7);
                }
            }
        } else if (isThisEnpassant(gameBoard, selectedX, selectedY, endX, endY, boardHistory)) {
            tempBoard.updateBoard(selectedX, selectedY, endX, endX);
            if (turn) {
                tempBoard.setNull(endX, endY - 1);
            } else {
                tempBoard.setNull(endX, endY + 1);
            }
        } else {
            tempBoard.updateBoard(selectedX, selectedY, endX, endY);
        }
        int kingX = tempBoard.returnKingX(turn);
        int kingY = tempBoard.returnKingY(turn);
        if (!isCheck(tempBoard, kingX, kingY, turn, boardHistory)) {
            return true;
        }
        return false;
    }

    // Highlights possible moves of selected piece in GUI
    public void highlightPossible(int x, int y) {
        clearHighlight();
        Vector<Integer> list;
        if (turn) {
            list = gameBoard.board[x][y].possibleMoves(gameBoard, x, y, boardHistory);
        } else {
            Board newBoard = new Board();
            newBoard.createCopy(gameBoard);
            newBoard.setBoard(newBoard.flipBoard());
            Vector<Board> tempHistory = flipHistoryVector(boardHistory);
            list = gameBoard.board[x][y].possibleMoves(newBoard, 7 - x, 7 - y, tempHistory);
            for (int k = 0; k < list.size(); k++) {
                int listX = 7 - (list.elementAt(k) / 10);
                int listY = 7 - (list.elementAt(k) % 10);
                list.set(k, ((listX * 10) + listY));
            }
        }
        if (gameBoard.board[selectedX][selectedY].getClass().getSimpleName().equals("Pawn")) {
            int position = -1;
            boolean found = false;
            for (int i = 0; i < list.size(); i++) {
                if (list.elementAt(i) / 10 == gameBoard.returnKingX(!turn)
                        && list.elementAt(i) % 10 == gameBoard.returnKingY(!turn)) {
                    position = i;
                    found = true;
                    break;
                }
            }
            if (found) {
                list.remove(position);
            }
        }
        if (isCheck(gameBoard, gameBoard.returnKingX(turn), gameBoard.returnKingY(turn), turn, boardHistory)) {
            Vector<Integer> indices = new Vector<Integer>();
            for (int i = 0; i < list.size(); i++) {
                if (!isNotStupid(gameBoard, x, y, list.elementAt(i) / 10, list.elementAt(i) % 10)) {
                    indices.add(i);
                }
            }
            for (int i = indices.size() - 1; i > -1; i--) {
                int z = indices.elementAt(i);
                list.remove(z);
            }
        }
        for (int i = 0; i < list.size(); i++) {
            int listX = list.elementAt(i) / 10;
            int listY = list.elementAt(i) % 10;
            int compNo;
            compNo = getCompNo(listX, listY);
            highlighted.add(compNo);
            if (gameBoard.board[listX][listY] != null) {
                String s = gameBoard.board[getX(highlighted.elementAt(i))][getY(highlighted.elementAt(i))].getClass()
                        .getSimpleName();
                boolean color = gameBoard.board[getX(highlighted.elementAt(i))][getY(highlighted.elementAt(i))].color;
                ImageIcon img = returnImg(s, color);
                ((JPanel) mainPanel.getComponent(highlighted.elementAt(i))).removeAll();
                ((JPanel) mainPanel.getComponent(compNo)).add(new JLabel(new ImageIcon("images/YellowDot.png")));
                ((JPanel) mainPanel.getComponent(highlighted.elementAt(i))).add(new JLabel(img));
            } else {
                ((JPanel) mainPanel.getComponent(compNo)).removeAll();
                ((JPanel) mainPanel.getComponent(compNo)).add(new JLabel(new ImageIcon("images/YellowDot.png")));
            }
        }
        mainPanel.validate();
        mainPanel.repaint();
    }

    // Selects piece that was clicked on and highlights possible moves
    public void selectPiece(int x, int y) {
        selectedX = x;
        selectedY = y;
        highlightPossible(x, y);
        if (!highlighted.isEmpty()) {
            isSelected = true;
        }
    }

    public void updateGUI() {
        for (int i = 0; i < 64; i++) {
            ((JPanel) mainPanel.getComponent(i)).removeAll();
            if (gameBoard.board[getX(i)][getY(i)] != null) {
                String s = gameBoard.board[getX(i)][getY(i)].getClass().getSimpleName();
                boolean color = gameBoard.board[getX(i)][getY(i)].color;
                ImageIcon img = returnImg(s, color);
                ((JPanel) mainPanel.getComponent(i)).add(new JLabel(img));
            }
        }
        mainPanel.validate();
        mainPanel.repaint();
    }

    public static boolean isThisEnpassant(Board currentBoard, int startX, int startY, int endX, int endY,
            Vector<Board> currentHistory) {
        if (currentHistory.size() < 2) {
            return false;
        }
        if (currentBoard.board[startX][startY] == null) {
            return false;
        }
        if (!currentBoard.board[startX][startY].getClass().getSimpleName().equals("Pawn")) {
            return false;
        }
        if (turn) {
            if (endY != 5) {
                return false;
            }
        } else {
            if (endY != 2) {
                return false;
            }
        }
        if (startX == endX) {
            return false;
        }

        if (startX - endX == 1) {
            if (startX - 1 <= 0) {
                if (currentBoard.board[startX - 1][startY] == null) {
                    return false;
                }
                if ((!currentBoard.board[startX - 1][startY].getClass().getSimpleName().equals("Pawn"))) {
                    return false;
                }
            }
        } else {
            if (startX + 1 < 8) {
                if (currentBoard.board[startX + 1][startY] == null) {
                    return false;
                }
                if ((!currentBoard.board[startX + 1][startY].getClass().getSimpleName().equals("Pawn"))) {
                    return false;
                }
            }
        }
        Board tempBoard = new Board();
        tempBoard.createCopy(currentHistory.elementAt(currentHistory.size() - 2));
        if (turn) {
            if (tempBoard.board[endX][4] != null) {
                return false;
            }
            if (tempBoard.board[endX][5] != null) {
                return false;
            }
            if (tempBoard.board[endX][6] == null) {
                return false;
            }
            if (!tempBoard.board[endX][6].getClass().getSimpleName().equals("Pawn")) {
                return false;
            }
        } else {
            if (tempBoard.board[endX][3] != null) {
                return false;
            }
            if (tempBoard.board[endX][2] != null) {
                return false;
            }
            if (tempBoard.board[endX][1] == null) {
                return false;
            }
            if (!tempBoard.board[endX][1].getClass().getSimpleName().equals("Pawn")) {
                return false;
            }
        }
        return true;
    }

    public int pawnPromotion() {
        String[] responses = { "Queen", "Bishop", "Knight", "Rook" };
        int ans = JOptionPane.showOptionDialog(frame, "Promote Pawn to?", "Pawn Promotion",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, responses, 0);
        return ans;
    }

    // Updates game state after making move
    public void updateGame(int x, int y) {
        clearHighlight();
        if (isUserCastling(x, y)) {
            if (turn) {
                if (x == 6) {
                    gameBoard.updateBoard(4, 0, 6, 0);
                    gameBoard.updateBoard(7, 0, 5, 0);
                } else if (x == 2) {
                    gameBoard.updateBoard(4, 0, 2, 0);
                    gameBoard.updateBoard(0, 0, 3, 0);
                }
            } else {
                if (x == 6) {
                    gameBoard.updateBoard(4, 7, 6, 7);
                    gameBoard.updateBoard(7, 7, 5, 7);
                } else if (x == 2) {
                    gameBoard.updateBoard(4, 7, 2, 7);
                    gameBoard.updateBoard(0, 7, 3, 7);
                }
            }
        } else if (isThisEnpassant(gameBoard, selectedX, selectedY, x, y, boardHistory)) {
            gameBoard.updateBoard(selectedX, selectedY, x, y);
            if (turn) {
                gameBoard.setNull(x, y - 1);
            } else {
                gameBoard.setNull(x, y + 1);
            }
        } else {
            gameBoard.updateBoard(selectedX, selectedY, x, y);
        }
        if (turn) {
            if (y == 7) {
                if (gameBoard.board[x][y].getClass().getSimpleName().equals("Pawn")) {
                    int option = pawnPromotion();
                    if (option == 0) {
                        gameBoard.board[x][y] = new Queen(true);
                    } else if (option == 1) {
                        gameBoard.board[x][y] = new Bishop(true);
                    } else if (option == 2) {
                        gameBoard.board[x][y] = new Knight(true);
                    } else if (option == 3) {
                        gameBoard.board[x][y] = new Rook(true);
                    }
                }
            }
        } else {
            if (y == 0) {
                if (gameBoard.board[x][y].getClass().getSimpleName().equals("Pawn")) {
                    int option = pawnPromotion();
                    if (option == 0) {
                        gameBoard.board[x][y] = new Queen(false);
                    } else if (option == 1) {
                        gameBoard.board[x][y] = new Bishop(false);
                    } else if (option == 2) {
                        gameBoard.board[x][y] = new Knight(false);
                    } else if (option == 3) {
                        gameBoard.board[x][y] = new Rook(false);
                    }
                }
            }
        }
        Board tempBoard = new Board();
        tempBoard.createCopy(gameBoard);
        boardHistory.add(tempBoard);
        updateGUI();
        isSelected = false;
        int kingX = gameBoard.returnKingX(!turn);
        int kingY = gameBoard.returnKingY(!turn);
        if (isCheck(gameBoard, kingX, kingY, !turn, boardHistory)) {
            JOptionPane.showMessageDialog(frame, "Check");
        }
        turn = !turn;
        if (isCheckMate(gameBoard, kingX, kingY, boardHistory)) {
            if (!turn) {
                JOptionPane.showMessageDialog(frame, "CheckMate!, White wins");
                gameBoard.set();
                updateGUI();
                turn = true;
                boardHistory.clear();
            } else {
                JOptionPane.showMessageDialog(frame, "Checkmate!, Black Wins");
                gameBoard.set();
                updateGUI();
                turn = true;
                boardHistory.clear();
            }
        }
        kingX = gameBoard.returnKingX(!turn);
        kingY = gameBoard.returnKingY(!turn);
        if (isStaleMate(gameBoard, !turn, kingX, kingY, boardHistory)) {
            JOptionPane.showMessageDialog(frame, "Stalemate");
            gameBoard.set();
            updateGUI();
            turn = true;
            boardHistory.clear();
        }
    }

    // Moves selected piece to selected position
    public void movePiece(int x, int y) {
        for (int i = 0; i < highlighted.size(); i++) {
            if (getX(highlighted.elementAt(i)) == x && getY(highlighted.elementAt(i)) == y) {
                updateGame(x, y);
                break;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // Not Needed
    }

    // Automatically Called when mouse is clicked
    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getComponent().getX() / e.getComponent().getWidth();
        int y = 7 - e.getComponent().getY() / e.getComponent().getHeight();
        if (gameBoard.board[x][y] != null) {
            if (gameBoard.board[x][y].color == turn) {
                selectPiece(x, y);
            } else if (isSelected) {
                movePiece(x, y);
            }
        } else if (isSelected) {
            movePiece(x, y);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Not Needed
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // Not Needed
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // Not Needed
    }

    public static boolean isUserCastling(int x, int y) {
        if (turn) {
            if (gameBoard.board[gameBoard.returnKingX(turn)][gameBoard.returnKingY(turn)]
                    .isLeftCastlingPossible(gameBoard, boardHistory)
                    || gameBoard.board[gameBoard.returnKingX(turn)][gameBoard.returnKingY(turn)]
                            .isRightCastlingPossible(gameBoard, boardHistory)) {
                if ((x == 6 && y == 0) || (x == 2 && y == 0)) {
                    return true;
                }
            }
        } else {
            Board temp = new Board();
            temp.createCopy(gameBoard);
            temp.setBoard(temp.flipBoard());
            Vector<Board> tempHistory = flipHistoryVector(boardHistory);
            if (gameBoard.board[gameBoard.returnKingX(turn)][gameBoard.returnKingY(turn)].isLeftCastlingPossible(temp,
                    tempHistory)
                    || gameBoard.board[gameBoard.returnKingX(turn)][gameBoard.returnKingY(turn)]
                            .isRightCastlingPossible(temp, tempHistory)) {
                if ((x == 6 && y == 7) || (x == 2 && y == 7)) {
                    return true;
                }
            }
        }
        return false;
    }
}