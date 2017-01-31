package ua.spalah.bank.commands;

import ua.spalah.bank.exceptions.ClientNotFoundException;
import ua.spalah.bank.ioCommander.IO;
import ua.spalah.bank.services.ClientService;

import java.util.Scanner;

/**
 * Created by Kostya on 12.01.2017.
 */
public class FindClientCommand implements Command { // находит клиента по имени и делает его текущим клиентом
    private final ClientService clientService;
    private final IO ioConsole;

    public FindClientCommand(ClientService clientService, IO ioConsole) {
        this.clientService = clientService;
        this.ioConsole = ioConsole;
    }

    @Override
    public void execute() {
        ioConsole.write("Please enter client name");
        String name = ioConsole.read();

        try {
            BankCommander.currentClient = clientService.findClientByName(BankCommander.currentBank, name);
            ioConsole.write("Client " + BankCommander.currentClient.getName() + " successfully was found");
        } catch (ClientNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String getCommandInfo() {
        return "Find a client";
    }
}
