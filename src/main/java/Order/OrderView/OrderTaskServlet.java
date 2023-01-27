package Order.OrderView;

import Order.DispatchService.Order;
import Order.DispatchService.ToManage;
import Order.DispatchService.ToManageDAO;
import User.AccountService.User;
import User.AccountService.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;


public class OrderTaskServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // ********* RECUPERARE L'ORIDNE DA GESTIRE E ASSEGNARLO A orderToMan *********

        Order orderToMan = null;

        if(orderToMan == null) // ********* fittizio, giusto per provare *********
        {
            orderToMan = new Order();
            orderToMan.setId(6);
        }

        DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
        UserDAO userDAO = new UserDAO(ds);
        ToManageDAO toManageDAO = new ToManageDAO(ds);

        Collection<User> orderManagers;

        try
        {
            orderManagers = userDAO.getAllBeginnerOrderManagers(); //RECUPERO I GESTORI DEGLI ORDINI CHE HANNO GESTITO 0 ORDINI

            if(orderManagers != null)
            {
                toManageDAO.create(new ToManage(orderManagers.iterator().next(), orderToMan)); // assegna un task a uno qualsiasi di loro (il plrimo)
            }
            else // RECUPERA I GESTORI CHE HANNO GESTITO ALMENO UN ORDINE E LI RESTITUISCE ORDINATI IN BASE AL NUMERO DI ORIDNI GESTITI
            {
                String userName = userDAO.getTargetOrderManagerUserName();
                if(userName != null && !userName.equals(""))
                {
                    User user = new User();
                    user.setUsername(userName);

                    toManageDAO.create(new ToManage(user, orderToMan));
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
