package ua.spalah.bank.models.accounts;

import ua.spalah.bank.models.type.AccountType;

/**
 * Created by Kostya on 10.01.2017.
 */
public interface Account {

    AccountType getType();

    double getBalance();
    void setBalance(double balance);
    long getId();
    void setId(long id);
}
