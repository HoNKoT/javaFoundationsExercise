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
    private static CreditCard mLoginHolder = null;

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
            System.out.println(System.getProperty("line.separator"));
            if (isLogin()) {
                System.out.println("Hello " + mLoginHolder.getAccountHolder() + "! How can I help you?");
            }
            Command.showMenu();
            System.out.print("SELECT A COMMAND > ");

            String command;
            Scanner scan = new Scanner(System.in);
            try {
                command = scan.next();
                Command cmd;
                try {
                    int numCommand = Integer.parseInt(command);
                    cmd = Command.getCommand(numCommand);
                } catch (NumberFormatException e) {
                    cmd = Command.getCommand(command);
                }
                if (cmd != null) {
                    System.out.println(System.getProperty("line.separator"));
                    cmd.execute();
                    continue;
                }
                System.out.println("");
            } catch (InputMismatchException e) {
                // nothing to do
            }

            System.out.println("FAILED...");
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

    private static boolean isLogin() {
        return mLoginHolder != null;
    }

    private static boolean login(int accountNumber) {
        for (CreditCard holder: mHolders) {
            if (accountNumber == holder.getAccountNumber()) {
                // logined successful
                mLoginHolder = holder;
                return true;
            }
        }
        mLoginHolder = null;
        return false;
    }

    private static void logout() {
        if (isLogin()) {
            mLoginHolder = null;
        }
    }

    private enum Command {

        Create(0, "signup", "Sign up your credit account", Type.BeforeLogin),
        Login(1, "login", "Log-in", Type.BeforeLogin),

        Logout(0, "logout", "Log-out", Type.AfterLogin),
        ShowBalance(1, "show", "Show balance", Type.AfterLogin),
        Purchase(2, "purchase", "Record purchase", Type.AfterLogin),
        RaiseLimit(3, "limit", "Raise purchase Limit", Type.AfterLogin),
        ChangeScore(7, "score", "[for debug] change credit score", Type.AfterLogin),
        ThrouMonth(8, "next", "[for debug] Move to next month", Type.Debug),
        Dump(9, "dump", "[for debug] dump", Type.Debug);

        private int command;
        private String stringCommand;
        private String message;
        private Type type;
        private enum Type {
            BeforeLogin(0), AfterLogin(1), Debug(2);
            private int i = 0;
            Type(int i) {this.i = i;}

            public boolean shouldShow() {
                switch (this) {
                    case AfterLogin: return isLogin();
                    case BeforeLogin: return !isLogin();
                    case Debug: return true; // put false if you want.
                }
                return false;
            }
        }

        Command(int command, String stringCommand, String message, Type type) {
            this.command = command;
            this.stringCommand = stringCommand;
            this.message = message;
            this.type = type;
        }

        public static void showMenu() {
            for (Command command: Command.values()) {
                if (command.type.shouldShow())
                    System.out.println(command);
            }
        }

        public static Command getCommand(Object requestCommand) {
            for (Command command: Command.values()) {
                boolean machCommand = requestCommand instanceof Integer
                        ? command.command == (Integer) requestCommand
                        : command.stringCommand.equalsIgnoreCase((String)requestCommand);
                if (machCommand && command.type.shouldShow()) {
                    return command;
                }
            }

            return null;
        }

        public void execute() {
            switch(this) {
                case Logout: logout(); break;
                case Login: login(); break;
                case Create: createAccount(); break;
                case ShowBalance: showBalance(); break;
                case Purchase: purchase(); break;
                case RaiseLimit: raiseLimit(); break;
                case ChangeScore: changeScore(); break;
                case ThrouMonth: throughMonth(); break;
                case Dump: dump(); break;
            }
        }

        private void login() {
            System.out.println("YOUR ACCOUNT NUMBER > ");

            int accountNumber = 0;
            Scanner scan = new Scanner(System.in);
            try {
                accountNumber = scan.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("FAILED...");
                return;
            }

            // looks strange, but this method name is also 'login',
            // Then we have to put more specifically like this. [ClassName].[MethodName]
            if (!Chapter5_3.login(accountNumber)) {
                System.out.println("FAILED...");
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
                System.out.println("And now, I've login your account.");
                mLoginHolder = newAccount;
            } else {
                System.out.println("ANY PROBLEM HAS OCCURRED. Try again from input command, sorry.");
            }
        }

        private void showBalance() {
            CreditCard card = mLoginHolder;
            if (card == null) return;

            System.out.println("Your balance is -> " + String.format("%1$.2f", card.getBalance()));
        }

        private void purchase() {
            CreditCard card = mLoginHolder;
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
            CreditCard card = mLoginHolder;
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

        private void changeScore() {
            CreditCard card = mLoginHolder;
            if (card == null) return;

            System.out.print("Put limit price you want here -> ");
            try {
                Scanner scan = new Scanner(System.in);
                card.changeScore(scan.nextInt());
            } catch (InputMismatchException e) {
                System.out.println("FAILED.");
            }
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
                    if (holder.getBalance() > 0.00d) {
                        holder.raiseRate(2.0);
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
