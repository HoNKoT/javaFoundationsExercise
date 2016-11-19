package honkot.java.foundation.training;

import java.util.Scanner;

/**
 * Created by hhonda on 2016-11-18.
 */
public class Chapter4_3 extends ChapterBase {

    @Override
    public void main() {
        String sentence;
        String word;
        String tempWord = "";

        // scan the input from user
        System.out.println("Enter a sentence: ");
        Scanner scan = new Scanner(System.in);

        // loop and find words using while
        String inputString = scan.next();

        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < inputString.length(); i++) {
            buf.append(inputString.charAt(i));
        }
        System.out.println(buf.toString());
    }
}
