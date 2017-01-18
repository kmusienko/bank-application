package ua.spalah.bank.exceptions;

/**
 * Created by Kostya on 10.01.2017.
 */
public class OverdraftLimitExceededException extends NotEnoughFundsException {
    public OverdraftLimitExceededException(double available) {
        super("You exceed your ovedraft only $ " + available);
    }
}
