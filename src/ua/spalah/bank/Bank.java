package ua.spalah.bank;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Kostya on 23.12.2016.
 */
public class Bank {

    private ArrayList<Client> clients = new ArrayList<>();

    public void addClient(Client client) {
        clients.add(client);
    }

    public List<Client> getAllClients() {
        return Collections.unmodifiableList(clients);
    }

    public String getClientInfo(String name) {
        for (Client client : clients) {
            if (client.getName().equals(name)) {
                return client.toString();
            }
        }
        return "client not found";
    }
}
