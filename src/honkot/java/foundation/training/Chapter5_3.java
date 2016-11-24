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

    private static ArrayList<CreditCard> mHolders = new ArrayList<CreditCard>();

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
                    continue;
                }
                System.out.println("");
            } catch (InputMismatchException e) {
                // nothing to do
            }

            System.out.println("Please type A NUMBER");
            System.out.println("");
        } while(true);
    }

    private static CreditCard getHolder() {
        // take Holder Name
        System.out.println("YOUR ACCOUNT NUMBER > ");
        Scanner scan = new Scanner(System.in);

        try {
            int accountNumber = scan.nextInt();
            for (CreditCard holder: mHolders) {
                if (holder.getAccountNumber() == accountNumber) {
                    return holder;
                }
            }
        } catch (InputMismatchException e) {
            // nothing to do
        }

        System.out.println("FAILED.");
        return null;
    }

    private enum Command {
        Create(1, "Create your credit account"),
        ShowBalance(2, "Show balance"),
        Purchase(3, "Purchase"),
        RaiseLimit(4, "Raise Limit"),
        ThrouMonth(8, "Move to next month (for debug)"),
        Dump(9, "dump (for debug)");

        private int command;
        private String message;

        Command(int command, String message) {
            this.command = command;
            this.message = message;
        }

        public static void showMenu() {
            for (Command command: Command.values()) {
                if (command.command != 9)
                    System.out.println(command);
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

        public void execute() {
            switch(this) {
                case Create: createAccount(); break;
                case ShowBalance: showBalance(); break;
                case Purchase: purchase(); break;
                case RaiseLimit: raiseLimit(); break;
                case ThrouMonth: throughMonth(); break;
                case Dump: dump(); break;
            }
        }

        private void createAccount() {
            System.out.println("YOUR NAME > ");
            Scanner scan = new Scanner(System.in);
            String accountHolder = scan.next();

            if (accountHolder != null && !accountHolder.isEmpty()) {
                CreditCard newAccount = new CreditCard(
                        accountHolder, new Random().nextInt(700));
                mHolders.add(newAccount);

                System.out.println("Has generated your credit card!");
                System.out.println("Your bank account is here. Don't forget! -> " + newAccount.getAccountNumber());
            } else {
                System.out.println("ANY PROBLEM HAS OCCURRED. Try again from input command, sorry.");
            }
        }

        private void showBalance() {
            CreditCard card = getHolder();
            if (card == null) return;

            System.out.println("Your balance is -> " + String.format("%1$.2f", card.getBalance()));
        }

        private void purchase() {
            CreditCard card = getHolder();
            if (card == null) return;

            System.out.print("Put your purchase price here -> ");
            try {
                Scanner scan = new Scanner(System.in);
                if (card.makePurchase(scan.nextDouble()))
                    return;
            } catch (InputMismatchException e) {
                // nothing to do.
            }

            System.out.println("FAILED.");
        }

        private void raiseLimit() {
            CreditCard card = getHolder();
            if (card == null) return;

            System.out.print("Put limit price you want here -> ");
            try {
                Scanner scan = new Scanner(System.in);
                if (card.raiseLimit(scan.nextDouble()))
                    return;
            } catch (InputMismatchException e) {
                // nothing to do.
            }

            System.out.println("FAILED.");
        }

        private void throughMonth() {
            System.out.println("OKAY! MOVE TO NEXT MONTH.");

            for (CreditCard holder: mHolders) {
                if (holder.getBalance() != 0.00d) {
                    System.out.println("Hello, " + holder.getAccountHolder()
                            + "! You have a balance, " + holder.getBalance());
                    System.out.println("Then, you have to pay now. How much do you do? > ");
                    double payment = 0.00d;
                    do {
                        Scanner scan = new Scanner(System.in);
                        try {
                            payment = scan.nextDouble();
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Please type A NUMBER OF PURCHASE (XX.XX)");
                        }
                    } while (true);

                    double lastBalance = holder.getBalance();
                    holder.makePayment(payment);
                    if (holder.getBalance() != 0.00d) {
                        holder.raiseRate(2.0);
                    } else {
                        holder.calculateBalance();
                    }

                    System.out.println("DONE YOUR PAYMENT! TAKE A RECEIPT!");
                    System.out.println("");
                    System.out.println("   last your balance : " + String.format("%1$8.2f", lastBalance));
                    System.out.println("        your payment : " + String.format("%1$8.2f", payment));
                    System.out.println("");
                    System.out.println(" ----------------------------------");
                    System.out.println("     current balance : " + String.format("%1$8.2f", holder.getBalance()));
                    System.out.println("     (It might include some interest)");
                    System.out.println("");
                }
            }
        }

        private void dump() {
            for (CreditCard holder: mHolders) {
                System.out.println(holder);
            }
        }

        @Override
        public String toString() {
            return " - " + command + ". " + message;
        }
    }

}
