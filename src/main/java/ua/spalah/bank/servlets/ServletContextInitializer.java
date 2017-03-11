package ua.spalah.bank.servlets;

import ua.spalah.bank.dao.AccountDao;
import ua.spalah.bank.dao.ClientDao;
import ua.spalah.bank.dao.impl.AccountDaoImpl;
import ua.spalah.bank.dao.impl.ClientDaoImpl;
import ua.spalah.bank.services.AccountService;
import ua.spalah.bank.services.BankReportService;
import ua.spalah.bank.services.ClientService;
import ua.spalah.bank.services.impl.AccountServiceImpl;
import ua.spalah.bank.services.impl.BankReportServiceImpl;
import ua.spalah.bank.services.impl.ClientServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created by Kostya on 08.03.2017.
 */
public class ServletContextInitializer implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/D:\\Programming\\SpalahJavaTasks\\BankApplication/dbbank", "sa", "");
            AccountDao accountDao = new AccountDaoImpl(connection);
            ClientDao clientDao = new ClientDaoImpl(connection, accountDao);
            ClientService clientService = new ClientServiceImpl(clientDao, accountDao);
            AccountService accountService = new AccountServiceImpl(accountDao, clientDao);
            BankReportService bankReportService = new BankReportServiceImpl(clientService, clientDao, accountDao);
            ServletContext context = servletContextEvent.getServletContext();
            context.setAttribute("connection", connection);
            context.setAttribute("clientService", clientService);
            context.setAttribute("accountService",accountService);
            context.setAttribute("bankReportService",bankReportService);

        } catch (Exception e) {
            RuntimeException ex = new RuntimeException("Initialization error");
            ex.initCause(e);
            throw ex;
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
