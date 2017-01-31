package ua.spalah.bank.models;

import java.util.*;

/**
 * Created by Kostya on 23.12.2016.
 */
public class Bank {

    //private List<Client> clients = new ArrayList<>();
    private Map<String, Client> clients = new HashMap<>();

    public Map<String, Client> getAllClients() {
        return clients;
    }

}
