package ua.spalah.bank.services.impl;

import ua.spalah.bank.commands.BankCommander;
import ua.spalah.bank.models.Bank;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.services.BankReportService;

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
        for (Client client : bank.getAllClients().values()) {
            numberOfAccounts += client.getAccounts().size();
        }
        return numberOfAccounts;
    }

    @Override
    public double getTotalAccountSum(Bank bank) {
        double totalAccountSum = 0;
        for (Client client : bank.getAllClients().values()) {
            ClientServiceImpl  clientService = new ClientServiceImpl();
            totalAccountSum += clientService.getTotalBalance(client);
        }
        return totalAccountSum;
    }

    @Override
    public double getBankCreditSum(Bank bank) {
        double bankCreditSum = 0;
        for (Client client : bank.getAllClients().values()) {
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
        List<Client> clients = new ArrayList<>(bank.getAllClients().values());
        Collections.sort(clients, new Comparator<Client>() {
            @Override
            public int compare(Client o1, Client o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        return clients;
    }

    @Override
    public Map<String, List<Client>> getClientsByCity(Bank bank) {
        Map<String, List<Client>> clients = new HashMap<>();
        for (Client client : BankCommander.currentBank.getAllClients().values()) {
            if (!clients.containsKey(client.getCity())) {
                List<Client> clientList = new ArrayList<>();
                clientList.add(client);
                clients.put(client.getCity(),clientList);
            } else {
                List<Client> clientList = clients.get(client.getCity());
                clientList.add(client);
            }
        }
        System.out.println(clients.size());
        return clients;
    }
}
