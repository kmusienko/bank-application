package ua.spalah.bank.commands;

import ua.spalah.bank.ioCommander.IO;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.services.ClientService;

import java.util.List;
import java.util.Scanner;

/**
 * Created by Kostya on 16.01.2017.
 */
// позволяет выбрать активный счет для текущего клиента
public class SelectActiveAccountCommand implements Command{
    private final ClientService clientService;
    private final IO ioConsole;
    public SelectActiveAccountCommand(ClientService clientService, IO ioConsole) {
        this.clientService = clientService;
        this.ioConsole = ioConsole;
    }

    @Override
    public void execute() {
        if (BankCommander.currentClient == null) {
            ioConsole.write("You didn't choose a client!");
        } else {
            ioConsole.write("Pick number of the account you want to make an active: ");
            clientService.getAccountsInfo(BankCommander.currentClient);
            List<Account> accounts = BankCommander.currentClient.getAccounts();
            int k = Integer.parseInt(ioConsole.read());
            try {
                if (BankCommander.currentClient.getActiveAccount() == accounts.get(k - 1)) {
                    ioConsole.write("This account is already active!");
                } else {
                    clientService.selectActiveAccount(BankCommander.currentClient, accounts.get(k - 1));
                    ioConsole.write("Operation successfully completed");
                }
            } catch (IndexOutOfBoundsException e) {
                ioConsole.write("Out of available index!");
            }
        }
    }

    @Override
    public String getCommandInfo() {
        return "Select an active account";
    }
}
