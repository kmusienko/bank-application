package ua.spalah.bank.exceptions;

/**
 * Created by Kostya on 10.01.2017.
 */
public class NotEnoughFundsException extends BankException{
    public NotEnoughFundsException(String str) {
        super(str);
    }
}
