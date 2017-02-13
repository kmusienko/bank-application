package ua.spalah.bank.commands;

import ua.spalah.bank.exceptions.ClientNotFoundException;
import ua.spalah.bank.ioCommander.AbstractCommand;
import ua.spalah.bank.ioCommander.IO;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.services.ClientService;

import java.util.Scanner;

/**
 * Created by Kostya on 12.01.2017.
 */
// удаляет клиента по имени
public class RemoveClientCommand extends AbstractCommand implements Command {
    private final ClientService clientService;
    
    public RemoveClientCommand(ClientService clientService, IO io) {
        super(io);
        this.clientService = clientService;
    }

    @Override
    public void execute() {
        write("Please enter client's name:");
        String name = read();
        try {
            Client client = clientService.findClientByName(name);
            clientService.deleteClient(client);
            write("Client " + name + " successfully removed");
        } catch (ClientNotFoundException e) {
            write(e.getMessage());
        }
    }

    @Override
    public String getCommandInfo() {
        return "Remove a client";
    }
}
