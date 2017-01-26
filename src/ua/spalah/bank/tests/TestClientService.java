package ua.spalah.bank.tests;

import org.junit.*;
import ua.spalah.bank.exceptions.ClientAlreadyExistsException;
import ua.spalah.bank.exceptions.ClientNotFoundException;
import ua.spalah.bank.models.Bank;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.models.accounts.CheckingAccount;
import ua.spalah.bank.models.accounts.SavingAccount;
import ua.spalah.bank.models.type.Gender;
import ua.spalah.bank.services.ClientService;
import ua.spalah.bank.services.impl.ClientServiceImpl;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by Kostya on 26.01.2017.
 */
public class TestClientService {
    private Bank bank;
    private ClientService clientService;

    @Before
    public void init() {
        bank = new Bank();
        clientService = new ClientServiceImpl();
        bank.getAllClients().put("Kostya", new Client("Kostya", Gender.MALE, "pro@gmail.com", "+380636908681", "Dnipro"));
        bank.getAllClients().put("Misha", new Client("Misha", Gender.MALE, "mixa@ukr.net", "+380674567890", "Odessa"));
        bank.getAllClients().put("Gera",  new Client("Gera", Gender.FEMALE, "gerahello@gmail.com", "+380964561234", "Dnipro"));
//        CheckingAccount c1 = new CheckingAccount(1000, 800);
//        SavingAccount s1 = new SavingAccount(3000);
//        CheckingAccount c2 = new CheckingAccount(17000, 7000);
//        SavingAccount s2 = new SavingAccount(5000);
//        CheckingAccount c3 = new CheckingAccount(2000, 1000);
//        SavingAccount s3 = new SavingAccount(20000);
//        bank.getAllClients().get("Kostya").getAccounts().add(c1);
//        bank.getAllClients().get("Kostya").getAccounts().add(s1);
//        bank.getAllClients().get("Misha").getAccounts().add(c2);
//        bank.getAllClients().get("Misha").getAccounts().add(s2);
//        bank.getAllClients().get("Gera").getAccounts().add(c3);
//        bank.getAllClients().get("Gera").getAccounts().add(s3);
    }
    @After
    public void clear() {
        bank.getAllClients().clear();
    }

    @Test
    public void testFindClientByName() throws ClientNotFoundException {
        Client kostya = new Client("Kostya", Gender.MALE, "pro@gmail.com", "+380636908681", "Dnipro");
        assertEquals(kostya, clientService.findClientByName(bank, "Kostya"));
    }
    @Test (expected = ClientNotFoundException.class)
    public void testFindClientByNameNotFound() throws ClientNotFoundException {
        clientService.findClientByName(bank, "Tina");
    }
    @Test
    public void testFindAllClients() {
        Map<String, Client> clients = new HashMap<>();
        clients.put("Kostya", new Client("Kostya", Gender.MALE, "pro@gmail.com", "+380636908681", "Dnipro"));
        clients.put("Misha", new Client("Misha", Gender.MALE, "mixa@ukr.net", "+380674567890", "Odessa"));
        clients.put("Gera",  new Client("Gera", Gender.FEMALE, "gerahello@gmail.com", "+380964561234", "Dnipro"));
        assertEquals(clients, clientService.findAllClients(bank));
    }
    @Test
    public void testSaveClient() throws ClientAlreadyExistsException {
        Bank testBank = new Bank();
        testBank.getAllClients().put("Kostya", new Client("Kostya", Gender.MALE, "pro@gmail.com", "+380636908681", "Dnipro"));
        testBank.getAllClients().put("Misha", new Client("Misha", Gender.MALE, "mixa@ukr.net", "+380674567890", "Odessa"));
        testBank.getAllClients().put("Gera",  new Client("Gera", Gender.FEMALE, "gerahello@gmail.com", "+380964561234", "Dnipro"));
        Client testClient = new Client("TestClient", Gender.MALE, "tests@gmail.com", "+380968965212", "TestCity");
        testBank.getAllClients().put("TestClient", testClient);
        clientService.saveClient(bank, testClient);
        assertEquals(testBank.getAllClients(), bank.getAllClients());
    }
    @Test (expected = ClientAlreadyExistsException.class)
    public void testSaveClientAlreadyExists() throws ClientAlreadyExistsException {
        clientService.saveClient(bank, new Client("Kostya", Gender.MALE, "pro@gmail.com", "+380636908681", "Dnipro"));
    }
    @Test
    public void testDeleteClient() throws ClientNotFoundException {
        Bank testBank = new Bank();
        testBank.getAllClients().put("Kostya", new Client("Kostya", Gender.MALE, "pro@gmail.com", "+380636908681", "Dnipro"));
        testBank.getAllClients().put("Misha", new Client("Misha", Gender.MALE, "mixa@ukr.net", "+380674567890", "Odessa"));
        testBank.getAllClients().put("Gera",  new Client("Gera", Gender.FEMALE, "gerahello@gmail.com", "+380964561234", "Dnipro"));
        testBank.getAllClients().remove("Gera");
        clientService.deleteClient(bank, new Client("Gera", Gender.FEMALE, "gerahello@gmail.com", "+380964561234", "Dnipro"));
        assertEquals(testBank.getAllClients(), bank.getAllClients());
    }
    @Test (expected = ClientNotFoundException.class)
    public void testDeleteClientNotFound() throws ClientNotFoundException {
        clientService.deleteClient(bank, new Client("Kiril", Gender.MALE, "kirill@mail.com", "+380567893412", "KirilCity"));
    }
    @Test
    public void testAddAccount() {
        Client vasya = new Client("Vasya", Gender.MALE, "vasya@mail.ru", "+380675674567", "Lviv");
        Account sAcc = new SavingAccount(500);
        vasya.getAccounts().add(sAcc);
        Client petya = new Client("Petya", Gender.MALE, "petya@mail.ru", "+380675674567", "Lviv");
        clientService.addAccount(petya, sAcc);
        assertEquals(vasya.getAccounts(), petya.getAccounts());
    }
    @Test
    public void testGetTotalBalance() {
        Client vasya = new Client("Vasya", Gender.MALE, "vasya@mail.ru", "+380675674567", "Lviv");
        Account sAcc = new SavingAccount(500);
        Account chAcc = new CheckingAccount(600, 200);
        vasya.getAccounts().add(sAcc);
        vasya.getAccounts().add(chAcc);
        assertEquals(1100, clientService.getTotalBalance(vasya), 0);
    }
    @Test
    public void testSelectActiveAccount() {
        Client vasya = new Client("Vasya", Gender.MALE, "vasya@mail.ru", "+380675674567", "Lviv");
        Account sAcc = new SavingAccount(500);
        Account chAcc = new CheckingAccount(600, 200);
        vasya.getAccounts().add(sAcc);
        vasya.getAccounts().add(chAcc);
        clientService.selectActiveAccount(vasya, chAcc);
        assertEquals(chAcc, vasya.getActiveAccount());
    }

}