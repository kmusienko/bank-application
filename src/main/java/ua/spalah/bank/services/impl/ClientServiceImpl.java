package ua.spalah.bank.services.impl;

import ua.spalah.bank.exceptions.ClientAlreadyExistsException;
import ua.spalah.bank.exceptions.ClientNotFoundException;
import ua.spalah.bank.exceptions.ClientNotHaveAccountException;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.dao.AccountDao;
import ua.spalah.bank.dao.ClientDao;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.services.ClientService;

import java.util.List;

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
    public Client findClientById(long id){
        Client client = clientDao.find(id);
        client.getAccounts().addAll(accountDao.findByClientId(client.getId()));
        client.setActiveAccount(accountDao.findActiveAccountByClientName(client.getName()));
        return client;
    }

    @Override
    public List<Client> findAllClients() {
        return clientDao.findAll();
    }

    @Override
    public Client saveClient(Client client) throws ClientAlreadyExistsException {
        if (!clientDao.findAll().contains(client)) {
            return clientDao.saveOrUpdate(client);
        } else {
            throw new ClientAlreadyExistsException(client.getName());
        }
    }

    @Override
    public void deleteClient(Client client) throws ClientNotFoundException {
        if (clientDao.findAll().contains(client)) {
            clientDao.delete(client.getId());
        } else {
            throw new ClientNotFoundException(client.getName());
        }
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
    @Deprecated
    public void getAccountsInfo(Client client) {
        List<Account> accounts = accountDao.findByClientId(client.getId());
        for (int i = 0; i < accounts.size(); i++) {
            String isActive = client.getActiveAccount().getId() == accounts.get(i).getId() ? ", *active account*" : "";
            System.out.println("[" + (i + 1) + "] " + accounts.get(i).toString() + isActive);
        }
    }
    @Override
    public List<Account> getAccounts(Client client) {
        List<Account> accounts = accountDao.findByClientId(client.getId());
        return accounts;
    }

    @Override
    public void selectActiveAccount(Client client, Account account) throws ClientNotHaveAccountException {
        /*
        * Имеет ли смысл добавлять валидацию, если эту команду вызывает
        * только SelectActiveAccountCommand(), в которой пользователю предоставляется
        * выбрать лишь те аккаунты, которые есть у текущего клиента?
        * Также эта команда вызывается при создании первого аккаунта у текущего клиента.*/
        if (client.getAccounts().contains(account)) {
            client.setActiveAccount(account);
            clientDao.update(client);
        } else {
            throw new ClientNotHaveAccountException(client.getName());
        }

    }
    @Override
    public Client updateClient(Client client) {
        client = clientDao.update(client);
        client.setActiveAccount(accountDao.findActiveAccountByClientName(client.getName()));
        return client;
    }
}
