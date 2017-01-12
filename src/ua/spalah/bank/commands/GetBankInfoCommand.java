package ua.spalah.bank.commands;

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
        int numberOfClients = bankReportService.getNumberOfClients(BankCommander.currentBank);
        int numberOfAccounts = bankReportService.getNumberOfAccounts(BankCommander.currentBank);
        double totalAccountSum = bankReportService.getTotalAccountSum(BankCommander.currentBank);
        double bankCreditSum = bankReportService.getBankCreditSum(BankCommander.currentBank);
        List<Client> clientsSortedByName = bankReportService.getClientsSortedByName(BankCommander.currentBank);
        System.out.println("Number of clients: " + numberOfClients + "\n" +
                            "Number of accounts: " + numberOfAccounts + "\n" +
                            "Total account sum: " + totalAccountSum + "\n" +
                            "Bank credit sum: " + bankCreditSum + "\n" +
                            "All clients sorted by name: \n" + clientsSortedByName);
    }

    @Override
    public String getCommandInfo() {
        return "Get bank info";
    }
}
