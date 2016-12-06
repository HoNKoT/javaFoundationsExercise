package honkot.java.foundation.training;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;

/**
 * Created by hhonda on 2016-11-18.
 */
public class Chapter8_1 implements ChapterBase {

    private static int checkingUserNumber = 0;
    private static int savingUserNumber = 0;
    private ArrayList<Account> mAccounts = new ArrayList<>();

    @Override
    public void main() {
        // initialize for test
        mAccounts.add(new CheckingAccount());
        mAccounts.get(0).deposit(10);
        mAccounts.add(new SavingAccount());
        mAccounts.get(1).deposit(10);

        System.out.println("Created a Saving and Checking account for testing.");
        /*
         * If you want make more bank account,
         * you should Create an instance which is CheckingAccount or SavingAccount and Add it to mAccounts.
         * Then, this program must work still fine.
         */

        do {
            // do withdraw each account.
            for (Account account : mAccounts) {
                System.out.println(account);
                if (account instanceof SavingAccount) {
                    System.out.println("withdraw for Saving (" + account.accountNumber + ") > ");
                } else {
                    System.out.println("withdraw for Checking (" + account.accountNumber + ") > ");
                }

                Scanner scan = new Scanner(System.in);
                try {
                    account.withdraw(scan.nextInt());
                } catch (InputMismatchException e) {
                    // nothing to do
                }
                System.out.println(" - result > " + account);
                System.out.println(System.getProperty("line.separator"));
            }

            // move to next month and print all.
            System.out.println(System.getProperty("line.separator"));
            System.out.println("Next month! ");
            for (Account account : mAccounts) {
                if (account instanceof SavingAccount) {
                    ((SavingAccount)account).recalculateBalance();
                }
            }
            printAll();
            System.out.println(System.getProperty("line.separator"));


        } while (true);
    }

    private void printAll() {
        for (Account account : mAccounts) {
            System.out.println(account);
        }
    }

    /* define classes ---------------------------------------------------------------- */

    private class CheckingAccount extends Account {
        private final int MINIMUM_BALANCE = -100;
        private final double OVERDRAFT_FEE = 10.50;

        public CheckingAccount() {
            super(1000 + ++checkingUserNumber);
        }

        @Override
        public void withdraw(int amount) {
            if ((balance - OVERDRAFT_FEE - amount) > MINIMUM_BALANCE) {
                super.withdraw(amount);
                if (isOverdraft()) {
                    if (Main.DEBUG) System.out.println(" - [DEBUG] the account holder takes penalty");
                    balance -= OVERDRAFT_FEE;
                }
            } else {
                System.out.println("You can not withdraw the amount cuz over using!");
            }
        }

        @Override
        public String toString() {
            return "CHQ{" + super.toString() + '}';
        }
    }

    private class SavingAccount extends Account {
        private double interestRate = 0.12d; // annual (year by year) rate

        public SavingAccount() {
            super(2000 + ++savingUserNumber);
        }

        @Override
        public void withdraw(int amount) {
            super.withdraw(amount);
        }

        public void recalculateBalance() {
            balance += (balance * (interestRate / 12));
        }

        @Override
        public String toString() {
            return "SAV{" + super.toString() + '}';
        }
    }

    private abstract class Account {
        int accountNumber;
        double balance;

        Account(int accountNumber) {
            this.accountNumber = accountNumber;
        }

        public void deposit(int amount) {
            balance += amount;
        }

        public void withdraw(int amount) {
            balance -= amount;
        }

        public boolean isOverdraft() {
            return balance < 0.0d;
        }

        @Override
        public String toString() {
            NumberFormat nfCur = NumberFormat.getCurrencyInstance(Locale.CANADA);
            return "accountNumber= " + accountNumber +
                    ", balance= " + nfCur.format(balance);
        }
    }

}
