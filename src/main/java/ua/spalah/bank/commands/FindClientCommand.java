package ua.spalah.bank.commands;

import ua.spalah.bank.exceptions.ClientNotFoundException;
import ua.spalah.bank.ioCommander.AbstractCommand;
import ua.spalah.bank.ioCommander.IO;
import ua.spalah.bank.services.ClientService;

/**
 * Created by Kostya on 12.01.2017.
 */
public class FindClientCommand extends AbstractCommand implements Command { // находит клиента по имени и делает его текущим клиентом
    private final ClientService clientService;
    
    public FindClientCommand(ClientService clientService, IO io) {
        super(io);
        this.clientService = clientService;
    }

    @Override
    public void execute() {
        write("Please enter client name");
        String name = read();

        try {
            BankCommander.currentClient = clientService.findClientByName(name);
            write("Client " + BankCommander.currentClient.getName() + " successfully was found");
        } catch (ClientNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String getCommandInfo() {
        return "Find a client";
    }
}
