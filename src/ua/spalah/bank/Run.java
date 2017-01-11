package ua.spalah.bank;

import ua.spalah.bank.exceptions.ClientAlreadyExistsException;
import ua.spalah.bank.exceptions.NotEnoughFundsException;
import ua.spalah.bank.exceptions.OverdraftLimitExceededException;
import ua.spalah.bank.models.accounts.*;
import ua.spalah.bank.models.Bank;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.type.AccountType;
import ua.spalah.bank.models.type.Gender;
import ua.spalah.bank.services.impl.AccountServiceImpl;
import ua.spalah.bank.services.impl.BankReportServiceImpl;
import ua.spalah.bank.services.impl.ClientServiceImpl;

/**
 * Created by Kostya on 23.12.2016.
 */

public class Run {
    public static void main(String[] args) {
        Bank bank = new Bank();
        Client kostya = new Client("Kostya", Gender.MALE);
        Client vasya = new Client("Vasya", Gender.FEMALE);
        Client vasya2 = new Client("Kostya", Gender.MALE);
        AccountServiceImpl accountService = new AccountServiceImpl();
        Account savingAccount = new SavingAccount(100);
        try {
            accountService.withdraw(savingAccount,50);
        } catch (NotEnoughFundsException e) {
            e.printStackTrace();
        }
        ClientServiceImpl clientService = new ClientServiceImpl();

        try {
            clientService.addAccount(kostya, savingAccount);
            clientService.saveClient(bank, kostya);
            clientService.saveClient(bank, vasya);
        } catch (Exception e) {
            RuntimeException ex = new RuntimeException("Initialization error");
            ex.initCause(e);
            throw ex;
        }

        BankReportServiceImpl bankReportService = new BankReportServiceImpl();
        System.out.println(bankReportService.getTotalAccountSum(bank));

//        System.out.println("Clients, sorted by name:");
//        System.out.println(new BankReportServiceImpl().getClientsSortedByName(bank));

    }
}
