package ua.spalah.bank.listeners;

import ua.spalah.bank.models.Client;

/**
 * Created by Kostya on 01.01.2017.
 */
public class PrintClientListener implements ClientRegistrationListener {

    @Override
    public void onClientAdded(Client c) {
        System.out.println(c.toString());
    }
}
