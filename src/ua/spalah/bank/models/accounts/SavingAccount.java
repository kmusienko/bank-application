package ua.spalah.bank.models.accounts;

import ua.spalah.bank.models.type.AccountType;

/**
 * Created by Kostya on 23.12.2016.
 */
public class SavingAccount implements Account {

    private double balance;
    private final AccountType accountType;
    protected SavingAccount(double balance, AccountType accountType) {
        this.balance = balance;
        this.accountType = accountType;
    }

    public SavingAccount(double balance) {
        this(balance, AccountType.SAVING);
    }

    @Override
    public String toString() {
        return "SavingAccount <-> balance: " + getBalance();
    }

    @Override
    public AccountType getType() {
        return accountType;
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
