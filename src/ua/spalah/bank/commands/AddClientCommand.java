package ua.spalah.bank.commands;

import ua.spalah.bank.exceptions.ClientAlreadyExistsException;
import ua.spalah.bank.exceptions.ClientNotFoundException;
import ua.spalah.bank.exceptions.NotEnoughFundsException;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.type.Gender;
import ua.spalah.bank.services.ClientService;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        Gender gender = null;
        String email = "";
        String tel = "";
        try {
            if (stringGender.equalsIgnoreCase("Male")) gender = Gender.MALE;
            else if (stringGender.equalsIgnoreCase("Female")) gender = Gender.FEMALE;
            else System.out.println("Incorrect gender!");

            do {
                System.out.println("Please, enter client's email: ");
                email = scanner.nextLine();
                if (!isValidEmail(email)) {
                    System.out.println("Incorrect email! Try again.");
                }
            } while (!isValidEmail(email));
            do {
                System.out.println("Please, enter client's phone number:");
                tel = scanner.nextLine();
                if (!isValidTel(tel)) {
                    System.out.println("Incorrect number! Try again.");
                }
            } while (!isValidTel(tel));
            System.out.println("Please, enter client's city:");
            String city = scanner.nextLine();
            clientService.saveClient(BankCommander.currentBank, new Client(name, gender, email, tel, city));
            System.out.println("Client " + name + " successfully added");
        } catch (ClientAlreadyExistsException e) {
            System.out.println(e.getMessage());
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
