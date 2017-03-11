package ua.spalah.bank.commands;

import ua.spalah.bank.exceptions.NotEnoughFundsException;
import ua.spalah.bank.ioCommander.AbstractCommand;
import ua.spalah.bank.ioCommander.IO;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.services.AccountService;
import ua.spalah.bank.services.ClientService;

import java.util.InputMismatchException;

/**
 * Created by Kostya on 12.01.2017.
 */
//переводит деньги с активно счета текущего клиента на активный счет клиента, имя которого укажет пользователь
public class TransferCommand extends AbstractCommand implements Command {
    private final ClientService clientService;
    private final AccountService accountService;
    public TransferCommand(ClientService clientService, AccountService accountService, IO io) {
        super(io);
        this.clientService = clientService;
        this.accountService = accountService;
    }

    @Override
    public void execute() {
        if (BankCommander.currentClient == null) { // может создать метод getCurrentClient()
            write("You didn't choose a client!"); // и кинуть там исключение, а здесь его словить?
        } else {
            write("Enter client's name who will receive money:");
            String name = read();
            try {
                Account activeAccount = accountService.findActiveAccountByClientName(name);
                write("Please enter amount: ");
                double amount = Double.parseDouble(read());
                accountService.transfer(BankCommander.currentClient.getActiveAccount(), activeAccount, amount);
                write("Operation successfully completed");
            } catch (InputMismatchException e) {
                write("This is not a number!");
            } catch (NotEnoughFundsException e) {
                write(e.getMessage());
            }
        }
    }

    @Override
    public String getCommandInfo() {
        return "Transfer from current client's active account to other client's active account";
    }
}
