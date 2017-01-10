package ua.spalah.bank.models.accounts;

/**
 * Created by Kostya on 10.01.2017.
 */
public interface Account {
    AccountType getType();

    double getBalance();
    void setBalance(double balance);
}
