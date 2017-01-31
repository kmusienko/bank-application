package ua.spalah.bank.commands;

import ua.spalah.bank.ioCommander.IO;
import ua.spalah.bank.services.ClientService;

/**
 * Created by Kostya on 12.01.2017.
 */
public class GetAccountsCommand implements Command { // выводит список счетов текущего клиента помечая активный счет
    private final ClientService clientService;
    private final IO ioConsole;

    public GetAccountsCommand(ClientService clientService, IO ioConsole) {
        this.clientService = clientService;
        this.ioConsole = ioConsole;
    }

    @Override
    public void execute() {
        if (BankCommander.currentClient == null) {
            ioConsole.write("You didn't choose a client!");
        } else {
            clientService.getAccountsInfo(BankCommander.currentClient);
        }
    }

    @Override
    public String getCommandInfo() {
        return "Get accounts";
    }
}
