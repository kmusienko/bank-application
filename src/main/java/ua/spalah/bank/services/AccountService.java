package ua.spalah.bank.services;

import ua.spalah.bank.exceptions.NotEnoughFundsException;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.accounts.Account;

/**
 * Created by Kostya on 05.01.2017.
 */
public interface AccountService {
    Account addAccount(Client client, Account account);
    void deposit(Account account, double amount);
    void withdraw(Account account, double amount) throws NotEnoughFundsException;
    void transfer(Account fromAccount, Account toAccount, double amount) throws NotEnoughFundsException;
    Account findActiveAccountByClientName(String clientName);
    Account findAccountById(long accountId);
}
