package ua.spalah.bank.models.accounts;

import org.hibernate.annotations.GenericGenerator;
import ua.spalah.bank.models.type.AccountType;

import javax.persistence.*;

/**
 * Created by Kostya on 23.12.2016.
 */
@Entity
@Table(name = "ACCOUNTS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //все наследники будут в одной таблице
@DiscriminatorColumn(name = "ACC_TYPE") //какая колонка будет отличать один класс от другого (чисто для хибера)
public abstract class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "BALANCE")
    private double balance;
    @Column(name = "TYPE")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    protected Account(AccountType accountType) {
        this.accountType = accountType;
    }
    protected Account(long id, double balance, AccountType accountType) {
        this.id = id;
        this.balance = balance;
        this.accountType = accountType;
    }
    protected Account(double balance, AccountType accountType) {
        this.balance = balance;
        this.accountType = accountType;
    }

    public Account(long id, double balance) {
        this(id, balance, AccountType.SAVING);
    }
    public Account(double balance) {
        this(balance, AccountType.SAVING);
    }


    public String toString() {
        return "Account <-> balance: " + getBalance();
    }


    public AccountType getAccountType() {
        return accountType;
    }
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }


    public double getBalance() {
        return balance;
    }


    public void setBalance(double balance) {
        this.balance = balance;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
