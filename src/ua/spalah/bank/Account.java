package ua.spalah.bank;

/**
 * Created by Kostya on 23.12.2016.
 */
public abstract class Account {
    double balance;

    public double getBalance() {
        return balance;
    }

    public void addMoney(double money) {
        if (money <= 0) {
            System.out.println("You want to add no money!");
        }
        balance += money;
    }

    public abstract void withdrawMoney(double money);

    public abstract String toString();


}
