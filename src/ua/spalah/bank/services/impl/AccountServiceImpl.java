package ua.spalah.bank.services.impl;

import ua.spalah.bank.accounts.Account;
import ua.spalah.bank.services.AccountService;

/**
 * Created by Kostya on 05.01.2017.
 */
public class AccountServiceImpl implements AccountService {
    @Override
    public void deposit(Account account, double amount) {
        account.deposit(amount);
    }

    @Override
    public void withdraw(Account account, double amount) {
        account.withdraw(amount);
    }

    @Override
    public void transfer(Account fromAccount, Account toAccount, double amount) {
        fromAccount.withdraw(amount);
        toAccount.deposit(amount);
    }
}
