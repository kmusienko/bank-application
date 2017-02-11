package ua.spalah.bank.services.impl;

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
    private Connection connection;

    @Override
    public Account save(Account account) {
        openConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO PUBLIC.ACCOUNTS (BALANCE, OVERDRAFT, TYPE) VALUES (?,?,?)");
            preparedStatement.setDouble(1,account.getBalance());
            if (account.getType() == AccountType.CHECKING) {
                CheckingAccount checkingAccount = (CheckingAccount) account;
                preparedStatement.setDouble(2, checkingAccount.getOverdraft());
            }
            preparedStatement.setString(3, account.getType() == AccountType.SAVING ? "saving" : "checking");
            preparedStatement.executeUpdate();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public Account update(Account account) {
        openConnection();
        String sql = "UPDATE PUBLIC.ACCOUNTS SET BALANCE = ?, OVERDRAFT = ? WHERE ID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, account.getBalance());
            if (account.getType() == AccountType.CHECKING) {
                CheckingAccount checkingAccount = (CheckingAccount) account;
                preparedStatement.setDouble(2, checkingAccount.getOverdraft());
            }
            preparedStatement.setLong(3, account.getId());
            preparedStatement.executeUpdate();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public Account saveOrUpdate(Account account) {
        openConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT ID FROM PUBLIC.ACCOUNTS WHERE ID = ?");
            preparedStatement.setLong(1, account.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                update(account);
            } else {
                save(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(long id) {
        openConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM PUBLIC.ACCOUNTS WHERE ID = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Account find(long id) {
        openConnection();
        Account account = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PUBLIC.ACCOUNTS WHERE ID = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                switch (resultSet.getString("type")) {
                    case "saving": account = new SavingAccount(resultSet.getDouble("balance")); break;
                    case "checking": account = new CheckingAccount(resultSet.getDouble("balance"), resultSet.getDouble("overdraft")); break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public List<Account> findAll() {
        openConnection();
        List<Account> accounts = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PUBLIC.ACCOUNTS");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                switch (resultSet.getString("type")) {
                    case "saving": accounts.add(new SavingAccount(resultSet.getDouble("balance"))); break;
                    case "checking": accounts.add(new CheckingAccount(resultSet.getDouble("balance"), resultSet.getDouble("overdraft"))); break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public List<Account> findByClientId(long clientId) {
        openConnection();
        List<Account> accounts = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PUBLIC.ACCOUNTS WHERE CLIENT_ID = ?");
            preparedStatement.setLong(1, clientId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                switch (resultSet.getString("type")) {
                    case "saving": accounts.add(new SavingAccount(resultSet.getDouble("balance"))); break;
                    case  "checking": accounts.add(new CheckingAccount(resultSet.getDouble("balance"), resultSet.getDouble("overdraft"))); break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return accounts;
    }

    @Override
    public Account findActiveAccountByClientName(String clientName) {
        openConnection();
        Account account = null;
        String sql = "SELECT * FROM PUBLIC.ACCOUNTS WHERE ID = (SELECT ACTIVE_ACCOUNT_ID FROM PUBLIC.CLIENTS WHERE NAME = ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, clientName);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                switch (resultSet.getString("type")) {
                    case "saving": account = new SavingAccount(resultSet.getDouble("balance")); break;
                    case "checking": account = new CheckingAccount(resultSet.getDouble("balance"), resultSet.getDouble("overdraft")); break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }
    private void openConnection() {
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/D:\\Programming\\SpalahJavaTasks\\BankApplication/dbbank", "sa", "");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
