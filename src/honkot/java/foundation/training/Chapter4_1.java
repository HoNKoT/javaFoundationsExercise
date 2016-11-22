package honkot.java.foundation.training;

import java.text.NumberFormat;
import java.util.Scanner;

/**
 * Created by hhonda on 2016-11-17.
 */
public class Chapter4_1 implements ChapterBase {

    private Evaluate[] EVALUATES = {
        new Evaluate("Excellent", 6.0),
        new Evaluate("Good", 4.0),
        new Evaluate("Poor", 1.5)
    };

    @Override
    public void main() {

        double currentSalary;   // employee's current salary
        double raise = 0;           // amount of the raise
        double newSalary;       // new salary for the employee
        String rating;          // performance rating

        Scanner scan = new Scanner(System.in);

        System.out.println("Enter the current salary: ");
        currentSalary = scan.nextDouble();
        System.out.println("Enter the performance rating (Excellent, Good, or Poor): ");
        rating = scan.next();

        // Compute the raise using if ...
        for (Evaluate evaluate: EVALUATES) {
            if (evaluate.rating.equalsIgnoreCase(rating)) {
                raise = currentSalary * (evaluate.rate / 100);
            }
        }
        newSalary = currentSalary + raise;

        // Print the results
        NumberFormat money = NumberFormat.getCurrencyInstance();
        // if you want to use specific country format, use this. (Just change Locale.xxxxx)
        // NumberFormat money = NumberFormat.getCurrencyInstance(Locale.JAPAN);
        System.out.println();
        System.out.println("Current Salary:       " + money.format(currentSalary));
        System.out.println("Amount of your raise: " + money.format(raise));
        System.out.println("Your new salary:      " + money.format(newSalary));
        System.out.println();
    }

    class Evaluate {
        String rating; double rate;
        Evaluate(String rating, double rate) { this.rating = rating; this.rate = rate;}
    }
}
