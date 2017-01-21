package ua.spalah.bank.commands;

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
    public SelectActiveAccountCommand(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void execute() {
        if (BankCommander.currentClient == null) {
            System.out.println("You didn't choose a client!");
        } else {
            System.out.println("Pick number of the account you want to make an active: ");
            clientService.getAccountsInfo(BankCommander.currentClient);
            List<Account> accounts = BankCommander.currentClient.getAccounts();
            Scanner scanner = new Scanner(System.in);
            int k = scanner.nextInt();
            try {
                if (BankCommander.currentClient.getActiveAccount() == accounts.get(k - 1)) {
                    System.out.println("This account is already active!");
                } else {
                    clientService.selectActiveAccount(BankCommander.currentClient, accounts.get(k - 1));
                    System.out.println("Operation successfully completed");
                }
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Out of available index!");
            }
        }
    }

    @Override
    public String getCommandInfo() {
        return "Select an active account";
    }
}
