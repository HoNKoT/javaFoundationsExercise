package honkot.java.foundation.training.calculator;

import honkot.java.foundation.training.Main;
import javax.swing.*;

/**
 * Created by hiroki on 2016-11-30.
 */
public class MainService {

    ViewController mViews;
    private static MainService mService;

    private final static double DEFAULT = 0d;
    private double flashNumber = DEFAULT;
    private double inputNumber = DEFAULT;
    private UserInput mCurrentOrder = null;
    private final static int ZERO = 0;
    private int dotInputMode = ZERO;

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
        if (inputNumber == DEFAULT && mCurrentOrder == null) {
            // Case 1: ready.
            mViews.display(flashNumber);
        } else if (inputNumber != DEFAULT && mCurrentOrder == null) {
            // Case 2: error state
            mViews.display(inputNumber);
        } else if (inputNumber == DEFAULT && mCurrentOrder != null) {
            // Case 3: User input just how user gonna calculate
            mViews.display(flashNumber);
        } else if (inputNumber != DEFAULT && mCurrentOrder != null) {
            // Case 4: in calculate progress. (User input numbers after case 3)
            mViews.display(inputNumber);
        }
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
        switch (input) {
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
                if (afterEquals()) {
                    // Here is after Equals. so reset first!
                    funcClear();
                }
                funcNumber(input.number);
                break;
            case Equal:
                funcCalculate();
                mCurrentOrder = null;
                break;
            case Clear:
                funcClear();
                break;
            case Addition:
            case Subtraction:
            case Multiplication:
            case Division:
                funcCalculate();
                mCurrentOrder = input;
                break;
            case Dot:
                startDotInputMpde();
                break;
            case Switch:
                funcSwitch();
                break;
            case Percentage:
                funcPercentage();
                break;
        }
        display();
    }

    private boolean afterEquals() {
        return mCurrentOrder == null
                && inputNumber == DEFAULT
                && flashNumber != DEFAULT;
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
            int temp = 1;
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

    /**
     * calculate depending on the order user inputted.
     */
    private void funcCalculate() {
        if (mCurrentOrder != null) {

            switch (mCurrentOrder) {
                case Addition:
                    flashNumber += inputNumber;
                    break;
                case Subtraction:
                    flashNumber -= inputNumber;
                    break;
                case Multiplication:
                    if (inputNumber != DEFAULT) {
                        flashNumber *= inputNumber;
                    }
                    break;
                case Division:
                    flashNumber /= inputNumber;
                    break;
            }
        } else {
            if (afterEquals()) {
                // nothing to do here
            } else {
                // Here is after clear.
                flashNumber = inputNumber;
            }
        }

        // reset inputNumber
        inputNumber = DEFAULT;
        stopDotInputMpde();
    }

    private void funcClear() {
        flashNumber = DEFAULT;
        inputNumber = DEFAULT;
        mCurrentOrder = null;
        stopDotInputMpde();
    }

    private void funcSwitch() {
        inputNumber *= -1;
    }

    private void funcPercentage() {
        if (inputNumber != DEFAULT) {
            flashNumber = inputNumber;
        }
        inputNumber = DEFAULT;
        mCurrentOrder = null;

        flashNumber /= 100;
    }

    // region execute end --------------------------------------


    @Override
    public String toString() {
        return "MainService{" +
                ", flashNumber=" + flashNumber +
                ", inputNumber=" + inputNumber +
                ", mCurrentOrder=" + mCurrentOrder +
                ", dotInputMode=" + dotInputMode +
                ", mListener=" + mListener +
                '}';
    }
}
