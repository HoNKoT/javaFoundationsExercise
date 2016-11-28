package honkot.java.foundation.training;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by hhonda on 2016-11-18.
 */
public class Chapter6_1 implements ChapterBase {

    @Override
    public void main() {
        JFrame frame = new JFrame("title");
        JPanel panel = new JPanel();
        JButton button = new JButton("aa");
        JLabel label = new JLabel("bb");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button.setText("aaa");
            }
        });

        panel.add(button);
        panel.add(label);
        panel.setPreferredSize(new Dimension(400, 100));
        panel.setBackground(Color.CYAN);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}
