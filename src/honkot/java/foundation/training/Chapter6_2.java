package honkot.java.foundation.training;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by hhonda on 2016-11-18.
 */
public class Chapter6_2 implements ChapterBase {

    @Override
    public void main() {
        String numStr, result;
        int num, again;

        do {
            numStr = JOptionPane.showInputDialog("Enter an integer: ");
            num = Integer.parseInt(numStr);
            result = "That number is " + ((num % 2 == 0) ? "even" : "odd");
            result += System.getProperty("line.separator") + "And also SquareRoots is " + Math.sqrt(num);
            JOptionPane.showMessageDialog(null, result);
            again = JOptionPane.showConfirmDialog(null, "Do Another?");

        } while (again == JOptionPane.YES_OPTION);
    }
}
