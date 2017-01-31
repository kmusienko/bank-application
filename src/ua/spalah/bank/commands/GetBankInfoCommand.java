package ua.spalah.bank.commands;

import ua.spalah.bank.ioCommander.IO;
import ua.spalah.bank.services.BankReportService;

/**
 * Created by Kostya on 12.01.2017.
 */
// печатает полную информацию о банке
public class GetBankInfoCommand implements Command {
    private final BankReportService bankReportService;
    private final IO ioConsole;
    public GetBankInfoCommand(BankReportService bankReportService, IO ioConsole) {
        this.bankReportService = bankReportService;
        this.ioConsole = ioConsole;
    }

    @Override
    public void execute() {
        ioConsole.write("Number of clients: " + bankReportService.getNumberOfClients(BankCommander.currentBank) + "\n" +
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
