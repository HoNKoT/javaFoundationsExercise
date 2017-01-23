package honkot.java.foundation.training.calculator;

import honkot.java.foundation.training.Main;
import javax.swing.*;
import java.util.ArrayList;

import static honkot.java.foundation.training.calculator.MainService.UserInput.Equal;
import static honkot.java.foundation.training.calculator.MainService.UserInput.Multiplication;

/**
 * Created by hiroki on 2016-11-30.
 */
public class MainService {

    ViewController mViews;
    private static MainService mService;

    private final static double DEFAULT = 0d;
    private double inputNumber = DEFAULT;
    private final static int ZERO = 0;
    private int dotInputMode = ZERO;

    private UserInput mLastInput;
    private UserInput mCurrentInput;

    private static class HistoryController {
        private static ArrayList<History> histories = new ArrayList<>();

        public static boolean isEmpty() { return histories.size() == 0;}
        public static void add(double number) {
            int lastIndex = histories.size() - 1;
            if (!isEmpty() && isNumberLast()) {
                histories.remove(lastIndex);
            }
            histories.add(new History(number));
        }
        public static void add(UserInput command) {
            int lastIndex = histories.size() - 1;
            if (!isEmpty() && isCommandLast()) {
                histories.remove(lastIndex);
            }
            histories.add(new History(command));
        }
        public static boolean isCommandLast() {
            return !isEmpty() && !histories.get(histories.size() - 1).isNumber();
        }
        public static boolean isNumberLast() {
            return !isEmpty() && histories.get(histories.size() - 1).isNumber();
        }
        public static void clear() {
            histories = new ArrayList<>();
        }
        public static boolean isEqualLast() {
            return !isEmpty() && histories.get(histories.size() - 1).command.equals(Equal);
        }
        public static String getHistoryString() {
            StringBuffer buf = new StringBuffer();

            for (History history : histories) {
                if (history.isNumber()) {
                    buf.append(history.number);
                } else {
                    switch (history.command) {
                        case Addition: buf.append(" + "); break;
                        case Subtraction: buf.append(" - "); break;
                        case Multiplication: buf.append(" * "); break;
                        case Division: buf.append(" / "); break;
                        case Equal: buf.append(" = "); break;
                    }
                }
            }
            return buf.toString();
        }

        public static double calculate() {
            double ret = DEFAULT;

            ArrayList<History> tempHistries = copy();

            // At first, calculate * and /
            for (int i = 0; i < tempHistries.size(); i++) {
                History history = tempHistries.get(i);

                if (history.isNumber()) {
                    int thisIndex = tempHistries.indexOf(history);

                    if (tempHistries.size() >= thisIndex + 3) {
                        // has next (command) and next (number)
                        UserInput command = tempHistries.get(thisIndex + 1).command;
                        double number = tempHistries.get(thisIndex + 2).number;

                        // calculate and remove them
                        switch (command) {
                            case Multiplication:
                                history.number *= number;
                                break;
                            case Division:
                                if (history.number != DEFAULT) {
                                    ret /= history.number;
                                }
                                break;
                            default:
                                continue;
                        }
                        tempHistries.remove(thisIndex + 2);
                        tempHistries.remove(thisIndex + 1);
                    }
                }
            }

            // finally, calculate + and -
            for (History history : tempHistries) {
                if (history.isNumber()) {
                    // number input
                    if (tempHistries.indexOf(history) == 0) {
                        // initialize as first number
                        ret = history.number;

                    } else {
                        // get command for calculation and boss.
                        UserInput command =
                                tempHistries.get(tempHistries.indexOf(history) - 1).command;

                        switch (command) {
                            case Addition:
                                ret += history.number;
                                break;
                            case Subtraction:
                                ret -= history.number;
                                break;
                        }
                    }

                }
            }

            return ret;
        }

        private static ArrayList<History> copy() {
            ArrayList<History> ret = new ArrayList<>();
            for (History history : histories) {
                ret.add(history.copy());
            }
            return ret;
        }

        private static class History {
            double number = DEFAULT;
            UserInput command;

            History(double number) { this.number = number;}
            History(UserInput command) { this.command = command;}
            public boolean isNumber() { return number != DEFAULT;}

            public History copy() {
                if (isNumber()) {
                    return new History(number);
                } else {
                    return new History(command);
                }
            }
        }
    }

    /**
     * This is for starting from GUI directly.
     * @param args
     */
    public static void main(String[] args) {
        start();
    }

    private MainService() {
        // initialize views
        mViews = new ViewController(mListener);

        JFrame frame = new JFrame("simple calculator");

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().add(mViews);
        frame.pack();
        frame.setVisible(true);
    }

    public static void start() {
        mService = new MainService();
    }

    private void display() {
        if (Main.DEBUG) System.out.println(this);

        if (!HistoryController.isEqualLast()) {
            mViews.display(inputNumber);
        } else {
            if (HistoryController.isEqualLast()) {
                mViews.display(HistoryController.calculate());
            } else {
                mViews.display(inputNumber);
            }
        }
        mViews.putHistory(HistoryController.getHistoryString());
    }

    protected enum UserInput {
        Btn1(1),
        Btn2(2),
        Btn3(3),
        Btn4(4),
        Btn5(5),
        Btn6(6),
        Btn7(7),
        Btn8(8),
        Btn9(9),
        Btn0(0),
        Addition,
        Subtraction,
        Multiplication,
        Division,
        Dot,
        Clear,
        Equal,
        Switch,
        Percentage;

        private int number = -1;

        UserInput() {}

        UserInput(int number) {
            this.number = number;
        }

        public boolean isNumber() {
            return number != -1;
        }

        public static UserInput byNumber(int number) {
            for (UserInput ret : values()) {
                if (ret.number == number) {
                    return ret;
                }
            }
            return null;
        }
    }

    protected interface OnUserInputListener {
        void onUserInput(MainService.UserInput input);
    }

    private OnUserInputListener mListener = new OnUserInputListener() {
        @Override
        public void onUserInput(UserInput input) {
            execute(input);
        }
    };

    // region execute start --------------------------------------

    private void execute(UserInput input) {
        mLastInput = mCurrentInput;
        mCurrentInput = input;

        switch (input) {
            // numbers
            case Btn1:
            case Btn2:
            case Btn3:
            case Btn4:
            case Btn5:
            case Btn6:
            case Btn7:
            case Btn8:
            case Btn9:
            case Btn0:
                if (HistoryController.isEqualLast()) {
                    // after equal, clear first!
                    HistoryController.clear();
                }
                funcNumber(input.number);
                break;

            // commands for calculation
            case Equal:
                if (!HistoryController.isEmpty()) {
                    if (HistoryController.isNumberLast()) {
                        HistoryController.add(inputNumber);
                    }
                    HistoryController.add(input);
                    inputNumber = DEFAULT;
                    stopDotInputMpde();
                }
                break;
            case Division:
                if (inputNumber == DEFAULT) {
                    // ignore error case
                    if (HistoryController.isCommandLast()) {
                        // just change command to last one
                        HistoryController.add(input);
                    }
                } else {
                    HistoryController.add(inputNumber);
                    HistoryController.add(input);
                    inputNumber = DEFAULT;
                }
                stopDotInputMpde();
                break;
            case Addition:
            case Subtraction:
            case Multiplication:
                if (!HistoryController.isEqualLast()) {
                    if (HistoryController.isNumberLast()) {
                        HistoryController.add(inputNumber);
                    }
                    inputNumber = DEFAULT;
                }
                HistoryController.add(input);
                stopDotInputMpde();
                break;

            // action immediately
            case Clear:
                funcClear();
                break;
            case Dot:
                if (!HistoryController.isEqualLast()) {
                    startDotInputMpde();
                }
                break;
            case Switch:
                if (!HistoryController.isEqualLast()) {
                    funcSwitch();
                }
                break;
            case Percentage:
                if (!HistoryController.isEqualLast()) {
                    funcPercentage();
                }
                break;
        }
        display();
    }

    private boolean isDotInputMode() {
        return dotInputMode > ZERO;
    }

    private void startDotInputMpde() {
        if (!isDotInputMode()) {
            dotInputMode = 1;
        }
    }

    private void stopDotInputMpde() {
        dotInputMode = ZERO;
    }

    private void funcNumber(int number) {
        if (isDotInputMode()) {
            /*
             * You know calculate between doubles is not exact sometimes,
             * although it is just division 10 again and again.
             * TODO: So I try to parse String first, and just append the Number as String.
             */
            double temp = 1;
            for (int i = 0; i < dotInputMode; i++) {
                temp *= 10;
            }
            inputNumber += (double)number / temp;
            dotInputMode++;
        } else {
            if (inputNumber >= 0) {
                inputNumber = inputNumber * 10 + number;
            } else {
                inputNumber = inputNumber * 10 - number;
            }
        }
    }

    private void funcClear() {
        inputNumber = DEFAULT;
        stopDotInputMpde();
        HistoryController.clear();
    }

    private void funcSwitch() {
        inputNumber *= -1;
    }

    private void funcPercentage() {
        HistoryController.clear();
        inputNumber /= 100.0d;
    }

    // region execute end --------------------------------------


    @Override
    public String toString() {
        return "MainService{" +
                " inputNumber=" + inputNumber +
                ", dotInputMode=" + dotInputMode +
                ", mLastInput=" + mLastInput +
                ", mCurrentInput=" + mCurrentInput +
                ", mListener=" + mListener +
                '}';
    }
}
