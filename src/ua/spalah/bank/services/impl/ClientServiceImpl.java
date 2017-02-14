package ua.spalah.bank.services.impl;

import ua.spalah.bank.exceptions.ClientAlreadyExistsException;
import ua.spalah.bank.exceptions.ClientNotFoundException;
import ua.spalah.bank.models.Bank;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.services.AccountDao;
import ua.spalah.bank.services.ClientDao;
import ua.spalah.bank.services.ClientService;

import java.util.List;
import java.util.Map;

/**
 * Created by Kostya on 05.01.2017.
 */
public class ClientServiceImpl implements ClientService {

    private ClientDao clientDao;
    private AccountDao accountDao;

    public ClientServiceImpl(ClientDao clientDao, AccountDao accountDao) {
        this.clientDao = clientDao;
        this.accountDao = accountDao;
    }

    @Override
    public Client findClientByName(String name) throws ClientNotFoundException {
        Client client = clientDao.findByName(name);
        if (client == null) {
            throw new ClientNotFoundException(name);
        } else {
            return client;
        }
    }

    @Override
    public List<Client> findAllClients() {
        return clientDao.findAll();
    }

    @Override
    public Client saveClient(Client client) throws ClientAlreadyExistsException {
//        if (!bank.getAllClients().containsKey(client.getName())) {
//            bank.getAllClients().put(client.getName(), client);
//        } else {
//            throw new ClientAlreadyExistsException(client.getName());
//        }

        return clientDao.saveOrUpdate(client);
    }

    @Override
    public void deleteClient(Client client) throws ClientNotFoundException {
//        if (bank.getAllClients().containsKey(client.getName())) {
//            bank.getAllClients().remove(client.getName());
//        } else {
//            throw new ClientNotFoundException(client.getName());
//        }

//        client.setActiveAccount(null);
//        clientDao.update(client);
//        List<Account> accounts = accountDao.findByClientId(client.getId());
//        for (Account account : accounts) {
//            accountDao.delete(account.getId());
//        }

        clientDao.delete(client.getId());
    }

    @Override
    public Account addAccount(Client client, Account account) {
       account = accountDao.save(client.getId(), account);
        client.getAccounts().add(account);
        if (client.getAccounts().size() == 1) {
            client.setActiveAccount(account);
            client.setActiveAccountId(account.getId());
            clientDao.update(client);
        }
        return account;
    }

    @Override
    public double getTotalBalance(Client client) {
        double totalBalance = 0;
        for (Account account : client.getAccounts()) {
            totalBalance += account.getBalance();
        }
        return totalBalance;
    }

    @Override
    public void getAccountsInfo(Client client) {
     //   List<Account> accounts = client.getAccounts();
        List<Account> accounts = accountDao.findByClientId(client.getId());
        for (int i = 0; i < accounts.size(); i++) {
            String isActive = client.getActiveAccount().getId() == accounts.get(i).getId() ? ", *active account*" : "";
            System.out.println("[" + (i + 1) + "] " + accounts.get(i).toString() + isActive);
        }
    }

    @Override
    public void selectActiveAccount(Client client, Account account) {
        client.setActiveAccount(account);
        client.setActiveAccountId(account.getId());
        clientDao.update(client);
    }
}
