package ua.spalah.bank.models.accounts;

import ua.spalah.bank.models.type.AccountType;

/**
 * Created by Kostya on 23.12.2016.
 */
public class SavingAccount implements Account {

    private long id;
    private double balance;
    private final AccountType accountType;
    protected SavingAccount(long id, double balance, AccountType accountType) {
        this.id = id;
        this.balance = balance;
        this.accountType = accountType;
    }
    protected SavingAccount(double balance, AccountType accountType) {
        this.balance = balance;
        this.accountType = accountType;
    }

    public SavingAccount(long id, double balance) {
        this(id, balance, AccountType.SAVING);
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

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
