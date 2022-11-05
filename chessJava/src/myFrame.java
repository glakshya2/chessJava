import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;


public class myFrame extends JFrame{

    JLabel textField = new JLabel();
    JPanel titlePanel = new JPanel();

    myFrame(){
        textField.setBackground(new Color(25,25,25));
        textField.setForeground(new Color(25,255, 0));
        textField.setFont(new Font("Ariel", Font.BOLD, 75));
        textField.setHorizontalAlignment(JLabel.CENTER);
        textField.setText("Chess");
        textField.setOpaque(true);

        titlePanel.setLayout(new BorderLayout());    

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(720, 640);
        this.setVisible(true);

        titlePanel.add(textField);
		this.add(titlePanel,BorderLayout.NORTH);
    }
    
}
