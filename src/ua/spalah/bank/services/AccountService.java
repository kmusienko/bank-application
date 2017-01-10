package ua.spalah.bank.services;

import ua.spalah.bank.models.accounts.Account;

/**
 * Created by Kostya on 05.01.2017.
 */
public interface AccountService {
    void deposit(Account account, double amount);
    void withdraw(Account account, double amount);
    void transfer(Account fromAccount, Account toAccount, double amount);
}
