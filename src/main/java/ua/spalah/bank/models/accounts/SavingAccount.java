package ua.spalah.bank.models.accounts;

import ua.spalah.bank.models.type.AccountType;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

/**
 * Created by Kostya on 12.03.2017.
 */
@Entity
@DiscriminatorValue("S")
public class SavingAccount extends Account {
    public SavingAccount() {
        super(AccountType.SAVING);
    }
    public SavingAccount(long id, double balance, AccountType accountType) {
        super(id, balance, accountType);
    }
    public SavingAccount(double balance) {
        super(balance, AccountType.SAVING);
    }
    public SavingAccount(long id, double balance) {
        super(id, balance, AccountType.SAVING);
    }
}
