package ua.spalah.bank.models;

import ua.spalah.bank.listeners.ClientRegistrationListener;

import java.util.*;

/**
 * Created by Kostya on 23.12.2016.
 */
public class Bank {

    private ArrayList<Client> clients = new ArrayList<>();
    private List<ClientRegistrationListener> listeners = new ArrayList<>();

    public void addClient(Client client) {
        clients.add(client);
        for (ClientRegistrationListener listener : listeners) {
            listener.onClientAdded(client);
        }
    }

    public List<Client> getAllClients() {
        return Collections.unmodifiableList(clients); //стоит ли использовать??
    }
    public List<Client> getClients() {
        return clients;
    }
    public String getClientInfo(String name) {
        for (Client client : clients) {
            if (client.getName().equals(name)) {
                return client.toString();
            }
        }
        return "client not found";
    }

    public void addListener(ClientRegistrationListener listener) {
        listeners.add(listener);
    }

//    public void deleteClient(Client client) {
//        if (clients.contains(client)) {
//            clients.remove(client);
//        }
//    }
}
