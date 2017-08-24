package ua.spalah.bank.hibernateDao.impl;

import ua.spalah.bank.dao.AccountDao;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.accounts.Account;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.sql.ResultSet;
import java.util.List;
import java.util.Objects;

/**
 * Created by Kostya on 12.03.2017.
 */
public class HibernateAccountDaoImpl implements AccountDao {
    private EntityManagerFactory entityManagerFactory;
    public HibernateAccountDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Account save(long clientId, Account account) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(account);

        entityManager.createQuery("UPDATE Account set CLIENT_ID =:client_id where ID =:account_id").setParameter("client_id", clientId).setParameter("account_id", account.getId()).executeUpdate();
        entityManager.getTransaction().commit();
        entityManager.close();
        return account;
    }

    @Override
    public Account update(Account account) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(account);
        entityManager.getTransaction().commit();
        entityManager.close();
        return account;
    }

    @Override
    public Account saveOrUpdate(long clientId, Account account) {
        if (account.getId() == 0) {
            return save(clientId, account);
        } else {
            return update(account);
        }
    }

    @Override
    public void delete(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Account account = entityManager.find(Account.class, id);
        entityManager.remove(account);
        entityManager.getTransaction().commit();
        entityManager.close();

    }

    @Override
    public void deleteByClientId(long clientId) {

    }

    @Override
    public Account find(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Account account = entityManager.find(Account.class, id);
        entityManager.getTransaction().commit();
        entityManager.close();
        return account;
    }

    @Override
    public List<Account> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Account> accounts = entityManager.createQuery("from Account", Account.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return accounts;
    }

    @Override
    public List<Account> findByClientId(long clientId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Account> accounts = entityManager.createNativeQuery("SELECT * FROM PUBLIC.ACCOUNTS WHERE CLIENT_ID =" + clientId, Account.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return accounts;
    }

    @Override
    public Account findActiveAccountByClientName(String clientName) {
        Account account = null;
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("select cl from Client cl where cl.name=:name");
        query.setParameter("name", clientName);
        Client client = (Client) query.getSingleResult();
        Query query1 = entityManager.createNativeQuery("SELECT active_account_id from PUBLIC .CLIENTS WHERE ID=" + client.getId());
        Object result = query1.getSingleResult();
        if (result != null) {
            long accountId = Long.parseLong(result.toString());
            account = entityManager.find(Account.class, accountId);
        }
        entityManager.getTransaction().commit();
        entityManager.close();
        return account;
    }

    @Override
    public Account getDesiredAccount(ResultSet resultSet) {

        return null;
    }
}
