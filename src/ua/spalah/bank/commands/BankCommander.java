package ua.spalah.bank.commands;

import ua.spalah.bank.models.Bank;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.accounts.CheckingAccount;
import ua.spalah.bank.models.accounts.SavingAccount;
import ua.spalah.bank.models.type.Gender;
import ua.spalah.bank.services.AccountService;
import ua.spalah.bank.services.ClientService;
import ua.spalah.bank.services.impl.AccountServiceImpl;
import ua.spalah.bank.services.impl.ClientServiceImpl;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by Kostya on 12.01.2017.
 */
public class BankCommander {
    // хранит в себе банк с кототорым мы работаем
    public static Bank currentBank;

    // хранит в себе клиента с которым мы работаем в данный момент
    public static Client currentClient;

    // Список команд которые мы можем выполнять
    private Command[] commands;

    public BankCommander() {
        init();
    }

    private void init() {
        try {

            ClientService clientService = new ClientServiceImpl();
            AccountService accountService = new AccountServiceImpl();

            Bank bank = new Bank();

            Client dima = new Client("Dima", Gender.MALE);
            Client misha = new Client("Misha", Gender.MALE);
            Client masha = new Client("Masha", Gender.FEMALE);
            Client kostya = new Client("Kostya", Gender.MALE);

            CheckingAccount dck = new CheckingAccount(1000, 800);
            SavingAccount dsa = new SavingAccount(3000);
            CheckingAccount mick = new CheckingAccount(17000, 7000);
            SavingAccount misa = new SavingAccount(5000);
            CheckingAccount mack = new CheckingAccount(4000, 5000);
            SavingAccount masa = new SavingAccount(100000);
            CheckingAccount kck = new CheckingAccount(2000, 1000);
            SavingAccount ksa = new SavingAccount(20000);

            clientService.saveClient(bank, dima);
            clientService.saveClient(bank, misha);
            clientService.saveClient(bank, masha);
            clientService.saveClient(bank, kostya);

            clientService.addAccount(dima, dck);
            clientService.addAccount(dima, dsa);
            clientService.addAccount(misha, mick);
            clientService.addAccount(misha, misa);
            clientService.addAccount(masha, masa);
            clientService.addAccount(masha, mack);
            clientService.addAccount(kostya, ksa);
            clientService.addAccount(kostya, kck);

            currentBank = bank;

            this.commands = new Command[]{new FindClientCommand(clientService)};

        } catch (Exception e) {
//            throw new RuntimeException("Initialization error", e);
            RuntimeException ex = new RuntimeException("Initialization error");
            ex.initCause(e);
            throw ex;
        }
    }

    public void run() {
        while (true) {

            System.out.print("\n");

            for (int i = 0; i < commands.length; i++) {
                System.out.println(i + 1 + ") " + commands[i].getCommandInfo());
            }

            Scanner in = new Scanner(System.in);

            try {
                System.out.print("\nEnter command number: ");
                int command = in.nextInt();
                commands[command - 1].execute();

            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Wrong command number!");

            } catch (InputMismatchException e) {
                System.out.println("This is not a number!");
            }
        }
    }

    // запуск нашего приложения

    public static void main(String[] args) {
        BankCommander bankCommander = new BankCommander();
        bankCommander.run();
    }
}
