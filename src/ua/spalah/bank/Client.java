package ua.spalah.bank;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by Kostya on 23.12.2016.
 */
public class Client {
    private String name;
    private Gender gender;
    private ArrayList<Account> accounts = new ArrayList<>();
    private Account activeAccount;

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

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;
        if (otherObject == null) return false;
        if (getClass() != otherObject.getClass()) return false;
        Client other = (Client) otherObject;
        return Objects.equals(name, other.name) && Objects.equals(gender, other.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, gender);
    }


}
