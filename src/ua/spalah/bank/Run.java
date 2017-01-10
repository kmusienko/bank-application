package ua.spalah.bank;

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
        Account savingAccount = new SavingAccount(100, AccountType.SAVING);

        ClientServiceImpl clientService = new ClientServiceImpl();
        clientService.addAccount(kostya, savingAccount);
        clientService.saveClient(bank, kostya);
        clientService.saveClient(bank, vasya);
        BankReportServiceImpl bankReportService = new BankReportServiceImpl();
        System.out.println(bankReportService.getTotalAccountSum(bank));

//        System.out.println("Clients, sorted by name:");
//        System.out.println(new BankReportServiceImpl().getClientsSortedByName(bank));

    }
}
