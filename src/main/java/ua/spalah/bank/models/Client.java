package ua.spalah.bank.models;

import org.hibernate.annotations.*;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.models.type.Gender;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Kostya on 23.12.2016.
 */
@Entity
@Table( name = "CLIENTS" )
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ACTIVE_ACCOUNT_ID", foreignKey = @ForeignKey(name = "FK_ACTIVE_ACC"))
    private Account activeAccount;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "CLIENT_ID", foreignKey = @ForeignKey(name = "FK_CLIENT_ACC"))
    private List<Account> accounts = new ArrayList<>();
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "TEL")
    private String tel;
    @Column(name = "CITY")
    private String city;

    public Client() {

    }

    public Client(String name, Gender gender, String email, String tel, String city) {
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.tel = tel;
        this.city = city;
    }
    public Client(long id, String name, Gender gender, String email, String tel, String city) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.tel = tel;
        this.city = city;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

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
    public void setName(String name) {
        this.name = name;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
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
