package honkot.java.foundation.training;

import java.util.Scanner;

/**
 * Created by hhonda on 2016-11-17.
 */
public class Chapter3_3 extends ChapterBase{
    private enum Season {
        SPRING, SUMMER, FALL, WINTER, AUTUMN
    }

    @Override
    public void main() {
        System.out.println("Type season you love : ");
        String userInput = new Scanner(System.in).next();

        try {
            Season.valueOf(userInput.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Are you sure? I Do Not know the season you type, sorry.");
            return;
        }

        System.out.println("Yeah, I love " + userInput + ", too! :)");
    }
}
