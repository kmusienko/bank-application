package ua.spalah.bank.commands;

import ua.spalah.bank.ioCommander.IO;
import ua.spalah.bank.services.AccountService;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by Kostya on 12.01.2017.
 */
public class DepositCommand implements Command { // кладет введенную пользователем сумму денег на активный счет текущего клиента
    private final AccountService accountService;
    private final IO ioConsole;

    public DepositCommand(AccountService accountService, IO ioConsole) {
        this.accountService = accountService;
        this.ioConsole = ioConsole;
    }

    @Override
    public void execute() {
        if (BankCommander.currentClient == null) {
            ioConsole.write("You didn't choose a client!");
        } else {
            ioConsole.write("Please enter deposit amount: ");
            try {
                double amount = Double.parseDouble(ioConsole.read());
                accountService.deposit(BankCommander.currentClient.getActiveAccount(), amount);
                ioConsole.write("Operation successfully completed");
            } catch (InputMismatchException e) {
                ioConsole.write("This is not a number!");
            }
        }
    }

    @Override
    public String getCommandInfo() {
        return "Deposit on client's active account";
    }
}
