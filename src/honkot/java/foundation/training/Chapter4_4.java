package honkot.java.foundation.training;

import java.util.Random;
import java.util.Scanner;

/**
 * Created by hhonda on 2016-11-18.
 */
public class Chapter4_4 extends ChapterBase {

    @Override
    public void main() {

        // scan the input from user
        System.out.println("Rock, Paper, Scissors Game Start!!");
        System.out.println("Which element will you use? (r, p, s) : ");
        Scanner scan = new Scanner(System.in);

        int result = Type.DRAW;
        Random random = new Random(System.currentTimeMillis());
        do {
            try {
                Type player = Type.getTypeByWord(scan.next());
                int enemyParam = random.nextInt(2);
                System.out.println("Your Enemy is " + Type.getTypeByParam(enemyParam).word);
                result = player.getResult(enemyParam);

                if (result == Type.DRAW) {
                    System.out.println("DRAW! What is your next element? (r, p, s) : ");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Unsupported value. Try type again!");
            }
        } while (result == Type.DRAW);

        if (result == Type.WIN) {
            System.out.println("YOU WIN! CONGRATULATIONS!");
        } else {
            System.out.println("You LOSE... Whats the hell...");
        }
    }

    private enum Type {
        Rock(2, "rock"), Paper(1, "paper"), Scissors(0, "scissors");

        static final int DRAW = -1;
        static final int WIN  = 0;
        static final int LOSE = 1;

        private final int parameter;
        private final String word;

        Type (int parameter, String word) {
            this.parameter = parameter;
            this.word = word;
        }

        static Type getTypeByParam(int param) throws IllegalArgumentException {
            Type[] allTypes = Type.values();
            for (Type type : allTypes) {
                if (param == type.parameter)
                    return type;
            }

            throw new IllegalArgumentException();
        }

        static Type getTypeByWord(String value) throws IllegalArgumentException {
            if (value != null) {
                Type[] allTypes = Type.values();
                for (Type type : allTypes) {
                    if (value.equalsIgnoreCase(type.word)
                            || value.charAt(0) == type.word.toLowerCase().charAt(0))
                        return type;
                }
            }

            throw new IllegalArgumentException();
        }

        int getResult(int enemyParam) {
            // get draw first
            if (this.parameter == enemyParam) {
                return DRAW;
            }

            // judge win or lose
            boolean win = this.parameter > enemyParam;
            if (this.parameter == Rock.parameter && enemyParam == Scissors.parameter) {
                win = false;
            }

            return win ? WIN : LOSE;
        }
    }
}
