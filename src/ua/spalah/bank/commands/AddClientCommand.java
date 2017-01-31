package ua.spalah.bank.commands;

import ua.spalah.bank.exceptions.ClientAlreadyExistsException;
import ua.spalah.bank.ioCommander.IO;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.type.Gender;
import ua.spalah.bank.services.ClientService;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Kostya on 12.01.2017.
 */
// добавляет нового клиента, получая полную информацию о нем у пользователя
public class AddClientCommand implements Command {
    private final ClientService clientService;
    private final IO ioConsole;
    public AddClientCommand(ClientService clientService, IO ioConsole) {
        this.clientService = clientService;
        this.ioConsole = ioConsole;
    }

    @Override
    public void execute() {
        ioConsole.write("Please enter client's name:");
        String name = ioConsole.read();
        ioConsole.write("Please enter client's gender(Male/Female):");
        String stringGender = ioConsole.read();
        Gender gender = null;
        String email = "";
        String tel = "";
        try {
            if (stringGender.equalsIgnoreCase("Male")) gender = Gender.MALE;
            else if (stringGender.equalsIgnoreCase("Female")) gender = Gender.FEMALE;
            else ioConsole.write("Incorrect gender!");

            ioConsole.write("Please, enter client's email: ");
            email = ioConsole.read();
            while (!isValidEmail(email)) {
                ioConsole.write("Incorrect email! Try again.");
                ioConsole.write("Enter client's email: ");
                email = ioConsole.read();
            }

            ioConsole.write("Please, enter client's phone number: ");
            tel = ioConsole.read();
            while (!isValidTel(tel)) {
                ioConsole.write("Incorrect number! Try again.");
                ioConsole.write("Enter client's phone number: ");
                tel = ioConsole.read();
            }

            ioConsole.write("Please, enter client's city:");
            String city = ioConsole.read();
            clientService.saveClient(BankCommander.currentBank, new Client(name, gender, email, tel, city));
            ioConsole.write("Client " + name + " successfully added");
        } catch (ClientAlreadyExistsException e) {
            ioConsole.write(e.getMessage());
        }
    }

    @Override
    public String getCommandInfo() {
        return "Add a new client";
    }

    private static boolean isValidEmail(String email) {
        String emailRegex = "^(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x07\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x07\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])$";
        Pattern emailPattern = Pattern.compile(emailRegex);
        Matcher emailMatcher = emailPattern.matcher(email);
        return emailMatcher.matches();
    }
    private static boolean isValidTel(String phone) {
        String phoneRegex = "^[+][0-9]{12}$";
        Pattern phonePattern = Pattern.compile(phoneRegex);
        Matcher phoneMatcher = phonePattern.matcher(phone);
        return phoneMatcher.matches();
    }
}
