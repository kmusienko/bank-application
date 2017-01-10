package ua.spalah.bank.models.accounts;

/**
 * Created by Kostya on 23.12.2016.
 */
public class SavingAccount implements Account {

    private double balance;
    AccountType type;

    public SavingAccount(double balance, AccountType type) {
        this.balance = balance;
        this.type = type;
    }

    @Override
    public String toString() {
        return "SavingAccount <-> balance: " + getBalance();
    }

    @Override
    public AccountType getType() {
        return type;
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public void setBalance(double balance) {
        this.balance = balance;

    }
}
