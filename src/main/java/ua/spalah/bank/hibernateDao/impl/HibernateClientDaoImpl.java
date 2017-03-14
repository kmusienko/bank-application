package ua.spalah.bank.hibernateDao.impl;

import ua.spalah.bank.dao.ClientDao;
import ua.spalah.bank.models.Client;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Kostya on 12.03.2017.
 */
public class HibernateClientDaoImpl implements ClientDao{
    private EntityManagerFactory entityManagerFactory;
    public HibernateClientDaoImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }
    @Override
    public Client save(Client client) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(client);
        entityManager.getTransaction().commit();

        return client;
    }

    @Override
    public Client update(Client client) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(client);
        entityManager.getTransaction().commit();
        entityManager.close();
        return client;
    }

    @Override
    public Client saveOrUpdate(Client client) {
        if (client.getId() == 0) {
            return save(client);
        } else {
            return update(client);
        }
    }

    @Override
    public void delete(long clientId) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Client client = entityManager.find(Client.class, clientId);
        entityManager.remove(client);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public Client find(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Client client = entityManager.find(Client.class, id);
        entityManager.getTransaction().commit();
        entityManager.close();
        return client;
    }

    @Override
    public List<Client> findAll() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        List<Client> clients = entityManager.createQuery("from Client", Client.class).getResultList();
        entityManager.getTransaction().commit();
        entityManager.close();
        return clients;
    }

    @Override
    public Client findByName(String name) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        Client client = entityManager.createQuery("select cl from Client cl where cl.name=" + name, Client.class).getSingleResult();

        return client;
    }

    @Override
    public Client createClient(ResultSet resultSet) {
        return null;
    }
}
