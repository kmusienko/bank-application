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
import java.util.List;

/**
 * Created by Kostya on 08.03.2017.
 */
public class FindClientServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = req.getSession().getServletContext();
        ClientService clientService = (ClientService) context.getAttribute("clientService");
        AccountService accountService = (AccountService) context.getAttribute("accountService");

        String idParam = req.getParameter("id");

        if (idParam != null) {
            long id = Long.parseLong(idParam);
            Client client = clientService.findClientById(id);
            req.setAttribute("client", client);

            List<Account> accounts = clientService.getAccounts(client);
            req.setAttribute("accounts", accounts);

            req.getRequestDispatcher("/WEB-INF/jsp/client.jsp").forward(req, resp);
        } else {
            req.setAttribute("clients", clientService.findAllClients());
            req.getRequestDispatcher("/WEB-INF/jsp/all-clients.jsp").forward(req, resp);
        }
    }
}
