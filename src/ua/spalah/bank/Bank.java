package ua.spalah.bank;

import java.util.ArrayList;

/**
 * Created by Kostya on 23.12.2016.
 */
public class Bank {

    private ArrayList<Client> clients = new ArrayList<>();

    public void addClient(Client client) {
        clients.add(client);
    }

    public ArrayList<Client> getAllClients() {
        return clients;
    }

    public String getClientInfo(String name) {
        String info = "";
        for (Client client : clients) {
            if (client.getName().equals(name)) {
                info = client.toString();
            }
        }
        return info;
    }
}
