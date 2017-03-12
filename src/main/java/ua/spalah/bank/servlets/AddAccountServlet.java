package ua.spalah.bank.servlets;

import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.accounts.CheckingAccount;
import ua.spalah.bank.models.accounts.Account;
import ua.spalah.bank.models.accounts.SavingAccount;
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
public class AddAccountServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = req.getSession().getServletContext();
        ClientService clientService = (ClientService) context.getAttribute("clientService");
        String clientIdParam = req.getParameter("clientId");

        Client client = null;
        if (clientIdParam != null) {
            client = clientService.findClientById(Long.parseLong(clientIdParam));
        } else {
            client = new Client();
        }
        req.setAttribute("clientName", client.getName());
        req.setAttribute("clientId", clientIdParam);
        req.getRequestDispatcher("/WEB-INF/jsp/add-account.jsp").forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = req.getSession().getServletContext();
        ClientService clientService = (ClientService) context.getAttribute("clientService");
        AccountService accountService = (AccountService) context.getAttribute("accountService");

        Account account = createAccount(req);
        String clientIdParam = req.getParameter("clientId");
        Client client = clientService.findClientById(Long.parseLong(clientIdParam));
        accountService.addAccount(client, account);

        resp.sendRedirect("/client?id=" + client.getId());

    }
    private Account createAccount(HttpServletRequest req) {
        String typeParam = req.getParameter("accountType");
        String balanceParam = req.getParameter("balance");
        String overdraftParam = req.getParameter("overdraft");
        Account account;
        if (typeParam.equals("CHECKING")) {
            account = new CheckingAccount(Double.parseDouble(balanceParam), Double.parseDouble(overdraftParam));
        } else {
            account = new SavingAccount(Double.parseDouble(balanceParam));
        }
        return account;
    }
}
