package ua.spalah.bank.dao.impl;

import ua.spalah.bank.commands.BankCommander;
import ua.spalah.bank.exceptions.DataBaseException;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.models.type.Gender;
import ua.spalah.bank.dao.AccountDao;
import ua.spalah.bank.dao.ClientDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kostya on 12.02.2017.
 */
public class ClientDaoImpl implements ClientDao {
    private AccountDao accountDao;

    public ClientDaoImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public Client save(Client client) {
        try {
            PreparedStatement preparedStatement = BankCommander.connection.prepareStatement("INSERT INTO PUBLIC.CLIENTS (NAME, GENDER, TEL, EMAIL, CITY, ACTIVE_ACCOUNT_ID) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getGender().name());
            preparedStatement.setString(3, client.getTel());
            preparedStatement.setString(4, client.getEmail());
            preparedStatement.setString(5, client.getCity());
            if (client.getActiveAccount() != null) {
                preparedStatement.setLong(6, client.getActiveAccount().getId());
            } else {
                preparedStatement.setNull(6, Types.NULL);
            }

            preparedStatement.executeUpdate();

            for (Account account : client.getAccounts()) {
                accountDao.saveOrUpdate(client.getId(), account);
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    client.setId(generatedKeys.getLong(1));
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return client;
    }

    @Override
    public Client update(Client client) {
        String sql = "UPDATE PUBLIC.CLIENTS SET NAME = ?, GENDER = ?, TEL = ?, EMAIL = ?, CITY = ?, ACTIVE_ACCOUNT_ID = ? WHERE ID = ?";
        try {
            PreparedStatement preparedStatement = BankCommander.connection.prepareStatement(sql);
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getGender().name());
            preparedStatement.setString(3, client.getTel());
            preparedStatement.setString(4, client.getEmail());
            preparedStatement.setString(5, client.getCity());
            if (client.getActiveAccount() != null) {
                preparedStatement.setLong(6, client.getActiveAccount().getId());
            } else {
                preparedStatement.setNull(6, Types.NULL);
            }
            preparedStatement.setLong(7, client.getId());

            preparedStatement.executeUpdate();

            // TODO: save accounts as well
            for (Account account : client.getAccounts()) {
                accountDao.saveOrUpdate(client.getId(), account);
            }

        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return client;
    }

    @Override
    public Client saveOrUpdate(Client client) {
        long id = client.getId();
        if (id == 0) {
            return save(client);
        } else {
            return update(client);
        }
    }

    @Override
    public void delete(long clientId) {
        try {
            Client client = find(clientId);
            if (client.getActiveAccount() != null) {
                client.setActiveAccount(null);
                update(client);
            }
            accountDao.deleteByClientId(clientId);
            PreparedStatement preparedStatement = BankCommander.connection.prepareStatement("DELETE FROM PUBLIC.CLIENTS WHERE ID = ?");
            preparedStatement.setLong(1, clientId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public Client find(long id) {
        try {
            PreparedStatement preparedStatement = BankCommander.connection.prepareStatement("SELECT * FROM PUBLIC.CLIENTS WHERE ID = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return createClient(resultSet);
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    @Override
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = BankCommander.connection.prepareStatement("SELECT * FROM PUBLIC.CLIENTS");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Client client = createClient(resultSet);
                clients.add(client);
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
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
                client = createClient(resultSet);
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return client;
    }
    @Override
    public Client createClient(ResultSet resultSet) {
        Client client;
        try {
            client = new Client(resultSet.getLong("id"), resultSet.getString("name"), Gender.valueOf(resultSet.getString("gender")), resultSet.getString("tel"), resultSet.getString("email"), resultSet.getString("city"));
            long activeAccountId = resultSet.getLong("active_account_id");
            List<Account> accounts = accountDao.findByClientId(resultSet.getLong("id"));
            client.setAccounts(accounts);
            for (Account account : accounts) {
                if (account.getId() == activeAccountId) {
                    client.setActiveAccount(account);
                    break;
                }
            }
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
        return client;
    }
}
