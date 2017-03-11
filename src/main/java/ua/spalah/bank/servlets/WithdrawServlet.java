package ua.spalah.bank.servlets;

import ua.spalah.bank.exceptions.NotEnoughFundsException;
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
 * Created by Kostya on 12.03.2017.
 */
public class WithdrawServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = req.getSession().getServletContext();
        ClientService clientService = (ClientService) context.getAttribute("clientService");
        AccountService accountService = (AccountService) context.getAttribute("accountService");
        String clientId = req.getParameter("clientId");
        String accountId = req.getParameter("id");
        String amount = req.getParameter("amount");
        Account account = accountService.findAccountById(Long.parseLong(accountId));
        try {
            accountService.withdraw(account, Double.parseDouble(amount));
        } catch (NotEnoughFundsException e) {
            e.getMessage();
        }
        resp.sendRedirect("/client/account?id=" + accountId + "&clientId=" + clientId);
    }
}
