package ua.spalah.bank;

import java.util.List;

/**
 * Created by Kostya on 05.01.2017.
 */
public interface ClientService {
    Client findClientByName(Bank bank, String name);
    List<Client> findAllClients(Bank bank);
    Client saveClient(Bank bank, Client client);
    void deleteClient(Bank bank, Client client);
}
