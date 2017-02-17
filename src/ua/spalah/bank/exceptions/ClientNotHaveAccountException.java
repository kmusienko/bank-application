package ua.spalah.bank.exceptions;

/**
 * Created by Kostya on 15.02.2017.
 */
public class ClientNotHaveAccountException extends BankException{
    public ClientNotHaveAccountException(String clientName) {
        super("Client " + clientName + " doesn't have this account.");
    }
}
