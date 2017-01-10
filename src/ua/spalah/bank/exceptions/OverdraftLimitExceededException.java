package ua.spalah.bank.exceptions;

/**
 * Created by Kostya on 10.01.2017.
 */
public class OverdraftLimitExceededException extends BankException {
    public OverdraftLimitExceededException(String str) {
        super(str);
    }
}
