package ua.spalah.bank.commands;

import ua.spalah.bank.exceptions.ClientNotFoundException;
import ua.spalah.bank.ioCommander.IO;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.services.ClientService;

import java.util.Scanner;

/**
 * Created by Kostya on 12.01.2017.
 */
// удаляет клиента по имени
public class RemoveClientCommand implements Command {
    private final ClientService clientService;
    private final IO ioConsole;
    public RemoveClientCommand(ClientService clientService, IO ioConsole) {
        this.clientService = clientService;
        this.ioConsole = ioConsole;
    }

    @Override
    public void execute() {
        ioConsole.write("Please enter client's name:");
        String name = ioConsole.read();
        try {
            Client client = clientService.findClientByName(BankCommander.currentBank, name);
            clientService.deleteClient(BankCommander.currentBank, client);
            ioConsole.write("Client " + name + " successfully removed");
        } catch (ClientNotFoundException e) {
            ioConsole.write(e.getMessage());
        }
    }

    @Override
    public String getCommandInfo() {
        return "Remove a client";
    }
}
