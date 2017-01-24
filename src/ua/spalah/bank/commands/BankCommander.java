package ua.spalah.bank.commands;

import ua.spalah.bank.models.Bank;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.accounts.CheckingAccount;
import ua.spalah.bank.models.accounts.SavingAccount;
import ua.spalah.bank.models.type.Gender;
import ua.spalah.bank.services.AccountService;
import ua.spalah.bank.services.BankReportService;
import ua.spalah.bank.services.ClientService;
import ua.spalah.bank.services.impl.AccountServiceImpl;
import ua.spalah.bank.services.impl.BankReportServiceImpl;
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
            BankReportService bankReportService = new BankReportServiceImpl();

            Bank bank = new Bank();
            Client kostya = new Client("Kostya", Gender.MALE, "pro@gmail.com", "+380636908681", "Dnipro");
            Client misha = new Client("Misha", Gender.MALE, "mixa@ukr.net", "+380674567890", "Odessa");
            Client gera = new Client("Gera", Gender.FEMALE, "gerahello@gmail.com", "+380964561234", "Dnipro");

            CheckingAccount c1 = new CheckingAccount(1000, 800);
            SavingAccount s1 = new SavingAccount(3000);
            CheckingAccount c2 = new CheckingAccount(17000, 7000);
            SavingAccount s2 = new SavingAccount(5000);
            CheckingAccount c3 = new CheckingAccount(2000, 1000);
            SavingAccount s3 = new SavingAccount(20000);

            clientService.saveClient(bank, kostya);
            clientService.saveClient(bank, misha);
            clientService.saveClient(bank, gera);

            clientService.addAccount(gera, c1);
            clientService.addAccount(gera, s1);
            clientService.addAccount(misha, c2);
            clientService.addAccount(misha, s2);
            clientService.addAccount(kostya, s3);
            clientService.addAccount(kostya, c3);

            currentBank = bank;

            this.commands = new Command[]{
                    new FindClientCommand(clientService),
                    new GetAccountsCommand(clientService),
                    new SelectActiveAccountCommand(clientService),
                    new DepositCommand(accountService),
                    new WithdrawCommand(accountService),
                    new TransferCommand(clientService, accountService),
                    new AddClientCommand(clientService),
                    new AddAccountCommand(clientService),
                    new RemoveClientCommand(clientService),
                    new GetBankInfoCommand(bankReportService),
                    new ExitCommand()
                    };

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
