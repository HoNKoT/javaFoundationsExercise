package honkot.java.foundation.training;

import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by hhonda on 2016-11-18.
 */
public class Chapter4_2 extends ChapterBase {

    @Override
    public void main() {
        String fileName = "res/input.txt";
        Scanner fileScan = null;
        try {
            fileScan = new Scanner(new File(fileName));
        } catch (IOException e) {

        }

        int sum = 0;
        int count = 0;

        while (fileScan.hasNext()) {
            try {
                sum += fileScan.nextInt();
            } catch (InputMismatchException e) {
                // nothing to do.
            }
            count++;
        }

        System.out.println(
                "There were " + count + " numbers in "
                        + fileName + " and their sum was " + sum + ".");
    }
}
