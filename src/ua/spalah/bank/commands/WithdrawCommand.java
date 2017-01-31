package ua.spalah.bank.commands;

import ua.spalah.bank.exceptions.NotEnoughFundsException;
import ua.spalah.bank.ioCommander.IO;
import ua.spalah.bank.services.AccountService;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by Kostya on 12.01.2017.
 */
public class WithdrawCommand implements Command { // снимает деьги с активного счета текущего клиента
    private final AccountService accountService;
    private final IO ioConsole;
    
    public WithdrawCommand(AccountService accountService, IO ioConsole) {
        this.accountService = accountService;
        this.ioConsole = ioConsole;
    }

    @Override
    public void execute() {
        if (BankCommander.currentClient == null) { // может создать метод getCurrentClient()
            ioConsole.write("You didn't choose a client!"); // и кинуть там исключение, а здесь его словить?
        } else {
            ioConsole.write("Please enter deposit amount: ");
            try {
                double amount = Double.parseDouble(ioConsole.read());
                accountService.withdraw(BankCommander.currentClient.getActiveAccount(), amount);
                ioConsole.write("Operation successfully completed");
            } catch (InputMismatchException e) {
                ioConsole.write("This is not a number!");
            } catch (NotEnoughFundsException e) {
                ioConsole.write(e.getMessage());
            }
        }
    }

    @Override
    public String getCommandInfo() {
        return "Withdraw from client's active account";
    }
}
