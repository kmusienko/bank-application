package ua.spalah.bank.commands;

import ua.spalah.bank.exceptions.ClientNotHaveAccountException;
import ua.spalah.bank.ioCommander.AbstractCommand;
import ua.spalah.bank.ioCommander.IO;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.services.ClientService;

import java.util.List;

/**
 * Created by Kostya on 16.01.2017.
 */
// позволяет выбрать активный счет для текущего клиента
public class SelectActiveAccountCommand extends AbstractCommand implements Command{
    private final ClientService clientService;
    public SelectActiveAccountCommand(ClientService clientService, IO io) {
        super(io);
        this.clientService = clientService;
    }

    @Override
    public void execute() {
        if (BankCommander.currentClient == null) {
            write("You didn't choose a client!");
        } else {
            write("Pick number of the account you want to make an active: ");
            clientService.getAccountsInfo(BankCommander.currentClient);
            List<Account> accounts = BankCommander.currentClient.getAccounts();
            int k = Integer.parseInt(read());
            try {
                if (BankCommander.currentClient.getActiveAccount() == accounts.get(k - 1)) {
                    write("This account is already active!");
                } else {
                    clientService.selectActiveAccount(BankCommander.currentClient, accounts.get(k - 1));
                    write("Operation successfully completed");
                }
            } catch (IndexOutOfBoundsException e) {
                write("Out of available index!");
            } catch (ClientNotHaveAccountException e) {
                write(e.getMessage());
            }
        }
    }

    @Override
    public String getCommandInfo() {
        return "Select an active account";
    }
}
