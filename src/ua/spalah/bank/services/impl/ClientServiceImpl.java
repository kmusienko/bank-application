package ua.spalah.bank.services.impl;

import ua.spalah.bank.exceptions.ClientAlreadyExistsException;
import ua.spalah.bank.exceptions.ClientNotFoundException;
import ua.spalah.bank.models.Bank;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.services.ClientService;

import java.util.List;
import java.util.Map;

/**
 * Created by Kostya on 05.01.2017.
 */
public class ClientServiceImpl implements ClientService {

    @Override
    public Client findClientByName(Bank bank, String name) throws ClientNotFoundException {
        Client client = bank.getAllClients().get(name);
        if (client == null) {
            throw new ClientNotFoundException(name);
        } else {
            return client;
        }
    }

    @Override
    public Map<String, Client> findAllClients(Bank bank) {
        return bank.getAllClients();
    }

    @Override
    public Client saveClient(Bank bank, Client client) throws ClientAlreadyExistsException {
        if (!bank.getAllClients().containsKey(client.getName())) {
            bank.getAllClients().put(client.getName(), client);
        } else {
            throw new ClientAlreadyExistsException(client.getName());
        }
        return client;
    }

    @Override
    public void deleteClient(Bank bank, Client client) throws ClientNotFoundException {
        if (bank.getAllClients().containsKey(client.getName())) {
            bank.getAllClients().remove(client.getName());
        } else {
            throw new ClientNotFoundException(client.getName());
        }
    }

    @Override
    public void addAccount(Client client, Account account) {
        client.getAccounts().add(account);
        if (client.getAccounts().size() == 1) {
            client.setActiveAccount(account);
        }

    }

    @Override
    public double getTotalBalance(Client client) {
        double totalBalance = 0;
        for (Account account : client.getAccounts()) {
            totalBalance += account.getBalance();
        }
        return totalBalance;
    }

    @Override
    public void getAccountsInfo(Client client) {
        List<Account> accounts = client.getAccounts();
        for (int i = 0; i < accounts.size(); i++) {
            String isActive = client.getActiveAccount() == accounts.get(i) ? ", *active account*" : "";
            System.out.println("[" + (i + 1) + "] " + accounts.get(i).toString() + isActive);
        }
    }

    @Override
    public void selectActiveAccount(Client client, Account account) {
        client.setActiveAccount(account);
    }
}
