package ua.spalah.bank.services.impl;

import ua.spalah.bank.commands.BankCommander;
import ua.spalah.bank.models.accounts.SavingAccount;
import ua.spalah.bank.services.AccountDao;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.models.accounts.CheckingAccount;
import ua.spalah.bank.models.type.AccountType;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kostya on 11.02.2017.
 */
public class AccountDaoImpl implements AccountDao {
    // private Connection connection;

    @Override
    public Account save(long client_id, Account account) {
        try {
            PreparedStatement preparedStatement = BankCommander.connection.prepareStatement("INSERT INTO PUBLIC.ACCOUNTS (BALANCE, OVERDRAFT, TYPE, CLIENT_ID) VALUES (?,?,?,?)");
            preparedStatement.setDouble(1, account.getBalance());
            if (account.getType() == AccountType.CHECKING) {
                CheckingAccount checkingAccount = (CheckingAccount) account;
                preparedStatement.setDouble(2, checkingAccount.getOverdraft());
            } else {
                preparedStatement.setNull(2, Types.DOUBLE);
            }
            preparedStatement.setString(3, account.getType() == AccountType.SAVING ? "saving" : "checking");
            preparedStatement.setLong(4, client_id);
            preparedStatement.executeUpdate();

            account = findByClientId(client_id).get(findByClientId(client_id).size()-1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public Account update(long client_id, Account account) {
        String sql = "UPDATE PUBLIC.ACCOUNTS SET BALANCE = ?, OVERDRAFT = ? WHERE ID = ?";
        try {
            PreparedStatement preparedStatement = BankCommander.connection.prepareStatement(sql);
            preparedStatement.setDouble(1, account.getBalance());
            if (account.getType() == AccountType.CHECKING) {
                CheckingAccount checkingAccount = (CheckingAccount) account;
                preparedStatement.setDouble(2, checkingAccount.getOverdraft());
            } else {
                preparedStatement.setNull(2, Types.DOUBLE);
            }
            preparedStatement.setLong(3, account.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public Account saveOrUpdate(long client_id, Account account) {
        try {
            PreparedStatement preparedStatement = BankCommander.connection.prepareStatement("SELECT ID FROM PUBLIC.ACCOUNTS WHERE ID = ?");
            preparedStatement.setLong(1, account.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                update(client_id, account);
            } else {
                save(client_id, account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public void delete(long id) {
        try {
            PreparedStatement preparedStatement = BankCommander.connection.prepareStatement("DELETE FROM PUBLIC.ACCOUNTS WHERE ID = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Account find(long id) {
        Account account = null;
        try {
            PreparedStatement preparedStatement = BankCommander.connection.prepareStatement("SELECT * FROM PUBLIC.ACCOUNTS WHERE ID = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                switch (resultSet.getString("type")) {
                    case "saving":
                        account = new SavingAccount(resultSet.getLong("id"), resultSet.getDouble("balance"));
                        break;
                    case "checking":
                        account = new CheckingAccount(resultSet.getLong("id"), resultSet.getDouble("balance"), resultSet.getDouble("overdraft"));
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public List<Account> findAll() {
        List<Account> accounts = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = BankCommander.connection.prepareStatement("SELECT * FROM PUBLIC.ACCOUNTS");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                switch (resultSet.getString("type")) {
                    case "saving":
                        accounts.add(new SavingAccount(resultSet.getLong("id"), resultSet.getDouble("balance")));
                        break;
                    case "checking":
                        accounts.add(new CheckingAccount(resultSet.getLong("id"), resultSet.getDouble("balance"), resultSet.getDouble("overdraft")));
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public List<Account> findByClientId(long clientId) {
        List<Account> accounts = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = BankCommander.connection.prepareStatement("SELECT * FROM PUBLIC.ACCOUNTS WHERE CLIENT_ID = ?");
            preparedStatement.setLong(1, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                switch (resultSet.getString("type")) {
                    case "saving":
                        accounts.add(new SavingAccount(resultSet.getLong("id"), resultSet.getDouble("balance")));
                        break;
                    case "checking":
                        accounts.add(new CheckingAccount(resultSet.getLong("id"), resultSet.getDouble("balance"), resultSet.getDouble("overdraft")));
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public Account findActiveAccountByClientName(String clientName) {
        Account account = null;
        String sql = "SELECT * FROM PUBLIC.ACCOUNTS WHERE ID = (SELECT ACTIVE_ACCOUNT_ID FROM PUBLIC.CLIENTS WHERE NAME = ?)";
        try {
            PreparedStatement preparedStatement = BankCommander.connection.prepareStatement(sql);
            preparedStatement.setString(1, clientName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                switch (resultSet.getString("type")) {
                    case "saving":
                        account = new SavingAccount(resultSet.getLong("id"), resultSet.getDouble("balance"));
                        break;
                    case "checking":
                        account = new CheckingAccount(resultSet.getLong("id"), resultSet.getDouble("balance"), resultSet.getDouble("overdraft"));
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }
}