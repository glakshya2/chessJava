import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class MyFrame extends JFrame implements MouseListener {

    static boolean turn = true; // True for white, false for black

    static Board gameBoard = new Board();

    // Returns true if king of given color is under check
    static boolean isCheck(Board currentBoard, int kingX, int kingY, boolean color) {
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
                        if (turn) {
                            list = temp.board[i][j].possibleMoves(temp, i, j);
                        } else {
                            Board newBoard = new Board();
                            newBoard.createCopy(temp);
                            newBoard.setBoard(newBoard.flipBoard());
                            list = temp.board[i][j].possibleMoves(newBoard, 7 - i, 7 - j);
                            for (int k = 0; k < list.size(); k++) {
                                int listX = 7 - (list.get(k) / 10);
                                int listY = 7 - (list.get(k) % 10);
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
    static Vector<Integer> checkList(Board currentBoard, int kingX, int kingY) {
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
                            list1 = tempBoard.board[i][j].possibleMoves(tempBoard, i, j);
                        } else {
                            Board newBoard = new Board();
                            newBoard.createCopy(tempBoard);
                            newBoard.setBoard(newBoard.flipBoard());
                            list1 = tempBoard.board[i][j].possibleMoves(newBoard, 7 - i, 7 - j);
                            for (int k = 0; k < list1.size(); k++) {
                                int listX = 7 - (list1.get(k) / 10);
                                int listY = 7 - (list1.get(k) % 10);
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
    static boolean isCheckMate(Board currentBoard, int kingX, int kingY) {
        Board tempBoard = new Board();
        tempBoard.createCopy(currentBoard);
        boolean color = tempBoard.board[kingX][kingY].color;
        int newKingX = kingX;
        int newKingY = kingY;
        if (!isCheck(tempBoard, kingX, kingY, color)) {
            return false;
        }
        Vector<Integer> list = new Vector<Integer>();
        if (tempBoard.board[kingX][kingY].color) {
            list = tempBoard.board[kingX][kingY].possibleMoves(tempBoard, kingX, kingY);
        } else {
            Board newBoard = new Board();
            newBoard.createCopy(newBoard);
            newBoard.setBoard(newBoard.flipBoard());
            list = tempBoard.board[kingX][kingY].possibleMoves(newBoard, 7 - kingX, 7 - kingY);
            for (int k = 0; k < list.size(); k++) {
                int listX = 7 - (list.get(k) / 10);
                int listY = 7 - (list.get(k) % 10);
                list.set(k, ((listX * 10) + listY));
            }
        }
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                tempBoard.updateBoard(kingX, kingY, list.get(i) / 10, list.get(i) % 10);
                if (!isCheck(tempBoard, list.get(i) / 10, list.get(i) % 10, color)) {
                    tempBoard.createCopy(gameBoard);
                    return false;
                }
                tempBoard.createCopy(gameBoard);
            }
        }
        tempBoard.createCopy(gameBoard);
        boolean evadable = true;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (tempBoard.board[i][j] != null) {
                    if (tempBoard.board[i][j].color == tempBoard.board[kingX][kingY].color) {
                        Vector<Integer> possible = new Vector<Integer>();
                        if (tempBoard.board[i][j].color) {
                            possible = tempBoard.board[i][j].possibleMoves(tempBoard, i, j);
                        } else {
                            Board newBoard = new Board();
                            newBoard.createCopy(tempBoard);
                            newBoard.setBoard(newBoard.flipBoard());
                            possible = tempBoard.board[i][j].possibleMoves(newBoard, 7 - i, 7 - j);
                            for (int k = 0; k < list.size(); k++) {
                                int listX = 7 - (list.get(k) / 10);
                                int listY = 7 - (list.get(k) % 10);
                                list.set(k, ((listX * 10) + listY));
                            }
                        }
                        for (int k = 0; k < possible.size(); k++) {
                            if (i == kingX && j == kingY) {
                                newKingX = possible.get(k) / 10;
                                newKingY = possible.get(k) % 10;
                            }
                            tempBoard.updateBoard(i, j, possible.get(k) / 10, possible.get(k) % 10);
                            for (int l = 0; l < 8; l++) {
                                for (int m = 0; m < 8; m++) {
                                    if (tempBoard.board[l][m] != null) {
                                        if (tempBoard.board[l][m].color != color) {
                                            Vector<Integer> possible2 = new Vector<Integer>();
                                            if (tempBoard.board[l][m].color) {
                                                possible2 = tempBoard.board[l][m]
                                                        .possibleMoves(tempBoard, l, m);
                                            } else {
                                                Board newBoard = new Board();
                                                newBoard.createCopy(tempBoard);
                                                newBoard.setBoard(newBoard.flipBoard());
                                                possible2 = tempBoard.board[l][m].possibleMoves(newBoard, 7 - l, 7 - m);
                                                for (int n = 0; n < list.size(); n++) {
                                                    int listX = 7 - (list.get(n) / 10);
                                                    int listY = 7 - (list.get(n) % 10);
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
    static boolean isStaleMate(Board currentBoard, boolean color, int kingX, int kingY) {
        if (!isCheck(currentBoard, kingX, kingY, currentBoard.board[kingX][kingY].color)) {
            return false;
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (currentBoard.board[i][j] != null) {
                    if (currentBoard.board[i][j].color == color) {
                        Vector<Integer> list = new Vector<Integer>();
                        if (currentBoard.board[i][j].color) {
                            list = currentBoard.board[i][j].possibleMoves(currentBoard, i, j);
                        } else {
                            Board newBoard = new Board();
                            newBoard.createCopy(newBoard);
                            newBoard.setBoard(newBoard.flipBoard());
                            list = currentBoard.board[i][j].possibleMoves(newBoard, 7 - i, 7 - j);
                            for (int k = 0; k < list.size(); k++) {
                                int listX = 7 - (list.get(k) / 10);
                                int listY = 7 - (list.get(k) % 10);
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
        return true;
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

    // Highlights possible moves of selected piece in GUI
    public void highlightPossible(int x, int y) {
        clearHighlight();
        Vector<Integer> list;
        if (turn) {
            list = gameBoard.board[x][y].possibleMoves(gameBoard, x, y);
        } else {
            Board newBoard = new Board();
            newBoard.createCopy(gameBoard);
            newBoard.setBoard(newBoard.flipBoard());
            list = gameBoard.board[x][y].possibleMoves(newBoard, 7 - x, 7 - y);
            for (int k = 0; k < list.size(); k++) {
                int listX = 7 - (list.get(k) / 10);
                int listY = 7 - (list.get(k) % 10);
                list.set(k, ((listX * 10) + listY));
            }
        }
        if (gameBoard.board[selectedX][selectedY].getClass().getSimpleName().equals("Pawn")) {
            int position = -1;
            boolean found = false;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) / 10 == gameBoard.returnKingX(!turn)
                        && list.get(i) % 10 == gameBoard.returnKingY(!turn)) {
                    position = i;
                    found = true;
                    break;
                }
            }
            if (found) {
                list.remove(position);
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

    // Updates game state after making move
    public void updateGame(int x, int y) {
        clearHighlight();
        int compNo = getCompNo(selectedX, selectedY);
        int newCompNo = getCompNo(x, y);
        String s = gameBoard.board[selectedX][selectedY].getClass().getSimpleName();
        boolean color = gameBoard.board[selectedX][selectedY].color;
        ImageIcon img = returnImg(s, color);

        ((JPanel) mainPanel.getComponent(newCompNo)).removeAll();
        ((JPanel) mainPanel.getComponent(newCompNo)).add(new JLabel(img));
        ((JPanel) mainPanel.getComponent(compNo)).removeAll();
        ((JPanel) mainPanel.getComponent(compNo)).add(new JLabel(new ImageIcon()));

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
        } else {
            gameBoard.updateBoard(selectedX, selectedY, x, y);
        }
        updateGUI();

        isSelected = false;
        int kingX = gameBoard.returnKingX(!turn);
        int kingY = gameBoard.returnKingY(!turn);
        if (isCheck(gameBoard, kingX, kingY, !turn)) {
            JOptionPane.showMessageDialog(frame, "Check");
        }
        turn = !turn;
        if (isCheckMate(gameBoard, kingX, kingY)) {
            if (!turn) {
                JOptionPane.showMessageDialog(frame, "CheckMate!, White wins");
                gameBoard.set();
                updateGUI();
                turn = true;
            } else {
                JOptionPane.showMessageDialog(frame, "Checkmate!, Black Wins");
                gameBoard.set();
                updateGUI();
                turn = true;
            }
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

    public boolean isUserCastling(int x, int y) {
        if (turn) {
            if (gameBoard.board[gameBoard.returnKingX(turn)][gameBoard.returnKingY(turn)]
                    .isLeftCastlingPossible(gameBoard)
                    || gameBoard.board[gameBoard.returnKingX(turn)][gameBoard.returnKingY(turn)]
                            .isRightCastlingPossible(gameBoard)) {
                if ((x == 6 && y == 0) || (x == 2 && y == 0)) {
                    return true;
                }
            }
        } else {
            Board temp = new Board();
            temp.createCopy(gameBoard);
            temp.setBoard(temp.flipBoard());
            if (gameBoard.board[gameBoard.returnKingX(turn)][gameBoard.returnKingY(turn)].isLeftCastlingPossible(temp)
                    || gameBoard.board[gameBoard.returnKingX(turn)][gameBoard.returnKingY(turn)]
                            .isRightCastlingPossible(temp)) {
                if ((x == 6 && y == 7) || (x == 2 && y == 7)) {
                    return true;
                }
            }
        }
        return false;
    }
}
