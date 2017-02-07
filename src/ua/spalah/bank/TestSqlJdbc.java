package ua.spalah.bank;


import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.models.accounts.CheckingAccount;
import ua.spalah.bank.models.accounts.SavingAccount;
import ua.spalah.bank.models.type.Gender;
import ua.spalah.bank.services.ClientService;
import ua.spalah.bank.services.impl.ClientServiceImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kostya on 07.02.2017.
 */
public class TestSqlJdbc {
    private Connection connection;
    private Statement statement;

    private void openConnection() {
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/D:\\Programming\\SpalahJavaTasks\\BankApplication/dbbank", "sa", "");
            statement = connection.createStatement();
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

    public List<Client> read() {
        List<Client> clients = new ArrayList<>();
        ClientService clientService = new ClientServiceImpl();
        openConnection();
        try {
            ResultSet resultSet = statement.executeQuery("SELECT * FROM CLIENTS");
            while (resultSet.next()) {
                Client client = new Client(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getString("gender").equalsIgnoreCase("Male") ? Gender.MALE : Gender.FEMALE, resultSet.getString("email"), resultSet.getString("tel"), resultSet.getString("city"));
                clients.add(client);
            }
            resultSet.close();

            for (Client client: clients) {
                ResultSet resAccounts = statement.executeQuery("SELECT * FROM ACCOUNTS");
                while(resAccounts.next()) {
                    if (resAccounts.getInt("client_id") == client.getId()) {
                        Account account = null;
                        switch (resAccounts.getString(3)) {
                            case "saving" : account = new SavingAccount(resAccounts.getInt(1)); break;
                            case "checking" : account = new CheckingAccount(resAccounts.getInt(1), resAccounts.getInt(2)); break;
                        }
                        clientService.addAccount(client, account);
                    }
                }
                resAccounts.close();
            }

            closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

}
