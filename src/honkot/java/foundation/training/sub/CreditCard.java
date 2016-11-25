package honkot.java.foundation.training.sub;

import java.util.Random;

/**
 * Created by hhonda on 2016-11-23.
 */
public class CreditCard {

    public static double baseRate;
    private static int lastAccountNumber = 1234 * 1000 + new Random().nextInt(999);

    private String accountHolder;
    private int accountNumber;
    private int creditScore;
    private Grade grade; // it has default rate and default creditLimit
    private double rate; // additional rate;
    private double limit; // additional limit;
    private double balance;

    private enum Grade {
        Silver(10.0d, 1000.00d),
        Gold(7.0d, 3000.00d),
        Platinum(4.0d, 7000.00d),
        Black(1.0d, 15000.00d);

        private final double defaultAdditionalRate;
        private final double defaultLimit;
        Grade (double defaultRate, double defaultLimit) {
            this.defaultAdditionalRate = defaultRate;
            this.defaultLimit = defaultLimit;
        }

        public boolean overLimit(double amount) {
            return amount > defaultLimit;
        }

        public static Grade getGrade(int creditScore) {
            if (creditScore < 300) {
                return Silver;
            } else if (creditScore < 500) {
                return Gold;
            } else if (creditScore < 700) {
                return Platinum;
            } else {
                return Black;
            }
        }

        @Override
        public String toString() {
            return "Grade{" +
                    "defaultAdditionalRate=" + String.format("%1$.1f", defaultAdditionalRate) +
                    ", defaultLimit=" + String.format("%1$.1f", defaultLimit) +
                    '}';
        }
    }

    public CreditCard(String accountHolder, int creditScore) {
        this.accountHolder = accountHolder;
        addCreditScore(creditScore);
        this.accountNumber = ++lastAccountNumber;
        rate = 0.0d;
        limit = 0.0d; // means using default limit
    }

    public String getAccountHolder() {
        return accountHolder;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    private void addCreditScore(int amount) {
        this.creditScore += amount;
        this.grade = Grade.getGrade(this.creditScore);
    }

    /**
     * Take a purchase amount as a parameter and updates the current balance.
     * If the amount + balance > creditLimit, deny the transaction.
     * @param amount
     * @return false: could not purchase as over the limit
     */
    public boolean makePurchase(double amount) {
        double tempBalance = balance + amount;

        if (!grade.overLimit(tempBalance)) {
            addBalance(amount);
            return true;
        }

        return false;
    }

    /**
     * Takes a payment amount as a parameter and updates the current balance.
     * If the payment is grater than the balance, set the balance to zero and print an appropriate message.
     * If the payment is less than 10% of the balance, apply the payment and raise the account holder's rate by 1%.
     * If the balance is paid off entirely, raise the creditScore by 10.
     * If the increased creditScore changes where the account holder falls in the above table, change rate and limit as appropriate.
     * @param amount
     * @return false: error amount
     */
    public boolean makePayment(double amount) {
        amount = round(amount, 2);
        if (amount < 0.00d) {
            return false;
        }

        double lastBalance = this.balance;
        boolean overPayment = amount > this.balance;
        this.balance = overPayment ? 0.00d : this.balance - amount;
        this.balance = round(this.balance, 2);

        if (overPayment) {
            System.out.println("I appropriate you for your over payment.");
        } else if (amount > lastBalance * 0.1) {
            raiseRate(1.0d);
        }

        if (this.balance == 0.00d) {
            addCreditScore(10);
        }

        return true;
    }

    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    /**
     * Raises the account holder's rate by a given percentage.
     * @param amount
     * @return
     */
    public boolean raiseRate(double amount) {
        if (amount < 0.0d) {
            return false;
        }

        this.rate += amount;
        return true;
    }

    /**
     * Raises the account holder's limit by a given dollar amount.
     * @param amount
     * @return
     */
    public boolean raiseLimit(double amount) {
        if (amount < 0.00d) {
            return false;
        }

        this.limit += amount;
        return true;
    }

    /**
     * For debug
     */
    public void changeScore(int creditScore) {
        this.creditScore = 0;
        addCreditScore(creditScore);
    }

    private void addBalance(double balance) {
        this.balance += balance;
    }

    /**
     * Calculates the balance on a monthly basis.
     * Remember that the rate is yearly, so the formula for calculating the balance monthly is balance + (balanece * (rate / 12))
     * @return
     */
    public void calculateBalance() {
        double multipleRate = ((baseRate + this.rate + grade.defaultAdditionalRate) / 100) + 1;
        System.out.println("### multipleRate - " + multipleRate);
        balance = round(balance * multipleRate, 2);
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "accountHolder='" + accountHolder + '\'' +
                ", accountNumber=" + "****" + String.format("%03d", accountNumber % 1000) +
                ", creditScore=" + creditScore +
                ", grade=" + grade.name() +
                ", rate=" + String.format("%1$.1f", rate) +
                ", limit=" + limit +
                ", balance=" + String.format("%1$.2f", balance) +
                '}';
    }
}
