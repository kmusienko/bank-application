package ua.spalah.bank;

/**
 * Created by Kostya on 23.12.2016.
 */
public class CheckingAccount extends Account {
    double overdraft; //задолженность.

    public CheckingAccount(double balance, double overdraft) {
        super(balance);
        this.overdraft = overdraft;
    }

    @Override
    public void withdrawMoney(double money) {
        if (balance - money < 0 && Math.abs(balance - money) > overdraft) {
            System.out.println("Out of overdraft limit.");
        } else {
            balance -= money;
        }
    }

    @Override
    public String toString() {
        return "CheckingAccount <-> balance: " + balance + ", overdraft: " + overdraft;
    }
}