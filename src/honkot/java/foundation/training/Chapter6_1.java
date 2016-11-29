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

    int mCounter1;
    int mCounter2;

    JButton btn1 = new JButton("<");
    JButton btn2 = new JButton(">");
    JButton btn3 = new JButton("<");
    JButton btn4 = new JButton(">");
    JLabel label1 = new JLabel();
    JLabel label2 = new JLabel();

    @Override
    public void main() {
        btn1.addActionListener(this);
        btn2.addActionListener(this);
        btn3.addActionListener(this);
        btn4.addActionListener(this);

        mPanel.add(btn1);
        mPanel.add(label1);
        mPanel.add(btn2);
        mPanel.add(btn3);
        mPanel.add(label2);
        mPanel.add(btn4);

        mPanel.setPreferredSize(new Dimension(200, 100));
        mPanel.setBackground(Color.CYAN);

        mFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mFrame.getContentPane().add(mPanel);
        mFrame.pack();
        mFrame.setVisible(true);

        updateLabels();
    }

    private void updateLabels() {
        label1.setText(Integer.toString(mCounter1));
        label2.setText(Integer.toString(mCounter2));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(btn1)) {
            mCounter1--;
        } else if (e.getSource().equals(btn2)) {
            mCounter1++;
        } else if (e.getSource().equals(btn3)) {
            mCounter2--;
        } else if (e.getSource().equals(btn4)) {
            mCounter2++;
        }

        updateLabels();
    }
}
