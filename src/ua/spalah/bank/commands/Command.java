package ua.spalah.bank.commands;

/**
 * Created by Kostya on 12.01.2017.
 */
public interface Command {
    void execute();
    String getCommandInfo();
}
