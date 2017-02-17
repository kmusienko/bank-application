package ua.spalah.bank.dao;

import ua.spalah.bank.models.accounts.Account;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Kostya on 11.02.2017.
 */
public interface AccountDao {
    Account save(long clientId, Account account);
    Account update(Account account);
    Account saveOrUpdate(long clientId, Account account);
    void delete(long id);
    void deleteByClientId(long clientId);

    Account find(long id);
    List<Account> findAll();
    List<Account> findByClientId(long clientId);
    Account findActiveAccountByClientName(String clientName);
    Account getDesiredAccount(ResultSet resultSet);
}
