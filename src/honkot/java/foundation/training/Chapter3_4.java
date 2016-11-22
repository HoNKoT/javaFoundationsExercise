package honkot.java.foundation.training;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by hhonda on 2016-11-17.
 */
public class Chapter3_4 implements ChapterBase {
    private final int MAXIMUM = 0xffff;

    @Override
    public void main() {

        boolean tryAgain = true;
        int userInput = 0;
        do {
            System.out.println("Enter a 4 digit pin number to encrypt: ");

            try {
                userInput = new Scanner(System.in).nextInt();
                if (userInput >= 1000 && userInput < 10000) {
                    break;
                }
            } catch (InputMismatchException e) {
                // nothing to do. just continue.
            }

            // Error case
            System.out.println("Error! Try again? (y) -> ");
            String userInput2 = new Scanner(System.in).next().toLowerCase();
            tryAgain = (userInput2.equalsIgnoreCase("y")
                    || userInput2.equalsIgnoreCase("yes")
                    || userInput2.isEmpty());

        } while (tryAgain);

        if (!tryAgain) {
            System.out.println("Bye bye! :D");
            return;
        }

        Random generator = new Random(userInput);

        // calculate random value
        int left = generator.nextInt(MAXIMUM - 0x1000) + 0x1000;
        int right = generator.nextInt(MAXIMUM - 0x1000) + 0x1000;

        // make encrypted pin number
        String encPin = Integer.toHexString(left) + userInput + Integer.toHexString(right);

        System.out.println("Your encrypted pin number is " + encPin);
    }
}
