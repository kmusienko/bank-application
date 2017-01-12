package ua.spalah.bank.commands;

import ua.spalah.bank.exceptions.ClientAlreadyExistsException;
import ua.spalah.bank.exceptions.ClientNotFoundException;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.type.Gender;
import ua.spalah.bank.services.ClientService;

import java.util.Scanner;

/**
 * Created by Kostya on 12.01.2017.
 */
// удаляет клиента по имени
public class RemoveClientCommand implements Command {
    private final ClientService clientService;
    public RemoveClientCommand(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void execute() {
        System.out.println("Please enter client's name:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        try {
            Client client = clientService.findClientByName(BankCommander.currentBank, name);
            clientService.deleteClient(BankCommander.currentBank, client);
            System.out.println("Client " + name + " successfully removed");
        } catch (ClientNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String getCommandInfo() {
        return "Remove a client";
    }
}
