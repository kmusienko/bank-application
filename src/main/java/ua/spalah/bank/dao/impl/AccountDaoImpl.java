package ua.spalah.bank.dao.impl;

import ua.spalah.bank.dao.AccountDao;
import ua.spalah.bank.exceptions.DataBaseException;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.models.accounts.CheckingAccount;
import ua.spalah.bank.models.accounts.SavingAccount;
import ua.spalah.bank.models.type.AccountType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kostya on 11.02.2017.
 */
public class AccountDaoImpl implements AccountDao {
    private Connection connection;
    public AccountDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Account save(long client_id, Account account) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO PUBLIC.ACCOUNTS (BALANCE, OVERDRAFT, TYPE, CLIENT_ID) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDouble(1, account.getBalance());
            if (account.getAccountType() == AccountType.CHECKING) {
                CheckingAccount checkingAccount = (CheckingAccount) account;
                preparedStatement.setDouble(2, checkingAccount.getOverdraft());
            } else {
                preparedStatement.setNull(2, Types.DOUBLE);
            }
            preparedStatement.setString(3, account.getAccountType().name());
            preparedStatement.setLong(4, client_id);
            preparedStatement.executeUpdate();

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    account.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
//            account = findByClientId(client_id).get(findByClientId(client_id).size()-1);

        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return account;
    }

    @Override
    public Account update(Account account) {
        String sql = "UPDATE PUBLIC.ACCOUNTS SET BALANCE = ?, OVERDRAFT = ? WHERE ID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, account.getBalance());
            if (account.getAccountType() == AccountType.CHECKING) {
                CheckingAccount checkingAccount = (CheckingAccount) account;
                preparedStatement.setDouble(2, checkingAccount.getOverdraft());
            } else {
                preparedStatement.setNull(2, Types.DOUBLE);
            }
            preparedStatement.setLong(3, account.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return account;
    }

    @Override
    public Account saveOrUpdate(long client_id, Account account) {
        long id = account.getId();
        if (id == 0) {
            return save(client_id, account);
        } else {
            return update(account);
        }
    }

    @Override
    public void delete(long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM PUBLIC.ACCOUNTS WHERE ID = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }
    @Override
    public void deleteByClientId(long clientId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM PUBLIC.ACCOUNTS WHERE CLIENT_ID = ?");
            preparedStatement.setLong(1, clientId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public Account find(long id) {
        Account account = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PUBLIC.ACCOUNTS WHERE ID = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                account = getDesiredAccount(resultSet);
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return account;
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PUBLIC.ACCOUNTS");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
               accounts.add(getDesiredAccount(resultSet));
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return accounts;
    }

    @Override
    public List<Account> findByClientId(long clientId) {
        List<Account> accounts = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PUBLIC.ACCOUNTS WHERE CLIENT_ID = ?");
            preparedStatement.setLong(1, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                accounts.add(getDesiredAccount(resultSet));
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return accounts;
    }

    @Override
    public Account findActiveAccountByClientName(String clientName) {
        Account account = null;
        String sql = "SELECT * FROM PUBLIC.ACCOUNTS WHERE ID = (SELECT ACTIVE_ACCOUNT_ID FROM PUBLIC.CLIENTS WHERE NAME = ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, clientName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
               account = getDesiredAccount(resultSet);
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return account;
    }
    @Override
    public Account getDesiredAccount(ResultSet resultSet) {
        Account account = null;
        AccountType type;
        try {
            type = AccountType.valueOf(resultSet.getString("type"));
            switch (type) {
                case SAVING:
                    account = new SavingAccount(resultSet.getLong("id"), resultSet.getDouble("balance"));
                    break;
                case CHECKING:
                    account = new CheckingAccount(resultSet.getLong("id"), resultSet.getDouble("balance"), resultSet.getDouble("overdraft"));
                    break;
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
       return account;
    }
}