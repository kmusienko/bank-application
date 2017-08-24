package ua.spalah.bank.servlets;

import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.services.AccountService;
import ua.spalah.bank.services.ClientService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Kostya on 11.03.2017.
 */
public class EditAccountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = req.getSession().getServletContext();
        ClientService clientService = (ClientService) context.getAttribute("clientService");
        AccountService accountService = (AccountService) context.getAttribute("accountService");
        String accountId = req.getParameter("id");
        String clientId = req.getParameter("clientId");

        Client client = clientService.findClientById(Long.parseLong(clientId));
        Account account = accountService.findAccountById(Long.parseLong(accountId));

        req.setAttribute("client", client);
        req.setAttribute("account", account);
        req.getRequestDispatcher("/WEB-INF/jsp/account.jsp").forward(req, resp);
    }
}
