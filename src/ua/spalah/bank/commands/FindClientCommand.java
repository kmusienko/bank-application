package ua.spalah.bank.commands;

import ua.spalah.bank.exceptions.ClientNotFoundException;
import ua.spalah.bank.services.ClientService;

import java.util.Scanner;

/**
 * Created by Kostya on 12.01.2017.
 */
public class FindClientCommand implements Command { // находит клиента по имени и делает его текущим клиентом
    private final ClientService clientService;

    public FindClientCommand(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void execute() {
        System.out.println("Please enter client name");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();

        try {
            BankCommander.currentClient = clientService.findClientByName(BankCommander.currentBank, name);
            System.out.println("Client " + BankCommander.currentClient.getName() + " successfully was found");
        } catch (ClientNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String getCommandInfo() {
        return "Find a client";
    }
}
