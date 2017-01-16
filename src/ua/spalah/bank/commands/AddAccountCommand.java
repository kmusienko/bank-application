package ua.spalah.bank.commands;

import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.models.accounts.CheckingAccount;
import ua.spalah.bank.models.accounts.SavingAccount;
import ua.spalah.bank.models.type.AccountType;
import ua.spalah.bank.services.ClientService;

import java.util.Scanner;

/**
 * Created by Kostya on 16.01.2017.
 */
// добавляет счет текущему клиенту, спрашивая всю информацию о счете у пользователя (если у клиента нет счетов,
// делаем этот активным, если уже есть, то прелагаем сделать его активным опционально)
public class AddAccountCommand implements Command {
    private final ClientService clientService;

    public AddAccountCommand(ClientService clientService) {
        this.clientService = clientService;
    }


    @Override
    public void execute() {
        if (BankCommander.currentClient == null) {
            System.out.println("You didn't choose a client!");
        } else {
            System.out.println("What type of account you want to create? 1 - Saving, 2 - Checking");
            Scanner scanner = new Scanner(System.in);
            int intType = scanner.nextInt();
            System.out.println("Please enter the balance: ");
            double balance = scanner.nextDouble();
            Account account = null;
            boolean isCorrectType = false;
            switch (intType) {
                case 1:
                    isCorrectType = true;
                    account = new SavingAccount(balance);
                    break;
                case 2:
                    isCorrectType = true;
                    System.out.println("Please enter the overdraft:");
                    double overdraft = scanner.nextDouble();
                    account = new CheckingAccount(balance, overdraft);
                    break;
                default:
                    System.out.println("Unknown type.");
            }
            if (isCorrectType) {
                clientService.addAccount(BankCommander.currentClient, account);
                if (BankCommander.currentClient.getAccounts().size() == 1) {
                    BankCommander.currentClient.setActiveAccount(account);
                } else {
                    System.out.println("Do you want to make this account active? (y/n)");
                    if (scanner.next().charAt(0) == 'y') {
                        BankCommander.currentClient.setActiveAccount(account);
                    }
                }
                System.out.println("Operation successfully completed");
            }
        }
    }

    @Override
    public String getCommandInfo() {
        return "Add an account";
    }
}
