package ua.spalah.bank.commands;

import ua.spalah.bank.models.Bank;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.services.BankReportService;

import java.util.List;

/**
 * Created by Kostya on 12.01.2017.
 */
// печатает полную информацию о банке
public class GetBankInfoCommand implements Command {
    private final BankReportService bankReportService;
    public GetBankInfoCommand(BankReportService bankReportService) {
        this.bankReportService = bankReportService;
    }

    @Override
    public void execute() {
        System.out.println("Number of clients: " + bankReportService.getNumberOfClients(BankCommander.currentBank) + "\n" +
                            "Number of accounts: " + bankReportService.getNumberOfAccounts(BankCommander.currentBank) + "\n" +
                            "Total account sum: " + bankReportService.getTotalAccountSum(BankCommander.currentBank) + "\n" +
                            "Bank credit sum: " + bankReportService.getBankCreditSum(BankCommander.currentBank) + "\n" +
                            "All clients sorted by name: \n" + bankReportService.getClientsSortedByName(BankCommander.currentBank) + "\n" +
                            "All clients sorted by city: \n" + bankReportService.getClientsByCity(BankCommander.currentBank));
    }

    @Override
    public String getCommandInfo() {
        return "Get bank info";
    }
}
