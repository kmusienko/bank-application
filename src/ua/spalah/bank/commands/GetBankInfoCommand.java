package ua.spalah.bank.commands;

import ua.spalah.bank.ioCommander.AbstractCommand;
import ua.spalah.bank.ioCommander.IO;
import ua.spalah.bank.services.BankReportService;

/**
 * Created by Kostya on 12.01.2017.
 */
// печатает полную информацию о банке
public class GetBankInfoCommand extends AbstractCommand implements Command {
    private final BankReportService bankReportService;
    
    public GetBankInfoCommand(BankReportService bankReportService, IO io) {
        super(io);
        this.bankReportService = bankReportService;
    }

    @Override
    public void execute() {
        write("Number of clients: " + bankReportService.getNumberOfClients(BankCommander.currentBank) + "\n" +
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
