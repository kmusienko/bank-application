package ua.spalah.bank.accounts;

/**
 * Created by Kostya on 23.12.2016.
 */
public class CheckingAccount extends Account {
    private double overdraft; //задолженность.

    public CheckingAccount(double balance, double overdraft) {
        super(balance);
        if (overdraft < 0) {
            System.out.println("Overdraft < 0!");
        } else {
            this.overdraft = overdraft;
        }
    }

    @Override
    public void withdraw(double money) {
        double available = getBalance() + overdraft;
        if (available < money) {
            System.out.println("Out of overdraft limit.");
        } else {
            setBalance(getBalance() - money);
        }
    }

    @Override
    public String toString() {
        return "CheckingAccount <-> balance: " + getBalance()
                + ", overdraft: " + overdraft;
    }
}