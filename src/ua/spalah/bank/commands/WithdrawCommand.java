package ua.spalah.bank.commands;

import ua.spalah.bank.exceptions.NotEnoughFundsException;
import ua.spalah.bank.ioCommander.AbstractCommand;
import ua.spalah.bank.ioCommander.IO;
import ua.spalah.bank.services.AccountService;

import java.util.InputMismatchException;

/**
 * Created by Kostya on 12.01.2017.
 */
public class WithdrawCommand extends AbstractCommand implements Command { // снимает деьги с активного счета текущего клиента
    private final AccountService accountService;
    
    public WithdrawCommand(AccountService accountService, IO io) {
        super(io);
        this.accountService = accountService;
    }

    @Override
    public void execute() {
        if (BankCommander.currentClient == null) { // может создать метод getCurrentClient()
            write("You didn't choose a client!"); // и кинуть там исключение, а здесь его словить?
        } else {
            write("Please enter deposit amount: ");
            try {
                double amount = Double.parseDouble(read());
                accountService.withdraw(BankCommander.currentClient.getActiveAccount(), amount);
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
        return "Withdraw from client's active account";
    }
}
