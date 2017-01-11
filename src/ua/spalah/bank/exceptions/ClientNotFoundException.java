package ua.spalah.bank.exceptions;

import java.security.spec.ECFieldF2m;

/**
 * Created by Kostya on 10.01.2017.
 */
public class ClientNotFoundException extends BankException {
    public ClientNotFoundException(String name) {
        super("Client " + name + " not found");
    }
}
