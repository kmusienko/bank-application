package ua.spalah.bank.exceptions;

/**
 * Created by Kostya on 11.01.2017.
 */
public class ClientAlreadyExistsException extends BankException {

    public ClientAlreadyExistsException(String name) {
        super("Client " + name + " already exists");
    }
}
