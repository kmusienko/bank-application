package ua.spalah.bank.models;

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
    private String email;
    private String tel;
    private String city;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Client(String name, Gender gender, String email, String tel, String city) {
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.tel = tel;
        this.city = city;
    }

    public void setActiveAccount(Account account) {
        activeAccount = account;
    }
    public Account getActiveAccount() {
        return activeAccount;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }

    @Override
    public String toString() {
        return "\nClient{" +
                "name=" + name +
                ",\n gender=" + gender +
                ",\n activeAccount=" + activeAccount +
                ",\n accounts=" + accounts +
                ",\n email = " + email +
                ",\n tel = " + tel +
                ",\n city = " + city +
                '}';
    }

    @Override
    public boolean equals(Object otherObject) {
        if (this == otherObject) return true;
        if (otherObject == null) return false;
        if (getClass() != otherObject.getClass()) return false;
        Client other = (Client) otherObject;
        return Objects.equals(name, other.name) && Objects.equals(gender, other.gender)
                && Objects.equals(email, other.email) && Objects.equals(tel, other.tel) && Objects.equals(city, other.city);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, gender, email, tel, city);
    }

    public List<Account> getAccounts() {
        return accounts;
    }

}
