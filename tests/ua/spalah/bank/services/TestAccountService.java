package ua.spalah.bank.services;

import org.junit.Before;
import org.junit.Test;
import ua.spalah.bank.exceptions.NotEnoughFundsException;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.models.accounts.CheckingAccount;
import ua.spalah.bank.models.accounts.SavingAccount;
import ua.spalah.bank.dao.impl.AccountDaoImpl;
import ua.spalah.bank.services.impl.AccountServiceImpl;

import static org.junit.Assert.assertEquals;

/**
 * Created by Kostya on 26.01.2017.
 */
public class TestAccountService {
   private AccountService accountService;
   private Account saving;
   private Account checking;

    @Before
    public void init() {
        accountService = new AccountServiceImpl(new AccountDaoImpl());
        saving = new SavingAccount(500);
        checking = new CheckingAccount(500, 200);
    }
    @Test
    public void testDeposit() {
        accountService.deposit(saving, 110);
        assertEquals(610, saving.getBalance(),0);
    }
    @Test (expected = IllegalArgumentException.class)
    public void testDepositNegativeAmount() {
        accountService.deposit(saving, -20);
    }
    @Test
    public void testWithdrawSaving() throws NotEnoughFundsException {
        accountService.withdraw(saving, 450);
        assertEquals(50, saving.getBalance(),0);
    }
    @Test
    public void testWithdrawChecking() throws NotEnoughFundsException {
        accountService.withdraw(checking, 600);
        assertEquals(-100, checking.getBalance(), 0);
    }
    @Test (expected = NotEnoughFundsException.class)
    public void testWithdrawSavingNotFunds() throws NotEnoughFundsException {
        accountService.withdraw(saving, 501);
    }
    @Test (expected = NotEnoughFundsException.class)
    public void testWithdrawCheckingNotFunds() throws NotEnoughFundsException {
        accountService.withdraw(checking, 701);
    }
    @Test
    public void testTransfer() throws NotEnoughFundsException {
        double amount = 400;
        double expSavingBalance = saving.getBalance() - amount;
        double expCheckingBalance = checking.getBalance() + amount;
        accountService.transfer(saving, checking, amount);
        assertEquals(expSavingBalance, saving.getBalance(), 0);
        assertEquals(expCheckingBalance, checking.getBalance(), 0);
    }

}
