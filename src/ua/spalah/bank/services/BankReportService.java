package ua.spalah.bank.services;

import ua.spalah.bank.models.Bank;
import ua.spalah.bank.models.Client;

import java.util.List;

/**
 * Created by Kostya on 05.01.2017.
 */
public interface BankReportService {
    int getNumberOfClients(Bank bank); // общее количество клиентов
    int getNumberOfAccounts(Bank bank); // общее количество счетов
    double getTotalAccountSum(Bank bank); // общая сумма по всем счетам
    double getBankCreditSum(Bank bank); // возвращает сумму отрицательных балансов по всем счетам
    List<Client> getClientsSortedByName(Bank bank); // Возвращает список клиентов отсортированных по имени

}
