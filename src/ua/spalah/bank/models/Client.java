package ua.spalah.bank.models;

import ua.spalah.bank.exceptions.ClientNotHaveAccountException;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.models.type.Gender;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Kostya on 23.12.2016.
 */
public class Client {
    private String name;
    private Gender gender;
    private Account activeAccount;
    private ArrayList<Account> accounts = new ArrayList<>();

    public Client(String name, Gender gender) {
        this.name = name;
        this.gender = gender;

    }

    public void setActiveAccount(Account account) throws ClientNotHaveAccountException {
        if (accounts.contains(account)) {
            activeAccount = account;
        } else {
           throw new ClientNotHaveAccountException("Client " + getName() + " doesn't have an account " + account);
        }
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name=" + name +
                ",\n gender=" + gender +
                ",\n activeAccount=" + activeAccount +
                ",\n accounts=" + accounts +
                '}';
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

    public List<Account> getAccounts() {
        return accounts;
    }

}
