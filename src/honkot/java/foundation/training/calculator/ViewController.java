package honkot.java.foundation.training.calculator;

import honkot.java.foundation.training.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by hiroki on 2016-11-30.
 */
public class ViewController extends Panel implements ActionListener {
    JPanel numberLayout = new JPanel();
    JPanel controlLayout = new JPanel();
    JLabel mResult = new JLabel();

    MainService.OnUserInputListener mListener;

    private static final String CLEAR = "C";
    private static final String EQUAL = "=";

    private static final String PLUS = "+";
    private static final String MINUS = "-";
    private static final String MULTIPLY = "*";
    private static final String DIVIDE = "/";

    private static final String[] CONTROL_ARRAY = {
            PLUS, MINUS, MULTIPLY, DIVIDE
    };


    public ViewController(MainService.OnUserInputListener listener) {
        mListener = listener;
        initLayout();
    }

    private void initLayout() {
        // Step1. set display view
        setLayout(new BorderLayout());
        add(mResult, BorderLayout.NORTH);
        display(0);

        // Step2. set numbers view
        numberLayout.setLayout(new GridLayout(4, 3));
        for (int i = 1; i <= 9; i++) {
            // add number 1 - 9
            JButton buttonX = new JButton(Integer.toString(i));
            buttonX.addActionListener(this);
            numberLayout.add(buttonX);
        }
        // add number Clear, 0, .
        JButton buttonC = new JButton(CLEAR);
        buttonC.addActionListener(this);
        numberLayout.add(buttonC);
        JButton button0 = new JButton(Integer.toString(0));
        button0.addActionListener(this);
        numberLayout.add(button0);
        JButton buttonD = new JButton(EQUAL);
        buttonD.addActionListener(this);
        numberLayout.add(buttonD);
        add(numberLayout, BorderLayout.WEST);


        // Step3. set controller
        controlLayout.setLayout(new GridLayout(4, 1));
        for (String print : CONTROL_ARRAY) {
            JButton control = new JButton(print);
            control.addActionListener(this);
            controlLayout.add(control);
        }
        add(controlLayout, BorderLayout.EAST);

    }

    public void display(double number) {
        mResult.setText(Double.toString(number));
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
