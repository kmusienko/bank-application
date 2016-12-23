package ua.spalah.bank;

import java.util.ArrayList;

/**
 * Created by Kostya on 23.12.2016.
 */
public class Client {
    String name;
    Gender gender;
    ArrayList<Account> accounts = new ArrayList<>();
    Account activeAccount;

    public Client(String name, Gender gender) {
        this.name = name;
        this.gender = gender;

    }

    public void addAccount(Account account) {
        if (accounts.size() == 0) {
            activeAccount = account;
        }
        accounts.add(account);
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setActiveAccount(Account account) {
        if (accounts.contains(account)) {
            activeAccount = account;
        } else {
            System.out.println("Account hasn't been created.");
        }
    }

    public double getTotalBalance() {
        double totalBalance = 0;
        for (Account account : accounts) {
            totalBalance += account.getBalance();
        }
        return totalBalance;
    }

    public String toString() {
        return "Name: " + name + ".\nGender: " + gender + ".\nAccounts: " + accounts + "\nActive account: " + activeAccount;
    }



}
