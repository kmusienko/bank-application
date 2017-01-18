package ua.spalah.bank.commands;

import ua.spalah.bank.exceptions.ClientNotFoundException;
import ua.spalah.bank.exceptions.NotEnoughFundsException;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.services.AccountService;
import ua.spalah.bank.services.ClientService;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by Kostya on 12.01.2017.
 */
//переводит деньги с активно счета текущего клиента на активный счет клиента, имя которого укажет пользователь
public class TransferCommand implements Command {
    private final ClientService clientService;
    private final AccountService accountService;
    public TransferCommand(ClientService clientService, AccountService accountService) {
        this.clientService = clientService;
        this.accountService = accountService;
    }

    @Override
    public void execute() {
        if (BankCommander.currentClient == null) { // может создать метод getCurrentClient()
            System.out.println("You didn't choose a client!"); // и кинуть там исключение, а здесь его словить?
        } else {
            System.out.println("Enter client's name who will receive money:");
            Scanner scanner = new Scanner(System.in);
            String name = scanner.nextLine();
            try {
                Client receiver = clientService.findClientByName(BankCommander.currentBank, name);
                System.out.println("Please enter amount: ");
                double amount = scanner.nextDouble();
                accountService.transfer(BankCommander.currentClient.getActiveAccount(), receiver.getActiveAccount(), amount);
                System.out.println("Operation successfully completed");
            } catch (InputMismatchException e) {
                System.out.println("This is not a number!");
            } catch (NotEnoughFundsException | ClientNotFoundException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public String getCommandInfo() {
        return "Transfer from current client's active account to other client's active account";
    }
}
