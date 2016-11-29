package honkot.java.foundation.training;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Dictionary;

/**
 * Created by hhonda on 2016-11-18.
 */
public class Chapter6_1 implements ChapterBase, ActionListener {

    JFrame mFrame = new JFrame("title");
    JPanel mPanel = new JPanel();

    JButton btn1 = new JButton("<");
    JButton btn2 = new JButton(">");
    JButton btn3 = new JButton("<");
    JButton btn4 = new JButton(">");
    JTextField textField1 = new JTextField("0");
    JTextField textField2 = new JTextField("0");

    @Override
    public void main() {
        btn1.addActionListener(this);
        btn2.addActionListener(this);
        btn3.addActionListener(this);
        btn4.addActionListener(this);

        mPanel.add(btn1);
        mPanel.add(textField1);
        mPanel.add(btn2);
        mPanel.add(btn3);
        mPanel.add(textField2);
        mPanel.add(btn4);

        mPanel.setPreferredSize(new Dimension(400, 100));
        mPanel.setBackground(Color.CYAN);

        mFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mFrame.getContentPane().add(mPanel);
        mFrame.pack();
        mFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btn1)) {
            int count = Integer.parseInt(textField1.getText());
            textField1.setText(Integer.toString(--count));
        } else if (e.getSource().equals(btn2)) {
            int count = Integer.parseInt(textField1.getText());
            textField1.setText(Integer.toString(++count));
        } else if (e.getSource().equals(btn3)) {
            int count = Integer.parseInt(textField2.getText());
            textField2.setText(Integer.toString(--count));
        } else if (e.getSource().equals(btn4)) {
            int count = Integer.parseInt(textField2.getText());
            textField2.setText(Integer.toString(++count));
        }
    }
}
