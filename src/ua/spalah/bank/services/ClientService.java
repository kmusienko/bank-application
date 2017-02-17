package ua.spalah.bank.services;

import ua.spalah.bank.exceptions.ClientAlreadyExistsException;
import ua.spalah.bank.exceptions.ClientNotFoundException;
import ua.spalah.bank.exceptions.ClientNotHaveAccountException;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.accounts.Account;

import java.util.List;

/**
 * Created by Kostya on 05.01.2017.
 */
public interface ClientService {
    Client findClientByName(String name) throws ClientNotFoundException;
    List<Client> findAllClients();
    Client saveClient(Client client) throws ClientAlreadyExistsException;;
    void deleteClient(Client client) throws ClientNotFoundException;
    double getTotalBalance(Client client);
    void getAccountsInfo(Client client);
    void selectActiveAccount(Client client, Account account) throws ClientNotHaveAccountException;
}
