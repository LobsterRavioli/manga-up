package Order.OrderView;

import Order.DispatchService.Order;
import Order.DispatchService.OrderDAO;
import User.AccountService.Address;
import User.AccountService.CreditCard;
import User.AccountService.EndUser;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;

public class createOrderServletTMP extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DataSource ds = (DataSource)getServletContext().getAttribute("DataSource");
        OrderDAO model = new OrderDAO(ds);

        ArrayList<EndUser> endUsers = new ArrayList<>();

        EndUser endUser1 = new EndUser(1);
        EndUser endUser2 = new EndUser(3);
        EndUser endUser3 = new EndUser(7);
        EndUser endUser4 = new EndUser(4);
        EndUser endUser5 = new EndUser(2);

        endUsers.add(endUser1);
        endUsers.add(endUser2);
        endUsers.add(endUser3);
        endUsers.add(endUser4);
        endUsers.add(endUser5);


        ArrayList<Address> addresses = new ArrayList<>();
        addresses.add(new Address(1, endUser1, "Italia", "Citta Z", "Via nuova", "8033", "1234567890", "Campania"));
        addresses.add(new Address(2, endUser2, "Italia", "Citta B", "Via JJ", "8010", "1578677842", "Sicilia"));
        addresses.add(new Address(3, endUser3, "Italia", "Citta V", "Via asfaltata", "8043", "8504578890", "Lombardia"));
        addresses.add(new Address(4, endUser4, "Italia", "Citta K", "Via Google", "8023", "5671747999", "Basilicata"));
        addresses.add(new Address(5, endUser5, "Italia", "Citta J", "Via quasi nuova", "8066", "1231259860", "Molise"));

        ArrayList<CreditCard> cards = new ArrayList<>();

        CreditCard c1 = new CreditCard();
        c1.setCardNumber("4444000011239988");
        c1.setCvv("123");
        c1.setCardHolder("Alessandro");
        c1.setExpirementDate(Date.valueOf("2022-12-12"));

        CreditCard c2 = new CreditCard();
        c2.setCardNumber("4954056019578988");
        c2.setCvv("483");
        c2.setCardHolder("Giuseppe");
        c2.setExpirementDate(Date.valueOf("2025-10-06"));

        CreditCard c3 = new CreditCard();
        c3.setCardNumber("498906044778933");
        c3.setCvv("422");
        c3.setCardHolder("Gino");
        c3.setExpirementDate(Date.valueOf("2025-10-06"));

        CreditCard c4 = new CreditCard();
        c4.setCardNumber("411996447675588");
        c4.setCvv("967");
        c4.setCardHolder("Lorenzo");
        c4.setExpirementDate(Date.valueOf("2025-11-08"));

        CreditCard c5 = new CreditCard();
        c5.setCardNumber("4177344477733948");
        c5.setCvv("955");
        c5.setCardHolder("Francesco");
        c5.setExpirementDate(Date.valueOf("2025-11-08"));

        cards.add(c1);
        cards.add(c2);
        cards.add(c3);
        cards.add(c4);
        cards.add(c5);

        try
        {
            /*
             * Create an order for each EndUser
             */
            Order newOrder = null;
            for(int i = 0; i < endUsers.size(); i++)
            {
                newOrder = new Order(endUsers.get(i), addresses.get(i), cards.get(i));

                synchronized (newOrder) {
                    model.create(newOrder);
                }

                request.removeAttribute("taskOrder");
                request.setAttribute("taskOrder", newOrder); // l'ordine da assegnare
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
