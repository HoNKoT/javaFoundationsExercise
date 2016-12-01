package honkot.java.foundation.training.calculator;

import honkot.java.foundation.training.Main;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by hiroki on 2016-11-30.
 */
public class ViewController extends JPanel implements ActionListener {
    JLabel mResult = new JLabel();

    MainService.OnUserInputListener mListener;

    private static final String CLEAR = "C";
    private static final String EQUAL = "=";

    private static final String PLUS = "+";
    private static final String MINUS = "-";
    private static final String MULTIPLY = "*";
    private static final String DIVIDE = "/";
    private static final String DOT = ".";
    private static final String SWITCH = "+/-";
    private static final String PERCENTAGE = "%";


    private static final String[] BUTTON_LAYOUT = {
            CLEAR,      SWITCH,     PERCENTAGE, DIVIDE,
            "7",        "8",        "9",        MULTIPLY,
            "4",        "5",        "6",        MINUS,
            "1",        "2",        "3",        PLUS,
            DOT,        "0",        DOT,        EQUAL,
    };

    public ViewController(MainService.OnUserInputListener listener) {
        mListener = listener;
        initLayout();
    }

    private void initLayout() {
        // Step1. set display view
        Border border = mResult.getBorder();
        Border margin = new EmptyBorder(20, 10, 10, 10);
        mResult.setBorder(new CompoundBorder(border, margin));
        setLayout(new BorderLayout());
        mResult.setHorizontalAlignment(JLabel.RIGHT);
        add(mResult, BorderLayout.NORTH);
        display(0);

        // Step2. control views
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(5, 4));
        for (String buttonString : BUTTON_LAYOUT) {
            JButton button = new JButton(buttonString);
            button.addActionListener(this);
            controlPanel.add(button);
        }
        add(controlPanel, BorderLayout.CENTER);
    }

    public void display(double number) {
        boolean numberOfDecimalPlaces = number % 1 < 0d;
        if (numberOfDecimalPlaces) {
            //TODO
        } else {
            //TODO
        }
        mResult.setText(Double.toString(number));
        mResult.setFont(new Font("Helvetica", Font.BOLD, 24));
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        JButton button = (JButton)event.getSource();
        String printed = button.getText();
        if (Main.DEBUG) System.out.println("### press the button " + printed);

        try {
            int number = Integer.parseInt(printed);
            mListener.onUserInput(MainService.UserInput.byNumber(number));
            return;
        } catch (NumberFormatException e) {
            // nothing to do
        }

        if (printed.equals(PLUS)) {
            mListener.onUserInput(MainService.UserInput.Addition);
        } else if (printed.equals(MINUS)) {
            mListener.onUserInput(MainService.UserInput.Subtraction);
        } else if (printed.equals(MULTIPLY)) {
            mListener.onUserInput(MainService.UserInput.Multiplication);
        } else if (printed.equals(DIVIDE)) {
            mListener.onUserInput(MainService.UserInput.Division);
        } else if (printed.equals(CLEAR)) {
            mListener.onUserInput(MainService.UserInput.Clear);
        } else if (printed.equals(EQUAL)) {
            mListener.onUserInput(MainService.UserInput.Equal);
        }
    }
}
