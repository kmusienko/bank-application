package ua.spalah.bank.commands;

import ua.spalah.bank.exceptions.ClientNotFoundException;
import ua.spalah.bank.exceptions.NotEnoughFundsException;
import ua.spalah.bank.ioCommander.IO;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.services.AccountService;
import ua.spalah.bank.services.ClientService;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by Kostya on 12.01.2017.
 */
//переводит деньги с активно счета текущего клиента на активный счет клиента, имя которого укажет пользователь
public class TransferCommand implements Command {
    private final ClientService clientService;
    private final AccountService accountService;
    private final IO ioConsole;
    public TransferCommand(ClientService clientService, AccountService accountService, IO ioConsole) {
        this.clientService = clientService;
        this.accountService = accountService;
        this.ioConsole = ioConsole;
    }

    @Override
    public void execute() {
        if (BankCommander.currentClient == null) { // может создать метод getCurrentClient()
            ioConsole.write("You didn't choose a client!"); // и кинуть там исключение, а здесь его словить?
        } else {
            ioConsole.write("Enter client's name who will receive money:");
            String name = ioConsole.read();
            try {
                Client receiver = clientService.findClientByName(BankCommander.currentBank, name);
                ioConsole.write("Please enter amount: ");
                double amount = Double.parseDouble(ioConsole.read());
                accountService.transfer(BankCommander.currentClient.getActiveAccount(), receiver.getActiveAccount(), amount);
                ioConsole.write("Operation successfully completed");
            } catch (InputMismatchException e) {
                ioConsole.write("This is not a number!");
            } catch (NotEnoughFundsException | ClientNotFoundException e) {
                ioConsole.write(e.getMessage());
            }
        }
    }

    @Override
    public String getCommandInfo() {
        return "Transfer from current client's active account to other client's active account";
    }
}
