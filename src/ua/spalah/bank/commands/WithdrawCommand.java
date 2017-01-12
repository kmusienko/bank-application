package ua.spalah.bank.commands;

import ua.spalah.bank.exceptions.NotEnoughFundsException;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.services.AccountService;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by Kostya on 12.01.2017.
 */
public class WithdrawCommand implements Command { // снимает деьги с активного счета текущего клиента
    private final AccountService accountService;
    public WithdrawCommand(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void execute() {
        if (BankCommander.currentClient == null) { // может создать метод getCurrentClient()
            System.out.println("You didn't choose a client!"); // и кинуть там исключение, а здесь его словить?
        } else {
            System.out.println("Please enter deposit amount: ");
            Scanner scanner = new Scanner(System.in);
            try {
                double amount = scanner.nextDouble();
                accountService.withdraw(BankCommander.currentClient.getActiveAccount(), amount);
                System.out.println("Operation successfully completed");
            } catch (InputMismatchException e) {
                System.out.println("This is not a number!");
            } catch (NotEnoughFundsException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public String getCommandInfo() {
        return "Withdraw from client's active account";
    }
}
