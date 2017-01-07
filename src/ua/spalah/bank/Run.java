package ua.spalah.bank;

import ua.spalah.bank.accounts.*;
import ua.spalah.bank.listeners.*;
import ua.spalah.bank.services.impl.BankReportServiceImpl;

import java.util.Scanner;

/**
 * Created by Kostya on 23.12.2016.
 */

public class Run {
    public static void main(String[] args) {
        Bank bank = new Bank();
        Client kostya = new Client("Kostya", Gender.MALE);
        Client vasya = new Client("Vasya", Gender.FEMALE);
        Client vasya2 = new Client("Kostya", Gender.MALE);
       // System.out.println(LocalDate.now() + " " + LocalTime.now());
        SavingAccount sv = new SavingAccount(1000);
        CheckingAccount ck = new CheckingAccount(450, 200);
        kostya.addAccount(sv);
        kostya.addAccount(ck);
        kostya.setActiveAccount(ck);
        bank.addClient(kostya);
        //bank.addClient(vasya);
        Scanner sc = new Scanner(System.in);
        System.out.println("Type client's name: ");
        String name = sc.nextLine();
        System.out.println(bank.getClientInfo(name));
        System.out.println(kostya.equals(vasya2));
        System.out.println(kostya.hashCode());
        System.out.println(vasya2.hashCode());
        ck.withdraw(800);
        System.out.println(ck.getBalance());

        System.out.println(Gender.MALE.getSalutation());
        Bank bank1 = new Bank();
        Client kostya1 = new Client("Kostya", Gender.MALE);
        Client nastya = new Client("Nastya", Gender.FEMALE);
        Client abram = new Client("Abram", Gender.MALE);
        Client lena = new Client("Lena", Gender.FEMALE);
        SavingAccount sv1 = new SavingAccount(1500);
        CheckingAccount ch = new CheckingAccount(800, 300);
        kostya.addAccount(sv1);
        nastya.addAccount(ch);
        EmailNotificationListener emailListener = new EmailNotificationListener();
        RegistrationLoggerListener registrationListener = new RegistrationLoggerListener();
        bank1.addListener(registrationListener);
        bank1.addListener(emailListener);
        bank1.addClient(kostya1);
        bank1.addClient(nastya);
        bank1.addClient(abram);
        bank1.addClient(lena);
        System.out.println("Clients, sorted by name:");
        System.out.println(new BankReportServiceImpl().getClientsSortedByName(bank1));

    }
}
