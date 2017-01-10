package ua.spalah.bank.services;

import ua.spalah.bank.exceptions.ClientNotFoundException;
import ua.spalah.bank.models.Bank;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.accounts.Account;

import java.util.List;

/**
 * Created by Kostya on 05.01.2017.
 */
public interface ClientService {
    Client findClientByName(Bank bank, String name) throws ClientNotFoundException;
    List<Client> findAllClients(Bank bank);
    Client saveClient(Bank bank, Client client);
    void deleteClient(Bank bank, Client client) throws ClientNotFoundException;
    void addAccount(Client client, Account account);
    double getTotalBalance(Client client);
}
