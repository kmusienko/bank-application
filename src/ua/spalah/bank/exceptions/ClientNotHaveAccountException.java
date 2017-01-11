package ua.spalah.bank.exceptions;

/**
 * Created by Kostya on 11.01.2017.
 */
public class ClientNotHaveAccountException extends BankException {
    public ClientNotHaveAccountException(String message) {
        super(message);
    }
}
