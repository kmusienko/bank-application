package ua.spalah.bank.commands;

import ua.spalah.bank.services.ClientService;

/**
 * Created by Kostya on 12.01.2017.
 */
public class GetAccountsCommand implements Command { // выводит список счетов текущего клиента помечая активный счет
    private final ClientService clientService;

    public GetAccountsCommand(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void execute() {
        if (BankCommander.currentClient == null) { // может создать метод getCurrentClient()
            System.out.println("You didn't choose a client!"); // и кинуть там исключение, а здесь его словить?
        } else {
            System.out.println(BankCommander.currentClient);
        }
    }

    @Override
    public String getCommandInfo() {
        return "Get accounts";
    }
}
