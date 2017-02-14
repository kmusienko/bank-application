package ua.spalah.bank.services.impl;

import ua.spalah.bank.commands.BankCommander;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.models.type.Gender;
import ua.spalah.bank.services.AccountDao;
import ua.spalah.bank.services.ClientDao;
import ua.spalah.bank.services.ClientService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kostya on 12.02.2017.
 */
public class ClientDaoImpl implements ClientDao {
    private AccountDao accountDao = new AccountDaoImpl();
   // private Connection connection;

    @Override
    public Client save(Client client) {
        Client newClient = null;
        try {
            PreparedStatement preparedStatement = BankCommander.connection.prepareStatement("INSERT INTO PUBLIC.CLIENTS (NAME, GENDER, TEL, EMAIL, CITY, ACTIVE_ACCOUNT_ID) VALUES (?,?,?,?,?,?)");
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getGender() == Gender.MALE ? "Male" : "Female");
            preparedStatement.setString(3, client.getTel());
            preparedStatement.setString(4, client.getEmail());
            preparedStatement.setString(5, client.getCity());
            if (client.getActiveAccountId() != 0) {
                preparedStatement.setLong(6, client.getActiveAccountId());
            } else {
                preparedStatement.setNull(6, Types.NULL);
            }

            preparedStatement.executeUpdate();

            preparedStatement = BankCommander.connection.prepareStatement("SELECT ID FROM PUBLIC.CLIENTS WHERE NAME = ?");
            preparedStatement.setString(1, client.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            newClient = new Client(resultSet.getLong("id"), client.getName(), client.getGender(), client.getEmail(), client.getTel(), client.getCity());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newClient;
    }

    @Override
    public Client update(Client client) {
        String sql = "UPDATE PUBLIC.CLIENTS SET NAME = ?, GENDER = ?, TEL = ?, EMAIL = ?, CITY = ?, ACTIVE_ACCOUNT_ID = ? WHERE ID = ?";
        try {
            PreparedStatement preparedStatement = BankCommander.connection.prepareStatement(sql);
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getGender() == Gender.MALE ? "Male" : "Female");
            preparedStatement.setString(3, client.getTel());
            preparedStatement.setString(4, client.getEmail());
            preparedStatement.setString(5, client.getCity());
            if (client.getActiveAccountId() != 0) {
                preparedStatement.setLong(6, client.getActiveAccountId());
            } else {
                preparedStatement.setNull(6, Types.NULL);
            }
            preparedStatement.setLong(7, client.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    @Override
    public Client saveOrUpdate(Client client) {
        try {
            PreparedStatement preparedStatement = BankCommander.connection.prepareStatement("SELECT ID FROM PUBLIC.CLIENTS WHERE ID = ?");
            preparedStatement.setLong(1, client.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                update(client);
            } else {
                save(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    @Override
    public void delete(long clientId) {
        try {
            Client client = find(clientId);
            client.setActiveAccountId(0);
            update(client);
            List<Account> accounts = accountDao.findByClientId(clientId);
            for (Account account : accounts) {
                accountDao.delete(account.getId());
            }
            PreparedStatement preparedStatement = BankCommander.connection.prepareStatement("DELETE FROM PUBLIC.CLIENTS WHERE ID = ?");
            preparedStatement.setLong(1, clientId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Client find(long id) {
        Client client = null;
        try {
            PreparedStatement preparedStatement = BankCommander.connection.prepareStatement("SELECT * FROM PUBLIC.CLIENTS WHERE ID = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                client = new Client(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getString("gender").equalsIgnoreCase("Male") ? Gender.MALE : Gender.FEMALE, resultSet.getString("tel"), resultSet.getString("email"), resultSet.getString("city"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = BankCommander.connection.prepareStatement("SELECT * FROM PUBLIC.CLIENTS");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Client client = new Client(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("gender").equalsIgnoreCase("Male") ? Gender.MALE : Gender.FEMALE, resultSet.getString("tel"), resultSet.getString("email"), resultSet.getString("city"));
                client.setAccounts(accountDao.findByClientId(resultSet.getLong("id")));
                client.setActiveAccount(accountDao.findActiveAccountByClientName(client.getName()));
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    @Override
    public Client findByName(String name) {
        Client client = null;
        try {
            PreparedStatement preparedStatement = BankCommander.connection.prepareStatement("SELECT * FROM PUBLIC.CLIENTS WHERE NAME = ?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                client = new Client(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getString("gender").equalsIgnoreCase("Male") ? Gender.MALE : Gender.FEMALE, resultSet.getString("tel"), resultSet.getString("email"), resultSet.getString("city"));
                client.setAccounts(accountDao.findByClientId(resultSet.getLong("id")));
                client.setActiveAccount(accountDao.findActiveAccountByClientName(name));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }
}
