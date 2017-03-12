package ua.spalah.bank.services.impl;

import ua.spalah.bank.models.Client;
import ua.spalah.bank.dao.AccountDao;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.services.BankReportService;
import ua.spalah.bank.dao.ClientDao;
import ua.spalah.bank.services.ClientService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Kostya on 05.01.2017.
 */
public class BankReportServiceImpl implements BankReportService {

    private ClientDao clientDao;
    private AccountDao accountDao;
    private ClientService  clientService;

    public BankReportServiceImpl(ClientService clientService, ClientDao clientDao, AccountDao accountDao) {
        this.clientDao = clientDao;
        this.accountDao = accountDao;
        this.clientService = clientService;
    }

    @Override
    public int getNumberOfClients() {

        return clientDao.findAll().size();
    }

    @Override
    public int getNumberOfAccounts() {

        return accountDao.findAll().size();
    }

    @Override
    public double getTotalAccountSum() {
        double totalAccountSum = 0;
        for (Client client : clientDao.findAll()) {
            totalAccountSum += clientService.getTotalBalance(client);
        }
        return totalAccountSum;
    }

    @Override
    public double getBankCreditSum() {
        double bankCreditSum = 0;
        for (Client client : clientDao.findAll()) {
            for (Account account : client.getAccounts()) {
                if (account.getBalance() < 0) {
                    bankCreditSum += Math.abs(account.getBalance());
                }
            }
        }
        return bankCreditSum;
    }

    @Override
    public List<Client> getClientsSortedByName() {
        List<Client> clients = new ArrayList<>(clientDao.findAll());
        Collections.sort(clients, new Comparator<Client>() {
            @Override
            public int compare(Client o1, Client o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        return clients;
    }

    @Override
    public Map<String, List<Client>> getClientsByCity() {
        Map<String, List<Client>> clients = new HashMap<>();
        for (Client client : clientDao.findAll()) {
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
