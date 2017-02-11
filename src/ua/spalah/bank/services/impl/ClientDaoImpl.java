package ua.spalah.bank.services.impl;

import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.type.Gender;
import ua.spalah.bank.services.ClientDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kostya on 12.02.2017.
 */
public class ClientDaoImpl implements ClientDao {
    private Connection connection;

    @Override
    public Client save(Client client) {
        openConnection();
        Client newClient = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO PUBLIC.CLIENTS (NAME, GENDER, TEL, EMAIL, CITY) VALUES (?,?,?,?,?)");
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getGender() == Gender.MALE ? "Male" : "Female");
            preparedStatement.setString(3, client.getTel());
            preparedStatement.setString(4, client.getEmail());
            preparedStatement.setString(5, client.getCity());

            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement("SELECT ID FROM PUBLIC.CLIENTS WHERE NAME = ?");
            preparedStatement.setString(1, client.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            newClient = new Client(resultSet.getInt("ID"), client.getName(), client.getGender(), client.getEmail(), client.getTel(), client.getCity());
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newClient;
    }

    @Override
    public Client update(Client client) {
        openConnection();
        String sql = "UPDATE PUBLIC.CLIENTS SET NAME = ?, GENDER = ?, TEL = ?, EMAIL = ?, CITY = ? WHERE ID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, client.getName());
            preparedStatement.setString(2, client.getGender() == Gender.MALE ? "Male" : "Female");
            preparedStatement.setString(3, client.getTel());
            preparedStatement.setString(4, client.getEmail());
            preparedStatement.setString(5, client.getCity());

            preparedStatement.executeUpdate();
            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    @Override
    public Client saveOrUpdate(Client client) {
        openConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT ID FROM PUBLIC.CLIENTS WHERE ID = ?");
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
        return null;
    }

    @Override
    public void delete(long clientId) {
        openConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM PUBLIC.CLIENTS WHERE ID = ?");
            preparedStatement.setLong(1, clientId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Client find(long id) {
        openConnection();
        Client client = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PUBLIC.CLIENTS WHERE ID = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                client = new Client(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("gender").equalsIgnoreCase("Male") ? Gender.MALE : Gender.FEMALE, resultSet.getString("tel"), resultSet.getString("email"), resultSet.getString("city"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
    }

    @Override
    public List<Client> findAll() {
        openConnection();
        List<Client> clients = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PUBLIC.CLIENTS");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                clients.add(new Client(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("gender").equalsIgnoreCase("Male") ? Gender.MALE : Gender.FEMALE, resultSet.getString("tel"), resultSet.getString("email"), resultSet.getString("city")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    @Override
    public Client findByName(String name) {
        openConnection();
        Client client = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM PUBLIC.CLIENTS WHERE NAME = ?");
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                client = new Client(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("gender").equalsIgnoreCase("Male") ? Gender.MALE : Gender.FEMALE, resultSet.getString("tel"), resultSet.getString("email"), resultSet.getString("city"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return client;
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
