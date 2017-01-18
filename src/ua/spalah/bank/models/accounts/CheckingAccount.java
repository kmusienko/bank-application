package ua.spalah.bank.models.accounts;

import ua.spalah.bank.models.type.AccountType;

/**
 * Created by Kostya on 23.12.2016.
 */
public class CheckingAccount extends SavingAccount {

    private double overdraft; //возможная задолженность.

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