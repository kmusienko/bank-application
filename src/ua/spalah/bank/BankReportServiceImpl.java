package ua.spalah.bank;

import ua.spalah.bank.accounts.Account;


import java.util.*;

/**
 * Created by Kostya on 05.01.2017.
 */
public class BankReportServiceImpl implements BankReportService {
    @Override
    public int getNumberOfClients(Bank bank) {
        return bank.getAllClients().size();
    }

    @Override
    public int getNumberOfAccounts(Bank bank) {
        int numberOfAccounts = 0;
        for (Client client : bank.getAllClients()) {
            numberOfAccounts += client.getAccounts().size();
        }
        return numberOfAccounts;
    }

    @Override
    public double getTotalAccountSum(Bank bank) {
        double totalAccountSum = 0;
        for (Client client : bank.getAllClients()) {
            totalAccountSum += client.getTotalBalance();
        }
        return totalAccountSum;
    }

    @Override
    public double getBankCreditSum(Bank bank) {
        double bankCreditSum = 0;
        for (Client client : bank.getAllClients()) {
            for (Account account : client.getAccounts()) {
                if (account.getBalance() < 0) {
                    bankCreditSum += Math.abs(account.getBalance());
                }
            }
        }
        return bankCreditSum;
    }

    @Override
    public List<Client> getClientsSortedByName(Bank bank) {
        List<Client> clients = bank.getAllClients();
        Collections.sort(clients);
        return clients;
    }
}
