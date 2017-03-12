package ua.spalah.bank;

import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.models.accounts.CheckingAccount;
import ua.spalah.bank.models.accounts.SavingAccount;
import ua.spalah.bank.models.type.Gender;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by Kostya on 12.03.2017.
 */
public class Main {
    public static void main(String[] args) {
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("bank-application");
        EntityManager entityManager = factory.createEntityManager();
        entityManager.getTransaction().begin();

        Account acc1 = new CheckingAccount(5000, 2300);
        Account acc2 = new SavingAccount(300);
        Account acc3 = new CheckingAccount(560, 100);

//        entityManager.persist(acc1);
//        entityManager.persist(acc2);
//        entityManager.persist(acc3);

        Client client = new Client("Kostya", Gender.MALE, "kostyan@gmail.com","+380636908681", "Dnipro" );

        client.setActiveAccount(acc1);
        client.getAccounts().add(acc1);
        client.getAccounts().add(acc2);
        client.getAccounts().add(acc3);


        entityManager.persist(client);

        entityManager.getTransaction().commit();
        entityManager.close();
        factory.close();
    }
}
