package ua.spalah.bank.commands;

import ua.spalah.bank.exceptions.ClientNotHaveAccountException;
import ua.spalah.bank.ioCommander.AbstractCommand;
import ua.spalah.bank.ioCommander.IO;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.models.accounts.CheckingAccount;
import ua.spalah.bank.models.accounts.SavingAccount;
import ua.spalah.bank.services.AccountService;
import ua.spalah.bank.services.ClientService;

/**
 * Created by Kostya on 16.01.2017.
 */
// добавляет счет текущему клиенту, спрашивая всю информацию о счете у пользователя (если у клиента нет счетов,
// делаем этот активным, если уже есть, то прелагаем сделать его активным опционально)
public class AddAccountCommand extends AbstractCommand implements Command {
    private final ClientService clientService;
    private final AccountService accountService;

    public AddAccountCommand(ClientService clientService, AccountService accountService, IO io) {
        super(io);
        this.clientService = clientService;
        this.accountService = accountService;
      
    }

    @Override
    public void execute() {
        if (BankCommander.currentClient == null) {
            write("You didn't choose a client!");
        } else {
            write("What type of account you want to create? 1 - Saving, 2 - Checking");
            int intType = Integer.parseInt(read());
            write("Please enter the balance: ");
            double balance = Double.parseDouble(read());
            Account account = null;
            boolean isCorrectType = false;
            switch (intType) {
                case 1:
                    isCorrectType = true;
                    account = new SavingAccount(balance);
                    break;
                case 2:
                    isCorrectType = true;
                    write("Please enter the overdraft:");
                    double overdraft = Double.parseDouble(read());
                    account = new CheckingAccount(balance, overdraft);
                    break;
                default:
                    write("Unknown type.");
            }
            if (isCorrectType) {
               account = accountService.addAccount(BankCommander.currentClient, account);
                if (BankCommander.currentClient.getAccounts().size() > 1) {
                    write("Do you want to make this account active? (y/n)");
                    if (read().charAt(0) == 'y') {
                        try {
                            clientService.selectActiveAccount(BankCommander.currentClient, account);
                        } catch (ClientNotHaveAccountException e) {
                            e.getMessage();
                        }
                    }
                }
                write("Operation successfully completed");
            }
        }
    }

    @Override
    public String getCommandInfo() {
        return "Add an account";
    }
}
