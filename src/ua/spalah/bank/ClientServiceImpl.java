package ua.spalah.bank;

import java.util.List;

/**
 * Created by Kostya on 05.01.2017.
 */
public class ClientServiceImpl implements ClientService {

    @Override
    public Client findClientByName(Bank bank, String name) {
        for (Client client : bank.getAllClients()) {
            if (client.getName().equals(name)) {
                return client;
            }
        }
        return null;
    }

    @Override
    public List<Client> findAllClients(Bank bank) {
        return bank.getAllClients();
    }

    @Override
    public Client saveClient(Bank bank, Client client) {
        bank.addClient(client);
        return client;
    }

    @Override
    public void deleteClient(Bank bank, Client client) {
        bank.deleteClient(client);
    }
}
