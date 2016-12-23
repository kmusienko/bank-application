package ua.spalah.bank;

/**
 * Created by Kostya on 23.12.2016.
 */
public class SavingAccount extends Account {

    public SavingAccount(double balance) {
        this.balance = balance;
    }

    @Override
    public void withdrawMoney(double money) {
        if (money > balance) {
            System.out.println("You want too much money.");
        } else {
            balance -= money;
        }
    }

    @Override
    public String toString() {
        return "SavingAccount <-> balance: " + balance;
    }
}
