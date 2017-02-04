package ua.spalah.bank.commands;

import ua.spalah.bank.ioCommander.AbstractCommand;
import ua.spalah.bank.ioCommander.IO;
import ua.spalah.bank.services.AccountService;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by Kostya on 12.01.2017.
 */
public class DepositCommand extends AbstractCommand implements Command { // кладет введенную пользователем сумму денег на активный счет текущего клиента
    private final AccountService accountService;

    public DepositCommand(AccountService accountService, IO io) {
        super(io);
        this.accountService = accountService;
    }

    @Override
    public void execute() {
        if (BankCommander.currentClient == null) {
            write("You didn't choose a client!");
        } else {
            write("Please enter deposit amount: ");
            try {
                double amount = Double.parseDouble(read());
                accountService.deposit(BankCommander.currentClient.getActiveAccount(), amount);
                write("Operation successfully completed");
            } catch (InputMismatchException e) {
                write("This is not a number!");
            }
        }
    }

    @Override
    public String getCommandInfo() {
        return "Deposit on client's active account";
    }
}
