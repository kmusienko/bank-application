package ua.spalah.bank.models.accounts;

import ua.spalah.bank.models.type.AccountType;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by Kostya on 23.12.2016.
 */
@Entity
@DiscriminatorValue("CH")
public class CheckingAccount extends Account {
    @Column(name = "OVERDRAFT")
    private double overdraft; //возможная задолженность.
    public CheckingAccount() {
        super(AccountType.CHECKING);
    }
    public CheckingAccount(long id, double balance, double overdraft) {
        super(id, balance, AccountType.CHECKING);
        if (overdraft < 0) {
            System.out.println("Overdraft < 0!");
        } else {
            this.overdraft = overdraft;
        }
    }
    public CheckingAccount(double balance, double overdraft) {
        super(balance, AccountType.CHECKING);
        if (overdraft < 0) {
            System.out.println("Overdraft < 0!");
        } else {
            this.overdraft = overdraft;
        }
    }


    @Override
    public String toString() {
        return "CheckingAccount <-> balance: " + getBalance() + ", overdraft: " + overdraft;
    }

    public double getOverdraft() {
        return overdraft;
    }

    public void setOverdraft(double overdraft) {
        this.overdraft = overdraft;
    }

}