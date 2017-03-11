package ua.spalah.bank.commands;

import ua.spalah.bank.ioCommander.ConsoleIO;
import ua.spalah.bank.ioCommander.IO;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.dao.AccountDao;
import ua.spalah.bank.services.AccountService;
import ua.spalah.bank.services.BankReportService;
import ua.spalah.bank.dao.ClientDao;
import ua.spalah.bank.services.ClientService;
import ua.spalah.bank.dao.impl.AccountDaoImpl;
import ua.spalah.bank.services.impl.AccountServiceImpl;
import ua.spalah.bank.services.impl.BankReportServiceImpl;
import ua.spalah.bank.dao.impl.ClientDaoImpl;
import ua.spalah.bank.services.impl.ClientServiceImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by Kostya on 12.01.2017.
 */
public class BankCommander {
    // хранит в себе клиента с которым мы работаем в данный момент
    public static Client currentClient;

    public static Connection connection;

    // Список команд которые мы можем выполнять
    private Command[] commands;

    public BankCommander() {
        init();
    }

    private void init() {
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/D:\\Programming\\SpalahJavaTasks\\BankApplication/dbbank", "sa", "");

            AccountDao accountDao = new AccountDaoImpl(connection);
            ClientDao clientDao = new ClientDaoImpl(connection, accountDao);
            ClientService clientService = new ClientServiceImpl(clientDao, accountDao);
            AccountService accountService = new AccountServiceImpl(accountDao, clientDao);
            BankReportService bankReportService = new BankReportServiceImpl(clientService, clientDao, accountDao);
            IO ioConsole = new ConsoleIO();

            /* Инициализация данных из файлов */
//            String pathRoot = System.getProperty("user.dir");
//            Path pathAccounts = Paths.get(pathRoot, "resources", "accounts.txt");
//            Path pathClients = Paths.get(pathRoot, "resources", "clients.txt");
//
//            List<String> clientLines = Files.readAllLines(pathClients);
//            for (String clientString : clientLines) {
//                String[] clientTokens = clientString.split("::");
//                String name = clientTokens[0];
//                Gender gender = Gender.valueOf(clientTokens[1]);
////                switch (clientTokens[1]) {
////                    case "MALE": gender = Gender.MALE; break;
////                    case "FEMALE" : gender = Gender.FEMALE; break;
////                    default: throw new IllegalArgumentException("Initialization error");
////                }
//                String email = clientTokens[2];
//                String telephone = clientTokens[3];
//                String city = clientTokens[4];
//                clientService.saveClient(bank, new Client(name, gender, email, telephone, city));
//            }
//            // можно переделать чуть проще:
//            List<String> accountsLines = Files.readAllLines(pathAccounts);
//            for (String accountString : accountsLines) {
//                String[] accountTokens = accountString.split("::");
//                Client client = clientService.findClientByName(bank, accountTokens[0]);
//                Account account = null;
//                double balance = Double.parseDouble(accountTokens[2]);
//                double overdraft = 0;
//                if (accountTokens.length == 4) {
//                    overdraft = Double.parseDouble(accountTokens[3]);
//                }
//                switch (accountTokens[1]) {
//                    case "SAVING" : account = new SavingAccount(balance); break;
//                    case "CHECKING" : account = new CheckingAccount(balance, overdraft); break;
//                    default: throw new IllegalArgumentException("Initialization error");
//                }
//                accountService.addAccount(client, account);
//            }
            /**/

            this.commands = new Command[]{
                    new FindClientCommand(clientService, ioConsole),
                    new GetAccountsCommand(clientService, ioConsole),
                    new SelectActiveAccountCommand(clientService, ioConsole),
                    new DepositCommand(accountService, ioConsole),
                    new WithdrawCommand(accountService, ioConsole),
                    new TransferCommand(clientService, accountService, ioConsole),
                    new AddClientCommand(clientService, ioConsole),
                    new AddAccountCommand(clientService, accountService, ioConsole),
                    new RemoveClientCommand(clientService, ioConsole),
                    new GetBankInfoCommand(bankReportService, ioConsole),
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
