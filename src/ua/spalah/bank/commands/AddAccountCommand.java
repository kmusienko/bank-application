package ua.spalah.bank.commands;

import ua.spalah.bank.ioCommander.IO;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.models.accounts.CheckingAccount;
import ua.spalah.bank.models.accounts.SavingAccount;
import ua.spalah.bank.services.ClientService;

import java.util.Scanner;

/**
 * Created by Kostya on 16.01.2017.
 */
// добавляет счет текущему клиенту, спрашивая всю информацию о счете у пользователя (если у клиента нет счетов,
// делаем этот активным, если уже есть, то прелагаем сделать его активным опционально)
public class AddAccountCommand implements Command {
    private final ClientService clientService;
    private final IO ioConsole;

    public AddAccountCommand(ClientService clientService, IO ioConsole) {
        this.clientService = clientService;
        this.ioConsole = ioConsole;
    }


    @Override
    public void execute() {
        if (BankCommander.currentClient == null) {
            ioConsole.write("You didn't choose a client!");
        } else {
            ioConsole.write("What type of account you want to create? 1 - Saving, 2 - Checking");
            int intType = Integer.parseInt(ioConsole.read());
            ioConsole.write("Please enter the balance: ");
            double balance = Double.parseDouble(ioConsole.read());
            Account account = null;
            boolean isCorrectType = false;
            switch (intType) {
                case 1:
                    isCorrectType = true;
                    account = new SavingAccount(balance);
                    break;
                case 2:
                    isCorrectType = true;
                    ioConsole.write("Please enter the overdraft:");
                    double overdraft = Double.parseDouble(ioConsole.read());
                    account = new CheckingAccount(balance, overdraft);
                    break;
                default:
                    ioConsole.write("Unknown type.");
            }
            if (isCorrectType) {
                clientService.addAccount(BankCommander.currentClient, account);
                if (BankCommander.currentClient.getAccounts().size() == 1) {
                    BankCommander.currentClient.setActiveAccount(account);
                } else {
                    ioConsole.write("Do you want to make this account active? (y/n)");
                    if (ioConsole.read().charAt(0) == 'y') {
                        BankCommander.currentClient.setActiveAccount(account);
                    }
                }
                ioConsole.write("Operation successfully completed");
            }
        }
    }

    @Override
    public String getCommandInfo() {
        return "Add an account";
    }
}
