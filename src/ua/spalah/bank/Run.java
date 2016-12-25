package ua.spalah.bank;

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

        SavingAccount sv = new SavingAccount(1000);
        CheckingAccount ck = new CheckingAccount(450, 200);
        kostya.addAccount(sv);
        kostya.addAccount(ck);
        kostya.setActiveAccount(ck);
        bank.addClient(kostya);
        Scanner sc = new Scanner(System.in);
        System.out.println("Type client's name: ");
        String name = sc.nextLine();
        System.out.println(bank.getClientInfo(name));
        System.out.println(kostya.equals(vasya2));
        System.out.println(kostya.hashCode());
        System.out.println(vasya2.hashCode());
    }
}
