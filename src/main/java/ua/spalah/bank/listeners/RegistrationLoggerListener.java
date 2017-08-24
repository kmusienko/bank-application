package ua.spalah.bank.listeners;

import ua.spalah.bank.models.Client;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by Kostya on 01.01.2017.
 */
public class RegistrationLoggerListener implements ClientRegistrationListener {
    @Override
    public void onClientAdded(Client c) {
        System.out.println("Client " + c.getName() + " added on " + LocalDate.now() + " " + LocalTime.now());
    }
}
