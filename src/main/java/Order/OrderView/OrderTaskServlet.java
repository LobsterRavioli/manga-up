package Order.DispatchService.service_layer;

import Order.DispatchService.beans.Order;
import Order.DispatchService.beans.ToManage;
import Order.DispatchService.dao_layer.implementations.ToManageDAOImp;
import Order.DispatchService.dao_layer.interfaces.ToManageDAO;
import User.AccountService.beans.User;
import User.AccountService.dao_layer.implementations.UserDAOImp;
import User.AccountService.dao_layer.interfaces.UserDAO;

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

        // RECUPERARE L'ORIDNE DA GESTIRE (DA DOVE ? DALLA SESSIONE ?)

        Order orderToMan = (Order)request.getSession().getAttribute("order_to_manage");

        if(orderToMan == null) // ********* fittizio, giusto per provare *********
        {
            orderToMan = new Order();
            orderToMan.setId(6);
        }

        DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
        UserDAO userDAO = new UserDAOImp(ds);
        ToManageDAO toManageDAO = new ToManageDAOImp(ds);

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
                int userID = userDAO.getTargetOrderManagerId();
                if(userID != 0)
                {
                    User user = new User();
                    user.setId(userID);

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
