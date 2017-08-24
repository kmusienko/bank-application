package ua.spalah.bank.commands;

import java.sql.SQLException;

/**
 * Created by Kostya on 12.01.2017.
 */
public class ExitCommand implements Command {
    @Override
    public void execute() {
        try {
            BankCommander.connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    @Override
    public String getCommandInfo() {
        return "Exit a program";
    }
}
