package ua.spalah.bank.services;

import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.accounts.Account;

import java.util.List;

/**
 * Created by Kostya on 11.02.2017.
 */
public interface AccountDao {
    Account save(Account account);
    Account update(Account account);
    Account saveOrUpdate(Account account);
    void delete(long id);

    Account find(long id);
    List<Account> findAll();
    List<Account> findByClientId(long clientId);
    Account findActiveAccountByClientName(String clientName);
}
