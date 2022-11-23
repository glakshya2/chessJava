import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;

public class MyFrame extends JFrame implements MouseListener {

    Board gameBoard = new Board();
    JPanel mainPanel;
    JPanel panel;
    JFrame frame;
    boolean turn = true; // True for white, false for black
    Vector<Integer> highlighted = new Vector<Integer>();
    boolean isSelected = false;
    int selectedX;
    int selectedY;

    // Returns an ImageIcon for given piece
    public ImageIcon returnImg(String s, boolean color) {
        if (s.equals("Bishop") && color) {
            return new ImageIcon("BishopW.png");
        } else if (s.equals("Bishop") && !color) {
            return new ImageIcon("BishopB.png");
        } else if (s.equals("King") && color) {
            return new ImageIcon("KingW.png");
        } else if (s.equals("King") && !color) {
            return new ImageIcon("KingB.png");
        } else if (s.equals("Knight") && color) {
            return new ImageIcon("HorseW.png");
        } else if (s.equals("Knight") && !color) {
            return new ImageIcon("HorseB.png");
        } else if (s.equals("Pawn") && color) {
            return new ImageIcon("PawnW.png");
        } else if (s.equals("Pawn") && !color) {
            return new ImageIcon("PawnB.png");
        } else if (s.equals("Rook") && color) {
            return new ImageIcon("RookW.png");
        } else if (s.equals("Rook") && !color) {
            return new ImageIcon("RookB.png");
        } else if (s.equals("Queen") && color) {
            return new ImageIcon("QueenW.png");
        } else if (s.equals("Queen") && !color) {
            return new ImageIcon("QueenB.png");
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

    // Removes all yellow dots used for highlighting possible movesfrom GUI
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
        Vector<Integer> list = new Vector<Integer>();
        if (turn) {
            list = gameBoard.board[x][y].possibleMoves(gameBoard.board, x, y);
        } else {
            list = gameBoard.board[x][y].possibleMoves(gameBoard.flipBoard(), 7 - x, 7 - y);
        }
        for (int i = 0; i < list.size(); i++) {
            int listX = list.elementAt(i) / 10;
            int listY = list.elementAt(i) % 10;
            if (!turn) {
                listX = 7 - listX;
                listY = 7 - listY;
            }
            int compNo;
            compNo = getCompNo(listX, listY);
            highlighted.add(compNo);
            if (gameBoard.board[listX][listY] != null) {
                String s = gameBoard.board[getX(highlighted.elementAt(i))][getY(highlighted.elementAt(i))].getClass()
                        .getSimpleName();
                boolean color = gameBoard.board[getX(highlighted.elementAt(i))][getY(highlighted.elementAt(i))].color;
                ImageIcon img = returnImg(s, color);
                ((JPanel) mainPanel.getComponent(highlighted.elementAt(i))).removeAll();
                ((JPanel) mainPanel.getComponent(compNo)).add(new JLabel(new ImageIcon("YellowDot.png")));
                ((JPanel) mainPanel.getComponent(highlighted.elementAt(i))).add(new JLabel(img));
            } else {
                ((JPanel) mainPanel.getComponent(compNo)).remove(0);
                ((JPanel) mainPanel.getComponent(compNo)).add(new JLabel(new ImageIcon("YellowDot.png")));
            }
            ((JPanel) mainPanel.getComponent(compNo)).remove(0);
            ((JPanel) mainPanel.getComponent(compNo)).add(new JLabel(new ImageIcon("YellowDot.png")));
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

    // Updates game state after making move
    public void updateGame(int x, int y) {
        clearHighlight();
        int compNo = getCompNo(selectedX, selectedY);
        int newCompNo = getCompNo(x, y);
        String s = gameBoard.board[selectedX][selectedY].getClass().getSimpleName();
        boolean color = gameBoard.board[selectedX][selectedY].color;
        ImageIcon img = returnImg(s, color);
        if (gameBoard.board[x][y] != null) {
            ((JPanel) mainPanel.getComponent(newCompNo)).removeAll();
            ((JPanel) mainPanel.getComponent(newCompNo)).add(new JLabel(img));
            ((JPanel) mainPanel.getComponent(compNo)).removeAll();
            ((JPanel) mainPanel.getComponent(compNo)).add(new JLabel(new ImageIcon()));
        } else {
            ((JPanel) mainPanel.getComponent(newCompNo)).removeAll();
            ((JPanel) mainPanel.getComponent(newCompNo)).add(new JLabel(img));
            ((JPanel) mainPanel.getComponent(compNo)).removeAll();
            ((JPanel) mainPanel.getComponent(compNo)).add(new JLabel(new ImageIcon()));
        }
        gameBoard.updateBoard(selectedX, selectedY, x, y);
        if (gameBoard.board[x][y].getClass().getSimpleName().equals("Pawn")) {
            gameBoard.board[x][y].updateFirstMove();
        }
        mainPanel.validate();
        isSelected = false;
        int kingX = gameBoard.returnKingX(!turn);
        int kingY = gameBoard.returnKingY(!turn);
        if (isCheckMate(gameBoard.board, kingX, kingY)) {
            JOptionPane.showMessageDialog(frame, "Check Mate");
        } else if (isCheck(gameBoard.board, kingX, kingY, !turn)) {
            JOptionPane.showMessageDialog(frame, "Check");
        }
        turn = !turn;
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

    // Returns true if king of given color is under check
    static boolean isCheck(Piece[][] board, int kingX, int kingY, boolean color) {
        int king = (kingX * 10) + kingY;
        for (int i = 0; i < 8; i++) {
            Vector<Integer> list = new Vector<Integer>();
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null) {
                    if (color != board[i][j].color) {
                        if (board[i][j].getClass().getSimpleName().equals("King")) {
                            continue;
                        }
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

    // Returns a vector containing positions of pieces that are given king is under
    // check from
    static Vector<Integer> checkList(Piece[][] board, int kingX, int kingY) {
        boolean color = board[kingX][kingY].color;
        int king = (kingX * 10) + kingY;
        Vector<Integer> list = new Vector<Integer>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null) {
                    if (color != board[i][j].color) {
                        Vector<Integer> list1 = board[i][j].possibleMoves(board, i, j);
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

    // Returns true if there iis Chec kMate
    static boolean isCheckMate(Piece[][] board, int kingX, int kingY) {
        boolean color = board[kingX][kingY].color;
        boolean flag = false;
        Vector<Integer> list = board[kingX][kingY].possibleMoves(board, kingX, kingY);
        for (int i = 0; i < list.size(); i++) {
            Board temp = new Board();
            temp.board = board;
            temp.updateBoard(kingX, kingY, (list.get(i) / 10), (list.get(i) % 10));
            if (!isCheck(temp.board, list.get(i) / 10, list.get(i) % 10,
                    temp.board[(list.get(i) / 10)][(list.get(i) % 10)].color)) {
                temp.updateBoard((list.get(i) / 10), (list.get(i) % 10), kingX, kingY);
                return false;
            }
            temp.updateBoard(list.get(i) / 10, list.get(i) % 10, kingX, kingY);
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
                                if (!isCheck(temp.board, list.get(i) / 10, list.get(i) % 10,
                                        temp.board[(list.get(i) / 10)][list.get(0 % 10)].color)) {
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

    // Returns true if game has reached Stale Mate
    static boolean isStaleMate(Piece[][] board, boolean color, int kingX, int kingY) {
        if (!isCheck(board, kingX, kingY, board[kingX][kingY].color)) {
            return false;
        }
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null) {
                    if (board[i][j].color == color) {
                        Vector<Integer> list = board[i][j].possibleMoves(board, i, j);
                        if (!list.isEmpty()) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
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

}