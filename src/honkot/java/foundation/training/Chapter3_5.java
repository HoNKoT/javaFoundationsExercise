package honkot.java.foundation.training;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by hhonda on 2016-11-18.
 */
public class Chapter3_5 extends ChapterBase {

    private final int MAX = 3;
    private final int MAX_STAR = 10;
    private int mCounterForTask4 = 0;
    private int[] mInputNumber = new int[MAX];

    private final int CMD_AVERAGE = 1;
    private final int CMD_PSTARS_WHILE = 2;
    private final int CMD_PSTARS_HtL = 3;
    private final int CMD_COUNTER = 4;
    private final int CMD_CHANGE_NUMBERS = 5;
    private final int CMD_EXIT = 6;

    @Override
    public void main() {
        // initialize input numbers
        getNumbers();

        boolean loop = true;

        do {
            // print information
            printNumbers();
            printCommand();

            // get command
            int command = getCommand();

            // execute each command
            switch (command) {
                case CMD_AVERAGE: task1(); break;
                case CMD_PSTARS_WHILE: task2(); break;
                case CMD_PSTARS_HtL: task3(); break;
                case CMD_COUNTER: task4(); break;
                case CMD_CHANGE_NUMBERS: task5(); break;
                case CMD_EXIT: loop = false; break;
                default:
                    System.out.println("Invalid command. Try Again!");
            }
        } while (loop);

        System.out.println("Thanks for playing! :)");
    }

    private void getNumbers() {
        System.out.println("Enter " + MAX +  " numbers, one by one");
        int getCount = 0;
        do {
            Scanner scan = new Scanner(System.in);

            try {
                mInputNumber[getCount] = scan.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("This is not number. Try Again!");
                continue;
            }

            getCount++;
        } while (getCount < MAX);
    }

    private int getCommand() {
        int command = -1;
        do {
            Scanner scan = new Scanner(System.in);

            try {
                command = scan.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("This is not number. Try Again!");
                continue;
            }
        } while (command == -1);

        return command;
    }

    private void printCommand() {
        System.out.println("Welcome, please choose from the following:");
        System.out.println("      1. Print the average of the " + MAX + " numbers");
        System.out.println("      2. Print out stars using while loop");
        System.out.println("      3. Print out stars from high to low using for");
        System.out.println("      4. Print out a counter of many times user used the program");
        System.out.println("      5. Change numbers");
        System.out.println("      6. Exit");
    }

    private void printNumbers() {
        StringBuffer buf = new StringBuffer();
        buf.append("Your Numbers are");
        for (int n: mInputNumber) {
            buf.append(" ");
            buf.append(Integer.toString(n));
        }
        System.out.println(buf.toString());
    }

    /**
     * 1. Print the average of the " + MAX + " numbers
     */
    private void task1() {
        int total = 0;
        for (int n: mInputNumber) {
            total += n;
        }

        System.out.println("Average is " + (total / MAX));
    }

    /**
     * 2. Print out stars using while loop
     * (I use while in generateStars() which is called in task2.
     */
    private void task2() {
        int stars = getMaximumStars();
        printStars(stars);
    }

    private int getMaximumStars() {
        int total = 0;
        for (int n: mInputNumber) {
            total += n;
        }
        return Math.min(total, MAX_STAR);
    }

    private void printStars(int starCount) {
        StringBuffer buf = new StringBuffer();

        while (starCount > 0) {
            buf.append("*");
            starCount--;
        }

        System.out.println(buf.toString());
    }

    /**
     * 3. Print out stars from high to low using for
     */
    private void task3() {
        int stars = getMaximumStars();

        for (int i = stars; i >= 0; i--) {
            printStars(i);
        }
    }

    /**
     * 4. Print out a counter of many times user used the program
     */
    private void task4() {
        System.out.println(++mCounterForTask4);
    }

    /**
     * 5. Change numbers
     */
    private void task5() {
        getNumbers();
    }
}
