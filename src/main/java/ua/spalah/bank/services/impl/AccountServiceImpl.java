package ua.spalah.bank.services.impl;

import ua.spalah.bank.dao.AccountDao;
import ua.spalah.bank.dao.ClientDao;
import ua.spalah.bank.exceptions.NotEnoughFundsException;
import ua.spalah.bank.exceptions.OverdraftLimitExceededException;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.models.accounts.CheckingAccount;
import ua.spalah.bank.models.type.AccountType;
import ua.spalah.bank.models.type.AccountType;
import ua.spalah.bank.services.AccountService;

/**
 * Created by Kostya on 05.01.2017.
 */
public class AccountServiceImpl implements AccountService {
    private AccountDao accountDao;
    private ClientDao clientDao;

    public AccountServiceImpl(AccountDao accountDao, ClientDao clientDao) {
        this.accountDao = accountDao;
        this.clientDao = clientDao;
    }

    @Override
    public Account addAccount(Client client, Account account) {
        account = accountDao.save(client.getId(), account);
        client.getAccounts().add(account);
        if (client.getAccounts().size() == 1) {
            client.setActiveAccount(account);
            clientDao.update(client);
        }
        return account;
    }

    @Override
    public void deposit(Account account, double amount) {

        if (amount <= 0) throw new IllegalArgumentException("Amount can't be negative.");
        account.setBalance(account.getBalance() + amount);
        accountDao.update(account);
    }

    @Override
    public void withdraw(Account account, double amount) throws NotEnoughFundsException {
        if (amount <= 0) throw new IllegalArgumentException("Amount can't be negative.");
        AccountType accountType = account.getAccountType();
        if (accountType == AccountType.SAVING) {
            if (amount > account.getBalance()) throw new NotEnoughFundsException(account.getBalance());
            account.setBalance(account.getBalance() - amount);
        } else if (accountType == AccountType.CHECKING) {
            double available = account.getBalance() + ((CheckingAccount) account).getOverdraft();
            if (available < amount) throw new OverdraftLimitExceededException(available);
            account.setBalance(account.getBalance() - amount);
            accountDao.update(account);
        } else {
            throw new IllegalArgumentException("Unknown type");
        }
    }

    @Override
    public void transfer(Account fromAccount, Account toAccount, double amount) throws NotEnoughFundsException {
        // не могу понять как грамотно прикрутить транзакции и батчи.
        withdraw(fromAccount, amount);
        deposit(toAccount, amount);

    }
    @Override
    public Account findActiveAccountByClientName(String clientName) {
        return accountDao.findActiveAccountByClientName(clientName);
    }
    @Override
    public Account findAccountById(long accountId) {
        return accountDao.find(accountId);
    }
}
