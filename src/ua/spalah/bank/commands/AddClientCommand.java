package ua.spalah.bank.commands;

import ua.spalah.bank.exceptions.ClientAlreadyExistsException;
import ua.spalah.bank.exceptions.ClientNotFoundException;
import ua.spalah.bank.exceptions.NotEnoughFundsException;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.type.Gender;
import ua.spalah.bank.services.ClientService;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by Kostya on 12.01.2017.
 */
// добавляет нового клиента, получая полную информацию о нем у пользователя
public class AddClientCommand implements Command {
    private final ClientService clientService;
    public AddClientCommand(ClientService clientService) {
        this.clientService = clientService;
    }

    @Override
    public void execute() {
        System.out.println("Please enter client's name:");
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        System.out.println("Please enter client's gender(Male/Female):");
        String stringGender = scanner.nextLine();
        Gender gender;
        try {
            if (stringGender.equalsIgnoreCase("Male")) gender = Gender.MALE;
            else if (stringGender.equalsIgnoreCase("Female")) gender = Gender.FEMALE;
            else throw new IllegalArgumentException();
            clientService.saveClient(BankCommander.currentBank, new Client(name, gender));
            System.out.println("Client " + name + " successfully added");
        } catch (IllegalArgumentException e) {
            System.out.println("Incorrect gender!");
        } catch (ClientAlreadyExistsException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String getCommandInfo() {
        return "Add a new client";
    }
}
