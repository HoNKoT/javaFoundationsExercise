package honkot.java.foundation.training;

import honkot.java.foundation.training.sub.CreditCard;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by hhonda on 2016-11-23.
 */
public class Chapter5_3 implements ChapterBase {

    ArrayList<CreditCard> mHolders = new ArrayList<CreditCard>();

    @Override
    public void main() {
        // initialize base rate
        System.out.print("INITIALIZE BASE RATE FIRST > ");
        do {
            Scanner scan = new Scanner(System.in);
            try {
                CreditCard.baseRate = scan.nextDouble();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Please type A NUMBER (XX.XX)");
            }
        } while (true);

        // Command mode
        do {
            Command.showMenu();
            System.out.print("SELECT A COMMAND > ");

            int command;
            Scanner scan = new Scanner(System.in);
            try {
                command = scan.nextInt();
                Command cmd = Command.getCommand(command);
                if (cmd != null) {
                    cmd.execute();
                    System.out.println("COMPLETE!");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please type A NUMBER");
            }

            System.out.println("");
        } while(true);
    }

    private enum Command {
        Create(1, "Create your credit account"),
        ShowBalance(2, "Show balance"),
        Purchase(3, "Purchase"),
        Payment(4, "Payment"),
        RaiseLimit(5, "Raise Limit");

        private int command;
        private String message;

        Command(int command, String message) {
            this.command = command;
            this.message = message;
        }

        public static void showMenu() {
            for (Command command: Command.values()) {
                System.out.print(command);
            }
        }

        public static Command getCommand(int requestCommand) {
            for (Command command: Command.values()) {
                if (command.command == requestCommand) {
                    return command;
                }
            }

            return null;
        }

        @Override
        public String toString() {
            return " - " + command +
                    ". " + message;
        }

        public void execute() {
            switch(this) {
                case Create: createAccount(); break;
                case ShowBalance: showBalance(); break;
                case Purchase: purchase(); break;
                case Payment: payment(); break;
                case RaiseLimit: raiseLimit(); break;
            }
        }

        private void createAccount() {
        }

        private void showBalance() {

        }

        private void purchase() {

        }

        private void payment() {

        }

        private void raiseLimit() {

        }
    }

}
