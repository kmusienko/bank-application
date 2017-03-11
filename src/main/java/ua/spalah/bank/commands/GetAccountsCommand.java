package ua.spalah.bank.commands;

import ua.spalah.bank.ioCommander.AbstractCommand;
import ua.spalah.bank.ioCommander.IO;
import ua.spalah.bank.services.ClientService;

/**
 * Created by Kostya on 12.01.2017.
 */
public class GetAccountsCommand extends AbstractCommand implements Command { // выводит список счетов текущего клиента помечая активный счет
    private final ClientService clientService;
    
    public GetAccountsCommand(ClientService clientService, IO io) {
        super(io);
        this.clientService = clientService;
    }

    @Override
    public void execute() {
        if (BankCommander.currentClient == null) {
            write("You didn't choose a client!");
        } else {
            clientService.getAccountsInfo(BankCommander.currentClient);
        }
    }

    @Override
    public String getCommandInfo() {
        return "Get accounts";
    }
}
