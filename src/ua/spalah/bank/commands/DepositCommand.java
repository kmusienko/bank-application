package ua.spalah.bank.commands;

import ua.spalah.bank.models.Client;
import ua.spalah.bank.services.AccountService;
import ua.spalah.bank.services.ClientService;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by Kostya on 12.01.2017.
 */
public class DepositCommand implements Command { // кладет введенную пользователем сумму денег на активный счет текущего клиента
    private final AccountService accountService;
    public DepositCommand(AccountService accountService) {
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
                accountService.deposit(BankCommander.currentClient.getActiveAccount(), amount);
                System.out.println("Operation successfully completed");
            } catch (InputMismatchException e) {
                System.out.println("This is not a number!");
            }
        }
    }

    @Override
    public String getCommandInfo() {
        return "Deposit on client's active account";
    }
}
