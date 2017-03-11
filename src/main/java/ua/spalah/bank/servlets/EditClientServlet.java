package ua.spalah.bank.servlets;

import ua.spalah.bank.exceptions.ClientAlreadyExistsException;
import ua.spalah.bank.models.Client;
import ua.spalah.bank.models.type.Gender;
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
public class EditClientServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            ServletContext context = req.getSession().getServletContext();
            ClientService clientService = (ClientService) context.getAttribute("clientService");
            String idParam = req.getParameter("id");

            Client client = null;
            if (idParam != null) {
                client = clientService.findClientById(Long.parseLong(idParam));
            } else {
                client = new Client();
            }
            req.setAttribute("client", client);
            req.getRequestDispatcher("/WEB-INF/jsp/edit-client.jsp").forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext context = req.getSession().getServletContext();
        ClientService clientService = (ClientService) context.getAttribute("clientService");

        Client client = createClient(req);
        String idParam = req.getParameter("id");
        if (idParam != null && !idParam.equals("0")) {
            client.setId(Long.parseLong(idParam));
            client = clientService.updateClient(client);
        } else {
            try {
                client = clientService.saveClient(client);
            } catch (ClientAlreadyExistsException e) {
                e.printStackTrace();
            }
        }
        //req.setAttribute("client", client);
        //req.getRequestDispatcher("/WEB-INF/views/client.jsp").forward(req, resp);

        resp.sendRedirect("/client?id=" + client.getId());
    }
    private Client createClient(HttpServletRequest req) {
        String nameParam = req.getParameter("name");
        String genderParam = req.getParameter("gender");
        String emailParam = req.getParameter("email");
        String telParam = req.getParameter("tel");
        String cityParam = req.getParameter("city");

        Client client = new Client(nameParam, Gender.valueOf(genderParam), emailParam, telParam, cityParam);
        return client;
    }
}
