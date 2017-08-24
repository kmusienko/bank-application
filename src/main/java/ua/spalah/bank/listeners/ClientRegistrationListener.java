package ua.spalah.bank.listeners;

import ua.spalah.bank.models.Client;

/**
 * Created by Kostya on 01.01.2017.
 */
public interface ClientRegistrationListener {
    void onClientAdded(Client c);
}
