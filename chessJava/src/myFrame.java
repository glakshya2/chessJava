import javax.swing.*;
//import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Vector;
//TODO: 1. Fix pawn possibleMoves()
//TODO: 2. pawn can not kill king but can check and checkmate king
//TODO: 3. king elephant swap 
//TODO: 4. pawn reach other end upgrade
//TODO: 5. Stalemate if 3 moves repeated

public class MyFrame extends JFrame implements MouseListener {

    Board gameBoard = new Board();
    JPanel mainPanel;
    JPanel panel;
    JFrame frame;
    JPanel boxPanel;
    boolean Turn = true; // True for white, false for black
    Vector<Integer> highlighted = new Vector<Integer>();
    boolean isSelected = false;
    int selectedX;
    int selectedY;

    public ImageIcon returnImg(String s, boolean color) {
        if (s == "Bishop" && color == true) {
            return new ImageIcon("BishopW.png");
        } else if (s == "Bishop" && color == false) {
            return new ImageIcon("BishopB.png");
        } else if (s == "King" && color == true) {
            return new ImageIcon("KingW.png");
        } else if (s == "King" && color == false) {
            return new ImageIcon("KingB.png");
        } else if (s == "Knight" && color == true) {
            return new ImageIcon("HorseW.png");
        } else if (s == "Knight" && color == false) {
            return new ImageIcon("HorseB.png");
        } else if (s == "Pawn" && color == true) {
            return new ImageIcon("PawnW.png");
        } else if (s == "Pawn" && color == false) {
            return new ImageIcon("PawnB.png");
        } else if (s == "Rook" && color == true) {
            return new ImageIcon("RookW.png");
        } else if (s == "Rook" && color == false) {
            return new ImageIcon("RookB.png");
        } else if (s == "Queen" && color == true) {
            return new ImageIcon("QueenW.png");
        } else if (s == "Queen" && color == false) {
            return new ImageIcon("QueenB.png");
        } else {
            return null;
        }
    }

    public int getCompNo(int x, int y) {
        return 63 - (8 * (y + 1)) + (x + 1);
    }

    public int getX(int compNo) {
        return compNo % 8;
    }

    public int getY(int compNo) {
        return 7 - (compNo / 8);
    }

    /*
     * public void updateGUI(int startX, int startY, int endX, int endY) {
     * highlighted.clear();
     * mainPanel.removeAll();
     * JPanel initial = (JPanel) panel.getComponent(getCompNo(startX, startY));
     * JLabel in = (JLabel) initial.getComponent(0);
     * JPanel fin = (JPanel) panel.getComponent((endX + (8 - endY) * 8) - 1);
     * JLabel finL = (JLabel) fin.getComponent(0);
     * finL.setIcon(in.getIcon());
     * in.setIcon(null);
     * int c = endX + (8 - endY) * 8 - 1;
     * JPanel a = ((JPanel) panel.getComponent(c));
     * JLabel d = ((JLabel) a.getComponent(0));
     * String s = gameBoard.board[endX][endY].getClass().getSimpleName();
     * boolean color = gameBoard.board[endX][endY].color;
     * ImageIcon img = returnImg(s, color);
     * d.setIcon(img);
     * mainPanel.validate();
     * mainPanel.repaint();
     * }
     * 
     * 
     */

    public void clearHighlight() {
        for (int i = 0; i < highlighted.size(); i++) {
            if (gameBoard.board[getX(highlighted.elementAt(i))][getY(highlighted.elementAt(i))] != null) {
                String s = gameBoard.board[getX(highlighted.elementAt(i))][getY(highlighted.elementAt(i))].getClass()
                        .getSimpleName();
                boolean color = gameBoard.board[getX(highlighted.elementAt(i))][getY(highlighted.elementAt(i))].color;
                ImageIcon img = returnImg(s, color);
                ((JPanel) mainPanel.getComponent(highlighted.elementAt(i))).removeAll();
                // ((JPanel) mainPanel.getComponent(highlighted.elementAt(i))).remove(1);
                ((JPanel) mainPanel.getComponent(highlighted.elementAt(i))).add(new JLabel(img));
            } else {
                ((JPanel) mainPanel.getComponent(highlighted.elementAt(i))).removeAll();
                mainPanel.validate();
                mainPanel.repaint();
                ((JPanel) mainPanel.getComponent(highlighted.elementAt(i))).add(new JLabel(new ImageIcon()));
            }
        }
        mainPanel.validate();
        mainPanel.repaint();
        highlighted.clear();
    }

    public void highlightPossible(int x, int y) {
        clearHighlight();
        Vector<Integer> list = new Vector<Integer>();
        if (Turn) {
            list = gameBoard.board[x][y].possibleMoves(gameBoard.board, x, y);
        } else {
            list = gameBoard.board[x][y].possibleMoves(gameBoard.flipBoard(), 7 - x, 7 - y);
        }
        for (int i = 0; i < list.size(); i++) {
            int listX = list.elementAt(i) / 10, listY = list.elementAt(i) % 10;
            if (!Turn) {
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

    public void selectPiece(int x, int y) {
        selectedX = x;
        selectedY = y;
        highlightPossible(x, y);
        if (highlighted.size() != 0) {
            isSelected = true;
        }
    }

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
        if (gameBoard.board[x][y].getClass().getSimpleName() == "Pawn") {
            gameBoard.board[x][y].updateFirstMove();
        }
        mainPanel.validate();
        // gameBoard.updateBoard(selectedX, selectedY, x, y);
        isSelected = false;
        Turn = !Turn;
    }

    public void movePiece(int x, int y) {
        for (int i = 0; i < highlighted.size(); i++) {
            if (getX(highlighted.elementAt(i)) == x && getY(highlighted.elementAt(i)) == y) {
                updateGame(x, y);
                break;
            }
        }
        isSelected = false;
    }

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
        frame.setSize(700, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.pack();
    }

    static boolean isCheck(Piece[][] board, int kingX, int kingY, boolean color) {
        // boolean color = board[kingX][kingY].color;
        int king = (kingX * 10) + kingY;
        for (int i = 0; i < 8; i++) {
            Vector<Integer> list = new Vector<Integer>();
            for (int j = 0; j < 8; j++) {
                if (board[i][j] != null) {
                    if (color != board[i][j].color) {
                        if (board[i][j].getClass().getSimpleName() == "King") {
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
            if (!isCheck(temp.board, list.get(i) / 10, list.get(i) % 10,
                    temp.board[(list.get(i) / 10)][list.get(0 % 10)].color)) {
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

    static boolean isStaleMate(Piece[][] board, boolean color, int kingX, int kingY) {
        if (!isCheck(board, kingX, kingY, board[kingX][kingY].color)) {
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

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getComponent().getX() / e.getComponent().getWidth();
        int y = 7 - e.getComponent().getY() / e.getComponent().getHeight();
        if (gameBoard.board[x][y] != null) {
            if (gameBoard.board[x][y].color == Turn) {
                if (x != selectedX && y != selectedY) {
                    selectPiece(x, y);
                }
            } else if (isSelected) {
                movePiece(x, y);
            }
        } else if (isSelected) {
            movePiece(x, y);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
