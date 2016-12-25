package ua.spalah.bank;

/**
 * Created by Kostya on 23.12.2016.
 */
public class SavingAccount extends Account {

    public SavingAccount(double balance) {
        super(balance);
    }

    @Override
    public String toString() {
        return "SavingAccount <-> balance: " + getBalance();
    }
}
